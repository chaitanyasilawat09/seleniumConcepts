console.log('=== Element Locator Generator Content Script Loaded ===');

class LocatorGenerator {
    constructor() {
        console.log('LocatorGenerator constructor called, window.self === window.top:', window.self === window.top);
        this.isEnabled = false;
        this.currentElement = null;
        this.highlightElement = null;
        this.locatorPanel = null;
        this.tooltip = null;
        this.selectedElement = null;
        this.selectedFilters = ['ID', 'Name', 'Class', 'Tag Name', 'Link Text', 'Partial Link Text', 'CSS Selector', 'XPath', 'Table Row', 'Table Cell', 'frameLocator (by name)', 'frameLocator (by URL)', 'frameLocator (by selector)', ':text-matches()', ':has()', 'id=', 'data-testid=', 'data-test-id=', 'data-test='];
        this.selectedFramework = 'selenium'; // 'selenium', 'playwright', or 'both'
        this.isRecording = false;
        this.recordedActions = [];
        this.recordingLanguage = 'javascript';
        this.lastClickTime = 0;
        this.lastClickElement = null;
        this.init();
    }

    init() {
        // Listen for messages from popup
        chrome.runtime.onMessage.addListener((request, sender, sendResponse) => {
            console.log('Content script received message:', request.action, request);
            if (request.action === 'toggle') {
                console.log('Toggle message received with enabled:', request.enabled);
                this.toggle(request.enabled);
                sendResponse({ success: true });
            }
        });

        // Listen for messages from iframes (only in main window)
        if (window.self === window.top) {
            window.addEventListener('message', (event) => {
                if (event.data.action === 'elementClicked') {
                    console.log('Received element click from iframe:', event.data);
                    this.displayAllLocatorsInPanelFromData(event.data.elementData, event.data.frameInfo);
                }
            });
        }

        // Add keyboard shortcut listener
        document.addEventListener('keydown', (e) => {
            if (e.ctrlKey && e.shiftKey && e.key === 'L') {
                this.toggle(!this.isEnabled);
            }
        });
    }

    toggle(enabled) {
        console.log('Toggle called with enabled:', enabled);
        this.isEnabled = enabled;
        if (enabled) {
            this.enableLocatorMode();
        } else {
            this.disableLocatorMode();
        }
    }

    togglePickingMode() {
        this.isPickingMode = !this.isPickingMode;
        const startPickingBtn = this.locatorPanel.querySelector('#startPickingBtn');
        const recordingPanel = this.locatorPanel.querySelector('#recordingPanel');
        const locatorPanelContent = this.locatorPanel.querySelector('.locator-panel-content');
        
        if (this.isPickingMode) {
            // Enable picking mode
            startPickingBtn.textContent = '⏹ Stop Picking';
            startPickingBtn.classList.add('active');
            document.body.style.cursor = 'crosshair';
            document.addEventListener('mouseover', this.handleMouseOver);
            document.addEventListener('mouseout', this.handleMouseOut);
            document.addEventListener('click', this.handleClick);
            
            // Hide recording panel and show locator content
            if (recordingPanel) recordingPanel.style.display = 'none';
            if (locatorPanelContent) locatorPanelContent.style.display = 'block';
            
            // Stop recording if it's active
            if (this.isRecording) {
                this.stopRecording();
            }
            
            console.log('Picking mode enabled');
        } else {
            // Disable picking mode
            startPickingBtn.textContent = '🎯 Start Picking';
            startPickingBtn.classList.remove('active');
            document.body.style.cursor = '';
            document.removeEventListener('mouseover', this.handleMouseOver);
            document.removeEventListener('mouseout', this.handleMouseOut);
            document.removeEventListener('click', this.handleClick);
            this.removeHighlight();
            this.hideTooltip();
            console.log('Picking mode disabled');
        }
    }

    enableLocatorMode() {
        console.log('Enabling locator mode, window.self === window.top:', window.self === window.top);
        document.body.style.cursor = 'crosshair';
        document.addEventListener('mouseover', this.handleMouseOver);
        document.addEventListener('mouseout', this.handleMouseOut);
        document.addEventListener('click', this.handleClick);
        console.log('Event listeners attached in', window.self === window.top ? 'main window' : 'iframe');
        
        // Only create panel if we're in the main window (not inside an iframe)
        if (window.self === window.top) {
            console.log('About to create locator panel');
            this.createLocatorPanel();
            console.log('Locator panel creation completed');
            
            // Auto-enable picking mode when locator mode is enabled from popup
            if (this.locatorPanel && !this.isPickingMode) {
                this.isPickingMode = true;
                const startPickingBtn = this.locatorPanel.querySelector('#startPickingBtn');
                if (startPickingBtn) {
                    startPickingBtn.textContent = '⏹ Stop Picking';
                    startPickingBtn.classList.add('active');
                }
                console.log('Auto-enabled picking mode from popup toggle');
            }
        } else {
            console.log('Skipping panel creation - running inside iframe');
        }
    }

    disableLocatorMode() {
        document.body.style.cursor = '';
        document.removeEventListener('mouseover', this.handleMouseOver);
        document.removeEventListener('mouseout', this.handleMouseOut);
        document.removeEventListener('click', this.handleClick);
        this.removeHighlight();
        this.hideTooltip();
        this.removeLocatorPanel();
    }

    handleMouseOver = (event) => {
        if (!this.isEnabled) return;
        
        // Only show tooltip in main window, not in iframes
        if (window.self !== window.top) return;
        
        const element = event.target;
        if (element === this.locatorPanel || this.locatorPanel?.contains(element) ||
            element === this.tooltip || this.tooltip?.contains(element)) {
            return;
        }

        this.currentElement = element;
        this.highlightCurrentElement(element);
        this.showTooltip(element, event);
        
        console.log('Mouse over:', element.tagName, element.id, element.className);
    }

    handleMouseOut = (event) => {
        if (!this.isEnabled) return;
        
        console.log('Mouse out event');
        
        // Only hide if we're leaving the element (not moving to a child element)
        if (event.target !== this.currentElement && !event.target.contains(event.relatedTarget)) {
            // Only hide if not moving to tooltip
            const relatedTarget = event.relatedTarget;
            if (relatedTarget !== this.tooltip && !this.tooltip?.contains(relatedTarget)) {
                this.removeHighlight();
                this.hideTooltip();
            }
        }
    }

    handleClick = (event) => {
        if (!this.isEnabled) return;
        
        const element = event.target;
        if (element === this.locatorPanel || this.locatorPanel?.contains(element) ||
            element === this.tooltip || this.tooltip?.contains(element)) {
            return;
        }

        event.preventDefault();
        event.stopPropagation();
        
        // If element is SVG or path, find a better parent element
        let targetElement = element;
        if (element.tagName.toLowerCase() === 'svg' || element.tagName.toLowerCase() === 'path') {
            console.log('Ignoring SVG/path tag, finding parent element');
            targetElement = this.findBestParentElement(element);
        }
        
        // If we're in an iframe, send element data to main window
        if (window.self !== window.top) {
            console.log('Click in iframe, sending to main window');
            
            const elementData = {
                tagName: targetElement.tagName,
                id: targetElement.id,
                name: targetElement.name,
                className: targetElement.className,
                textContent: targetElement.textContent,
                attributes: {}
            };
            
            // Collect all attributes
            for (let i = 0; i < targetElement.attributes.length; i++) {
                const attr = targetElement.attributes[i];
                elementData.attributes[attr.name] = attr.value;
            }
            
            const frameInfo = this.getFrameInfo(targetElement);
            
            window.top.postMessage({
                action: 'elementClicked',
                elementData: elementData,
                frameInfo: frameInfo
            }, '*');
            
            console.log('Sent element data to main window');
            return;
        }
        
        this.selectedElement = targetElement;
        this.hideTooltip();
        this.displayAllLocatorsInPanel(targetElement);
        
        // If in picking mode, disable it after clicking on an element
        if (this.isPickingMode) {
            this.togglePickingMode();
        }
    }

    highlightCurrentElement(element) {
        this.removeHighlight();
        
        this.highlightElement = document.createElement('div');
        this.highlightElement.style.cssText = `
            position: absolute;
            pointer-events: none;
            border: 2px solid #ff6b6b;
            background: rgba(255, 107, 107, 0.2);
            z-index: 999999;
            box-shadow: 0 0 10px rgba(255, 107, 107, 0.5);
        `;
        
        const rect = element.getBoundingClientRect();
        this.highlightElement.style.top = `${rect.top + window.scrollY}px`;
        this.highlightElement.style.left = `${rect.left + window.scrollX}px`;
        this.highlightElement.style.width = `${rect.width}px`;
        this.highlightElement.style.height = `${rect.height}px`;
        
        document.body.appendChild(this.highlightElement);
    }

    removeHighlight() {
        if (this.highlightElement) {
            this.highlightElement.remove();
            this.highlightElement = null;
        }
    }

    createLocatorPanel() {
        console.log('createLocatorPanel called, this.locatorPanel:', this.locatorPanel);
        
        if (this.locatorPanel) {
            console.log('Locator panel already exists, skipping creation');
            return;
        }

        console.log('Creating locator panel element');
        this.locatorPanel = document.createElement('div');
        this.locatorPanel.id = 'locator-generator-panel';
        this.locatorPanel.innerHTML = `
            <div class="locator-panel-header">
                <div class="panel-header-content">
                    <div class="drag-handle" id="dragHandle">⋮⋮</div>
                    <h3>🎯 Element Locators Panel</h3>
                    <button class="close-btn" onclick="window.locatorGenerator.disableLocatorMode()">×</button>
                </div>
                <div class="panel-instructions">Click on any element to see all locators</div>
                <div class="panel-actions">
                    <button class="start-picking-btn" id="startPickingBtn">🎯 Start Picking</button>
                    <button class="record-play-btn" id="recordPlayBtn">🔴 Record & Play</button>
                </div>
            </div>
            <div class="recording-panel" id="recordingPanel" style="display: none;">
                <div class="recording-controls">
                    <button class="rec-btn" id="startRecordingBtn">▶ Start Recording</button>
                    <button class="rec-btn" id="stopRecordingBtn" style="display: none;">⏹ Stop Recording</button>
                    <button class="rec-btn" id="playRecordingBtn">▶ Play</button>
                    <button class="rec-btn" id="clearRecordingBtn">🗑 Clear</button>
                    <button class="rec-btn" id="copyCodeBtn">📋 Copy Code</button>
                </div>
                <div class="language-selector">
                    <label for="languageSelect">Language:</label>
                    <select id="languageSelect">
                        <option value="javascript">JavaScript</option>
                        <option value="java">Java</option>
                        <option value="python">Python</option>
                    </select>
                </div>
                <div class="recording-status" id="recordingStatus"></div>
                <textarea class="recording-code" id="recordingCode" placeholder="Recorded Playwright code will appear here..."></textarea>
            </div>
            <div class="locator-panel-content">
                <div class="framework-selector">
                    <div class="framework-option">
                        <input type="radio" id="framework-selenium" name="framework" value="selenium" checked>
                        <label for="framework-selenium">Selenium</label>
                    </div>
                    <div class="framework-option">
                        <input type="radio" id="framework-playwright" name="framework" value="playwright">
                        <label for="framework-playwright">Playwright</label>
                    </div>
                </div>
                <div class="filter-section">
                    <div class="filter-toggle" id="filterToggle">
                        <span>🔍 Filter Locators</span>
                        <span class="filter-arrow">▼</span>
                    </div>
                    <div class="filter-dropdown" id="filterDropdown" style="display: none;">
                        <div class="filter-option all-filter">
                            <input type="checkbox" id="filter-All" value="All" checked>
                            <label for="filter-All">All checked</label>
                        </div>
                        <div class="filter-divider"></div>
                        <div class="filter-option selenium-filter">
                            <input type="checkbox" id="filter-ID" value="ID" checked>
                            <label for="filter-ID">ID</label>
                        </div>
                        <div class="filter-option selenium-filter">
                            <input type="checkbox" id="filter-Name" value="Name" checked>
                            <label for="filter-Name">Name</label>
                        </div>
                        <div class="filter-option selenium-filter">
                            <input type="checkbox" id="filter-Class" value="Class" checked>
                            <label for="filter-Class">Class</label>
                        </div>
                        <div class="filter-option selenium-filter">
                            <input type="checkbox" id="filter-Tag" value="Tag Name" checked>
                            <label for="filter-Tag">Tag Name</label>
                        </div>
                        <div class="filter-option selenium-filter">
                            <input type="checkbox" id="filter-Link Text" value="Link Text" checked>
                            <label for="filter-Link Text">Link Text</label>
                        </div>
                        <div class="filter-option selenium-filter">
                            <input type="checkbox" id="filter-Partial Link Text" value="Partial Link Text" checked>
                            <label for="filter-Partial Link Text">Partial Link Text</label>
                        </div>
                        <div class="filter-option both-filter">
                            <input type="checkbox" id="filter-CSS Selector" value="CSS Selector" checked>
                            <label for="filter-CSS Selector">CSS Selector</label>
                        </div>
                        <div class="filter-option both-filter">
                            <input type="checkbox" id="filter-XPath" value="XPath" checked>
                            <label for="filter-XPath">XPath</label>
                        </div>
                        <div class="filter-divider"></div>
                        <div class="filter-option playwright-filter">
                            <input type="checkbox" id="filter-getByTestId" value="getByTestId" checked>
                            <label for="filter-getByTestId">getByTestId</label>
                        </div>
                        <div class="filter-option playwright-filter">
                            <input type="checkbox" id="filter-getByRole" value="getByRole" checked>
                            <label for="filter-getByRole">getByRole</label>
                        </div>
                        <div class="filter-option playwright-filter">
                            <input type="checkbox" id="filter-getByText" value="getByText" checked>
                            <label for="filter-getByText">getByText</label>
                        </div>
                        <div class="filter-option playwright-filter">
                            <input type="checkbox" id="filter-getByLabel" value="getByLabel" checked>
                            <label for="filter-getByLabel">getByLabel</label>
                        </div>
                        <div class="filter-option playwright-filter">
                            <input type="checkbox" id="filter-getByPlaceholder" value="getByPlaceholder" checked>
                            <label for="filter-getByPlaceholder">getByPlaceholder</label>
                        </div>
                        <div class="filter-option playwright-filter">
                            <input type="checkbox" id="filter-getByAltText" value="getByAltText" checked>
                            <label for="filter-getByAltText">getByAltText</label>
                        </div>
                        <div class="filter-option playwright-filter">
                            <input type="checkbox" id="filter-getByTitle" value="getByTitle" checked>
                            <label for="filter-getByTitle">getByTitle</label>
                        </div>
                        <div class="filter-option playwright-filter">
                            <input type="checkbox" id="filter-:has-text()" value=":has-text()" checked>
                            <label for="filter-:has-text()">:has-text()</label>
                        </div>
                        <div class="filter-option playwright-filter">
                            <input type="checkbox" id="filter-:visible" value=":visible" checked>
                            <label for="filter-:visible">:visible</label>
                        </div>
                        <div class="filter-option playwright-filter">
                            <input type="checkbox" id="filter-:text()" value=":text()" checked>
                            <label for="filter-:text()">:text()</label>
                        </div>
                        <div class="filter-option playwright-filter">
                            <input type="checkbox" id="filter-:text-is()" value=":text-is()" checked>
                            <label for="filter-:text-is()">:text-is()</label>
                        </div>
                        <div class="filter-option playwright-filter">
                            <input type="checkbox" id="filter-nth=" value="nth=" checked>
                            <label for="filter-nth=">nth=</label>
                        </div>
                        <div class="filter-option both-filter">
                            <input type="checkbox" id="filter-Table Row" value="Table Row" checked>
                            <label for="filter-Table Row">Table Row</label>
                        </div>
                        <div class="filter-option both-filter">
                            <input type="checkbox" id="filter-Table Cell" value="Table Cell" checked>
                            <label for="filter-Table Cell">Table Cell</label>
                        </div>
                        <div class="filter-divider"></div>
                        <div class="filter-option both-filter">
                            <input type="checkbox" id="filter-frameLocator (by name)" value="frameLocator (by name)" checked>
                            <label for="filter-frameLocator (by name)">frameLocator (by name)</label>
                        </div>
                        <div class="filter-option both-filter">
                            <input type="checkbox" id="filter-frameLocator (by URL)" value="frameLocator (by URL)" checked>
                            <label for="filter-frameLocator (by URL)">frameLocator (by URL)</label>
                        </div>
                        <div class="filter-option both-filter">
                            <input type="checkbox" id="filter-frameLocator (by selector)" value="frameLocator (by selector)" checked>
                            <label for="filter-frameLocator (by selector)">frameLocator (by selector)</label>
                        </div>
                        <div class="filter-divider"></div>
                        <div class="filter-option playwright-filter">
                            <input type="checkbox" id="filter-:text-matches()" value=":text-matches()" checked>
                            <label for="filter-:text-matches()">:text-matches()</label>
                        </div>
                        <div class="filter-option playwright-filter">
                            <input type="checkbox" id="filter-:has()" value=":has()" checked>
                            <label for="filter-:has()">:has()</label>
                        </div>
                        <div class="filter-option playwright-filter">
                            <input type="checkbox" id="filter-id=" value="id=" checked>
                            <label for="filter-id=">id=</label>
                        </div>
                        <div class="filter-option playwright-filter">
                            <input type="checkbox" id="filter-data-testid=" value="data-testid=" checked>
                            <label for="filter-data-testid=">data-testid=</label>
                        </div>
                        <div class="filter-option playwright-filter">
                            <input type="checkbox" id="filter-data-test-id=" value="data-test-id=" checked>
                            <label for="filter-data-test-id=">data-test-id=</label>
                        </div>
                        <div class="filter-option playwright-filter">
                            <input type="checkbox" id="filter-data-test=" value="data-test=" checked>
                            <label for="filter-data-test=">data-test=</label>
                        </div>
                    </div>
                </div>
                <div class="element-info" id="elementInfo"></div>
                <div class="locators-list" id="locatorsList"></div>
            </div>
        `;
        
        console.log('Appending locator panel to body');
        document.body.appendChild(this.locatorPanel);
        console.log('Locator panel appended, offsetParent:', this.locatorPanel.offsetParent);
        
        // Make panel draggable
        this.makePanelDraggable();
        
        // Set initial framework class on filter dropdown
        const filterDropdown = this.locatorPanel.querySelector('#filterDropdown');
        if (filterDropdown) {
            filterDropdown.classList.add(`framework-${this.selectedFramework}`);
        }
        
        // Attach event listeners programmatically
        const frameworkRadios = this.locatorPanel.querySelectorAll('input[name="framework"]');
        frameworkRadios.forEach(radio => {
            radio.addEventListener('change', (e) => this.updateFramework(e.target.value));
        });
        console.log('Framework radio event listeners attached, count:', frameworkRadios.length);
        
        // Start Picking button event listener
        const startPickingBtn = this.locatorPanel.querySelector('#startPickingBtn');
        if (startPickingBtn) {
            startPickingBtn.addEventListener('click', () => this.togglePickingMode());
        }
        
        // Record & Play button event listener
        const recordPlayBtn = this.locatorPanel.querySelector('#recordPlayBtn');
        if (recordPlayBtn) {
            recordPlayBtn.addEventListener('click', () => this.toggleRecordingPanel());
        }
        
        // Recording control buttons
        const startRecordingBtn = this.locatorPanel.querySelector('#startRecordingBtn');
        if (startRecordingBtn) {
            startRecordingBtn.addEventListener('click', () => this.startRecording());
        }
        
        const stopRecordingBtn = this.locatorPanel.querySelector('#stopRecordingBtn');
        if (stopRecordingBtn) {
            stopRecordingBtn.addEventListener('click', () => this.stopRecording());
        }
        
        const playRecordingBtn = this.locatorPanel.querySelector('#playRecordingBtn');
        if (playRecordingBtn) {
            playRecordingBtn.addEventListener('click', () => this.playRecording());
        }
        
        const clearRecordingBtn = this.locatorPanel.querySelector('#clearRecordingBtn');
        if (clearRecordingBtn) {
            clearRecordingBtn.addEventListener('click', () => this.clearRecording());
        }
        
        const copyCodeBtn = this.locatorPanel.querySelector('#copyCodeBtn');
        if (copyCodeBtn) {
            copyCodeBtn.addEventListener('click', () => this.copyRecordedCode());
        }
        
        const languageSelect = this.locatorPanel.querySelector('#languageSelect');
        if (languageSelect) {
            languageSelect.addEventListener('change', (e) => {
                this.recordingLanguage = e.target.value;
                this.updateRecordedCode();
            });
        }
        
        const filterToggle = this.locatorPanel.querySelector('#filterToggle');
        if (filterToggle) {
            filterToggle.addEventListener('click', () => this.toggleFilterDropdown());
            console.log('Filter toggle event listener attached');
        }
        
        const allCheckedCheckbox = this.locatorPanel.querySelector('#filter-All');
        if (allCheckedCheckbox) {
            allCheckedCheckbox.addEventListener('change', (e) => this.toggleAllFilters(e.target.checked));
            console.log('All checked checkbox listener attached');
        }
        
        const checkboxes = this.locatorPanel.querySelectorAll('#filterDropdown input[type="checkbox"]:not(#filter-All)');
        checkboxes.forEach(checkbox => {
            checkbox.addEventListener('change', () => {
                this.updateFilters();
                this.syncAllCheckedCheckbox();
            });
        });
        console.log('Checkbox event listeners attached, count:', checkboxes.length);
        
        // Add click-outside listener to auto-close dropdown
        document.addEventListener('click', (e) => {
            const filterSection = this.locatorPanel?.querySelector('.filter-section');
            if (filterSection && !filterSection.contains(e.target)) {
                const dropdown = this.locatorPanel?.querySelector('#filterDropdown');
                if (dropdown && dropdown.style.display !== 'none') {
                    dropdown.style.display = 'none';
                }
            }
        });
        console.log('Click-outside listener attached');
        
        // Load saved filter preferences
        this.loadFilterPreferences();
        
        console.log('Locator panel created successfully');
    }

    removeLocatorPanel() {
        if (this.locatorPanel) {
            this.locatorPanel.remove();
            this.locatorPanel = null;
        }
        this.hideTooltip();
    }

    displayAllLocatorsInPanel(element) {
        console.log('displayAllLocatorsInPanel called for element:', element.tagName, element.id, element.className);
        
        const locators = this.getAllLocators(element);
        console.log('Generated locators:', locators.length, locators.map(l => l.type));
        
        const sortedLocators = this.sortLocatorsByPriority(locators);
        
        // Filter locators based on selected filters
        let filteredLocators = sortedLocators.filter(locator => 
            this.selectedFilters.includes(locator.type)
        );
        console.log('Filtered locators:', filteredLocators.length, 'Selected filters:', this.selectedFilters);
        
        // Transform locators to show only selected framework
        const frameworkLocators = filteredLocators.map(locator => ({
            type: locator.type,
            value: this.selectedFramework === 'selenium' ? locator.selenium : locator.playwright,
            priority: locator.priority
        }));
        console.log('Framework locators:', frameworkLocators.length);
        
        this.displayElementInfo(element);
        this.displayFrameworkLocators(frameworkLocators);
        
        console.log('Displayed', frameworkLocators.length, 'locators in panel (filtered from', sortedLocators.length, ')');
    }

    displayAllLocatorsInPanelFromData(elementData, frameInfo) {
        console.log('displayAllLocatorsInPanelFromData called with frame info:', frameInfo);
        
        // Create a mock element object with the data
        const mockElement = {
            tagName: elementData.tagName,
            id: elementData.id,
            name: elementData.name,
            className: elementData.className,
            getAttribute: (attr) => elementData.attributes[attr] || null,
            textContent: elementData.textContent,
            parentElement: null
        };
        
        const locators = this.getAllLocators(mockElement);
        console.log('Generated locators from data:', locators.length, locators.map(l => l.type));
        
        const sortedLocators = this.sortLocatorsByPriority(locators);
        
        // Filter locators based on selected filters
        let filteredLocators = sortedLocators.filter(locator => 
            this.selectedFilters.includes(locator.type)
        );
        
        // Transform locators to show only selected framework and chain frame locator
        const frameworkLocators = filteredLocators.map(locator => {
            const seleniumLocator = locator.selenium;
            const playwrightLocator = locator.playwright;
            
            // Chain frame locator for Playwright
            if (this.selectedFramework === 'playwright' && frameInfo) {
                // Replace page. with frameLocator() for the first occurrence
                const chainedPlaywright = playwrightLocator.replace(/^page\./, `page.frameLocator("${frameInfo.selector}").`);
                return {
                    type: locator.type,
                    value: chainedPlaywright,
                    priority: locator.priority
                };
            }
            
            // For Selenium, frame handling is different (switchTo)
            if (this.selectedFramework === 'selenium' && frameInfo) {
                return {
                    type: locator.type,
                    value: `driver.switchTo().frame(${frameInfo.name ? `"${frameName}"` : `driver.findElement(By.cssSelector("${frameInfo.selector}"))`}).${seleniumLocator.replace(/^By\./, 'findElement(By.')}`,
                    priority: locator.priority
                };
            }
            
            // Return the correct locator based on selected framework
            if (this.selectedFramework === 'selenium') {
                return {
                    type: locator.type,
                    value: seleniumLocator,
                    priority: locator.priority
                };
            } else {
                return {
                    type: locator.type,
                    value: playwrightLocator,
                    priority: locator.priority
                };
            }
        });
        
        this.displayElementInfo(mockElement);
        this.displayFrameworkLocators(frameworkLocators);
        
        console.log('Displayed', frameworkLocators.length, 'locators in panel from iframe');
    }

    displayFrameworkLocators(locators) {
        const locatorsList = this.locatorPanel.querySelector('#locatorsList');
        
        if (locators.length === 0) {
            locatorsList.innerHTML = '<div class="no-locators">No locators found</div>';
            return;
        }

        const locatorsHTML = locators.map(locator => {
            const encodedValue = encodeURIComponent(locator.value);
            return `
            <div class="locator-item">
                <div class="locator-header">
                    <span class="locator-type">${locator.type}</span>
                    <div class="locator-header-actions">
                        <span class="locator-priority">${locator.priority}</span>
                        <button class="copy-btn" data-value="${encodedValue}">Copy</button>
                    </div>
                </div>
                <div class="locator-value">${this.escapeHtml(locator.value)}</div>
            </div>
            `;
        }).join('');
        
        locatorsList.innerHTML = locatorsHTML;
        
        // Add copy button listeners
        locatorsList.querySelectorAll('.copy-btn').forEach(btn => {
            btn.addEventListener('click', function() {
                const decodedValue = decodeURIComponent(this.dataset.value);
                navigator.clipboard.writeText(decodedValue).then(() => {
                    const originalText = btn.textContent;
                    btn.textContent = 'Copied!';
                    btn.style.background = '#28a745';
                    setTimeout(() => {
                        btn.textContent = originalText;
                        btn.style.background = '';
                    }, 2000);
                });
            });
        });
    }

    toggleFilterDropdown() {
        const dropdown = document.getElementById('filterDropdown');
        if (dropdown) {
            dropdown.style.display = dropdown.style.display === 'none' ? 'block' : 'none';
        }
    }

    toggleAllFilters(checked) {
        const checkboxes = document.querySelectorAll('#filterDropdown input[type="checkbox"]:not(#filter-All)');
        checkboxes.forEach(checkbox => {
            checkbox.checked = checked;
        });
        
        // Update selectedFilters
        this.updateFilters();
    }

    syncAllCheckedCheckbox() {
        const allCheckedCheckbox = document.querySelector('#filter-All');
        const checkboxes = document.querySelectorAll('#filterDropdown input[type="checkbox"]:not(#filter-All)');
        
        const allChecked = Array.from(checkboxes).every(checkbox => checkbox.checked);
        
        if (allCheckedCheckbox) {
            allCheckedCheckbox.checked = allChecked;
        }
    }

    updateFilters() {
        const checkboxes = document.querySelectorAll('#filterDropdown input[type="checkbox"]');
        this.selectedFilters = [];
        
        checkboxes.forEach(checkbox => {
            if (checkbox.checked && checkbox.value !== 'All') {
                this.selectedFilters.push(checkbox.value);
            }
        });
        
        console.log('Updated filters:', this.selectedFilters);
        
        // Save filter preferences
        chrome.storage.local.set({ locatorFilters: this.selectedFilters });
        
        // Refresh the current element display if one is selected
        if (this.selectedElement) {
            this.displayAllLocatorsInPanel(this.selectedElement);
        }
    }

    loadFilterPreferences() {
        chrome.storage.local.get(['locatorFilters', 'selectedFramework'], (result) => {
            const filterDropdown = document.getElementById('filterDropdown');
            
            if (result.selectedFramework) {
                this.selectedFramework = result.selectedFramework;
                
                // Update radio buttons to match saved preference
                const frameworkRadios = document.querySelectorAll('input[name="framework"]');
                frameworkRadios.forEach(radio => {
                    radio.checked = radio.value === this.selectedFramework;
                });
                
                // Set framework class on dropdown
                if (filterDropdown) {
                    filterDropdown.classList.remove('framework-selenium', 'framework-playwright');
                    filterDropdown.classList.add(`framework-${this.selectedFramework}`);
                }
                
                console.log('Loaded framework preference:', this.selectedFramework);
            }
            
            // Set default framework class if not set
            if (filterDropdown && !filterDropdown.classList.contains('framework-selenium') && !filterDropdown.classList.contains('framework-playwright')) {
                filterDropdown.classList.add('framework-selenium');
            }
            
            if (result.locatorFilters) {
                this.selectedFilters = result.locatorFilters;
                
                // Update checkboxes to match saved preferences
                const checkboxes = document.querySelectorAll('#filterDropdown input[type="checkbox"]:not(#filter-All)');
                checkboxes.forEach(checkbox => {
                    checkbox.checked = this.selectedFilters.includes(checkbox.value);
                });
                
                // Sync All checked checkbox
                this.syncAllCheckedCheckbox();
                
                console.log('Loaded filter preferences:', this.selectedFilters);
            }
        });
    }

    updateFramework(framework) {
        console.log('Framework changed to:', framework);
        this.selectedFramework = framework;
        
        // Update filter dropdown visibility based on framework
        const filterDropdown = document.getElementById('filterDropdown');
        if (filterDropdown) {
            filterDropdown.classList.remove('framework-selenium', 'framework-playwright');
            filterDropdown.classList.add(`framework-${framework}`);
        }
        
        // Update selectedFilters based on framework
        if (framework === 'selenium') {
            this.selectedFilters = ['ID', 'Name', 'Class', 'Tag Name', 'Link Text', 'Partial Link Text', 'CSS Selector', 'XPath', 'Table Row', 'Table Cell', 'frameLocator (by name)', 'frameLocator (by URL)', 'frameLocator (by selector)'];
        } else if (framework === 'playwright') {
            this.selectedFilters = ['getByTestId', 'getByRole', 'getByText', 'getByLabel', 'getByPlaceholder', 'getByAltText', 'getByTitle', ':has-text()', ':visible', ':text()', ':text-is()', ':text-matches()', ':has()', 'id=', 'data-testid=', 'data-test-id=', 'data-test=', 'nth=', 'Table Row', 'Table Cell', 'frameLocator (by name)', 'frameLocator (by URL)', 'frameLocator (by selector)', 'CSS Selector', 'XPath'];
        }
        
        // Update checkboxes to match selectedFilters
        const checkboxes = document.querySelectorAll('#filterDropdown input[type="checkbox"]');
        checkboxes.forEach(checkbox => {
            checkbox.checked = this.selectedFilters.includes(checkbox.value);
        });
        
        // Save framework preference and filters
        chrome.storage.local.set({ 
            selectedFramework: framework,
            locatorFilters: this.selectedFilters
        });
        
        // Refresh the current element display if one is selected
        if (this.selectedElement) {
            this.displayAllLocatorsInPanel(this.selectedElement);
        }
    }

    showTooltip(element, event) {
        console.log('showTooltip called for element:', element.tagName, element.id, element.className);
        
        this.hideTooltip();
        
        const locators = this.getAllLocators(element);
        console.log('Generated locators:', locators.length, locators.map(l => l.type));
        
        const sortedLocators = this.sortLocatorsByPriority(locators);
        
        // Filter locators based on selected filters
        const filteredLocators = sortedLocators.filter(locator => 
            this.selectedFilters.includes(locator.type)
        );
        
        // Transform locators to show only selected framework
        const frameworkLocators = filteredLocators.map(locator => ({
            type: locator.type,
            value: this.selectedFramework === 'selenium' ? locator.selenium : locator.playwright,
            priority: locator.priority
        }));
        
        const top5Locators = frameworkLocators.slice(0, 5);
        
        this.tooltip = document.createElement('div');
        this.tooltip.id = 'locator-tooltip';
        this.tooltip.innerHTML = this.generateTooltipHTML(top5Locators);
        
        // Apply inline styles - smaller and more compact
        this.tooltip.style.position = 'absolute';
        this.tooltip.style.zIndex = '2147483647';
        this.tooltip.style.background = 'white';
        this.tooltip.style.color = '#333';
        this.tooltip.style.border = '1px solid #4facfe';
        this.tooltip.style.borderRadius = '4px';
        this.tooltip.style.padding = '8px 12px';
        this.tooltip.style.minWidth = '200px';
        this.tooltip.style.maxWidth = '300px';
        this.tooltip.style.width = 'auto';
        this.tooltip.style.height = 'auto';
        this.tooltip.style.fontFamily = 'Arial, sans-serif';
        this.tooltip.style.fontSize = '11px';
        this.tooltip.style.boxShadow = '0 2px 10px rgba(0, 0, 0, 0.2)';
        this.tooltip.style.display = 'block';
        this.tooltip.style.visibility = 'visible';
        this.tooltip.style.opacity = '1';
        this.tooltip.style.pointerEvents = 'none';
        
        // Position 50px below cursor
        const scrollTop = window.pageYOffset || document.documentElement.scrollTop;
        const scrollLeft = window.pageXOffset || document.documentElement.scrollLeft;
        
        let top = event.clientY + scrollTop + 50;
        let left = event.clientX + scrollLeft;
        
        // Adjust if tooltip would go off screen
        if (left + 300 > window.innerWidth + scrollLeft) {
            left = window.innerWidth + scrollLeft - 310;
        }
        if (top + 150 > window.innerHeight + scrollTop) {
            top = event.clientY + scrollTop - 160;
        }
        
        this.tooltip.style.top = `${top}px`;
        this.tooltip.style.left = `${left}px`;
        
        document.body.appendChild(this.tooltip);
        
        // Force a reflow
        void this.tooltip.offsetHeight;
        
        console.log('Tooltip appended to body. Position:', top, left);
    }

    hideTooltip() {
        if (this.tooltip) {
            this.tooltip.remove();
            this.tooltip = null;
        }
    }

    generateTooltipHTML(locators) {
        console.log('generateTooltipHTML called with', locators.length, 'locators');
        
        if (locators.length === 0) {
            return '<div class="tooltip-content">No locators found</div>';
        }

        const locatorsHTML = locators.map((locator, index) => `
            <div class="tooltip-locator-item">
                <span class="tooltip-locator-type">${locator.type}:</span>
                <span class="tooltip-locator-value">${this.escapeHtml(locator.value)}</span>
            </div>
        `).join('');

        return `<div class="tooltip-content">
            <div style="color: #333; font-weight: bold; margin-bottom: 6px; font-size: 12px;">🎯 Top ${locators.length} Locators</div>
            ${locatorsHTML}
        </div>`;
    }

    sendElementToPopup(element) {
        console.log('sendElementToPopup called for element:', element.tagName);
        
        const locators = this.getAllLocators(element);
        const sortedLocators = this.sortLocatorsByPriority(locators);
        
        const elementData = {
            tagName: element.tagName.toLowerCase(),
            id: element.id || '',
            className: element.className || '',
            text: element.textContent ? element.textContent.trim().substring(0, 100) : '',
            locators: sortedLocators
        };
        
        console.log('Storing element data in chrome.storage:', elementData);
        
        // Store in chrome.storage so popup can retrieve it when opened
        chrome.storage.local.set({ selectedElement: elementData }, function() {
            console.log('Element data stored successfully');
            if (chrome.runtime.lastError) {
                console.error('Error storing element data:', chrome.runtime.lastError);
            }
        });
    }

    generateAndDisplayLocators(element) {
        const locators = this.getAllLocators(element);
        const sortedLocators = this.sortLocatorsByPriority(locators);
        
        this.displayElementInfo(element);
        this.displayLocators(sortedLocators);
    }

    displayElementInfo(element) {
        const elementInfo = this.locatorPanel.querySelector('#elementInfo');
        const tagName = element.tagName.toLowerCase();
        const id = element.id ? `#${element.id}` : '';
        const classes = element.className ? `.${String(element.className).split(' ').join('.')}` : '';
        const text = element.textContent ? element.textContent.trim().substring(0, 50) + (element.textContent.length > 50 ? '...' : '') : '';

        elementInfo.innerHTML = `
            <div class="element-tag">&lt;${tagName}${id}${classes}&gt;</div>
            ${text ? `<div class="element-text"><strong>Text:</strong> ${this.escapeHtml(text)}</div>` : ''}
        `;
    }

    displayLocators(locators) {
        const locatorsList = this.locatorPanel.querySelector('#locatorsList');
        
        if (locators.length === 0) {
            locatorsList.innerHTML = '<div class="no-locators">No locators found</div>';
            return;
        }

        const locatorsHTML = locators.map((locator, index) => `
            <div class="locator-item">
                <div class="locator-header">
                    <span class="locator-type">${locator.type}</span>
                    <span class="locator-priority">Priority: ${locator.priority}</span>
                </div>
                <div class="locator-value">${this.escapeHtml(locator.value)}</div>
                <div class="locator-codes">
                    <div class="code-item">
                        <span class="code-label">Selenium:</span>
                        <code>${this.escapeHtml(locator.selenium)}</code>
                        <button class="copy-btn" data-value="${this.escapeHtml(locator.selenium)}">Copy</button>
                    </div>
                    <div class="code-item">
                        <span class="code-label">Playwright:</span>
                        <code>${this.escapeHtml(locator.playwright)}</code>
                        <button class="copy-btn" data-value="${this.escapeHtml(locator.playwright)}">Copy</button>
                    </div>
                </div>
            </div>
        `).join('');

        locatorsList.innerHTML = locatorsHTML;

        // Add copy button listeners
        locatorsList.querySelectorAll('.copy-btn').forEach(btn => {
            btn.addEventListener('click', (e) => {
                e.stopPropagation();
                this.copyToClipboard(btn.dataset.value);
            });
        });
    }

    getAllLocators(element) {
        const locators = [];

        // Check if element is inside a frame/iframe
        const frameInfo = this.getFrameInfo(element);
        console.log('Frame info:', frameInfo);


        // ID Locator (highest priority)
        if (element.id) {
            locators.push({
                type: 'ID',
                value: `#${element.id}`,
                priority: 1,
                selenium: frameInfo ? `By.cssSelector("iframe").switchTo().findElement(By.id("${element.id}"))` : `By.id("${element.id}")`,
                playwright: frameInfo ? `page.frameLocator("${frameInfo.selector}").locator("#${element.id}")` : `page.locator('#${element.id}')`
            });

            // Playwright getByTestId
            locators.push({
                type: 'getByTestId',
                value: element.id,
                priority: 1,
                selenium: frameInfo ? `By.cssSelector("iframe").switchTo().findElement(By.cssSelector("[data-testid=\\"${element.id}\\"]"))` : `By.cssSelector("[data-testid=\\"${element.id}\\"]")`,
                playwright: frameInfo ? `page.frameLocator("${frameInfo.selector}").getByTestId("${element.id}")` : `page.getByTestId("${element.id}")`
            });
        }

        // Name attribute
        if (element.name) {
            locators.push({
                type: 'Name',
                value: `[name="${element.name}"]`,
                priority: 2,
                selenium: frameInfo ? `By.cssSelector("iframe").switchTo().findElement(By.name("${element.name}"))` : `By.name("${element.name}")`,
                playwright: frameInfo ? `page.frameLocator("${frameInfo.selector}").locator('[name="${element.name}"]')` : `page.locator('[name="${element.name}"]')`
            });
        }

        // Class name (single class)
        if (element.className) {
            const classes = String(element.className).split(' ').filter(c => c.trim());
            if (classes.length > 0) {
                locators.push({
                    type: 'Class',
                    value: `.${classes[0]}`,
                    priority: 3,
                    selenium: frameInfo ? `By.cssSelector("iframe").switchTo().findElement(By.className("${classes[0]}"))` : `By.className("${classes[0]}")`,
                    playwright: frameInfo ? `page.frameLocator("${frameInfo.selector}").locator('.${classes[0]}')` : `page.locator('.${classes[0]}')`
                });
            }
        }

        // Tag Name
        locators.push({
            type: 'Tag Name',
            value: element.tagName.toLowerCase(),
            priority: 4,
            selenium: frameInfo ? `By.cssSelector("iframe").switchTo().findElement(By.tagName("${element.tagName.toLowerCase()}"))` : `By.tagName("${element.tagName.toLowerCase()}")`,
            playwright: frameInfo ? `page.frameLocator("${frameInfo.selector}").locator('${element.tagName.toLowerCase()}')` : `page.locator('${element.tagName.toLowerCase()}')`
        });

        // Link Text (for anchor tags)
        if (element.tagName.toLowerCase() === 'a' && element.textContent) {
            const linkText = element.textContent.trim();
            if (linkText) {
                locators.push({
                    type: 'Link Text',
                    value: linkText,
                    priority: 5,
                    selenium: `By.linkText("${linkText}")`,
                    playwright: `page.locator('text=${linkText}')`
                });
            }
        }

        // Partial Link Text
        if (element.tagName.toLowerCase() === 'a' && element.textContent) {
            const linkText = element.textContent.trim();
            if (linkText.length > 3) {
                const partialText = linkText.substring(0, Math.min(10, linkText.length));
                locators.push({
                    type: 'Partial Link Text',
                    value: partialText,
                    priority: 6,
                    selenium: `By.partialLinkText("${partialText}")`,
                    playwright: `page.locator('text=${partialText}')`
                });
            }
        }

        // Playwright getByRole
        const ariaRole = element.getAttribute('role');
        if (ariaRole) {
            const name = element.getAttribute('aria-label') || (element.textContent ? element.textContent.trim().substring(0, 50) : '');
            if (name) {
                locators.push({
                    type: 'getByRole',
                    value: `${ariaRole} with name "${name}"`,
                    priority: 1,
                    selenium: frameInfo ? `By.cssSelector("iframe").switchTo().findElement(By.xpath("//*[@role=\\"${ariaRole}\\"]"))` : `By.xpath("//*[@role=\\"${ariaRole}\\"]")`,
                    playwright: frameInfo ? `page.frameLocator("${frameInfo.selector}").getByRole("${ariaRole}", { name: "${name}" })` : `page.getByRole("${ariaRole}", { name: "${name}" })`
                });
            } else {
                locators.push({
                    type: 'getByRole',
                    value: ariaRole,
                    priority: 1,
                    selenium: frameInfo ? `By.cssSelector("iframe").switchTo().findElement(By.xpath("//*[@role=\\"${ariaRole}\\"]"))` : `By.xpath("//*[@role=\\"${ariaRole}\\"]")`,
                    playwright: frameInfo ? `page.frameLocator("${frameInfo.selector}").getByRole("${ariaRole}")` : `page.getByRole("${ariaRole}")`
                });
            }
        }

        // Playwright getByText
        if (element.textContent) {
            const text = element.textContent.trim();
            if (text && text.length < 50) {
                locators.push({
                    type: 'getByText',
                    value: text,
                    priority: 2,
                    selenium: frameInfo ? `By.cssSelector("iframe").switchTo().findElement(By.xpath("//*[text()=\\"${text}\\"]"))` : `By.xpath("//*[text()=\\"${text}\\"]")`,
                    playwright: frameInfo ? `page.frameLocator("${frameInfo.selector}").getByText("${text}")` : `page.getByText("${text}")`
                });
            }
        }

        // Playwright getByLabel
        if (element.id) {
            const label = document.querySelector(`label[for="${element.id}"]`);
            if (label) {
                locators.push({
                    type: 'getByLabel',
                    value: label.textContent.trim(),
                    priority: 2,
                    selenium: frameInfo ? `By.cssSelector("iframe").switchTo().findElement(By.xpath("//label[contains(text(),\\"${label.textContent.trim()}\\")]"))` : `By.xpath("//label[contains(text(),\\"${label.textContent.trim()}\\")]")`,
                    playwright: frameInfo ? `page.frameLocator("${frameInfo.selector}").getByLabel("${label.textContent.trim()}")` : `page.getByLabel("${label.textContent.trim()}")`
                });
            }
        }

        // Playwright getByPlaceholder
        const placeholder = element.getAttribute('placeholder');
        if (placeholder) {
            locators.push({
                type: 'getByPlaceholder',
                value: placeholder,
                priority: 2,
                selenium: frameInfo ? `By.cssSelector("iframe").switchTo().findElement(By.cssSelector("[placeholder=\\"${placeholder}\\"]"))` : `By.cssSelector("[placeholder=\\"${placeholder}\\"]")`,
                playwright: frameInfo ? `page.frameLocator("${frameInfo.selector}").getByPlaceholder("${placeholder}")` : `page.getByPlaceholder("${placeholder}")`
            });
        }

        // Playwright getByAltText
        const altText = element.getAttribute('alt');
        if (altText) {
            locators.push({
                type: 'getByAltText',
                value: altText,
                priority: 2,
                selenium: frameInfo ? `By.cssSelector("iframe").switchTo().findElement(By.cssSelector("[alt=\\"${altText}\\"]"))` : `By.cssSelector("[alt=\\"${altText}\\"]")`,
                playwright: frameInfo ? `page.frameLocator("${frameInfo.selector}").getByAltText("${altText}")` : `page.getByAltText("${altText}")`
            });
        }

        // Playwright getByTitle
        const titleAttr = element.getAttribute('title');
        if (titleAttr) {
            locators.push({
                type: 'getByTitle',
                value: titleAttr,
                priority: 3,
                selenium: frameInfo ? `By.cssSelector("iframe").switchTo().findElement(By.cssSelector("[title=\\"${titleAttr}\\"]"))` : `By.cssSelector("[title=\\"${titleAttr}\\"]")`,
                playwright: frameInfo ? `page.frameLocator("${frameInfo.selector}").getByTitle("${titleAttr}")` : `page.getByTitle("${titleAttr}")`
            });
        }

        // Playwright :has-text() pseudo-class
        if (element.textContent) {
            const text = element.textContent.trim();
            if (text && text.length < 30) {
                locators.push({
                    type: ':has-text()',
                    value: text,
                    priority: 4,
                    selenium: frameInfo ? `By.cssSelector("iframe").switchTo().findElement(By.xpath("//*[contains(text(),\\"${text}\\")]"))` : `By.xpath("//*[contains(text(),\\"${text}\\")]")`,
                    playwright: frameInfo ? `page.frameLocator("${frameInfo.selector}").locator(":has-text(\\"${text}\\")")` : `page.locator(":has-text(\\"${text}\\")")`
                });
            }
        }

        // Playwright :visible pseudo-class
        locators.push({
            type: ':visible',
            value: `${element.tagName.toLowerCase()}:visible`,
            priority: 5,
            selenium: frameInfo ? `By.cssSelector("iframe").switchTo().findElement(By.cssSelector("${element.tagName.toLowerCase()}"))` : `By.cssSelector("${element.tagName.toLowerCase()}")`,
            playwright: frameInfo ? `page.frameLocator("${frameInfo.selector}").locator("${element.tagName.toLowerCase()}:visible")` : `page.locator("${element.tagName.toLowerCase()}:visible")`
        });

        // Playwright :text() pseudo-class
        if (element.textContent) {
            const text = element.textContent.trim();
            if (text && text.length < 30) {
                locators.push({
                    type: ':text()',
                    value: text,
                    priority: 4,
                    selenium: frameInfo ? `By.cssSelector("iframe").switchTo().findElement(By.xpath("//*[text()=\\"${text}\\"]"))` : `By.xpath("//*[text()=\\"${text}\\"]")`,
                    playwright: frameInfo ? `page.frameLocator("${frameInfo.selector}").locator("${element.tagName.toLowerCase()}:text(\\"${text}\\")")` : `page.locator("${element.tagName.toLowerCase()}:text(\\"${text}\\")")`
                });
            }
        }

        // Playwright :text-is() pseudo-class
        if (element.textContent) {
            const text = element.textContent.trim();
            if (text && text.length < 30) {
                locators.push({
                    type: ':text-is()',
                    value: text,
                    priority: 4,
                    selenium: frameInfo ? `By.cssSelector("iframe").switchTo().findElement(By.xpath("//*[text()=\\"${text}\\"]"))` : `By.xpath("//*[text()=\\"${text}\\"]")`,
                    playwright: frameInfo ? `page.frameLocator("${frameInfo.selector}").locator("${element.tagName.toLowerCase()}:text-is(\\"${text}\\")")` : `page.locator("${element.tagName.toLowerCase()}:text-is(\\"${text}\\")")`
                });
            }
        }

        // Playwright :text-matches() pseudo-class (regex)
        if (element.textContent) {
            const text = element.textContent.trim();
            if (text && text.length < 30) {
                // Escape special regex characters
                const escapedText = text.replace(/[.*+?^${}()|[\]\\]/g, '\\$&');
                locators.push({
                    type: ':text-matches()',
                    value: escapedText,
                    priority: 5,
                    selenium: frameInfo ? `By.cssSelector("iframe").switchTo().findElement(By.xpath("//*[text()=\\"${text}\\"]"))` : `By.xpath("//*[text()=\\"${text}\\"]")`,
                    playwright: frameInfo ? `page.frameLocator("${frameInfo.selector}").locator("${element.tagName.toLowerCase()}:text-matches(\\"${escapedText}\\")")` : `page.locator("${element.tagName.toLowerCase()}:text-matches(\\"${escapedText}\\")")`
                });
            }
        }

        // Playwright :has() pseudo-class (elements containing other elements)
        if (element.children && element.children.length > 0) {
            const firstChild = element.children[0];
            const childSelector = this.generateCSSSelector(firstChild);
            locators.push({
                type: ':has()',
                value: `:has(${childSelector})`,
                priority: 4,
                selenium: frameInfo ? `By.cssSelector("iframe").switchTo().findElement(By.cssSelector("${element.tagName.toLowerCase()} ${childSelector}"))` : `By.cssSelector("${element.tagName.toLowerCase()} ${childSelector}")`,
                playwright: frameInfo ? `page.frameLocator("${frameInfo.selector}").locator("${element.tagName.toLowerCase()}:has(${childSelector})")` : `page.locator("${element.tagName.toLowerCase()}:has(${childSelector})")`
            });
        }

        // Playwright shorthand selectors (id=, data-testid=, data-test-id=, data-test=)
        if (element.id) {
            locators.push({
                type: 'id=',
                value: `id=${element.id}`,
                priority: 1,
                selenium: frameInfo ? `By.cssSelector("iframe").switchTo().findElement(By.id("${element.id}"))` : `By.id("${element.id}")`,
                playwright: frameInfo ? `page.frameLocator("${frameInfo.selector}").locator("id=${element.id}")` : `page.locator("id=${element.id}")`
            });
        }

        const testId = element.getAttribute('data-testid');
        if (testId) {
            locators.push({
                type: 'data-testid=',
                value: `data-testid=${testId}`,
                priority: 1,
                selenium: frameInfo ? `By.cssSelector("iframe").switchTo().findElement(By.cssSelector("[data-testid=\\"${testId}\\"]"))` : `By.cssSelector("[data-testid=\\"${testId}\\"]")`,
                playwright: frameInfo ? `page.frameLocator("${frameInfo.selector}").locator("data-testid=${testId}")` : `page.locator("data-testid=${testId}")`
            });
        }

        const testId2 = element.getAttribute('data-test-id');
        if (testId2) {
            locators.push({
                type: 'data-test-id=',
                value: `data-test-id=${testId2}`,
                priority: 1,
                selenium: frameInfo ? `By.cssSelector("iframe").switchTo().findElement(By.cssSelector("[data-test-id=\\"${testId2}\\"]"))` : `By.cssSelector("[data-test-id=\\"${testId2}\\"]")`,
                playwright: frameInfo ? `page.frameLocator("${frameInfo.selector}").locator("data-test-id=${testId2}")` : `page.locator("data-test-id=${testId2}")`
            });
        }

        const test = element.getAttribute('data-test');
        if (test) {
            locators.push({
                type: 'data-test=',
                value: `data-test=${test}`,
                priority: 1,
                selenium: frameInfo ? `By.cssSelector("iframe").switchTo().findElement(By.cssSelector("[data-test=\\"${test}\\"]"))` : `By.cssSelector("[data-test=\\"${test}\\"]")`,
                playwright: frameInfo ? `page.frameLocator("${frameInfo.selector}").locator("data-test=${test}")` : `page.locator("data-test=${test}")`
            });
        }

        // Radio button and checkbox label-based locators
        if (element.tagName.toLowerCase() === 'input' && (element.type === 'radio' || element.type === 'checkbox')) {
            const label = element.closest('label') || document.querySelector(`label[for="${element.id}"]`);
            if (label) {
                const labelText = label.textContent.trim();
                if (labelText && labelText.length < 50) {
                    // Find by label text
                    locators.push({
                        type: element.type === 'radio' ? 'Radio (Label)' : 'Checkbox (Label)',
                        value: `input[type="${element.type}"] with label "${labelText}"`,
                        priority: 1,
                        selenium: frameInfo ? `By.cssSelector("iframe").switchTo().findElement(By.xpath("//label[contains(text(),\\"${labelText}\\")]/input[@type=\\"${element.type}\\"]"))` : `By.xpath("//label[contains(text(),\\"${labelText}\\")]/input[@type=\\"${element.type}\\"]")`,
                        playwright: frameInfo ? `page.frameLocator("${frameInfo.selector}").getByLabel("${labelText}")` : `page.getByLabel("${labelText}")`
                    });
                }
            }
            
            // Use value attribute if present
            const value = element.getAttribute('value');
            if (value && value.length < 30) {
                locators.push({
                    type: element.type === 'radio' ? 'Radio (Value)' : 'Checkbox (Value)',
                    value: `input[type="${element.type}"][value="${value}"]`,
                    priority: 1,
                    selenium: frameInfo ? `By.cssSelector("iframe").switchTo().findElement(By.cssSelector("input[type=\\"${element.type}\\"][value=\\"${value}\\"]"))` : `By.cssSelector("input[type=\\"${element.type}\\"][value=\\"${value}\\"]")`,
                    playwright: frameInfo ? `page.frameLocator("${frameInfo.selector}").locator("input[type=\\"${element.type}\\"][value=\\"${value}\\"]")` : `page.locator("input[type=\\"${element.type}\\"][value=\\"${value}\\"]")`
                });
            }
            
            // Use name attribute + value combination
            const name = element.getAttribute('name');
            if (name && value && value.length < 30) {
                locators.push({
                    type: element.type === 'radio' ? 'Radio (Name+Value)' : 'Checkbox (Name+Value)',
                    value: `input[name="${name}"][value="${value}"]`,
                    priority: 1,
                    selenium: frameInfo ? `By.cssSelector("iframe").switchTo().findElement(By.cssSelector("input[name=\\"${name}\\"][value=\\"${value}\\"]"))` : `By.cssSelector("input[name=\\"${name}\\"][value=\\"${value}\\"]")`,
                    playwright: frameInfo ? `page.frameLocator("${frameInfo.selector}").locator("input[name=\\"${name}\\"][value=\\"${value}\\"]")` : `page.locator("input[name=\\"${name}\\"][value=\\"${value}\\"]")`
                });
            }
        }

        // Playwright nth= locator
        const parent = element.parentElement;
        if (parent) {
            const siblings = Array.from(parent.children);
            const index = siblings.indexOf(element);
            locators.push({
                type: 'nth=',
                value: `${element.tagName.toLowerCase()} nth=${index}`,
                priority: 6,
                selenium: frameInfo ? `By.cssSelector("iframe").switchTo().findElement(By.cssSelector("${element.tagName.toLowerCase()}"))` : `By.cssSelector("${element.tagName.toLowerCase()}")`,
                playwright: frameInfo ? `page.frameLocator("${frameInfo.selector}").locator("${element.tagName.toLowerCase()}").locator("nth=${index}")` : `page.locator("${element.tagName.toLowerCase()}").locator("nth=${index}")`
            });
        }

        // Web table support
        if (element.tagName.toLowerCase() === 'tr') {
            const table = element.closest('table');
            if (table) {
                const rows = Array.from(table.querySelectorAll('tr'));
                const rowIndex = rows.indexOf(element);
                
                // Find unique identifier for this row
                const cells = element.querySelectorAll('td, th');
                if (cells.length > 0) {
                    const firstCellText = cells[0].textContent.trim();
                    if (firstCellText) {
                        locators.push({
                            type: 'Table Row',
                            value: `tr with text "${firstCellText}"`,
                            priority: 2,
                            selenium: `By.xpath("//tr[td[contains(text(),\\"${firstCellText}\\")]]")`,
                            playwright: `page.locator("tr").filter({ hasText: "${firstCellText}" })`
                        });
                    }
                }
                
                locators.push({
                    type: 'Table Row Index',
                    value: `tr:nth-child(${rowIndex + 1})`,
                    priority: 3,
                    selenium: `By.cssSelector("tr:nth-child(${rowIndex + 1})")`,
                    playwright: `page.locator("tr").locator("nth=${rowIndex}")`
                });
            }
        }

        if (element.tagName.toLowerCase() === 'td' || element.tagName.toLowerCase() === 'th') {
            const cellText = element.textContent.trim();
            const parentRow = element.closest('tr');
            const table = element.closest('table');
            
            if (cellText) {
                locators.push({
                    type: 'Table Cell',
                    value: `td with text "${cellText}"`,
                    priority: 2,
                    selenium: `By.xpath("//td[contains(text(),\\"${cellText}\\")]")`,
                    playwright: `page.locator("td").filter({ hasText: "${cellText}" })`
                });
            }
            
            // Enhanced locators with parent row chaining
            if (parentRow && table) {
                const rows = Array.from(table.querySelectorAll('tr'));
                const rowIndex = rows.indexOf(parentRow);
                const cells = Array.from(parentRow.querySelectorAll('td, th'));
                const cellIndex = cells.indexOf(element);
                
                // Chain with parent row by index
                locators.push({
                    type: 'Table Cell (Chained Row Index)',
                    value: `tr:nth-child(${rowIndex + 1}) >> td:nth-child(${cellIndex + 1})`,
                    priority: 1,
                    selenium: `By.xpath("//tr[${rowIndex + 1}]/td[${cellIndex + 1}]")`,
                    playwright: `page.locator("tr").locator("nth=${rowIndex}").locator("td").locator("nth=${cellIndex}")`
                });
                
                // Chain with parent row using :has-text() if row has unique text
                const firstCellText = cells[0]?.textContent.trim();
                if (firstCellText && firstCellText.length < 30) {
                    locators.push({
                        type: 'Table Cell (Chained Row Text)',
                        value: `tr:has-text("${firstCellText}") >> td:nth-child(${cellIndex + 1})`,
                        priority: 1,
                        selenium: `By.xpath("//tr[td[contains(text(),\\"${firstCellText}\\")]]/td[${cellIndex + 1}]")`,
                        playwright: `page.locator("tr").filter({ hasText: "${firstCellText}" }).locator("td").locator("nth=${cellIndex}")`
                    });
                }
                
                // Use column header if table has headers
                const headers = table.querySelectorAll('thead th, thead td');
                if (headers.length > 0 && cellIndex < headers.length) {
                    const headerText = headers[cellIndex].textContent.trim();
                    if (headerText && headerText.length < 30) {
                        locators.push({
                            type: 'Table Cell (Column Header)',
                            value: `tr:has-text("${firstCellText || 'row'}") >> td:has-text("${headerText}")`,
                            priority: 1,
                            selenium: `By.xpath("//tr[td[contains(text(),\\"${firstCellText || 'row'}\\")]]/td[contains(text(),\\"${headerText}\\")]")`,
                            playwright: `page.locator("tr").filter({ hasText: "${firstCellText || 'row'}" }).locator("td").filter({ hasText: "${headerText}" })`
                        });
                    }
                }
                
                // Parent-child relationship using parent row text and cell text
                if (firstCellText && cellText && cellText.length < 30) {
                    locators.push({
                        type: 'Table Cell (Parent-Child)',
                        value: `tr:has-text("${firstCellText}") >> td:has-text("${cellText}")`,
                        priority: 1,
                        selenium: `By.xpath("//tr[td[contains(text(),\\"${firstCellText}\\")]]/td[contains(text(),\\"${cellText}\\")]")`,
                        playwright: `page.locator("tr").filter({ hasText: "${firstCellText}" }).locator("td").filter({ hasText: "${cellText}" })`
                    });
                }
            }
        }

        // CSS Selector with attributes
        const cssSelector = this.generateCSSSelector(element);
        if (cssSelector) {
            locators.push({
                type: 'CSS Selector',
                value: cssSelector,
                priority: 7,
                selenium: frameInfo ? `By.cssSelector("iframe").switchTo().findElement(By.cssSelector("${cssSelector}"))` : `By.cssSelector("${cssSelector}")`,
                playwright: frameInfo ? `page.frameLocator("${frameInfo.selector}").locator('${cssSelector}')` : `page.locator('${cssSelector}')`
            });
        }

        // XPath
        const xpath = this.generateXPath(element);
        if (xpath) {
            locators.push({
                type: 'XPath',
                value: xpath,
                priority: 8,
                selenium: frameInfo ? `By.cssSelector("iframe").switchTo().findElement(By.xpath("${xpath}"))` : `By.xpath("${xpath}")`,
                playwright: frameInfo ? `page.frameLocator("${frameInfo.selector}").locator('xpath=${xpath}')` : `page.locator('xpath=${xpath}')`
            });
        }

        // Frame-specific locators for iframe/frame elements
        if (element.tagName.toLowerCase() === 'iframe' || element.tagName.toLowerCase() === 'frame') {
            const frameName = element.getAttribute('name');
            if (frameName) {
                locators.push({
                    type: 'frameLocator (by name)',
                    value: `frame with name "${frameName}"`,
                    priority: 1,
                    selenium: `By.cssSelector("iframe[name=\\"${frameName}\\"]")`,
                    playwright: `page.frame("${frameName}")`
                });
            }

            const frameSrc = element.getAttribute('src');
            if (frameSrc) {
                locators.push({
                    type: 'frameLocator (by URL)',
                    value: `frame with src "${frameSrc}"`,
                    priority: 2,
                    selenium: `By.cssSelector("iframe[src=\\"${frameSrc}\\"]")`,
                    playwright: `page.frameByUrl("${frameSrc}")`
                });
            }

            const frameSelector = this.generateCSSSelector(element);
            locators.push({
                type: 'frameLocator (by selector)',
                value: frameSelector,
                priority: 3,
                selenium: `By.cssSelector("${frameSelector}")`,
                playwright: `page.frameLocator("${frameSelector}")`
            });
        }

        return locators;
    }

    generateCSSSelector(element) {
        const tagName = element.tagName.toLowerCase();
        const attributes = [];

        // Add ID if present
        if (element.id) {
            attributes.push(`#${element.id}`);
        }

        // Add classes if present
        if (element.className) {
            const classes = String(element.className).split(' ').filter(c => c.trim());
            if (classes.length > 0) {
                attributes.push(`.${classes[0]}`);
            }
        }

        // Add other attributes
        ['name', 'type', 'value', 'placeholder', 'href', 'data-testid', 'data-cy'].forEach(attr => {
            if (element.getAttribute(attr)) {
                attributes.push(`[${attr}="${element.getAttribute(attr)}"]`);
            }
        });

        // Combine tag name with attributes
        let selector = tagName;
        if (attributes.length > 0) {
            selector += attributes.join('');
        }

        return selector;
    }

    generateXPath(element) {
        const tagName = element.tagName.toLowerCase();
        
        // Try to create a more specific XPath
        if (element.id) {
            return `//*[@id="${element.id}"]`;
        }

        if (element.name) {
            return `//*[@name="${element.name}"]`;
        }

        // XPath with text content
        if (element.textContent && element.textContent.trim()) {
            const text = element.textContent.trim();
            if (text.length < 50) {
                return `//${tagName}[text()="${text}"]`;
            }
        }

        // XPath with attributes
        const attributes = [];
        ['class', 'type', 'value', 'placeholder', 'href', 'data-testid', 'data-cy'].forEach(attr => {
            if (element.getAttribute(attr)) {
                attributes.push(`@${attr}="${element.getAttribute(attr)}"`);
            }
        });

        if (attributes.length > 0) {
            return `//${tagName}[${attributes.join(' and ')}]`;
        }

        // Basic XPath
        return `//${tagName}`;
    }

    sortLocatorsByPriority(locators) {
        return locators.sort((a, b) => a.priority - b.priority);
    }

    getFrameInfo(element) {
        // Check if element is inside a frame or iframe
        let current = element;
        while (current && current !== document.body) {
            if (current.tagName === 'IFRAME' || current.tagName === 'FRAME') {
                // Found the frame, now get its selector
                const frameSelector = this.generateCSSSelector(current);
                const frameName = current.getAttribute('name');
                const frameSrc = current.getAttribute('src');
                
                return {
                    selector: frameSelector,
                    name: frameName,
                    src: frameSrc,
                    tagName: current.tagName.toLowerCase()
                };
            }
            current = current.parentElement;
        }
        
        // If we're inside an iframe, return frame info from window.parent
        if (window.self !== window.top) {
            // Try to get frame info from parent window
            const frameId = window.frameElement?.id;
            const frameName = window.frameElement?.getAttribute('name');
            const frameSrc = window.frameElement?.getAttribute('src');
            
            if (window.frameElement) {
                const frameSelector = this.generateCSSSelector(window.frameElement);
                return {
                    selector: frameSelector,
                    name: frameName,
                    src: frameSrc,
                    tagName: window.frameElement.tagName.toLowerCase()
                };
            }
        }
        
        return null;
    }

    makePanelDraggable() {
        const dragHandle = this.locatorPanel.querySelector('#dragHandle');
        if (!dragHandle) return;

        let isDragging = false;
        let startX, startY, initialX, initialY;

        dragHandle.addEventListener('mousedown', (e) => {
            isDragging = true;
            startX = e.clientX;
            startY = e.clientY;
            initialX = this.locatorPanel.offsetLeft;
            initialY = this.locatorPanel.offsetTop;
            this.locatorPanel.style.cursor = 'grabbing';
            dragHandle.style.cursor = 'grabbing';
            e.preventDefault();
        });

        document.addEventListener('mousemove', (e) => {
            if (!isDragging) return;

            const deltaX = e.clientX - startX;
            const deltaY = e.clientY - startY;

            this.locatorPanel.style.left = `${initialX + deltaX}px`;
            this.locatorPanel.style.top = `${initialY + deltaY}px`;
            this.locatorPanel.style.right = 'auto';
        });

        document.addEventListener('mouseup', () => {
            if (isDragging) {
                isDragging = false;
                this.locatorPanel.style.cursor = '';
                dragHandle.style.cursor = 'move';
            }
        });
    }

    findBestParentElement(element) {
        // Priority order for best parent elements
        const priorityTags = ['button', 'a', 'input', 'select', 'textarea', 'label', 'div', 'span'];
        
        let current = element.parentElement;
        let bestElement = null;
        let bestScore = 0;
        let distance = 0;
        const maxDistance = 5; // Limit traversal to 5 levels up
        
        while (current && current !== document.body && distance < maxDistance) {
            // Skip SVG-related elements
            if (current.tagName.toLowerCase() === 'svg' || current.tagName.toLowerCase() === 'g') {
                current = current.parentElement;
                distance++;
                continue;
            }
            
            let score = 0;
            const tagName = current.tagName.toLowerCase();
            
            // Score based on tag priority
            const tagIndex = priorityTags.indexOf(tagName);
            if (tagIndex !== -1) {
                score += (priorityTags.length - tagIndex) * 10;
            }
            
            // Score for having id (very high priority)
            if (current.id) {
                score += 100;
            }
            
            // Score for having class
            if (current.className && current.className.trim()) {
                score += 10;
            }
            
            // Score for having text content
            if (current.textContent && current.textContent.trim()) {
                score += 5;
            }
            
            // Score for interactive elements
            if (current.hasAttribute('onclick') || current.hasAttribute('href')) {
                score += 15;
            }
            
            // Score for data-* attributes
            if (current.hasAttribute('data-testid') || current.hasAttribute('data-test-id') || current.hasAttribute('data-test')) {
                score += 25;
            }
            
            // Apply distance penalty - closer elements are preferred
            const distancePenalty = distance * 20;
            score -= distancePenalty;
            
            // Update best element if this one scores higher
            if (score > bestScore) {
                bestScore = score;
                bestElement = current;
            }
            
            // If we found an element with an id at distance 0, it's the best target - stop immediately
            if (current.id && distance === 0) {
                console.log('Found parent with id at distance 0, stopping:', current.id);
                break;
            }
            
            // If we found an element with an id at distance 1, it's likely good - stop
            if (current.id && distance === 1) {
                console.log('Found parent with id at distance 1, stopping:', current.id);
                break;
            }
            
            current = current.parentElement;
            distance++;
        }
        
        console.log('Best parent element found:', bestElement?.id || bestElement?.tagName, 'with score:', bestScore);
        
        // If no good parent found, return the original element's parent
        return bestElement || element.parentElement || element;
    }

    escapeHtml(text) {
        const div = document.createElement('div');
        div.textContent = text;
        return div.innerHTML;
    }

    toggleRecordingPanel() {
        const recordingPanel = this.locatorPanel.querySelector('#recordingPanel');
        const locatorPanelContent = this.locatorPanel.querySelector('.locator-panel-content');
        
        if (recordingPanel) {
            const isHidden = recordingPanel.style.display === 'none';
            
            // Show recording panel and hide locator content
            if (isHidden) {
                recordingPanel.style.display = 'block';
                if (locatorPanelContent) locatorPanelContent.style.display = 'none';
                
                // Disable picking mode if it's active
                if (this.isEnabled) {
                    this.togglePickingMode();
                }
            } else {
                // Hide recording panel and show locator content
                recordingPanel.style.display = 'none';
                if (locatorPanelContent) locatorPanelContent.style.display = 'block';
            }
        }
    }

    startRecording() {
        this.isRecording = true;
        this.recordedActions = [];
        
        const startBtn = this.locatorPanel.querySelector('#startRecordingBtn');
        const stopBtn = this.locatorPanel.querySelector('#stopRecordingBtn');
        const status = this.locatorPanel.querySelector('#recordingStatus');
        
        if (startBtn) startBtn.style.display = 'none';
        if (stopBtn) stopBtn.style.display = 'inline-block';
        if (status) {
            status.textContent = '🔴 Recording... Click on elements or type to record actions';
            status.classList.add('recording');
        }
        
        // Add recording event listeners
        document.addEventListener('click', this.handleRecordingClick, true);
        document.addEventListener('input', this.handleRecordingInput, true);
        
        console.log('Recording started');
    }

    stopRecording() {
        this.isRecording = false;
        
        const startBtn = this.locatorPanel.querySelector('#startRecordingBtn');
        const stopBtn = this.locatorPanel.querySelector('#stopRecordingBtn');
        const status = this.locatorPanel.querySelector('#recordingStatus');
        
        if (startBtn) startBtn.style.display = 'inline-block';
        if (stopBtn) stopBtn.style.display = 'none';
        if (status) {
            status.textContent = `Recording stopped. ${this.recordedActions.length} actions recorded.`;
            status.classList.remove('recording');
        }
        
        // Remove recording event listeners
        document.removeEventListener('click', this.handleRecordingClick, true);
        document.removeEventListener('input', this.handleRecordingInput, true);
        
        // Update the code textarea
        this.updateRecordedCode();
        
        console.log('Recording stopped, actions:', this.recordedActions);
    }

    clearRecording() {
        this.recordedActions = [];
        const codeArea = this.locatorPanel.querySelector('#recordingCode');
        if (codeArea) codeArea.value = '';
        const status = this.locatorPanel.querySelector('#recordingStatus');
        if (status) status.textContent = 'Recording cleared.';
        console.log('Recording cleared');
    }

    copyRecordedCode() {
        const codeArea = this.locatorPanel.querySelector('#recordingCode');
        if (codeArea && codeArea.value) {
            navigator.clipboard.writeText(codeArea.value).then(() => {
                const status = this.locatorPanel.querySelector('#recordingStatus');
                if (status) {
                    status.textContent = 'Code copied to clipboard!';
                    setTimeout(() => {
                        status.textContent = '';
                    }, 2000);
                }
            });
        }
    }

    async playRecording() {
        if (this.recordedActions.length === 0) {
            const status = this.locatorPanel.querySelector('#recordingStatus');
            if (status) status.textContent = 'No actions to play.';
            return;
        }

        const status = this.locatorPanel.querySelector('#recordingStatus');
        if (status) {
            status.textContent = '▶ Playing recorded actions...';
            status.classList.add('recording');
        }

        const playBtn = this.locatorPanel.querySelector('#playRecordingBtn');
        if (playBtn) playBtn.disabled = true;

        try {
            for (let i = 0; i < this.recordedActions.length; i++) {
                const action = this.recordedActions[i];
                
                if (status) {
                    status.textContent = `▶ Playing action ${i + 1}/${this.recordedActions.length}...`;
                }

                await this.executeAction(action);
                
                // Small delay between actions for better visualization
                await new Promise(resolve => setTimeout(resolve, 500));
            }

            if (status) {
                status.textContent = `✅ Played ${this.recordedActions.length} actions successfully!`;
                status.classList.remove('recording');
            }
        } catch (error) {
            console.error('Error playing recording:', error);
            if (status) {
                status.textContent = `❌ Error: ${error.message}`;
                status.classList.remove('recording');
            }
        } finally {
            if (playBtn) playBtn.disabled = false;
        }
    }

    async executeAction(action) {
        // Parse the Playwright locator to find the element
        const element = this.findElementByLocator(action.locator);
        
        if (!element) {
            throw new Error(`Element not found: ${action.locator}`);
        }

        // Scroll element into view
        element.scrollIntoView({ behavior: 'smooth', block: 'center' });
        await new Promise(resolve => setTimeout(resolve, 300));

        if (action.type === 'click') {
            // Simulate a real click event
            const clickEvent = new MouseEvent('click', {
                bubbles: true,
                cancelable: true,
                view: window
            });
            element.dispatchEvent(clickEvent);
            
            // Also trigger the click directly
            if (element.click) {
                element.click();
            }
        } else if (action.type === 'rightclick') {
            // Simulate a right-click (context menu)
            const contextMenuEvent = new MouseEvent('contextmenu', {
                bubbles: true,
                cancelable: true,
                view: window,
                button: 2
            });
            element.dispatchEvent(contextMenuEvent);
        } else if (action.type === 'dblclick') {
            // Simulate a double click
            const dblClickEvent = new MouseEvent('dblclick', {
                bubbles: true,
                cancelable: true,
                view: window
            });
            element.dispatchEvent(dblClickEvent);
            
            // Also trigger two single clicks for compatibility
            if (element.click) {
                element.click();
                await new Promise(resolve => setTimeout(resolve, 50));
                element.click();
            }
        } else if (action.type === 'fill') {
            // Set the value
            element.value = action.value;
            
            // Trigger input events
            const inputEvent = new Event('input', { bubbles: true });
            element.dispatchEvent(inputEvent);
            
            const changeEvent = new Event('change', { bubbles: true });
            element.dispatchEvent(changeEvent);
        } else if (action.type === 'check') {
            // Handle checkbox and radio button
            // For radio buttons, we need to click to select
            // For checkboxes, we need to click to toggle
            
            const currentChecked = element.checked;
            const targetChecked = action.checked;
            
            // Only click if the state needs to change
            if (currentChecked !== targetChecked) {
                // Click the element to toggle state
                element.click();
                
                // Ensure the checked state is set correctly
                element.checked = targetChecked;
                
                // Trigger change event
                const changeEvent = new Event('change', { bubbles: true });
                element.dispatchEvent(changeEvent);
            }
        } else if (action.type === 'select') {
            // Handle dropdown/select
            element.value = action.value;
            
            // Trigger change event
            const changeEvent = new Event('change', { bubbles: true });
            element.dispatchEvent(changeEvent);
        }
    }

    findElementByLocator(locator) {
        // Parse common Playwright locator patterns and find the element
        // This is a simplified implementation - you may need to expand this
        
        try {
            // Handle page.locator('selector') pattern
            if (locator.includes('page.locator(')) {
                const match = locator.match(/page\.locator\(['"]([^'"]+)['"]\)/);
                if (match) {
                    return this.querySelectorByString(match[1]);
                }
            }
            
            // Handle getByRole
            if (locator.includes('getByRole(')) {
                const match = locator.match(/getByRole\(['"]([^'"]+)['"]\)/);
                if (match) {
                    const role = match[1];
                    return document.querySelector(`[role="${role}"]`);
                }
            }
            
            // Handle getByText
            if (locator.includes('getByText(')) {
                const match = locator.match(/getByText\(['"]([^'"]+)['"]\)/);
                if (match) {
                    const text = match[1];
                    return this.findElementByText(text);
                }
            }
            
            // Handle getByLabel
            if (locator.includes('getByLabel(')) {
                const match = locator.match(/getByLabel\(['"]([^'"]+)['"]\)/);
                if (match) {
                    const label = match[1];
                    return document.querySelector(`label[for]`) || 
                           Array.from(document.querySelectorAll('label')).find(l => l.textContent.includes(label));
                }
            }
            
            // Handle getByPlaceholder
            if (locator.includes('getByPlaceholder(')) {
                const match = locator.match(/getByPlaceholder\(['"]([^'"]+)['"]\)/);
                if (match) {
                    const placeholder = match[1];
                    return document.querySelector(`[placeholder*="${placeholder}"]`);
                }
            }
            
            // Handle getByTestId
            if (locator.includes('getByTestId(')) {
                const match = locator.match(/getByTestId\(['"]([^'"]+)['"]\)/);
                if (match) {
                    const testId = match[1];
                    return document.querySelector(`[data-testid="${testId}"]`);
                }
            }
            
            // Handle CSS selectors directly
            if (locator.startsWith('#') || locator.startsWith('.') || locator.startsWith('[')) {
                return document.querySelector(locator);
            }
            
            // Handle id= shorthand
            if (locator.startsWith('id=')) {
                const id = locator.replace('id=', '');
                return document.getElementById(id);
            }
            
            // Handle data-testid= shorthand
            if (locator.startsWith('data-testid=')) {
                const testId = locator.replace('data-testid=', '');
                return document.querySelector(`[data-testid="${testId}"]`);
            }
            
            // Default: try as CSS selector
            return document.querySelector(locator);
        } catch (error) {
            console.error('Error finding element by locator:', locator, error);
            return null;
        }
    }

    querySelectorByString(selector) {
        try {
            return document.querySelector(selector);
        } catch (error) {
            console.error('Invalid selector:', selector);
            return null;
        }
    }

    findElementByText(text) {
        // Find element by exact text match
        const allElements = document.querySelectorAll('*');
        for (const element of allElements) {
            if (element.textContent && element.textContent.trim() === text) {
                // Prefer interactive elements
                if (['button', 'a', 'input', 'select', 'textarea'].includes(element.tagName.toLowerCase())) {
                    return element;
                }
            }
        }
        
        // If no exact match, try partial match
        for (const element of allElements) {
            if (element.textContent && element.textContent.includes(text)) {
                if (['button', 'a', 'input', 'select', 'textarea'].includes(element.tagName.toLowerCase())) {
                    return element;
                }
            }
        }
        
        return null;
    }

    handleRecordingClick = (event) => {
        if (!this.isRecording) return;
        
        // Ignore clicks on the locator panel
        if (event.target.closest('#locator-generator-panel')) return;
        
        const element = event.target;
        
        // Check if it's a right-click
        const isRightClick = event.button === 2;
        
        // Ignore SVG/path elements, find parent
        let targetElement = element;
        if (element.tagName.toLowerCase() === 'svg' || element.tagName.toLowerCase() === 'path') {
            targetElement = this.findBestParentElement(element);
        }
        
        // Get the best locator for this element
        const locators = this.getAllLocators(targetElement);
        const playwrightLocators = locators.filter(l => l.playwright);
        
        if (playwrightLocators.length > 0) {
            // Sort by priority and get the best one
            playwrightLocators.sort((a, b) => a.priority - b.priority);
            const bestLocator = playwrightLocators[0];
            
            // Check if it's a checkbox or radio button
            const isCheckbox = targetElement.tagName.toLowerCase() === 'input' && targetElement.type === 'checkbox';
            const isRadio = targetElement.tagName.toLowerCase() === 'input' && targetElement.type === 'radio';
            
            const currentTime = Date.now();
            
            if (isRightClick) {
                // Record right-click
                this.recordedActions.push({
                    type: 'rightclick',
                    locator: bestLocator.playwright,
                    elementTag: targetElement.tagName.toLowerCase(),
                    timestamp: currentTime
                });
                console.log('Recorded right-click:', bestLocator.playwright);
            } else {
                // Detect double click for left clicks
                const timeDiff = currentTime - this.lastClickTime;
                const isDoubleClick = timeDiff < 500 && this.lastClickElement === targetElement;
                
                if (isDoubleClick) {
                    // Remove the last single click and replace with double click
                    this.recordedActions.pop();
                    this.recordedActions.push({
                        type: 'dblclick',
                        locator: bestLocator.playwright,
                        elementTag: targetElement.tagName.toLowerCase(),
                        timestamp: currentTime
                    });
                    console.log('Recorded double click:', bestLocator.playwright);
                    this.lastClickTime = 0;
                    this.lastClickElement = null;
                } else if (isCheckbox || isRadio) {
                    this.recordedActions.push({
                        type: 'check',
                        locator: bestLocator.playwright,
                        elementTag: targetElement.tagName.toLowerCase(),
                        inputType: targetElement.type,
                        checked: targetElement.checked,
                        timestamp: currentTime
                    });
                    console.log('Recorded check:', bestLocator.playwright, 'checked:', targetElement.checked);
                    this.lastClickTime = currentTime;
                    this.lastClickElement = targetElement;
                } else {
                    this.recordedActions.push({
                        type: 'click',
                        locator: bestLocator.playwright,
                        elementTag: targetElement.tagName.toLowerCase(),
                        timestamp: currentTime
                    });
                    console.log('Recorded click:', bestLocator.playwright);
                    this.lastClickTime = currentTime;
                    this.lastClickElement = targetElement;
                }
            }
        }
    }

    handleRecordingInput = (event) => {
        if (!this.isRecording) return;
        
        // Ignore inputs on the locator panel
        if (event.target.closest('#locator-generator-panel')) return;
        
        const element = event.target;
        const value = element.value;
        
        // Check if it's a select/dropdown
        const isSelect = element.tagName.toLowerCase() === 'select';
        
        // Get the best locator for this element
        const locators = this.getAllLocators(element);
        const playwrightLocators = locators.filter(l => l.playwright);
        
        if (playwrightLocators.length > 0) {
            playwrightLocators.sort((a, b) => a.priority - b.priority);
            const bestLocator = playwrightLocators[0];
            
            if (isSelect) {
                this.recordedActions.push({
                    type: 'select',
                    locator: bestLocator.playwright,
                    value: value,
                    elementTag: element.tagName.toLowerCase(),
                    timestamp: Date.now()
                });
                console.log('Recorded select:', bestLocator.playwright, 'with value:', value);
            } else {
                this.recordedActions.push({
                    type: 'fill',
                    locator: bestLocator.playwright,
                    value: value,
                    elementTag: element.tagName.toLowerCase(),
                    timestamp: Date.now()
                });
                console.log('Recorded fill:', bestLocator.playwright, 'with value:', value);
            }
        }
    }

    updateRecordedCode() {
        const codeArea = this.locatorPanel.querySelector('#recordingCode');
        if (!codeArea) return;
        
        const code = this.generatePlaywrightCode(this.recordedActions, this.recordingLanguage);
        codeArea.value = code;
    }

    generatePlaywrightCode(actions, language) {
        if (actions.length === 0) {
            return '// No actions recorded yet. Start recording to generate code.';
        }
        
        let code = '';
        
        if (language === 'javascript') {
            code = `const { test, expect } = require('@playwright/test');

test('recorded test', async ({ page }) => {
    await page.goto('${window.location.href}');
`;
            actions.forEach(action => {
                if (action.type === 'click') {
                    code += `    await page.locator('${action.locator}').click();\n`;
                } else if (action.type === 'rightclick') {
                    code += `    await page.locator('${action.locator}').click({ button: 'right' });\n`;
                } else if (action.type === 'dblclick') {
                    code += `    await page.locator('${action.locator}').dblclick();\n`;
                } else if (action.type === 'fill') {
                    code += `    await page.locator('${action.locator}').fill('${action.value}');\n`;
                } else if (action.type === 'check') {
                    if (action.checked) {
                        code += `    await page.locator('${action.locator}').check();\n`;
                    } else {
                        code += `    await page.locator('${action.locator}').uncheck();\n`;
                    }
                } else if (action.type === 'select') {
                    code += `    await page.locator('${action.locator}').selectOption('${action.value}');\n`;
                }
            });
            code += `});`;
        } else if (language === 'java') {
            code = `import com.microsoft.playwright.*;

public class RecordedTest {
    public static void main(String[] args) {
        try (Playwright playwright = Playwright.create()) {
            Browser browser = playwright.chromium().launch();
            Page page = browser.newPage();
            page.navigate("${window.location.href}");
`;
            actions.forEach(action => {
                if (action.type === 'click') {
                    code += `            page.locator("${action.locator}").click();\n`;
                } else if (action.type === 'rightclick') {
                    code += `            page.locator("${action.locator}").click(new Locator.ClickOptions().setButton(MouseButton.RIGHT));\n`;
                } else if (action.type === 'dblclick') {
                    code += `            page.locator("${action.locator}").dblclick();\n`;
                } else if (action.type === 'fill') {
                    code += `            page.locator("${action.locator}").fill("${action.value}");\n`;
                } else if (action.type === 'check') {
                    if (action.checked) {
                        code += `            page.locator("${action.locator}").check();\n`;
                    } else {
                        code += `            page.locator("${action.locator}").uncheck();\n`;
                    }
                } else if (action.type === 'select') {
                    code += `            page.locator("${action.locator}").selectOption("${action.value}");\n`;
                }
            });
            code += `            browser.close();
        }
    }
}`;
        } else if (language === 'python') {
            code = `from playwright.sync_api import sync_playwright

def run(playwright):
    browser = playwright.chromium.launch()
    page = browser.new_page()
    page.goto("${window.location.href}")
`;
            actions.forEach(action => {
                if (action.type === 'click') {
                    code += `    page.locator("${action.locator}").click()\n`;
                } else if (action.type === 'rightclick') {
                    code += `    page.locator("${action.locator}").click(button="right")\n`;
                } else if (action.type === 'dblclick') {
                    code += `    page.locator("${action.locator}").dblclick()\n`;
                } else if (action.type === 'fill') {
                    code += `    page.locator("${action.locator}").fill("${action.value}")\n`;
                } else if (action.type === 'check') {
                    if (action.checked) {
                        code += `    page.locator("${action.locator}").check()\n`;
                    } else {
                        code += `    page.locator("${action.locator}").uncheck()\n`;
                    }
                } else if (action.type === 'select') {
                    code += `    page.locator("${action.locator}").select_option("${action.value}")\n`;
                }
            });
            code += `    browser.close()

with sync_playwright() as p:
    run(p)`;
        }
        
        return code;
    }

    copyToClipboard(text) {
        navigator.clipboard.writeText(text).then(() => {
            // Show feedback
            const notification = document.createElement('div');
            notification.className = 'copy-notification';
            notification.textContent = 'Copied to clipboard!';
            document.body.appendChild(notification);
            
            setTimeout(() => notification.remove(), 2000);
        });
    }
}

// Initialize the locator generator
const locatorGenerator = new LocatorGenerator();
window.locatorGenerator = locatorGenerator;
console.log('LocatorGenerator initialized and attached to window');
