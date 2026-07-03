package com.serenitydojo.cashback_rewards.domain.model;

public class Switch {

    private final String id;
    private final String groupId;
    private LightState state;

    public Switch(String id, String groupId) {
        this.id = id;
        this.groupId = groupId;
        this.state = LightState.OFF;
    }

    public Switch(String id, String groupId, LightState state) {
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

    public void toggle() {
        state = (state == LightState.OFF) ? LightState.ON : LightState.OFF;
    }
}
