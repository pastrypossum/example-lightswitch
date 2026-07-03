package com.serenitydojo.cashback_rewards.adapter.out.persistence;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "groups")
public class GroupJpaEntity {

    @Id
    private String id;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "group_lights", joinColumns = @JoinColumn(name = "group_id"))
    @Column(name = "light_id")
    private List<String> lightIds = new ArrayList<>();

    protected GroupJpaEntity() {
    }

    public GroupJpaEntity(String id, List<String> lightIds) {
        this.id = id;
        this.lightIds = new ArrayList<>(lightIds);
    }

    public String getId() {
        return id;
    }

    public List<String> getLightIds() {
        return lightIds;
    }
}
