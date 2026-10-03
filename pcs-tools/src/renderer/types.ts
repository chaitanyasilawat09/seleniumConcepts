export interface LocatorInfo {
  t: string;
  v: string;
  p: number;
  n: number;
  hit: boolean;
  actionable: boolean;
  isAl?: boolean;
  aiConfidence?: 'HIGH' | 'MEDIUM' | 'LOW';
}

export interface ElementInfo {
  tag: string;
  id: string | null;
  text: string | null;
  classes: string | null;
}

export interface RecordedAction {
  type: string;
  loc: string;
  value?: string;
  key?: string;
  confidence: 'high' | 'medium' | 'low';
}

export interface AppConfig {
  framework: 'playwright' | 'selenium';
  language: 'typescript' | 'javascript' | 'java' | 'python';
  locatorMode: 'playwright' | 'custom';
  enabledTypes: {
    getByRole: boolean;
    getByText: boolean;
    getByLabel: boolean;
    getByPlaceholder: boolean;
    getByTestid: boolean;
    CSS: boolean;
    XPath: boolean;
  };
  simpleMode: boolean;
  multiTooltipEnabled: boolean;
  qualityFilter: string;
  preferredType: string;
  priorityPreference: string;
  strictRecordMode: boolean;
  autoAssertEnabled: boolean;
  rockaiEnabled: boolean;
  rockaiApiBase: string;
  rockaiDeploymentId: string;
  rockaiApiKey: string;
  rockaiUserEmail: string;
}

export interface RockAiConfig {
  apiBase: string;
  deploymentId: string;
  apiKey: string;
  userEmail: string;
}

export interface TestResult {
  locator: string;
  count: number;
  matches: Array<{
    tag: string;
    id: string | null;
    text: string | null;
  }>;
}
