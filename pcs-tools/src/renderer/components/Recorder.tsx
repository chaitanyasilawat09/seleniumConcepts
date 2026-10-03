import React, { useState, useEffect } from 'react';
import { AppConfig, RecordedAction } from '../types';

interface RecorderProps {
  config: AppConfig;
  isRecording: boolean;
  recordedActions: RecordedAction[];
  onStartRecord: () => void;
  onStopRecord: () => void;
}

const Recorder: React.FC<RecorderProps> = ({
  config,
  isRecording,
  recordedActions,
  onStartRecord,
  onStopRecord
}) => {
  const [actions, setActions] = useState<RecordedAction[]>([]);
  const [generatedCode, setGeneratedCode] = useState('');
  const [strictMode, setStrictMode] = useState(false);
  const [autoAssert, setAutoAssert] = useState(false);

  useEffect(() => {
    setActions(recordedActions);
    generateCode(recordedActions);
  }, [recordedActions, config]);

  const generateCode = (recordedActions: RecordedAction[]) => {
    if (recordedActions.length === 0) {
      setGeneratedCode('// No actions recorded');
      return;
    }

    let code = '';
    
    if (config.framework === 'playwright') {
      if (config.language === 'typescript') {
        code = generatePlaywrightTS(recordedActions);
      } else if (config.language === 'javascript') {
        code = generatePlaywrightJS(recordedActions);
      } else if (config.language === 'python') {
        code = generatePlaywrightPython(recordedActions);
      } else if (config.language === 'java') {
        code = generatePlaywrightJava(recordedActions);
      }
    } else if (config.framework === 'selenium') {
      if (config.language === 'java') {
        code = generateSeleniumJava(recordedActions);
      } else if (config.language === 'python') {
        code = generateSeleniumPython(recordedActions);
      }
    }

    setGeneratedCode(code);
  };

  const generatePlaywrightTS = (actions: RecordedAction[]): string => {
    let code = `import { test, expect } from '@playwright/test';\n\ntest('recorded test', async ({ page }) => {\n`;
    
    actions.forEach(action => {
      if (action.confidence === 'medium' || action.confidence === 'low') {
        code += `  // Confidence: ${action.confidence.toUpperCase()}\n`;
      }
      
      switch (action.type) {
        case 'click':
          code += `  await ${action.loc}.click();\n`;
          break;
        case 'fill':
          code += `  await ${action.loc}.fill('${action.value}');\n`;
          break;
        case 'check':
          code += `  await ${action.loc}.check();\n`;
          break;
        case 'uncheck':
          code += `  await ${action.loc}.uncheck();\n`;
          break;
        case 'select':
          code += `  await ${action.loc}.selectOption('${action.value}');\n`;
          break;
        case 'keypress':
          code += `  await ${action.loc}.press('${action.key || ''}');\n`;
          break;
      }
    });
    
    code += `});\n`;
    return code;
  };

  const generatePlaywrightJS = (actions: RecordedAction[]): string => {
    let code = `const { test, expect } = require('@playwright/test');\n\ntest('recorded test', async ({ page }) => {\n`;
    
    actions.forEach(action => {
      if (action.confidence === 'medium' || action.confidence === 'low') {
        code += `  // Confidence: ${action.confidence.toUpperCase()}\n`;
      }
      
      switch (action.type) {
        case 'click':
          code += `  await ${action.loc}.click();\n`;
          break;
        case 'fill':
          code += `  await ${action.loc}.fill('${action.value}');\n`;
          break;
        case 'check':
          code += `  await ${action.loc}.check();\n`;
          break;
        case 'uncheck':
          code += `  await ${action.loc}.uncheck();\n`;
          break;
        case 'select':
          code += `  await ${action.loc}.selectOption('${action.value}');\n`;
          break;
        case 'keypress':
          code += `  await ${action.loc}.press('${action.key || ''}');\n`;
          break;
      }
    });
    
    code += `});\n`;
    return code;
  };

  const generatePlaywrightPython = (actions: RecordedAction[]): string => {
    let code = `from playwright.sync_api import Page, expect\n\ndef test_recorded_test(page: Page):\n`;
    
    actions.forEach(action => {
      if (action.confidence === 'medium' || action.confidence === 'low') {
        code += `    # Confidence: ${action.confidence.toUpperCase()}\n`;
      }
      
      switch (action.type) {
        case 'click':
          code += `    ${action.loc.replace('page.', 'page.')}.click()\n`;
          break;
        case 'fill':
          code += `    ${action.loc.replace('page.', 'page.')}.fill("${action.value}")\n`;
          break;
        case 'check':
          code += `    ${action.loc.replace('page.', 'page.')}.check()\n`;
          break;
        case 'uncheck':
          code += `    ${action.loc.replace('page.', 'page.')}.uncheck()\n`;
          break;
        case 'select':
          code += `    ${action.loc.replace('page.', 'page.')}.select_option("${action.value}")\n`;
          break;
        case 'keypress':
          code += `    ${action.loc.replace('page.', 'page.')}.press("${action.key}")\n`;
          break;
      }
    });
    
    return code;
  };

  const generatePlaywrightJava = (actions: RecordedAction[]): string => {
    let code = `import com.microsoft.playwright.*;\nimport static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;\n\npublic class RecordedTest {\n  public static void main(String[] args) {\n    try (Playwright playwright = Playwright.create()) {\n      Browser browser = playwright.chromium().launch();\n      Page page = browser.newPage();\n`;
    
    actions.forEach(action => {
      if (action.confidence === 'medium' || action.confidence === 'low') {
        code += `      // Confidence: ${action.confidence.toUpperCase()}\n`;
      }
      
      switch (action.type) {
        case 'click':
          code += `      ${action.loc.replace('page.', 'page.')}.click();\n`;
          break;
        case 'fill':
          code += `      ${action.loc.replace('page.', 'page.')}.fill("${action.value}");\n`;
          break;
        case 'check':
          code += `      ${action.loc.replace('page.', 'page.')}.check();\n`;
          break;
        case 'uncheck':
          code += `      ${action.loc.replace('page.', 'page.')}.uncheck();\n`;
          break;
        case 'select':
          code += `      ${action.loc.replace('page.', 'page.')}.selectOption("${action.value}");\n`;
          break;
        case 'keypress':
          code += `      ${action.loc.replace('page.', 'page.')}.press("${action.key}");\n`;
          break;
      }
    });
    
    code += `    }\n  }\n}\n`;
    return code;
  };

  const generateSeleniumJava = (actions: RecordedAction[]): string => {
    let code = `import org.openqa.selenium.*;\nimport org.openqa.selenium.chrome.ChromeDriver;\n\npublic class RecordedTest {\n  public static void main(String[] args) {\n    WebDriver driver = new ChromeDriver();\n`;
    
    actions.forEach(action => {
      if (action.confidence === 'medium' || action.confidence === 'low') {
        code += `    // Confidence: ${action.confidence.toUpperCase()}\n`;
      }
      
      switch (action.type) {
        case 'click':
          code += `    driver.findElement(${action.loc}).click();\n`;
          break;
        case 'fill':
          code += `    driver.findElement(${action.loc}).clear();\n    driver.findElement(${action.loc}).sendKeys("${action.value}");\n`;
          break;
        case 'check':
          code += `    if (!driver.findElement(${action.loc}).isSelected()) {\n      driver.findElement(${action.loc}).click();\n    }\n`;
          break;
        case 'uncheck':
          code += `    if (driver.findElement(${action.loc}).isSelected()) {\n      driver.findElement(${action.loc}).click();\n    }\n`;
          break;
        case 'select':
          code += `    new Select(driver.findElement(${action.loc})).selectByVisibleText("${action.value}");\n`;
          break;
        case 'keypress':
          code += `    driver.findElement(${action.loc}).sendKeys(Keys.${(action.key || '').toUpperCase()});\n`;
          break;
      }
    });
    
    code += `    driver.quit();\n  }\n}\n`;
    return code;
  };

  const generateSeleniumPython = (actions: RecordedAction[]): string => {
    let code = `from selenium import webdriver\nfrom selenium.webdriver.common.by import By\nfrom selenium.webdriver.common.keys import Keys\nfrom selenium.webdriver.support.ui import Select\n\ndriver = webdriver.Chrome()\n`;
    
    actions.forEach(action => {
      if (action.confidence === 'medium' || action.confidence === 'low') {
        code += `# Confidence: ${action.confidence.toUpperCase()}\n`;
      }
      
      switch (action.type) {
        case 'click':
          code += `driver.find_element(${action.loc}).click()\n`;
          break;
        case 'fill':
          code += `driver.find_element(${action.loc}).clear()\ndriver.find_element(${action.loc}).send_keys("${action.value}")\n`;
          break;
        case 'check':
          code += `if not driver.find_element(${action.loc}).is_selected():\n    driver.find_element(${action.loc}).click()\n`;
          break;
        case 'uncheck':
          code += `if driver.find_element(${action.loc}).is_selected():\n    driver.find_element(${action.loc}).click()\n`;
          break;
        case 'select':
          code += `Select(driver.find_element(${action.loc})).select_by_visible_text("${action.value}")\n`;
          break;
        case 'keypress':
          code += `driver.find_element(${action.loc}).send_keys(Keys.${(action.key || '').toUpperCase()})\n`;
          break;
      }
    });
    
    code += `driver.quit()\n`;
    return code;
  };

  const handleCopyCode = () => {
    navigator.clipboard.writeText(generatedCode);
  };

  const handleSaveCode = async () => {
    if (!window.electronBridge) return;
    
    const extension = config.language === 'typescript' ? 'ts' : 
                     config.language === 'javascript' ? 'js' :
                     config.language === 'java' ? 'java' : 'py';
    
    const result = await window.electronBridge.invoke('save-file', {
      content: generatedCode,
      defaultPath: `recorded-test.${extension}`,
      filters: [{ name: extension.toUpperCase(), extensions: [extension] }]
    });
  };

  const handleClear = () => {
    setActions([]);
    setGeneratedCode('');
  };

  const handlePlay = () => {
    if (!window.electronBridge) return;
    window.electronBridge.send('play-recording', actions);
  };

  return (
    <div style={{ padding: '12px', display: 'flex', flexDirection: 'column', height: '100%' }}>
      {/* Controls */}
      <div style={{ display: 'flex', gap: '8px', marginBottom: '12px', flexWrap: 'wrap' }}>
        <button
          onClick={isRecording ? onStopRecord : onStartRecord}
          style={{
            background: isRecording ? '#ef4444' : '#22c55e',
            color: '#fff',
            padding: '8px 16px',
            fontWeight: '600'
          }}
        >
          {isRecording ? 'Stop Recording' : 'Start Recording'}
        </button>
        <button
          onClick={handlePlay}
          disabled={actions.length === 0}
          style={{ background: '#3b82f6', color: '#fff' }}
        >
          Play
        </button>
        <button
          onClick={handleClear}
          disabled={actions.length === 0}
          style={{ background: '#45475a', color: '#cdd6f4' }}
        >
          Clear
        </button>
        <button
          onClick={handleCopyCode}
          disabled={!generatedCode}
          style={{ background: '#45475a', color: '#cdd6f4' }}
        >
          Copy
        </button>
        <button
          onClick={handleSaveCode}
          disabled={!generatedCode}
          style={{ background: '#45475a', color: '#cdd6f4' }}
        >
          Save
        </button>
      </div>

      {/* Options */}
      <div style={{ display: 'flex', gap: '16px', marginBottom: '12px', flexWrap: 'wrap' }}>
        <label style={{ fontSize: '12px', color: '#a6adc8' }}>
          <input
            type="checkbox"
            checked={strictMode}
            onChange={(e) => setStrictMode(e.target.checked)}
            style={{ marginRight: '4px' }}
          />
          Strict Mode
        </label>
        <label style={{ fontSize: '12px', color: '#a6adc8' }}>
          <input
            type="checkbox"
            checked={autoAssert}
            onChange={(e) => setAutoAssert(e.target.checked)}
            style={{ marginRight: '4px' }}
          />
          Auto Assert
        </label>
      </div>

      {/* Actions Count */}
      <div style={{ marginBottom: '8px', fontSize: '12px', color: '#a6adc8' }}>
        Actions: {actions.length}
      </div>

      {/* Code Output */}
      <div style={{ flex: 1, display: 'flex', flexDirection: 'column' }}>
        <textarea
          value={generatedCode}
          readOnly
          style={{
            flex: 1,
            background: '#11111b',
            border: '1px solid #45475a',
            borderRadius: '4px',
            padding: '8px',
            fontFamily: 'Monaco, Menlo, Ubuntu Mono, monospace',
            fontSize: '12px',
            color: '#a6e3a1',
            resize: 'none',
            minHeight: '300px'
          }}
        />
      </div>
    </div>
  );
};

export default Recorder;
