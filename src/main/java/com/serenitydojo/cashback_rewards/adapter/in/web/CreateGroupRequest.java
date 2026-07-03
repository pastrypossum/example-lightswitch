package com.serenitydojo.cashback_rewards.adapter.in.web;

import java.util.List;

public record CreateGroupRequest(String id, List<String> lightIds) {
}
