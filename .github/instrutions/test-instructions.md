---
paths: "src/test/java/**"
---

You are editing test code.

Tests are executable specifications.
Each @Feature has a test class and @Description to describe the feature.
Each @Story will annotate every nested class that implements a rule of the story.
Each nested class will have a @DisplayName to describe the rule.
Each test in a nested class will @DisplayName to describe the scenario.

MUST use @ParameterizedTest for all scenarios where multiple inputs and output share the same logic.

MUST use domain value objects in test data where possible, not raw strings or primitives.

MUST use assertj for all assertions.
MUST use assertj iteration assertions for collections.
MUST use assertj soft assertions for multiple assertions in a single test.

NEVER recalculate expected values.
NEVER modify a test to make it pass.

## Test type directories

Acceptance = @SpringBootTest + MockMvc. Full stack, no mocks.
Acceptance tests -> package `com.light_switch.acceptance_test`
                -> class name suffix `*AcceptanceIT`
                -> run during `mvn verify` phase

Unit = pure Java, no Spring context.
Unit tests       -> package `com.light_switch.unit_test`
                -> class name suffix `*Test`
                -> run during `mvn test` phase