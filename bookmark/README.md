# Element Locator Generator - Bookmarklet

A bookmarklet version of the Element Locator Generator that works without installing a browser extension. Perfect for corporate environments where extensions cannot be installed.

## How to Use

### Method 1: Drag and Drop (Easiest)

1. Open `bookmarklet.html` in your browser
2. Drag the "🎯 Locator Generator" button to your bookmarks bar
3. Navigate to any website
4. Click the bookmark to activate the locator tool

### Method 2: Manual Bookmark Creation

1. Copy the bookmarklet code from `bookmarklet-code.txt`
2. Create a new bookmark in your browser
3. Name it "🎯 Locator Generator"
4. Paste the code as the URL
5. Save the bookmark

## Features

- **Picking Mode**: Hover over elements to see locators, click to select
- **Multiple Locator Types**: ID, Name, Class, Tag Name, Link Text, CSS Selector, XPath
- **Framework Support**: Selenium and Playwright locators
- **Priority-based**: Shows best locators first
- **Copy to Clipboard**: One-click copy for any locator
- **Draggable Panel**: Move the panel anywhere on the screen
- **No Installation**: Works instantly without any setup

## How It Works

When you click the bookmarklet:
1. The JavaScript code is injected into the current page
2. A locator panel appears on the right side of the screen
3. Hover over elements to see tooltip with locators
4. Click on an element to see all available locators in the panel
5. Click "Copy" to copy any locator to your clipboard
6. Click "×" to close the tool

## Supported Browsers

- Chrome/Edge
- Firefox
- Safari
- Opera

## Limitations

- Bookmarklets cannot access cross-origin resources
- Some websites may block bookmarklet execution
- The tool runs in the context of the current page
- State is not preserved between page navigations

## Troubleshooting

**Bookmarklet doesn't work:**
- Make sure JavaScript is enabled in your browser
- Some websites may block bookmarklets - try on a different site
- Check if your browser's security settings allow bookmarklets

**Panel doesn't appear:**
- Refresh the page and try again
- Check browser console for errors (F12)
- Make sure you're not on a chrome:// or about: page

**Locators not showing:**
- Try clicking on different elements
- Check if the element is inside an iframe (not fully supported)
- Verify the element has identifiable attributes

## Security

The bookmarklet code runs entirely in your browser's JavaScript context. It:
- Does not send any data to external servers
- Does not track your browsing activity
- Does not modify the page permanently
- Only reads element attributes to generate locators

## License

Same as the original Element Locator Generator extension.
