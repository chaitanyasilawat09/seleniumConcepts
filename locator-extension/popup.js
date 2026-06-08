document.addEventListener('DOMContentLoaded', function() {
    const enableToggle = document.getElementById('enableToggle');
    const selectedElementSection = document.getElementById('selectedElementSection');
    const selectedElementInfo = document.getElementById('selectedElementInfo');
    const locatorsContainer = document.getElementById('locatorsContainer');
    
    // Load saved state and check for selected element
    chrome.storage.local.get(['enabled', 'selectedElement'], function(result) {
        if (result.enabled !== undefined) {
            enableToggle.checked = result.enabled;
        }
        
        // Display selected element if exists
        if (result.selectedElement) {
            console.log('Found stored selected element:', result.selectedElement);
            displaySelectedElement(result.selectedElement);
            // Clear the stored data after displaying
            chrome.storage.local.remove('selectedElement');
        }
    });

    // Toggle locator mode
    enableToggle.addEventListener('change', function() {
        const enabled = enableToggle.checked;
        
        // Save state
        chrome.storage.local.set({ enabled: enabled });
        
        // Send message to content script
        chrome.tabs.query({ active: true, currentWindow: true }, function(tabs) {
            chrome.tabs.sendMessage(tabs[0].id, { action: 'toggle', enabled: enabled }, function(response) {
                if (chrome.runtime.lastError) {
                    console.error('Error sending message:', chrome.runtime.lastError);
                }
            });
        });
    });

    // Check current tab status on popup open
    chrome.tabs.query({ active: true, currentWindow: true }, function(tabs) {
        if (tabs[0].url.startsWith('chrome://') || tabs[0].url.startsWith('chrome-extension://')) {
            enableToggle.disabled = true;
            enableToggle.checked = false;
            const instructions = document.querySelector('.instructions ul');
            instructions.innerHTML = '<li>Extension cannot work on Chrome pages</li><li>Navigate to a regular website to use</li>';
        }
    });

    function displaySelectedElement(data) {
        console.log('displaySelectedElement called with data:', data);
        
        // Show the selected element section
        selectedElementSection.style.display = 'block';
        
        // Display element info
        const id = data.id ? `#${data.id}` : '';
        const classes = data.className ? `.${String(data.className).split(' ').join('.')}` : '';
        const text = data.text ? data.text.substring(0, 50) + (data.text.length > 50 ? '...' : '') : '';
        
        selectedElementInfo.innerHTML = `
            <div class="element-tag">&lt;${data.tagName}${id}${classes}&gt;</div>
            ${text ? `<div class="element-text"><strong>Text:</strong> ${escapeHtml(text)}</div>` : ''}
        `;
        
        // Display all locators
        if (data.locators && data.locators.length > 0) {
            const locatorsHTML = data.locators.map(locator => `
                <div class="popup-locator-item">
                    <div class="popup-locator-header">
                        <span class="popup-locator-type">${locator.type}</span>
                        <span class="popup-locator-priority">Priority: ${locator.priority}</span>
                    </div>
                    <div class="popup-locator-value">${escapeHtml(locator.value)}</div>
                    <div class="popup-locator-codes">
                        <div class="popup-code-item">
                            <span class="popup-code-label">Selenium:</span>
                            <code>${escapeHtml(locator.selenium)}</code>
                            <button class="popup-copy-btn" data-value="${escapeHtml(locator.selenium)}">Copy</button>
                        </div>
                        <div class="popup-code-item">
                            <span class="popup-code-label">Playwright:</span>
                            <code>${escapeHtml(locator.playwright)}</code>
                            <button class="popup-copy-btn" data-value="${escapeHtml(locator.playwright)}">Copy</button>
                        </div>
                    </div>
                </div>
            `).join('');
            
            locatorsContainer.innerHTML = locatorsHTML;
            
            // Add copy button listeners
            locatorsContainer.querySelectorAll('.popup-copy-btn').forEach(btn => {
                btn.addEventListener('click', function() {
                    copyToClipboard(this.dataset.value);
                });
            });
        } else {
            locatorsContainer.innerHTML = '<div class="no-locators">No locators found</div>';
        }
    }

    function escapeHtml(text) {
        const div = document.createElement('div');
        div.textContent = text;
        return div.innerHTML;
    }

    function copyToClipboard(text) {
        navigator.clipboard.writeText(text).then(() => {
            const btn = event.target;
            const originalText = btn.textContent;
            btn.textContent = 'Copied!';
            btn.style.background = '#28a745';
            
            setTimeout(() => {
                btn.textContent = originalText;
                btn.style.background = '';
            }, 2000);
        });
    }
});
