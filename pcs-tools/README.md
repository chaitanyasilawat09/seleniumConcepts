# PCS Tools

A desktop Electron application for Playwright/Selenium test automation - featuring a locator picker, action recorder, and test code generator.

## Features

- **Locator Picker**: Click on elements to generate reliable Playwright/Selenium locators
- **Action Recorder**: Record user interactions and generate test code
- **Multi-Framework Support**: Playwright (TypeScript, JavaScript, Python, Java) and Selenium (Java, Python)
- **Code Generation**: Automatically generate test code in your preferred language
- **Shadow DOM Support**: Handle web components and shadow DOM elements
- **AI Integration**: Rock AI integration for intelligent locator generation
- **BrowserView**: Embedded browser for real-time testing

## Project Structure

```
pcs-tools/
├── main.js                    # Electron main process
├── preload-renderer.js        # Bridge for React sidebar
├── preload-content.js         # Bridge for content script
├── content-script.js          # Injected into target pages
├── package.json               # Dependencies and scripts
├── webpack.config.js          # Webpack configuration
├── tsconfig.json             # TypeScript configuration
└── src/renderer/
    ├── index.html            # Renderer HTML
    ├── index.tsx             # React entry point
    ├── index.css             # Global styles
    ├── App.tsx               # Main React component
    ├── types.ts              # TypeScript types
    └── components/
        ├── ConfigBar.tsx     # Configuration bar
        ├── LocatorPicker.tsx # Locator picker tab
        ├── Recorder.tsx      # Recorder tab
        └── StatusBar.tsx     # Status bar
```

## Installation

```bash
cd pcs-tools
npm install
```

## Development

```bash
# Build the renderer
npm run build

# Start the application
npm start

# Watch mode (auto-rebuild on changes)
npm run watch:renderer

# Full development mode (watch + auto-restart)
npm run dev
```

## Building for Distribution

```bash
npm run dist
```

This will create a portable executable in the `release/` directory.

## Usage

### Locator Picker

1. Click "Start Picking" in the Picker tab
2. Click on any element in the embedded browser
3. View generated locators with confidence scores
4. Copy, test, or highlight locators

### Recorder

1. Navigate to a URL in the embedded browser
2. Click "Start Recording" in the Recorder tab
3. Perform actions (click, type, select, etc.)
4. Click "Stop Recording"
5. View generated test code
6. Copy or save the code

### Configuration

- **Framework**: Choose between Playwright or Selenium
- **Language**: TypeScript, JavaScript, Python, or Java
- **Locator Mode**: Playwright or Custom
- **Strict Mode**: Only record high-confidence actions
- **Auto Assert**: Automatically add assertions

## Keyboard Shortcuts

- `Ctrl+1`: Picker tab
- `Ctrl+2`: Recorder tab
- `Ctrl+3`: Inspector tab
- `Ctrl+4`: Batch tab
- `Ctrl+5`: History tab
- `Ctrl+6`: HTML tab
- `Ctrl+7`: AI tab
- `Ctrl++`: Zoom in
- `Ctrl+-`: Zoom out
- `Ctrl+0`: Reset zoom

## Locator Generation

The app generates locators in the following priority order:

1. getByTestId (data-testid, data-cy, data-pw, etc.)
2. getByRole (ARIA roles with names)
3. getByLabel (for inputs)
4. getByPlaceholder
5. getByText
6. getByTitle
7. CSS selectors
8. XPath

Each locator is scored based on:
- Uniqueness (n=1)
- Hit rate (matches the element)
- Actionability (can be interacted with)
- Stability (not dynamic/timestamp-based)

## Shadow DOM Support

The content script automatically detects and handles:
- Shadow root boundaries
- Nested shadow DOM
- Shadow-piercing selectors (>>>)
- Event propagation across shadow roots

## AI Integration

Configure Rock AI in the AI tab:
- API Base URL
- Deployment ID
- API Key
- User Email

The AI will generate additional locator candidates based on element HTML and context.

## Troubleshooting

### Picker doesn't auto-stop
- Ensure `stopPicking()` is called after sending locators to renderer

### Events firing twice
- Event deduplication is implemented with 50ms window

### SVG elements not clickable
- SVG escalation is applied automatically in recording/playback

### Dynamic text in locators
- Dynamic text detection filters out timestamps, dates, currency, etc.

## License

MIT
