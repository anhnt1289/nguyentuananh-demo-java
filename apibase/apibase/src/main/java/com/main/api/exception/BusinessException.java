package com.main.api.exception;

import lombok.Data;

@Data
public class BusinessException extends RuntimeException{
    private int errorCode;
    private String message;

    public BusinessException(int code) {
        this.errorCode = code;
    }

    public BusinessException(int code, String message) {
        this.errorCode = code;
        this.message = message;
    }
    public int getErrorCode() {
        return errorCode;
    }

}
