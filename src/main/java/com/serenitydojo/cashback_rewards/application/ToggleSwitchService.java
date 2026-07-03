package com.serenitydojo.cashback_rewards.application;

import com.serenitydojo.cashback_rewards.domain.model.CapacityExceededException;
import com.serenitydojo.cashback_rewards.domain.model.Group;
import com.serenitydojo.cashback_rewards.domain.model.Light;
import com.serenitydojo.cashback_rewards.domain.model.LightState;
import com.serenitydojo.cashback_rewards.domain.model.NoLightsConfiguredException;
import com.serenitydojo.cashback_rewards.domain.model.Switch;
import com.serenitydojo.cashback_rewards.domain.model.ToggleResult;
import com.serenitydojo.cashback_rewards.domain.model.ToggleState;
import com.serenitydojo.cashback_rewards.domain.port.in.ToggleSwitchUseCase;
import com.serenitydojo.cashback_rewards.domain.port.out.LoadGroupPort;
import com.serenitydojo.cashback_rewards.domain.port.out.LoadLightPort;
import com.serenitydojo.cashback_rewards.domain.port.out.LoadSwitchPort;
import com.serenitydojo.cashback_rewards.domain.port.out.SaveLightPort;
import com.serenitydojo.cashback_rewards.domain.port.out.SaveSwitchPort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ToggleSwitchService implements ToggleSwitchUseCase {

    private final LoadSwitchPort loadSwitchPort;
    private final LoadGroupPort loadGroupPort;
    private final LoadLightPort loadLightPort;
    private final SaveLightPort saveLightPort;
    private final SaveSwitchPort saveSwitchPort;

    public ToggleSwitchService(LoadSwitchPort loadSwitchPort,
                               LoadGroupPort loadGroupPort,
                               LoadLightPort loadLightPort,
                               SaveLightPort saveLightPort,
                               SaveSwitchPort saveSwitchPort) {
        this.loadSwitchPort = loadSwitchPort;
        this.loadGroupPort = loadGroupPort;
        this.loadLightPort = loadLightPort;
        this.saveLightPort = saveLightPort;
        this.saveSwitchPort = saveSwitchPort;
    }

    @Override
    public ToggleResult toggleSwitch(String switchId) {
        Switch lightSwitch = loadSwitchPort.loadSwitch(switchId)
                .orElseThrow(() -> new IllegalArgumentException("switch not found: " + switchId));

        Group group = loadGroupPort.loadGroup(lightSwitch.getGroupId())
                .orElseThrow(() -> new IllegalArgumentException("group not found: " + lightSwitch.getGroupId()));

        if (group.hasNoLights()) {
            throw new NoLightsConfiguredException();
        }

        if (group.exceedsCapacity()) {
            throw new CapacityExceededException();
        }

        lightSwitch.toggle();
        saveSwitchPort.saveSwitch(lightSwitch);

        LightState targetState = lightSwitch.getState();
        List<Light> toggledLights = group.getLightIds().stream()
                .map(id -> loadLightPort.loadLight(id)
                        .orElseThrow(() -> new IllegalArgumentException("light not found: " + id)))
                .map(light -> { light.setState(targetState); return light; })
                .toList();
        toggledLights.forEach(saveLightPort::saveLight);

        return targetState == LightState.ON
                ? ToggleResult.on(switchId, group.getId(), toggledLights)
                : ToggleResult.off(switchId, group.getId(), toggledLights);
    }
}
