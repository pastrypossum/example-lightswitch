# Toggle More Than One Light

**GitHub Issue:** [#1 Toggle more than one light](https://github.com/pastrypossum/example-lightswitch/issues/1)

**Story**

> As a homeowner,
> I want to control more than one light with a toggle switch,
> so that I can support a larger room that requires more lighting.

---

## Known Constraints

- `Light` already exists as a domain entity with an `id` and a `LightState` (`OFF`, `ON`, `NOT_REGISTERED`). Every light is registered individually and starts `OFF`.
- **New entity — Group:** A named, persistable collection of lights. A light may belong to at most one group.
- **New entity — Switch:** A named, persistable entity that references a group and carries its own ON/OFF state. Multiple switches may reference the same group.
- Group membership is mutable: lights can be added to or removed from a group after creation.
- The 10-light capacity limit is enforced at **operation time** only. A group may be configured with any number of lights; the limit is checked when a switch toggle is attempted.
- The switch state is authoritative for the group it controls: when toggled, all lights in the group are driven to match the new switch state. There is no mixed-state concept.
- The group state is derived from all switches controlling it: the group is ON if at least one controlling switch is ON; the group is OFF if every controlling switch is OFF.

---

## Rule 1: Should report an error when a switch's group has no lights configured

A toggle attempted on a switch whose group contains zero lights must be rejected with an error. No light state changes.

- **Example:** The one where a homeowner toggles a switch whose group has no lights — the system returns 400 "no lights configured" and no lights change.
- **Counter-example:** The one where the group has exactly 1 light — the toggle proceeds and that light changes state.

---

## Rule 2: Should report an error when a switch's group exceeds 10 lights

A toggle attempted on a switch whose group contains more than 10 lights must be rejected with an error. No light state changes.

| Group size at toggle time | Outcome |
|---|---|
| 0 | 400 — "no lights configured" |
| 1 | All lights toggle successfully |
| 9 | All lights toggle successfully |
| 10 | All lights toggle successfully (boundary: maximum) |
| 11 | 400 — "capacity exceeded", no lights change |

- **Example:** The one where a group has 11 lights and the homeowner toggles a switch controlling it — the system returns 400 "capacity exceeded" and all 11 lights remain unchanged.
- **Counter-example:** The one where the group has exactly 10 lights — the toggle proceeds and all 10 lights change state.

> **Note:** A group may be *configured* with any number of lights. The 10-light ceiling is checked at toggle time, not at group-configuration time.

---

## Rule 3: Must drive all lights in the group to the new switch state when toggled

The switch state is authoritative. When a switch is toggled, every light in its group is set to match the resulting switch state. Individual light states are not consulted. There is no mixed-state scenario.

- **Example:** The one where a switch is OFF and the homeowner toggles it ON — all lights in the group become ON regardless of their prior states.
- **Example:** The one where a switch is ON and the homeowner toggles it OFF — all lights in the group become OFF.
The response includes the new state of every light in the group.

---

## Rule 4: Must derive group state from all switches controlling it

A group's observable state is determined by the collective state of every switch that references it.

| Switch states controlling the group | Group state |
|---|---|
| All switches OFF | Group OFF |
| At least one switch ON | Group ON |

- **Example:** The one where two switches control the same group and one is toggled ON — the group becomes ON even though the other switch remains OFF.
- **Example:** The one where the last ON switch controlling a group is toggled OFF — the group becomes OFF.
- **Counter-example:** The one where a group is controlled by only one switch — group state directly mirrors the single switch state.

---

## Rule 5: A light may belong to at most one group

A registered light may be added to a group only if it is not already a member of another group.

- **Example:** The one where a homeowner tries to add a light to a second group — the system rejects the request.
- **Counter-example:** The one where a light is removed from its current group and then added to a new group — the system accepts this because the light is no longer a member of any group.

---

## API Shape

Two separate resource trees are introduced:

### Switch resource

| Method | Path | Purpose |
|---|---|---|
| `POST` | `/switches` | Create or modify a named switch; body includes `id` and `groupId` |
| `GET` | `/switches` | List all switches with current state and associated group |
| `POST` | `/switches/{id}/toggle` | Toggle the switch; drives the group and returns per-light new states |

### Group resource

| Method | Path | Purpose |
|---|---|---|
| `POST` | `/groups` | Create or modify a named group; body includes `id` and `lightIds` |
| `GET` | `/groups` | List all groups |
| `GET` | `/groups/{id}` | View the lights belonging to a group |

---

## Toggle Response Shape

A successful `POST /switches/{id}/toggle` returns the per-light list of new states, for example:

```json
{
  "switchId": "living-room",
  "switchState": "ON",
  "groupId": "main-lights",
  "lights": [
    { "id": "l1", "state": "ON" },
    { "id": "l2", "state": "ON" },
    { "id": "l3", "state": "ON" }
  ]
}
```

---

## Best-Effort Toggle Behaviour

The multi-light toggle is **not atomic**. If toggling one light fails (e.g. infrastructure error), the remaining lights in the group are still toggled. The response should indicate which lights succeeded and which failed.

---

## Out of Scope

- Direct state assignment (force ON / force OFF) without using toggle.
- The existing single-light endpoint (`POST /lights/{id}/toggle`) is unchanged.
