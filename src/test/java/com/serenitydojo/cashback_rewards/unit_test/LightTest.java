package com.serenitydojo.cashback_rewards.unit_test;

import com.serenitydojo.cashback_rewards.domain.model.Light;
import com.serenitydojo.cashback_rewards.domain.model.LightState;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Light domain model")
class LightTest {

    @Test
    @DisplayName("A new light starts in the OFF state")
    void newLightStartsOff() {
        Light light = new Light("light-1");
        assertThat(light.getState()).isEqualTo(LightState.OFF);
    }

    @Test
    @DisplayName("Toggling an OFF light turns it ON")
    void toggleOffLightTurnsOn() {
        Light light = new Light("light-1");
        light.toggle();
        assertThat(light.getState()).isEqualTo(LightState.ON);
    }

    @Test
    @DisplayName("Toggling an ON light turns it OFF")
    void toggleOnLightTurnsOff() {
        Light light = new Light("light-1", LightState.ON);
        light.toggle();
        assertThat(light.getState()).isEqualTo(LightState.OFF);
    }
}
