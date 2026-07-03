---
path: "src/main/java/**/adapter/in/web/**"
---

You are editing web adapter code.

Controllers are THIN so delegate to application services immediately.
NEVER put business logic in controllers, only HTTP mapping and request validation.
Use DTOs for request and response bodies, never domain objects.
@Valid on request bodies, 201 create, 200 query, 400 validation, 404 not found, 500 server error.
Test with @WebMvcTest (one controller)