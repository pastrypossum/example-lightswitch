package com.serenitydojo.cashback_rewards.adapter.out.persistence;

import com.serenitydojo.cashback_rewards.domain.model.LightState;
import com.serenitydojo.cashback_rewards.domain.model.Switch;
import com.serenitydojo.cashback_rewards.domain.port.out.LoadSwitchPort;
import com.serenitydojo.cashback_rewards.domain.port.out.LoadSwitchesForGroupPort;
import com.serenitydojo.cashback_rewards.domain.port.out.SaveSwitchPort;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class SwitchPersistenceAdapter implements LoadSwitchPort, SaveSwitchPort, LoadSwitchesForGroupPort {

    private final SwitchJpaRepository repository;

    public SwitchPersistenceAdapter(SwitchJpaRepository repository) {
        this.repository = repository;
    }

    @Override
    public Optional<Switch> loadSwitch(String id) {
        return repository.findById(id)
                .map(entity -> new Switch(entity.getId(), entity.getGroupId(),
                        entity.getState() != null ? entity.getState() : LightState.OFF));
    }

    @Override
    public void saveSwitch(Switch lightSwitch) {
        repository.save(new SwitchJpaEntity(lightSwitch.getId(), lightSwitch.getGroupId(), lightSwitch.getState()));
    }

    @Override
    public List<Switch> loadSwitchesForGroup(String groupId) {
        return repository.findByGroupId(groupId).stream()
                .map(entity -> new Switch(entity.getId(), entity.getGroupId(),
                        entity.getState() != null ? entity.getState() : LightState.OFF))
                .toList();
    }
}
