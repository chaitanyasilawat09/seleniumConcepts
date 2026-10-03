# Accessibility Testing - Complete Interview Preparation Guide

---

## TABLE OF CONTENTS
1. Accessibility Testing Fundamentals
2. WCAG Guidelines
3. Accessibility Testing Types
4. Screen Reader Testing
5. Keyboard Navigation Testing
6. Color Contrast Testing
7. Alt Text Testing
8. Form Accessibility Testing
9. ARIA Attributes
10. Accessibility Testing Tools
11. Automated Accessibility Testing
12. Manual Accessibility Testing
13. Mobile Accessibility Testing
14. Accessibility Testing in CI/CD
15. Accessibility Testing Best Practices
16. Common Accessibility Issues
17. Accessibility Compliance
18. Accessibility Testing Process
19. Accessibility Reporting
20. Common Interview Questions
21. Scenario Examples
22. Practice Scenarios

---

## 1. ACCESSIBILITY TESTING FUNDAMENTALS

### What is Accessibility Testing?
Accessibility testing ensures that web and mobile applications are usable by people with disabilities, including visual, auditory, motor, and cognitive impairments.

### Accessibility Principles

**Principles (POUR):**
- **Perceivable**: Information must be presentable to users
- **Operable**: Interface components must be operable
- **Understandable**: Information and operation must be understandable
- **Robust**: Content must be robust enough to be interpreted

### Target Users

**Users with disabilities:**
- Visual impairments (blindness, low vision, color blindness)
- Hearing impairments (deafness, hard of hearing)
- Motor impairments (limited mobility, tremors)
- Cognitive impairments (dyslexia, ADHD, autism)

### Legal Requirements

**Laws:**
- ADA (Americans with Disabilities Act)
- Section 508
- EN 301 549
- WCAG 2.1

### Interview Questions

**Q1: What is accessibility testing?**
- Ensures applications are usable by people with disabilities
- Tests for visual, auditory, motor, cognitive impairments
- Follows WCAG guidelines
- Ensures legal compliance

**Q2: What are the POUR principles?**
- Perceivable: Information must be presentable
- Operable: Components must be operable
- Understandable: Information must be understandable
- Robust: Content must be robust

**Q3: What are common disabilities to consider?**
- Visual impairments
- Hearing impairments
- Motor impairments
- Cognitive impairments

---

## 2. WCAG GUIDELINES

### What is WCAG?
Web Content Accessibility Guidelines (WCAG) are internationally recognized standards for web accessibility. WCAG 2.1 is the current version.

### WCAG Levels

**Levels:**
- **A**: Minimum accessibility
- **AA**: Standard accessibility (recommended)
- **AAA**: Enhanced accessibility

### WCAG Guidelines

**Guidelines:**
1. **Perceivable**: Text alternatives, time-based media, adaptable, distinguishable
2. **Operable**: Keyboard accessible, enough time, seizures, navigable
3. **Understandable**: Readable, predictable, input assistance
4. **Robust**: Compatible, future-proof

### Interview Questions

**Q1: What is WCAG?**
- Web Content Accessibility Guidelines
- International standards
- WCAG 2.1 current version
- Defines accessibility requirements

**Q2: What are WCAG levels?**
- A: Minimum accessibility
- AA: Standard accessibility (recommended)
- AAA: Enhanced accessibility

**Q3: What are the four WCAG principles?**
- Perceivable: Information presentable
- Operable: Components operable
- Understandable: Information understandable
- Robust: Content robust

---

## 3. ACCESSIBILITY TESTING TYPES

### Automated Testing

**Purpose:**
- Quick accessibility checks
- Identify common issues
- Continuous monitoring
- CI/CD integration

### Manual Testing

**Purpose:**
- Test with assistive technologies
- Test keyboard navigation
- Test color contrast
- Test user experience

### User Testing

**Purpose:**
- Test with real users with disabilities
- Get real feedback
- Identify usability issues
- Validate accessibility

### Interview Questions

**Q1: What are the types of accessibility testing?**
- Automated: Quick checks, CI/CD
- Manual: Assistive technologies, keyboard
- User testing: Real users with disabilities

**Q2: What is automated accessibility testing?**
- Quick accessibility checks
- Identify common issues
- Continuous monitoring
- CI/CD integration

**Q3: What is manual accessibility testing?**
- Test with assistive technologies
- Test keyboard navigation
- Test color contrast
- Test user experience

---

## 4. SCREEN READER TESTING

### What are Screen Readers?
Screen readers are software that reads aloud the text displayed on a screen, enabling blind or visually impaired users to use computers.

### Common Screen Readers

**Screen Readers:**
- **NVDA**: Windows, free
- **JAWS**: Windows, commercial
- **VoiceOver**: macOS, iOS
- **TalkBack**: Android
- **Narrator**: Windows

### Screen Reader Testing

**Testing:**
- Navigate with screen reader
- Verify alt text is read
- Verify headings are announced
- Verify form labels are read
- Verify links are descriptive

### Interview Questions

**Q1: What are screen readers?**
- Software that reads screen text
- Enables blind users to use computers
- Announces content and navigation
- Essential for accessibility

**Q2: What are common screen readers?**
- NVDA: Windows, free
- JAWS: Windows, commercial
- VoiceOver: macOS, iOS
- TalkBack: Android

**Q3: How do you test with screen readers?**
- Navigate with screen reader
- Verify alt text is read
- Verify headings are announced
- Verify form labels are read

---

## 5. KEYBOARD NAVIGATION TESTING

### Keyboard Navigation

**Purpose:**
- Test all functionality is accessible via keyboard
- Test tab order is logical
- Test focus indicators are visible
- Test keyboard shortcuts

### Keyboard Testing

**Testing:**
- Navigate with Tab key
- Verify tab order is logical
- Verify focus indicators are visible
- Test Enter key for actions
- Test Escape key for closing modals

### Focus Management

**Management:**
- Focus trap in modals
- Focus return after modal close
- Skip navigation links
- Focus visible indicators

### Interview Questions

**Q1: What is keyboard navigation testing?**
- Test all functionality accessible via keyboard
- Test tab order is logical
- Test focus indicators are visible
- Test keyboard shortcuts

**Q2: How do you test keyboard navigation?**
- Navigate with Tab key
- Verify tab order is logical
- Verify focus indicators are visible
- Test Enter and Escape keys

**Q3: What is focus management?**
- Focus trap in modals
- Focus return after modal close
- Skip navigation links
- Focus visible indicators

---

## 6. COLOR CONTRAST TESTING

### Color Contrast

**Purpose:**
- Ensure text is readable
- Ensure sufficient contrast
- Meet WCAG AA standards
- Support color blindness

### WCAG Contrast Requirements

**Requirements:**
- **AA Level**: 4.5:1 for normal text, 3:1 for large text
- **AAA Level**: 7:1 for normal text, 4.5:1 for large text

### Color Contrast Testing

**Testing:**
- Use contrast checker tools
- Test text and background colors
- Test interactive elements
- Test color combinations

### Interview Questions

**Q1: What is color contrast testing?**
- Ensure text is readable
- Ensure sufficient contrast
- Meet WCAG AA standards
- Support color blindness

**Q2: What are WCAG contrast requirements?**
- AA: 4.5:1 for normal text, 3:1 for large text
- AAA: 7:1 for normal text, 4.5:1 for large text

**Q3: How do you test color contrast?**
- Use contrast checker tools
- Test text and background colors
- Test interactive elements
- Test color combinations

---

## 7. ALT TEXT TESTING

### Alt Text

**Purpose:**
- Provide text alternative for images
- Describe image content
- Enable screen readers to announce images
- Support users who can't see images

### Alt Text Guidelines

**Guidelines:**
- Descriptive alt text for meaningful images
- Empty alt text (alt="") for decorative images
- Concise and accurate descriptions
- Include context if needed

### Alt Text Testing

**Testing:**
- Verify all images have alt text
- Verify alt text is descriptive
- Verify decorative images have empty alt
- Verify screen readers announce alt text

### Interview Questions

**Q1: What is alt text?**
- Text alternative for images
- Describes image content
- Enables screen readers to announce images
- Supports users who can't see images

**Q2: What are alt text guidelines?**
- Descriptive for meaningful images
- Empty (alt="") for decorative images
- Concise and accurate
- Include context if needed

**Q3: How do you test alt text?**
- Verify all images have alt text
- Verify alt text is descriptive
- Verify decorative images have empty alt
- Verify screen readers announce alt text

---

## 8. FORM ACCESSIBILITY TESTING

### Form Accessibility

**Purpose:**
- Ensure forms are accessible
- Ensure form labels are associated
- Ensure error messages are accessible
- Ensure form validation is accessible

### Form Testing

**Testing:**
- Verify form labels are associated with inputs
- Verify required fields are indicated
- Verify error messages are accessible
- Verify form validation is announced
- Verify form submission is accessible

### Form Best Practices

**Practices:**
- Use label elements for form labels
- Use aria-label for icon buttons
- Use aria-describedby for help text
- Use aria-invalid for errors
- Use fieldset and legend for groups

### Interview Questions

**Q1: What is form accessibility testing?**
- Ensure forms are accessible
- Ensure form labels are associated
- Ensure error messages are accessible
- Ensure form validation is accessible

**Q2: How do you test form accessibility?**
- Verify form labels are associated with inputs
- Verify required fields are indicated
- Verify error messages are accessible
- Verify form validation is announced

**Q3: What are form accessibility best practices?**
- Use label elements for form labels
- Use aria-label for icon buttons
- Use aria-describedby for help text
- Use aria-invalid for errors

---

## 9. ARIA ATTRIBUTES

### What is ARIA?
Accessible Rich Internet Applications (ARIA) is a set of attributes that define ways to make web content and web applications more accessible to people with disabilities.

### Common ARIA Attributes

**Attributes:**
- **aria-label**: Provides label for elements
- **aria-labelledby**: References element that labels
- **aria-describedby**: References element that describes
- **aria-hidden**: Hides element from screen readers
- **aria-expanded**: Indicates expanded state
- **aria-current**: Indicates current item

### ARIA Roles

**Roles:**
- **role="button"**: Button behavior
- **role="navigation"**: Navigation landmark
- **role="main"**: Main content
- **role="alert"**: Alert message
- **role="dialog"**: Dialog window

### Interview Questions

**Q1: What is ARIA?**
- Accessible Rich Internet Applications
- Set of attributes for accessibility
- Makes web content accessible
- Defines roles, states, properties

**Q2: What are common ARIA attributes?**
- aria-label: Provides label
- aria-labelledby: References label
- aria-describedby: References description
- aria-hidden: Hides from screen readers

**Q3: What are common ARIA roles?**
- role="button": Button behavior
- role="navigation": Navigation landmark
- role="main": Main content
- role="alert": Alert message

---

## 10. ACCESSIBILITY TESTING TOOLS

### Automated Tools

**Tools:**
- **axe DevTools**: Browser extension
- **WAVE**: Web accessibility evaluation tool
- **Lighthouse**: Chrome audit tool
- **Pa11y**: Command-line tool
- **Axe-core**: JavaScript library

### Screen Readers

**Tools:**
- **NVDA**: Windows, free
- **JAWS**: Windows, commercial
- **VoiceOver**: macOS, iOS
- **TalkBack**: Android

### Color Contrast Tools

**Tools:**
- **WebAIM Contrast Checker**: Online tool
- **Colour Contrast Analyser**: Desktop tool
- **Contrast Checker**: Browser extension

### Interview Questions

**Q1: What are automated accessibility testing tools?**
- axe DevTools: Browser extension
- WAVE: Web accessibility evaluation tool
- Lighthouse: Chrome audit tool
- Pa11y: Command-line tool

**Q2: What are screen reader tools?**
- NVDA: Windows, free
- JAWS: Windows, commercial
- VoiceOver: macOS, iOS
- TalkBack: Android

**Q3: What are color contrast tools?**
- WebAIM Contrast Checker: Online tool
- Colour Contrast Analyser: Desktop tool
- Contrast Checker: Browser extension

---

## 11. AUTOMATED ACCESSIBILITY TESTING

### Automated Testing Setup

**Setup:**
```javascript
// Using axe-core
const { AxePuppeteer } = require('@axe-core/puppeteer');

(async () => {
  const results = await new AxePuppeteer(page).analyze();
  console.log(results);
})();
```

### CI/CD Integration

**Integration:**
- Run axe in CI pipeline
- Fail on accessibility violations
- Generate accessibility reports
- Monitor accessibility over time

### Automated Testing Limitations

**Limitations:**
- Cannot test user experience
- Cannot test with assistive technologies
- May produce false positives
- Cannot test context

### Interview Questions

**Q1: How do you set up automated accessibility testing?**
- Use axe-core library
- Integrate with test framework
- Run in CI/CD pipeline
- Generate reports

**Q2: How do you integrate accessibility testing in CI/CD?**
- Run axe in CI pipeline
- Fail on accessibility violations
- Generate accessibility reports
- Monitor accessibility over time

**Q3: What are automated testing limitations?**
- Cannot test user experience
- Cannot test with assistive technologies
- May produce false positives
- Cannot test context

---

## 12. MANUAL ACCESSIBILITY TESTING

### Manual Testing Checklist

**Checklist:**
- [ ] Keyboard navigation works
- [ ] Screen reader announces content
- [ ] Color contrast meets WCAG
- [ ] Alt text is descriptive
- [ ] Form labels are associated
- [ ] Focus indicators are visible
- [ ] Skip navigation works
- [ ] Error messages are accessible

### Manual Testing Process

**Process:**
1. Test keyboard navigation
2. Test with screen reader
3. Test color contrast
4. Test forms
5. Test dynamic content
6. Test mobile accessibility

### Manual Testing Tools

**Tools:**
- Screen readers
- Keyboard
- Color contrast checker
- Browser developer tools
- Accessibility inspector

### Interview Questions

**Q1: What is a manual accessibility testing checklist?**
- Keyboard navigation works
- Screen reader announces content
- Color contrast meets WCAG
- Alt text is descriptive
- Form labels are associated

**Q2: What is the manual testing process?**
- Test keyboard navigation
- Test with screen reader
- Test color contrast
- Test forms
- Test dynamic content

**Q3: What tools are used for manual testing?**
- Screen readers
- Keyboard
- Color contrast checker
- Browser developer tools
- Accessibility inspector

---

## 13. MOBILE ACCESSIBILITY TESTING

### Mobile Accessibility

**Considerations:**
- Touch targets size
- Screen reader on mobile
- Keyboard support
- Orientation support
- Zoom and scaling

### Mobile Testing Tools

**Tools:**
- **VoiceOver**: iOS screen reader
- **TalkBack**: Android screen reader
- **Accessibility Inspector**: iOS
- **Accessibility Scanner**: Android

### Mobile Testing Checklist

**Checklist:**
- [ ] Touch targets are 44x44 pixels minimum
- [ ] Screen reader works
- [ ] Keyboard support works
- [ ] Orientation is supported
- [ ] Zoom and scaling works

### Interview Questions

**Q1: What are mobile accessibility considerations?**
- Touch targets size
- Screen reader on mobile
- Keyboard support
- Orientation support
- Zoom and scaling

**Q2: What are mobile accessibility testing tools?**
- VoiceOver: iOS screen reader
- TalkBack: Android screen reader
- Accessibility Inspector: iOS
- Accessibility Scanner: Android

**Q3: What is the mobile accessibility testing checklist?**
- Touch targets are 44x44 pixels minimum
- Screen reader works
- Keyboard support works
- Orientation is supported

---

## 14. ACCESSIBILITY TESTING IN CI/CD

### CI/CD Integration

**Integration:**
- Run automated tests in CI
- Fail on accessibility violations
- Generate accessibility reports
- Monitor accessibility metrics

### Accessibility Gates

**Gates:**
- Block on critical violations
- Block on serious violations
- Warn on moderate violations
- Monitor minor violations

### CI/CD Tools

**Tools:**
- axe-core: Automated testing
- Lighthouse CI: Chrome audit
- Pa11y CI: Command-line tool
- Custom scripts

### Interview Questions

**Q1: How do you integrate accessibility testing in CI/CD?**
- Run automated tests in CI
- Fail on accessibility violations
- Generate accessibility reports
- Monitor accessibility metrics

**Q2: What are accessibility gates in CI/CD?**
- Block on critical violations
- Block on serious violations
- Warn on moderate violations
- Monitor minor violations

**Q3: What tools are used for CI/CD accessibility testing?**
- axe-core: Automated testing
- Lighthouse CI: Chrome audit
- Pa11y CI: Command-line tool
- Custom scripts

---

## 15. ACCESSIBILITY TESTING BEST PRACTICES

### Test Early

**Practice:**
- Start accessibility testing early
- Test during development
- Test in CI/CD
- Continuous accessibility testing

### Test with Real Users

**Practice:**
- Test with users with disabilities
- Get real feedback
- Identify usability issues
- Validate accessibility

### Use Multiple Tools

**Practice:**
- Use automated tools
- Use manual testing
- Use screen readers
- Use color contrast checkers

### Interview Questions

**Q1: What are accessibility testing best practices?**
- Test early
- Test with real users
- Use multiple tools
- Document findings

**Q2: Why test accessibility early?**
- Identify issues early
- Cheaper to fix
- Prevent production issues
- Build accessibility in

**Q3: Why test with real users?**
- Get real feedback
- Identify usability issues
- Validate accessibility
- Understand user needs

---

## 16. COMMON ACCESSIBILITY ISSUES

### Common Issues

**Issues:**
- Missing alt text
- Poor color contrast
- Missing form labels
- No keyboard support
- Missing focus indicators
- Non-descriptive links
- Missing skip navigation

### Issue Severity

**Severity:**
- **Critical**: Blocks accessibility
- **Serious**: Significantly impacts accessibility
- **Moderate**: Partially impacts accessibility
- **Minor**: Minimally impacts accessibility

### Issue Resolution

**Resolution:**
- Prioritize critical issues
- Fix serious issues
- Address moderate issues
- Monitor minor issues

### Interview Questions

**Q1: What are common accessibility issues?**
- Missing alt text
- Poor color contrast
- Missing form labels
- No keyboard support
- Missing focus indicators

**Q2: How do you prioritize accessibility issues?**
- Critical: Blocks accessibility
- Serious: Significantly impacts
- Moderate: Partially impacts
- Minor: Minimally impacts

**Q3: How do you resolve accessibility issues?**
- Prioritize critical issues
- Fix serious issues
- Address moderate issues
- Monitor minor issues

---

## 17. ACCESSIBILITY COMPLIANCE

### Compliance Standards

**Standards:**
- **WCAG 2.1 AA**: Recommended standard
- **Section 508**: US federal standard
- **EN 301 549**: European standard
- **ADA**: US legal requirement

### Compliance Testing

**Testing:**
- Automated testing
- Manual testing
- User testing
- Third-party audit

### Compliance Reporting

**Reporting:**
- Accessibility statement
- VPAT (Voluntary Product Accessibility Template)
- ACR (Accessibility Conformance Report)
- Compliance certificate

### Interview Questions

**Q1: What are accessibility compliance standards?**
- WCAG 2.1 AA: Recommended standard
- Section 508: US federal standard
- EN 301 549: European standard
- ADA: US legal requirement

**Q2: How do you test for compliance?**
- Automated testing
- Manual testing
- User testing
- Third-party audit

**Q3: What are compliance reporting documents?**
- Accessibility statement
- VPAT (Voluntary Product Accessibility Template)
- ACR (Accessibility Conformance Report)
- Compliance certificate

---

## 18. ACCESSIBILITY TESTING PROCESS

### Planning Phase

**Activities:**
- Define accessibility requirements
- Identify target users
- Select testing tools
- Plan testing approach

### Execution Phase

**Activities:**
- Run automated tests
- Perform manual testing
- Test with assistive technologies
- Test with real users

### Reporting Phase

**Activities:**
- Document findings
- Assess severity
- Provide recommendations
- Create remediation plan

### Interview Questions

**Q1: What are the phases of accessibility testing?**
- Planning: Define requirements
- Execution: Run tests
- Reporting: Document findings
- Remediation: Fix issues

**Q2: What happens in the planning phase?**
- Define accessibility requirements
- Identify target users
- Select testing tools
- Plan testing approach

**Q3: What happens in the reporting phase?**
- Document findings
- Assess severity
- Provide recommendations
- Create remediation plan

---

## 19. ACCESSIBILITY REPORTING

### Report Components

**Components:**
- Executive summary
- Testing scope
- Findings summary
- Detailed findings
- Recommendations
- Remediation timeline

### Report Format

**Format:**
- Executive summary for management
- Technical details for developers
- Screenshots for context
- Severity ratings for prioritization

### Report Distribution

**Distribution:**
- Management: Executive summary
- Development team: Technical details
- Design team: UI/UX findings
- Stakeholders: Full report

### Interview Questions

**Q1: What should be in an accessibility report?**
- Executive summary
- Testing scope
- Findings summary
- Detailed findings
- Recommendations

**Q2: How do you format accessibility reports?**
- Executive summary for management
- Technical details for developers
- Screenshots for context
- Severity ratings for prioritization

**Q3: How do you distribute accessibility reports?**
- Management: Executive summary
- Development team: Technical details
- Design team: UI/UX findings
- Stakeholders: Full report

---

## 20. COMMON INTERVIEW QUESTIONS

### Fundamentals

**Q1: What is accessibility testing?**
- Ensures applications are usable by people with disabilities
- Tests for visual, auditory, motor, cognitive impairments
- Follows WCAG guidelines
- Ensures legal compliance

**Q2: What are the POUR principles?**
- Perceivable: Information must be presentable
- Operable: Components must be operable
- Understandable: Information must be understandable
- Robust: Content must be robust

**Q3: What are common disabilities to consider?**
- Visual impairments
- Hearing impairments
- Motor impairments
- Cognitive impairments

### WCAG

**Q4: What is WCAG?**
- Web Content Accessibility Guidelines
- International standards
- WCAG 2.1 current version
- Defines accessibility requirements

**Q5: What are WCAG levels?**
- A: Minimum accessibility
- AA: Standard accessibility (recommended)
- AAA: Enhanced accessibility

**Q6: What are the four WCAG principles?**
- Perceivable: Information presentable
- Operable: Components operable
- Understandable: Information understandable
- Robust: Content robust

### Testing Types

**Q7: What are the types of accessibility testing?**
- Automated: Quick checks, CI/CD
- Manual: Assistive technologies, keyboard
- User testing: Real users with disabilities

**Q8: What is automated accessibility testing?**
- Quick accessibility checks
- Identify common issues
- Continuous monitoring
- CI/CD integration

**Q9: What is manual accessibility testing?**
- Test with assistive technologies
- Test keyboard navigation
- Test color contrast
- Test user experience

### Screen Readers

**Q10: What are screen readers?**
- Software that reads screen text
- Enables blind users to use computers
- Announces content and navigation
- Essential for accessibility

**Q11: What are common screen readers?**
- NVDA: Windows, free
- JAWS: Windows, commercial
- VoiceOver: macOS, iOS
- TalkBack: Android

**Q12: How do you test with screen readers?**
- Navigate with screen reader
- Verify alt text is read
- Verify headings are announced
- Verify form labels are read

### Keyboard Navigation

**Q13: What is keyboard navigation testing?**
- Test all functionality accessible via keyboard
- Test tab order is logical
- Test focus indicators are visible
- Test keyboard shortcuts

**Q14: How do you test keyboard navigation?**
- Navigate with Tab key
- Verify tab order is logical
- Verify focus indicators are visible
- Test Enter and Escape keys

**Q15: What is focus management?**
- Focus trap in modals
- Focus return after modal close
- Skip navigation links
- Focus visible indicators

### Color Contrast

**Q16: What is color contrast testing?**
- Ensure text is readable
- Ensure sufficient contrast
- Meet WCAG AA standards
- Support color blindness

**Q17: What are WCAG contrast requirements?**
- AA: 4.5:1 for normal text, 3:1 for large text
- AAA: 7:1 for normal text, 4.5:1 for large text

**Q18: How do you test color contrast?**
- Use contrast checker tools
- Test text and background colors
- Test interactive elements
- Test color combinations

### Alt Text

**Q19: What is alt text?**
- Text alternative for images
- Describes image content
- Enables screen readers to announce images
- Supports users who can't see images

**Q20: What are alt text guidelines?**
- Descriptive for meaningful images
- Empty (alt="") for decorative images
- Concise and accurate
- Include context if needed

**Q21: How do you test alt text?**
- Verify all images have alt text
- Verify alt text is descriptive
- Verify decorative images have empty alt
- Verify screen readers announce alt text

### ARIA

**Q22: What is ARIA?**
- Accessible Rich Internet Applications
- Set of attributes for accessibility
- Makes web content accessible
- Defines roles, states, properties

**Q23: What are common ARIA attributes?**
- aria-label: Provides label
- aria-labelledby: References label
- aria-describedby: References description
- aria-hidden: Hides from screen readers

**Q24: What are common ARIA roles?**
- role="button": Button behavior
- role="navigation": Navigation landmark
- role="main": Main content
- role="alert": Alert message

### Tools

**Q25: What are automated accessibility testing tools?**
- axe DevTools: Browser extension
- WAVE: Web accessibility evaluation tool
- Lighthouse: Chrome audit tool
- Pa11y: Command-line tool

**Q26: What are screen reader tools?**
- NVDA: Windows, free
- JAWS: Windows, commercial
- VoiceOver: macOS, iOS
- TalkBack: Android

**Q27: What are color contrast tools?**
- WebAIM Contrast Checker: Online tool
- Colour Contrast Analyser: Desktop tool
- Contrast Checker: Browser extension

### Best Practices

**Q28: What are accessibility testing best practices?**
- Test early
- Test with real users
- Use multiple tools
- Document findings

**Q29: Why test accessibility early?**
- Identify issues early
- Cheaper to fix
- Prevent production issues
- Build accessibility in

**Q30: Why test with real users?**
- Get real feedback
- Identify usability issues
- Validate accessibility
- Understand user needs

### Scenario-Based

**Q31: How do you design an accessibility testing strategy?**
- Define accessibility requirements
- Identify target users
- Select testing tools
- Plan testing approach
- Document findings

**Q32: How do you handle accessibility violations in production?**
- Assess severity
- Prioritize fixes
- Create remediation plan
- Communicate with stakeholders

**Q33: How do you ensure accessibility across the application lifecycle?**
- Test during development
- Test in CI/CD
- Test in staging
- Test in production
- Continuous monitoring

**Q34: How do you communicate accessibility findings to stakeholders?**
- Executive summary
- Technical details
- Recommendations
- Remediation timeline

**Q35: How do you ensure accessibility compliance?**
- Follow WCAG guidelines
- Test with automated tools
- Test with manual testing
- Test with real users
- Third-party audit

---

## CONCLUSION

This comprehensive guide covers all essential accessibility testing topics for interview preparation. Key takeaways:

1. **Accessibility Testing**: Ensures applications are usable by people with disabilities
2. **POUR Principles**: Perceivable, Operable, Understandable, Robust
3. **WCAG Guidelines**: International standards, levels A/AA/AAA
4. **Testing Types**: Automated, manual, user testing
5. **Screen Readers**: NVDA, JAWS, VoiceOver, TalkBack
6. **Keyboard Navigation**: Tab order, focus indicators, shortcuts
7. **Color Contrast**: WCAG requirements, contrast checkers
8. **Alt Text**: Descriptive, empty for decorative, screen reader announcement
9. **Form Accessibility**: Labels, error messages, validation
10. **ARIA**: Attributes, roles, states, properties
11. **Tools**: axe, WAVE, Lighthouse, Pa11y
12. **Automated Testing**: axe-core, CI/CD integration, limitations
13. **Manual Testing**: Checklist, process, tools
14. **Mobile Accessibility**: Touch targets, screen readers, orientation
15. **CI/CD**: Automated tests, gates, monitoring
16. **Best Practices**: Test early, test with users, use multiple tools
17. **Common Issues**: Missing alt text, poor contrast, missing labels
18. **Compliance**: WCAG, Section 508, EN 301 549, ADA
19. **Process**: Planning, execution, reporting, remediation
20. **Reporting**: Executive summary, technical details, recommendations

Practice these concepts with real accessibility testing projects and be prepared to explain the "why" behind each accessibility measure. Good luck with your interview!
