package com.serenitydojo.cashback_rewards.adapter.out.persistence;

import com.serenitydojo.cashback_rewards.domain.model.LightState;
import jakarta.persistence.*;

@Entity
@Table(name = "lights")
public class LightJpaEntity {

    @Id
    private String id;

    @Enumerated(EnumType.STRING)
    private LightState state;

    protected LightJpaEntity() {
    }

    public LightJpaEntity(String id, LightState state) {
        this.id = id;
        this.state = state;
    }

    public String getId() {
        return id;
    }

    public LightState getState() {
        return state;
    }
}
