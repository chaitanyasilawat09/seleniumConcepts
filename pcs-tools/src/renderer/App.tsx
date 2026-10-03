import React, { useState, useEffect } from 'react';
import ConfigBar from './components/ConfigBar';
import LocatorPicker from './components/LocatorPicker';
import Recorder from './components/Recorder';
import StatusBar from './components/StatusBar';
import { AppConfig, LocatorInfo, RecordedAction, TestResult } from './types';

declare global {
  interface Window {
    electronBridge?: {
      send: (channel: string, data?: any) => void;
      on: (channel: string, callback: (data: any) => void) => void;
      removeListener: (channel: string, callback: (data: any) => void) => void;
      invoke: (channel: string, ...args: any[]) => Promise<any>;
    };
  }
}

type TabType = 'picker' | 'recorder' | 'inspector' | 'batch' | 'history' | 'html' | 'ai';

function App() {
  const [activeTab, setActiveTab] = useState<TabType>('picker');
  const [config, setConfig] = useState<AppConfig>({
    framework: 'playwright',
    language: 'typescript',
    locatorMode: 'playwright',
    enabledTypes: {
      getByRole: true,
      getByText: true,
      getByLabel: true,
      getByPlaceholder: true,
      getByTestid: true,
      CSS: true,
      XPath: true
    },
    simpleMode: true,
    multiTooltipEnabled: true,
    qualityFilter: 'all',
    preferredType: 'auto',
    priorityPreference: 'auto',
    strictRecordMode: false,
    autoAssertEnabled: false,
    rockaiEnabled: false,
    rockaiApiBase: 'https://rockai.cnp-common.musw2.rockai.extdns.pshcloud.blackrock.com/model-deployment/deployments',
    rockaiDeploymentId: '',
    rockaiApiKey: '',
    rockaiUserEmail: ''
  });

  const [isPicking, setIsPicking] = useState(false);
  const [isRecording, setIsRecording] = useState(false);
  const [isPlaying, setIsPlaying] = useState(false);
  const [currentUrl, setCurrentUrl] = useState('');
  const [urlInput, setUrlInput] = useState('');
  const [zoom, setZoom] = useState(100);
  const [status, setStatus] = useState<'ready' | 'recording' | 'playing' | 'error'>('ready');
  const [statusMessage, setStatusMessage] = useState('Ready');
  const [pickedData, setPickedData] = useState<any>(null);
  const [recordedActions, setRecordedActions] = useState<any[]>([]);

  useEffect(() => {
    if (!window.electronBridge) return;

    // Listen for status updates
    window.electronBridge.on('locators-picked', (data: any) => {
      setIsPicking(false);
      setStatus('ready');
      setStatusMessage('Locator picked');
      setPickedData(data);
    });

    window.electronBridge.on('action-recorded', (data: any) => {
      setStatusMessage(`Recorded ${data.total} actions`);
      setRecordedActions(data.actions || []);
    });

    window.electronBridge.on('test-result', (result: TestResult) => {
      setStatusMessage(`Found ${result.count} matches`);
    });

    window.electronBridge.on('zoom-changed', (level: number) => {
      setZoom(level);
    });

    window.electronBridge.on('did-navigate', (url: string) => {
      setCurrentUrl(url);
      setUrlInput(url);
    });

    return () => {
      if (window.electronBridge) {
        window.electronBridge.removeListener('locators-picked', () => {});
        window.electronBridge.removeListener('action-recorded', () => {});
        window.electronBridge.removeListener('test-result', () => {});
        window.electronBridge.removeListener('zoom-changed', () => {});
        window.electronBridge.removeListener('did-navigate', () => {});
      }
    };
  }, []);

  const handleConfigChange = (newConfig: Partial<AppConfig>) => {
    const updated = { ...config, ...newConfig };
    setConfig(updated);
    if (window.electronBridge) {
      window.electronBridge.send('cfg-change', updated);
    }
  };

  const handleNavigate = (url: string) => {
    let finalUrl = url.trim();
    if (finalUrl && !/^https?:\/\//i.test(finalUrl)) {
      finalUrl = 'https://' + finalUrl;
    }
    setUrlInput(finalUrl);
    if (window.electronBridge && finalUrl) {
      window.electronBridge.send('navigate-to', finalUrl);
    }
  };

  const handleStartPick = () => {
    setIsPicking(true);
    setStatus('recording');
    setStatusMessage('Picking...');
    if (window.electronBridge) {
      window.electronBridge.send('start-pick');
    }
  };

  const handleStopPick = () => {
    setIsPicking(false);
    setStatus('ready');
    setStatusMessage('Ready');
    if (window.electronBridge) {
      window.electronBridge.send('stop-pick');
    }
  };

  const handleStartRecord = () => {
    setIsRecording(true);
    setStatus('recording');
    setStatusMessage('Recording...');
    if (window.electronBridge) {
      window.electronBridge.send('start-rec');
    }
  };

  const handleStopRecord = () => {
    setIsRecording(false);
    setStatus('ready');
    setStatusMessage('Ready');
    if (window.electronBridge) {
      window.electronBridge.send('stop-rec');
    }
  };

  const handleZoomIn = () => {
    if (window.electronBridge) {
      window.electronBridge.send('zoom-in');
    }
  };

  const handleZoomOut = () => {
    if (window.electronBridge) {
      window.electronBridge.send('zoom-out');
    }
  };

  const handleZoomReset = () => {
    if (window.electronBridge) {
      window.electronBridge.send('zoom-reset');
    }
  };

  const renderTab = () => {
    switch (activeTab) {
      case 'picker':
        return (
          <LocatorPicker
            config={config}
            isPicking={isPicking}
            pickedData={pickedData}
            onStartPick={handleStartPick}
            onStopPick={handleStopPick}
          />
        );
      case 'recorder':
        return (
          <Recorder
            config={config}
            isRecording={isRecording}
            recordedActions={recordedActions}
            onStartRecord={handleStartRecord}
            onStopRecord={handleStopRecord}
          />
        );
      default:
        return <div style={{ padding: '20px', textAlign: 'center' }}>Coming soon</div>;
    }
  };

  return (
    <div style={{ display: 'flex', flexDirection: 'column', height: '100%' }}>
      {/* Config Bar */}
      <ConfigBar
        config={config}
        onConfigChange={handleConfigChange}
        currentUrl={urlInput}
        onUrlChange={setUrlInput}
        onNavigate={handleNavigate}
        zoom={zoom}
        onZoomIn={handleZoomIn}
        onZoomOut={handleZoomOut}
        onZoomReset={handleZoomReset}
      />

      {/* Tabs */}
      <div style={{ display: 'flex', borderBottom: '1px solid #45475a' }}>
        {[
          { key: 'picker' as TabType, label: 'Picker', shortcut: 'Ctrl+1' },
          { key: 'recorder' as TabType, label: 'Recorder', shortcut: 'Ctrl+2' },
          { key: 'inspector' as TabType, label: 'Inspector', shortcut: 'Ctrl+3' },
          { key: 'batch' as TabType, label: 'Batch', shortcut: 'Ctrl+4' },
          { key: 'history' as TabType, label: 'History', shortcut: 'Ctrl+5' },
          { key: 'html' as TabType, label: 'HTML', shortcut: 'Ctrl+6' },
          { key: 'ai' as TabType, label: 'AI', shortcut: 'Ctrl+7' }
        ].map(tab => (
          <button
            key={tab.key}
            className={`tab-button ${activeTab === tab.key ? 'active' : ''}`}
            onClick={() => setActiveTab(tab.key)}
            style={{ flex: 1 }}
          >
            {tab.label}
          </button>
        ))}
      </div>

      {/* Tab Content */}
      <div style={{ flex: 1, overflow: 'auto' }}>
        {renderTab()}
      </div>

      {/* Status Bar */}
      <StatusBar status={status} message={statusMessage} />
    </div>
  );
}

export default App;
