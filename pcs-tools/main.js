const { app, BrowserWindow, BrowserView, ipcMain, dialog } = require('electron');
const path = require('path');
const https = require('https');

let mainWindow;
let browserView;
let currentZoom = 1.0;

// Corporate site support
app.commandLine.appendSwitch('ignore-certificate-errors');
app.commandLine.appendSwitch('allow-insecure-localhost');
app.commandLine.appendSwitch('auth-server-whitelist', '*');
app.commandLine.appendSwitch('auth-negotiate-delegate-whitelist', '*');

function createWindow() {
  console.log('[PCS] Creating window...');
  mainWindow = new BrowserWindow({
    width: 1400,
    height: 900,
    webPreferences: {
      preload: path.join(__dirname, 'preload-renderer.js'),
      contextIsolation: true,
      nodeIntegration: false
    },
    title: 'PCS Tools'
  });
  console.log('[PCS] Window created');

  // Create BrowserView for embedded browser
  browserView = new BrowserView({
    webPreferences: {
      preload: path.join(__dirname, 'preload-content.js'),
      contextIsolation: true,
      nodeIntegration: false,
      webSecurity: false
    }
  });

  // Set initial bounds (380px left sidebar, remaining for BrowserView)
  const [width, height] = mainWindow.getContentSize();
  browserView.setBounds({
    x: 380,
    y: 40,
    width: width - 380,
    height: height - 40
  });

  mainWindow.addBrowserView(browserView);
  mainWindow.on('resize', () => {
    const [width, height] = mainWindow.getContentSize();
    browserView.setBounds({
      x: 380,
      y: 40,
      width: width - 380,
      height: height - 40
    });
  });

  // Load renderer
  mainWindow.loadFile('dist/index.html');

  // Load initial page in BrowserView
  browserView.webContents.loadURL('about:blank');

  // Inject content script after navigation
  browserView.webContents.on('did-finish-load', () => {
    injectContentScript();
    // Notify renderer of current URL
    mainWindow.webContents.send('did-navigate', browserView.webContents.getURL());
  });

  // Forward content-script → main → renderer (these come from preload-content.js)
  browserView.webContents.on('ipc-message', (event, channel, ...args) => {
    const forwardChannels = ['locators-picked', 'action-recorded', 'status-update', 'test-result'];
    if (forwardChannels.includes(channel)) {
      console.log('[PCS] Forwarding from content to renderer:', channel);
      mainWindow.webContents.send(channel, ...args);
    }
  });

  // Navigation notification to renderer
  browserView.webContents.on('did-navigate', (event, url) => {
    mainWindow.webContents.send('did-navigate', url);
  });

  browserView.webContents.on('did-navigate-in-page', (event, url) => {
    mainWindow.webContents.send('did-navigate', url);
  });

  // Always open DevTools for debugging
  mainWindow.webContents.openDevTools({ mode: 'detach' });
}

function injectContentScript() {
  browserView.webContents.executeJavaScript(`
    if (!window.pcsContentScriptInjected) {
      ${require('fs').readFileSync(path.join(__dirname, 'content-script.js'), 'utf8')}
      window.pcsContentScriptInjected = true;
    }
  `);
}

// IPC Handlers

// Navigation
ipcMain.on('navigate-to', (event, url) => {
  try {
    browserView.webContents.loadURL(url);
  } catch (error) {
    console.error('[PCS] Navigation error:', error);
  }
});

// Send to content script
ipcMain.on('to-content', (event, channel, data) => {
  browserView.webContents.send(channel, data);
});

// Config changes
ipcMain.on('cfg-change', (event, config) => {
  browserView.webContents.send('cfg-change', config);
});

// Picker controls
ipcMain.on('start-pick', () => {
  browserView.webContents.send('start-pick');
});

ipcMain.on('stop-pick', () => {
  browserView.webContents.send('stop-pick');
});

// Recorder controls
ipcMain.on('start-rec', () => {
  browserView.webContents.send('start-rec');
});

ipcMain.on('stop-rec', () => {
  browserView.webContents.send('stop-rec');
});

ipcMain.on('play-recording', (event, actions) => {
  browserView.webContents.send('play-recording', actions);
});

ipcMain.on('stop-playback', () => {
  browserView.webContents.send('stop-playback');
});

// Test locator
ipcMain.on('test-locator', (event, locator) => {
  browserView.webContents.send('test-locator', locator);
});

// Clear highlights
ipcMain.on('clear-highlights', () => {
  browserView.webContents.send('clear-highlights');
});

// Zoom controls
ipcMain.on('zoom-in', () => {
  currentZoom = Math.min(browserView.webContents.getZoomFactor() + 0.1, 3.0);
  browserView.webContents.setZoomFactor(currentZoom);
  mainWindow.webContents.send('zoom-changed', Math.round(currentZoom * 100));
});

ipcMain.on('zoom-out', () => {
  currentZoom = Math.max(browserView.webContents.getZoomFactor() - 0.1, 0.3);
  browserView.webContents.setZoomFactor(currentZoom);
  mainWindow.webContents.send('zoom-changed', Math.round(currentZoom * 100));
});

ipcMain.on('zoom-reset', () => {
  currentZoom = 1.0;
  browserView.webContents.setZoomFactor(1.0);
  mainWindow.webContents.send('zoom-changed', 100);
});

// File I/O
ipcMain.handle('save-file', async (event, { content, defaultPath, filters }) => {
  const { filePath } = await dialog.showSaveDialog(mainWindow, {
    defaultPath,
    filters
  });
  if (filePath) {
    require('fs').writeFileSync(filePath, content);
    return { success: true, path: filePath };
  }
  return { success: false };
});

ipcMain.handle('load-file', async (event, { filters }) => {
  const { filePaths } = await dialog.showOpenDialog(mainWindow, {
    filters
  });
  if (filePaths && filePaths.length > 0) {
    const content = require('fs').readFileSync(filePaths[0], 'utf8');
    return { success: true, content, path: filePaths[0] };
  }
  return { success: false };
});

ipcMain.handle('load-session', async () => {
  const userDataPath = app.getPath('userData');
  const sessionPath = path.join(userDataPath, 'pcs-session.json');
  try {
    if (require('fs').existsSync(sessionPath)) {
      const content = require('fs').readFileSync(sessionPath, 'utf8');
      return JSON.parse(content);
    }
  } catch (error) {
    console.error('[PCS] Failed to load session:', error);
  }
  return null;
});

// Rock AI Integration
ipcMain.handle('rockai-generate', async (event, { apiBase, deploymentId, apiKey, userEmail, elementHtml, elementInfo }) => {
  return new Promise((resolve) => {
    const prompt = `You are a Playwright expert. Given HTML of a UI element, generate 3 reliable Playwright locators.
Tag: ${elementInfo.tag}
ID: ${elementInfo.id || 'none'}
Text: ${elementInfo.text || 'none'}
Classes: ${elementInfo.classes || 'none'}
HTML: ${elementHtml}

RULES:
1) Prefer getByRole > getByTestId > getByLabel > getByPlaceholder > CSS[data-*]
2) AVOID timestamps, dynamic IDs, auto-classes
3) AVOID hardcoded changing text
4) Use aria-label/title for icon buttons
5) For grid buttons: role + aria-label NOT text
6) Return only page. or locator strings:

CANDIDATE_1:\n<locator>\nConfidence: HIGH/MEDIUM/LOW\n
CANDIDATE_2:\n<locator>\nConfidence: HIGH/MEDIUM/LOW\n
CANDIDATE_3:\n<locator>\nConfidence: HIGH/MEDIUM/LOW`;

    const body = JSON.stringify({
      messages: [{ role: 'user', content: prompt }],
      reasoning_effort: 'medium'
    });

    const url = new URL(`${apiBase}/${deploymentId}/infer`);

    const options = {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
        'Authorization': `Bearer ${apiKey}`,
        'X-User-Email': userEmail
      },
      rejectUnauthorized: false,
      timeout: 30000
    };

    const req = https.request(url, options, (res) => {
      let data = '';
      res.on('data', (chunk) => data += chunk);
      res.on('end', () => {
        try {
          const response = JSON.parse(data);
          // Parse response to extract candidates
          const candidates = [];
          const matches = response.content?.match(/CANDIDATE_\d+:\s*([\s\S]*?)(?=CANDIDATE_\d+:|$)/g) || [];
          
          matches.forEach((match) => {
            const lines = match.trim().split('\n');
            const locator = lines[1]?.trim() || '';
            const confidenceLine = lines.find(l => l.includes('Confidence'));
            const confidence = confidenceLine?.includes('HIGH') ? 'HIGH' : 
                              confidenceLine?.includes('MEDIUM') ? 'MEDIUM' : 'LOW';
            
            if (locator && locator.startsWith('page.')) {
              candidates.push({ locator, confidence });
            }
          });

          resolve({ success: true, candidates });
        } catch (error) {
          console.error('[PCS] Rock AI parse error:', error);
          resolve({ success: false, error: error.message });
        }
      });
    });

    req.on('error', (error) => {
      console.error('[PCS] Rock AI request error:', error);
      resolve({ success: false, error: error.message });
    });

    req.on('timeout', () => {
      req.destroy();
      resolve({ success: false, error: 'Request timeout' });
    });

    req.write(body);
    req.end();
  });
});

app.whenReady().then(createWindow);

app.on('window-all-closed', () => {
  if (process.platform !== 'darwin') {
    app.quit();
  }
});

app.on('activate', () => {
  if (BrowserWindow.getAllWindows().length === 0) {
    createWindow();
  }
});

// Keyboard shortcuts
app.on('browser-window-focus', () => {
  // Register keyboard shortcuts
});
