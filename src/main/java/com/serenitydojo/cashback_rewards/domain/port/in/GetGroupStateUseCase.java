package com.serenitydojo.cashback_rewards.domain.port.in;

import com.serenitydojo.cashback_rewards.domain.model.GroupState;

public interface GetGroupStateUseCase {
    GroupState getGroupState(String groupId);
}
