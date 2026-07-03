package com.serenitydojo.cashback_rewards.domain.port.out;

import com.serenitydojo.cashback_rewards.domain.model.Light;

import java.util.Optional;

public interface LoadLightPort {
    Optional<Light> loadLight(String id);
}
