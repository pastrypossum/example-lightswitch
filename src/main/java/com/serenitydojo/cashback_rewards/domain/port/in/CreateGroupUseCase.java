package com.serenitydojo.cashback_rewards.domain.port.in;

import java.util.List;

public interface CreateGroupUseCase {
    void createGroup(String id, List<String> lightIds);
}
