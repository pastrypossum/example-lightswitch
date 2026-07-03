package com.serenitydojo.cashback_rewards.adapter.in.web;

import com.serenitydojo.cashback_rewards.domain.model.LightState;
import com.serenitydojo.cashback_rewards.domain.port.in.RegisterLightUseCase;
import com.serenitydojo.cashback_rewards.domain.port.in.ToggleLightUseCase;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/lights")
public class LightController {

    private final RegisterLightUseCase registerLightUseCase;
    private final ToggleLightUseCase toggleLightUseCase;

    public LightController(RegisterLightUseCase registerLightUseCase,
                           ToggleLightUseCase toggleLightUseCase) {
        this.registerLightUseCase = registerLightUseCase;
        this.toggleLightUseCase = toggleLightUseCase;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void registerLight(@RequestBody RegisterLightRequest request) {
        registerLightUseCase.registerLight(request.id());
    }

    @PostMapping("/{id}/toggle")
    public ToggleLightResponse toggleLight(@PathVariable String id) {
        LightState state = toggleLightUseCase.toggleLight(id);
        return new ToggleLightResponse(state.name());
    }
}
