# Copilot Instructions

## Repository context

A spring boot microservice implementing a series of light switches.

## Build and test commands

- Run the current test suite: `mvn clean test`
- Run the full Maven verification lifecycle: `mvn clean verify`
- Build the application jar: `mvn clean package`
- Run the application locally: `mvn spring-boot:run`

## High-level architecture: Hexagonal (Ports and Adapters)

- A spring boot bootstrap application under the root package `com.light_switch`.

domain/ - pure java business logic and value objects, no framework dependencies
  model/ - entries and value objects
  service/ - business rules
  port/
    in/
    out/
application/ - interfaces for ports, @Service orchestration only, no business logic
adapter/
  in/web/ - REST controllers and DTO only, no business logic
  out/persistence/ - JPA repositories and entries only, no business logic

Domain NEVER imports spring framework or JPA, adapter packages.
Controllers NEVER contain business logic (not even simple if statements).
Business rules belong in the domain layer (model methods, use domain services only for cross aggregate logic).
Dependencies flow inward from adapter to application to domain.
Outbound port naming in format LoadXxxPort / SaveXxxPort.
Inbound port naming in format XxxUseCase.

## Key conventions

- Target Java version is 25 (`pom.xml`).
- Keep all new code under the existing package root `com.light_switch`.
- When the hexagonal structure is introduced, preserve the dependency direction promised by the README: the domain stays free of Spring and JPA, the application layer defines ports, and adapters contain framework-specific code.
- Use version-controlled spec files as project artifacts when the feature workflow is introduced; do not leave specs only in chat output.

## Coding standard

### REST & Spring
- Constructor injection for all dependencies, no field injection.
- Domain exception for business rule violations, map to HTTP in controller only.
- Never swallow exceptions or lead infrastructure details.

### Editing web adapter code 
See @github/instrutions/web-instructions.md for guidance.

### Editing test code
See @github/instrutions/test-instructions.md for guidance.

### Editing persistence adapter code
See @github/instrutions/persistence-instructions.md for guidance.

### Editing domain layer code
See @github/instrutions/domain-instructions.md for guidance.

### Writing discovery output or updating spec files
See @github/instrutions/example-map-instructions.md for guidance.


## Development workflow

Use this workflow after discovery is complete and the final spec has been committed.

### Workflow steps

For each remaining rule in the spec, drive the full cycle without pausing between stages:

1. Run `/discovery "<user story>"` to produce an Example Mapping spec.
2. Refine the spec by proposing questions for a reviewer to answer, and update the spec with the answers.
3. Run `/accept "<rule name> @doc/specs/<feature>.md"` for exactly one rule.
4. Run `/tdd "<test class or method to drive>"` for that same rule.
5. Run `/review` for that same rule.
6. Check the review results for `Feedback Needed`, `Rework Needed`, and `PR Ready`.
7. If review raises feedback, update the spec and/or code, then rerun `/review` for the same rule.
8. Keep rerunning `/review` until the feedback is cleared and the review is complete.

Pause at step 2 for user feedback on discovery questions.
Do not pause between steps 3–5.
Pause at step 6 for user feedback on review questions.

After all rules are complete:

1. Run the full PR test suite.
2. Raise the pull request.

Do not move to the next rule until the current rule has no remaining review feedback.

### Format of review output

The review output should show:

- Issues found
- Missing coverage
- Feedback required
- Recommendation
- Rework completed
- Summary