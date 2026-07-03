package com.serenitydojo.cashback_rewards.adapter.in.web;

import com.serenitydojo.cashback_rewards.domain.model.GroupState;
import com.serenitydojo.cashback_rewards.domain.port.in.CreateGroupUseCase;
import com.serenitydojo.cashback_rewards.domain.port.in.GetGroupStateUseCase;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/groups")
public class GroupController {

    private final CreateGroupUseCase createGroupUseCase;
    private final GetGroupStateUseCase getGroupStateUseCase;

    public GroupController(CreateGroupUseCase createGroupUseCase,
                           GetGroupStateUseCase getGroupStateUseCase) {
        this.createGroupUseCase = createGroupUseCase;
        this.getGroupStateUseCase = getGroupStateUseCase;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createGroup(@RequestBody CreateGroupRequest request) {
        createGroupUseCase.createGroup(request.id(), request.lightIds());
    }

    @GetMapping("/{id}")
    public GroupStateResponse getGroupState(@PathVariable String id) {
        GroupState state = getGroupStateUseCase.getGroupState(id);
        return new GroupStateResponse(state.groupId(), state.state().name());
    }
}
