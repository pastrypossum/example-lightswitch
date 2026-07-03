package com.serenitydojo.cashback_rewards.adapter.out.persistence;

import com.serenitydojo.cashback_rewards.domain.model.LightState;
import jakarta.persistence.*;

@Entity
@Table(name = "switches")
public class SwitchJpaEntity {

    @Id
    private String id;

    @Column(name = "group_id")
    private String groupId;

    @Enumerated(EnumType.STRING)
    @Column(name = "state")
    private LightState state;

    protected SwitchJpaEntity() {
    }

    public SwitchJpaEntity(String id, String groupId, LightState state) {
        this.id = id;
        this.groupId = groupId;
        this.state = state;
    }

    public String getId() {
        return id;
    }

    public String getGroupId() {
        return groupId;
    }

    public LightState getState() {
        return state;
    }
}
