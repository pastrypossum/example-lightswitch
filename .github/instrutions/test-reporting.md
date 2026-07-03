# Test Reporting Instructions

Reporting should identify features, stories, rules and tests

A class should follow this structure for test reporting:

@Feature("Feature Name") - for each acceptance test class and should be the same as the feature name in the spec file.
@Story("Story Name") - for each nested class and should be the same as the story name in the spec file.
@Displayname("Display Name") - for each class and method and should be the same as the display name in the spec file.
```
@Feature("Light switch")
@DisplayName("Toggle room lights")
class ToggleLightsAcceptanceIT {

    ...
    
    @Nested
    @Story("Toggle Lights")
    @@DisplayName("As a homeShould toggle a light's state when toggled")
    class ShouldToggleALightsStateWhenToggled {


        @Nested
        @Story("Toggle Lights")
        @DisplayName("Rule: Should toggle a light's state when toggled")
        class ShouldToggleALightsStateWhenToggled {
        
            @Test
            @DisplayName("The one where I enter the room and turn the lights on")
            void theOneWhereIEnterTheRoomAndTurnTheLightsOn() throws Exception {
            }
        }
    }
}
```