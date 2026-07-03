package com.serenitydojo.cashback_rewards.domain.port.out;

import com.serenitydojo.cashback_rewards.domain.model.Switch;

import java.util.Optional;

public interface LoadSwitchPort {
    Optional<Switch> loadSwitch(String id);
}
