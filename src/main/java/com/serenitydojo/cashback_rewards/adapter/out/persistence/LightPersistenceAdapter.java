package com.serenitydojo.cashback_rewards.adapter.out.persistence;

import com.serenitydojo.cashback_rewards.domain.model.Light;
import com.serenitydojo.cashback_rewards.domain.port.out.LoadLightPort;
import com.serenitydojo.cashback_rewards.domain.port.out.SaveLightPort;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class LightPersistenceAdapter implements LoadLightPort, SaveLightPort {

    private final LightJpaRepository repository;

    public LightPersistenceAdapter(LightJpaRepository repository) {
        this.repository = repository;
    }

    @Override
    public Optional<Light> loadLight(String id) {
        return repository.findById(id)
                .map(entity -> new Light(entity.getId(), entity.getState()));
    }

    @Override
    public void saveLight(Light light) {
        repository.save(new LightJpaEntity(light.getId(), light.getState()));
    }
}
