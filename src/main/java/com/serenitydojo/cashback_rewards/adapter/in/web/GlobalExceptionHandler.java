package com.serenitydojo.cashback_rewards.adapter.in.web;

import com.serenitydojo.cashback_rewards.domain.model.CapacityExceededException;
import com.serenitydojo.cashback_rewards.domain.model.GroupNotFoundException;
import com.serenitydojo.cashback_rewards.domain.model.LightAlreadyAssignedException;
import com.serenitydojo.cashback_rewards.domain.model.NoLightsConfiguredException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NoLightsConfiguredException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleNoLightsConfigured(NoLightsConfiguredException ex) {
        return new ErrorResponse(ex.getMessage());
    }

    @ExceptionHandler(CapacityExceededException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleCapacityExceeded(CapacityExceededException ex) {
        return new ErrorResponse(ex.getMessage());
    }

    @ExceptionHandler(GroupNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleGroupNotFound(GroupNotFoundException ex) {
        return new ErrorResponse(ex.getMessage());
    }

    @ExceptionHandler(LightAlreadyAssignedException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleLightAlreadyAssigned(LightAlreadyAssignedException ex) {
        return new ErrorResponse(ex.getMessage());
    }
}
