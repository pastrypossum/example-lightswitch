package com.serenitydojo.cashback_rewards.domain.model;

public class LightAlreadyAssignedException extends RuntimeException {

    public LightAlreadyAssignedException(String lightId) {
        super("light " + lightId + " already belongs to a group");
    }
}
