package com.serenitydojo.cashback_rewards.domain.port.in;

import com.serenitydojo.cashback_rewards.domain.model.ToggleResult;

public interface ToggleSwitchUseCase {
    ToggleResult toggleSwitch(String switchId);
}
