package com.serenitydojo.cashback_rewards.adapter.in.web;

import com.serenitydojo.cashback_rewards.domain.model.LightState;
import com.serenitydojo.cashback_rewards.domain.port.in.GetLightStateUseCase;
import com.serenitydojo.cashback_rewards.domain.port.in.RegisterLightUseCase;
import com.serenitydojo.cashback_rewards.domain.port.in.ToggleLightUseCase;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/lights")
public class LightController {

    private final RegisterLightUseCase registerLightUseCase;
    private final ToggleLightUseCase toggleLightUseCase;
    private final GetLightStateUseCase getLightStateUseCase;

    public LightController(RegisterLightUseCase registerLightUseCase,
                           ToggleLightUseCase toggleLightUseCase,
                           GetLightStateUseCase getLightStateUseCase) {
        this.registerLightUseCase = registerLightUseCase;
        this.toggleLightUseCase = toggleLightUseCase;
        this.getLightStateUseCase = getLightStateUseCase;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void registerLight(@RequestBody RegisterLightRequest request) {
        registerLightUseCase.registerLight(request.id());
    }

    @GetMapping("/{id}")
    public ToggleLightResponse getLightState(@PathVariable String id) {
        LightState state = getLightStateUseCase.getLightState(id);
        return new ToggleLightResponse(state.name());
    }

    @PostMapping("/{id}/toggle")
    public ToggleLightResponse toggleLight(@PathVariable String id) {
        LightState state = toggleLightUseCase.toggleLight(id);
        return new ToggleLightResponse(state.name());
    }
}
