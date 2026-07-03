# Toggle More Than One Light

**GitHub Issue:** [#1 Toggle more than one light](https://github.com/pastrypossum/example-lightswitch/issues/1)

**Story**

> As a homeowner,
> I want to control more than one light with a toggle switch,
> so that I can support a larger room that requires more lighting.

---

## Known Constraints

- `Light` already exists as a domain entity with an `id` and a `LightState` (`OFF`, `ON`, `NOT_REGISTERED`). Every light is registered individually and starts `OFF`.
- `LightState.NOT_REGISTERED` is the sentinel value returned today when a toggle is attempted on an unregistered light ID.
- There is no existing concept of a multi-light switch or room grouping; this feature introduces that concept.
- The existing API is: `POST /lights` to register a light, `POST /lights/{id}/toggle` to operate a single light.

---

## Rule 1: Should support between 1 and 10 lights registered to a switch

A switch may only be operated when it has 1 to 10 lights assigned to it. Both bounds are inclusive.

| Lights assigned | Toggle switch | Outcome |
|---|---|---|
| 0 | Toggle | Error — no lights registered to this switch |
| 1 | Toggle | The 1 light changes state |
| 9 | Toggle | All 9 lights change state |
| 10 | Toggle | All 10 lights change state |
| 11 | Toggle | No lights change state |

**Counter-example:** The one where a switch has exactly 10 lights and a homeowner adds an 11th — operating the switch no longer changes any lights.

> ⚠️ **Inconsistency in the AC:** 0 lights says "throw an error on switch"; 11 lights says "turn no lights on" (silent no-op). These are different behaviours for structurally similar violations. See Question 1.

### Questions

1. For 0 lights the AC specifies "throw an error"; for 11 lights it specifies "turn no lights on" (silent no-op). Is this distinction intentional, or should both cases produce an explicit error?
2. Is the 10-light limit enforced at **registration time** (you cannot add an 11th light to a switch's configuration) or only at **operation time** (the switch may hold 11 IDs but toggling does nothing)?
3. Should 0 lights and 11+ lights produce the same HTTP status / error code, or different ones?

---

## Rule 2: Must toggle all registered lights in unison when the switch is operated

When a valid switch (1–10 lights) is operated, every light assigned to it changes state together in a single operation.

- **Example:** The one where all lights are `OFF` and the switch is toggled — all lights become `ON`.
- **Example:** The one where all lights are `ON` and the switch is toggled — all lights become `OFF`.

**Counter-example:** The one where the switch has a mixed state (some lights `ON`, some `OFF`) — the expected outcome is undefined until the mixed-state policy is decided (see Question 4).

### Questions

4. If lights are in a **mixed state** when the switch is operated, what should happen?
   - Toggle each light individually (so a mixed state reverses itself)?
   - Force all lights `ON`?
   - Force all lights `OFF`?
   - Read the majority state and flip everything to match the minority?
5. Should the multi-light toggle be **atomic**? If persisting one light fails mid-operation, should the whole toggle roll back, or is partial success acceptable?
6. What should the API **response** look like after a successful multi-light toggle — a per-light list of new states, or a summary (e.g. `"all": "ON"`)?

---

## Rule 3: Must only toggle lights that are individually registered in the system

If a switch references a light ID that has never been registered as a `Light`, the system must treat that light as inoperable (`NOT_REGISTERED`).

- **Example:** The one where a switch has 3 light IDs but one was never registered — toggling the switch encounters a `NOT_REGISTERED` light.

**Counter-example:** The one where all light IDs on the switch are registered — all lights toggle normally with no errors.

### Questions

7. If a switch has 5 lights but 1 is `NOT_REGISTERED`, does the toggle **partially succeed** for the other 4, or does the entire operation fail?
8. Should the system **reject** a switch configuration that references an unregistered light ID at registration time, or is this only caught when the switch is toggled?

---

## Rule 4: Should require a switch to be registered as a named entity before it can be operated

There is currently no switch concept in the domain. A multi-light switch must be a registerable entity with its own identity and an assigned list of light IDs.

- **Example:** The one where a homeowner registers a new switch with 3 light IDs, then toggles the switch — all 3 lights change state together.
- **Example:** The one where a homeowner tries to toggle a switch ID that has never been registered — the system returns a "switch not found" response (analogous to `NOT_REGISTERED` for individual lights).

**Counter-example:** The one where a homeowner registers a switch using an ID that already exists — the system should either reject the duplicate registration or explicitly overwrite (policy decision, see Question 9).

### Questions

9. What should happen if a homeowner registers a switch with an **ID that already exists** — overwrite it silently, reject it with an error, or merge the light lists?
10. Can a **single light ID belong to more than one switch** (e.g. a hallway light shared by two rooms)? If yes, what happens when both switches are toggled independently?
11. Can lights be **added to or removed from** a switch after its initial registration, or is the light list fixed at creation?
12. What is the intended **API shape** for this feature? For example:
    - `POST /switches` with body `{"id": "living-room", "lightIds": ["l1", "l2", "l3"]}`
    - `POST /switches/{id}/toggle` to operate it

---

## Discovery Questions — Full List

### Rules

1. For 0 lights the AC says "throw an error"; for 11 lights it says "turn no lights on". Is this distinction intentional, or should both be errors?
2. Is the 10-light limit enforced at registration time or only at toggle time?
3. Should 0 lights and 11+ lights produce the same HTTP status / error response, or different ones?

### Examples

4. If lights are in a mixed state (`ON`/`OFF` mix), what should toggling the switch do?
5. Should a multi-light toggle be atomic (all-or-nothing on failure)?
6. What should the API response contain after a successful multi-light toggle?
7. If a switch references one `NOT_REGISTERED` light, does the toggle partially succeed for the others, or fail entirely?
8. Should a switch configuration referencing an unregistered light ID be rejected at registration time or only at toggle time?

### Unknowns / Assumptions

9. What happens if a switch is registered with an ID that already exists — overwrite, reject, or merge?
10. Can a single light ID belong to more than one switch?
11. Can lights be added to or removed from a switch after initial registration?
12. What is the intended API shape for registering a switch and toggling it?
