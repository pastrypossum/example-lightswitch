---
name: accept
model: claude-haiku-4.5
description: Write a failing acceptance test for the next spec rule
argument-hint: "<rule name> @doc/specs/<feature>.md"
---
Write a failing acceptance test for: $ARGUMENTS

Read the spec file for the rule, its examples, and counter-examples. Do not read any other files.

Architecture rules:
- Package: `com.serenitydojo.cashback_rewards.acceptance_test`, class `<Feature>AcceptanceIT`
- One @Nested inner class per rule, one @Test per spec example
- @DisplayName uses spec's exact business language
- @SpringBootTest + MockMvc only — no mocks, no direct service/repo calls
- @Transactional at class level for test isolation
- Assert exact values from spec examples

Do NOT write production code. The test MUST FAIL.
Run the test. Report: rule tested, examples covered, failure reason. STOP.