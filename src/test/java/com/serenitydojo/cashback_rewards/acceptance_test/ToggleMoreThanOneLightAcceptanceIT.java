package com.serenitydojo.cashback_rewards.acceptance_test;

import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@Feature("Toggle more than one light")
@DisplayName("Toggle More Than One Light")
class ToggleMoreThanOneLightAcceptanceIT {

    @Autowired
    MockMvc mockMvc;

    @Nested
    @Story("Toggle More Than One Light")
    @DisplayName("Rule 1: Should report an error when a switch's group has no lights configured")
    class ShouldReportAnErrorWhenASwitchsGroupHasNoLightsConfigured {

        @Test
        @DisplayName("The one where a homeowner toggles a switch whose group has no lights — the system returns 400 \"no lights configured\" and no lights change")
        void theOneWhereAHomeownerTogglesASwitchWhoseGroupHasNoLights() throws Exception {
            // Create a group with no lights
            mockMvc.perform(post("/groups")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content("""
                                    { "id": "empty-group-rule1", "lightIds": [] }
                                    """))
                    .andExpect(status().isCreated());

            // Create a switch referencing that empty group
            mockMvc.perform(post("/switches")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content("""
                                    { "id": "switch-no-lights", "groupId": "empty-group-rule1" }
                                    """))
                    .andExpect(status().isCreated());

            // Toggle the switch — must be rejected with 400 "no lights configured"
            mockMvc.perform(post("/switches/switch-no-lights/toggle"))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.message").value("no lights configured"));
        }
    }

    @Nested
    @Story("Toggle More Than One Light")
    @DisplayName("Rule 2: Should report an error when a switch's group exceeds 10 lights")
    class ShouldReportAnErrorWhenASwitchsGroupExceeds10Lights {

        @Test
        @DisplayName("The one where a group has 11 lights and the homeowner toggles a switch controlling it — the system returns 400 \"capacity exceeded\" and all 11 lights remain unchanged")
        void theOneWhereAGroupHas11LightsAndTheHomeownerTogglesASwitch() throws Exception {
            // Create 11 lights
            mockMvc.perform(post("/lights")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content("""
                                    { "id": "light-1" }
                                    """))
                    .andExpect(status().isCreated());
            mockMvc.perform(post("/lights")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content("""
                                    { "id": "light-2" }
                                    """))
                    .andExpect(status().isCreated());
            mockMvc.perform(post("/lights")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content("""
                                    { "id": "light-3" }
                                    """))
                    .andExpect(status().isCreated());
            mockMvc.perform(post("/lights")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content("""
                                    { "id": "light-4" }
                                    """))
                    .andExpect(status().isCreated());
            mockMvc.perform(post("/lights")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content("""
                                    { "id": "light-5" }
                                    """))
                    .andExpect(status().isCreated());
            mockMvc.perform(post("/lights")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content("""
                                    { "id": "light-6" }
                                    """))
                    .andExpect(status().isCreated());
            mockMvc.perform(post("/lights")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content("""
                                    { "id": "light-7" }
                                    """))
                    .andExpect(status().isCreated());
            mockMvc.perform(post("/lights")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content("""
                                    { "id": "light-8" }
                                    """))
                    .andExpect(status().isCreated());
            mockMvc.perform(post("/lights")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content("""
                                    { "id": "light-9" }
                                    """))
                    .andExpect(status().isCreated());
            mockMvc.perform(post("/lights")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content("""
                                    { "id": "light-10" }
                                    """))
                    .andExpect(status().isCreated());
            mockMvc.perform(post("/lights")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content("""
                                    { "id": "light-11" }
                                    """))
                    .andExpect(status().isCreated());

            // Create a group with all 11 lights
            mockMvc.perform(post("/groups")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content("""
                                    { "id": "group-11-lights", "lightIds": ["light-1", "light-2", "light-3", "light-4", "light-5", "light-6", "light-7", "light-8", "light-9", "light-10", "light-11"] }
                                    """))
                    .andExpect(status().isCreated());

            // Create a switch referencing the group with 11 lights
            mockMvc.perform(post("/switches")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content("""
                                    { "id": "switch-11-lights", "groupId": "group-11-lights" }
                                    """))
                    .andExpect(status().isCreated());

            // Toggle the switch — must be rejected with 400 "capacity exceeded"
            mockMvc.perform(post("/switches/switch-11-lights/toggle"))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.message").value("capacity exceeded"));

            // Verify all 11 lights remain OFF (unchanged)
            mockMvc.perform(get("/lights/light-1"))
                    .andExpect(jsonPath("$.state").value("OFF"));
            mockMvc.perform(get("/lights/light-2"))
                    .andExpect(jsonPath("$.state").value("OFF"));
            mockMvc.perform(get("/lights/light-3"))
                    .andExpect(jsonPath("$.state").value("OFF"));
            mockMvc.perform(get("/lights/light-4"))
                    .andExpect(jsonPath("$.state").value("OFF"));
            mockMvc.perform(get("/lights/light-5"))
                    .andExpect(jsonPath("$.state").value("OFF"));
            mockMvc.perform(get("/lights/light-6"))
                    .andExpect(jsonPath("$.state").value("OFF"));
            mockMvc.perform(get("/lights/light-7"))
                    .andExpect(jsonPath("$.state").value("OFF"));
            mockMvc.perform(get("/lights/light-8"))
                    .andExpect(jsonPath("$.state").value("OFF"));
            mockMvc.perform(get("/lights/light-9"))
                    .andExpect(jsonPath("$.state").value("OFF"));
            mockMvc.perform(get("/lights/light-10"))
                    .andExpect(jsonPath("$.state").value("OFF"));
            mockMvc.perform(get("/lights/light-11"))
                    .andExpect(jsonPath("$.state").value("OFF"));
        }

        @Test
        @DisplayName("The one where the group has exactly 10 lights — the toggle proceeds and all 10 lights change state")
        void theOneWhereTheGroupHasExactly10Lights() throws Exception {
            // Create 10 lights
            mockMvc.perform(post("/lights")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content("""
                                    { "id": "light-10-a" }
                                    """))
                    .andExpect(status().isCreated());
            mockMvc.perform(post("/lights")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content("""
                                    { "id": "light-10-b" }
                                    """))
                    .andExpect(status().isCreated());
            mockMvc.perform(post("/lights")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content("""
                                    { "id": "light-10-c" }
                                    """))
                    .andExpect(status().isCreated());
            mockMvc.perform(post("/lights")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content("""
                                    { "id": "light-10-d" }
                                    """))
                    .andExpect(status().isCreated());
            mockMvc.perform(post("/lights")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content("""
                                    { "id": "light-10-e" }
                                    """))
                    .andExpect(status().isCreated());
            mockMvc.perform(post("/lights")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content("""
                                    { "id": "light-10-f" }
                                    """))
                    .andExpect(status().isCreated());
            mockMvc.perform(post("/lights")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content("""
                                    { "id": "light-10-g" }
                                    """))
                    .andExpect(status().isCreated());
            mockMvc.perform(post("/lights")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content("""
                                    { "id": "light-10-h" }
                                    """))
                    .andExpect(status().isCreated());
            mockMvc.perform(post("/lights")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content("""
                                    { "id": "light-10-i" }
                                    """))
                    .andExpect(status().isCreated());
            mockMvc.perform(post("/lights")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content("""
                                    { "id": "light-10-j" }
                                    """))
                    .andExpect(status().isCreated());

            // Create a group with exactly 10 lights
            mockMvc.perform(post("/groups")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content("""
                                    { "id": "group-10-lights", "lightIds": ["light-10-a", "light-10-b", "light-10-c", "light-10-d", "light-10-e", "light-10-f", "light-10-g", "light-10-h", "light-10-i", "light-10-j"] }
                                    """))
                    .andExpect(status().isCreated());

            // Create a switch referencing the group with 10 lights
            mockMvc.perform(post("/switches")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content("""
                                    { "id": "switch-10-lights", "groupId": "group-10-lights" }
                                    """))
                    .andExpect(status().isCreated());

            // Toggle the switch — must succeed
            mockMvc.perform(post("/switches/switch-10-lights/toggle"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.switchState").value("ON"))
                    .andExpect(jsonPath("$.lights.length()").value(10))
                    .andExpect(jsonPath("$.lights[0].state").value("ON"))
                    .andExpect(jsonPath("$.lights[1].state").value("ON"))
                    .andExpect(jsonPath("$.lights[2].state").value("ON"))
                    .andExpect(jsonPath("$.lights[3].state").value("ON"))
                    .andExpect(jsonPath("$.lights[4].state").value("ON"))
                    .andExpect(jsonPath("$.lights[5].state").value("ON"))
                    .andExpect(jsonPath("$.lights[6].state").value("ON"))
                    .andExpect(jsonPath("$.lights[7].state").value("ON"))
                    .andExpect(jsonPath("$.lights[8].state").value("ON"))
                    .andExpect(jsonPath("$.lights[9].state").value("ON"));
        }
    }

    @Nested
    @Story("Toggle More Than One Light")
    @DisplayName("Rule 3: Must drive all lights in the group to the new switch state when toggled")
    class MustDriveAllLightsInTheGroupToTheNewSwitchState {

        @Test
        @DisplayName("The one where a switch is OFF and the homeowner toggles it ON — all lights in the group become ON regardless of their prior states")
        void theOneWhereASwitchIsOffAndTheHomeownerTogglesItOn() throws Exception {
            // Create 3 lights
            mockMvc.perform(post("/lights")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content("""
                                    { "id": "light-rule3-1" }
                                    """))
                    .andExpect(status().isCreated());
            mockMvc.perform(post("/lights")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content("""
                                    { "id": "light-rule3-2" }
                                    """))
                    .andExpect(status().isCreated());
            mockMvc.perform(post("/lights")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content("""
                                    { "id": "light-rule3-3" }
                                    """))
                    .andExpect(status().isCreated());

            // Create a group with the 3 lights
            mockMvc.perform(post("/groups")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content("""
                                    { "id": "group-rule3-on", "lightIds": ["light-rule3-1", "light-rule3-2", "light-rule3-3"] }
                                    """))
                    .andExpect(status().isCreated());

            // Create a switch referencing the group (switch starts OFF by default)
            mockMvc.perform(post("/switches")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content("""
                                    { "id": "switch-rule3-on", "groupId": "group-rule3-on" }
                                    """))
                    .andExpect(status().isCreated());

            // Toggle the switch — switch goes from OFF to ON
            mockMvc.perform(post("/switches/switch-rule3-on/toggle"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.switchId").value("switch-rule3-on"))
                    .andExpect(jsonPath("$.switchState").value("ON"))
                    .andExpect(jsonPath("$.groupId").value("group-rule3-on"))
                    .andExpect(jsonPath("$.lights.length()").value(3))
                    .andExpect(jsonPath("$.lights[0].id").value("light-rule3-1"))
                    .andExpect(jsonPath("$.lights[0].state").value("ON"))
                    .andExpect(jsonPath("$.lights[1].id").value("light-rule3-2"))
                    .andExpect(jsonPath("$.lights[1].state").value("ON"))
                    .andExpect(jsonPath("$.lights[2].id").value("light-rule3-3"))
                    .andExpect(jsonPath("$.lights[2].state").value("ON"));

            // Verify all 3 lights are now ON
            mockMvc.perform(get("/lights/light-rule3-1"))
                    .andExpect(jsonPath("$.state").value("ON"));
            mockMvc.perform(get("/lights/light-rule3-2"))
                    .andExpect(jsonPath("$.state").value("ON"));
            mockMvc.perform(get("/lights/light-rule3-3"))
                    .andExpect(jsonPath("$.state").value("ON"));
        }

        @Test
        @DisplayName("The one where a switch is ON and the homeowner toggles it OFF — all lights in the group become OFF")
        void theOneWhereASwitchIsOnAndTheHomeownerTogglesItOff() throws Exception {
            // Create 2 lights
            mockMvc.perform(post("/lights")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content("""
                                    { "id": "light-rule3-off-1" }
                                    """))
                    .andExpect(status().isCreated());
            mockMvc.perform(post("/lights")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content("""
                                    { "id": "light-rule3-off-2" }
                                    """))
                    .andExpect(status().isCreated());

            // Create a group with the 2 lights
            mockMvc.perform(post("/groups")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content("""
                                    { "id": "group-rule3-off", "lightIds": ["light-rule3-off-1", "light-rule3-off-2"] }
                                    """))
                    .andExpect(status().isCreated());

            // Create a switch referencing the group (switch starts OFF by default)
            mockMvc.perform(post("/switches")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content("""
                                    { "id": "switch-rule3-off", "groupId": "group-rule3-off" }
                                    """))
                    .andExpect(status().isCreated());

            // Toggle the switch first to turn lights ON
            mockMvc.perform(post("/switches/switch-rule3-off/toggle"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.switchState").value("ON"));

            // Verify lights are ON before toggling back
            mockMvc.perform(get("/lights/light-rule3-off-1"))
                    .andExpect(jsonPath("$.state").value("ON"));
            mockMvc.perform(get("/lights/light-rule3-off-2"))
                    .andExpect(jsonPath("$.state").value("ON"));

            // Toggle the switch again — switch goes from ON to OFF
            mockMvc.perform(post("/switches/switch-rule3-off/toggle"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.switchId").value("switch-rule3-off"))
                    .andExpect(jsonPath("$.switchState").value("OFF"))
                    .andExpect(jsonPath("$.groupId").value("group-rule3-off"))
                    .andExpect(jsonPath("$.lights.length()").value(2))
                    .andExpect(jsonPath("$.lights[0].id").value("light-rule3-off-1"))
                    .andExpect(jsonPath("$.lights[0].state").value("OFF"))
                    .andExpect(jsonPath("$.lights[1].id").value("light-rule3-off-2"))
                    .andExpect(jsonPath("$.lights[1].state").value("OFF"));

            // Verify all 2 lights are now OFF
            mockMvc.perform(get("/lights/light-rule3-off-1"))
                    .andExpect(jsonPath("$.state").value("OFF"));
            mockMvc.perform(get("/lights/light-rule3-off-2"))
                    .andExpect(jsonPath("$.state").value("OFF"));
        }
    }

    @Nested
    @Story("Toggle More Than One Light")
    @DisplayName("Rule 4: Must derive group state from all switches controlling it")
    class MustDeriveGroupStateFromAllSwitchesControllingIt {

        @Test
        @DisplayName("The one where two switches control the same group and one is toggled ON — the group becomes ON even though the other switch remains OFF")
        void theOneWhereTwoSwitchesControlTheSameGroupAndOneIsToggledOn() throws Exception {
            // Create a light for the group
            mockMvc.perform(post("/lights")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content("""
                                    { "id": "light-rule4-multi-switch-1" }
                                    """))
                    .andExpect(status().isCreated());

            // Create a group with the light
            mockMvc.perform(post("/groups")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content("""
                                    { "id": "group-rule4-multi-switch", "lightIds": ["light-rule4-multi-switch-1"] }
                                    """))
                    .andExpect(status().isCreated());

            // Create two switches that both control the same group
            mockMvc.perform(post("/switches")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content("""
                                    { "id": "switch-rule4-multi-1", "groupId": "group-rule4-multi-switch" }
                                    """))
                    .andExpect(status().isCreated());

            mockMvc.perform(post("/switches")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content("""
                                    { "id": "switch-rule4-multi-2", "groupId": "group-rule4-multi-switch" }
                                    """))
                    .andExpect(status().isCreated());

            // Initially both switches are OFF, so group should be OFF
            mockMvc.perform(get("/groups/group-rule4-multi-switch"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.state").value("OFF"));

            // Toggle first switch to ON
            mockMvc.perform(post("/switches/switch-rule4-multi-1/toggle"))
                    .andExpect(status().isOk());

            // Group should now be ON because at least one switch is ON
            mockMvc.perform(get("/groups/group-rule4-multi-switch"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.state").value("ON"));

        }

        @Test
        @DisplayName("The one where the last ON switch controlling a group is toggled OFF — the group becomes OFF")
        void theOneWhereTheLastOnSwitchControllingAGroupIsToggledOff() throws Exception {
            // Create a light for the group
            mockMvc.perform(post("/lights")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content("""
                                    { "id": "light-rule4-last-switch" }
                                    """))
                    .andExpect(status().isCreated());

            // Create a group with the light
            mockMvc.perform(post("/groups")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content("""
                                    { "id": "group-rule4-last-switch", "lightIds": ["light-rule4-last-switch"] }
                                    """))
                    .andExpect(status().isCreated());

            // Create two switches that both control the same group
            mockMvc.perform(post("/switches")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content("""
                                    { "id": "switch-rule4-last-1", "groupId": "group-rule4-last-switch" }
                                    """))
                    .andExpect(status().isCreated());

            mockMvc.perform(post("/switches")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content("""
                                    { "id": "switch-rule4-last-2", "groupId": "group-rule4-last-switch" }
                                    """))
                    .andExpect(status().isCreated());

            // Toggle both switches to ON
            mockMvc.perform(post("/switches/switch-rule4-last-1/toggle"))
                    .andExpect(status().isOk());

            mockMvc.perform(post("/switches/switch-rule4-last-2/toggle"))
                    .andExpect(status().isOk());

            // Group should be ON since both switches are ON
            mockMvc.perform(get("/groups/group-rule4-last-switch"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.state").value("ON"));

            // Toggle first switch back to OFF
            mockMvc.perform(post("/switches/switch-rule4-last-1/toggle"))
                    .andExpect(status().isOk());

            // Group should still be ON because second switch is still ON
            mockMvc.perform(get("/groups/group-rule4-last-switch"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.state").value("ON"));

            // Toggle second (last ON) switch to OFF
            mockMvc.perform(post("/switches/switch-rule4-last-2/toggle"))
                    .andExpect(status().isOk());

            // Group should now be OFF because all switches are OFF
            mockMvc.perform(get("/groups/group-rule4-last-switch"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.state").value("OFF"));
        }
    }

    @Nested
    @Story("Toggle More Than One Light")
    @DisplayName("Rule 5: A light may belong to at most one group")
    class ALightMayBelongToAtMostOneGroup {

        @Test
        @DisplayName("The one where a homeowner tries to add a light to a second group — the system rejects the request")
        void theOneWhereAHomeownerTriesToAddALightToASecondGroup() throws Exception {
            // Create a light
            mockMvc.perform(post("/lights")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content("""
                                    { "id": "light-rule5-1" }
                                    """))
                    .andExpect(status().isCreated());

            // Create first group with the light
            mockMvc.perform(post("/groups")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content("""
                                    { "id": "group-rule5-first", "lightIds": ["light-rule5-1"] }
                                    """))
                    .andExpect(status().isCreated());

            // Attempt to add the same light to a second group — must be rejected
            mockMvc.perform(post("/groups")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content("""
                                    { "id": "group-rule5-second", "lightIds": ["light-rule5-1"] }
                                    """))
                    .andExpect(status().isBadRequest());
        }

        @Test
        @DisplayName("The one where a light is removed from its current group and then added to a new group — the system accepts this because the light is no longer a member of any group")
        void theOneWhereALightIsRemovedFromCurrentGroupAndAddedToNewGroup() throws Exception {
            // Create a light
            mockMvc.perform(post("/lights")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content("""
                                    { "id": "light-rule5-2" }
                                    """))
                    .andExpect(status().isCreated());

            // Create first group with the light
            mockMvc.perform(post("/groups")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content("""
                                    { "id": "group-rule5-first-remove", "lightIds": ["light-rule5-2"] }
                                    """))
                    .andExpect(status().isCreated());

            // Remove the light from the first group (update the group to have empty lightIds)
            mockMvc.perform(post("/groups")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content("""
                                    { "id": "group-rule5-first-remove", "lightIds": [] }
                                    """))
                    .andExpect(status().isCreated());

            // Add the light to a second group — must succeed because light is no longer in any group
            mockMvc.perform(post("/groups")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content("""
                                    { "id": "group-rule5-second-add", "lightIds": ["light-rule5-2"] }
                                    """))
                    .andExpect(status().isCreated());
        }
    }
}
