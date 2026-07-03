package com.serenitydojo.cashback_rewards.domain.port.out;

import com.serenitydojo.cashback_rewards.domain.model.Group;

public interface SaveGroupPort {
    void saveGroup(Group group);
}
