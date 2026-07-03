package com.serenitydojo.cashback_rewards.domain.port.in;

import com.serenitydojo.cashback_rewards.domain.model.LightState;

public interface GetLightStateUseCase {
    LightState getLightState(String id);
}
