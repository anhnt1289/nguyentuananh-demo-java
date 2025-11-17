package com.main.core.impl;

import com.base.common.model.User;
import com.base.common.payload.LoginRequest;
import com.base.common.payload.SignUpRequest;
import com.base.common.repository.UserRepository;
import com.main.api.ApiBaseProjectApplication;
import com.main.api.model.BlackListRedis;
import com.main.api.security.TokenProvider;
import com.main.api.service.ContentCommentService;
import com.main.api.service.impl.AuthServiceImpl;
import com.main.api.util.JwtTokenUtil;
import org.junit.Test;
import org.junit.Before;
import org.junit.After;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;

/**
 * AuthServiceImpl Tester.
 *
 * @author AnhNT
 * @version 1.0
 * @since <pre>Feb 16, 2023</pre>
 */
@SpringBootTest(classes = ApiBaseProjectApplication.class)
@RunWith(SpringRunner.class)
public class AuthServiceImplTest {
    @Autowired
    AuthServiceImpl authService;
    @MockBean
    private TokenProvider tokenProvider;
    @MockBean
    private UserRepository userRepository;
    @MockBean
    private JwtTokenUtil jwtTokenUtil;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    /**
     * Method: authenticateUser(LoginRequest loginRequest)
     */
    @Test
    @DisplayName("testAuthenticateUser")
    public void testAuthenticateUser() throws Exception {
        String token = "token";
        Mockito.when(tokenProvider.createToken(Mockito.any())).thenReturn(token);
        LoginRequest loginRequest = LoginRequest.builder().email("xxxxx@gmail.com").password("123456").build();
        String tokenCall = authService.authenticateUser(loginRequest);
        assertEquals(tokenCall, token);
    }

    /**
     * Method: registerUser(SignUpRequest signUpRequest)
     */
    @Test
    @DisplayName("testRegisterUserWithException")
    public void testRegisterUser() throws Exception {
        User user = new User();
        user.setName("van A");
        user.setEmail("xxxxx@gmail.com");
        user.setPassword("123456");
        user.setId(1l);
        SignUpRequest signUpRequest = new SignUpRequest("Nguyen Van A", "xxxxx@gmail.com", "123456");
        Mockito.when(userRepository.existsByEmailAndStatus(Mockito.any(), Mockito.any())).thenReturn(false);
        Mockito.when(userRepository.save(Mockito.any())).thenReturn(user);

        User userCall = authService.registerUser(signUpRequest);
        assertEquals(userCall, user);
    }

    /**
     * Method: registerUser(SignUpRequest signUpRequest)
     */
    @Test
    @DisplayName("testRegisterUserWithException")
    public void testRegisterUserWithException() throws Exception {
        SignUpRequest signUpRequest = new SignUpRequest("Nguyen Van A", "xxxxx@gmail.com", "123456");
        Mockito.when(userRepository.existsByEmailAndStatus(Mockito.any(), Mockito.any())).thenReturn(true);
        boolean isError = false;
        try {
            authService.registerUser(signUpRequest);
        } catch (Exception e) {
            isError = true;
        }
        assertEquals(true, isError);
    }

    /**
     * Method: refreshToken()
     */
    @Test
    @DisplayName("testRefreshToken")
    public void testRefreshToken() throws Exception {
        String newToken = "newToken";
        BlackListRedis blackListRedis = new BlackListRedis();
        blackListRedis.setToken("token");
        blackListRedis.setUserName("user name");
        blackListRedis.setIp("ip");
        blackListRedis.setId(1l);
        Mockito.when(jwtTokenUtil.getBlackListRedis()).thenReturn(blackListRedis);
        Mockito.when(jwtTokenUtil.refreshToken(Mockito.any())).thenReturn(newToken);
        String token = authService.refreshToken();
        assertEquals(newToken, token);
    }

    /**
     * Method: refreshToken()
     */
    @Test
    @DisplayName("testRefreshTokenWithNull")
    public void testRefreshTokenWithNull() throws Exception {
        Mockito.when(jwtTokenUtil.getBlackListRedis()).thenReturn(null);
        String token = authService.refreshToken();
        assertEquals(null, token);
    }

    /**
     * Method: logout()
     */
    @Test
    @DisplayName("testLogout")
    public void testLogout() throws Exception {
        BlackListRedis blackListRedis = new BlackListRedis();
        blackListRedis.setToken("token");
        blackListRedis.setUserName("user name");
        blackListRedis.setIp("ip");
        blackListRedis.setId(1l);
        Mockito.when(jwtTokenUtil.getBlackListRedis()).thenReturn(blackListRedis);
        Mockito.when(tokenProvider.validateToken(Mockito.any())).thenReturn(true);
        authService.logout();
    }

} 
