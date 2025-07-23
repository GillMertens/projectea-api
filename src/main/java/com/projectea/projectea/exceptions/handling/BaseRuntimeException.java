package com.projectea.projectea.exceptions.handling;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.jetbrains.annotations.NotNull;

@Getter
public class BaseRuntimeException extends RuntimeException{
    private final HttpStatus status;

    public BaseRuntimeException(@NotNull String message, HttpStatus status) {
        super(message);
        this.status = status;
    }
}
