package com.serenitydojo.cashback_rewards.unit_test;

import com.serenitydojo.cashback_rewards.application.RegisterLightService;
import com.serenitydojo.cashback_rewards.application.ToggleLightService;
import com.serenitydojo.cashback_rewards.domain.model.Light;
import com.serenitydojo.cashback_rewards.domain.model.LightState;
import com.serenitydojo.cashback_rewards.domain.port.out.LoadLightPort;
import com.serenitydojo.cashback_rewards.domain.port.out.SaveLightPort;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("Light application services")
class LightServiceTest {

    @Mock
    LoadLightPort loadLightPort;

    @Mock
    SaveLightPort saveLightPort;

    @Test
    @DisplayName("RegisterLightService saves a new light with OFF state")
    void registerLightSavesNewLight() {
        RegisterLightService service = new RegisterLightService(saveLightPort);

        service.registerLight("light-1");

        ArgumentCaptor<Light> captor = ArgumentCaptor.forClass(Light.class);
        verify(saveLightPort).saveLight(captor.capture());
        Light saved = captor.getValue();
        assertThat(saved.getId()).isEqualTo("light-1");
        assertThat(saved.getState()).isEqualTo(LightState.OFF);
    }

    @Test
    @DisplayName("ToggleLightService toggles light from OFF to ON and returns ON")
    void toggleLightOffToOn() {
        when(loadLightPort.loadLight("light-1")).thenReturn(Optional.of(new Light("light-1", LightState.OFF)));
        ToggleLightService service = new ToggleLightService(loadLightPort, saveLightPort);

        LightState result = service.toggleLight("light-1");

        assertThat(result).isEqualTo(LightState.ON);
        verify(saveLightPort).saveLight(any());
    }

    @Test
    @DisplayName("ToggleLightService returns NOT_REGISTERED when light not found")
    void toggleUnregisteredLightReturnsNotRegistered() {
        when(loadLightPort.loadLight("unknown")).thenReturn(Optional.empty());
        ToggleLightService service = new ToggleLightService(loadLightPort, saveLightPort);

        LightState result = service.toggleLight("unknown");

        assertThat(result).isEqualTo(LightState.NOT_REGISTERED);
    }
}
