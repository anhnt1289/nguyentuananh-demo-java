package com.main.core.impl;

import com.main.api.ApiBaseProjectApplication;
import com.main.api.model.BlackListRedis;
import com.main.api.service.impl.BlackListRedisServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * BlackListRedisServiceImpl Tester.
 *
 * @author AnhNT
 * @version 1.0
 * @since <pre>Feb 16, 2023</pre>
 */
@SpringBootTest(classes = ApiBaseProjectApplication.class)
@RunWith(SpringRunner.class)
public class BlackListRedisServiceImplTest {
    @Autowired
    BlackListRedisServiceImpl blackListRedisServiceImpl;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    /**
     * Method: addBlackList(BlackListRedis blackListRedis)
     */
    @Test
    @DisplayName("testAddBlackList")
    public void testAddBlackList() throws Exception {
        BlackListRedis blackListRedis = new BlackListRedis();
        blackListRedis.setId(1l);
        blackListRedis.setIp("ip");
        blackListRedis.setUserName("user name");
        blackListRedis.setToken("token");
        String token = blackListRedisServiceImpl.addBlackList(blackListRedis);
        assertEquals(blackListRedis.getToken(), token);
    }

    /**
     * Method: deleteTokenBlackList(String token)
     */
    @Test
    @DisplayName("testDeleteTokenBlackList")
    public void testDeleteTokenBlackList() throws Exception {
        String token = "token";
        blackListRedisServiceImpl.deleteTokenBlackList(token);
    }

    /**
     * Method: findByToken(String token)
     */
    @Test
    @DisplayName("testFindByToken")
    public void testFindByToken() throws Exception {
        String token = "token";
        blackListRedisServiceImpl.findByToken(token);
    }
} 
