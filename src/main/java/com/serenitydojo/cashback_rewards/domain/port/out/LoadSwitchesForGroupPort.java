package com.serenitydojo.cashback_rewards.domain.port.out;

import com.serenitydojo.cashback_rewards.domain.model.Switch;

import java.util.List;

public interface LoadSwitchesForGroupPort {
    List<Switch> loadSwitchesForGroup(String groupId);
}
