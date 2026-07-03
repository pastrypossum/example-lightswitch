package com.serenitydojo.cashback_rewards.domain.model;

public class NoLightsConfiguredException extends RuntimeException {

    public NoLightsConfiguredException() {
        super("no lights configured");
    }
}
