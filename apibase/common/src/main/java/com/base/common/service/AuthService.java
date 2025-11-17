package com.base.common.service;

import com.base.common.model.User;
import com.base.common.payload.LoginRequest;
import com.base.common.payload.SignUpRequest;

/**
 * @author : AnhNT
 * @since : 10/11/2021, Wed
 */
public interface AuthService {
    default String authenticateUser(LoginRequest loginRequest) {
        return null;
    }

    default User registerUser(SignUpRequest signUpRequest) {
        return null;
    }

    default String refreshToken() {
        return null;
    }

    default void logout() {
    }
}
