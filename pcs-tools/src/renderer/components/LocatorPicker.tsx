import React, { useState, useEffect } from 'react';
import { AppConfig, LocatorInfo, ElementInfo } from '../types';

interface LocatorPickerProps {
  config: AppConfig;
  isPicking: boolean;
  pickedData: any;
  onStartPick: () => void;
  onStopPick: () => void;
}

const LocatorPicker: React.FC<LocatorPickerProps> = ({
  config,
  isPicking,
  pickedData,
  onStartPick,
  onStopPick
}) => {
  const [locators, setLocators] = useState<LocatorInfo[]>([]);
  const [selectedLocator, setSelectedLocator] = useState<LocatorInfo | null>(null);
  const [elementInfo, setElementInfo] = useState<ElementInfo | null>(null);
  const [elementHtml, setElementHtml] = useState<string>('');
  const [simpleMode, setSimpleMode] = useState(true);
  const [qualityFilter, setQualityFilter] = useState('all');

  useEffect(() => {
    if (!pickedData) return;
    setLocators(pickedData.locators || []);
    setElementInfo(pickedData.element || null);
    setElementHtml(pickedData.elementHtml || '');
    if (pickedData.locators && pickedData.locators.length > 0) {
      setSelectedLocator(pickedData.locators[0]);
    }
  }, [pickedData]);

  const handleCopyLocator = (locator: string) => {
    navigator.clipboard.writeText(locator);
  };

  const handleTestLocator = (locator: string) => {
    if (window.electronBridge) {
      window.electronBridge.send('test-locator', locator);
    }
  };

  const handleHighlight = (locator: string) => {
    if (window.electronBridge) {
      window.electronBridge.send('test-locator', locator);
    }
  };

  const handleRegenerate = () => {
    // Trigger regeneration logic
    if (window.electronBridge) {
      window.electronBridge.send('start-pick');
    }
  };

  const getConfidenceBadge = (n: number, hit: boolean, actionable: boolean) => {
    if (hit && n === 1 && actionable) return 'HIGH';
    if (hit && n === 1) return 'MEDIUM';
    if (n === -1) return 'LOW';
    return 'LOW';
  };

  const filteredLocators = locators.filter(loc => {
    if (qualityFilter === 'verified-unique') return loc.hit && loc.n === 1;
    if (qualityFilter === 'unique') return loc.n === 1;
    if (qualityFilter === 'has-text') return loc.v.includes('getByText') || loc.v.includes('getByLabel');
    return true;
  });

  return (
    <div style={{ padding: '12px' }}>
      {/* Controls */}
      <div style={{ display: 'flex', gap: '8px', marginBottom: '12px', flexWrap: 'wrap' }}>
        <button
          onClick={isPicking ? onStopPick : onStartPick}
          style={{
            background: isPicking ? '#ef4444' : '#22c55e',
            color: '#fff',
            padding: '8px 16px',
            fontWeight: '600'
          }}
        >
          {isPicking ? 'Stop Picking' : 'Start Picking'}
        </button>
        <button
          onClick={handleRegenerate}
          style={{ background: '#8b5cf6', color: '#fff' }}
        >
          Regenerate
        </button>
      </div>

      {/* Filters */}
      <div style={{ display: 'flex', gap: '8px', marginBottom: '12px', alignItems: 'center', flexWrap: 'wrap' }}>
        <label style={{ fontSize: '12px', color: '#a6adc8' }}>
          <input
            type="checkbox"
            checked={simpleMode}
            onChange={(e) => setSimpleMode(e.target.checked)}
            style={{ marginRight: '4px' }}
          />
          Simple Mode
        </label>
        <select
          value={qualityFilter}
          onChange={(e) => setQualityFilter(e.target.value)}
          style={{ fontSize: '12px', padding: '4px 8px' }}
        >
          <option value="all">All</option>
          <option value="verified-unique">Verified Unique</option>
          <option value="unique">Unique</option>
          <option value="has-text">Has Text</option>
        </select>
      </div>

      {/* Element Info */}
      {elementInfo && (
        <div style={{ background: '#313244', padding: '8px', borderRadius: '4px', marginBottom: '12px', fontSize: '12px' }}>
          <div><strong>Tag:</strong> {elementInfo.tag}</div>
          {elementInfo.id && <div><strong>ID:</strong> {elementInfo.id}</div>}
          {elementInfo.text && <div><strong>Text:</strong> {elementInfo.text.substring(0, 50)}...</div>}
          {elementInfo.classes && <div><strong>Classes:</strong> {elementInfo.classes}</div>}
        </div>
      )}

      {/* Locators List */}
      <div style={{ maxHeight: 'calc(100vh - 300px)', overflow: 'auto' }}>
        {filteredLocators.length === 0 ? (
          <div style={{ textAlign: 'center', color: '#a6adc8', padding: '20px' }}>
            {isPicking ? 'Click on an element to pick locators' : 'Start picking to generate locators'}
          </div>
        ) : (
          filteredLocators.map((loc, index) => (
            <div
              key={index}
              className={`locator-row ${selectedLocator === loc ? 'selected' : ''}`}
              onClick={() => setSelectedLocator(loc)}
            >
              <div style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center', marginBottom: '4px' }}>
                <span style={{ fontSize: '11px', fontWeight: '600', color: '#89b4fa' }}>
                  {loc.t}
                </span>
                <div style={{ display: 'flex', gap: '4px' }}>
                  <span className={`badge badge-${getConfidenceBadge(loc.n, loc.hit, loc.actionable).toLowerCase()}`}>
                    {getConfidenceBadge(loc.n, loc.hit, loc.actionable)}
                  </span>
                  {loc.actionable && <span className="badge badge-high">ACTIONABLE</span>}
                  {loc.hit && <span className="badge badge-medium">HIT</span>}
                  <span style={{ fontSize: '10px', color: '#a6adc8' }}>
                    n={loc.n === -1 ? '?' : loc.n}
                  </span>
                </div>
              </div>
              <div className="code-block" style={{ marginBottom: '8px' }}>
                {loc.v}
              </div>
              <div style={{ display: 'flex', gap: '4px' }}>
                <button
                  onClick={(e) => {
                    e.stopPropagation();
                    handleCopyLocator(loc.v);
                  }}
                  style={{ background: '#45475a', color: '#cdd6f4', fontSize: '11px', padding: '4px 8px' }}
                >
                  Copy
                </button>
                <button
                  onClick={(e) => {
                    e.stopPropagation();
                    handleTestLocator(loc.v);
                  }}
                  style={{ background: '#45475a', color: '#cdd6f4', fontSize: '11px', padding: '4px 8px' }}
                >
                  Test
                </button>
                <button
                  onClick={(e) => {
                    e.stopPropagation();
                    handleHighlight(loc.v);
                  }}
                  style={{ background: '#f59e0b', color: '#1e1e2e', fontSize: '11px', padding: '4px 8px' }}
                >
                  Highlight
                </button>
              </div>
            </div>
          ))
        )}
      </div>
    </div>
  );
};

export default LocatorPicker;
