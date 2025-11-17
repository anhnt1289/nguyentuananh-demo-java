package com.main.api.exception;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
@SuppressWarnings("serial")
@Setter
@Getter
public class InternalServerErrorException extends RuntimeException {

    private String errorCode = String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value());

    private String message = HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase();

    public InternalServerErrorException() {
        super();
    }

    public InternalServerErrorException(String message) {
        super(message);
    }
}