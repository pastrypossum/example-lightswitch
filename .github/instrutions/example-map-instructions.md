# Example Mapping Instructions

Use this guidance when writing discovery output or updating spec files.

## Rules

- Write each rule as a normative statement beginning with `Should...` or `Must...`.
- Rules must be independent of each other. Rules group examples; examples illustrate the rule.
  A rule that can only be understood alongside another is not independent — fold it into the
  relevant rule or move it to Known constraints.

## Examples

- Keep each example distinct; avoid duplicate examples that only change amounts, merchant names, wording, or channel when the business outcome is unchanged.
- Prefer a compact markdown table when inputs vary independently.
- Include at least one valid counter-example per rule when a meaningful boundary exists.
- Use plain business language only; do not use Gherkin syntax or UI steps.

## Output shape

1. The story title
2. Known constraints (hard environmental facts that apply across all rules — currency, rounding, timezone, etc.)
3. Rule
4. Example
5. Counter-example
6. Questions

Leave one blank line between rule blocks. Save the final output to `doc/specs/<feature>.md`.