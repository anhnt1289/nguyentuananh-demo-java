package com.main.core.controller;

import com.base.common.model.User;
import com.base.common.payload.LoginRequest;
import com.base.common.payload.SignUpRequest;
import com.base.common.service.AuthService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.main.api.ApiBaseProjectApplication;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static junit.framework.Assert.assertEquals;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;

/**
 * AuthController Tester.
 *
 * @author AnhNT
 * @version 1.0
 * @since <pre>Feb 16, 2023</pre>
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ApiBaseProjectApplication.class)
public class AuthControllerTest {
    @Autowired
    private WebApplicationContext context;

    @MockBean
    private AuthService authService;

    private MockMvc mvc;

    @Before
    public void setup() {
        mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }

    /**
     * Method: authenticateUser(@Valid @RequestBody LoginRequest loginRequest, HttpServletRequest request)
     */
    @Test
    @DisplayName("testAuthenticateUserWithStatus200")
    public void testAuthenticateUserWithStatus200() throws Exception {
        LoginRequest loginRequest = LoginRequest.builder().email("xxxxx@gmail.com").password("123456").build();
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String json = ow.writeValueAsString(loginRequest);
        Mockito.when(authService.authenticateUser(loginRequest)).thenReturn("1123SDJ.21321S.232SDSDSA");
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post("/api/auth/login").contentType(MediaType.APPLICATION_JSON).content(json)).andReturn();
        int status = mvcResult.getResponse().getStatus();
        assertEquals(HttpStatus.OK.value(), status);
    }

    /**
     * Method: authenticateUser(@Valid @RequestBody LoginRequest loginRequest, HttpServletRequest request)
     */
    @Test
    @DisplayName("testAuthenticateUserWithError")
    public void testAuthenticateUserWithException() throws Exception {
        LoginRequest loginRequest = LoginRequest.builder().email("xxxxx@gmail.com").password("123456").build();
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String json = ow.writeValueAsString(loginRequest);
        Mockito.when(authService.authenticateUser(Mockito.anyObject()))
                .thenThrow(new RuntimeException());
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post("/api/auth/login").contentType(MediaType.APPLICATION_JSON).content(json)).andReturn();
        int status = mvcResult.getResponse().getStatus();
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), status);
    }

    /**
     * Method: registerUser(@Valid @RequestBody SignUpRequest signUpRequest, HttpServletRequest request)
     */
    @Test
    @DisplayName("testRegisterUserWithStatus201")
    public void testRegisterUserWithStatus201() throws Exception {
        SignUpRequest signUpRequest = new SignUpRequest("Nguyen Van A", "xxxxx@gmail.com", "123456");
        User result = new User();
        result.setEmail("xxxxx@gmail.com");
        result.setName("Nguyen Van A");
        result.setId(1l);
        result.setImageUrl("url");
        result.setEmailVerified(true);
        result.setRoleId(1);
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String json = ow.writeValueAsString(signUpRequest);
        Mockito.when(authService.registerUser(Mockito.any())).thenReturn(Mockito.spy(result));
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post("/api/auth/signup").contentType(MediaType.APPLICATION_JSON).content(json)).andReturn();
        int status = mvcResult.getResponse().getStatus();
        assertEquals(HttpStatus.CREATED.value(), status);
    }

    /**
     * Method: registerUser(@Valid @RequestBody SignUpRequest signUpRequest, HttpServletRequest request)
     */
    @Test
    @DisplayName("testRegisterUserWithError")
    public void testRegisterUserWithError() throws Exception {
        SignUpRequest signUpRequest = new SignUpRequest("Nguyen Van A", "xxxxx@gmail.com", "123456");
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String json = ow.writeValueAsString(signUpRequest);
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post("/api/auth/signup").contentType(MediaType.APPLICATION_JSON).content(json)).andReturn();
        int status = mvcResult.getResponse().getStatus();
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), status);
    }

    /**
     * Method: refreshToken(HttpServletRequest request)
     */
    @Test
    @DisplayName("testRefreshTokenWithStatus200")
    @WithMockUser(username = "admin", authorities = { "ADMIN", "USER" })
    public void testRefreshTokenWithStatus200() throws Exception {
        Mockito.when(authService.refreshToken()).thenReturn("1123SDJ.21321S.232SDSDSA");
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post("/api/auth/refresh-token").contentType(MediaType.APPLICATION_JSON)).andReturn();
        int status = mvcResult.getResponse().getStatus();
        assertEquals(HttpStatus.OK.value(), status);
    }

    /**
     * Method: refreshToken(HttpServletRequest request)
     */
    @Test
    @DisplayName("testRefreshTokenWithError")
    @WithMockUser(username = "admin", authorities = { "ADMIN", "USER" })
    public void testRefreshTokenWithError() throws Exception {
        Mockito.when(authService.refreshToken()).thenThrow(new RuntimeException());
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post("/api/auth/refresh-token").contentType(MediaType.APPLICATION_JSON)).andReturn();
        int status = mvcResult.getResponse().getStatus();
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), status);
    }

    /**
     * Method: logout(HttpServletRequest request)
     */
    @Test
    @DisplayName("testLogoutWithStatus200")
    @WithMockUser(username = "admin", authorities = { "ADMIN", "USER" })
    public void testLogoutWithStatus200() throws Exception {
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post("/api/auth/logout").contentType(MediaType.APPLICATION_JSON)).andReturn();
        int status = mvcResult.getResponse().getStatus();
        assertEquals(HttpStatus.OK.value(), status);
    }

    /**
     * Method: logout(HttpServletRequest request)
     */
    @Test
    @DisplayName("testLogoutWithError")
    @WithMockUser(username = "admin", authorities = { "ADMIN", "USER" })
    public void testLogoutWithError() throws Exception {
        Mockito.doThrow(new RuntimeException()).when(authService).logout();
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post("/api/auth/logout").contentType(MediaType.APPLICATION_JSON)).andReturn();
        int status = mvcResult.getResponse().getStatus();
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), status);
    }


} 
