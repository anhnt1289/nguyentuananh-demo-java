package com.main.api.exception;

import org.springframework.security.core.AuthenticationException;

/**
 * @author : AnhNT
 * @since : 10/11/2021, Wed
 */
public class OAuth2AuthenticationProcessingException extends AuthenticationException {
    public OAuth2AuthenticationProcessingException(String msg, Throwable t) {
        super(msg, t);
    }

    public OAuth2AuthenticationProcessingException(String msg) {
        super(msg);
    }
}
