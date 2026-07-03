package com.serenitydojo.cashback_rewards.domain.model;

public class Light {

    private final String id;
    private LightState state;

    public Light(String id) {
        this.id = id;
        this.state = LightState.OFF;
    }

    public Light(String id, LightState state) {
        this.id = id;
        this.state = state;
    }

    public String getId() {
        return id;
    }

    public LightState getState() {
        return state;
    }

    public void toggle() {
        state = (state == LightState.OFF) ? LightState.ON : LightState.OFF;
    }
}
