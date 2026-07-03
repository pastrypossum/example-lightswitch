---
name: accept
model: claude-haiku-4.5
description: Write a failing acceptance test for the next spec rule
argument-hint: "<rule name> @doc/specs/<feature>.md"
---
Write a failing acceptance test for: $ARGUMENTS

Read these files before writing anything:
1. The spec file for the rule, its examples, and counter-examples.
2. The existing `*AcceptanceIT` class (if it exists) — match its exact pattern (@Nested, @Transactional, MockMvc setup, helper methods).
3. All existing controllers in `adapter/in/web/` — only call endpoints that already exist or that this rule explicitly requires to be created. Do not call endpoints outside this rule's scope.

Architecture rules:
- Package: `com.serenitydojo.cashback_rewards.acceptance_test`, class `<Feature>AcceptanceIT`
- One @Nested inner class per rule, one @Test per spec example
- @DisplayName uses spec's exact business language
- @SpringBootTest + MockMvc only — no mocks, no direct service/repo calls
- @Transactional at class level for test isolation
- Assert exact values from spec examples

Do NOT write production code. The test MUST FAIL.
Run the test. Report: rule tested, examples covered, failure reason. STOP.