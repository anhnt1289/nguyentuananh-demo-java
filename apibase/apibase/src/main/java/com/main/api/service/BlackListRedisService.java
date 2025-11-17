package com.main.api.service;

import com.main.api.model.BlackListRedis;

/**
 * @author : AnhNT
 * @since : 24/11/2021, Wed
 */
public interface BlackListRedisService {
    String addBlackList(BlackListRedis blackListRedis);
    void deleteTokenBlackList(String token);
    String findByToken(String token);
}
