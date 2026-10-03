# Git & Version Control - Complete Interview Preparation Guide

---

## TABLE OF CONTENTS
1. Version Control Fundamentals
2. Git Fundamentals
3. Git Installation and Setup
4. Git Basic Commands
5. Git Branching
6. Git Merging
7. Git Rebase
8. Git Stashing
9. Git Remote Operations
10. Git Tagging
11. Git Log and History
12. Git Diff and Comparison
13. Git Reset and Revert
14. Git Cherry-Pick
15. Git Bisect
16. Git Blame
17. Git Ignore
18. Git Hooks
19. Git Submodules
20. Git Workflows
21. Git Best Practices
22. Git vs SVN
23. Git Commands Reference
24. Common Interview Questions
25. Practice Scenarios

---

## 1. VERSION CONTROL FUNDAMENTALS

### What is Version Control?
Version control is a system that records changes to files over time so you can recall specific versions later. It allows you to revert files back to a previous state, compare changes, and collaborate with others.

### Types of Version Control

**Local Version Control:**
- Stores file versions locally
- Simple but limited
- Example: RCS (Revision Control System)

**Centralized Version Control:**
- Single server stores all versions
- Clients check out files
- Example: SVN (Subversion), CVS

**Distributed Version Control:**
- Every client has full repository
- No single point of failure
- Example: Git, Mercurial

### Benefits of Version Control

- **History**: Track all changes
- **Collaboration**: Work with others
- **Backup**: Remote repository backup
- **Revert**: Go back to previous versions
- **Branching**: Work on multiple features
- **Merging**: Combine changes

### Interview Questions

**Q1: What is version control?**
- System to track file changes
- Record history of changes
- Allows reverting to previous versions
- Enables collaboration

**Q2: What are the types of version control?**
- Local: RCS
- Centralized: SVN, CVS
- Distributed: Git, Mercurial

**Q3: What are the benefits of version control?**
- Track history
- Enable collaboration
- Provide backup
- Allow reverting
- Support branching

---

## 2. GIT FUNDAMENTALS

### What is Git?
Git is a distributed version control system created by Linus Torvalds in 2005. It's designed for speed, data integrity, and support for distributed, non-linear workflows.

### Git Architecture

```
Working Directory
    ↓ (git add)
Staging Area (Index)
    ↓ (git commit)
Local Repository
    ↓ (git push)
Remote Repository
```

### Git Key Concepts

**Repository:**
- Stores all files and history
- Contains .git directory
- Can be local or remote

**Commit:**
- Snapshot of changes
- Has unique SHA-1 hash
- Contains author, message, changes

**Branch:**
- Independent line of development
- Pointer to commit
- Default branch: main/master

**HEAD:**
- Pointer to current branch
- Points to latest commit
- Moves with new commits

### Git vs Other VCS

| Feature | Git | SVN | Mercurial |
|---------|-----|-----|-----------|
| Type | Distributed | Centralized | Distributed |
| Speed | Fast | Slower | Fast |
| Branching | Easy | Difficult | Easy |
| Merging | Powerful | Limited | Good |
| Learning Curve | Steep | Easy | Medium |

### Interview Questions

**Q1: What is Git?**
- Distributed version control system
- Created by Linus Torvalds
- Designed for speed and integrity
- Supports distributed workflows

**Q2: What is the Git architecture?**
- Working Directory → Staging → Local Repo → Remote Repo
- git add: Stage changes
- git commit: Commit to local
- git push: Push to remote

**Q3: What is the difference between Git and SVN?**
- Git: Distributed, fast, easy branching
- SVN: Centralized, slower, difficult branching
- Git: Every client has full history
- SVN: Server has full history

---

## 3. GIT INSTALLATION AND SETUP

### Installation

**Linux (Ubuntu/Debian):**
```bash
sudo apt update
sudo apt install git
```

**macOS:**
```bash
brew install git
```

**Windows:**
- Download from git-scm.com
- Run installer
- Choose default options

### Configuration

```bash
# Set user name
git config --global user.name "Your Name"

# Set user email
git config --global user.email "your.email@example.com"

# Set default branch name
git config --global init.defaultBranch main

# Set default editor
git config --global core.editor vim

# View configuration
git config --list
```

### Initialize Repository

```bash
# Initialize new repository
git init

# Clone existing repository
git clone https://github.com/user/repo.git

# Clone with specific branch
git clone -b develop https://github.com/user/repo.git
```

### Interview Questions

**Q1: How do you install Git?**
- Linux: apt install git
- macOS: brew install git
- Windows: Download from git-scm.com
- Verify with git --version

**Q2: How do you configure Git?**
- Set user name and email
- Set default branch name
- Set default editor
- Use git config command

**Q3: What is the difference between git init and git clone?**
- git init: Initialize new repository
- git clone: Copy existing repository
- init: Creates .git directory
- clone: Copies from remote

---

## 4. GIT BASIC COMMANDS

### git status

```bash
# Check repository status
git status

# Short status
git status -s
```

### git add

```bash
# Add specific file
git add filename.txt

# Add all files
git add .

# Add all tracked files
git add -u

# Interactive add
git add -i
```

### git commit

```bash
# Commit with message
git commit -m "Commit message"

# Add and commit in one command
git commit -am "Commit message"

# Amend last commit
git commit --amend

# Commit with detailed message
git commit
```

### git log

```bash
# Show commit history
git log

# Show compact history
git log --oneline

# Show graph
git log --graph --oneline

# Show last N commits
git log -n 5
```

### git show

```bash
# Show commit details
git show

# Show specific commit
git show <commit-hash>

# Show file at specific commit
git show <commit-hash>:filename.txt
```

### Interview Questions

**Q1: What does git status do?**
- Shows repository status
- Shows staged, unstaged, untracked files
- Shows current branch
- Shows ahead/behind info

**Q2: What is the difference between git add and git commit?**
- git add: Stage changes
- git commit: Save changes
- add: Moves to staging
- commit: Moves to repository

**Q3: What is git log used for?**
- Show commit history
- Show commit messages
- Show commit authors
- Can filter and format

---

## 5. GIT BRANCHING

### Create Branch

```bash
# Create new branch
git branch feature-branch

# Create and switch to branch
git checkout -b feature-branch

# Create branch from specific commit
git branch feature-branch <commit-hash>
```

### List Branches

```bash
# List all branches
git branch

# List all branches with last commit
git branch -v

# List remote branches
git branch -r

# List all branches (local and remote)
git branch -a
```

### Switch Branch

```bash
# Switch to branch
git checkout feature-branch

# Switch to previous branch
git checkout -

# Switch and create if not exists
git checkout -b new-branch
```

### Delete Branch

```bash
# Delete local branch
git branch -d feature-branch

# Force delete branch
git branch -D feature-branch

# Delete remote branch
git push origin --delete feature-branch
```

### Rename Branch

```bash
# Rename current branch
git branch -m new-name

# Rename specific branch
git branch -m old-name new-name
```

### Interview Questions

**Q1: What is a Git branch?**
- Independent line of development
- Pointer to commit
- Allows parallel work
- Easy to create and merge

**Q2: How do you create a new branch?**
- git branch branch-name
- git checkout -b branch-name
- Can create from specific commit
- Example: git checkout -b feature-login

**Q3: How do you delete a branch?**
- git branch -d branch-name (safe)
- git branch -D branch-name (force)
- Delete remote: git push origin --delete branch-name
- Must switch off branch first

---

## 6. GIT MERGING

### Merge Branch

```bash
# Merge branch into current branch
git merge feature-branch

# Merge with commit message
git merge feature-branch -m "Merge message"

# Merge without commit (for resolving conflicts)
git merge --no-commit feature-branch
```

### Merge Strategies

**Fast-Forward Merge:**
```bash
# Linear history
git merge feature-branch
```

**Three-Way Merge:**
```bash
# Creates merge commit
git merge --no-ff feature-branch
```

**Squash Merge:**
```bash
# Combine all commits into one
git merge --squash feature-branch
```

### Resolve Conflicts

```bash
# After merge conflict
# Edit conflicted files
# Mark as resolved
git add filename.txt

# Continue merge
git commit

# Abort merge
git merge --abort
```

### Merge Tools

```bash
# Use merge tool
git mergetool

# Configure merge tool
git config --global merge.tool vimdiff
```

### Interview Questions

**Q1: What is Git merge?**
- Combine changes from one branch to another
- Creates merge commit (unless fast-forward)
- Can have conflicts
- Resolves conflicts manually

**Q2: What is the difference between fast-forward and three-way merge?**
- Fast-forward: Linear, no merge commit
- Three-way: Creates merge commit
- Fast-forward: When no divergence
- Three-way: When branches diverged

**Q3: How do you resolve merge conflicts?**
- Edit conflicted files
- Remove conflict markers
- git add to mark resolved
- git commit to complete merge

---

## 7. GIT REBASE

### Basic Rebase

```bash
# Rebase current branch onto another
git rebase main

# Rebase specific branch
git rebase main feature-branch
```

### Interactive Rebase

```bash
# Interactive rebase last N commits
git rebase -i HEAD~3

# Interactive rebase from specific commit
git rebase -i <commit-hash>
```

### Rebase Commands

- **pick**: Use commit as is
- **reword**: Edit commit message
- **edit**: Edit commit
- **squash**: Combine with previous
- **fixup**: Combine with previous (discard message)
- **drop**: Remove commit

### Rebase vs Merge

```bash
# Merge: Creates merge commit
git merge feature-branch

# Rebase: Linear history
git rebase main
```

### Abort Rebase

```bash
# Abort rebase
git rebase --abort

# Continue rebase after resolving conflicts
git rebase --continue
```

### Interview Questions

**Q1: What is Git rebase?**
- Move commits to new base
- Linearizes history
- Reapplies commits on top
- Alternative to merge

**Q2: What is the difference between merge and rebase?**
- Merge: Preserves history, creates merge commit
- Rebase: Rewrites history, linear
- Merge: Safer for shared branches
- Rebase: Cleaner history

**Q3: When should you use rebase vs merge?**
- Rebase: Local branches, clean history
- Merge: Shared branches, preserve history
- Rebase: Before merging to main
- Merge: Feature branches to main

---

## 8. GIT STASHING

### Stash Changes

```bash
# Stash current changes
git stash

# Stash with message
git stash save "Work in progress"

# Stash including untracked files
git stash -u

# Stash including ignored files
git stash -a
```

### List Stashes

```bash
# List all stashes
git stash list

# Show stash details
git stash show stash@{0}
```

### Apply Stash

```bash
# Apply most recent stash
git stash apply

# Apply specific stash
git stash apply stash@{1}

# Apply and drop stash
git stash pop
```

### Drop Stash

```bash
# Drop specific stash
git stash drop stash@{0}

# Drop all stashes
git stash clear
```

### Interview Questions

**Q1: What is Git stash?**
- Save work temporarily
- Switch branches without committing
- Store uncommitted changes
- Can apply later

**Q2: What is the difference between stash apply and stash pop?**
- apply: Apply stash, keep in list
- pop: Apply stash, remove from list
- apply: Can reapply multiple times
- pop: One-time application

**Q3: When would you use stash?**
- Switch branches without committing
- Save work in progress
- Test different approaches
- Emergency context switch

---

## 9. GIT REMOTE OPERATIONS

### Add Remote

```bash
# Add remote repository
git remote add origin https://github.com/user/repo.git

# Add remote with different name
git remote add upstream https://github.com/original/repo.git
```

### List Remotes

```bash
# List all remotes
git remote

# List remotes with URLs
git remote -v

# Show remote details
git remote show origin
```

### Push to Remote

```bash
# Push to remote
git push origin main

# Push all branches
git push --all origin

# Push with upstream tracking
git push -u origin main

# Force push (dangerous)
git push -f origin main
```

### Pull from Remote

```bash
# Pull from remote
git pull origin main

# Pull with rebase
git pull --rebase origin main

# Fetch only (don't merge)
git fetch origin
```

### Fetch vs Pull

```bash
# Fetch: Download changes, don't merge
git fetch origin

# Pull: Fetch and merge
git pull origin main
```

### Interview Questions

**Q1: What is a Git remote?**
- Reference to remote repository
- Stored in .git/config
- Can have multiple remotes
- Example: origin, upstream

**Q2: What is the difference between fetch and pull?**
- fetch: Download changes only
- pull: Fetch and merge
- fetch: Safe, no merge
- pull: Automatic merge

**Q3: What is git push -u used for?**
- Set upstream tracking
- Future pushes don't need branch name
- Links local to remote branch
- Example: git push -u origin main

---

## 10. GIT TAGGING

### Create Tag

```bash
# Create lightweight tag
git tag v1.0.0

# Create annotated tag
git tag -a v1.0.0 -m "Version 1.0.0"

# Tag specific commit
git tag -a v1.0.0 <commit-hash> -m "Version 1.0.0"
```

### List Tags

```bash
# List all tags
git tag

# List tags with pattern
git tag -l "v1.*"

# Show tag details
git show v1.0.0
```

### Push Tags

```bash
# Push specific tag
git push origin v1.0.0

# Push all tags
git push origin --tags
```

### Delete Tag

```bash
# Delete local tag
git tag -d v1.0.0

# Delete remote tag
git push origin --delete v1.0.0

# Or push empty tag
git push origin :refs/tags/v1.0.0
```

### Checkout Tag

```bash
# Checkout tag (detached HEAD)
git checkout v1.0.0

# Create branch from tag
git checkout -b release-1.0.0 v1.0.0
```

### Interview Questions

**Q1: What is a Git tag?**
- Reference to specific commit
- Used for releases
- Can be lightweight or annotated
- Example: v1.0.0

**Q2: What is the difference between lightweight and annotated tags?**
- Lightweight: Pointer to commit
- Annotated: Full object with metadata
- Lightweight: Simple, no info
- Annotated: Has message, author, date

**Q3: How do you push tags to remote?**
- Push specific tag: git push origin v1.0.0
- Push all tags: git push origin --tags
- Tags not pushed by default
- Must push explicitly

---

## 11. GIT LOG AND HISTORY

### Basic Log

```bash
# Show commit history
git log

# Show compact history
git log --oneline

# Show graph
git log --graph --oneline --all
```

### Filter Log

```bash
# Show commits by author
git log --author="John Doe"

# Show commits since date
git log --since="2023-01-01"

# Show commits until date
git log --until="2023-12-31"

# Show commits with specific message
git log --grep="fix"
```

### Format Log

```bash
# Custom format
git log --format="%h - %an, %ar : %s"

# Pretty formats
git log --pretty=format:"%h - %an, %ar : %s"
git log --pretty=oneline
git log --pretty=full
```

### Limit Log

```bash
# Show last N commits
git log -n 5

# Show commits for file
git log filename.txt

# Show commits for directory
git log src/
```

### Interview Questions

**Q1: What does git log show?**
- Commit history
- Commit messages
- Author information
- Commit hashes

**Q2: How do you filter git log?**
- By author: --author
- By date: --since, --until
- By message: --grep
- By file: filename

**Q3: How do you format git log output?**
- Use --format flag
- Use --pretty flag
- Custom placeholders: %h, %an, %s
- Example: --format="%h - %s"

---

## 12. GIT DIFF AND COMPARISON

### Basic Diff

```bash
# Show unstaged changes
git diff

# Show staged changes
git diff --staged

# Show changes between commits
git diff commit1 commit2
```

### Diff File

```bash
# Show changes for specific file
git diff filename.txt

# Show staged changes for file
git diff --staged filename.txt
```

### Diff Branches

```bash
# Show differences between branches
git diff main feature-branch

# Show changes in feature-branch not in main
git diff main..feature-branch
```

### Word Diff

```bash
# Show word-level differences
git diff --word-diff
```

### Ignore Whitespace

```bash
# Ignore whitespace changes
git diff -w
```

### Interview Questions

**Q1: What does git diff show?**
- Unstaged changes
- Differences between files
- Line-by-line comparison
- Can show staged changes

**Q2: What is the difference between git diff and git diff --staged?**
- git diff: Unstaged changes
- git diff --staged: Staged changes
- diff: Working directory vs staging
- diff --staged: Staging vs last commit

**Q3: How do you compare branches in Git?**
- git diff branch1 branch2
- git diff main..feature
- Shows differences
- Can use with files

---

## 13. GIT RESET AND REVERT

### git reset

```bash
# Reset to last commit (keep changes)
git reset --soft HEAD~1

# Reset to last commit (discard staging)
git reset --mixed HEAD~1

# Reset to last commit (discard changes)
git reset --hard HEAD~1

# Reset to specific commit
git reset --hard <commit-hash>
```

### git revert

```bash
# Revert last commit
git revert HEAD

# Revert specific commit
git revert <commit-hash>

# Revert with no edit
git revert --no-commit HEAD
```

### Reset vs Revert

```bash
# Reset: Rewrites history
git reset --hard HEAD~1

# Revert: Creates new commit
git revert HEAD
```

### Interview Questions

**Q1: What is git reset?**
- Move HEAD to different commit
- Can discard changes
- Rewrites history
- Three modes: soft, mixed, hard

**Q2: What is git revert?**
- Create new commit to undo changes
- Doesn't rewrite history
- Safe for shared branches
- Preserves history

**Q3: What is the difference between reset and revert?**
- reset: Rewrites history
- revert: Creates new commit
- reset: Dangerous for shared branches
- revert: Safe for shared branches

---

## 14. GIT CHERRY-PICK

### Cherry-Pick Commit

```bash
# Cherry-pick specific commit
git cherry-pick <commit-hash>

# Cherry-pick multiple commits
git cherry-pick <commit1> <commit2>

# Cherry-pick without commit
git cherry-pick -n <commit-hash>
```

### Cherry-Pick Range

```bash
# Cherry-pick range of commits
git cherry-pick commit1..commit2

# Cherry-pick range (exclusive)
git cherry-pick commit1^..commit2
```

### Abort Cherry-Pick

```bash
# Abort cherry-pick on conflict
git cherry-pick --abort

# Continue after resolving conflicts
git cherry-pick --continue
```

### Interview Questions

**Q1: What is git cherry-pick?**
- Apply specific commit to current branch
- Copy commit from one branch to another
- Doesn't merge entire branch
- Useful for specific fixes

**Q2: When would you use cherry-pick?**
- Apply specific fix to multiple branches
- Backport fix to release branch
- Selectively apply commits
- Avoid full merge

**Q3: What happens on cherry-pick conflict?**
- Git pauses cherry-pick
- Resolve conflicts manually
- git add to mark resolved
- git cherry-pick --continue

---

## 15. GIT BISECT

### Start Bisect

```bash
# Start bisect
git bisect start

# Mark current commit as bad
git bisect bad

# Mark known good commit
git bisect good <commit-hash>
```

### Bisect Process

```bash
# Git will checkout middle commit
# Test the code
# Mark as good or bad
git bisect good
git bisect bad

# Repeat until found
```

### End Bisect

```bash
# Finish bisect
git bisect reset
```

### Interview Questions

**Q1: What is git bisect?**
- Binary search through commits
- Find commit that introduced bug
- Automates testing
- Efficient debugging

**Q2: How does git bisect work?**
- Binary search algorithm
- Marks commits as good/bad
- Narrows down to problematic commit
- Requires testing at each step

**Q3: When would you use git bisect?**
- Find commit that introduced bug
- Debug regression
- Large commit history
- Unknown cause

---

## 16. GIT BLAME

### Basic Blame

```bash
# Show blame for file
git blame filename.txt

# Show blame for specific lines
git blame -L 10,20 filename.txt
```

### Blame Options

```bash
# Show email instead of author
git blame -e filename.txt

# Show raw output
git blame -w filename.txt

# Ignore whitespace
git blame -w filename.txt
```

### Interview Questions

**Q1: What is git blame?**
- Show who changed each line
- Show commit hash for each line
- Show author and date
- Useful for code review

**Q2: How do you use git blame?**
- git blame filename.txt
- Shows line-by-line authorship
- Can show specific line range
- Example: git blame -L 10,20 file.txt

**Q3: When would you use git blame?**
- Find who changed code
- Understand code history
- Code review
- Debugging

---

## 17. GIT IGNORE

### Create .gitignore

```bash
# Create .gitignore file
touch .gitignore
```

### .gitignore Patterns

```gitignore
# Ignore file
config.properties

# Ignore directory
/target/

# Ignore all .class files
*.class

# Ignore all files in directory
logs/*

# Ignore directory but keep file
target/*
!target/.gitkeep

# Ignore pattern
*.log

# Ignore specific file
secret.key
```

### Global .gitignore

```bash
# Create global .gitignore
git config --global core.excludesfile ~/.gitignore_global
```

### Check Ignored Files

```bash
# Show ignored files
git check-ignore -v filename.txt

# List all ignored files
git ls-files --others --ignored --exclude-standard
```

### Interview Questions

**Q1: What is .gitignore?**
- File to specify ignored files
- Patterns for files to ignore
- Not tracked by Git
- Can be global or per-repo

**Q2: How do you ignore files in Git?**
- Add to .gitignore
- Use patterns
- Can ignore files and directories
- Example: *.log, /target/

**Q3: What is the difference between .gitignore and git rm --cached?**
- .gitignore: Ignore future files
- git rm --cached: Remove tracked files
- .gitignore: Doesn't affect tracked files
- git rm --cached: Removes from index

---

## 18. GIT HOOKS

### Hook Types

**Client-Side Hooks:**
- pre-commit: Before commit
- pre-push: Before push
- commit-msg: Validate message
- pre-rebase: Before rebase

**Server-Side Hooks:**
- pre-receive: Before receiving push
- post-receive: After receiving push
- update: Before updating each ref

### Create Hook

```bash
# Navigate to hooks directory
cd .git/hooks

# Create hook file
touch pre-commit

# Make executable
chmod +x pre-commit

# Add script
#!/bin/bash
# Your hook logic
```

### Example Pre-Commit Hook

```bash
#!/bin/bash
# Run tests before commit
mvn test
if [ $? -ne 0 ]; then
    echo "Tests failed. Commit aborted."
    exit 1
fi
```

### Interview Questions

**Q1: What are Git hooks?**
- Scripts that run at specific events
- Can automate tasks
- Client-side and server-side
- Located in .git/hooks/

**Q2: What are common Git hooks?**
- pre-commit: Before commit
- pre-push: Before push
- commit-msg: Validate message
- pre-receive: Server-side

**Q3: When would you use Git hooks?**
- Run tests before commit
- Validate commit messages
- Format code
- Enforce policies

---

## 19. GIT SUBMODULES

### Add Submodule

```bash
# Add submodule
git submodule add https://github.com/user/repo.git

# Add to specific directory
git submodule add https://github.com/user/repo.git path/to/dir
```

### Clone with Submodules

```bash
# Clone repository with submodules
git clone --recursive https://github.com/user/repo.git

# Or initialize submodules after clone
git submodule init
git submodule update
```

### Update Submodules

```bash
# Update all submodules
git submodule update

# Update to latest
git submodule update --remote

# Update specific submodule
git submodule update path/to/submodule
```

### Remove Submodule

```bash
# Remove submodule
git submodule deinit path/to/submodule
git rm path/to/submodule
rm -rf .git/modules/path/to/submodule
```

### Interview Questions

**Q1: What are Git submodules?**
- Repository within repository
- Track external dependencies
- Separate Git repository
- Useful for shared libraries

**Q2: How do you add a submodule?**
- git submodule add URL
- Can specify directory
- Creates .gitmodules file
- Example: git submodule add https://github.com/user/repo.git

**Q3: How do you clone repository with submodules?**
- git clone --recursive URL
- Or git submodule init && update
- Submodules not cloned by default
- Must initialize explicitly

---

## 20. GIT WORKFLOWS

### Git Flow

```
main (production)
  ↑
develop (development)
  ↑
feature/* (features)
  ↑
hotfix/* (hotfixes)
  ↑
release/* (releases)
```

**Branches:**
- main: Production code
- develop: Integration branch
- feature/*: New features
- release/*: Release preparation
- hotfix/*: Production fixes

### GitHub Flow

```
main (production)
  ↑
feature/* (features)
  ↑
Pull Request
  ↑
Merge to main
```

**Branches:**
- main: Always deployable
- feature/*: Feature branches
- Pull Request: Review and merge
- Deploy from main

### GitLab Flow

```
main (production)
  ↑
develop (development)
  ↑
feature/* (features)
  ↑
environment branches (staging, production)
```

**Branches:**
- main: Production
- develop: Development
- feature/*: Features
- environment/*: Environments

### Interview Questions

**Q1: What is Git Flow?**
- Branching model by Vincent Driessen
- main, develop, feature, release, hotfix
- Strict branching strategy
- Suitable for release-based projects

**Q2: What is GitHub Flow?**
- Simpler workflow
- main branch always deployable
- Feature branches
- Pull requests for review

**Q3: What is the difference between Git Flow and GitHub Flow?**
- Git Flow: Complex, release-based
- GitHub Flow: Simple, continuous delivery
- Git Flow: Multiple long-lived branches
- GitHub Flow: Single main branch

---

## 21. GIT BEST PRACTICES

### Commit Messages

```bash
# Good commit message
feat: Add user authentication

Add login and registration functionality
with JWT token support.

Closes #123

# Bad commit message
fixed stuff
```

**Conventional Commits:**
- feat: New feature
- fix: Bug fix
- docs: Documentation
- style: Formatting
- refactor: Refactoring
- test: Tests
- chore: Maintenance

### Branch Naming

```bash
# Good branch names
feature/user-authentication
fix/login-bug
hotfix/security-patch
release/v1.0.0

# Bad branch names
stuff
fix
temp
```

### Commit Frequency

- Commit often, small changes
- One logical change per commit
- Commit tested code
- Don't commit broken code

### Pull Request Practices

- Keep PRs small
- Write clear description
- Add tests
- Review before merge
- Resolve conflicts

### Interview Questions

**Q1: What are best practices for commit messages?**
- Use conventional commits
- Clear and descriptive
- Include issue reference
- Explain why, not what

**Q2: What are best practices for branching?**
- Use descriptive names
- Short-lived feature branches
- Delete merged branches
- Follow workflow

**Q3: What are best practices for pull requests?**
- Keep PRs small
- Write clear description
- Add tests
- Review before merge

---

## 22. GIT VS SVN

### Comparison

| Feature | Git | SVN |
|---------|-----|-----|
| Type | Distributed | Centralized |
| Branching | Easy | Difficult |
| Merging | Powerful | Limited |
| Speed | Fast | Slower |
| Offline Work | Yes | No |
| History | Full on each client | On server |
| Learning Curve | Steep | Easy |
| File Size | Small | Large |

### When to Use Git

- Distributed team
- Frequent branching
- Complex merging
- Offline work needed
- Large repositories

### When to Use SVN

- Centralized control needed
- Simple workflow
- Binary files
- Limited branching
- Legacy systems

### Interview Questions

**Q1: What is the main difference between Git and SVN?**
- Git: Distributed
- SVN: Centralized
- Git: Every client has full history
- SVN: Server has full history

**Q2: When would you choose Git over SVN?**
- Distributed team
- Frequent branching
- Complex merging
- Offline work
- Large repositories

**Q3: What are the advantages of Git over SVN?**
- Distributed
- Better branching
- Faster
- Offline work
- Smaller file size

---

## 23. GIT COMMANDS REFERENCE

### Common Commands

```bash
# Initialize
git init
git clone <url>

# Status
git status
git diff
git log

# Staging
git add <file>
git add .
git add -u

# Commit
git commit -m "message"
git commit -am "message"
git commit --amend

# Branching
git branch
git branch <name>
git checkout <name>
git checkout -b <name>
git branch -d <name>

# Merging
git merge <branch>
git merge --no-ff <branch>

# Remote
git remote add <name> <url>
git push origin <branch>
git pull origin <branch>
git fetch origin

# Stash
git stash
git stash pop
git stash list

# Reset
git reset --soft HEAD~1
git reset --hard HEAD~1
git revert HEAD

# Tag
git tag <name>
git tag -a <name> -m "message"
git push origin --tags

# Log
git log
git log --oneline
git log --graph

# Diff
git diff
git diff --staged
git diff <branch1> <branch2>

# Blame
git blame <file>

# Bisect
git bisect start
git bisect bad
git bisect good <commit>
git bisect reset
```

### Interview Questions

**Q1: What are the most common Git commands?**
- git status, git add, git commit
- git push, git pull
- git branch, git checkout
- git merge, git log

**Q2: How do you undo a commit in Git?**
- git reset --soft HEAD~1 (keep changes)
- git reset --hard HEAD~1 (discard changes)
- git revert HEAD (create new commit)

**Q3: How do you resolve merge conflicts?**
- Edit conflicted files
- Remove conflict markers
- git add to mark resolved
- git commit to complete

---

## 24. COMMON INTERVIEW QUESTIONS

### Git Fundamentals

**Q1: What is Git?**
- Distributed version control system
- Created by Linus Torvalds
- Tracks file changes
- Enables collaboration

**Q2: What is the difference between Git and SVN?**
- Git: Distributed
- SVN: Centralized
- Git: Every client has full history
- SVN: Server has full history

**Q3: What are the benefits of using Git?**
- Distributed
- Fast
- Easy branching
- Offline work

### Basic Commands

**Q4: What does git add do?**
- Stage changes for commit
- Move to staging area
- Prepare for commit
- Can add specific files or all

**Q5: What does git commit do?**
- Save staged changes
- Create commit with message
- Move to local repository
- Create snapshot

**Q6: What does git push do?**
- Push commits to remote
- Upload local changes
- Update remote repository
- Need to specify branch

### Branching and Merging

**Q7: What is a Git branch?**
- Independent line of development
- Pointer to commit
- Allows parallel work
- Easy to create and merge

**Q8: How do you create a new branch?**
- git branch branch-name
- git checkout -b branch-name
- Can create from specific commit
- Example: git checkout -b feature-login

**Q9: What is the difference between merge and rebase?**
- Merge: Preserves history, creates merge commit
- Rebase: Rewrites history, linear
- Merge: Safer for shared branches
- Rebase: Cleaner history

### Remote Operations

**Q10: What is a Git remote?**
- Reference to remote repository
- Stored in .git/config
- Can have multiple remotes
- Example: origin, upstream

**Q11: What is the difference between fetch and pull?**
- fetch: Download changes only
- pull: Fetch and merge
- fetch: Safe, no merge
- pull: Automatic merge

**Q12: How do you resolve merge conflicts?**
- Edit conflicted files
- Remove conflict markers
- git add to mark resolved
- git commit to complete

### Advanced

**Q13: What is git stash?**
- Save work temporarily
- Switch branches without committing
- Store uncommitted changes
- Can apply later

**Q14: What is git cherry-pick?**
- Apply specific commit to current branch
- Copy commit from one branch to another
- Doesn't merge entire branch
- Useful for specific fixes

**Q15: What is git rebase?**
- Move commits to new base
- Linearizes history
- Reapplies commits on top
- Alternative to merge

### Best Practices

**Q16: What are best practices for commit messages?**
- Use conventional commits
- Clear and descriptive
- Include issue reference
- Explain why, not what

**Q17: What are best practices for branching?**
- Use descriptive names
- Short-lived feature branches
- Delete merged branches
- Follow workflow

**Q18: What are best practices for pull requests?**
- Keep PRs small
- Write clear description
- Add tests
- Review before merge

### Scenario-Based

**Q19: How do you undo the last commit?**
- git reset --soft HEAD~1 (keep changes)
- git reset --hard HEAD~1 (discard changes)
- git revert HEAD (create new commit)
- Choose based on situation

**Q20: How do you recover a deleted branch?**
- git reflog to find commit
- git branch branch-name <commit-hash>
- Use git reflog to find SHA
- Recreate from commit

**Q21: How do you find which commit introduced a bug?**
- Use git bisect
- Mark good and bad commits
- Binary search through history
- Test at each step

**Q22: How do you sync local branch with remote?**
- git fetch origin
- git rebase origin/main
- Or git pull --rebase
- Resolve conflicts if any

**Q23: How do you discard local changes?**
- git checkout -- filename
- git reset --hard HEAD
- git clean -fd (untracked files)
- Choose based on situation

**Q24: How do you rename a branch?**
- git branch -m new-name
- git branch -m old-name new-name
- Update remote: git push origin :old-name new-name
- Set upstream: git push -u origin new-name

**Q25: How do you see changes in a commit?**
- git show <commit-hash>
- git show HEAD
- git show HEAD~1
- Can show specific file

---

## 25. PRACTICE SCENARIOS

### Scenario 1: Start New Feature

```bash
# Create feature branch
git checkout -b feature/new-feature

# Make changes
git add .
git commit -m "feat: Add new feature"

# Push to remote
git push -u origin feature/new-feature

# Create pull request
# Review and merge
```

### Scenario 2: Fix Bug in Production

```bash
# Create hotfix branch from main
git checkout main
git pull origin main
git checkout -b hotfix/critical-bug

# Fix bug
git add .
git commit -m "fix: Critical bug in production"

# Push and merge
git push -u origin hotfix/critical-bug
# Create PR and merge to main

# Merge back to develop
git checkout develop
git merge main
git push origin develop
```

### Scenario 3: Resolve Merge Conflict

```bash
# Attempt merge
git merge feature-branch

# Conflict occurs
# Edit conflicted files
# Remove conflict markers
# Choose correct version

# Mark as resolved
git add conflicted-file.txt

# Complete merge
git commit
```

### Scenario 4: Undo Last Commit

```bash
# Keep changes
git reset --soft HEAD~1

# Discard changes
git reset --hard HEAD~1

# Create revert commit
git revert HEAD
```

### Scenario 5: Clean Up Branches

```bash
# List merged branches
git branch --merged

# Delete merged branches
git branch -d feature-branch

# Delete remote branches
git push origin --delete feature-branch

# Prune remote branches
git remote prune origin
```

### Scenario 6: Sync with Remote

```bash
# Fetch latest changes
git fetch origin

# Rebase local branch
git rebase origin/main

# Or pull with rebase
git pull --rebase origin main
```

### Scenario 7: Cherry-Pick Commit

```bash
# Find commit hash
git log

# Cherry-pick to current branch
git cherry-pick <commit-hash>

# Resolve conflicts if needed
git add .
git cherry-pick --continue
```

### Scenario 8: Stash Work

```bash
# Save current work
git stash save "Work in progress"

# Switch branches
git checkout other-branch

# Apply stash
git stash pop
```

### Scenario 9: Find Bug with Bisect

```bash
# Start bisect
git bisect start

# Mark current as bad
git bisect bad

# Mark known good commit
git bisect good <commit-hash>

# Test each commit
git bisect good
git bisect bad

# Finish
git bisect reset
```

### Scenario 10: Recover Lost Commit

```bash
# View reflog
git reflog

# Find lost commit
# Copy commit hash

# Recover commit
git branch recovered-branch <commit-hash>
```

---

## CONCLUSION

This comprehensive guide covers all essential Git and version control topics for interview preparation. Key takeaways:

1. **Version Control**: Track changes, enable collaboration, provide backup
2. **Git Fundamentals**: Distributed, fast, easy branching
3. **Basic Commands**: add, commit, push, pull, status, log
4. **Branching**: Create, switch, delete, rename branches
5. **Merging**: Merge branches, resolve conflicts
6. **Rebase**: Linearize history, alternative to merge
7. **Stashing**: Save work temporarily
8. **Remote Operations**: push, pull, fetch, remotes
9. **Tagging**: Mark releases, version control
10. **Reset/Revert**: Undo changes, safe vs dangerous
11. **Cherry-Pick**: Apply specific commits
12. **Bisect**: Find bug-causing commit
13. **Blame**: Track line changes
14. **Ignore**: Exclude files from tracking
15. **Hooks**: Automate tasks
16. **Submodules**: Include external repositories
17. **Workflows**: Git Flow, GitHub Flow, GitLab Flow
18. **Best Practices**: Commit messages, branching, PRs
19. **Git vs SVN**: Distributed vs centralized
20. **Commands Reference**: Common commands

Practice these commands with real repositories and be prepared to explain the "why" behind each approach. Good luck with your interview!
