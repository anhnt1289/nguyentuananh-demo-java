package com.main.api.exception;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
public class EntityNotFoundException extends RuntimeException {
    private Class<?> clazz;

    private String errorCode;

    private Map<String, String> parameters;

    public EntityNotFoundException(Class<?> clazz, Map<String, String> parameters, String errorCode) {
        this.clazz = clazz;
        this.errorCode = errorCode;
        this.parameters = parameters;
    }

    public EntityNotFoundException(String message) {
        super(message);
    }
}
