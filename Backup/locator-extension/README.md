# Element Locator Generator - Chrome Extension

A powerful Chrome extension that automatically generates XPath, CSS selectors, and other locators when hovering over web elements. Features priority-based locator suggestions for Selenium WebDriver and Playwright automation.

## Features

- **Real-time Element Detection**: Hover over any element to instantly see its locators
- **Priority-based Locators**: Locators are ordered by reliability (ID > Name > Class > Tag > Link Text > CSS > XPath)
- **Multiple Framework Support**: Generates code for both Selenium WebDriver and Playwright
- **Visual Highlighting**: Elements are highlighted when hovered for easy identification
- **One-click Copy**: Copy locators directly to clipboard
- **Keyboard Shortcut**: Use `Ctrl+Shift+L` to toggle locator mode
- **Modern UI**: Clean, responsive interface with gradient styling

## Locator Priority System

1. **ID** - Most reliable (Priority: 1)
2. **Name** - Very reliable (Priority: 2)
3. **Class** - Good reliability (Priority: 3)
4. **Tag Name** - Basic selector (Priority: 4)
5. **Link Text** - For anchor elements (Priority: 5)
6. **Partial Link Text** - For anchor elements (Priority: 6)
7. **CSS Selector** - Attribute-based (Priority: 7)
8. **XPath** - Most flexible (Priority: 8)

## Installation

### Step 1: Generate Icons

Before installing, you need to generate the extension icons:

1. Open `icons/generate-icons.html` in your browser
2. Click "Download All" to download all three icon files (icon16.png, icon48.png, icon128.png)
3. Save these files in the `icons/` folder

### Step 2: Load Extension in Chrome

1. Open Chrome browser
2. Navigate to `chrome://extensions/`
3. Enable "Developer mode" using the toggle in the top-right corner
4. Click the "Load unpacked" button
5. Select the `locator-extension` folder from your computer
6. The extension will now appear in your extensions list

### Step 3: Use the Extension

1. Click the extension icon in your browser toolbar
2. Toggle "Enable Locator Mode" to activate
3. Navigate to any website (not Chrome pages)
4. Hover over elements to see their locators
5. Click the copy buttons to copy locators to clipboard

## Usage

### Basic Usage

1. Click the extension icon in your browser toolbar
2. Enable "Locator Mode" using the toggle
3. Move your mouse over any element on the page
4. A panel will appear showing:
   - Element information (tag, ID, classes, text)
   - All available locators with priority
   - Selenium WebDriver code
   - Playwright code
5. Click "Copy" buttons to copy the desired locator

### Keyboard Shortcut

Press `Ctrl+Shift+L` (Windows/Linux) or `Cmd+Shift+L` (Mac) to toggle locator mode on/off.

### Disabling

To disable locator mode:
- Click the extension icon and toggle off "Enable Locator Mode"
- Or press the keyboard shortcut again
- Or click the × button in the locator panel

## File Structure

```
locator-extension/
├── manifest.json          # Extension configuration
├── background.js           # Service worker for extension lifecycle
├── content.js              # Content script for element detection
├── content.css             # Styles for injected elements
├── popup.html              # Extension popup UI
├── popup.js                # Popup logic
├── popup.css               # Popup styling
├── icons/
│   ├── generate-icons.html # Icon generator tool
│   ├── icon.svg            # SVG source icon
│   ├── icon16.png          # 16x16 icon (generate first)
│   ├── icon48.png          # 48x48 icon (generate first)
│   └── icon128.png         # 128x128 icon (generate first)
└── README.md               # This file
```

## Development

### Modifying the Extension

1. Make changes to any file in the extension folder
2. Go to `chrome://extensions/`
3. Click the refresh button on your extension card
4. Changes will be applied immediately

### Adding New Locator Types

Edit `content.js` and add new locator types in the `getAllLocators()` method:

```javascript
// Add your custom locator
if (element.hasAttribute('data-custom')) {
    locators.push({
        type: 'Custom',
        value: `[data-custom="${element.getAttribute('data-custom')}"]`,
        priority: 9,
        selenium: `By.cssSelector("[data-custom='${element.getAttribute('data-custom')}']")`,
        playwright: `page.locator('[data-custom="${element.getAttribute('data-custom')}"]')`
    });
}
```

### Customizing Styling

- **Popup UI**: Edit `popup.css`
- **Locator Panel**: Edit `content.css`
- **Highlight Color**: Edit the `highlightCurrentElement()` method in `content.js`

## Browser Compatibility

- Chrome/Edge: Full support (Manifest V3)
- Firefox: May require manifest adjustments
- Safari: Not currently supported

## Troubleshooting

### Extension not working on Chrome pages

Chrome extensions cannot work on `chrome://` pages for security reasons. Navigate to a regular website to use the extension.

### Locators not appearing

- Make sure "Locator Mode" is enabled in the popup
- Refresh the page if elements are not being detected
- Check browser console for errors (F12)

### Icons not showing

- Ensure you've generated and saved the PNG icons in the icons folder
- Verify filenames are exactly: `icon16.png`, `icon48.png`, `icon128.png`
- Reload the extension after adding icons

## Privacy

This extension:
- Does not collect any personal data
- Does not send data to external servers
- Works entirely locally in your browser
- Only accesses the DOM of the current page when enabled

## License

Free to use and modify for personal and commercial projects.

## Contributing

Feel free to submit issues and enhancement requests!

## Version History

- **v1.0.0** - Initial release
  - Basic locator generation
  - Priority-based ordering
  - Selenium and Playwright support
  - Visual element highlighting
  - Copy to clipboard functionality
