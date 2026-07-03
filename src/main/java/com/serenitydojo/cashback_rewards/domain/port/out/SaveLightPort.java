package com.serenitydojo.cashback_rewards.domain.port.out;

import com.serenitydojo.cashback_rewards.domain.model.Light;

public interface SaveLightPort {
    void saveLight(Light light);
}
