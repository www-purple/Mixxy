This repo is only open so we can take advantage of the free Travis plan.  Therefore, no outside contributiuons are being accepted at this time.

# PR/Issue-Tracking Rules

1. Two teammates must approve each PR, and if the PR isn't Jesse's then he must be one of them.
2. On the day of and the day before a deadline, only one teammate needs to approve a PR.
3. Only the person who submitted the PR should merge it.
4. Where applicable, only close an issue when the code that resolves it is merged into master.
5. **Do not push directly to `master`.**
6. **Do not merge PRs that don't build.**
7. **Do not merge PRs that don't pass their tests,** unless you have a *very* good reason for doing so.
8. *Please* write as many tests as you possibly can.
9. Issues or PRs that do not see any activity for three days (discussions or commits) should be bumped, and the submitter and assignee reminded.  This may be a good time to update us on progress, or simply close the issue/PR.
10. If a PR isn't approved because it's half-baked, don't close it; keep working on it!  Only close a PR if it's no longer relevant, not useful to Mixxy, or irredeemably flawed.
11. You can submit a PR whenever in its development you'd like, but if it's not ready to be merged then say so!  And if that's the case, let us know when it *is* ready to be merged, and then we'll review it.

If you break these rules, the rest of us are granted the right to slap your shit.  No, seriously; if there weren't road rules that everybody agreed on (e.g. line placement, speed limits, sign placement conventions), traffic wouldn't work.

# Things to Remember

1. Pull the latest changes from `master` before making a new branch!
2. If you want someone in particular to look at your PR, assign them.
3. Label your PRs and issues (and assign milestones where applicable) as soon as you submit them!
4. If your PR goes awry due to extreme merge conflicts, you can always scrap it and try again, [cherry-picking the relevant commits](http://git-scm.com/docs/git-cherry-pick) into a new branch.
5. If your PR resolves an issue, explicitly name the issue's number in the PR's description or in a commit message.  When the PR is merged, the issue(s) will be automatically closed.
6. Feel free to cross-reference issues and commits, even between repos; this is encouraged, GitHub will display the link.
7. You *may* want to disable desktop notifications for `#github` in the Slack room.
8. Not all issues are created equal; one issue may take weeks to resolve, and another may take minutes.  But if you *intend* for an issue to take weeks to resolve, you might consider splitting it up and putting it all into a milestone.

Any lapse in memory of these things will result in mild inconveniences.
