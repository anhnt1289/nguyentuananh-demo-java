package com.main.api.service.impl;

import com.main.api.component.JedisUtil;
import com.main.api.model.BlackListRedis;
import com.main.api.service.BlackListRedisService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author : AnhNT
 * @since : 24/11/2021, Wed
 */
@Slf4j
@Service
public class BlackListRedisServiceImpl implements BlackListRedisService {

    @Autowired
    private JedisUtil jedisUtil;

    @Override
    public String addBlackList(BlackListRedis blackListRedis) {
        jedisUtil.set(blackListRedis.getToken(), blackListRedis.getToken());
        return blackListRedis.getToken();
    }

    @Override
    public void deleteTokenBlackList(String token) {
        jedisUtil.del(token);
    }

    @Override
    public String findByToken(String token) {
        return jedisUtil.get(token);
    }
}
