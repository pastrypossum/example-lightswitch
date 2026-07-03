package com.serenitydojo.cashback_rewards.domain.model;

import java.util.List;

public class Group {

    private final String id;
    private final List<String> lightIds;

    public Group(String id, List<String> lightIds) {
        this.id = id;
        this.lightIds = List.copyOf(lightIds);
    }

    public String getId() {
        return id;
    }

    public List<String> getLightIds() {
        return lightIds;
    }

    public boolean hasNoLights() {
        return lightIds.isEmpty();
    }

    public boolean exceedsCapacity() {
        return lightIds.size() > 10;
    }
}
