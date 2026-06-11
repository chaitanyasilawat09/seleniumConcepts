# Jenkins & CI/CD - Complete Interview Preparation Guide

---

## TABLE OF CONTENTS
1. Jenkins Fundamentals
2. Jenkins Architecture
3. Jenkins Installation and Setup
4. Jenkins Jobs (Freestyle vs Pipeline)
5. Jenkins Pipeline Concepts
6. Declarative Pipeline
7. Scripted Pipeline
8. Jenkinsfile Syntax
9. Pipeline Stages and Steps
10. Pipeline Agents
11. Environment Variables
12. Credentials Management
13. Build Triggers
14. SCM Integration (Git)
15. Maven Integration
16. TestNG Integration with Jenkins
17. Playwright Integration with Jenkins
18. Jenkins Reporting
19. Email Notifications
20. Pipeline as Code
21. Shared Libraries
22. Docker Integration with Jenkins
23. Jenkins Security
24. Jenkins Best Practices
25. Common Interview Questions

---

## 1. JENKINS FUNDAMENTALS

### What is Jenkins?
Jenkins is an open-source automation server that enables continuous integration (CI) and continuous delivery (CD). It automates the building, testing, and deployment of applications.

### Key Features
- **Continuous Integration**: Automatically builds and tests code changes
- **Continuous Delivery**: Automates deployment to various environments
- **Plugin Ecosystem**: 1000+ plugins for integration with various tools
- **Distributed Builds**: Execute builds across multiple machines
- **Pipeline as Code**: Define build process in code (Jenkinsfile)
- **Extensible**: Easy to extend with custom plugins

### CI/CD Concepts

**Continuous Integration (CI):**
- Developers merge code changes frequently
- Automated builds and tests run on every commit
- Early detection of integration issues
- Faster feedback to developers

**Continuous Delivery (CD):**
- Automated deployment to staging/production
- Code is always in deployable state
- Manual approval for production deployment
- Reduces deployment risk and time

**Continuous Deployment:**
- Automated deployment to production
- No manual intervention
- Fully automated pipeline
- Requires robust testing

### Jenkins vs Other CI/CD Tools

| Feature | Jenkins | GitHub Actions | GitLab CI | CircleCI |
|---------|---------|---------------|-----------|----------|
| Open Source | ✅ | ❌ (limited) | ✅ | ❌ |
| Self-hosted | ✅ | ❌ | ✅ | ❌ |
| Plugin Ecosystem | 🌟 Largest | Limited | Good | Limited |
| Learning Curve | 📈 High | 📈 Low | 📈 Medium | 📈 Low |
| Pipeline as Code | ✅ | ✅ | ✅ | ✅ |
| Distributed Builds | ✅ | ❌ | ❌ | ❌ |
| Cost | Free (self-hosted) | Paid tiers | Free tier | Paid |

### Interview Questions

**Q1: What is Jenkins and why is it used?**
- Open-source automation server
- Used for CI/CD
- Automates build, test, deployment
- Integrates with various tools via plugins

**Q2: What is the difference between CI and CD?**
- CI: Continuous Integration - build and test automatically
- CD: Continuous Delivery/Deployment - automate deployment
- CI focuses on integration, CD on delivery
- CI is prerequisite for CD

**Q3: Why choose Jenkins over other CI/CD tools?**
- Open-source and free
- Largest plugin ecosystem
- Self-hosted option
- Highly customizable
- Distributed builds support

---

## 2. JENKINS ARCHITECTURE

### Master-Slave Architecture

```
┌─────────────────────────────────────┐
│         Jenkins Master              │
│  ┌───────────────────────────────┐  │
│  │   Scheduler                   │  │
│  │   Build Executor              │  │
│  │   Configuration               │  │
│  │   Web UI                      │  │
│  └───────────────────────────────┘  │
└─────────────────────────────────────┘
            │
            │ Connects to
            ↓
┌─────────────────────────────────────┐
│         Jenkins Slave/Agent         │
│  ┌───────────────────────────────┐  │
│  │   Executor Service           │  │
│  │   File System                 │  │
│  │   Build Tools                 │  │
│  └───────────────────────────────┘  │
└─────────────────────────────────────┘
```

### Jenkins Master
- **Scheduler**: Schedules builds on available agents
- **Build Executor**: Manages build execution
- **Configuration**: Stores job configurations
- **Web UI**: Provides user interface
- **Plugin Manager**: Manages plugins

### Jenkins Slave/Agent
- **Executor Service**: Executes build tasks
- **File System**: Workspace for builds
- **Build Tools**: Contains required tools (Java, Maven, etc.)
- **Agent Communication**: Communicates with master via JNLP/HTTP

### Communication Protocols
- **JNLP (Java Network Launching Protocol)**: Default for agent communication
- **HTTP**: Alternative communication method
- **SSH**: For launching agents via SSH

### Workspace
- Each job has its own workspace
- Located at `$JENKINS_HOME/jobs/<job_name>/workspace`
- Contains source code checked out from SCM
- Cleaned up between builds (configurable)

### Interview Questions

**Q1: Explain Jenkins Master-Slave architecture.**
- Master: Schedules and manages builds
- Slave/Agent: Executes build tasks
- Master communicates with agents via JNLP
- Enables distributed builds

**Q2: What is the purpose of Jenkins workspace?**
- Directory where builds execute
- Contains source code from SCM
- Job-specific workspace
- Can be cleaned between builds

**Q3: How do Jenkins master and agents communicate?**
- JNLP (Java Network Launching Protocol)
- HTTP protocol
- SSH for agent launching
- Agents register with master

---

## 3. JENKINS INSTALLATION AND SETUP

### Installation Methods

#### 1. WAR File Installation
```bash
# Download Jenkins WAR
wget http://updates.jenkins-ci.org/latest/jenkins.war

# Run Jenkins
java -jar jenkins.war

# Run with specific port
java -jar jenkins.war --httpPort=8081
```

#### 2. Docker Installation
```bash
# Pull Jenkins image
docker pull jenkins/jenkins:lts

# Run Jenkins container
docker run -d -p 8080:8080 -p 50000:50000 \
  -v jenkins_home:/var/jenkins_home \
  --name jenkins \
  jenkins/jenkins:lts

# Get initial admin password
docker exec jenkins cat /var/jenkins_home/secrets/initialAdminPassword
```

#### 3. Linux Package Installation
```bash
# Ubuntu/Debian
wget -q -O - https://pkg.jenkins.io/debian/jenkins.io.key | sudo apt-key add -
sudo sh -c 'echo deb http://pkg.jenkins.io/debian-stable binary/ > /etc/apt/sources.list.d/jenkins.list'
sudo apt update
sudo apt install jenkins

# Start Jenkins
sudo systemctl start jenkins
sudo systemctl enable jenkins
```

### Initial Setup
1. Access Jenkins at `http://localhost:8080`
2. Unlock Jenkins using initial admin password
3. Install suggested plugins or select plugins manually
4. Create first admin user
5. Configure Jenkins URL

### Essential Plugins
```bash
# Install via Manage Jenkins → Manage Plugins
- Pipeline Plugin
- Git Plugin
- Maven Integration Plugin
- Email Extension Plugin
- HTML Publisher Plugin
- Credentials Binding Plugin
- SSH Agent Plugin
- Docker Plugin
- Blue Ocean Plugin
```

### Configuration

**System Configuration:**
```groovy
// Manage Jenkins → Configure System
- Jenkins URL
- System Admin email address
- Executors (number of parallel builds)
- JDK installations
- Maven installations
- Git installations
```

**Global Tool Configuration:**
```groovy
// Manage Jenkins → Global Tool Configuration
- JDK installations
- Maven installations
- Git installations
- Ant installations
```

### Interview Questions

**Q1: What are the different ways to install Jenkins?**
- WAR file deployment
- Docker container
- Package manager (apt, yum)
- Kubernetes (Helm chart)

**Q2: What is the initial setup process for Jenkins?**
- Unlock with admin password
- Install plugins
- Create admin user
- Configure Jenkins URL

**Q3: What are essential Jenkins plugins for automation?**
- Pipeline Plugin
- Git Plugin
- Maven Integration
- Email Extension
- HTML Publisher

---

## 4. JENKINS JOBS (FREESTYLE VS PIPELINE)

### Freestyle Projects

**Characteristics:**
- UI-based configuration
- No code required
- Simple to set up
- Limited flexibility
- Not version-controlled

**Example Configuration:**
```
Job Name: My-Freestyle-Job
Source Code Management: Git
  - Repository URL: https://github.com/user/repo.git
  - Branch: */main
Build Triggers:
  - Poll SCM: H/5 * * * *
Build Environment:
  - Delete workspace before build starts
Build:
  - Invoke top-level Maven targets
    - Goals: clean test
Post-build Actions:
  - Publish HTML reports
  - Email notification
```

**Advantages:**
- Easy to create and configure
- No programming knowledge required
- Good for simple tasks

**Disadvantages:**
- Not version-controlled
- Difficult to maintain
- Limited flexibility
- Cannot reuse logic

### Pipeline Projects

**Characteristics:**
- Code-based configuration (Jenkinsfile)
- Version-controlled
- Highly flexible
- Reusable logic
- Supports complex workflows

**Example Pipeline:**
```groovy
pipeline {
    agent any
    stages {
        stage('Build') {
            steps {
                sh 'mvn clean compile'
            }
        }
        stage('Test') {
            steps {
                sh 'mvn test'
            }
        }
        stage('Deploy') {
            steps {
                sh 'mvn deploy'
            }
        }
    }
}
```

**Advantages:**
- Version-controlled (Jenkinsfile in repo)
- Reusable and maintainable
- Supports complex logic
- Better for CI/CD
- Pipeline as Code

**Disadvantages:**
- Requires Groovy knowledge
- Steeper learning curve
- More complex setup

### Comparison

| Feature | Freestyle | Pipeline |
|---------|-----------|----------|
| Configuration | UI-based | Code-based |
| Version Control | ❌ | ✅ |
| Flexibility | Limited | High |
| Reusability | ❌ | ✅ |
| Learning Curve | Low | High |
| Best For | Simple tasks | CI/CD pipelines |

### Interview Questions

**Q1: What is the difference between Freestyle and Pipeline jobs?**
- Freestyle: UI-based, not version-controlled
- Pipeline: Code-based, version-controlled
- Pipeline more flexible and maintainable
- Freestyle simpler but limited

**Q2: When would you use Freestyle over Pipeline?**
- Simple, one-off tasks
- No version control needed
- Quick setup
- Limited build requirements

**Q3: What are the advantages of Pipeline jobs?**
- Version-controlled (Jenkinsfile)
- Reusable logic
- Complex workflows
- Better for CI/CD
- Pipeline as Code

---

## 5. JENKINS PIPELINE CONCEPTS

### What is a Pipeline?
A pipeline is a suite of plugins that supports implementing and integrating continuous delivery pipelines into Jenkins.

### Pipeline Types

#### 1. Declarative Pipeline
- Simple, structured syntax
- Uses pipeline block
- Easier to learn and use
- Best for most use cases

#### 2. Scripted Pipeline
- Groovy-based programming
- More flexible and powerful
- Requires Groovy knowledge
- Best for complex logic

### Pipeline Concepts

**Pipeline:**
- The entire CD process
- Defined in Jenkinsfile
- Contains multiple stages

**Stage:**
- Logical division of pipeline
- Represents a phase (Build, Test, Deploy)
- Contains multiple steps

**Step:**
- Single task in pipeline
- Can be shell command, script, etc.
- Smallest unit of work

**Node:**
- Machine where pipeline executes
- Can be master or agent
- Defined by agent directive

**Agent:**
- Specifies where pipeline runs
- Can be any, none, label, docker, etc.
- Allocates workspace

### Pipeline Execution Flow

```
┌─────────────────────────────────────┐
│         Pipeline Start              │
└─────────────────────────────────────┘
              ↓
┌─────────────────────────────────────┐
│         Checkout SCM                 │
└─────────────────────────────────────┘
              ↓
┌─────────────────────────────────────┐
│         Stage: Build                │
│  ┌───────────────────────────────┐  │
│  │   Step: Maven Build           │  │
│  └───────────────────────────────┘  │
└─────────────────────────────────────┘
              ↓
┌─────────────────────────────────────┐
│         Stage: Test                 │
│  ┌───────────────────────────────┐  │
│  │   Step: Run Tests             │  │
│  └───────────────────────────────┘  │
└─────────────────────────────────────┘
              ↓
┌─────────────────────────────────────┐
│         Stage: Deploy               │
│  ┌───────────────────────────────┐  │
│  │   Step: Deploy to Env         │  │
│  └───────────────────────────────┘  │
└─────────────────────────────────────┘
              ↓
┌─────────────────────────────────────┐
│         Pipeline End                 │
└─────────────────────────────────────┘
```

### Jenkinsfile
- Text file that defines pipeline
- Stored in SCM (Git)
- Version-controlled
- Can be branched and tagged
- Supports both declarative and scripted syntax

### Interview Questions

**Q1: What is a Jenkins Pipeline?**
- Suite of plugins for CD
- Defines entire build process
- Contains stages and steps
- Defined in Jenkinsfile

**Q2: What are the different types of Jenkins Pipelines?**
- Declarative Pipeline: Simple, structured
- Scripted Pipeline: Groovy-based, flexible
- Declarative recommended for most cases

**Q3: What is the difference between Stage and Step?**
- Stage: Logical division (Build, Test, Deploy)
- Step: Single task within stage
- Pipeline contains stages
- Stage contains steps

---

## 6. DECLARATIVE PIPELINE

### Basic Structure

```groovy
pipeline {
    agent any
    stages {
        stage('Build') {
            steps {
                echo 'Building...'
            }
        }
        stage('Test') {
            steps {
                echo 'Testing...'
            }
        }
        stage('Deploy') {
            steps {
                echo 'Deploying...'
            }
        }
    }
}
```

### Agent Directive

```groovy
pipeline {
    // Run on any available agent
    agent any
    
    // Run on specific agent label
    agent { label 'linux' }
    
    // Run on specific agent
    agent { node 'agent-1' }
    
    // Run in Docker container
    agent {
        docker {
            image 'maven:3.8.1-openjdk-11'
        }
    }
    
    // No agent (for scripted pipeline)
    agent none
}
```

### Stages and Steps

```groovy
pipeline {
    agent any
    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }
        
        stage('Build') {
            steps {
                sh 'mvn clean compile'
                // For Windows
                bat 'mvn clean compile'
            }
        }
        
        stage('Test') {
            steps {
                sh 'mvn test'
            }
        }
        
        stage('Package') {
            steps {
                sh 'mvn package'
            }
        }
    }
}
```

### Post Section

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
    post {
        always {
            echo 'Pipeline completed'
        }
        success {
            echo 'Pipeline succeeded'
            emailext subject: 'Build Success',
                body: 'Build completed successfully',
                to: 'team@example.com'
        }
        failure {
            echo 'Pipeline failed'
            emailext subject: 'Build Failed',
                body: 'Build failed. Check logs.',
                to: 'team@example.com'
        }
        unstable {
            echo 'Pipeline unstable'
        }
        cleaned {
            echo 'Pipeline cleaned'
        }
    }
}
```

### Environment Variables

```groovy
pipeline {
    agent any
    environment {
        // Simple variable
        MAVEN_HOME = '/opt/maven'
        
        // From credentials
        DB_PASSWORD = credentials('db-password')
        
        // Dynamic value
        BUILD_NUMBER = "${env.BUILD_NUMBER}"
        
        // Path
        PATH = "${MAVEN_HOME}/bin:${env.PATH}"
    }
    stages {
        stage('Build') {
            steps {
                sh 'echo $MAVEN_HOME'
                sh 'mvn -version'
            }
        }
    }
}
```

### Parameters

```groovy
pipeline {
    agent any
    parameters {
        string(name: 'BRANCH', defaultValue: 'main', description: 'Git branch')
        choice(name: 'ENVIRONMENT', choices: ['dev', 'staging', 'prod'], description: 'Deployment environment')
        booleanParam(name: 'SKIP_TESTS', defaultValue: false, description: 'Skip tests')
    }
    stages {
        stage('Checkout') {
            steps {
                git branch: params.BRANCH, url: 'https://github.com/user/repo.git'
            }
        }
        stage('Test') {
            when {
                expression { !params.SKIP_TESTS }
            }
            steps {
                sh 'mvn test'
            }
        }
    }
}
```

### When Directive

```groovy
pipeline {
    agent any
    stages {
        stage('Deploy to Dev') {
            when {
                branch 'main'
            }
            steps {
                sh 'deploy to dev'
            }
        }
        
        stage('Deploy to Prod') {
            when {
                branch 'release'
            }
            steps {
                sh 'deploy to prod'
            }
        }
        
        stage('Performance Test') {
            when {
                expression {
                    return params.RUN_PERFORMANCE_TEST == true
                }
            }
            steps {
                sh 'run performance tests'
            }
        }
    }
}
```

### Parallel Execution

```groovy
pipeline {
    agent any
    stages {
        stage('Parallel Tests') {
            parallel {
                stage('Unit Tests') {
                    steps {
                        sh 'mvn test'
                    }
                }
                stage('Integration Tests') {
                    steps {
                        sh 'mvn verify'
                    }
                }
                stage('Code Quality') {
                    steps {
                        sh 'mvn sonar:sonar'
                    }
                }
            }
        }
    }
}
```

### Options

```groovy
pipeline {
    agent any
    options {
        // Timeout for entire pipeline
        timeout(time: 30, unit: 'MINUTES')
        
        // Retry entire pipeline
        retry(3)
        
        // Skip default checkout
        skipDefaultCheckout()
        
        // Keep builds for 10 days
        buildDiscarder(logRotator(numToKeepStr: '10'))
        
        // Disable concurrent builds
        disableConcurrentBuilds()
        
        // Add timestamps to console output
        timestamps()
        
        // ANSI color output
        ansiColor('xterm')
    }
    stages {
        stage('Build') {
            steps {
                sh 'mvn clean package'
            }
        }
    }
}
```

### Tools

```groovy
pipeline {
    agent any
    tools {
        maven 'Maven 3.8.1'
        jdk 'JDK 11'
        git 'Git 2.30'
    }
    stages {
        stage('Build') {
            steps {
                sh 'mvn -version'
                sh 'java -version'
            }
        }
    }
}
```

### Triggers

```groovy
pipeline {
    agent any
    triggers {
        // Poll SCM every 5 minutes
        pollSCM('H/5 * * * *')
        
        // Cron schedule (every day at 2 AM)
        cron('H 2 * * *')
        
        // Trigger on upstream project
        upstream(upstreamProjects: 'upstream-job', threshold: hudson.model.Result.SUCCESS)
        
        // Trigger on GitHub push
        githubPush()
    }
    stages {
        stage('Build') {
            steps {
                sh 'mvn clean package'
            }
        }
    }
}
```

### Interview Questions

**Q1: What is Declarative Pipeline?**
- Simple, structured syntax
- Uses pipeline block
- Easier to learn
- Best for most use cases

**Q2: What are the key sections in Declarative Pipeline?**
- agent: Where pipeline runs
- stages: Logical divisions
- steps: Tasks within stages
- post: Actions after completion
- environment: Variables
- parameters: User input
- when: Conditional execution

**Q3: How do you run stages in parallel in Declarative Pipeline?**
- Use parallel block within stage
- Define multiple stages inside parallel
- Each stage runs on separate executor
- Useful for faster execution

---

## 7. SCRIPTED PIPELINE

### Basic Structure

```groovy
node {
    stage('Checkout') {
        checkout scm
    }
    
    stage('Build') {
        sh 'mvn clean compile'
    }
    
    stage('Test') {
        sh 'mvn test'
    }
    
    stage('Deploy') {
        sh 'mvn deploy'
    }
}
```

### Node Block

```groovy
// Run on any node
node {
    // Pipeline steps
}

// Run on specific node label
node('linux') {
    // Pipeline steps
}

// Run on specific node
node('agent-1') {
    // Pipeline steps
}
```

### Conditional Logic

```groovy
node {
    def branch = env.BRANCH_NAME ?: 'main'
    
    stage('Checkout') {
        checkout scm
    }
    
    stage('Build') {
        sh 'mvn clean compile'
    }
    
    stage('Test') {
        if (branch == 'main') {
            sh 'mvn test'
        } else {
            echo 'Skipping tests on feature branch'
        }
    }
    
    stage('Deploy') {
        if (branch == 'release') {
            sh 'mvn deploy'
        } else {
            echo 'Skipping deployment'
        }
    }
}
```

### Try-Catch-Finally

```groovy
node {
    try {
        stage('Checkout') {
            checkout scm
        }
        
        stage('Build') {
            sh 'mvn clean package'
        }
        
        stage('Test') {
            sh 'mvn test'
        }
    } catch (Exception e) {
        currentBuild.result = 'FAILURE'
        echo "Build failed: ${e}"
    } finally {
        stage('Cleanup') {
            echo 'Cleaning up workspace'
            cleanWs()
        }
    }
}
```

### Loops

```groovy
node {
    stage('Parallel Tests') {
        def tests = ['test1', 'test2', 'test3']
        
        def parallelStages = [:]
        
        for (test in tests) {
            parallelStages[test] = {
                node {
                    stage(test) {
                        sh "mvn test -Dtest=${test}"
                    }
                }
            }
        }
        
        parallel parallelStages
    }
}
```

### Functions

```groovy
def buildAndTest(String module) {
    stage("Build ${module}") {
        sh "mvn clean install -pl ${module}"
    }
    
    stage("Test ${module}") {
        sh "mvn test -pl ${module}"
    }
}

node {
    stage('Checkout') {
        checkout scm
    }
    
    buildAndTest('module1')
    buildAndTest('module2')
}
```

### Script Block in Declarative

```groovy
pipeline {
    agent any
    stages {
        stage('Complex Logic') {
            steps {
                script {
                    def files = findFiles(glob: '**/*.java')
                    echo "Found ${files.size()} Java files"
                    
                    if (files.size() > 100) {
                        echo 'Large project detected'
                    }
                }
            }
        }
    }
}
```

### Interview Questions

**Q1: What is Scripted Pipeline?**
- Groovy-based programming
- More flexible than declarative
- Requires Groovy knowledge
- Best for complex logic

**Q2: When would you use Scripted over Declarative Pipeline?**
- Complex conditional logic
- Custom functions and loops
- Advanced error handling
- Dynamic pipeline generation

**Q3: How do you handle exceptions in Scripted Pipeline?**
- Use try-catch-finally blocks
- Set currentBuild.result
- Use finally for cleanup
- Echo error messages

---

## 8. JENKINSFILE SYNTAX

### File Location
- Stored in project root
- Named `Jenkinsfile` (no extension)
- Version-controlled in Git
- Can be in subdirectories

### Basic Jenkinsfile

```groovy
pipeline {
    agent any
    stages {
        stage('Build') {
            steps {
                echo 'Building...'
            }
        }
    }
}
```

### Multibranch Pipeline

```groovy
// Jenkinsfile in each branch
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

// Branch-specific logic
pipeline {
    agent any
    stages {
        stage('Build') {
            steps {
                sh 'mvn clean package'
            }
        }
        stage('Deploy') {
            when {
                branch 'main'
            }
            steps {
                sh 'deploy to production'
            }
        }
    }
}
```

### Loading Jenkinsfile from SCM

```groovy
// Pipeline job configuration
Pipeline script from SCM: Git
Repository URL: https://github.com/user/repo.git
Script Path: Jenkinsfile
Branch: */main
```

### Shared Jenkinsfile

```groovy
// Shared library
@Library('shared-library@main') _

pipeline {
    agent any
    stages {
        stage('Build') {
            steps {
                buildProject()
            }
        }
    }
}
```

### Interview Questions

**Q1: Where should Jenkinsfile be located?**
- Project root directory
- Named exactly "Jenkinsfile"
- Version-controlled in Git
- Can be in subdirectories

**Q2: How does Jenkins load Jenkinsfile from SCM?**
- Configure Pipeline job
- Select SCM (Git)
- Specify script path (Jenkinsfile)
- Jenkins checks out and loads file

**Q3: What is Multibranch Pipeline?**
- Automatically creates jobs for branches
- Each branch has its own Jenkinsfile
- Branch-specific pipeline logic
- Useful for Git flow workflow

---

## 9. PIPELINE STAGES AND STEPS

### Stages

**Purpose:** Logical division of pipeline into phases

```groovy
pipeline {
    agent any
    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }
        
        stage('Build') {
            steps {
                sh 'mvn clean compile'
            }
        }
        
        stage('Test') {
            steps {
                sh 'mvn test'
            }
        }
        
        stage('Package') {
            steps {
                sh 'mvn package'
            }
        }
        
        stage('Deploy') {
            steps {
                sh 'deploy.sh'
            }
        }
    }
}
```

### Steps

**Purpose:** Individual tasks within a stage

```groovy
pipeline {
    agent any
    stages {
        stage('Build') {
            steps {
                // Shell command
                sh 'mvn clean compile'
                
                // Batch command (Windows)
                bat 'mvn clean compile'
                
                // PowerShell
                powershell 'mvn clean compile'
                
                // Echo message
                echo 'Building...'
                
                // Checkout SCM
                checkout scm
                
                // Git clone
                git url: 'https://github.com/user/repo.git', branch: 'main'
                
                // Archive artifacts
                archiveArtifacts artifacts: 'target/*.jar', fingerprint: true
                
                // Publish HTML reports
                publishHTML([
                    reportDir: 'target/site/surefire-report',
                    reportFiles: 'index.html',
                    reportName: 'Test Report'
                ])
                
                // JUnit test results
                junit 'target/surefire-reports/*.xml'
                
                // Send email
                emailext subject: 'Build Success',
                    body: 'Build completed successfully',
                    to: 'team@example.com'
            }
        }
    }
}
```

### Common Steps

**Checkout:**
```groovy
checkout scm
checkout([$class: 'GitSCM', 
    branches: [[name: '*/main']], 
    userRemoteConfigs: [[url: 'https://github.com/user/repo.git']]])
```

**Shell/Batch:**
```groovy
sh 'ls -la'
sh './build.sh'
bat 'dir'
powershell 'Get-ChildItem'
```

**Archive:**
```groovy
archiveArtifacts 'target/*.jar'
archiveArtifacts artifacts: '**/*.jar', fingerprint: true
```

**Publish Reports:**
```groovy
junit 'target/surefire-reports/*.xml'
publishHTML target: [
    reportDir: 'target/site/surefire-report',
    reportFiles: 'index.html',
    reportName: 'Test Report'
]
```

**Clean Workspace:**
```groovy
cleanWs()
deleteDir()
```

### Interview Questions

**Q1: What is the difference between Stage and Step?**
- Stage: Logical division (Build, Test, Deploy)
- Step: Single task within stage
- Pipeline contains stages
- Stage contains steps

**Q2: What are common steps used in Jenkins Pipeline?**
- checkout: Get source code
- sh/bat: Execute commands
- archiveArtifacts: Save build artifacts
- junit: Publish test results
- publishHTML: Publish HTML reports

**Q3: How do you clean workspace in Pipeline?**
- cleanWs() step
- deleteDir() step
- Can be in post section
- Useful for fresh builds

---

## 10. PIPELINE AGENTS

### Agent Types

#### 1. Any
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

#### 2. Label
```groovy
pipeline {
    agent { label 'linux' }
    stages {
        stage('Build') {
            steps {
                sh 'mvn clean package'
            }
        }
    }
}
```

#### 3. Node
```groovy
pipeline {
    agent { node 'agent-1' }
    stages {
        stage('Build') {
            steps {
                sh 'mvn clean package'
            }
        }
    }
}
```

#### 4. Docker
```groovy
pipeline {
    agent {
        docker {
            image 'maven:3.8.1-openjdk-11'
            args '-v $HOME/.m2:/root/.m2'
        }
    }
    stages {
        stage('Build') {
            steps {
                sh 'mvn clean package'
            }
        }
    }
}
```

#### 5. Dockerfile
```groovy
pipeline {
    agent {
        dockerfile {
            filename 'Dockerfile'
            dir 'build'
            args '-v $HOME/.m2:/root/.m2'
        }
    }
    stages {
        stage('Build') {
            steps {
                sh 'mvn clean package'
            }
        }
    }
}
```

#### 6. None
```groovy
pipeline {
    agent none
    stages {
        stage('Build') {
            agent { label 'linux' }
            steps {
                sh 'mvn clean package'
            }
        }
        stage('Test') {
            agent { label 'windows' }
            steps {
                bat 'mvn test'
            }
        }
    }
}
```

### Stage-Level Agent

```groovy
pipeline {
    agent none
    stages {
        stage('Build') {
            agent { label 'build-agent' }
            steps {
                sh 'mvn clean package'
            }
        }
        stage('Test') {
            agent { label 'test-agent' }
            steps {
                sh 'mvn test'
            }
        }
        stage('Deploy') {
            agent { label 'deploy-agent' }
            steps {
                sh 'deploy.sh'
            }
        }
    }
}
```

### Agent Options

```groovy
pipeline {
    agent {
        docker {
            image 'maven:3.8.1-openjdk-11'
            args '-v $HOME/.m2:/root/.m2'
            registryUrl 'https://registry.example.com'
            registryCredentialsId 'docker-registry-creds'
            alwaysPull true
        }
    }
    stages {
        stage('Build') {
            steps {
                sh 'mvn clean package'
            }
        }
    }
}
```

### Interview Questions

**Q1: What are the different types of agents in Jenkins Pipeline?**
- any: Any available agent
- label: Agent with specific label
- node: Specific agent node
- docker: Docker container
- dockerfile: Build from Dockerfile
- none: No default agent

**Q2: How do you use Docker agents in Jenkins Pipeline?**
- Specify docker image
- Can mount volumes
- Can pull always
- Useful for consistent build environment

**Q3: When would you use stage-level agents?**
- Different stages need different environments
- Optimize resource usage
- Specific tools on specific agents
- Cross-platform builds

---

## 11. ENVIRONMENT VARIABLES

### Defining Environment Variables

```groovy
pipeline {
    agent any
    environment {
        // Simple string
        APP_NAME = 'MyApp'
        
        // From credentials
        DB_PASSWORD = credentials('db-password')
        
        // From environment
        PATH = "${env.PATH}:/opt/tools"
        
        // Dynamic value
        BUILD_TIME = "${new Date().toString()}"
        
        // From parameter
        DEPLOY_ENV = "${params.ENVIRONMENT}"
    }
    stages {
        stage('Build') {
            steps {
                sh "echo Building ${APP_NAME}"
                sh "echo Build time: ${BUILD_TIME}"
            }
        }
    }
}
```

### Using Environment Variables

```groovy
pipeline {
    agent any
    environment {
        MAVEN_OPTS = '-Xmx1024m'
    }
    stages {
        stage('Build') {
            steps {
                // Access in shell
                sh 'echo $MAVEN_OPTS'
                
                // Access in Groovy
                echo "Maven options: ${env.MAVEN_OPTS}"
            }
        }
    }
}
```

### Global Environment Variables

**Configure in Jenkins:**
1. Manage Jenkins → Configure System
2. Global properties → Environment variables
3. Add key-value pairs

**Access in Pipeline:**
```groovy
pipeline {
    agent any
    stages {
        stage('Build') {
            steps {
                sh 'echo $GLOBAL_VAR'
                echo "${env.GLOBAL_VAR}"
            }
        }
    }
}
```

### With Credentials

```groovy
pipeline {
    agent any
    environment {
        // Username/password credentials
        AWS_ACCESS_KEY = credentials('aws-access-key')
        AWS_SECRET_KEY = credentials('aws-secret-key')
        
        // Secret text
        API_KEY = credentials('api-key')
        
        // Secret file
        KEY_FILE = credentials('ssh-key-file')
    }
    stages {
        stage('Deploy') {
            steps {
                sh 'aws s3 ls'
                sh 'echo $API_KEY'
            }
        }
    }
}
```

### Interview Questions

**Q1: How do you define environment variables in Jenkins Pipeline?**
- Use environment block
- Define key-value pairs
- Can use credentials
- Can reference parameters

**Q2: How do you access environment variables in Pipeline?**
- In shell: $VAR_NAME
- In Groovy: ${env.VAR_NAME}
- Can use withEnv step
- Can load from file

**Q3: How do you use credentials as environment variables?**
- Use credentials() function
- Pass credential ID
- Automatically masked in logs
- Supports username/password, secret text, file

---

## 12. CREDENTIALS MANAGEMENT

### Credential Types

#### 1. Username with Password
```groovy
pipeline {
    agent any
    environment {
        AWS_ACCESS_KEY = credentials('aws-creds')
    }
    stages {
        stage('Deploy') {
            steps {
                sh 'echo $AWS_ACCESS_KEY'
                // AWS_ACCESS_KEY contains username:password
            }
        }
    }
}
```

#### 2. Secret Text
```groovy
pipeline {
    agent any
    environment {
        API_KEY = credentials('api-key')
    }
    stages {
        stage('Deploy') {
            steps {
                sh 'echo $API_KEY'
            }
        }
    }
}
```

#### 3. Secret File
```groovy
pipeline {
    agent any
    stages {
        stage('Deploy') {
            steps {
                withCredentials([file(credentialsId: 'ssh-key', variable: 'KEY_FILE')]) {
                    sh 'ssh -i $KEY_FILE user@server'
                }
            }
        }
    }
}
```

#### 4. SSH Username with Private Key
```groovy
pipeline {
    agent any
    stages {
        stage('Deploy') {
            steps {
                withCredentials([sshUserPrivateKey(
                    credentialsId: 'ssh-creds',
                    keyFileVariable: 'KEY_FILE',
                    usernameVariable: 'USER'
                )]) {
                    sh 'ssh -i $KEY_FILE $USER@server'
                }
            }
        }
    }
}
```

### Creating Credentials

**Via Jenkins UI:**
1. Credentials → System → Global credentials
2. Add Credentials
3. Select type (Username/Password, Secret Text, etc.)
4. Enter values
5. Save with ID

**Via Pipeline:**
```groovy
withCredentials([usernamePassword(
    credentialsId: 'my-creds',
    usernameVariable: 'USER',
    passwordVariable: 'PASS'
)]) {
    sh "curl -u $USER:$PASS https://api.example.com"
}
```

### Binding Credentials

```groovy
pipeline {
    agent any
    stages {
        stage('Deploy') {
            steps {
                withCredentials([
                    string(credentialsId: 'api-key', variable: 'API_KEY'),
                    usernamePassword(
                        credentialsId: 'db-creds',
                        usernameVariable: 'DB_USER',
                        passwordVariable: 'DB_PASS'
                    )
                ]) {
                    sh 'deploy.sh'
                }
            }
        }
    }
}
```

### Masking Credentials

```groovy
pipeline {
    agent any
    stages {
        stage('Deploy') {
            steps {
                withCredentials([string(credentialsId: 'api-key', variable: 'API_KEY')]) {
                    // API_KEY is automatically masked in logs
                    sh "echo Deploying with key"
                    sh "curl -H 'Authorization: Bearer $API_KEY' https://api.example.com"
                }
            }
        }
    }
}
```

### Interview Questions

**Q1: What are the different credential types in Jenkins?**
- Username with password
- Secret text
- Secret file
- SSH username with private key
- Certificate

**Q2: How do you use credentials in Jenkins Pipeline?**
- Use credentials() function
- Use withCredentials block
- Pass credential ID
- Automatically masked in logs

**Q3: How does Jenkins mask credentials in logs?**
- Automatically replaces with ****
- Works with credentials binding
- Prevents credential leakage
- Configurable in security settings

---

## 13. BUILD TRIGGERS

### Poll SCM

```groovy
pipeline {
    agent any
    triggers {
        // Poll every 5 minutes
        pollSCM('H/5 * * * *')
        
        // Poll every hour
        pollSCM('H * * * *')
        
        // Poll every day at midnight
        pollSCM('H 0 * * *')
    }
    stages {
        stage('Build') {
            steps {
                sh 'mvn clean package'
            }
        }
    }
}
```

### Cron Schedule

```groovy
pipeline {
    agent any
    triggers {
        // Every day at 2 AM
        cron('H 2 * * *')
        
        // Every Monday at 9 AM
        cron('H 9 * * 1')
        
        // Every 6 hours
        cron('H */6 * * *')
        
        // Every 15 minutes
        cron('H/15 * * * *')
    }
    stages {
        stage('Build') {
            steps {
                sh 'mvn clean package'
            }
        }
    }
}
```

### Upstream Projects

```groovy
pipeline {
    agent any
    triggers {
        upstream(
            upstreamProjects: 'upstream-job',
            threshold: hudson.model.Result.SUCCESS
        )
    }
    stages {
        stage('Build') {
            steps {
                sh 'mvn clean package'
            }
        }
    }
}
```

### GitHub Push

```groovy
pipeline {
    agent any
    triggers {
        githubPush()
    }
    stages {
        stage('Build') {
            steps {
                sh 'mvn clean package'
            }
        }
    }
}
```

### Manual Trigger with Parameters

```groovy
pipeline {
    agent any
    parameters {
        string(name: 'BRANCH', defaultValue: 'main', description: 'Git branch')
        choice(name: 'ENVIRONMENT', choices: ['dev', 'staging', 'prod'], description: 'Environment')
    }
    stages {
        stage('Build') {
            steps {
                git branch: params.BRANCH, url: 'https://github.com/user/repo.git'
                sh 'mvn clean package'
            }
        }
    }
}
```

### Webhook Triggers

**GitHub Webhook:**
1. Configure GitHub repository webhook
2. URL: `http://jenkins-server/github-webhook/`
3. Select events (push, pull request)
4. Jenkins automatically triggers on push

**GitLab Webhook:**
1. Configure GitLab project webhook
2. URL: `http://jenkins-server/project/job-name`
3. Select events
4. Jenkins triggers on events

### Interview Questions

**Q1: What are the different types of build triggers in Jenkins?**
- Poll SCM: Check for changes periodically
- Cron: Scheduled builds
- Upstream: Triggered by other jobs
- Webhook: Triggered by SCM events
- Manual: Triggered by user

**Q2: How does Poll SCM trigger work?**
- Jenkins polls SCM at specified interval
- Checks for new commits
- Triggers build if changes found
- Can be resource-intensive

**Q3: What is the difference between Poll SCM and Webhook?**
- Poll SCM: Jenkins checks periodically
- Webhook: SCM notifies Jenkins
- Webhook is faster and more efficient
- Poll SCM is simpler to set up

---

## 14. SCM INTEGRATION (GIT)

### Git Configuration

**Global Tool Configuration:**
1. Manage Jenkins → Global Tool Configuration
2. Git installations
3. Add Git
4. Specify path or auto-install

### Checkout from Git

```groovy
pipeline {
    agent any
    stages {
        stage('Checkout') {
            steps {
                // Simple checkout
                checkout scm
                
                // Specific branch
                git branch: 'main', url: 'https://github.com/user/repo.git'
                
                // With credentials
                git branch: 'main',
                    url: 'https://github.com/user/repo.git',
                    credentialsId: 'git-creds'
                
                // Specific commit
                git url: 'https://github.com/user/repo.git',
                    branch: 'main'
                
                // Tag
                git url: 'https://github.com/user/repo.git',
                    branch: 'refs/tags/v1.0.0'
            }
        }
    }
}
```

### Multiple Repositories

```groovy
pipeline {
    agent any
    stages {
        stage('Checkout') {
            steps {
                dir('app') {
                    git url: 'https://github.com/user/app.git', branch: 'main'
                }
                dir('config') {
                    git url: 'https://github.com/user/config.git', branch: 'main'
                }
            }
        }
    }
}
```

### Git Parameters

```groovy
pipeline {
    agent any
    parameters {
        gitParameter(
            name: 'BRANCH',
            type: 'PT_BRANCH',
            branchFilter: 'origin/(.*)',
            defaultValue: 'main',
            selectedValue: 'DEFAULT',
            sortMode: 'ASCENDING_SMART',
            description: 'Select branch'
        )
    }
    stages {
        stage('Checkout') {
            steps {
                git branch: params.BRANCH, url: 'https://github.com/user/repo.git'
            }
        }
    }
}
```

### Sparse Checkout

```groovy
pipeline {
    agent any
    stages {
        stage('Checkout') {
            steps {
                checkout([
                    $class: 'GitSCM',
                    branches: [[name: '*/main']],
                    userRemoteConfigs: [[url: 'https://github.com/user/repo.git']],
                    extensions: [
                        [$class: 'SparseCheckoutPaths', 
                         sparseCheckoutPaths: [[path: 'src/'], [path: 'pom.xml']]]
                    ]
                ])
            }
        }
    }
}
```

### Interview Questions

**Q1: How do you checkout code from Git in Jenkins Pipeline?**
- Use checkout scm step
- Use git step with URL and branch
- Can specify credentials
- Can checkout specific commit/tag

**Q2: How do you checkout multiple repositories in Pipeline?**
- Use dir step for each repo
- Specify different directories
- Each checkout in its own directory
- Useful for multi-module projects

**Q3: What is sparse checkout in Git?**
- Checkout only specific directories
- Reduces checkout time
- Saves disk space
- Useful for large repositories

---

## 15. MAVEN INTEGRATION

### Maven Configuration

**Global Tool Configuration:**
1. Manage Jenkins → Global Tool Configuration
2. Maven installations
3. Add Maven
4. Specify name and MAVEN_HOME

### Maven Build

```groovy
pipeline {
    agent any
    tools {
        maven 'Maven 3.8.1'
    }
    stages {
        stage('Build') {
            steps {
                sh 'mvn clean compile'
            }
        }
        stage('Test') {
            steps {
                sh 'mvn test'
            }
        }
        stage('Package') {
            steps {
                sh 'mvn package'
            }
        }
    }
}
```

### Maven with Custom Settings

```groovy
pipeline {
    agent any
    stages {
        stage('Build') {
            steps {
                withMaven(
                    maven: 'Maven 3.8.1',
                    mavenSettingsConfig: 'my-maven-settings'
                ) {
                    sh 'mvn clean deploy'
                }
            }
        }
    }
}
```

### Maven Goals

```groovy
pipeline {
    agent any
    stages {
        stage('Clean') {
            steps {
                sh 'mvn clean'
            }
        }
        stage('Compile') {
            steps {
                sh 'mvn compile'
            }
        }
        stage('Test') {
            steps {
                sh 'mvn test'
            }
        }
        stage('Package') {
            steps {
                sh 'mvn package'
            }
        }
        stage('Install') {
            steps {
                sh 'mvn install'
            }
        }
        stage('Deploy') {
            steps {
                sh 'mvn deploy'
            }
        }
    }
}
```

### Maven with Profiles

```groovy
pipeline {
    agent any
    stages {
        stage('Build') {
            steps {
                sh 'mvn clean package -Pdev'
            }
        }
        stage('Deploy') {
            when {
                branch 'main'
            }
            steps {
                sh 'mvn deploy -Pprod'
            }
        }
    }
}
```

### Interview Questions

**Q1: How do you integrate Maven with Jenkins?**
- Configure Maven in Global Tool Configuration
- Use tools block in Pipeline
- Use withMaven wrapper
- Specify Maven installation

**Q2: What are common Maven goals used in Jenkins?**
- clean: Clean build directory
- compile: Compile source code
- test: Run tests
- package: Create JAR/WAR
- install: Install to local repo
- deploy: Deploy to remote repo

**Q3: How do you use Maven profiles in Jenkins?**
- Use -P flag with profile name
- Can use different profiles per stage
- Useful for environment-specific builds
- Can be parameterized

---

## 16. TESTNG INTEGRATION WITH JENKINS

### TestNG Configuration

**pom.xml:**
```xml
<plugin>
    <groupId>org.apache.maven.plugins</groupId>
    <artifactId>maven-surefire-plugin</artifactId>
    <version>3.0.0-M5</version>
    <configuration>
        <suiteXmlFiles>
            <suiteXmlFile>testng.xml</suiteXmlFile>
        </suiteXmlFiles>
    </configuration>
</plugin>
```

### Running TestNG Tests

```groovy
pipeline {
    agent any
    stages {
        stage('Test') {
            steps {
                sh 'mvn test'
            }
        }
        post {
            always {
                junit 'target/surefire-reports/*.xml'
            }
        }
    }
}
```

### TestNG with XML

```groovy
pipeline {
    agent any
    stages {
        stage('Test') {
            steps {
                sh 'mvn test -DsuiteXmlFile=testng.xml'
            }
        }
        post {
            always {
                junit 'target/surefire-reports/*.xml'
            }
        }
    }
}
```

### Parallel Test Execution

```groovy
pipeline {
    agent any
    stages {
        stage('Test') {
            steps {
                sh 'mvn test -Dparallel=methods -DthreadCount=4'
            }
        }
    }
}
```

### TestNG Report

```groovy
pipeline {
    agent any
    stages {
        stage('Test') {
            steps {
                sh 'mvn test'
            }
        }
        post {
            always {
                // JUnit report
                junit 'target/surefire-reports/*.xml'
                
                // HTML report
                publishHTML([
                    reportDir: 'target/site/surefire-report',
                    reportFiles: 'index.html',
                    reportName: 'TestNG Report'
                ])
            }
        }
    }
}
```

### Interview Questions

**Q1: How do you run TestNG tests in Jenkins?**
- Use Maven surefire plugin
- Run mvn test command
- Can specify testng.xml
- Publish JUnit results

**Q2: How do you publish TestNG reports in Jenkins?**
- Use junit step for XML results
- Use publishHTML for HTML reports
- Configure in post section
- Always publish regardless of result

**Q3: How do you run TestNG tests in parallel in Jenkins?**
- Use Maven surefire plugin configuration
- Set parallel and threadCount parameters
- Can use TestNG XML configuration
- Distributes tests across threads

---

## 17. PLAYWRIGHT INTEGRATION WITH JENKINS

### Playwright Setup

**Install Playwright Browsers:**
```groovy
pipeline {
    agent any
    stages {
        stage('Setup') {
            steps {
                sh 'npx playwright install --with-deps'
            }
        }
    }
}
```

### Running Playwright Tests

```groovy
pipeline {
    agent any
    stages {
        stage('Install Dependencies') {
            steps {
                sh 'npm install'
            }
        }
        stage('Install Browsers') {
            steps {
                sh 'npx playwright install --with-deps'
            }
        }
        stage('Run Tests') {
            steps {
                sh 'npx playwright test'
            }
        }
    }
}
```

### Playwright with Maven

```groovy
pipeline {
    agent any
    tools {
        maven 'Maven 3.8.1'
    }
    stages {
        stage('Test') {
            steps {
                sh 'mvn test -Dtest=PlaywrightTest'
            }
        }
    }
}
```

### Playwright Report

```groovy
pipeline {
    agent any
    stages {
        stage('Test') {
            steps {
                sh 'npx playwright test'
            }
        }
        post {
            always {
                // Publish HTML report
                publishHTML([
                    reportDir: 'playwright-report',
                    reportFiles: 'index.html',
                    reportName: 'Playwright Report'
                ])
                
                // Archive test results
                archiveArtifacts artifacts: 'playwright-report/**/*', 
                               fingerprint: true
            }
        }
    }
}
```

### Playwright with Docker

```groovy
pipeline {
    agent {
        docker {
            image 'mcr.microsoft.com/playwright:latest'
            args '-v /var/run/docker.sock:/var/run/docker.sock'
        }
    }
    stages {
        stage('Test') {
            steps {
                sh 'npx playwright test'
            }
        }
    }
}
```

### Headless vs Headful

```groovy
pipeline {
    agent any
    stages {
        stage('Test') {
            steps {
                // Headless (default for CI)
                sh 'npx playwright test'
                
                // Headful (for debugging)
                sh 'npx playwright test --headed'
            }
        }
    }
}
```

### Interview Questions

**Q1: How do you integrate Playwright with Jenkins?**
- Install Playwright browsers
- Run tests via npm or Maven
- Publish HTML reports
- Use Docker for consistent environment

**Q2: How do you run Playwright tests in Docker?**
- Use Playwright Docker image
- Mount Docker socket if needed
- Run tests in container
- Consistent environment

**Q3: How do you publish Playwright reports in Jenkins?**
- Use publishHTML step
- Specify report directory
- Archive test results
- View in Jenkins UI

---

## 18. JENKINS REPORTING

### JUnit Reports

```groovy
pipeline {
    agent any
    stages {
        stage('Test') {
            steps {
                sh 'mvn test'
            }
        }
        post {
            always {
                junit 'target/surefire-reports/*.xml'
            }
        }
    }
}
```

### HTML Reports

```groovy
pipeline {
    agent any
    stages {
        stage('Test') {
            steps {
                sh 'mvn test'
            }
        }
        post {
            always {
                publishHTML([
                    reportDir: 'target/site/surefire-report',
                    reportFiles: 'index.html',
                    reportName: 'Test Report',
                    keepAll: true,
                    alwaysLinkToLastBuild: true
                ])
            }
        }
    }
}
```

### Coverage Reports

```groovy
pipeline {
    agent any
    stages {
        stage('Test') {
            steps {
                sh 'mvn clean test jacoco:report'
            }
        }
        post {
            always {
                jacoco(
                    execPattern: 'target/jacoco.exec',
                    classPattern: 'target/classes',
                    sourcePattern: 'src/main/java',
                    exclusionPattern: '**/*Test*.class'
                )
            }
        }
    }
}
```

### Allure Reports

```groovy
pipeline {
    agent any
    stages {
        stage('Test') {
            steps {
                sh 'mvn test'
            }
        }
        post {
            always {
                allure([
                    includeProperties: false,
                    jdk: '',
                    properties: [],
                    reportBuildPolicy: 'ALWAYS',
                    results: [[path: 'target/allure-results']]
                ])
            }
        }
    }
}
```

### Custom Reports

```groovy
pipeline {
    agent any
    stages {
        stage('Generate Report') {
            steps {
                sh './generate-report.sh'
            }
        }
        post {
            always {
                publishHTML([
                    reportDir: 'reports',
                    reportFiles: 'custom-report.html',
                    reportName: 'Custom Report'
                ])
            }
        }
    }
}
```

### Interview Questions

**Q1: How do you publish test reports in Jenkins?**
- Use junit step for XML results
- Use publishHTML for HTML reports
- Use jacoco for coverage
- Use allure for Allure reports

**Q2: What is the difference between JUnit and HTML reports?**
- JUnit: XML format, test results
- HTML: Visual reports, detailed
- JUnit for test history
- HTML for detailed analysis

**Q3: How do you configure coverage reports in Jenkins?**
- Use JaCoCo plugin
- Generate JaCoCo report
- Use jacoco step in Pipeline
- Shows line and branch coverage

---

## 19. EMAIL NOTIFICATIONS

### Basic Email

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
    post {
        success {
            emailext subject: 'Build Success',
                body: 'Build completed successfully',
                to: 'team@example.com'
        }
        failure {
            emailext subject: 'Build Failed',
                body: 'Build failed. Check logs.',
                to: 'team@example.com'
        }
    }
}
```

### Email Extension Plugin

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
    post {
        always {
            emailext(
                subject: "Build ${currentBuild.result}: ${env.JOB_NAME} - ${env.BUILD_NUMBER}",
                body: """
                <p>Build Status: ${currentBuild.result}</p>
                <p>Job: ${env.JOB_NAME}</p>
                <p>Build Number: ${env.BUILD_NUMBER}</p>
                <p>URL: ${env.BUILD_URL}</p>
                """,
                to: 'team@example.com',
                mimeType: 'text/html'
            )
        }
    }
}
```

### Conditional Email

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
    post {
        failure {
            emailext subject: 'Build Failed',
                body: 'Build failed. Check logs.',
                to: 'dev-team@example.com'
        }
        unstable {
            emailext subject: 'Build Unstable',
                body: 'Build unstable. Some tests failed.',
                to: 'qa-team@example.com'
        }
    }
}
```

### Email with Attachments

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
    post {
        always {
            emailext(
                subject: 'Build Report',
                body: 'Please find attached build report.',
                to: 'team@example.com',
                attachmentsPattern: 'target/*.jar,target/reports/**/*'
            )
        }
    }
}
```

### Email Configuration

**Configure in Jenkins:**
1. Manage Jenkins → Configure System
2. E-mail Notification
3. SMTP server settings
4. Default email suffix
5. Configure Email Extension Plugin

### Interview Questions

**Q1: How do you send email notifications in Jenkins?**
- Use emailext step
- Configure SMTP settings
- Use in post section
- Can attach files

**Q2: What is the difference between mail and emailext?**
- mail: Basic email plugin
- emailext: Extended email plugin
- emailext has more features
- HTML support, attachments, etc.

**Q3: How do you configure email notifications in Jenkins?**
- Configure SMTP in system settings
- Use emailext step in Pipeline
- Can use post-build actions
- Conditional on build status

---

## 20. PIPELINE AS CODE

### What is Pipeline as Code?
- Define pipeline in source code (Jenkinsfile)
- Version-controlled with application code
- Reviewable like code
- Single source of truth

### Benefits
- **Version Control**: Pipeline changes tracked in Git
- **Code Review**: Pipeline changes reviewed like code
- **Reusability**: Share pipeline across projects
- **Documentation**: Pipeline is self-documenting
- **Consistency**: Same pipeline across environments

### Jenkinsfile in Repository

```
my-project/
├── src/
├── pom.xml
├── Jenkinsfile
└── README.md
```

### Multibranch Pipeline

```groovy
// Jenkinsfile in each branch
pipeline {
    agent any
    stages {
        stage('Build') {
            steps {
                sh 'mvn clean package'
            }
        }
        stage('Deploy') {
            when {
                branch 'main'
            }
            steps {
                sh 'deploy to production'
            }
        }
    }
}
```

### Pipeline from SCM

**Job Configuration:**
- Pipeline script from SCM
- Git repository
- Script path: Jenkinsfile
- Branch: */main

### Shared Libraries

**Create Shared Library:**
```
shared-library/
├── src/
│   └── org/
│       └── devops/
│           └── StandardPipeline.groovy
└── vars/
    └── standardBuild.groovy
```

**Use in Pipeline:**
```groovy
@Library('shared-library@main') _

standardBuild()

// Or
pipeline {
    agent any
    stages {
        stage('Build') {
            steps {
                standardBuild()
            }
        }
    }
}
```

### Interview Questions

**Q1: What is Pipeline as Code?**
- Define pipeline in Jenkinsfile
- Store in source control
- Version-controlled
- Single source of truth

**Q2: What are the benefits of Pipeline as Code?**
- Version control
- Code review
- Reusability
- Documentation
- Consistency

**Q3: How do you implement Pipeline as Code?**
- Create Jenkinsfile in repo
- Use Multibranch Pipeline
- Define pipeline in code
- Commit to Git

---

## 21. SHARED LIBRARIES

### Creating Shared Library

**Directory Structure:**
```
shared-library/
├── src/
│   └── org/
│       └── devops/
│           ├── BuildUtils.groovy
│           └── DeployUtils.groovy
├── vars/
│   ├── standardBuild.groovy
│   └── deployToEnv.groovy
└── resources/
    └── templates/
        └── email-template.html
```

**Groovy Class (src/org/devops/BuildUtils.groovy):**
```groovy
package org.devops

class BuildUtils implements Serializable {
    def script
    
    BuildUtils(script) {
        this.script = script
    }
    
    def mavenBuild() {
        script.sh 'mvn clean package'
    }
    
    def runTests() {
        script.sh 'mvn test'
    }
}
```

**Global Variable (vars/standardBuild.groovy):**
```groovy
def call(Map config = [:]) {
    pipeline {
        agent any
        stages {
            stage('Build') {
                steps {
                    sh 'mvn clean package'
                }
            }
            stage('Test') {
                steps {
                    sh 'mvn test'
                }
            }
        }
    }
}
```

### Configuring Shared Library

**Manage Jenkins → Configure System → Global Pipeline Libraries:**
```
Library Name: shared-library
Default Version: main
Retrieval Method: Modern SCM
Project Repository: https://github.com/user/shared-library.git
```

### Using Shared Library

```groovy
@Library('shared-library@main') _

// Using global variable
standardBuild()

// Using Groovy class
pipeline {
    agent any
    stages {
        stage('Build') {
            steps {
                script {
                    def buildUtils = new org.devops.BuildUtils(this)
                    buildUtils.mavenBuild()
                }
            }
        }
    }
}
```

### Dynamic Shared Library

```groovy
library identifier: 'shared-library@main', 
        retriever: modernSCM([
            $class: 'GitSCMSource',
            remote: 'https://github.com/user/shared-library.git'
        ])
```

### Interview Questions

**Q1: What are Shared Libraries in Jenkins?**
- Reusable pipeline code
- Stored in separate repository
- Can be shared across projects
- Contains Groovy classes and global variables

**Q2: How do you create a Shared Library?**
- Create Git repository
- Add src/, vars/, resources/ directories
- Define Groovy classes and global variables
- Configure in Jenkins

**Q3: How do you use Shared Libraries in Pipeline?**
- Use @Library annotation
- Import library by name
- Use global variables directly
- Instantiate Groovy classes

---

## 22. DOCKER INTEGRATION WITH JENKINS

### Docker Agent

```groovy
pipeline {
    agent {
        docker {
            image 'maven:3.8.1-openjdk-11'
            args '-v $HOME/.m2:/root/.m2'
        }
    }
    stages {
        stage('Build') {
            steps {
                sh 'mvn clean package'
            }
        }
    }
}
```

### Dockerfile Agent

```groovy
pipeline {
    agent {
        dockerfile {
            filename 'Dockerfile'
            dir 'build'
            args '-v $HOME/.m2:/root/.m2'
        }
    }
    stages {
        stage('Build') {
            steps {
                sh 'mvn clean package'
            }
        }
    }
}
```

### Build Docker Image

```groovy
pipeline {
    agent any
    stages {
        stage('Build Image') {
            steps {
                script {
                    docker.build('myapp:${env.BUILD_NUMBER}')
                }
            }
        }
    }
}
```

### Push Docker Image

```groovy
pipeline {
    agent any
    stages {
        stage('Push Image') {
            steps {
                script {
                    docker.withRegistry('https://registry.example.com', 'docker-creds') {
                        docker.image('myapp:${env.BUILD_NUMBER}').push()
                        docker.image('myapp:latest').push()
                    }
                }
            }
        }
    }
}
```

### Run Docker Container

```groovy
pipeline {
    agent any
    stages {
        stage('Run Container') {
            steps {
                script {
                    docker.image('myapp:${env.BUILD_NUMBER}').run('-p 8080:8080')
                }
            }
        }
    }
}
```

### Docker Compose

```groovy
pipeline {
    agent any
    stages {
        stage('Start Services') {
            steps {
                sh 'docker-compose up -d'
            }
        }
        stage('Test') {
            steps {
                sh 'mvn test'
            }
        }
        stage('Stop Services') {
            steps {
                sh 'docker-compose down'
            }
        }
    }
}
```

### Interview Questions

**Q1: How do you use Docker agents in Jenkins?**
- Use docker agent directive
- Specify Docker image
- Can mount volumes
- Consistent build environment

**Q2: How do you build and push Docker images in Jenkins?**
- Use docker.build() to build
- Use docker.withRegistry() to push
- Tag with build number
- Push multiple tags

**Q3: How do you use Docker Compose in Jenkins?**
- Use docker-compose commands
- Start services before tests
- Stop services after tests
- Useful for integration tests

---

## 23. JENKINS SECURITY

### Authentication

**Jenkins Security Realm:**
- Jenkins own user database
- LDAP
- Active Directory
- GitHub OAuth
- SAML

### Authorization

**Authorization Strategies:**
- **Logged-in users can do anything**: No restrictions
- **Legacy mode**: Matrix-based security
- **Project-based Matrix Authorization**: Per-project permissions
- **Role-based Strategy**: Role-based access control

### Matrix Authorization

```
Global Matrix Authorization Strategy:
- Admin: Overall/Read, Overall/Administer
- Developer: Overall/Read, Job/Build, Job/Configure
- Viewer: Overall/Read
```

### CSRF Protection

**Configure in Jenkins:**
- Manage Jenkins → Configure Global Security
- CSRF Protection: Enable
- Default Crumb Issuer: Default

### Agent Security

**Agent Protocols:**
- JNLP: Default
- SSH: More secure
- Inbound Agent: TCP port

### Secrets Management

**Best Practices:**
- Use Jenkins Credentials
- Never hardcode secrets
- Mask credentials in logs
- Rotate credentials regularly

### Security Plugins

- **Credentials Binding Plugin**: Secure credential usage
- **Role-Based Strategy**: Role-based access control
- **Folder Permissions**: Per-folder security
- **Audit Trail**: Track user actions

### Interview Questions

**Q1: How do you secure Jenkins?**
- Enable authentication
- Configure authorization
- Enable CSRF protection
- Use credentials for secrets
- Regular updates

**Q2: What are the different authentication methods in Jenkins?**
- Jenkins own user database
- LDAP/Active Directory
- OAuth (GitHub, Google)
- SAML
- SSO

**Q3: How do you manage secrets in Jenkins?**
- Use Jenkins Credentials
- Never hardcode in scripts
- Use credentials binding
- Mask in logs
- Rotate regularly

---

## 24. JENKINS BEST PRACTICES

### Pipeline Design

**1. Use Pipeline as Code:**
- Store Jenkinsfile in repository
- Version control pipeline changes
- Code review pipeline changes

**2. Keep Pipelines Simple:**
- Break into stages
- Use parallel execution
- Avoid complex logic

**3. Use Shared Libraries:**
- Reuse common logic
- Maintain consistency
- Reduce duplication

### Build Performance

**1. Optimize Workspace:**
- Clean workspace periodically
- Use lightweight checkout
- Archive only necessary artifacts

**2. Parallel Execution:**
- Run tests in parallel
- Use multiple agents
- Split large jobs

**3. Caching:**
- Cache dependencies
- Use Docker layers
- Reuse build tools

### Security

**1. Credentials:**
- Never hardcode secrets
- Use Jenkins Credentials
- Rotate regularly

**2. Agent Security:**
- Use dedicated agents
- Restrict agent permissions
- Isolate environments

**3. Updates:**
- Keep Jenkins updated
- Update plugins regularly
- Monitor security advisories

### Monitoring

**1. Build Notifications:**
- Email on failure
- Slack integration
- Dashboard monitoring

**2. Metrics:**
- Track build times
- Monitor success rates
- Resource utilization

**3. Logs:**
- Archive build logs
- Centralize logging
- Monitor errors

### Documentation

**1. Pipeline Documentation:**
- Comment Jenkinsfile
- Document shared libraries
- Maintain README

**2. Job Documentation:**
- Describe job purpose
- Document parameters
- Explain triggers

### Interview Questions

**Q1: What are Jenkins best practices?**
- Use Pipeline as Code
- Keep pipelines simple
- Use shared libraries
- Optimize performance
- Secure credentials

**Q2: How do you optimize Jenkins build performance?**
- Parallel execution
- Caching dependencies
- Clean workspace
- Use lightweight checkout
- Optimize Maven/Gradle

**Q3: How do you secure Jenkins?**
- Enable authentication
- Configure authorization
- Use credentials
- Enable CSRF protection
- Regular updates

---

## 25. COMMON INTERVIEW QUESTIONS

### Jenkins Fundamentals

**Q1: What is Jenkins and why is it used?**
- Open-source automation server
- Used for CI/CD
- Automates build, test, deployment
- Integrates with various tools via plugins

**Q2: What is the difference between CI and CD?**
- CI: Continuous Integration - build and test automatically
- CD: Continuous Delivery/Deployment - automate deployment
- CI focuses on integration, CD on delivery
- CI is prerequisite for CD

**Q3: What is Jenkins Master-Slave architecture?**
- Master: Schedules and manages builds
- Slave: Executes build tasks
- Master communicates with slaves via JNLP
- Enables distributed builds

### Pipeline

**Q4: What is a Jenkins Pipeline?**
- Suite of plugins for CD
- Defines entire build process
- Contains stages and steps
- Defined in Jenkinsfile

**Q5: What is the difference between Declarative and Scripted Pipeline?**
- Declarative: Simple, structured syntax
- Scripted: Groovy-based, flexible
- Declarative easier to learn
- Scripted more powerful

**Q6: What is a Jenkinsfile?**
- Text file defining pipeline
- Stored in SCM
- Version-controlled
- Pipeline as Code

**Q7: What are the key sections in Declarative Pipeline?**
- agent: Where pipeline runs
- stages: Logical divisions
- steps: Tasks within stages
- post: Actions after completion
- environment: Variables
- parameters: User input

### Build Triggers

**Q8: What are the different types of build triggers?**
- Poll SCM: Check for changes periodically
- Cron: Scheduled builds
- Upstream: Triggered by other jobs
- Webhook: Triggered by SCM events
- Manual: Triggered by user

**Q9: What is the difference between Poll SCM and Webhook?**
- Poll SCM: Jenkins checks periodically
- Webhook: SCM notifies Jenkins
- Webhook is faster and more efficient
- Poll SCM is simpler to set up

### Agents

**Q10: What are the different types of agents in Jenkins Pipeline?**
- any: Any available agent
- label: Agent with specific label
- node: Specific agent node
- docker: Docker container
- dockerfile: Build from Dockerfile
- none: No default agent

**Q11: How do you use Docker agents in Jenkins?**
- Specify docker image
- Can mount volumes
- Consistent build environment
- Isolated from host

### Credentials

**Q12: How do you manage credentials in Jenkins?**
- Use Jenkins Credentials store
- Never hardcode in scripts
- Use credentials binding
- Automatically masked in logs

**Q13: What are the different credential types in Jenkins?**
- Username with password
- Secret text
- Secret file
- SSH username with private key
- Certificate

### Integration

**Q14: How do you integrate Maven with Jenkins?**
- Configure Maven in Global Tool Configuration
- Use tools block in Pipeline
- Use withMaven wrapper
- Specify Maven installation

**Q15: How do you run TestNG tests in Jenkins?**
- Use Maven surefire plugin
- Run mvn test command
- Publish JUnit results
- Publish HTML reports

**Q16: How do you integrate Playwright with Jenkins?**
- Install Playwright browsers
- Run tests via npm or Maven
- Publish HTML reports
- Use Docker for consistent environment

### Reporting

**Q17: How do you publish test reports in Jenkins?**
- Use junit step for XML results
- Use publishHTML for HTML reports
- Use jacoco for coverage
- Use allure for Allure reports

**Q18: How do you send email notifications in Jenkins?**
- Use emailext step
- Configure SMTP settings
- Use in post section
- Can attach files

### Advanced

**Q19: What is Pipeline as Code?**
- Define pipeline in Jenkinsfile
- Store in source control
- Version-controlled
- Single source of truth

**Q20: What are Shared Libraries in Jenkins?**
- Reusable pipeline code
- Stored in separate repository
- Can be shared across projects
- Contains Groovy classes and global variables

**Q21: How do you build and push Docker images in Jenkins?**
- Use docker.build() to build
- Use docker.withRegistry() to push
- Tag with build number
- Push multiple tags

### Security

**Q22: How do you secure Jenkins?**
- Enable authentication
- Configure authorization
- Enable CSRF protection
- Use credentials for secrets
- Regular updates

**Q23: What are the different authentication methods in Jenkins?**
- Jenkins own user database
- LDAP/Active Directory
- OAuth (GitHub, Google)
- SAML
- SSO

### Best Practices

**Q24: What are Jenkins best practices?**
- Use Pipeline as Code
- Keep pipelines simple
- Use shared libraries
- Optimize performance
- Secure credentials

**Q25: How do you optimize Jenkins build performance?**
- Parallel execution
- Caching dependencies
- Clean workspace
- Use lightweight checkout
- Optimize Maven/Gradle

### Scenario-Based Questions

**Q26: How do you handle a failing build in Jenkins?**
- Configure email notifications
- Analyze build logs
- Fix the issue
- Rerun build
- Monitor success

**Q27: How do you deploy to different environments in Jenkins?**
- Use parameters for environment selection
- Use different stages for different environments
- Use conditional deployment
- Use environment-specific configurations

**Q28: How do you rollback a deployment in Jenkins?**
- Keep previous versions
- Use versioned artifacts
- Implement rollback stage
- Use database migrations
- Monitor deployment

**Q29: How do you handle large projects in Jenkins?**
- Use multibranch pipeline
- Use shared libraries
- Parallel execution
- Modularize pipelines
- Use folder organization

**Q30: How do you integrate Jenkins with other tools?**
- Use plugins for integration
- Use webhooks for triggers
- Use API for automation
- Use shared libraries
- Use external services

---

## CONCLUSION

This comprehensive guide covers all essential Jenkins and CI/CD topics for interview preparation. Key takeaways:

1. **Jenkins Fundamentals**: Open-source CI/CD server with plugin ecosystem
2. **Architecture**: Master-Slave for distributed builds
3. **Pipelines**: Declarative vs Scripted, Pipeline as Code
4. **Jenkinsfile**: Version-controlled pipeline definition
5. **Agents**: Various types including Docker
6. **Triggers**: Poll SCM, cron, webhooks, upstream
7. **Integration**: Maven, TestNG, Playwright, Docker
8. **Reporting**: JUnit, HTML, coverage reports
9. **Security**: Authentication, authorization, credentials
10. **Best Practices**: Pipeline as Code, shared libraries, optimization

Practice these concepts with real-world examples and be prepared to explain the "why" behind each approach. Good luck with your interview!
