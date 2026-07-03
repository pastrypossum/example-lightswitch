package com.serenitydojo.cashback_rewards.domain.model;

import java.util.List;

public class ToggleResult {

    private final ToggleState state;
    private final String errorMessage;
    private final String switchId;
    private final String groupId;
    private final List<Light> lights;

    private ToggleResult(ToggleState state, String errorMessage,
                         String switchId, String groupId, List<Light> lights) {
        this.state = state;
        this.errorMessage = errorMessage;
        this.switchId = switchId;
        this.groupId = groupId;
        this.lights = lights;
    }

    public static ToggleResult on(String switchId, String groupId, List<Light> lights) {
        return new ToggleResult(ToggleState.ON, null, switchId, groupId, lights);
    }

    public static ToggleResult off(String switchId, String groupId, List<Light> lights) {
        return new ToggleResult(ToggleState.OFF, null, switchId, groupId, lights);
    }

    public static ToggleResult error(String message) {
        return new ToggleResult(ToggleState.ERROR, message, null, null, null);
    }

    public ToggleState getState() {
        return state;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public String getSwitchId() {
        return switchId;
    }

    public String getGroupId() {
        return groupId;
    }

    public List<Light> getLights() {
        return lights;
    }
}
