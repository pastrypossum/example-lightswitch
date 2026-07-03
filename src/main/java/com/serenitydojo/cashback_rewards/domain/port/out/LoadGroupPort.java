package com.serenitydojo.cashback_rewards.domain.port.out;

import com.serenitydojo.cashback_rewards.domain.model.Group;

import java.util.Optional;

public interface LoadGroupPort {
    Optional<Group> loadGroup(String id);
}
