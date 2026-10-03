// PCS Tools Content Script
// Injected into BrowserView pages

// Global State
var isPicking = false;
var isRecording = false;
var isPlaying = false;
var isAssertPicking = false;

var recordedActions = []; // (type, loc, el, value, key, confidence, actionable)
var lastPickedEl = null;
var lastGenLocs = [];

var highlightBox = null;
var tooltipBox = null;
var hlOverlays = [];
var hoverEl = null;
var hoverTimer = null;
var hoverRecordDelay = 5000;

var lastInputEl = null;
var lastInputValues = '';
var inputTimer = null;

var strictRecordMode = false;
var autoAssertEnabled = false;

var cfg = {
  framework: 'playwright',
  language: 'typescript'
};

var locatorMode = 'playwright'; // 'playwright' | 'custom'

var enabledTypes = {
  getByRole: true,
  getByText: true,
  getByLabel: true,
  getByPlaceholder: true,
  getByTestid: true,
  CSS: true,
  XPath: true
};

var simpleMode = true;
var multiTooltipEnabled = true;

var qualityFilter = 'all';
var preferredType = 'auto';
var priorityPreference = 'auto';

// SVG Tags
var _svgTags = {
  svg: 1,
  path: 1,
  polyline: 1,
  polygon: 1,
  circle: 1,
  ellipse: 1,
  line: 1,
  rect: 1,
  g: 1,
  use: 1,
  text: 1,
  tspan: 1,
  defs: 1,
  clippath: 1,
  mask: 1,
  image: 1,
  symbol: 1,
  marker: 1,
  pattern: 1,
  foreignobject: 1
};

// Event deduplication
var _lastEventTs = 0;
var _lastEventTarget = null;
var _lastEventType = null;

function isDuplicateEvent(e) {
  var t = e.composedPath ? e.composedPath()[0] : e.target;
  var now = e.timeStamp || Date.now();
  // Same target + same event type within 100ms = duplicate (shadow re-fire)
  if (t === _lastEventTarget && e.type === _lastEventType && Math.abs(now - _lastEventTs) < 100) {
    return true;
  }
  _lastEventTs = now;
  _lastEventTarget = t;
  _lastEventType = e.type;
  return false;
}

// SVG Escalation
function escapeSvgTarget(el) {
  if (!el || !el.tagName || !_svgTags[el.tagName.toLowerCase()]) {
    return el;
  }
  for (var i = 0, node = el; i < 10 && node; i++) {
    node = node.parentElement || (node.getRootNode && node.getRootNode().host) || null;
    if (node && node.tagName && !_svgTags[node.tagName.toLowerCase()]) {
      return node;
    }
  }
  return el;
}

// Shadow DOM Utilities
function isShadow(el) {
  return el.getRootNode() instanceof ShadowRoot;
}

function shadowPath(el) {
  var path = [];
  var node = el;
  while (node) {
    var root = node.getRootNode();
    if (root instanceof ShadowRoot) {
      path.unshift({ host: root.host, inner: node });
      node = root.host;
    } else {
      break;
    }
  }
  return path;
}

function shadowCss(el, selector) {
  // Deep querySelectorAll across shadow roots
  function deepQSA(root, sel) {
    var results = [];
    var direct = root.querySelectorAll(sel);
    for (var i = 0; i < direct.length; i++) {
      results.push(direct[i]);
    }
    // Check shadow roots
    var all = root.querySelectorAll('*');
    for (var j = 0; j < all.length; j++) {
      var shadow = all[j].shadowRoot;
      if (shadow) {
        var nested = deepQSA(shadow, sel);
        for (var k = 0; k < nested.length; k++) {
          results.push(nested[k]);
        }
      }
    }
    return results;
  }
  return deepQSA(document, selector);
}

// CSS Class Filtering
function goodCls(cls) {
  if (!cls) return false;
  // Reject prefixes
  var badPrefixes = ['ng-', 'css-', 'sc-', 'jsx-', 'svelte-', 'cdk-', 'mat-', 'mdc-'];
  for (var i = 0; i < badPrefixes.length; i++) {
    if (cls.indexOf(badPrefixes[i]) === 0) return false;
  }
  // Reject state words
  var stateWords = ['focused', 'active', 'hover', 'selected', 'open', 'expanded', 'collapsed', 'disabled', 'checked', 'pressed', 'dragging', 'highlighted', 'visible', 'hidden', 'entered', 'leaving', 'animating'];
  for (var j = 0; j < stateWords.length; j++) {
    if (cls === stateWords[j]) return false;
  }
  // Reject single char
  if (cls.length === 1) return false;
  // Reject length > 60
  if (cls.length > 60) return false;
  // Reject starts with digit
  if (/^\d/.test(cls)) return false;
  return true;
}

// Stable ID check
function stableId(id) {
  if (!id) return false;
  // 8+ consecutive hex chars
  if (/[0-9a-fA-F]{8}/.test(id)) return false;
  // 4+ consecutive digits
  if (/\d{4}/.test(id)) return false;
  // Contains spaces or underscores
  if (/\s|_/.test(id)) return false;
  // Matches [-]\d{1,3}[-] or [-]\d{1,3}$
  if (/[-]\d{1,3}[-]|[-]\d{1,3}$/.test(id)) return false;
  // Length > 60
  if (id.length > 60) return false;
  // Timestamp/date patterns
  if (/^\d{4}-\d{2}-\d{2}/.test(id)) return false;
  if (/\d{8}/.test(id)) return false;
  return true;
}

// ARIA Role
function getAriaRole(el) {
  if (el.getAttribute) {
    var explicit = el.getAttribute('role');
    if (explicit) return explicit;
  }
  var tag = el.tagName.toLowerCase();
  var type = el.getAttribute ? el.getAttribute('type') : '';
  var roleMap = {
    'button': 'button',
    'a': 'link',
    'input': 'textbox',
    'textarea': 'textbox',
    'select': 'combobox'
  };
  if (tag === 'input') {
    if (type === 'checkbox') return 'checkbox';
    if (type === 'radio') return 'radio';
    if (type === 'text' || type === 'email' || type === 'password') return 'textbox';
  }
  if (tag === 'h1' || tag === 'h2' || tag === 'h3' || tag === 'h4' || tag === 'h5' || tag === 'h6') {
    return 'heading';
  }
  return roleMap[tag] || null;
}

// Accessible Name
function getName(el) {
  // 1. aria-label
  var ariaLabel = el.getAttribute ? el.getAttribute('aria-label') : null;
  if (ariaLabel) return ariaLabel;
  
  // 2. aria-labelledby (shadow-aware)
  var labelledBy = el.getAttribute ? el.getAttribute('aria-labelledby') : null;
  if (labelledBy) {
    var ref = document.getElementById(labelledBy);
    if (ref) return ref.textContent.trim();
  }
  
  // 3. label[for=id] text
  var id = el.id;
  if (id) {
    var label = document.querySelector('label[for="' + id + '"]');
    if (label) return label.textContent.trim();
  }
  
  // 4. Wrapping <label> text minus el value
  var parentLabel = el.closest ? el.closest('label') : null;
  if (parentLabel) {
    var text = parentLabel.textContent;
    var val = el.value || '';
    return text.replace(val, '').trim();
  }
  
  // 5. Previous sibling text
  var prev = el.previousElementSibling;
  if (prev && prev.textContent) {
    return prev.textContent.trim();
  }
  
  // 6. First short-text label/span/div within 5 parent levels
  var node = el;
  for (var i = 0; i < 5 && node; i++) {
    var candidates = node.querySelectorAll ? node.querySelectorAll('label, span, div') : [];
    for (var j = 0; j < candidates.length; j++) {
      var cand = candidates[j];
      if (cand.textContent.length < 80 && cand.children.length < 3) {
        return cand.textContent.trim();
      }
    }
    node = node.parentElement;
  }
  
  return null;
}

// CSS Builders
function segCss(el) {
  var parts = [];
  // ID if stable
  if (el.id && stableId(el.id)) {
    return '#' + el.id;
  }
  // data-attr if unique
  var dataAttrs = ['data-testid', 'data-cy', 'data-pw', 'data-test', 'data-qa'];
  for (var i = 0; i < dataAttrs.length; i++) {
    var val = el.getAttribute(dataAttrs[i]);
    if (val) {
      var sel = '[' + dataAttrs[i] + '="' + val + '"]';
      if (document.querySelectorAll(sel).length === 1) {
        return sel;
      }
    }
  }
  // aria-label if unique
  var ariaLabel = el.getAttribute ? el.getAttribute('aria-label') : null;
  if (ariaLabel) {
    var sel = '[aria-label="' + ariaLabel + '"]';
    if (document.querySelectorAll(sel).length === 1) {
      return sel;
    }
  }
  // tag + classes
  var tag = el.tagName.toLowerCase();
  var classes = [];
  if (el.classList) {
    for (var j = 0; j < el.classList.length; j++) {
      if (goodCls(el.classList[j])) {
        classes.push(el.classList[j]);
      }
    }
  }
  if (classes.length > 0) {
    return tag + '.' + classes.join('.');
  }
  return tag;
}

function buildCssUnique(el) {
  var parts = [];
  var node = el;
  while (node && node !== document.body) {
    parts.unshift(segCss(node));
    var sel = parts.join(' > ');
    try {
      if (document.querySelectorAll(sel).length === 1) {
        return sel;
      }
    } catch (e) {
      break;
    }
    node = node.parentElement;
  }
  return segCss(el);
}

function buildCssAttr(el) {
  var attrs = ['name', 'placeholder', 'role', 'aria-label', 'data-testid', 'data-cy', 'data-pw', 'href', 'title', 'for', 'type'];
  var tag = el.tagName.toLowerCase();
  for (var i = 0; i < attrs.length; i++) {
    var val = el.getAttribute(attrs[i]);
    if (val) {
      var sel = tag + '[' + attrs[i] + '="' + val + '"]';
      try {
        if (document.querySelectorAll(sel).length === 1) {
          return sel;
        }
      } catch (e) {}
    }
  }
  return '';
}

// XPath Builders
function buildXPUnique(el) {
  var parts = [];
  var node = el;
  while (node && node !== document.body) {
    var tag = node.tagName.toLowerCase();
    var parent = node.parentElement;
    if (parent) {
      var siblings = Array.from(parent.children).filter(function(c) {
        return c.tagName === node.tagName;
      });
      var index = siblings.indexOf(node) + 1;
      parts.unshift(tag + '[' + index + ']');
    } else {
      parts.unshift(tag);
    }
    node = parent;
  }
  return '//' + parts.join('/');
}

function buildIdXP(el) {
  var parts = [];
  var node = el;
  while (node) {
    if (node.id && stableId(node.id)) {
      parts.unshift('//*[@id="' + node.id + '"]');
      break;
    }
    var tag = node.tagName.toLowerCase();
    var parent = node.parentElement;
    if (parent) {
      var siblings = Array.from(parent.children).filter(function(c) {
        return c.tagName === node.tagName;
      });
      var index = siblings.indexOf(node) + 1;
      parts.unshift(tag + '[' + index + ']');
    }
    node = parent;
  }
  return parts.length > 0 ? '//' + parts.join('/') : '';
}

// Main Locator Generation
function genLocs(el) {
  var locs = [];
  
  if (cfg.framework === 'selenium') {
    return genSeleniumLocs(el);
  }
  
  // getByRole
  var role = getAriaRole(el);
  if (role) {
    var name = getName(el);
    if (name) {
      locs.push({ t: 'getByRole', v: "getByRole('" + role + "', {name: '" + name + "'})", p: 4, n: 0, hit: false, actionable: false });
      locs.push({ t: 'getByRole', v: "getByRole('" + role + "', {name: '" + name + "', exact: true})", p: 5, n: 0, hit: false, actionable: false });
    }
    locs.push({ t: 'getByRole', v: "getByRole('" + role + "')", p: 10, n: 0, hit: false, actionable: false });
  }
  
  // getByText
  var text = el.textContent ? el.textContent.trim() : '';
  if (text && text.length < 100 && !isDynamicText(text)) {
    locs.push({ t: 'getByText', v: "getByText('" + text + "')", p: 5, n: 0, hit: false, actionable: false });
    locs.push({ t: 'getByText', v: "getByText('" + text + "', {exact: true})", p: 10, n: 0, hit: false, actionable: false });
  }
  
  // getByLabel (for inputs)
  if (el.tagName === 'INPUT' || el.tagName === 'TEXTAREA' || el.tagName === 'SELECT') {
    var labelText = getName(el);
    if (labelText) {
      locs.push({ t: 'getByLabel', v: "getByLabel('" + labelText + "')", p: 10, n: 0, hit: false, actionable: false });
      locs.push({ t: 'getByLabel', v: "getByLabel('" + labelText + "', {exact: true})", p: 10, n: 0, hit: false, actionable: false });
    }
  }
  
  // getByPlaceholder
  var placeholder = el.getAttribute ? el.getAttribute('placeholder') : null;
  if (placeholder) {
    locs.push({ t: 'getByPlaceholder', v: "getByPlaceholder('" + placeholder + "')", p: 2, n: 0, hit: false, actionable: false });
    locs.push({ t: 'getByPlaceholder', v: "getByPlaceholder('" + placeholder + "', {exact: true})", p: 2, n: 0, hit: false, actionable: false });
  }
  
  // getByTestid
  var testid = el.getAttribute('data-testid') || el.getAttribute('data-test-id') || el.getAttribute('data-test') || el.getAttribute('data-cy') || el.getAttribute('data-pw');
  if (testid) {
    locs.push({ t: 'getByTestid', v: "getByTestId('" + testid + "')", p: 6, n: 0, hit: false, actionable: false });
  }
  
  // CSS
  var css = buildCssUnique(el);
  if (css) {
    locs.push({ t: 'CSS', v: "locator('" + css + "')", p: 8, n: 0, hit: false, actionable: false });
  }
  
  var cssAttr = buildCssAttr(el);
  if (cssAttr) {
    locs.push({ t: 'CSS', v: "locator('" + cssAttr + "')", p: 8, n: 0, hit: false, actionable: false });
  }
  
  // XPath
  var xp = buildXPUnique(el);
  if (xp) {
    locs.push({ t: 'XPath', v: "locator('" + xp + "')", p: 9, n: 0, hit: false, actionable: false });
  }
  
  var idXp = buildIdXP(el);
  if (idXp) {
    locs.push({ t: 'XPath', v: "locator('" + idXp + "')", p: 9, n: 0, hit: false, actionable: false });
  }
  
  // Verify and score
  for (var i = 0; i < locs.length; i++) {
    var resolved = resolveAny(locs[i].v);
    locs[i].n = resolved.length;
    locs[i].hit = resolved.includes(el);
    locs[i].actionable = isActionable(el);
  }
  
  return locs;
}

// Dynamic text detection
function isDynamicText(text) {
  if (!text) return false;
  // Numbers with separators
  if (/^\d[\d,]+$/.test(text)) return true;
  // Timestamps
  if (/\d{1,2}:\d{2}/.test(text)) return true;
  // Dates
  if (/\d{4}-\d{2}-\d{2}/.test(text)) return true;
  // Currency
  if (/^\$\d+/.test(text)) return true;
  // Status counts
  if (/^\d+ items?$/.test(text)) return true;
  // Pure numbers
  if (/^\d+$/.test(text)) return true;
  return false;
}

// Actionable check
function isActionable(el) {
  if (!el) return false;
  var rect = el.getBoundingClientRect();
  if (rect.width === 0 || rect.height === 0) return false;
  var style = window.getComputedStyle(el);
  if (style.display === 'none' || style.visibility === 'hidden') return false;
  if (style.pointerEvents === 'none') return false;
  if (el.disabled) return false;
  return true;
}

// Locator Resolution
function resolveAny(sel) {
  if (!sel) return [];
  
  // Playwright locator parsing
  if (sel.startsWith('page.')) {
    return resolvePwLocatorDeep(sel);
  }
  
  // XPath
  if (sel.startsWith('//')) {
    try {
      var result = document.evaluate(sel, document, null, XPathResult.ORDERED_NODE_SNAPSHOT_TYPE, null);
      var els = [];
      for (var i = 0; i < result.snapshotLength; i++) {
        els.push(result.snapshotItem(i));
      }
      return els;
    } catch (e) {
      return [];
    }
  }
  
  // CSS
  try {
    return Array.from(document.querySelectorAll(sel));
  } catch (e) {
    return [];
  }
}

function resolvePwLocatorDeep(sel) {
  // Parse Playwright locator
  if (sel.includes('getByTestId')) {
    var match = sel.match(/getByTestId\(['"]([^'"]+)['"]\)/);
    if (match) {
      var attrs = ['data-testid', 'data-test-id', 'data-test', 'data-cy', 'data-pw', 'data-e2e', 'data-qa'];
      var results = [];
      for (var i = 0; i < attrs.length; i++) {
        var found = document.querySelectorAll('[' + attrs[i] + '="' + match[1] + '"]');
        for (var j = 0; j < found.length; j++) {
          results.push(found[j]);
        }
      }
      return results;
    }
  }
  
  if (sel.includes('getByRole')) {
    var roleMatch = sel.match(/getByRole\(['"]([^'"]+)['"]/);
    if (roleMatch) {
      var role = roleMatch[1];
      var results = [];
      var all = document.querySelectorAll('*');
      for (var k = 0; k < all.length; k++) {
        if (getAriaRole(all[k]) === role) {
          results.push(all[k]);
        }
      }
      return results;
    }
  }
  
  if (sel.includes('getByText')) {
    var textMatch = sel.match(/getByText\(['"]([^'"]+)['"]/);
    if (textMatch) {
      var text = textMatch[1];
      var results = [];
      var all = document.querySelectorAll('*');
      for (var l = 0; l < all.length; l++) {
        if (all[l].textContent && all[l].textContent.trim() === text) {
          results.push(all[l]);
        }
      }
      return results;
    }
  }
  
  // Default to CSS
  var cssMatch = sel.match(/locator\(['"]([^'"]+)['"]\)/);
  if (cssMatch) {
    try {
      return Array.from(document.querySelectorAll(cssMatch[1]));
    } catch (e) {
      return [];
    }
  }
  
  return [];
}

// Selenium Locator Generation
function genSeleniumLocs(el) {
  var locs = [];
  
  // By.id
  if (el.id && stableId(el.id)) {
    locs.push({ t: 'By.id', v: "By.id('" + el.id + "')", p: 65, n: 0, hit: false, actionable: false });
  }
  
  // By.name
  var name = el.getAttribute('name');
  if (name) {
    locs.push({ t: 'By.name', v: "By.name('" + name + "')", p: 60, n: 0, hit: false, actionable: false });
  }
  
  // By.className
  if (el.classList && el.classList.length > 0) {
    var goodClasses = [];
    for (var i = 0; i < el.classList.length; i++) {
      if (goodCls(el.classList[i])) {
        goodClasses.push(el.classList[i]);
      }
    }
    if (goodClasses.length > 0) {
      locs.push({ t: 'By.className', v: "By.className('" + goodClasses[0] + "')", p: 50, n: 0, hit: false, actionable: false });
    }
  }
  
  // By.tagName
  locs.push({ t: 'By.tagName', v: "By.tagName('" + el.tagName.toLowerCase() + "')", p: 30, n: 0, hit: false, actionable: false });
  
  // By.cssSelector
  var css = buildCssUnique(el);
  if (css) {
    locs.push({ t: 'By.cssSelector', v: "By.cssSelector('" + css + "')", p: 40, n: 0, hit: false, actionable: false });
  }
  
  // By.xpath
  var xp = buildXPUnique(el);
  if (xp) {
    locs.push({ t: 'By.xpath', v: "By.xpath('" + xp + "')", p: 10, n: 0, hit: false, actionable: false });
  }
  
  // Verify
  for (var j = 0; j < locs.length; j++) {
    var resolved = resolveAny(locs[j].v);
    locs[j].n = resolved.length;
    locs[j].hit = resolved.includes(el);
    locs[j].actionable = isActionable(el);
  }
  
  return locs;
}

// Overlay System
function createHighlightBox() {
  if (highlightBox) return;
  highlightBox = document.createElement('div');
  highlightBox.style.cssText = 'position:fixed;z-index:2147483646;pointer-events:none;border:2px solid #22c55e;background:rgba(34,197,94,0.12);display:none;';
  document.body.appendChild(highlightBox);
}

function createTooltipBox() {
  if (tooltipBox) return;
  tooltipBox = document.createElement('div');
  tooltipBox.style.cssText = 'position:fixed;z-index:2147483647;pointer-events:none;background:#1e1e2e;color:#a5f3fc;font-family:monospace;font-size:11px;padding:4px 8px;border-radius:4px;white-space:pre;display:none;';
  document.body.appendChild(tooltipBox);
}

function updateHighlight(el) {
  if (!el) {
    if (highlightBox) highlightBox.style.display = 'none';
    if (tooltipBox) tooltipBox.style.display = 'none';
    return;
  }
  
  createHighlightBox();
  createTooltipBox();
  
  var rect = el.getBoundingClientRect();
  highlightBox.style.left = rect.left + 'px';
  highlightBox.style.top = rect.top + 'px';
  highlightBox.style.width = rect.width + 'px';
  highlightBox.style.height = rect.height + 'px';
  highlightBox.style.display = 'block';
  
  var locs = genLocs(el);
  var bestLoc = locs.length > 0 ? locs[0].v : 'No locators';
  tooltipBox.textContent = bestLoc;
  tooltipBox.style.left = (rect.left + 10) + 'px';
  tooltipBox.style.top = (rect.top + rect.height + 15) + 'px';
  tooltipBox.style.display = 'block';
}

// Picker Handlers
function onPickMove(e) {
  if (!isPicking) return;
  
  var target = e.composedPath ? e.composedPath()[0] : e.target;
  if (target.nodeType === Node.TEXT_NODE) {
    target = target.parentElement;
  }
  
  target = escapeSvgTarget(target);
  
  if (target === hoverEl) return;
  hoverEl = target;
  
  if (hoverTimer) clearTimeout(hoverTimer);
  hoverTimer = setTimeout(function() {
    updateHighlight(target);
  }, 50);
}

function doPickClick(e) {
  if (!isPicking) return;
  e.preventDefault();
  e.stopPropagation();
  
  var target = e.composedPath ? e.composedPath()[0] : e.target;
  if (target.nodeType === Node.TEXT_NODE) {
    target = target.parentElement;
  }
  
  target = escapeSvgTarget(target);
  lastPickedEl = target;
  
  var locs = genLocs(target);
  lastGenLocs = locs;
  
  var elementHtml = target.outerHTML ? target.outerHTML.substring(0, 3000) : '';
  
  if (window.electronBridge) {
    window.electronBridge.send('locators-picked', {
      locators: locs,
      element: {
        tag: target.tagName,
        id: target.id || null,
        text: target.textContent ? target.textContent.substring(0, 100) : null,
        classes: target.className || null
      },
      elementHtml: elementHtml
    });
  }
  
  stopPicking();
}

function startPicking() {
  isPicking = true;
  document.addEventListener('mousemove', onPickMove, true);
  document.addEventListener('click', doPickClick, true);
  document.addEventListener('pointerdown', onPickPointerDown, true);
  findAndAttachShadowRoots([
    { event: 'mousemove', handler: onPickMove },
    { event: 'click', handler: doPickClick },
    { event: 'pointerdown', handler: onPickPointerDown }
  ]);
}

function stopPicking() {
  isPicking = false;
  document.removeEventListener('mousemove', onPickMove, true);
  document.removeEventListener('click', doPickClick, true);
  document.removeEventListener('pointerdown', onPickPointerDown, true);
  detachShadowListeners();
  updateHighlight(null);
  hoverEl = null;
}

function onPickPointerDown(e) {
  // Placeholder for pointerdown handling
}

// Recording Handlers
function onRC(e) {
  if (!isRecording) return;
  if (isDuplicateEvent(e)) return;
  
  var target = e.composedPath ? e.composedPath()[0] : e.target;
  if (target.nodeType === Node.TEXT_NODE) {
    target = target.parentElement;
  }
  
  target = escapeSvgTarget(target);
  flushInput();
  
  // Skip checkboxes (handled separately)
  if (target.tagName === 'INPUT' && target.type === 'checkbox') {
    var checked = target.checked;
    var locs = genLocs(target);
    var bestLoc = locs.length > 0 ? locs[0] : null;
    if (bestLoc) {
      recordedActions.push({
        type: checked ? 'check' : 'uncheck',
        loc: bestLoc.v,
        confidence: bestLoc.hit && bestLoc.n === 1 ? 'high' : 'medium'
      });
      notifyRecordedAction();
    }
    return;
  }
  
  // Skip inputs (handled by onRI)
  if (target.tagName === 'INPUT' || target.tagName === 'TEXTAREA') {
    return;
  }
  
  var locs = genLocs(target);
  var bestLoc = locs.length > 0 ? locs[0] : null;
  
  if (bestLoc) {
    recordedActions.push({
      type: 'click',
      loc: bestLoc.v,
      confidence: bestLoc.hit && bestLoc.n === 1 ? 'high' : 'medium'
    });
    notifyRecordedAction();
  }
}

function onRI(e) {
  if (!isRecording) return;
  
  var target = e.target;
  
  if (target.tagName === 'SELECT') {
    flushInput();
    var locs = genLocs(target);
    var bestLoc = locs.length > 0 ? locs[0] : null;
    if (bestLoc) {
      recordedActions.push({
        type: 'select',
        loc: bestLoc.v,
        value: target.value,
        confidence: bestLoc.hit && bestLoc.n === 1 ? 'high' : 'medium'
      });
      notifyRecordedAction();
    }
    return;
  }
  
  if (target.tagName === 'INPUT' || target.tagName === 'TEXTAREA') {
    lastInputEl = target;
    lastInputValues = target.value;
    
    if (inputTimer) clearTimeout(inputTimer);
    inputTimer = setTimeout(flushInput, 800);
  }
}

function onRK(e) {
  if (!isRecording) return;
  
  if (e.key === 'Enter' || e.key === 'Tab' || e.key === 'Escape') {
    flushInput();
    
    var target = e.target;
    if (target.tagName === 'INPUT' || target.tagName === 'TEXTAREA') {
      var locs = genLocs(target);
      var bestLoc = locs.length > 0 ? locs[0] : null;
      if (bestLoc) {
        recordedActions.push({
          type: 'keypress',
          loc: bestLoc.v,
          key: e.key,
          confidence: bestLoc.hit && bestLoc.n === 1 ? 'high' : 'medium'
        });
        notifyRecordedAction();
      }
    }
  }
}

function flushInput() {
  if (!lastInputEl || !inputTimer) return;
  
  clearTimeout(inputTimer);
  inputTimer = null;
  
  var locs = genLocs(lastInputEl);
  var bestLoc = locs.length > 0 ? locs[0] : null;
  
  if (bestLoc && lastInputValues !== undefined) {
    recordedActions.push({
      type: 'fill',
      loc: bestLoc.v,
      value: lastInputValues,
      confidence: bestLoc.hit && bestLoc.n === 1 ? 'high' : 'medium'
    });
    notifyRecordedAction();
  }
  
  lastInputEl = null;
  lastInputValues = '';
}

function notifyRecordedAction() {
  if (window.electronBridge) {
    window.electronBridge.send('action-recorded', {
      actions: recordedActions,
      total: recordedActions.length
    });
  }
}

function startRecording() {
  isRecording = true;
  recordedActions = [];
  // Reset dedup state on start
  _lastEventTs = 0;
  _lastEventTarget = null;
  _lastEventType = null;
  // Only attach to document in capture phase - shadow events bubble up and are caught here
  document.addEventListener('click', onRC, true);
  document.addEventListener('input', onRI, true);
  document.addEventListener('change', onRI, true);
  document.addEventListener('keydown', onRK, true);
  // Attach to shadow roots ONLY for components that call stopImmediatePropagation
  findAndAttachShadowRootsForRecording();
}

function stopRecording() {
  isRecording = false;
  flushInput();
  document.removeEventListener('click', onRC, true);
  document.removeEventListener('input', onRI, true);
  document.removeEventListener('change', onRI, true);
  document.removeEventListener('keydown', onRK, true);
  detachShadowListeners();
  _lastEventTs = 0;
  _lastEventTarget = null;
  _lastEventType = null;
}

// Shadow Root Handling
var shadowRootsListened = new WeakSet();
var shadowRootsRecording = new WeakSet();
var shadowObservers = [];

// For picker: attach all events to shadow roots
function findAndAttachShadowRoots(handlers) {
  var all = document.querySelectorAll('*');
  for (var i = 0; i < all.length; i++) {
    var shadow = all[i].shadowRoot;
    if (shadow && !shadowRootsListened.has(shadow)) {
      attachShadowListeners(shadow, handlers);
      shadowRootsListened.add(shadow);
    }
  }
}

// For recorder: only attach click to shadow roots that block propagation.
// isDuplicateEvent dedup prevents double-recording for normal events.
var _shadowRecordHandlers = [
  { event: 'click', handler: null },
  { event: 'input', handler: null },
  { event: 'change', handler: null },
  { event: 'keydown', handler: null }
];

function findAndAttachShadowRootsForRecording() {
  _shadowRecordHandlers[0].handler = onRC;
  _shadowRecordHandlers[1].handler = onRI;
  _shadowRecordHandlers[2].handler = onRI;
  _shadowRecordHandlers[3].handler = onRK;
  var all = document.querySelectorAll('*');
  for (var i = 0; i < all.length; i++) {
    var shadow = all[i].shadowRoot;
    if (shadow && !shadowRootsRecording.has(shadow)) {
      for (var h = 0; h < _shadowRecordHandlers.length; h++) {
        shadow.addEventListener(_shadowRecordHandlers[h].event, _shadowRecordHandlers[h].handler, true);
      }
      shadowRootsRecording.add(shadow);
    }
  }
}

function attachShadowListeners(shadowRoot, handlers) {
  for (var i = 0; i < handlers.length; i++) {
    shadowRoot.addEventListener(handlers[i].event, handlers[i].handler, true);
  }
  
  // MutationObserver for dynamic shadow roots
  var observer = new MutationObserver(function(mutations) {
    for (var j = 0; j < mutations.length; j++) {
      for (var k = 0; k < mutations[j].addedNodes.length; k++) {
        var node = mutations[j].addedNodes[k];
        if (node.shadowRoot && !shadowRootsListened.has(node.shadowRoot)) {
          attachShadowListeners(node.shadowRoot, handlers);
          shadowRootsListened.add(node.shadowRoot);
        }
      }
    }
  });
  
  observer.observe(shadowRoot, { childList: true, subtree: true });
  shadowObservers.push(observer);
}

function detachShadowListeners() {
  for (var i = 0; i < shadowObservers.length; i++) {
    shadowObservers[i].disconnect();
  }
  shadowObservers = [];
}

// IPC Handlers
if (window.electronBridge) {
  window.electronBridge.on('cfg-change', function(config) {
    cfg = config;
  });
  
  window.electronBridge.on('start-pick', function() {
    startPicking();
  });
  
  window.electronBridge.on('stop-pick', function() {
    stopPicking();
  });
  
  window.electronBridge.on('start-rec', function() {
    startRecording();
  });
  
  window.electronBridge.on('stop-rec', function() {
    stopRecording();
  });
  
  window.electronBridge.on('play-recording', function(actions) {
    playRecording(actions);
  });
  
  window.electronBridge.on('stop-playback', function() {
    isPlaying = false;
  });
  
  window.electronBridge.on('test-locator', function(locator) {
    testLocator(locator);
  });
  
  window.electronBridge.on('clear-highlights', function() {
    clearHighlights();
  });
}

// Playback
function playRecording(actions) {
  isPlaying = true;
  var index = 0;
  
  function doNext() {
    if (!isPlaying || index >= actions.length) {
      isPlaying = false;
      return;
    }
    
    var action = actions[index];
    var els = resolveAny(action.loc);
    
    if (els.length > 0) {
      var el = els[0];
      el.scrollIntoView({ block: 'center', behavior: 'smooth' });
      
      setTimeout(function() {
        executeAction(el, action);
        index++;
        setTimeout(doNext, 1200);
      }, 500);
    } else {
      console.error('[PCS] Element not found:', action.loc);
      index++;
      setTimeout(doNext, 1200);
    }
  }
  
  doNext();
}

function executeAction(el, action) {
  switch (action.type) {
    case 'click':
      safeClick(el);
      break;
    case 'fill':
      if (el.value !== undefined) {
        el.value = action.value;
        el.dispatchEvent(new Event('input', { bubbles: true, composed: true }));
        el.dispatchEvent(new Event('change', { bubbles: true, composed: true }));
      }
      break;
    case 'check':
      if (!el.checked) el.click();
      break;
    case 'uncheck':
      if (el.checked) el.click();
      break;
    case 'select':
      el.value = action.value;
      el.dispatchEvent(new Event('change', { bubbles: true, composed: true }));
      break;
    case 'keypress':
      el.dispatchEvent(new KeyboardEvent('keydown', { key: action.key, bubbles: true, composed: true }));
      if (action.key === 'Enter') {
        el.form && el.form.submit();
      }
      break;
  }
}

function safeClick(el) {
  if (typeof el.click === 'function') {
    el.click();
  } else {
    el.dispatchEvent(new MouseEvent('click', { bubbles: true, cancelable: true, composed: true }));
  }
}

// Test Locator
function testLocator(locator) {
  var els = resolveAny(locator);
  var result = {
    locator: locator,
    count: els.length,
    matches: els.map(function(el) {
      return {
        tag: el.tagName,
        id: el.id || null,
        text: el.textContent ? el.textContent.substring(0, 50) : null
      };
    })
  };
  
  if (window.electronBridge) {
    window.electronBridge.send('test-result', result);
  }
  
  // Highlight matches
  clearHighlights();
  for (var i = 0; i < els.length; i++) {
    var rect = els[i].getBoundingClientRect();
    var overlay = document.createElement('div');
    overlay.style.cssText = 'position:fixed;z-index:2147483645;pointer-events:none;border:2px solid #f59e0b;background:rgba(245,158,11,0.1);';
    overlay.style.left = rect.left + 'px';
    overlay.style.top = rect.top + 'px';
    overlay.style.width = rect.width + 'px';
    overlay.style.height = rect.height + 'px';
    document.body.appendChild(overlay);
    hlOverlays.push(overlay);
  }
}

function clearHighlights() {
  for (var i = 0; i < hlOverlays.length; i++) {
    hlOverlays[i].remove();
  }
  hlOverlays = [];
}

// Initialize
console.log('[PCS] Content script loaded');
