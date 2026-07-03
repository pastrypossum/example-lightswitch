package com.serenitydojo.cashback_rewards.adapter.in.web;

import com.serenitydojo.cashback_rewards.domain.model.ToggleResult;
import com.serenitydojo.cashback_rewards.domain.port.in.CreateSwitchUseCase;
import com.serenitydojo.cashback_rewards.domain.port.in.ToggleSwitchUseCase;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/switches")
public class SwitchController {

    private final CreateSwitchUseCase createSwitchUseCase;
    private final ToggleSwitchUseCase toggleSwitchUseCase;

    public SwitchController(CreateSwitchUseCase createSwitchUseCase,
                            ToggleSwitchUseCase toggleSwitchUseCase) {
        this.createSwitchUseCase = createSwitchUseCase;
        this.toggleSwitchUseCase = toggleSwitchUseCase;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createSwitch(@RequestBody CreateSwitchRequest request) {
        createSwitchUseCase.createSwitch(request.id(), request.groupId());
    }

    @PostMapping("/{id}/toggle")
    @ResponseStatus(HttpStatus.OK)
    public ToggleSwitchResponse toggleSwitch(@PathVariable String id) {
        ToggleResult result = toggleSwitchUseCase.toggleSwitch(id);
        return new ToggleSwitchResponse(
                result.getSwitchId(),
                result.getState().name(),
                result.getGroupId(),
                result.getLights().stream()
                        .map(l -> new ToggleSwitchResponse.LightResponse(l.getId(), l.getState().name()))
                        .toList()
        );
    }
}
