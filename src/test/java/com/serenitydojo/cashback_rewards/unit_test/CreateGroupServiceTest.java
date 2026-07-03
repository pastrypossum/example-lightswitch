package com.serenitydojo.cashback_rewards.unit_test;

import com.serenitydojo.cashback_rewards.application.CreateGroupService;
import com.serenitydojo.cashback_rewards.domain.model.Group;
import com.serenitydojo.cashback_rewards.domain.model.LightAlreadyAssignedException;
import com.serenitydojo.cashback_rewards.domain.port.out.LoadGroupByLightIdPort;
import com.serenitydojo.cashback_rewards.domain.port.out.SaveGroupPort;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("CreateGroupService")
class CreateGroupServiceTest {

    @Mock
    SaveGroupPort saveGroupPort;

    @Mock
    LoadGroupByLightIdPort loadGroupByLightIdPort;

    @Nested
    @DisplayName("Rule 5: A light may belong to at most one group")
    class ALightMayBelongToAtMostOneGroup {

        @Test
        @DisplayName("throws LightAlreadyAssignedException when a light already belongs to a different group")
        void throwsWhenLightAlreadyBelongsToDifferentGroup() {
            when(loadGroupByLightIdPort.findGroupByLightId("light-1"))
                    .thenReturn(Optional.of(new Group("existing-group", List.of("light-1"))));

            var service = new CreateGroupService(saveGroupPort, loadGroupByLightIdPort);

            assertThatThrownBy(() -> service.createGroup("new-group", List.of("light-1")))
                    .isInstanceOf(LightAlreadyAssignedException.class);
        }

        @Test
        @DisplayName("does not throw when the light belongs to the same group being updated")
        void doesNotThrowWhenLightBelongsToSameGroup() {
            when(loadGroupByLightIdPort.findGroupByLightId("light-1"))
                    .thenReturn(Optional.of(new Group("my-group", List.of("light-1"))));

            var service = new CreateGroupService(saveGroupPort, loadGroupByLightIdPort);

            assertThatCode(() -> service.createGroup("my-group", List.of("light-1")))
                    .doesNotThrowAnyException();
        }

        @Test
        @DisplayName("does not throw when the light does not belong to any group")
        void doesNotThrowWhenLightHasNoGroup() {
            when(loadGroupByLightIdPort.findGroupByLightId("light-1"))
                    .thenReturn(Optional.empty());

            var service = new CreateGroupService(saveGroupPort, loadGroupByLightIdPort);

            assertThatCode(() -> service.createGroup("new-group", List.of("light-1")))
                    .doesNotThrowAnyException();
        }
    }
}
