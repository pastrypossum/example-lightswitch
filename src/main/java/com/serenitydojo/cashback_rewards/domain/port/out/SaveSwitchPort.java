package com.serenitydojo.cashback_rewards.domain.port.out;

import com.serenitydojo.cashback_rewards.domain.model.Switch;

public interface SaveSwitchPort {
    void saveSwitch(Switch lightSwitch);
}
