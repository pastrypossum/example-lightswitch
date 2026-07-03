package com.serenitydojo.cashback_rewards.domain.model;

public class GroupNotFoundException extends RuntimeException {

    public GroupNotFoundException(String groupId) {
        super("group not found: " + groupId);
    }
}
