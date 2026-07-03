package com.serenitydojo.cashback_rewards.domain.port.in;

import com.serenitydojo.cashback_rewards.domain.model.LightState;

public interface ToggleLightUseCase {
    LightState toggleLight(String id);
}
