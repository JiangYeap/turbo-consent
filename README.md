# README #

Please read this up to learn the proper git workflow: https://www.atlassian.com/git/tutorials/learn-git-with-bitbucket-cloud, especially the **Collaborating** section as it accurately depicts what the actual workflow will be like. 

### Terminal commands for initialising git: ###

`git clone <url>` : creates a copy of this repository locally on your machine


### Terminal commands for using git: ###

`git pull` : downloads and updates all files from the remote repository to your local repository

`git branch <branchname>` : creates a new branch with the specified name in your local repository

`git checkout <branchname>` : switches your current local working branch to the specified branch

`git status` : displays all changes made since the previous commit

`git add .` : add all files and folder from your current directory

`git commit -am "<description>"` : saves the changes made to the git-added files in your local repository

`git push -u  origin <branchname>` : uploads the changes on your local repository branch to the remote repository (GitHub)


### Before You Begin... ###

- `git checkout` to your `master` branch (and your other shared branches) and issue a `git pull` to get the latest versions of the branches **EVERY TIME** before starting development work.

- Before working on a new feature / bug fixing, **ALWAYS** create a separate branch with `git branch <featureName>` (use camelCase for separation) and work on that branch instead of `master`. This prevents bad code from being accidentally committed to `master` branch.


