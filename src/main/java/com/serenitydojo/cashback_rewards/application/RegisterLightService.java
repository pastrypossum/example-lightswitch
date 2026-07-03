package com.serenitydojo.cashback_rewards.application;

import com.serenitydojo.cashback_rewards.domain.model.Light;
import com.serenitydojo.cashback_rewards.domain.port.in.RegisterLightUseCase;
import com.serenitydojo.cashback_rewards.domain.port.out.SaveLightPort;
import org.springframework.stereotype.Service;

@Service
public class RegisterLightService implements RegisterLightUseCase {

    private final SaveLightPort saveLightPort;

    public RegisterLightService(SaveLightPort saveLightPort) {
        this.saveLightPort = saveLightPort;
    }

    @Override
    public void registerLight(String id) {
        saveLightPort.saveLight(new Light(id));
    }
}
