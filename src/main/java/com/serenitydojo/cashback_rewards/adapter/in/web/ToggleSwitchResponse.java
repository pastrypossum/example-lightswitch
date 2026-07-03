package com.serenitydojo.cashback_rewards.adapter.in.web;

import java.util.List;

public record ToggleSwitchResponse(
        String switchId,
        String switchState,
        String groupId,
        List<LightResponse> lights) {

    public record LightResponse(String id, String state) {
    }
}
