## 1. AGILE FUNDAMENTALS

### What is Agile?
Agile is an iterative approach to software development that focuses on delivering value to customers faster and with fewer headaches. It emphasizes flexibility, collaboration, and customer feedback.

### Agile vs Waterfall

| Aspect | Agile | Waterfall |
|--------|-------|----------|
| Approach | Iterative | Sequential |
| Flexibility | High | Low |
| Customer Involvement | Continuous | Limited |
| Documentation | Minimal | Extensive |
| Delivery | Incremental | End of project |
| Risk | Low | High |

### Agile Methodologies

**Popular Methodologies:**
- Scrum
- Kanban
- Extreme Programming (XP)
- Lean
- Feature-Driven Development (FDD)

### Interview Questions

**Q1: What is Agile?**
- Iterative software development
- Focus on customer value
- Flexible and collaborative
- Responds to change

# Git & Version Control - Complete Interview Preparation Guide

### What is Version Control?
Version control is a system that records changes to files over time so you can recall specific versions later. It allows you to revert files back to a previous state, compare changes, and collaborate with others.

### Benefits of Version Control

- **History**: Track all changes
- **Collaboration**: Work with others
- **Backup**: Remote repository backup
- **Revert**: Go back to previous versions
- **Branching**: Work on multiple features
- **Merging**: Combine changes


### What is Git?
Git is a distributed version control system created by Linus Torvalds in 2005. It's designed for speed, data integrity, and support for distributed, non-linear workflows.

Q1: What is Git?

Distributed version control system,
Designed for speed and integrity
Supports distributed workflows

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
### git commit

```bash
# Commit with message
git commit -m "Commit message"

# Add and commit in one command
git commit -am "Commit message"

# Amend last commit (apna last commit modify karna bina naye commit banaye.)
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
### Rename Branch

```bash
# Rename current branch
git branch -m new-name

# Rename specific branch
git branch -m old-name new-name
```
### Merge Branch

```bash
# Merge branch into current branch
git merge feature-branch

# Merge with commit message
git merge feature-branch -m "Merge message"

# Abort merge
git merge --abort

# Merge without commit (for resolving conflicts)
git merge --no-commit feature-branch
```

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


## 8. GIT STASHING

```bash
# Stash current changes
git stash

# Stash with message
git stash save "Work in progress"

# Stash including untracked files
git stash -u

# List all stashes
git stash list
# Apply most recent stash
git stash apply

# Apply specific stash
git stash apply stash@{1}

# Apply and drop stash
git stash pop
# Drop all stashes
git stash clear
# Stash including ignored files
git stash -a
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

### Fetch vs Pull

```bash
# Fetch: Download changes, don't merge
git fetch origin

# Pull: Fetch and merge
git pull origin main
```
**Q2: What is the difference between fetch and pull?**
- fetch: Download changes only
- pull: Fetch and merge
- fetch: Safe, no merge
- pull: Automatic merge

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

### git revert

```bash
# Revert last commit
git revert HEAD

# Revert specific commit
git revert <commit-hash>

# Revert with no edit
git revert --no-commit HEAD
```

**Q2: What is git revert?**
- Create new commit to undo changes
- Doesn't rewrite history
- Safe for shared branches
- Preserves history

### Cherry-Pick Commit

```bash
# Cherry-pick specific commit
git cherry-pick <commit-hash>

# Cherry-pick multiple commits
git cherry-pick <commit1> <commit2>

# Cherry-pick without commit
git cherry-pick -n <commit-hash>
```


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
