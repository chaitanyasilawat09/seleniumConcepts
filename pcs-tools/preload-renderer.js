const { contextBridge, ipcRenderer } = require('electron');

const validSendChannels = [
  'navigate-to',
  'to-content',
  'cfg-change',
  'start-pick',
  'stop-pick',
  'start-rec',
  'stop-rec',
  'play-recording',
  'stop-playback',
  'test-locator',
  'clear-highlights',
  'zoom-in',
  'zoom-out',
  'zoom-reset'
];

const validRecvChannels = [
  'locators-picked',
  'action-recorded',
  'status-update',
  'test-result',
  'zoom-changed',
  'did-navigate'
];

const validInvokeChannels = [
  'save-file',
  'load-file',
  'load-session',
  'rockai-generate'
];

contextBridge.exposeInMainWorld('electronBridge', {
  send: (channel, data) => {
    if (validSendChannels.includes(channel)) {
      ipcRenderer.send(channel, data);
    }
  },
  on: (channel, callback) => {
    if (validRecvChannels.includes(channel)) {
      ipcRenderer.on(channel, (event, data) => callback(data));
    }
  },
  removeListener: (channel, callback) => {
    if (validRecvChannels.includes(channel)) {
      ipcRenderer.removeListener(channel, callback);
    }
  },
  invoke: async (channel, ...args) => {
    if (validInvokeChannels.includes(channel)) {
      return ipcRenderer.invoke(channel, ...args);
    }
  }
});
