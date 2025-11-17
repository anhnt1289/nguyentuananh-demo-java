package com.main.api.controller;

import com.base.common.model.User;
import com.base.common.payload.LoginRequest;
import com.base.common.payload.SignUpRequest;
import com.base.common.service.AuthService;
import com.base.common.util.DateUtil;
import com.main.api.payload.*;
import com.main.api.pointcutadvice.annotations.Loggable;
import com.main.api.util.Util;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.net.URI;

/**
 * @author : AnhNT
 * @since : 10/11/2021, Wed
 */
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authenticate;
    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    /**
     * Entry point for the user log in. Return the jwt auth token and the refresh token
     */
    @ApiOperation(value = "Logs the user in to the system and return the auth tokens (Local)")
    @PostMapping("/login")
    @Loggable
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest, HttpServletRequest request) {
        String path = Util.getFullURL(request);
        try {
            String token = authenticate.authenticateUser(loginRequest);
            ResponseData responseData = ResponseData.builder().body(token).timestamp(DateUtil.getCurrentTimeStamp()).status(HttpStatus.OK.value()).message(HttpStatus.OK.toString()).path(path).trace(logger.getName()).build();
            return DataResponse.success(responseData);
        } catch (Exception ex) {
            ResponseData responseData = ResponseData.builder().timestamp(DateUtil.getCurrentTimeStamp()).status(HttpStatus.INTERNAL_SERVER_ERROR.value()).error(HttpStatus.INTERNAL_SERVER_ERROR.toString()).trace(logger.getName()).message(ex.getMessage()).path(path).build();
            return DataResponse.error(responseData);
        }
    }

    /**
     * Entry point for the user registration process. On successful registration,
     * publish an event to generate email verification token
     */
    @ApiOperation(value = "Registers the user and publishes an event to generate the email verification")
    @PostMapping("/signup")
    @Loggable
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignUpRequest signUpRequest, HttpServletRequest request) {
        String path = Util.getFullURL(request);
        try {
            User result = authenticate.registerUser(signUpRequest);
            LoginRequest loginRequest = LoginRequest.builder().email(signUpRequest.getEmail()).password(signUpRequest.getPassword()).build();
            String token = authenticate.authenticateUser(loginRequest);
            URI location = ServletUriComponentsBuilder
                    .fromCurrentContextPath().path("/user/me")
                    .buildAndExpand(result.getId()).toUri();
            ResponseData responseData = ResponseData.builder().body(token).timestamp(DateUtil.getCurrentTimeStamp()).status(HttpStatus.OK.value()).message(HttpStatus.OK.toString()).path(path).trace(logger.getName()).build();
            return ResponseEntity.created(location)
                    .body(responseData);
        } catch (Exception ex) {
            ResponseData responseData = ResponseData.builder().timestamp(DateUtil.getCurrentTimeStamp()).status(HttpStatus.INTERNAL_SERVER_ERROR.value()).error(HttpStatus.INTERNAL_SERVER_ERROR.toString()).trace(logger.getName()).message(ex.getMessage()).path(path).build();
            return DataResponse.error(responseData);
        }
    }

    @PostMapping("/refresh-token")
    @Loggable
    public ResponseEntity<?> refreshToken(HttpServletRequest request) {
        String path = Util.getFullURL(request);
        try {
            String token = authenticate.refreshToken();
            ResponseData responseData = ResponseData.builder().body(token).timestamp(DateUtil.getCurrentTimeStamp()).status(HttpStatus.OK.value()).message(HttpStatus.OK.toString()).path(path).trace(logger.getName()).build();
            return DataResponse.success(responseData);
        } catch (Exception ex) {
            ResponseData responseData = ResponseData.builder().timestamp(DateUtil.getCurrentTimeStamp()).status(HttpStatus.INTERNAL_SERVER_ERROR.value()).error(HttpStatus.INTERNAL_SERVER_ERROR.toString()).trace(logger.getName()).message(ex.getMessage()).path(path).build();
            return DataResponse.error(responseData);
        }
    }

    @PostMapping("/logout")
    @Loggable
    public ResponseEntity<?> logout(HttpServletRequest request) {
        String path = Util.getFullURL(request);
        try {
            authenticate.logout();
            ResponseData responseData = ResponseData.builder().timestamp(DateUtil.getCurrentTimeStamp()).status(HttpStatus.OK.value()).message(HttpStatus.OK.toString()).path(path).trace(logger.getName()).build();
            return DataResponse.success(responseData);
        } catch (Exception ex) {
            ResponseData responseData = ResponseData.builder().timestamp(DateUtil.getCurrentTimeStamp()).status(HttpStatus.INTERNAL_SERVER_ERROR.value()).error(HttpStatus.INTERNAL_SERVER_ERROR.toString()).trace(logger.getName()).message(ex.getMessage()).path(path).build();
            return DataResponse.error(responseData);
        }
    }

}
