package com.main.core.impl;

import com.base.common.model.User;
import com.base.common.repository.UserRepository;
import com.main.api.ApiBaseProjectApplication;
import com.main.api.service.impl.UserCacheService;
import org.junit.Test;
import org.junit.Before;
import org.junit.After;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * UserCacheService Tester.
 *
 * @author AnhNT
 * @version 1.0
 * @since <pre>Feb 16, 2023</pre>
 */
@SpringBootTest(classes = ApiBaseProjectApplication.class)
@RunWith(SpringRunner.class)
public class UserCacheServiceTest {

    @Autowired
    UserCacheService userCacheService;
    @MockBean
    UserRepository userRepository;

    /**
     * Method: init()
     */
    @Test
    @DisplayName("testInit")
    public void testInit() throws Exception {
        Mockito.when(userRepository.findByStatusAndIdGreaterThanOrderByIdAsc(Mockito.any(), Mockito.any(), Mockito.any())).thenReturn(Page.empty());
        userCacheService.init();
    }


    /**
     * Method: init()
     */
    @Test
    @DisplayName("testInitWithException")
    public void testInitWithException() throws Exception {
        Mockito.when(userRepository.findByStatusAndIdGreaterThanOrderByIdAsc(Mockito.any(), Mockito.any(), Mockito.any())).thenReturn(null);
       userCacheService.init();

    }
}
