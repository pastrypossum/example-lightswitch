---
path: "src/main/java/**/domain/**"
---

You are editing domain layer code.

NEVER import org.springframework
NEVER import jakarta.persistence
Records for value objects, sealed interfaces, pattern matching.
Use records instead of lombok.
Use big decimal for all monetary calculations, never use double or float.
Use BigDecimal.valueOf() or new BigDecimal(String) for instantiation, never new BigDecimal(double).
Add explicit rounding using half-up and limited to 2 decimal places.
Test with junit and assertj.