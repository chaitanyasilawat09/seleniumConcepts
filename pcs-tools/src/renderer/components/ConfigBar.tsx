import React from 'react';
import { AppConfig } from '../types';

interface ConfigBarProps {
  config: AppConfig;
  onConfigChange: (config: Partial<AppConfig>) => void;
  currentUrl: string;
  onUrlChange: (url: string) => void;
  onNavigate: (url: string) => void;
  zoom: number;
  onZoomIn: () => void;
  onZoomOut: () => void;
  onZoomReset: () => void;
}

const ConfigBar: React.FC<ConfigBarProps> = ({
  config,
  onConfigChange,
  currentUrl,
  onUrlChange,
  onNavigate,
  zoom,
  onZoomIn,
  onZoomOut,
  onZoomReset
}) => {
  return (
    <div style={{ padding: '8px', borderBottom: '1px solid #45475a', background: '#1e1e2e' }}>
      {/* Framework & Language */}
      <div style={{ display: 'flex', gap: '8px', marginBottom: '8px', flexWrap: 'wrap' }}>
        <div style={{ display: 'flex', alignItems: 'center', gap: '4px' }}>
          <label style={{ fontSize: '11px', color: '#a6adc8' }}>FW:</label>
          <select
            value={config.framework}
            onChange={(e) => onConfigChange({ framework: e.target.value as any })}
            style={{ fontSize: '11px', padding: '4px 8px' }}
          >
            <option value="playwright">Playwright</option>
            <option value="selenium">Selenium</option>
          </select>
        </div>

        <div style={{ display: 'flex', alignItems: 'center', gap: '4px' }}>
          <label style={{ fontSize: '11px', color: '#a6adc8' }}>Lang:</label>
          <select
            value={config.language}
            onChange={(e) => onConfigChange({ language: e.target.value as any })}
            style={{ fontSize: '11px', padding: '4px 8px' }}
          >
            <option value="typescript">TS</option>
            <option value="javascript">JS</option>
            <option value="java">Java</option>
            <option value="python">Python</option>
          </select>
        </div>

        <div style={{ display: 'flex', alignItems: 'center', gap: '4px' }}>
          <label style={{ fontSize: '11px', color: '#a6adc8' }}>Loc:</label>
          <select
            value={config.locatorMode}
            onChange={(e) => onConfigChange({ locatorMode: e.target.value as any })}
            style={{ fontSize: '11px', padding: '4px 8px' }}
          >
            <option value="playwright">Playwright</option>
            <option value="custom">Custom</option>
          </select>
        </div>
      </div>

      {/* URL Bar */}
      <div style={{ display: 'flex', gap: '8px', marginBottom: '8px' }}>
        <input
          type="text"
          value={currentUrl}
          onChange={(e) => onUrlChange(e.target.value)}
          onKeyDown={(e) => {
            if (e.key === 'Enter') {
              onNavigate(currentUrl);
            }
          }}
          placeholder="Enter URL to navigate..."
          style={{ flex: 1, fontSize: '12px' }}
        />
        <button onClick={() => onNavigate(currentUrl)} style={{ background: '#89b4fa', color: '#1e1e2e' }}>
          Go
        </button>
      </div>

      {/* Zoom Controls */}
      <div style={{ display: 'flex', alignItems: 'center', gap: '8px' }}>
        <button onClick={onZoomOut} style={{ background: '#45475a', color: '#cdd6f4', padding: '4px 8px' }}>
          -
        </button>
        <span style={{ fontSize: '12px', color: '#a6adc8', minWidth: '40px', textAlign: 'center' }}>
          {zoom}%
        </span>
        <button onClick={onZoomIn} style={{ background: '#45475a', color: '#cdd6f4', padding: '4px 8px' }}>
          +
        </button>
        <button onClick={onZoomReset} style={{ background: '#45475a', color: '#cdd6f4', padding: '4px 8px', fontSize: '11px' }}>
          Reset
        </button>
      </div>
    </div>
  );
};

export default ConfigBar;
