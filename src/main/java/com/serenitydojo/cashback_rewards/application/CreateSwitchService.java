package com.serenitydojo.cashback_rewards.application;

import com.serenitydojo.cashback_rewards.domain.model.Switch;
import com.serenitydojo.cashback_rewards.domain.port.in.CreateSwitchUseCase;
import com.serenitydojo.cashback_rewards.domain.port.out.SaveSwitchPort;
import org.springframework.stereotype.Service;

@Service
public class CreateSwitchService implements CreateSwitchUseCase {

    private final SaveSwitchPort saveSwitchPort;

    public CreateSwitchService(SaveSwitchPort saveSwitchPort) {
        this.saveSwitchPort = saveSwitchPort;
    }

    @Override
    public void createSwitch(String id, String groupId) {
        saveSwitchPort.saveSwitch(new Switch(id, groupId));
    }
}
