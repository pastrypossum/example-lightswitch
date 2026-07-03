# Toggle Lights

As a homeowner,
I want to toggle my lights,
So that I can turn them on or off as needed.

## Known Constraints

- A light has three observable states: **OFF**, **ON**, and **NOT_REGISTERED**.
- A light's initial state upon registration is **OFF**.
- Each light is independently addressable by a unique identifier.
- Any number of lights may be registered.
- A light must be registered before it can be toggled.
- Toggling is the only supported state transition; direct state assignment
  (force ON / force OFF) is out of scope.

---

### Rule: Should toggle a light's state when toggled

| Current State | Action | Resulting State |
|---------------|--------|-----------------|
| OFF           | toggle | ON              |
| ON            | toggle | OFF             |

- **Example:** The one where I enter the room and turn the lights on → a registered light in state OFF is toggled and becomes ON.
- **Example:** The one where I leave the room and turn the lights off → a registered light in state ON is toggled and becomes OFF.
- **Counter-example:** The one where the same light is toggled twice in
  succession → it returns to its original state (this is a consequence of the
  two examples above, not a separate acceptance criterion).

---

### Rule: Should report NOT_REGISTERED when toggling an unregistered light

- **Example:** The one where the homeowner attempts to toggle a light ID that
  has never been registered → the light's state is reported as NOT_REGISTERED.
- **Counter-example:** The one where the homeowner toggles a light that exists
  but is currently OFF → the request is accepted and the light turns ON
  (OFF is a valid registered state, not an error).
