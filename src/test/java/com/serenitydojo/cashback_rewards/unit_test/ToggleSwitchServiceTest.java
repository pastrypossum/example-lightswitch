package com.serenitydojo.cashback_rewards.unit_test;

import com.serenitydojo.cashback_rewards.application.ToggleSwitchService;
import com.serenitydojo.cashback_rewards.domain.model.CapacityExceededException;
import com.serenitydojo.cashback_rewards.domain.model.Group;
import com.serenitydojo.cashback_rewards.domain.model.Light;
import com.serenitydojo.cashback_rewards.domain.model.LightState;
import com.serenitydojo.cashback_rewards.domain.model.NoLightsConfiguredException;
import com.serenitydojo.cashback_rewards.domain.model.Switch;
import com.serenitydojo.cashback_rewards.domain.model.ToggleResult;
import com.serenitydojo.cashback_rewards.domain.model.ToggleState;
import com.serenitydojo.cashback_rewards.domain.port.out.LoadGroupPort;
import com.serenitydojo.cashback_rewards.domain.port.out.LoadLightPort;
import com.serenitydojo.cashback_rewards.domain.port.out.LoadSwitchPort;
import com.serenitydojo.cashback_rewards.domain.port.out.SaveLightPort;
import com.serenitydojo.cashback_rewards.domain.port.out.SaveSwitchPort;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("ToggleSwitchService")
class ToggleSwitchServiceTest {

    @Mock
    LoadSwitchPort loadSwitchPort;

    @Mock
    LoadGroupPort loadGroupPort;

    @Mock
    LoadLightPort loadLightPort;

    @Mock
    SaveLightPort saveLightPort;

    @Mock
    SaveSwitchPort saveSwitchPort;

    private ToggleSwitchService service() {
        return new ToggleSwitchService(loadSwitchPort, loadGroupPort, loadLightPort, saveLightPort, saveSwitchPort);
    }

    @Test
    @DisplayName("throws NoLightsConfiguredException when the group has no lights")
    void throwsWhenGroupHasNoLights() {
        when(loadSwitchPort.loadSwitch("sw1"))
                .thenReturn(Optional.of(new Switch("sw1", "g1")));
        when(loadGroupPort.loadGroup("g1"))
                .thenReturn(Optional.of(new Group("g1", List.of())));

        assertThatThrownBy(() -> service().toggleSwitch("sw1"))
                .isInstanceOf(NoLightsConfiguredException.class);
    }

    @Test
    @DisplayName("returns a successful ToggleResult when the group has exactly one light")
    void returnsSuccessWhenGroupHasOneLight() {
        when(loadSwitchPort.loadSwitch("sw1"))
                .thenReturn(Optional.of(new Switch("sw1", "g1")));
        when(loadGroupPort.loadGroup("g1"))
                .thenReturn(Optional.of(new Group("g1", List.of("light-1"))));
        when(loadLightPort.loadLight("light-1"))
                .thenReturn(Optional.of(new Light("light-1")));

        ToggleResult result = service().toggleSwitch("sw1");

        assertThat(result.getState()).isEqualTo(ToggleState.ON);
        assertThat(result.getErrorMessage()).isNull();
    }

    @Test
    @DisplayName("throws CapacityExceededException when the group has more than 10 lights")
    void throwsWhenGroupExceedsCapacity() {
        List<String> elevenLightIds = IntStream.rangeClosed(1, 11)
                .mapToObj(i -> "light-" + i)
                .toList();

        when(loadSwitchPort.loadSwitch("sw1"))
                .thenReturn(Optional.of(new Switch("sw1", "g1")));
        when(loadGroupPort.loadGroup("g1"))
                .thenReturn(Optional.of(new Group("g1", elevenLightIds)));

        assertThatThrownBy(() -> service().toggleSwitch("sw1"))
                .isInstanceOf(CapacityExceededException.class)
                .hasMessage("capacity exceeded");
    }

    @Test
    @DisplayName("does not throw when the group has exactly 10 lights (boundary)")
    void doesNotThrowWhenGroupHasExactly10Lights() {
        List<String> tenLightIds = IntStream.rangeClosed(1, 10)
                .mapToObj(i -> "light-" + i)
                .toList();

        when(loadSwitchPort.loadSwitch("sw1"))
                .thenReturn(Optional.of(new Switch("sw1", "g1")));
        when(loadGroupPort.loadGroup("g1"))
                .thenReturn(Optional.of(new Group("g1", tenLightIds)));
        tenLightIds.forEach(id ->
                when(loadLightPort.loadLight(id)).thenReturn(Optional.of(new Light(id))));

        ToggleResult result = service().toggleSwitch("sw1");

        assertThat(result.getState()).isEqualTo(ToggleState.ON);
        assertThat(result.getLights()).hasSize(10);
    }

    @Test
    @DisplayName("drives all lights to ON when a switch is toggled from OFF")
    void drivesAllLightsToNewSwitchState() {
        when(loadSwitchPort.loadSwitch("sw1"))
                .thenReturn(Optional.of(new Switch("sw1", "g1", LightState.OFF)));
        when(loadGroupPort.loadGroup("g1"))
                .thenReturn(Optional.of(new Group("g1", List.of("light-1", "light-2"))));
        when(loadLightPort.loadLight("light-1"))
                .thenReturn(Optional.of(new Light("light-1", LightState.OFF)));
        when(loadLightPort.loadLight("light-2"))
                .thenReturn(Optional.of(new Light("light-2", LightState.OFF)));

        ToggleResult result = service().toggleSwitch("sw1");

        assertThat(result.getState()).isEqualTo(ToggleState.ON);
        assertThat(result.getLights()).extracting(Light::getState)
                .containsOnly(LightState.ON);
    }

    @Test
    @DisplayName("does not save any lights when capacity is exceeded")
    void doesNotSaveLightsWhenCapacityExceeded() {
        List<String> elevenLightIds = IntStream.rangeClosed(1, 11)
                .mapToObj(i -> "light-" + i)
                .toList();

        when(loadSwitchPort.loadSwitch("sw1"))
                .thenReturn(Optional.of(new Switch("sw1", "g1")));
        when(loadGroupPort.loadGroup("g1"))
                .thenReturn(Optional.of(new Group("g1", elevenLightIds)));

        assertThatThrownBy(() -> service().toggleSwitch("sw1"))
                .isInstanceOf(CapacityExceededException.class);

        verify(saveLightPort, never()).saveLight(any());
        verify(saveSwitchPort, never()).saveSwitch(any());
    }
}
