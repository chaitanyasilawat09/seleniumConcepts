# GitHub Actions & YAML CI/CD - Complete Interview Preparation Guide

---

## TABLE OF CONTENTS
1. GitHub Actions Fundamentals
2. GitHub Actions Architecture
3. Workflow File Structure (.yml)
4. YAML Syntax Basics
5. Workflow Triggers
6. Jobs and Steps
7. Runners (Self-hosted vs GitHub-hosted)
8. Environment Variables and Secrets
9. Actions and Marketplace
10. Matrix Strategy
11. Conditional Execution
12. Caching Dependencies
13. Artifacts and Storage
14. Maven Integration
15. TestNG Integration
16. Playwright Integration
17. Docker Integration
18. Deployment Strategies
19. Workflow Dispatch
20. Reusable Workflows
21. Composite Actions
22. Security Best Practices
23. GitHub Actions vs Jenkins
24. Common Interview Questions
25. Practice Workflows

---

## 1. GITHUB ACTIONS FUNDAMENTALS

### What is GitHub Actions?
GitHub Actions is a CI/CD platform that allows you to automate build, test, and deployment pipelines directly within your GitHub repository. It uses YAML files to define workflows.

### Key Features
- **YAML-based**: Simple, declarative syntax
- **Integrated with GitHub**: Native integration with repositories
- **Free for public repositories**: Generous free tier for private repos
- **Marketplace**: Thousands of pre-built actions
- **Self-hosted runners**: Run on your own infrastructure
- **Matrix builds**: Test across multiple configurations
- **Caching**: Speed up builds by caching dependencies
- **Secrets management**: Secure credential storage

### CI/CD with GitHub Actions

**Continuous Integration:**
- Automatically runs on push/PR
- Builds and tests code
- Provides feedback in PR checks

**Continuous Deployment:**
- Deploys to environments on merge
- Supports multiple environments (dev, staging, prod)
- Manual approval gates
- Rollback capabilities

### GitHub Actions vs Other CI/CD Tools

| Feature | GitHub Actions | Jenkins | GitLab CI | CircleCI |
|---------|---------------|---------|-----------|----------|
| YAML-based | ✅ | ❌ (Groovy) | ✅ | ✅ |
| Integrated with Git | ✅ Native | ❌ | ✅ Native | ❌ |
| Free Tier | ✅ Generous | ✅ Self-hosted | ✅ Limited | ❌ |
| Marketplace | ✅ Large | ✅ Plugins | ✅ | ❌ |
| Self-hosted | ✅ | ✅ | ✅ | ❌ |
| Learning Curve | 📈 Low | 📈 High | 📈 Low | 📈 Low |
| Docker Support | ✅ Native | ✅ Plugin | ✅ Native | ✅ |

### Interview Questions

**Q1: What is GitHub Actions and why is it used?**
- CI/CD platform by GitHub
- Automates build, test, deployment
- YAML-based workflow definitions
- Integrated with GitHub repositories

**Q2: What are the advantages of GitHub Actions over Jenkins?**
- YAML-based (simpler than Groovy)
- Native GitHub integration
- Built-in marketplace
- No server maintenance
- Free for public repos

**Q3: What is the difference between GitHub Actions and GitLab CI?**
- Both YAML-based
- GitHub Actions: GitHub native
- GitLab CI: GitLab native
- Similar syntax and features
- Choice depends on Git host

---

## 2. GITHUB ACTIONS ARCHITECTURE

### Workflow Architecture

```
┌─────────────────────────────────────┐
│         GitHub Repository           │
│  ┌───────────────────────────────┐  │
│  │   .github/workflows/           │  │
│  │   ├── build.yml               │  │
│  │   ├── test.yml                │  │
│  │   └── deploy.yml              │  │
│  └───────────────────────────────┘  │
└─────────────────────────────────────┘
              ↓ Trigger
┌─────────────────────────────────────┐
│         GitHub Actions              │
│  ┌───────────────────────────────┐  │
│  │   Workflow Dispatcher         │  │
│  │   Job Scheduler               │  │
│  │   Runner Manager              │  │
│  └───────────────────────────────┘  │
└─────────────────────────────────────┘
              ↓
┌─────────────────────────────────────┐
│         Runner (Hosted/Self)        │
│  ┌───────────────────────────────┐  │
│  │   Checkout Code               │  │
│  │   Setup Tools                 │  │
│  │   Run Steps                   │  │
│  │   Upload Artifacts            │  │
│  └───────────────────────────────┘  │
└─────────────────────────────────────┘
```

### Components

**Workflow:**
- Automated process defined in YAML file
- Contains one or more jobs
- Triggered by events

**Job:**
- Set of steps that run on same runner
- Can run sequentially or in parallel
- Shares environment with steps

**Step:**
- Individual command or action
- Runs shell commands or actions
- Can depend on previous steps

**Action:**
- Reusable unit of code
- Can be from marketplace or custom
- Can be JavaScript or Docker container

**Runner:**
- Server that runs workflows
- GitHub-hosted or self-hosted
- Runs jobs in isolated environment

### Workflow File Location
- `.github/workflows/` directory
- `.yml` or `.yaml` extension
- Can have multiple workflow files
- Automatically detected by GitHub

### Interview Questions

**Q1: Explain GitHub Actions architecture.**
- Workflows defined in YAML files
- Located in .github/workflows/
- Triggered by GitHub events
- Jobs run on runners (hosted/self-hosted)

**Q2: What are the components of GitHub Actions?**
- Workflow: Automated process
- Job: Set of steps
- Step: Individual command
- Action: Reusable code
- Runner: Execution environment

**Q3: Where are workflow files stored in GitHub?**
- .github/workflows/ directory
- .yml or .yaml extension
- Repository root
- Automatically detected

---

## 3. WORKFLOW FILE STRUCTURE (.YML)

### Basic Structure

```yaml
name: Workflow Name
on: [push, pull_request]
jobs:
  job-name:
    runs-on: ubuntu-latest
    steps:
      - name: Step Name
        run: echo "Hello World"
```

### Complete Structure

```yaml
# Workflow name
name: CI/CD Pipeline

# Triggers
on:
  push:
    branches: [ main, develop ]
  pull_request:
    branches: [ main ]
  workflow_dispatch:

# Environment variables
env:
  NODE_VERSION: '18'
  JAVA_VERSION: '11'

# Jobs
jobs:
  build:
    name: Build Job
    runs-on: ubuntu-latest
    
    # Job-level variables
    env:
      BUILD_ENV: production
    
    steps:
      - name: Checkout
        uses: actions/checkout@v4
      
      - name: Setup Node
        uses: actions/setup-node@v4
        with:
          node-version: ${{ env.NODE_VERSION }}
      
      - name: Build
        run: npm run build
  
  test:
    name: Test Job
    runs-on: ubuntu-latest
    needs: build
    
    steps:
      - name: Checkout
        uses: actions/checkout@v4
      
      - name: Run Tests
        run: npm test
```

### Key Elements

**name:**
- Workflow name
- Displayed in GitHub Actions tab
- Optional but recommended

**on:**
- Triggers for workflow
- Can be multiple events
- Can include filters

**env:**
- Global environment variables
- Available to all jobs and steps
- Can be overridden at job/step level

**jobs:**
- Collection of jobs
- Each job runs on runner
- Can have dependencies

**runs-on:**
- Runner type
- ubuntu-latest, windows-latest, macos-latest
- Self-hosted runner labels

**steps:**
- Sequence of steps
- Run in order
- Can use actions or shell commands

### Interview Questions

**Q1: What is the basic structure of a GitHub Actions workflow file?**
- name: Workflow name
- on: Triggers
- env: Environment variables
- jobs: Collection of jobs
- steps: Sequence of steps

**Q2: Where are workflow files stored?**
- .github/workflows/ directory
- .yml or .yaml extension
- Repository root
- Automatically detected by GitHub

**Q3: What are the key elements in a workflow file?**
- name: Workflow name
- on: Triggers
- env: Variables
- jobs: Jobs
- steps: Steps within jobs

---

## 4. YAML SYNTAX BASICS

### YAML Basics

**Comments:**
```yaml
# This is a comment
name: My Workflow  # Inline comment
```

**Strings:**
```yaml
name: My Workflow
description: 'This is a string'
multi-line: |
  This is a
  multi-line
  string
```

**Numbers:**
```yaml
port: 8080
timeout: 30
```

**Booleans:**
```yaml
enabled: true
debug: false
```

**Lists/Arrays:**
```yaml
branches:
  - main
  - develop
  - feature/*

# Alternative syntax
branches: [main, develop, feature/*]
```

**Dictionaries/Maps:**
```yaml
env:
  NODE_VERSION: '18'
  JAVA_VERSION: '11'
```

### GitHub Actions Syntax

**Expressions:**
```yaml
# Access context
${{ github.event_name }}
${{ github.ref }}
${{ secrets.MY_SECRET }}

# Conditional
${{ github.event_name == 'push' }}

# Default value
${{ github.event.comment || 'default' }}
```

**Contexts:**
```yaml
# GitHub context
${{ github.repository }}
${{ github.sha }}
${{ github.ref }}
${{ github.actor }}

# Job context
${{ job.status }}
${{ job.container }}

# Steps context
${{ steps.step-id.outputs.output-name }}

# Runner context
${{ runner.os }}
${{ runner.arch }}
```

### Interview Questions

**Q1: What is YAML and why is it used in GitHub Actions?**
- Human-readable data serialization
- Simple and clean syntax
- Used for configuration
- Easy to write and maintain

**Q2: How do you write comments in YAML?**
- Use # for comments
- Can be inline or separate line
- Everything after # is ignored

**Q3: How do you access GitHub context in YAML?**
- Use ${{ }} syntax
- Example: ${{ github.repository }}
- Can access various contexts
- Used for dynamic values

---

## 5. WORKFLOW TRIGGERS

### Push Events

```yaml
on:
  push:
    branches:
      - main
      - develop
    tags:
      - 'v*'
    paths:
      - 'src/**'
      - 'pom.xml'
```

### Pull Request Events

```yaml
on:
  pull_request:
    branches:
      - main
    types:
      - opened
      - synchronize
      - reopened
```

### Workflow Dispatch (Manual)

```yaml
on:
  workflow_dispatch:
    inputs:
      environment:
        description: 'Deployment environment'
        required: true
        default: 'dev'
        type: choice
        options:
          - dev
          - staging
          - prod
```

### Scheduled Triggers (Cron)

```yaml
on:
  schedule:
    # Runs every day at 2 AM UTC
    - cron: '0 2 * * *'
    
    # Runs every Monday at 9 AM UTC
    - cron: '0 9 * * 1'
    
    # Runs every 6 hours
    - cron: '0 */6 * * *'
```

### Repository Events

```yaml
on:
  repository_dispatch:
    types: [build-deploy]
```

### Webhook Events

```yaml
on:
  release:
    types: [created]
  
  issues:
    types: [opened, edited]
  
  issue_comment:
    types: [created]
```

### Multiple Triggers

```yaml
on:
  push:
    branches: [main]
  pull_request:
    branches: [main]
  workflow_dispatch:
```

### Trigger Filters

```yaml
on:
  push:
    branches:
      - main
      - 'release/**'
    tags:
      - 'v*'
    paths:
      - 'src/**'
      - 'pom.xml'
      - '.github/workflows/**'
    paths-ignore:
      - 'docs/**'
      - 'README.md'
```

### Interview Questions

**Q1: What are the different types of workflow triggers in GitHub Actions?**
- push: On code push
- pull_request: On PR events
- workflow_dispatch: Manual trigger
- schedule: Cron-based
- repository_dispatch: Repository events
- webhook: Various GitHub events

**Q2: How do you trigger a workflow manually?**
- Use workflow_dispatch trigger
- Add inputs for parameters
- Trigger from Actions tab
- Can use GitHub CLI

**Q3: How do you schedule a workflow to run periodically?**
- Use schedule trigger
- Use cron syntax
- Specify time in UTC
- Multiple schedules supported

---

## 6. JOBS AND STEPS

### Job Structure

```yaml
jobs:
  build:
    name: Build Application
    runs-on: ubuntu-latest
    
    # Job outputs
    outputs:
      artifact-name: ${{ steps.build.outputs.artifact }}
    
    # Job-level environment
    env:
      BUILD_ENV: production
    
    # Default shell
    defaults:
      run:
        shell: bash
    
    # Container
    container:
      image: node:18
      credentials:
        username: ${{ github.actor }}
        password: ${{ secrets.GITHUB_TOKEN }}
    
    # Services
    services:
      postgres:
        image: postgres:15
        env:
          POSTGRES_PASSWORD: postgres
        options: >-
          --health-cmd pg_isready
          --health-interval 10s
          --health-timeout 5s
          --health-retries 5
    
    steps:
      - name: Checkout
        uses: actions/checkout@v4
      
      - name: Build
        id: build
        run: |
          echo "artifact=my-app.jar" >> $GITHUB_OUTPUT
          mvn package
```

### Step Types

**Shell Commands:**
```yaml
steps:
  - name: Run shell command
    run: echo "Hello World"
  
  - name: Multi-line script
    run: |
      echo "Line 1"
      echo "Line 2"
      echo "Line 3"
```

**Using Actions:**
```yaml
steps:
  - name: Checkout code
    uses: actions/checkout@v4
    with:
      fetch-depth: 0
  
  - name: Setup Node
    uses: actions/setup-node@v4
    with:
      node-version: '18'
      cache: 'npm'
```

**Composite Steps:**
```yaml
steps:
  - name: Build and Test
    run: |
      npm install
      npm run build
      npm test
```

### Step Options

```yaml
steps:
  - name: Run with options
    run: npm test
    shell: bash
    working-directory: ./app
    env:
      NODE_ENV: test
    timeout-minutes: 10
    continue-on-error: false
```

### Job Dependencies

```yaml
jobs:
  build:
    runs-on: ubuntu-latest
    steps:
      - uses: actions/checkout@v4
      - run: mvn package
  
  test:
    runs-on: ubuntu-latest
    needs: build
    steps:
      - uses: actions/checkout@v4
      - run: mvn test
  
  deploy:
    runs-on: ubuntu-latest
    needs: [build, test]
    steps:
      - run: deploy.sh
```

### Interview Questions

**Q1: What is the difference between a job and a step?**
- Job: Set of steps running on same runner
- Step: Individual command or action
- Jobs can run in parallel
- Steps run sequentially within job

**Q2: How do you create job dependencies?**
- Use needs keyword
- Specify dependent job names
- Can have multiple dependencies
- Jobs wait for dependencies to complete

**Q3: What are the different types of steps in GitHub Actions?**
- run: Shell commands
- uses: Actions from marketplace
- Composite: Multiple commands
- with: Action parameters

---

## 7. RUNNERS (SELF-HOSTED VS GITHUB-HOSTED)

### GitHub-Hosted Runners

**Available Runners:**
```yaml
jobs:
  ubuntu:
    runs-on: ubuntu-latest
  
  windows:
    runs-on: windows-latest
  
  macos:
    runs-on: macos-latest
  
  # Specific versions
  ubuntu-20-04:
    runs-on: ubuntu-20.04
  
  windows-2019:
    runs-on: windows-2019
```

**Advantages:**
- No maintenance required
- Always available
- Updated automatically
- Free for public repos
- Generous free tier for private repos

**Disadvantages:**
- Limited customization
- Resource limits
- Not suitable for sensitive data
- Network restrictions

### Self-Hosted Runners

**Setup:**
1. Repository Settings → Actions → Runners
2. New self-hosted runner
3. Download and run runner script
4. Configure runner

**Usage:**
```yaml
jobs:
  build:
    runs-on: self-hosted
  
  # With labels
  build:
    runs-on: [self-hosted, linux, x64]
  
  # Specific runner
  build:
    runs-on: [self-hosted, my-runner-label]
```

**Advantages:**
- Full customization
- Access to internal resources
- No resource limits
- Can use specific hardware
- Better for sensitive data

**Disadvantages:**
- Maintenance required
- Security concerns
- Need to manage updates
- Infrastructure cost

### Runner Groups

```yaml
jobs:
  build:
    runs-on:
      group: my-runner-group
      labels: [self-hosted, linux]
```

### Interview Questions

**Q1: What are the differences between GitHub-hosted and self-hosted runners?**
- GitHub-hosted: Managed by GitHub, no maintenance
- Self-hosted: Custom, need maintenance
- GitHub-hosted: Limited customization
- Self-hosted: Full control

**Q2: When would you use self-hosted runners?**
- Need specific hardware
- Access to internal resources
- Sensitive data processing
- Custom software requirements
- Cost optimization at scale

**Q3: How do you set up a self-hosted runner?**
- Repository Settings → Actions → Runners
- Download runner script
- Run script on your server
- Configure with labels
- Runner connects to GitHub

---

## 8. ENVIRONMENT VARIABLES AND SECRETS

### Environment Variables

**Workflow-level:**
```yaml
env:
  NODE_VERSION: '18'
  JAVA_VERSION: '11'
  MAVEN_OPTS: '-Xmx1024m'

jobs:
  build:
    runs-on: ubuntu-latest
    steps:
      - run: echo $NODE_VERSION
```

**Job-level:**
```yaml
jobs:
  build:
    runs-on: ubuntu-latest
    env:
      BUILD_ENV: production
    steps:
      - run: echo $BUILD_ENV
```

**Step-level:**
```yaml
steps:
  - name: Build
    env:
      NODE_ENV: production
    run: npm run build
```

**Default Environment:**
```yaml
jobs:
  build:
    runs-on: ubuntu-latest
    defaults:
      run:
        shell: bash
        working-directory: ./app
    steps:
      - run: npm test
```

### GitHub Context Variables

```yaml
steps:
  - name: Print GitHub context
    run: |
      echo "Repository: ${{ github.repository }}"
      echo "Branch: ${{ github.ref }}"
      echo "Commit: ${{ github.sha }}"
      echo "Actor: ${{ github.actor }}"
      echo "Event: ${{ github.event_name }}"
```

### Secrets

**Setting Secrets:**
1. Repository Settings → Secrets and variables → Actions
2. New repository secret
3. Name and value
4. Add secret

**Using Secrets:**
```yaml
jobs:
  deploy:
    runs-on: ubuntu-latest
    steps:
      - name: Deploy
        env:
          API_KEY: ${{ secrets.API_KEY }}
          DB_PASSWORD: ${{ secrets.DB_PASSWORD }}
        run: deploy.sh
```

**Secrets in Actions:**
```yaml
steps:
  - name: Login to Docker
        uses: docker/login-action@v3
        with:
          username: ${{ secrets.DOCKER_USERNAME }}
          password: ${{ secrets.DOCKER_PASSWORD }}
```

**Organization Secrets:**
```yaml
jobs:
  build:
    runs-on: ubuntu-latest
    steps:
      - name: Use org secret
        env:
          ORG_SECRET: ${{ secrets.ORG_SECRET }}
        run: echo "Using org secret"
```

### Environment Secrets

```yaml
jobs:
  deploy:
    runs-on: ubuntu-latest
    environment: production
    steps:
      - name: Deploy
        env:
          PROD_SECRET: ${{ secrets.PROD_API_KEY }}
        run: deploy.sh
```

### Interview Questions

**Q1: How do you define environment variables in GitHub Actions?**
- Workflow-level env
- Job-level env
- Step-level env
- Can override at each level

**Q2: How do you use secrets in GitHub Actions?**
- Store in repository settings
- Access with secrets context
- Automatically masked in logs
- Never printed in plain text

**Q3: What is the difference between secrets and environment variables?**
- Secrets: Sensitive data, encrypted
- Environment variables: Configuration data
- Secrets stored securely
- Environment variables visible in logs

---

## 9. ACTIONS AND MARKETPLACE

### Using Marketplace Actions

```yaml
steps:
  # Checkout code
  - uses: actions/checkout@v4
    with:
      fetch-depth: 0
  
  # Setup Node.js
  - uses: actions/setup-node@v4
    with:
      node-version: '18'
      cache: 'npm'
  
  # Setup Java
  - uses: actions/setup-java@v4
    with:
      distribution: 'temurin'
      java-version: '11'
      cache: 'maven'
  
  # Setup Python
  - uses: actions/setup-python@v5
    with:
      python-version: '3.11'
      cache: 'pip'
```

### Popular Actions

**Checkout:**
```yaml
- uses: actions/checkout@v4
  with:
    fetch-depth: 0
    lfs: true
    submodules: recursive
```

**Setup Tools:**
```yaml
- uses: actions/setup-node@v4
- uses: actions/setup-java@v4
- uses: actions/setup-python@v5
- uses: actions/setup-go@v5
- uses: actions/setup-dotnet@v4
```

**Docker:**
```yaml
- uses: docker/login-action@v3
  with:
    username: ${{ secrets.DOCKER_USERNAME }}
    password: ${{ secrets.DOCKER_PASSWORD }}

- uses: docker/build-push-action@v5
  with:
    context: .
    push: true
    tags: user/app:latest
```

**AWS:**
```yaml
- uses: aws-actions/configure-aws-credentials@v4
  with:
    aws-access-key-id: ${{ secrets.AWS_ACCESS_KEY_ID }}
    aws-secret-access-key: ${{ secrets.AWS_SECRET_ACCESS_KEY }}
    aws-region: us-east-1
```

**Slack:**
```yaml
- uses: 8398a7/action-slack-notify@v3
  with:
    status: ${{ job.status }}
    text: 'Build completed'
    webhook_url: ${{ secrets.SLACK_WEBHOOK }}
```

### Custom Actions

**JavaScript Action:**
```yaml
# action.yml
name: 'My Custom Action'
description: 'A custom action'
runs:
  using: 'node16'
  main: 'index.js'
```

**Docker Action:**
```yaml
# action.yml
name: 'My Docker Action'
description: 'A Docker action'
runs:
  using: 'docker'
  image: 'Dockerfile'
```

**Using Custom Action:**
```yaml
steps:
  - uses: ./.github/actions/my-action
  - uses: username/repo/.github/actions/my-action@v1
```

### Composite Actions

```yaml
# action.yml
name: 'Build and Test'
description: 'Build and test application'
runs:
  using: 'composite'
  steps:
    - name: Install dependencies
      shell: bash
      run: npm install
    
    - name: Build
      shell: bash
      run: npm run build
    
    - name: Test
      shell: bash
      run: npm test
```

### Interview Questions

**Q1: What are GitHub Actions marketplace actions?**
- Pre-built reusable actions
- Created by community
- Can be used in workflows
- Thousands available

**Q2: How do you use a marketplace action?**
- Use uses keyword
- Specify action with version
- Provide with parameters
- Example: actions/checkout@v4

**Q3: How do you create a custom action?**
- Create action.yml file
- Define metadata
- Implement logic (JS/Docker)
- Publish to marketplace or use locally

---

## 10. MATRIX STRATEGY

### Basic Matrix

```yaml
jobs:
  test:
    runs-on: ubuntu-latest
    strategy:
      matrix:
        node-version: [16, 18, 20]
    steps:
      - uses: actions/setup-node@v4
        with:
          node-version: ${{ matrix.node-version }}
      - run: npm test
```

### Multiple Dimensions

```yaml
jobs:
  test:
    runs-on: ${{ matrix.os }}
    strategy:
      matrix:
        os: [ubuntu-latest, windows-latest, macos-latest]
        node-version: [16, 18, 20]
    steps:
      - uses: actions/setup-node@v4
        with:
          node-version: ${{ matrix.node-version }}
      - run: npm test
```

### Matrix with Exclude

```yaml
jobs:
  test:
    runs-on: ${{ matrix.os }}
    strategy:
      matrix:
        os: [ubuntu-latest, windows-latest]
        node-version: [16, 18, 20]
        exclude:
          - os: windows-latest
            node-version: 16
    steps:
      - uses: actions/setup-node@v4
        with:
          node-version: ${{ matrix.node-version }}
      - run: npm test
```

### Matrix with Include

```yaml
jobs:
  test:
    runs-on: ${{ matrix.os }}
    strategy:
      matrix:
        os: [ubuntu-latest]
        node-version: [16, 18]
        include:
          - os: windows-latest
            node-version: 18
          - os: macos-latest
            node-version: 20
    steps:
      - uses: actions/setup-node@v4
        with:
          node-version: ${{ matrix.node-version }}
      - run: npm test
```

### Fail Fast

```yaml
jobs:
  test:
    runs-on: ubuntu-latest
    strategy:
      fail-fast: false
      matrix:
        node-version: [16, 18, 20]
    steps:
      - uses: actions/setup-node@v4
        with:
          node-version: ${{ matrix.node-version }}
      - run: npm test
```

### Max Parallel

```yaml
jobs:
  test:
    runs-on: ubuntu-latest
    strategy:
      max-parallel: 2
      matrix:
        node-version: [16, 18, 20]
    steps:
      - uses: actions/setup-node@v4
        with:
          node-version: ${{ matrix.node-version }}
      - run: npm test
```

### Interview Questions

**Q1: What is matrix strategy in GitHub Actions?**
- Run jobs across multiple configurations
- Test different versions/platforms
- Parallel execution
- Define matrix variables

**Q2: How do you use matrix strategy?**
- Use strategy.matrix in job
- Define variables as arrays
- Access with matrix.variable
- Creates job for each combination

**Q3: What are fail-fast and max-parallel in matrix?**
- fail-fast: Cancel all if one fails
- max-parallel: Limit concurrent jobs
- fail-fast: true by default
- max-parallel: Unlimited by default

---

## 11. CONDITIONAL EXECUTION

### Job Conditions

```yaml
jobs:
  deploy:
    runs-on: ubuntu-latest
    if: github.ref == 'refs/heads/main'
    steps:
      - run: deploy.sh
```

### Step Conditions

```yaml
steps:
  - name: Deploy to Prod
    if: github.ref == 'refs/heads/main'
    run: deploy-prod.sh
  
  - name: Deploy to Dev
    if: github.ref == 'refs/heads/develop'
    run: deploy-dev.sh
```

### Complex Conditions

```yaml
jobs:
  deploy:
    runs-on: ubuntu-latest
    if: |
      github.event_name == 'push' &&
      github.ref == 'refs/heads/main' &&
      !contains(github.event.head_commit.message, 'skip-ci')
    steps:
      - run: deploy.sh
```

### Event-Based Conditions

```yaml
jobs:
  build:
    runs-on: ubuntu-latest
    if: github.event_name == 'push'
    steps:
      - run: echo "Push event"
  
  pr-build:
    runs-on: ubuntu-latest
    if: github.event_name == 'pull_request'
    steps:
      - run: echo "PR event"
```

### Output-Based Conditions

```yaml
jobs:
  test:
    runs-on: ubuntu-latest
    outputs:
      result: ${{ steps.test.outputs.result }}
    steps:
      - id: test
        run: echo "result=success" >> $GITHUB_OUTPUT
  
  deploy:
    runs-on: ubuntu-latest
    needs: test
    if: needs.test.outputs.result == 'success'
    steps:
      - run: deploy.sh
```

### Status-Based Conditions

```yaml
jobs:
  deploy:
    runs-on: ubuntu-latest
    if: success()
    steps:
      - run: deploy.sh
  
  notify-failure:
    runs-on: ubuntu-latest
    if: failure()
    steps:
      - run: notify-failure.sh
  
  always-run:
    runs-on: ubuntu-latest
    if: always()
    steps:
      - run: cleanup.sh
```

### Interview Questions

**Q1: How do you conditionally execute jobs in GitHub Actions?**
- Use if keyword at job level
- Can use expressions
- Check GitHub context
- Example: if: github.ref == 'refs/heads/main'

**Q2: How do you conditionally execute steps?**
- Use if keyword at step level
- Can check previous step status
- Can check outputs
- Example: if: success()

**Q3: What are the different status functions?**
- success(): Previous steps succeeded
- failure(): Previous steps failed
- always(): Always run
- cancelled(): Workflow cancelled

---

## 12. CACHING DEPENDENCIES

### Basic Caching

```yaml
steps:
  - name: Cache Node modules
    uses: actions/cache@v4
    with:
      path: ~/.npm
      key: ${{ runner.os }}-node-${{ hashFiles('**/package-lock.json') }}
      restore-keys: |
        ${{ runner.os }}-node-
```

### Maven Caching

```yaml
steps:
  - name: Cache Maven packages
    uses: actions/cache@v4
    with:
      path: ~/.m2/repository
      key: ${{ runner.os }}-maven-${{ hashFiles('**/pom.xml') }}
      restore-keys: |
        ${{ runner.os }}-maven-
```

### Setup Action with Caching

```yaml
steps:
  - name: Setup Node
    uses: actions/setup-node@v4
    with:
      node-version: '18'
      cache: 'npm'
```

### Multiple Caches

```yaml
steps:
  - name: Cache Node modules
    uses: actions/cache@v4
    with:
      path: |
        ~/.npm
        node_modules
      key: ${{ runner.os }}-node-${{ hashFiles('**/package-lock.json') }}
  
  - name: Cache Build output
    uses: actions/cache@v4
    with:
      path: dist
      key: ${{ runner.os }}-build-${{ github.sha }}
```

### Cache Scope

```yaml
jobs:
  build:
    runs-on: ubuntu-latest
    steps:
      - uses: actions/cache@v4
        with:
          path: ~/.npm
          key: npm-${{ github.sha }}
          # Cache only for this workflow run
```

### Interview Questions

**Q1: How do you cache dependencies in GitHub Actions?**
- Use actions/cache@v4
- Specify path to cache
- Use hashFiles for key
- Specify restore-keys

**Q2: What is the benefit of caching?**
- Faster workflow execution
- Reduced network usage
- Lower costs
- Improved developer experience

**Q3: How do you create cache keys?**
- Use hashFiles for file-based keys
- Include runner.os for OS-specific
- Use restore-keys for fallback
- Example: ${{ runner.os }}-node-${{ hashFiles('**/package-lock.json') }}

---

## 13. ARTIFACTS AND STORAGE

### Upload Artifacts

```yaml
steps:
  - name: Build
    run: mvn package
  
  - name: Upload artifact
    uses: actions/upload-artifact@v4
    with:
      name: my-app
      path: target/*.jar
      retention-days: 30
```

### Download Artifacts

```yaml
jobs:
  build:
    runs-on: ubuntu-latest
    steps:
      - name: Build
        run: mvn package
      - name: Upload artifact
        uses: actions/upload-artifact@v4
        with:
          name: my-app
          path: target/*.jar
  
  deploy:
    runs-on: ubuntu-latest
    needs: build
    steps:
      - name: Download artifact
        uses: actions/download-artifact@v4
        with:
          name: my-app
      - name: Deploy
        run: deploy.sh
```

### Multiple Artifacts

```yaml
steps:
  - name: Upload multiple artifacts
    uses: actions/upload-artifact@v4
    with:
      name: my-artifacts
      path: |
        target/*.jar
        target/*.war
        reports/
```

### Artifact Retention

```yaml
steps:
  - name: Upload with retention
    uses: actions/upload-artifact@v4
    with:
      name: my-app
      path: target/*.jar
      retention-days: 90
      if-no-files-found: warn
```

### Caching vs Artifacts

```yaml
# Caching: Speed up workflow
steps:
  - uses: actions/cache@v4
    with:
      path: node_modules
      key: npm-${{ hashFiles('**/package-lock.json') }}

# Artifacts: Share between jobs/workflows
steps:
  - uses: actions/upload-artifact@v4
    with:
      name: build-output
      path: dist/
```

### Interview Questions

**Q1: What are artifacts in GitHub Actions?**
- Files generated during workflow
- Can be uploaded and downloaded
- Shared between jobs
- Retained for specified days

**Q2: How do you upload artifacts?**
- Use actions/upload-artifact@v4
- Specify name and path
- Set retention period
- Can upload multiple files

**Q3: What is the difference between caching and artifacts?**
- Caching: Speed up workflow, temporary
- Artifacts: Share between jobs, persistent
- Caching: Dependencies, build cache
- Artifacts: Build outputs, reports

---

## 14. MAVEN INTEGRATION

### Setup Java and Maven

```yaml
jobs:
  build:
    runs-on: ubuntu-latest
    steps:
      - name: Checkout
        uses: actions/checkout@v4
      
      - name: Set up JDK 11
        uses: actions/setup-java@v4
        with:
          distribution: 'temurin'
          java-version: '11'
          cache: 'maven'
      
      - name: Build with Maven
        run: mvn clean package
```

### Maven Goals

```yaml
jobs:
  build:
    runs-on: ubuntu-latest
    steps:
      - uses: actions/setup-java@v4
        with:
          distribution: 'temurin'
          java-version: '11'
          cache: 'maven'
      
      - name: Clean
        run: mvn clean
      
      - name: Compile
        run: mvn compile
      
      - name: Test
        run: mvn test
      
      - name: Package
        run: mvn package
      
      - name: Install
        run: mvn install
```

### Maven with Settings

```yaml
jobs:
  build:
    runs-on: ubuntu-latest
    steps:
      - uses: actions/setup-java@v4
        with:
          distribution: 'temurin'
          java-version: '11'
          cache: 'maven'
          server-id: github
          server-username: MAVEN_USERNAME
          server-password: MAVEN_PASSWORD
      
      - name: Build
        run: mvn deploy
        env:
          MAVEN_USERNAME: ${{ secrets.MAVEN_USERNAME }}
          MAVEN_PASSWORD: ${{ secrets.MAVEN_PASSWORD }}
```

### Maven Profiles

```yaml
jobs:
  build:
    runs-on: ubuntu-latest
    steps:
      - uses: actions/setup-java@v4
        with:
          distribution: 'temurin'
          java-version: '11'
          cache: 'maven'
      
      - name: Build with profile
        run: mvn clean package -Pdev
      
      - name: Build with multiple profiles
        run: mvn clean package -Pdev,ci
```

### Interview Questions

**Q1: How do you integrate Maven with GitHub Actions?**
- Use actions/setup-java@v4
- Specify distribution and version
- Use cache: 'maven'
- Run Maven commands

**Q2: How do you cache Maven dependencies?**
- Use cache: 'maven' in setup-java
- Automatically caches ~/.m2
- Uses pom.xml for cache key
- Speeds up builds

**Q3: How do you use Maven profiles in GitHub Actions?**
- Use -P flag with profile name
- Can use multiple profiles
- Environment-specific builds
- Example: mvn package -Pdev

---

## 15. TESTNG INTEGRATION

### Run TestNG Tests

```yaml
jobs:
  test:
    runs-on: ubuntu-latest
    steps:
      - uses: actions/checkout@v4
      
      - name: Set up JDK 11
        uses: actions/setup-java@v4
        with:
          distribution: 'temurin'
          java-version: '11'
          cache: 'maven'
      
      - name: Run TestNG tests
        run: mvn test
```

### TestNG with XML

```yaml
jobs:
  test:
    runs-on: ubuntu-latest
    steps:
      - uses: actions/setup-java@v4
        with:
          distribution: 'temurin'
          java-version: '11'
          cache: 'maven'
      
      - name: Run with TestNG XML
        run: mvn test -DsuiteXmlFile=testng.xml
```

### Publish Test Results

```yaml
jobs:
  test:
    runs-on: ubuntu-latest
    steps:
      - uses: actions/setup-java@v4
        with:
          distribution: 'temurin'
          java-version: '11'
          cache: 'maven'
      
      - name: Run tests
        run: mvn test
      
      - name: Publish Test Results
        uses: EnricoMi/publish-unit-test-result-action@v2
        if: always()
        with:
          files: target/surefire-reports/*.xml
```

### Parallel Test Execution

```yaml
jobs:
  test:
    runs-on: ubuntu-latest
    steps:
      - uses: actions/setup-java@v4
        with:
          distribution: 'temurin'
          java-version: '11'
          cache: 'maven'
      
      - name: Run parallel tests
        run: mvn test -Dparallel=methods -DthreadCount=4
```

### Interview Questions

**Q1: How do you run TestNG tests in GitHub Actions?**
- Use Maven surefire plugin
- Run mvn test command
- Can specify testng.xml
- Publish test results

**Q2: How do you publish TestNG results in GitHub Actions?**
- Use publish-unit-test-result-action
- Specify test report files
- Use if: always() to always publish
- Shows in PR checks

**Q3: How do you run TestNG tests in parallel in GitHub Actions?**
- Use Maven surefire configuration
- Set parallel and threadCount
- Can use TestNG XML
- Distributes tests across threads

---

## 16. PLAYWRIGHT INTEGRATION

### Setup Playwright

```yaml
jobs:
  test:
    runs-on: ubuntu-latest
    steps:
      - uses: actions/checkout@v4
      
      - name: Setup Node
        uses: actions/setup-node@v4
        with:
          node-version: '18'
          cache: 'npm'
      
      - name: Install dependencies
        run: npm ci
      
      - name: Install Playwright Browsers
        run: npx playwright install --with-deps
      
      - name: Run Playwright tests
        run: npx playwright test
```

### Playwright with Maven

```yaml
jobs:
  test:
    runs-on: ubuntu-latest
    steps:
      - uses: actions/checkout@v4
      
      - name: Set up JDK 11
        uses: actions/setup-java@v4
        with:
          distribution: 'temurin'
          java-version: '11'
          cache: 'maven'
      
      - name: Run Playwright tests
        run: mvn test -Dtest=PlaywrightTest
```

### Playwright Report

```yaml
jobs:
  test:
    runs-on: ubuntu-latest
    steps:
      - uses: actions/setup-node@v4
        with:
          node-version: '18'
          cache: 'npm'
      
      - name: Install dependencies
        run: npm ci
      
      - name: Install Playwright
        run: npx playwright install --with-deps
      
      - name: Run tests
        run: npx playwright test
      
      - name: Upload Playwright Report
        uses: actions/upload-artifact@v4
        if: always()
        with:
          name: playwright-report
          path: playwright-report/
          retention-days: 30
```

### Playwright with Docker

```yaml
jobs:
  test:
    runs-on: ubuntu-latest
    container:
      image: mcr.microsoft.com/playwright:latest
    steps:
      - uses: actions/checkout@v4
      
      - name: Install dependencies
        run: npm ci
      
      - name: Run tests
        run: npx playwright test
```

### Headless vs Headful

```yaml
jobs:
  test:
    runs-on: ubuntu-latest
    steps:
      - uses: actions/setup-node@v4
        with:
          node-version: '18'
          cache: 'npm'
      
      - name: Install dependencies
        run: npm ci
      
      - name: Install Playwright
        run: npx playwright install --with-deps
      
      - name: Run tests (headless)
        run: npx playwright test
      
      - name: Run tests (headful for debugging)
        run: npx playwright test --headed
```

### Interview Questions

**Q1: How do you integrate Playwright with GitHub Actions?**
- Setup Node.js
- Install dependencies
- Install Playwright browsers
- Run npx playwright test

**Q2: How do you use Playwright Docker image in GitHub Actions?**
- Use container in job
- Specify Playwright image
- Run tests in container
- Consistent environment

**Q3: How do you publish Playwright reports in GitHub Actions?**
- Upload playwright-report as artifact
- Use actions/upload-artifact@v4
- Set if: always() to always upload
- Can view in Actions UI

---

## 17. DOCKER INTEGRATION

### Build Docker Image

```yaml
jobs:
  build:
    runs-on: ubuntu-latest
    steps:
      - uses: actions/checkout@v4
      
      - name: Set up Docker Buildx
        uses: docker/setup-buildx-action@v3
      
      - name: Build Docker image
        uses: docker/build-push-action@v5
        with:
          context: .
          push: false
          tags: myapp:latest
          cache-from: type=gha
          cache-to: type=gha,mode=max
```

### Push Docker Image

```yaml
jobs:
  build:
    runs-on: ubuntu-latest
    steps:
      - uses: actions/checkout@v4
      
      - name: Login to Docker Hub
        uses: docker/login-action@v3
        with:
          username: ${{ secrets.DOCKER_USERNAME }}
          password: ${{ secrets.DOCKER_PASSWORD }}
      
      - name: Build and push
        uses: docker/build-push-action@v5
        with:
          context: .
          push: true
          tags: |
            username/myapp:latest
            username/myapp:${{ github.sha }}
```

### Docker Compose

```yaml
jobs:
  test:
    runs-on: ubuntu-latest
    services:
      postgres:
        image: postgres:15
        env:
          POSTGRES_PASSWORD: postgres
        options: >-
          --health-cmd pg_isready
          --health-interval 10s
    steps:
      - uses: actions/checkout@v4
      - name: Run tests
        run: mvn test
        env:
          DB_HOST: postgres
          DB_PASSWORD: postgres
```

### Multi-Architecture Build

```yaml
jobs:
  build:
    runs-on: ubuntu-latest
    steps:
      - uses: actions/checkout@v4
      
      - name: Set up QEMU
        uses: docker/setup-qemu-action@v3
      
      - name: Set up Docker Buildx
        uses: docker/setup-buildx-action@v3
      
      - name: Login to Docker Hub
        uses: docker/login-action@v3
        with:
          username: ${{ secrets.DOCKER_USERNAME }}
          password: ${{ secrets.DOCKER_PASSWORD }}
      
      - name: Build and push multi-arch
        uses: docker/build-push-action@v5
        with:
          context: .
          platforms: linux/amd64,linux/arm64
          push: true
          tags: username/myapp:latest
```

### Interview Questions

**Q1: How do you build Docker images in GitHub Actions?**
- Use docker/setup-buildx-action
- Use docker/build-push-action
- Specify context and tags
- Can push to registry

**Q2: How do you push Docker images to a registry?**
- Login with docker/login-action
- Use credentials as secrets
- Push with build-push-action
- Tag with version/sha

**Q3: How do you build multi-architecture Docker images?**
- Use docker/setup-qemu-action
- Use docker/setup-buildx-action
- Specify platforms
- Build for multiple architectures

---

## 18. DEPLOYMENT STRATEGIES

### Deploy to Environment

```yaml
jobs:
  deploy:
    runs-on: ubuntu-latest
    environment:
      name: production
      url: https://example.com
    steps:
      - name: Deploy
        run: deploy.sh
```

### Manual Approval

```yaml
jobs:
  deploy:
    runs-on: ubuntu-latest
    environment:
      name: production
      url: https://example.com
    steps:
      - name: Deploy
        run: deploy.sh
```

**Configure approval:**
- Repository Settings → Environments
- Create environment
- Add required reviewers

### Multiple Environments

```yaml
jobs:
  deploy-dev:
    runs-on: ubuntu-latest
    environment: development
    steps:
      - name: Deploy to Dev
        run: deploy-dev.sh
  
  deploy-staging:
    runs-on: ubuntu-latest
    needs: deploy-dev
    environment: staging
    steps:
      - name: Deploy to Staging
        run: deploy-staging.sh
  
  deploy-prod:
    runs-on: ubuntu-latest
    needs: deploy-staging
    environment: production
    steps:
      - name: Deploy to Production
        run: deploy-prod.sh
```

### Blue-Green Deployment

```yaml
jobs:
  deploy-blue:
    runs-on: ubuntu-latest
    environment:
      name: production-blue
    steps:
      - name: Deploy to Blue
        run: deploy-blue.sh
  
  switch-traffic:
    runs-on: ubuntu-latest
    needs: deploy-blue
    environment: production
    steps:
      - name: Switch traffic to Blue
        run: switch-traffic.sh
```

### Canary Deployment

```yaml
jobs:
  deploy-canary:
    runs-on: ubuntu-latest
    environment:
      name: production-canary
    steps:
      - name: Deploy Canary
        run: deploy-canary.sh
  
  monitor:
    runs-on: ubuntu-latest
    needs: deploy-canary
    steps:
      - name: Monitor Canary
        run: monitor-canary.sh
  
  deploy-full:
    runs-on: ubuntu-latest
    needs: monitor
    environment: production
    steps:
      - name: Deploy Full
        run: deploy-full.sh
```

### Interview Questions

**Q1: How do you deploy to environments in GitHub Actions?**
- Use environment keyword
- Configure environments in settings
- Can add approval gates
- Track deployments

**Q2: How do you implement manual approval in GitHub Actions?**
- Configure environment protection rules
- Add required reviewers
- Workflow waits for approval
- Can timeout

**Q3: What are different deployment strategies?**
- Blue-Green: Switch traffic between environments
- Canary: Gradual rollout
- Rolling: Incremental update
- Recreate: Stop all, start new

---

## 19. WORKFLOW DISPATCH

### Basic Dispatch

```yaml
on:
  workflow_dispatch:
```

### With Inputs

```yaml
on:
  workflow_dispatch:
    inputs:
      environment:
        description: 'Deployment environment'
        required: true
        default: 'dev'
        type: choice
        options:
          - dev
          - staging
          - prod
      version:
        description: 'Version to deploy'
        required: true
        type: string
      skip_tests:
        description: 'Skip tests'
        required: false
        type: boolean
        default: false
```

### Using Inputs

```yaml
jobs:
  deploy:
    runs-on: ubuntu-latest
    steps:
      - name: Deploy
        env:
          ENVIRONMENT: ${{ inputs.environment }}
          VERSION: ${{ inputs.version }}
          SKIP_TESTS: ${{ inputs.skip_tests }}
        run: |
          echo "Deploying to $ENVIRONMENT"
          echo "Version: $VERSION"
          if [ "$SKIP_TESTS" = "true" ]; then
            echo "Skipping tests"
          fi
```

### Dispatch with GitHub CLI

```bash
gh workflow run "Deploy.yml" -f environment=prod -f version=v1.0.0
```

### Interview Questions

**Q1: What is workflow_dispatch in GitHub Actions?**
- Manual trigger for workflow
- Can add input parameters
- Trigger from Actions tab
- Can use GitHub CLI

**Q2: How do you add inputs to workflow_dispatch?**
- Define inputs under workflow_dispatch
- Specify type, description, default
- Access with inputs context
- Example: ${{ inputs.environment }}

**Q3: How do you trigger workflow_dispatch programmatically?**
- Use GitHub CLI
- Use REST API
- Specify input values
- Example: gh workflow run

---

## 20. REUSABLE WORKFLOWS

### Create Reusable Workflow

```yaml
# .github/workflows/build.yml
name: Reusable Build Workflow
on:
  workflow_call:
    inputs:
      node-version:
        required: true
        type: string
    outputs:
      artifact-name:
        value: ${{ jobs.build.outputs.artifact }}
    secrets:
      api-key:
        required: true

jobs:
  build:
    runs-on: ubuntu-latest
    outputs:
      artifact: ${{ steps.build.outputs.artifact }}
    steps:
      - uses: actions/checkout@v4
      - uses: actions/setup-node@v4
        with:
          node-version: ${{ inputs.node-version }}
      - id: build
        run: |
          npm ci
          npm run build
          echo "artifact=my-app" >> $GITHUB_OUTPUT
      - name: Upload artifact
        uses: actions/upload-artifact@v4
        with:
          name: my-app
          path: dist/
```

### Call Reusable Workflow

```yaml
# .github/workflows/ci.yml
name: CI Pipeline
on:
  push:
    branches: [main]

jobs:
  call-build:
    uses: ./.github/workflows/build.yml
    with:
      node-version: '18'
    secrets:
      api-key: ${{ secrets.API_KEY }}
```

### Call from Another Repository

```yaml
jobs:
  call-build:
    uses: username/repo/.github/workflows/build.yml@main
    with:
      node-version: '18'
    secrets:
      api-key: ${{ secrets.API_KEY }}
```

### Interview Questions

**Q1: What are reusable workflows in GitHub Actions?**
- Workflows that can be called by other workflows
- Define inputs, outputs, secrets
- Promote reusability
- Can be in same or different repo

**Q2: How do you create a reusable workflow?**
- Use workflow_call trigger
- Define inputs and outputs
- Define secrets
- Jobs can have outputs

**Q3: How do you call a reusable workflow?**
- Use uses keyword
- Specify workflow path
- Pass inputs and secrets
- Can use outputs

---

## 21. COMPOSITE ACTIONS

### Create Composite Action

```yaml
# .github/actions/my-action/action.yml
name: 'My Composite Action'
description: 'A composite action'
inputs:
  node-version:
    description: 'Node version'
    required: true
    default: '18'
outputs:
  result:
    description: 'Build result'
    value: ${{ steps.build.outputs.result }}
runs:
  using: 'composite'
  steps:
    - name: Setup Node
      uses: actions/setup-node@v4
      with:
        node-version: ${{ inputs.node-version }}
    
    - name: Install dependencies
      shell: bash
      run: npm ci
    
    - name: Build
      id: build
      shell: bash
      run: |
        npm run build
        echo "result=success" >> $GITHUB_OUTPUT
```

### Use Composite Action

```yaml
jobs:
  build:
    runs-on: ubuntu-latest
    steps:
      - uses: actions/checkout@v4
      
      - name: Use composite action
        id: build
        uses: ./.github/actions/my-action
        with:
          node-version: '18'
      
      - name: Use output
        run: echo "Build result: ${{ steps.build.outputs.result }}"
```

### Composite Action from Marketplace

```yaml
steps:
  - uses: username/my-action@v1
    with:
      node-version: '18'
```

### Interview Questions

**Q1: What are composite actions in GitHub Actions?**
- Actions composed of multiple steps
- Defined in action.yml
- Can have inputs and outputs
- Reusable across workflows

**Q2: How do you create a composite action?**
- Create action.yml file
- Define inputs and outputs
- Use runs: composite
- Define steps

**Q3: What is the difference between composite and JavaScript actions?**
- Composite: Multiple steps, YAML-based
- JavaScript: Custom logic, JS-based
- Composite: Easier to create
- JavaScript: More powerful

---

## 22. SECURITY BEST PRACTICES

### Secrets Management

```yaml
# Never hardcode secrets
env:
  API_KEY: ${{ secrets.API_KEY }}
  DB_PASSWORD: ${{ secrets.DB_PASSWORD }}
```

### Token Permissions

```yaml
permissions:
  contents: read
  issues: write
  pull-requests: write

jobs:
  build:
    runs-on: ubuntu-latest
    steps:
      - uses: actions/checkout@v4
```

### Third-Party Actions

```yaml
# Pin to specific version
- uses: actions/checkout@v4  # Good
- uses: actions/checkout@main  # Bad

# Use trusted actions
- uses: actions/checkout@v4  # Official
- uses: some-user/action@v1  # Verify trust
```

### Code Scanning

```yaml
jobs:
  analyze:
    runs-on: ubuntu-latest
    steps:
      - uses: actions/checkout@v4
      - name: Initialize CodeQL
        uses: github/codeql-action/init@v3
        with:
          languages: javascript
      - name: Perform Analysis
        uses: github/codeql-action/analyze@v3
```

### Dependency Review

```yaml
jobs:
  dependency-review:
    runs-on: ubuntu-latest
    steps:
      - uses: actions/checkout@v4
      - name: Dependency Review
        uses: actions/dependency-review-action@v4
```

### Security Hardening

```yaml
jobs:
  build:
    runs-on: ubuntu-latest
    permissions:
      contents: read
    steps:
      - uses: actions/checkout@v4
      - name: Run security scan
        run: npm audit
```

### Interview Questions

**Q1: What are security best practices for GitHub Actions?**
- Use secrets for sensitive data
- Pin action versions
- Limit permissions
- Use code scanning
- Review third-party actions

**Q2: How do you manage secrets in GitHub Actions?**
- Store in repository settings
- Access with secrets context
- Automatically masked
- Never hardcode

**Q3: How do you limit permissions in GitHub Actions?**
- Use permissions keyword
- Grant minimum required
- Can set at workflow level
- Default: read-all

---

## 23. GITHUB ACTIONS VS JENKINS

### Comparison

| Feature | GitHub Actions | Jenkins |
|---------|---------------|---------|
| Syntax | YAML | Groovy |
| Git Integration | Native | Plugin |
| Setup | Zero config | Server setup |
| Maintenance | None | Required |
| Free Tier | Generous | Self-hosted |
| Marketplace | Large | Plugins |
| Self-hosted | ✅ | ✅ |
| Learning Curve | Low | High |
| Docker Support | Native | Plugin |
| Cost | Free/Paid | Self-hosted |

### When to Use GitHub Actions

**Use GitHub Actions when:**
- Code is on GitHub
- Want simple YAML syntax
- No server maintenance
- Need native Git integration
- Want free tier for public repos

**Use Jenkins when:**
- Need complex logic
- Have existing Jenkins setup
- Need self-hosted only
- Require extensive customization
- Have non-Git repositories

### Migration from Jenkins to GitHub Actions

**Jenkins Pipeline:**
```groovy
pipeline {
    agent any
    stages {
        stage('Build') {
            steps {
                sh 'mvn clean package'
            }
        }
    }
}
```

**GitHub Actions:**
```yaml
jobs:
  build:
    runs-on: ubuntu-latest
    steps:
      - uses: actions/checkout@v4
      - name: Build
        run: mvn clean package
```

### Interview Questions

**Q1: What are the main differences between GitHub Actions and Jenkins?**
- GitHub Actions: YAML, native Git integration
- Jenkins: Groovy, plugin-based
- GitHub Actions: No maintenance
- Jenkins: Self-hosted, customizable

**Q2: When would you choose GitHub Actions over Jenkins?**
- Code on GitHub
- Want simple setup
- No server maintenance
- Native Git integration
- Free tier needed

**Q3: How do you migrate from Jenkins to GitHub Actions?**
- Convert Groovy to YAML
- Replace plugins with actions
- Use marketplace actions
- Update triggers
- Test thoroughly

---

## 24. COMMON INTERVIEW QUESTIONS

### GitHub Actions Fundamentals

**Q1: What is GitHub Actions?**
- CI/CD platform by GitHub
- YAML-based workflow definitions
- Integrated with GitHub repositories
- Automates build, test, deployment

**Q2: What are the advantages of GitHub Actions?**
- YAML-based (simple syntax)
- Native GitHub integration
- No server maintenance
- Free for public repos
- Large marketplace

**Q3: What is the difference between GitHub Actions and GitLab CI?**
- Both YAML-based
- GitHub Actions: GitHub native
- GitLab CI: GitLab native
- Similar syntax and features
- Choice depends on Git host

### Workflow Syntax

**Q4: What is the basic structure of a GitHub Actions workflow?**
- name: Workflow name
- on: Triggers
- env: Environment variables
- jobs: Collection of jobs
- steps: Steps within jobs

**Q5: Where are workflow files stored?**
- .github/workflows/ directory
- .yml or .yaml extension
- Repository root
- Automatically detected

**Q6: What are the different types of workflow triggers?**
- push: On code push
- pull_request: On PR events
- workflow_dispatch: Manual trigger
- schedule: Cron-based
- repository_dispatch: Repository events

### Jobs and Steps

**Q7: What is the difference between a job and a step?**
- Job: Set of steps on same runner
- Step: Individual command or action
- Jobs can run in parallel
- Steps run sequentially

**Q8: How do you create job dependencies?**
- Use needs keyword
- Specify dependent job names
- Can have multiple dependencies
- Jobs wait for dependencies

**Q9: What are the different types of steps?**
- run: Shell commands
- uses: Actions from marketplace
- Composite: Multiple commands
- with: Action parameters

### Runners

**Q10: What are the differences between GitHub-hosted and self-hosted runners?**
- GitHub-hosted: Managed by GitHub
- Self-hosted: Custom, need maintenance
- GitHub-hosted: Limited customization
- Self-hosted: Full control

**Q11: When would you use self-hosted runners?**
- Need specific hardware
- Access to internal resources
- Sensitive data processing
- Custom software requirements

### Variables and Secrets

**Q12: How do you define environment variables?**
- Workflow-level env
- Job-level env
- Step-level env
- Can override at each level

**Q13: How do you use secrets?**
- Store in repository settings
- Access with secrets context
- Automatically masked
- Never hardcode

**Q14: What is the difference between secrets and environment variables?**
- Secrets: Sensitive, encrypted
- Environment variables: Configuration
- Secrets stored securely
- Environment variables visible

### Actions and Marketplace

**Q15: What are marketplace actions?**
- Pre-built reusable actions
- Created by community
- Can be used in workflows
- Thousands available

**Q16: How do you use a marketplace action?**
- Use uses keyword
- Specify action with version
- Provide with parameters
- Example: actions/checkout@v4

### Matrix Strategy

**Q17: What is matrix strategy?**
- Run jobs across configurations
- Test different versions/platforms
- Parallel execution
- Define matrix variables

**Q18: How do you use matrix strategy?**
- Use strategy.matrix in job
- Define variables as arrays
- Access with matrix.variable
- Creates job for each combination

### Caching and Artifacts

**Q19: How do you cache dependencies?**
- Use actions/cache@v4
- Specify path to cache
- Use hashFiles for key
- Specify restore-keys

**Q20: What is the difference between caching and artifacts?**
- Caching: Speed up workflow
- Artifacts: Share between jobs
- Caching: Temporary
- Artifacts: Persistent

### Integration

**Q21: How do you integrate Maven with GitHub Actions?**
- Use actions/setup-java@v4
- Specify distribution and version
- Use cache: 'maven'
- Run Maven commands

**Q22: How do you integrate Playwright with GitHub Actions?**
- Setup Node.js
- Install dependencies
- Install Playwright browsers
- Run npx playwright test

**Q23: How do you build Docker images?**
- Use docker/setup-buildx-action
- Use docker/build-push-action
- Specify context and tags
- Can push to registry

### Deployment

**Q24: How do you deploy to environments?**
- Use environment keyword
- Configure environments in settings
- Can add approval gates
- Track deployments

**Q25: How do you implement manual approval?**
- Configure environment protection
- Add required reviewers
- Workflow waits for approval
- Can timeout

### Advanced

**Q26: What are reusable workflows?**
- Workflows called by others
- Define inputs, outputs, secrets
- Promote reusability
- Can be in same/different repo

**Q27: What are composite actions?**
- Actions with multiple steps
- Defined in action.yml
- Can have inputs/outputs
- Reusable across workflows

**Q28: How do you secure GitHub Actions?**
- Use secrets for sensitive data
- Pin action versions
- Limit permissions
- Use code scanning
- Review third-party actions

### Scenario-Based

**Q29: How do you handle a failing workflow?**
- Configure notifications
- Analyze logs
- Fix the issue
- Rerun workflow
- Monitor success

**Q30: How do you optimize workflow performance?**
- Use caching
- Parallel execution
- Matrix strategy
- Optimize dependencies
- Use self-hosted runners

---

## 25. PRACTICE WORKFLOWS

### Practice 1: Simple Build Workflow

```yaml
name: Simple Build
on: [push]
jobs:
  build:
    runs-on: ubuntu-latest
    steps:
      - uses: actions/checkout@v4
      - name: Run a script
        run: echo "Hello World"
```

### Practice 2: Maven Build Workflow

```yaml
name: Maven Build
on: [push]
jobs:
  build:
    runs-on: ubuntu-latest
    steps:
      - uses: actions/checkout@v4
      - name: Set up JDK 11
        uses: actions/setup-java@v4
        with:
          distribution: 'temurin'
          java-version: '11'
          cache: 'maven'
      - name: Build with Maven
        run: mvn clean package
```

### Practice 3: TestNG Test Workflow

```yaml
name: TestNG Tests
on: [push, pull_request]
jobs:
  test:
    runs-on: ubuntu-latest
    steps:
      - uses: actions/checkout@v4
      - name: Set up JDK 11
        uses: actions/setup-java@v4
        with:
          distribution: 'temurin'
          java-version: '11'
          cache: 'maven'
      - name: Run tests
        run: mvn test
      - name: Publish test results
        uses: EnricoMi/publish-unit-test-result-action@v2
        if: always()
        with:
          files: target/surefire-reports/*.xml
```

### Practice 4: Playwright Test Workflow

```yaml
name: Playwright Tests
on: [push, pull_request]
jobs:
  test:
    runs-on: ubuntu-latest
    steps:
      - uses: actions/checkout@v4
      - name: Setup Node
        uses: actions/setup-node@v4
        with:
          node-version: '18'
          cache: 'npm'
      - name: Install dependencies
        run: npm ci
      - name: Install Playwright
        run: npx playwright install --with-deps
      - name: Run tests
        run: npx playwright test
      - name: Upload report
        uses: actions/upload-artifact@v4
        if: always()
        with:
          name: playwright-report
          path: playwright-report/
```

### Practice 5: Multi-Stage CI/CD Workflow

```yaml
name: CI/CD Pipeline
on:
  push:
    branches: [main, develop]
  pull_request:
    branches: [main]

jobs:
  build:
    runs-on: ubuntu-latest
    steps:
      - uses: actions/checkout@v4
      - name: Set up JDK 11
        uses: actions/setup-java@v4
        with:
          distribution: 'temurin'
          java-version: '11'
          cache: 'maven'
      - name: Build
        run: mvn clean package
      - name: Upload artifact
        uses: actions/upload-artifact@v4
        with:
          name: my-app
          path: target/*.jar

  test:
    runs-on: ubuntu-latest
    needs: build
    steps:
      - uses: actions/checkout@v4
      - name: Set up JDK 11
        uses: actions/setup-java@v4
        with:
          distribution: 'temurin'
          java-version: '11'
          cache: 'maven'
      - name: Run tests
        run: mvn test
      - name: Publish test results
        uses: EnricoMi/publish-unit-test-result-action@v2
        if: always()
        with:
          files: target/surefire-reports/*.xml

  deploy:
    runs-on: ubuntu-latest
    needs: [build, test]
    if: github.ref == 'refs/heads/main'
    environment: production
    steps:
      - name: Download artifact
        uses: actions/download-artifact@v4
        with:
          name: my-app
      - name: Deploy
        run: echo "Deploying to production"
```

### Practice 6: Matrix Strategy Workflow

```yaml
name: Matrix Build
on: [push]
jobs:
  test:
    runs-on: ${{ matrix.os }}
    strategy:
      matrix:
        os: [ubuntu-latest, windows-latest, macos-latest]
        java-version: [11, 17]
    steps:
      - uses: actions/checkout@v4
      - name: Set up JDK ${{ matrix.java-version }}
        uses: actions/setup-java@v4
        with:
          distribution: 'temurin'
          java-version: ${{ matrix.java-version }}
          cache: 'maven'
      - name: Build and test
        run: mvn clean test
```

### Practice 7: Docker Build Workflow

```yaml
name: Docker Build
on: [push]
jobs:
  build:
    runs-on: ubuntu-latest
    steps:
      - uses: actions/checkout@v4
      - name: Set up Docker Buildx
        uses: docker/setup-buildx-action@v3
      - name: Login to Docker Hub
        uses: docker/login-action@v3
        with:
          username: ${{ secrets.DOCKER_USERNAME }}
          password: ${{ secrets.DOCKER_PASSWORD }}
      - name: Build and push
        uses: docker/build-push-action@v5
        with:
          context: .
          push: true
          tags: |
            username/myapp:latest
            username/myapp:${{ github.sha }}
```

### Practice 8: Scheduled Workflow

```yaml
name: Scheduled Tests
on:
  schedule:
    - cron: '0 2 * * *'  # Runs every day at 2 AM UTC
  workflow_dispatch:

jobs:
  test:
    runs-on: ubuntu-latest
    steps:
      - uses: actions/checkout@v4
      - name: Set up JDK 11
        uses: actions/setup-java@v4
        with:
          distribution: 'temurin'
          java-version: '11'
          cache: 'maven'
      - name: Run tests
        run: mvn test
```

### Practice 9: Manual Deployment Workflow

```yaml
name: Manual Deploy
on:
  workflow_dispatch:
    inputs:
      environment:
        description: 'Deployment environment'
        required: true
        type: choice
        options:
          - dev
          - staging
          - prod

jobs:
  deploy:
    runs-on: ubuntu-latest
    environment: ${{ inputs.environment }}
    steps:
      - uses: actions/checkout@v4
      - name: Deploy to ${{ inputs.environment }}
        run: echo "Deploying to ${{ inputs.environment }}"
```

### Practice 10: Complete CI/CD with Notifications

```yaml
name: Complete CI/CD
on:
  push:
    branches: [main, develop]
  pull_request:
    branches: [main]

jobs:
  build:
    runs-on: ubuntu-latest
    steps:
      - uses: actions/checkout@v4
      - name: Set up JDK 11
        uses: actions/setup-java@v4
        with:
          distribution: 'temurin'
          java-version: '11'
          cache: 'maven'
      - name: Build
        run: mvn clean package
      - name: Upload artifact
        uses: actions/upload-artifact@v4
        with:
          name: my-app
          path: target/*.jar

  test:
    runs-on: ubuntu-latest
    needs: build
    steps:
      - uses: actions/checkout@v4
      - name: Set up JDK 11
        uses: actions/setup-java@v4
        with:
          distribution: 'temurin'
          java-version: '11'
          cache: 'maven'
      - name: Run tests
        run: mvn test
      - name: Publish test results
        uses: EnricoMi/publish-unit-test-result-action@v2
        if: always()
        with:
          files: target/surefire-reports/*.xml

  deploy-dev:
    runs-on: ubuntu-latest
    needs: [build, test]
    if: github.ref == 'refs/heads/develop'
    environment: development
    steps:
      - name: Download artifact
        uses: actions/download-artifact@v4
        with:
          name: my-app
      - name: Deploy to Dev
        run: echo "Deploying to development"

  deploy-prod:
    runs-on: ubuntu-latest
    needs: [build, test]
    if: github.ref == 'refs/heads/main'
    environment: production
    steps:
      - name: Download artifact
        uses: actions/download-artifact@v4
        with:
          name: my-app
      - name: Deploy to Prod
        run: echo "Deploying to production"
      - name: Notify Slack
        uses: 8398a7/action-slack-notify@v3
        if: always()
        with:
          status: ${{ job.status }}
          text: 'Production deployment completed'
          webhook_url: ${{ secrets.SLACK_WEBHOOK }}
```

---

## CONCLUSION

This comprehensive guide covers all essential GitHub Actions and YAML CI/CD topics for interview preparation. Key takeaways:

1. **GitHub Actions Fundamentals**: YAML-based CI/CD, native GitHub integration
2. **Workflow Structure**: name, on, env, jobs, steps
3. **Triggers**: push, pull_request, workflow_dispatch, schedule
4. **Jobs and Steps**: Job dependencies, step types
5. **Runners**: GitHub-hosted vs self-hosted
6. **Variables and Secrets**: Environment variables, secure secrets
7. **Actions**: Marketplace actions, custom actions
8. **Matrix Strategy**: Parallel execution across configurations
9. **Caching**: Speed up workflows with dependency caching
10. **Artifacts**: Share files between jobs
11. **Integration**: Maven, TestNG, Playwright, Docker
12. **Deployment**: Environments, approvals, strategies
13. **Advanced**: Reusable workflows, composite actions
14. **Security**: Secrets, permissions, code scanning
15. **Best Practices**: Pin versions, limit permissions, review actions

Practice these workflows in your repository and be prepared to explain the "why" behind each approach. Good luck with your interview!
