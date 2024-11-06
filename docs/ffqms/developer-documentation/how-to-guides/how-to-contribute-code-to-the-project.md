# How to contribute code to the project

#### Workflow

We are following the "GitHub Flow", using feature branches for development. Please read through the following short guide before you get started: [Understanding the GitHub Flow.](https://docs.github.com/en/get-started/using-github/github-flow)

In summary, this means each feature will be implemented on it's own branch, which will then merged back onto the main branch. If you participate in the development, either by fixing a bug or implementing a new feature, follow these steps:

1. Analyze the bug or feature in a [GitHub issue](https://github.com/Rice-Fortification-DPG/Food-Fortification-DPG/issues). Discuss possible approaches to solve it in the issue.
2. Create a new branch (from the current main!) for each issue/feature you are working on. Use a descriptive branch name.
3. Make commits on this branch. Use descriptive commit messages. If things are complex make multiple, logical commits. Do not use the same branch for implementing multiple features or bug fixes (instead create a separate branch from the main branch for each).
4. Run mvn test to see if all unit tests are passed. If possible/necessary add more unit tests related to your new code. All tests must be passed before your contribution can be accepted.
5. Once you have finished your work or want some feedback/discussion about it, open a new pull request (PR) on GitHub. Any reviews, feedback and discussions about your code will take place in this PR on GitHub. Please follow it and explain or adapt your code if necessary.
6. A different developer will merge your PR back onto the main branch. This makes sure there was a code review and manual testing.
7. After merging, delete your feature branch and make sure the corresponding issue(s) and pull request are closed.
