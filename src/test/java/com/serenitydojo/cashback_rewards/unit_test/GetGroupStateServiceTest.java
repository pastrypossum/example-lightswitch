package com.serenitydojo.cashback_rewards.unit_test;

import com.serenitydojo.cashback_rewards.application.GetGroupStateService;
import com.serenitydojo.cashback_rewards.domain.model.Group;
import com.serenitydojo.cashback_rewards.domain.model.GroupNotFoundException;
import com.serenitydojo.cashback_rewards.domain.model.GroupState;
import com.serenitydojo.cashback_rewards.domain.model.LightState;
import com.serenitydojo.cashback_rewards.domain.model.Switch;
import com.serenitydojo.cashback_rewards.domain.port.out.LoadGroupPort;
import com.serenitydojo.cashback_rewards.domain.port.out.LoadSwitchesForGroupPort;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("GetGroupStateService")
class GetGroupStateServiceTest {

    @Mock
    LoadGroupPort loadGroupPort;

    @Mock
    LoadSwitchesForGroupPort loadSwitchesForGroupPort;

    private GetGroupStateService service() {
        return new GetGroupStateService(loadGroupPort, loadSwitchesForGroupPort);
    }

    private void groupExists(String groupId) {
        when(loadGroupPort.loadGroup(groupId)).thenReturn(Optional.of(new Group(groupId, List.of())));
    }

    @Test
    @DisplayName("group is OFF when all switches controlling it are OFF")
    void groupIsOffWhenAllSwitchesAreOff() {
        groupExists("g1");
        when(loadSwitchesForGroupPort.loadSwitchesForGroup("g1"))
                .thenReturn(List.of(
                        new Switch("sw1", "g1", LightState.OFF),
                        new Switch("sw2", "g1", LightState.OFF)
                ));

        GroupState result = service().getGroupState("g1");

        assertThat(result.groupId()).isEqualTo("g1");
        assertThat(result.state()).isEqualTo(LightState.OFF);
    }

    @Test
    @DisplayName("group is ON when at least one switch controlling it is ON")
    void groupIsOnWhenAtLeastOneSwitchIsOn() {
        groupExists("g1");
        when(loadSwitchesForGroupPort.loadSwitchesForGroup("g1"))
                .thenReturn(List.of(
                        new Switch("sw1", "g1", LightState.ON),
                        new Switch("sw2", "g1", LightState.OFF)
                ));

        GroupState result = service().getGroupState("g1");

        assertThat(result.groupId()).isEqualTo("g1");
        assertThat(result.state()).isEqualTo(LightState.ON);
    }

    @Test
    @DisplayName("group is OFF when no switches control it")
    void groupIsOffWhenNoSwitchesExist() {
        groupExists("g1");
        when(loadSwitchesForGroupPort.loadSwitchesForGroup("g1")).thenReturn(List.of());

        GroupState result = service().getGroupState("g1");

        assertThat(result.state()).isEqualTo(LightState.OFF);
    }

    @Test
    @DisplayName("throws GroupNotFoundException when group does not exist")
    void throwsWhenGroupDoesNotExist() {
        when(loadGroupPort.loadGroup("unknown")).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service().getGroupState("unknown"))
                .isInstanceOf(GroupNotFoundException.class)
                .hasMessageContaining("unknown");
    }
}
