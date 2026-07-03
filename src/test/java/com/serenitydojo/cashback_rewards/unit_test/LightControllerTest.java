package com.serenitydojo.cashback_rewards.unit_test;

import com.serenitydojo.cashback_rewards.adapter.in.web.LightController;
import com.serenitydojo.cashback_rewards.domain.model.LightState;
import com.serenitydojo.cashback_rewards.domain.port.in.GetLightStateUseCase;
import com.serenitydojo.cashback_rewards.domain.port.in.RegisterLightUseCase;
import com.serenitydojo.cashback_rewards.domain.port.in.ToggleLightUseCase;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(LightController.class)
@DisplayName("Light controller")
class LightControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockitoBean
    RegisterLightUseCase registerLightUseCase;

    @MockitoBean
    ToggleLightUseCase toggleLightUseCase;

    @MockitoBean
    GetLightStateUseCase getLightStateUseCase;

    @Test
    @DisplayName("POST /lights returns 201 Created")
    void registerLightReturns201() throws Exception {
        mockMvc.perform(post("/lights")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                { "id": "light-1" }
                                """))
                .andExpect(status().isCreated());

        verify(registerLightUseCase).registerLight("light-1");
    }

    @Test
    @DisplayName("POST /lights/{id}/toggle returns 200 with state ON")
    void toggleLightReturnsStateOn() throws Exception {
        when(toggleLightUseCase.toggleLight("light-1")).thenReturn(LightState.ON);

        mockMvc.perform(post("/lights/light-1/toggle"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.state").value("ON"));
    }

    @Test
    @DisplayName("GET /lights/{id} returns 200 with current state")
    void getLightStateReturnsCurrentState() throws Exception {
        when(getLightStateUseCase.getLightState("light-1")).thenReturn(LightState.OFF);

        mockMvc.perform(get("/lights/light-1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.state").value("OFF"));
    }
}
