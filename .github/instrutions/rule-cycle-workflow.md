# Rule Cycle Workflow

Use this workflow after discovery is complete and the final spec has been committed.

## Recommended Models

| Step | Model | Rationale |
|---|---|---|
| `/discovery` | `claude-haiku-4.5` | Guided Q&A — prompt drives quality |
| `/accept` | `claude-haiku-4.5` | Mechanical scaffolding from spec |
| `/tdd` | `claude-sonnet-4.6` (medium effort) | Solid coding with hexagonal architecture awareness |
| `/review` | `claude-haiku-4.5` (medium effort) | Thorough compliance analysis at lower cost |

Upgrade a stage to `claude-sonnet-5` only if the output quality is not good enough in practice.

For each remaining rule in the spec:

1. Run `/accept "<rule name> @doc/specs/<feature>.md"` for exactly one rule.
2. Run `/tdd "<test class or method to drive>"` for that same rule.
3. Run `/review`.
4. Check the review results for `Feedback Needed`, `Rework Needed`, and `PR Ready`.
5. If review raises feedback, update the spec and/or code, then rerun `/review` for the same rule.
6. Keep rerunning `/review` until the feedback is cleared and the review is complete.

After all rules are complete:

1. Run the full PR test suite.
2. Raise the pull request.

Do not move to the next rule until the current rule has no remaining review feedback.

The review output should show:

- Review Status
- Changes Overview
- Summary
- Passed
- Issues
- Missing Coverage
- Feedback Needed
- Rework Needed
- Re-run Review
- Final Verification
- PR Ready
- Quick Response
- Recommendation
