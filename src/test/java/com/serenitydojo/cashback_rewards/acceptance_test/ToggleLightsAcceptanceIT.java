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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Feature("Light switch")
@DisplayName("Toggle Lights")
class ToggleLightsAcceptanceIT {

    @Autowired
    MockMvc mockMvc;

    @Nested
    @Story("Toggle Lights")
    @DisplayName("Rule: Should toggle a light's state when toggled")
    class ShouldToggleALightsStateWhenToggled {

        @Test
        @DisplayName("The one where I enter the room and turn the lights on")
        void theOneWhereIEnterTheRoomAndTurnTheLightsOn() throws Exception {
            // A light is registered — its initial state is OFF
            mockMvc.perform(post("/lights")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content("""
                                    { "id": "light-enter-room" }
                                    """))
                    .andExpect(status().isCreated());

            // Toggle: OFF → ON
            mockMvc.perform(post("/lights/light-enter-room/toggle"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.state").value("ON"));
        }

        @Test
        @DisplayName("The one where I leave the room and turn the lights off")
        void theOneWhereILeaveTheRoomAndTurnTheLightsOff() throws Exception {
            // A light is registered — its initial state is OFF
            mockMvc.perform(post("/lights")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content("""
                                    { "id": "light-leave-room" }
                                    """))
                    .andExpect(status().isCreated());

            // First toggle: OFF → ON
            mockMvc.perform(post("/lights/light-leave-room/toggle"))
                    .andExpect(status().isOk());

            // Second toggle: ON → OFF
            mockMvc.perform(post("/lights/light-leave-room/toggle"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.state").value("OFF"));
        }
    }
}
