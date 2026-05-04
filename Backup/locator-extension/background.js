// Background service worker for Element Locator Generator extension

chrome.runtime.onInstalled.addListener(() => {
    console.log('Element Locator Generator extension installed');
    
    // Set default enabled state
    chrome.storage.local.set({ enabled: false });
});

// Listen for messages from content scripts
chrome.runtime.onMessage.addListener((request, sender, sendResponse) => {
    if (request.action === 'elementSelected') {
        // Forward element data to popup if it's open
        console.log('Element selected, forwarding to popup');
        sendResponse({ success: true });
    }
    return true;
});
