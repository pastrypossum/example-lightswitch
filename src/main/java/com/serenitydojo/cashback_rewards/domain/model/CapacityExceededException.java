package com.serenitydojo.cashback_rewards.domain.model;

public class CapacityExceededException extends RuntimeException {

    public CapacityExceededException() {
        super("capacity exceeded");
    }
}
