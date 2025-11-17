package com.main.api.payload;

import lombok.*;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * @author : AnhNT
 * @since : 10/11/2021, Wed
 */
@SuppressWarnings("serial")

@Getter
@Setter
@Builder
public class ResponseData<T> implements Serializable {
    private Timestamp timestamp;
    private Integer status;
    private String error;
    private String trace;
    private String message;
    private String path;
    private T body;

    public ResponseData(){

    }

    public ResponseData(Timestamp timestamp, Integer status, String error, String trace, String message, String path, T body) {
        this.timestamp = timestamp;
        this.status = status;
        this.error = error;
        this.trace = trace;
        this.message = message;
        this.path = path;
        this.body = body;
    }

    public ResponseData(Timestamp timestamp, Integer status, String error, String trace, String message, String path) {
        this.timestamp = timestamp;
        this.status = status;
        this.error = error;
        this.trace = trace;
        this.message = message;
        this.path = path;
    }
}
