package com.main.api.service.impl;

import com.base.common.constant.UserStatusEnum;
import com.base.common.model.User;
import com.base.common.payload.LoginRequest;
import com.base.common.payload.SignUpRequest;
import com.base.common.repository.UserRepository;
import com.base.common.service.AuthService;
import com.base.common.util.AuthProvider;
import com.base.common.util.Util;
import com.main.api.exception.BadRequestException;
import com.main.api.model.BlackListRedis;
import com.main.api.pointcutadvice.annotations.RedisCache;
import com.main.api.security.TokenProvider;
import com.main.api.service.BlackListRedisService;
import com.main.api.util.JwtTokenUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * @author : AnhNT
 * @since : 10/11/2021, Wed
 */
@Slf4j
@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private BlackListRedisService blackListRedisService;

    @Autowired
    private TokenProvider tokenProvider;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserCacheService userCacheService;

    /**
     * Authenticate with web
     *
     * @param loginRequest
     * @return token
     */
    @Override
    @RedisCache
    public String authenticateUser(LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getEmail(),
                        loginRequest.getPassword()
                )
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = tokenProvider.createToken(authentication);
        return token;
    }

    /**
     * Register user
     *
     * @param signUpRequest
     * @return user
     */
    @Override
    public User registerUser(SignUpRequest signUpRequest) {
        if(userRepository.existsByEmailAndStatus(signUpRequest.getEmail(), UserStatusEnum.ACTIVATE.intValue())) {
            throw new BadRequestException("Email address already in use.");
        }
        // Creating user's account
        User user = new User();
        user.setName(signUpRequest.getName());
        user.setEmail(signUpRequest.getEmail());
        user.setPassword(signUpRequest.getPassword());
        user.setProvider(AuthProvider.local);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user = userRepository.save(user);
        if (Util.validate(user.getId())) {
            userCacheService.getInstance().put(user.getId(), user);
        }
        return user;
    }

    /**
     * Refresh Token
     *
     * @return token
     */
    @Override
    public String refreshToken() {
        BlackListRedis blackListRedis = jwtTokenUtil.getBlackListRedis();
        if (blackListRedis == null)
            return null;
        String tokenOld = blackListRedis.getToken();
        String token = jwtTokenUtil.refreshToken(blackListRedis.getToken());
        blackListRedis.setToken(token);
        blackListRedisService.addBlackList(blackListRedis);
        blackListRedisService.deleteTokenBlackList(tokenOld);
        return token;
    }

    /**
     * Logout
     *
     * @return token
     */
    @Override
    public void logout() {
        BlackListRedis blackListRedis = jwtTokenUtil.getBlackListRedis();
        if (tokenProvider.validateToken(blackListRedis.getToken())) {
            blackListRedisService.deleteTokenBlackList(blackListRedis.getToken());
        }
    }
}
