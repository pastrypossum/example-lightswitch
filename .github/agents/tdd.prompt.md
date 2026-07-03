---
name: tdd
model: claude-sonnet-4.6
description: Run one TDD cycle (RED → GREEN → REFACTOR → CHALLENGE → STOP)
argument-hint: "<test class or method to drive>"
---

Run ONE TDD cycle for: $ARGUMENTS

Architecture rules (do not read copilot-instructions.md — use these):
- Domain: pure Java, no Spring/JPA. Ports in domain/port/in (XxxUseCase) and domain/port/out (LoadXxxPort/SaveXxxPort).
- Application: @Service orchestration only, no business logic.
- Adapter/in/web: controllers + DTOs only, no domain objects in HTTP responses.
- Adapter/out/persistence: JPA entities + repos only.
- Constructor injection everywhere. Domain exceptions mapped to HTTP in GlobalExceptionHandler only.
- Unit tests: `com.serenitydojo.cashback_rewards.unit_test`, suffix `*Test`
- Acceptance tests: `com.serenitydojo.cashback_rewards.acceptance_test`, suffix `*AcceptanceIT`

## RED — confirm the failure
Run the failing test first. Read the failure message.
Understand WHY it fails before writing any production code.
If the test already passes, STOP — something is wrong.

## GREEN — minimum code to pass
Write the MINIMUM production code to make this one test pass.
No extra methods, no anticipating the next test, no abstractions until refactoring demands them.

## REFACTOR — clean up with confidence
Run ALL tests after refactoring. Fix anything that breaks before moving on.

## CHALLENGE — drive out edge cases
Propose at least one edge case. If approved, it becomes the next RED.

## STOP
Report: which test passes, what production code was written/modified, what was refactored, what edge case is proposed.
Do NOT write additional tests. Do NOT modify existing tests to make them pass. Wait for the user.
