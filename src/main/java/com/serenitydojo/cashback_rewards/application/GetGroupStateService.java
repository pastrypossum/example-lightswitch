package com.serenitydojo.cashback_rewards.application;

import com.serenitydojo.cashback_rewards.domain.model.GroupNotFoundException;
import com.serenitydojo.cashback_rewards.domain.model.GroupState;
import com.serenitydojo.cashback_rewards.domain.model.LightState;
import com.serenitydojo.cashback_rewards.domain.model.Switch;
import com.serenitydojo.cashback_rewards.domain.port.in.GetGroupStateUseCase;
import com.serenitydojo.cashback_rewards.domain.port.out.LoadGroupPort;
import com.serenitydojo.cashback_rewards.domain.port.out.LoadSwitchesForGroupPort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GetGroupStateService implements GetGroupStateUseCase {

    private final LoadGroupPort loadGroupPort;
    private final LoadSwitchesForGroupPort loadSwitchesForGroupPort;

    public GetGroupStateService(LoadGroupPort loadGroupPort, LoadSwitchesForGroupPort loadSwitchesForGroupPort) {
        this.loadGroupPort = loadGroupPort;
        this.loadSwitchesForGroupPort = loadSwitchesForGroupPort;
    }

    @Override
    public GroupState getGroupState(String groupId) {
        loadGroupPort.loadGroup(groupId)
                .orElseThrow(() -> new GroupNotFoundException(groupId));
        List<Switch> switches = loadSwitchesForGroupPort.loadSwitchesForGroup(groupId);
        boolean anyOn = switches.stream().anyMatch(s -> s.getState() == LightState.ON);
        return new GroupState(groupId, anyOn ? LightState.ON : LightState.OFF);
    }
}
