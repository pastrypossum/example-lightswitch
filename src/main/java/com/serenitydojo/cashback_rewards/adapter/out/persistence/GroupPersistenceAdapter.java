package com.serenitydojo.cashback_rewards.adapter.out.persistence;

import com.serenitydojo.cashback_rewards.domain.model.Group;
import com.serenitydojo.cashback_rewards.domain.port.out.LoadGroupByLightIdPort;
import com.serenitydojo.cashback_rewards.domain.port.out.LoadGroupPort;
import com.serenitydojo.cashback_rewards.domain.port.out.SaveGroupPort;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class GroupPersistenceAdapter implements LoadGroupPort, SaveGroupPort, LoadGroupByLightIdPort {

    private final GroupJpaRepository repository;

    public GroupPersistenceAdapter(GroupJpaRepository repository) {
        this.repository = repository;
    }

    @Override
    public Optional<Group> loadGroup(String id) {
        return repository.findById(id)
                .map(entity -> new Group(entity.getId(), entity.getLightIds()));
    }

    @Override
    public void saveGroup(Group group) {
        repository.save(new GroupJpaEntity(group.getId(), group.getLightIds()));
    }

    @Override
    public Optional<Group> findGroupByLightId(String lightId) {
        return repository.findByLightIdsContaining(lightId)
                .map(entity -> new Group(entity.getId(), entity.getLightIds()));
    }
}
