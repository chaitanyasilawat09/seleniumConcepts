const { contextBridge, ipcRenderer } = require('electron');

const validSendChannels = [
  'locators-picked',
  'action-recorded',
  'status-update',
  'test-result',
  'did-navigate'
];

const validRecvChannels = [
  'cfg-change',
  'start-pick',
  'stop-pick',
  'start-rec',
  'stop-rec',
  'play-recording',
  'stop-playback',
  'test-locator',
  'clear-highlights'
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
  }
});
