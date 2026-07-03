package com.serenitydojo.cashback_rewards.unit_test;

import com.serenitydojo.cashback_rewards.domain.model.Group;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Group domain model")
class GroupTest {

    @Test
    @DisplayName("hasNoLights() returns true for an empty list")
    void hasNoLightsReturnsTrueForEmptyList() {
        Group group = new Group("g1", List.of());

        assertThat(group.hasNoLights()).isTrue();
    }

    @Test
    @DisplayName("hasNoLights() returns false for a list with one ID")
    void hasNoLightsReturnsFalseForListWithOneId() {
        Group group = new Group("g1", List.of("light-1"));

        assertThat(group.hasNoLights()).isFalse();
    }

    @Test
    @DisplayName("exceedsCapacity() returns false for a group with exactly 10 lights")
    void exceedsCapacityReturnsFalseForExactly10Lights() {
        Group group = new Group("g1", List.of(
                "l1", "l2", "l3", "l4", "l5",
                "l6", "l7", "l8", "l9", "l10"));

        assertThat(group.exceedsCapacity()).isFalse();
    }

    @Test
    @DisplayName("exceedsCapacity() returns true for a group with 11 lights")
    void exceedsCapacityReturnsTrueFor11Lights() {
        Group group = new Group("g1", List.of(
                "l1", "l2", "l3", "l4", "l5",
                "l6", "l7", "l8", "l9", "l10", "l11"));

        assertThat(group.exceedsCapacity()).isTrue();
    }
}
