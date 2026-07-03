# Copilot Instructions

## Repository context

A spring boot microservice implementing a cashback rewards program.

## Build and test commands

- Run the current test suite: `mvn clean test`
- Run the full Maven verification lifecycle: `mvn clean verify`
- Build the application jar: `mvn clean package`
- Run the application locally: `mvn spring-boot:run`

## High-level architecture: Hexagonal (Ports and Adapters)

- A spring boot bootstrap application under the root package `com.serenitydojo.cashback_rewards`.

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
- Keep all new code under the existing package root `com.serenitydojo.cashback_rewards`.
- When the hexagonal structure is introduced, preserve the dependency direction promised by the README: the domain stays free of Spring and JPA, the application layer defines ports, and adapters contain framework-specific code.
- Use version-controlled spec files as project artifacts when the feature workflow is introduced; do not leave specs only in chat output.

## Coding standard

### REST & Spring
- Constructor injection for all dependencies, no field injection.
- Domain exception for business rule violations, map to HTTP in controller only.
- Never swallow exceptions or lead infrastructure details.

## Workflow conventions

### Discovery workflow
- Invoke the discovery agent with `/discovery "<user story>"` to produce an Example Mapping spec.
- The agent saves output to `doc/specs/<feature>.md` and follows the conventions in `.github/instrutions/example-map-instructions.md`.
- The intended flow is: run `/discovery` → refine the spec → derive acceptance tests → implement with TDD.
- Once the final spec review is approved and the committed `doc/specs/<feature>.md` is in place, follow the repo-controlled rule cycle workflow in `.github/instrutions/rule-cycle-workflow.md`.