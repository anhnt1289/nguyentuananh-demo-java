package com.main.api.exception;

import com.base.common.util.DateUtil;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
import java.time.LocalDateTime;

/**
 * @author : AnhNT
 * @since : 10/11/2021, Wed
 */
@Builder
public class ApiError {

    private String errorCode;

    private Timestamp timestamp;

    private String message;

    private String trace;

    public ApiError(String errorCode, Timestamp timestamp, String message, String trace) {
        this.errorCode = errorCode;
        this.timestamp = timestamp;
        this.message = message;
        this.trace = trace;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTrace() {
        return trace;
    }

    public void setTrace(String trace) {
        this.trace = trace;
    }
}
