package com.serenitydojo.cashback_rewards.application;

import com.serenitydojo.cashback_rewards.domain.model.Light;
import com.serenitydojo.cashback_rewards.domain.model.LightState;
import com.serenitydojo.cashback_rewards.domain.port.in.GetLightStateUseCase;
import com.serenitydojo.cashback_rewards.domain.port.in.ToggleLightUseCase;
import com.serenitydojo.cashback_rewards.domain.port.out.LoadLightPort;
import com.serenitydojo.cashback_rewards.domain.port.out.SaveLightPort;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ToggleLightService implements ToggleLightUseCase, GetLightStateUseCase {

    private final LoadLightPort loadLightPort;
    private final SaveLightPort saveLightPort;

    public ToggleLightService(LoadLightPort loadLightPort, SaveLightPort saveLightPort) {
        this.loadLightPort = loadLightPort;
        this.saveLightPort = saveLightPort;
    }

    @Override
    public LightState toggleLight(String id) {
        Optional<Light> maybeLight = loadLightPort.loadLight(id);
        if (maybeLight.isEmpty()) {
            return LightState.NOT_REGISTERED;
        }
        Light light = maybeLight.get();
        light.toggle();
        saveLightPort.saveLight(light);
        return light.getState();
    }

    @Override
    public LightState getLightState(String id) {
        return loadLightPort.loadLight(id)
                .map(Light::getState)
                .orElse(LightState.NOT_REGISTERED);
    }
}
