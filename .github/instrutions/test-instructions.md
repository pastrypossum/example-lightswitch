---
paths: "src/test/java/**"
---

You are editing test code.

Tests are executable specifications.
@DisplayName on every class and method.
Use @Nested to group related tests.
Use @ParameterizedTest to model data-driven tests.

## Test type directories

Acceptance tests -> package `com.serenitydojo.cashback_rewards.acceptance_test`
                -> class name suffix `*AcceptanceIT`
                -> run during `mvn verify` phase

Unit tests       -> package `com.serenitydojo.cashback_rewards.unit_test`
                -> class name suffix `*Test`
                -> run during `mvn test` phase

Acceptance = @SpringBootTest + MockMvc. Full stack, no mocks.
Unit = pure Java, no Spring context.
Use assertj for all assertions
Inline test data per test, no shared fixtures.

NEVER recalculate expected values.
NEVER modify a test to make it pass.