---
path: "src/main/java/**/adapter/out/persistence/**"
---

You are editing persistence adapter code.

JPA repositories and related persistence code go here.
Map domain objects to JPA entities in this layer, never in the domain.
Implement outbound ports defined in the application layer here, never in the domain.
NEVER expose JPA entities outside this layer, always map to domain objects before returning from the adapter.
Test with @DataJpaTest.
Use Testcontainers for integration tests.