# Add Lights to a Room

As a homeowner, I want to add a set of lights to a room, so I can adjust the lighting using a switch of my choice.

## Known Constraints

- Room names are required and must be non-empty
- Room names must be unique (case-insensitive); two rooms cannot share the same name
- A room must contain at least 1 light
- A room can contain at most 10 lights
- There is no limit on the number of rooms
- Each light has a system-assigned unique ID; lights do not have user-provided names
- Deleting a room removes all lights within it

## Rules

### Rule: Should create a named room

A homeowner can create a new room by providing a unique name.

**Example:**
- The one where a homeowner creates a room named "Living Room" — succeeds

**Counter-examples:**
- The one where attempting to create a room with no name fails
- The one where attempting to create a room with the same name as an existing room fails (case-insensitive)

---

### Rule: Should be able to add lights to a room (between 1 and 10)

A homeowner can add individual lights to a room up to a maximum of 10. Each added light receives a system-assigned unique ID.

| Scenario | Current Light Count | Lights to Add | Result |
|----------|---------------------|---------------|--------|
| Add first light to new room | 0 | 1 | Success, room now has 1 light |
| Add lights to existing room | 2 | 3 | Success, room now has 5 lights |
| Add light to reach maximum | 9 | 1 | Success, room now has 10 lights |
| Add lights exceeding maximum | 8 | 3 | Fails, cannot exceed 10 lights |

**Counter-examples:**
- The one where attempting to add lights when the room already has 10 fails

---

### Rule: Should be able to remove lights from a room

A homeowner can remove a light from a room by its ID, as long as at least one light remains.

**Example:**
- The one where a room has 3 lights and 1 is removed — succeeds, room now has 2 lights

**Counter-examples:**
- The one where a room has only 1 light and removal is attempted — fails, a room must always have at least 1 light
- The one where attempting to remove a light that does not exist in the room fails

---

### Rule: Should be able to delete a room including any lights within

A homeowner can delete a room, which automatically removes all lights associated with that room.

**Example:**
- The one where a room with lights is deleted — the room and all its lights are removed

**Counter-example:**
- The one where deleting a non-existent room fails

---
