package com.serenitydojo.cashback_rewards.application;

import com.serenitydojo.cashback_rewards.domain.model.Group;
import com.serenitydojo.cashback_rewards.domain.model.LightAlreadyAssignedException;
import com.serenitydojo.cashback_rewards.domain.port.in.CreateGroupUseCase;
import com.serenitydojo.cashback_rewards.domain.port.out.LoadGroupByLightIdPort;
import com.serenitydojo.cashback_rewards.domain.port.out.SaveGroupPort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CreateGroupService implements CreateGroupUseCase {

    private final SaveGroupPort saveGroupPort;
    private final LoadGroupByLightIdPort loadGroupByLightIdPort;

    public CreateGroupService(SaveGroupPort saveGroupPort, LoadGroupByLightIdPort loadGroupByLightIdPort) {
        this.saveGroupPort = saveGroupPort;
        this.loadGroupByLightIdPort = loadGroupByLightIdPort;
    }

    @Override
    public void createGroup(String id, List<String> lightIds) {
        for (String lightId : lightIds) {
            loadGroupByLightIdPort.findGroupByLightId(lightId)
                    .filter(existingGroup -> !existingGroup.getId().equals(id))
                    .ifPresent(existingGroup -> {
                        throw new LightAlreadyAssignedException(lightId);
                    });
        }
        saveGroupPort.saveGroup(new Group(id, lightIds));
    }
}
