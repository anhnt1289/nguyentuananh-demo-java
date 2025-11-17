package com.main.core.controller;

import com.base.common.payload.UserRequest;
import com.base.common.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.main.api.ApiBaseProjectApplication;
import com.main.api.service.ContentCommentService;
import org.junit.Test;
import org.junit.Before; 
import org.junit.After;
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

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static junit.framework.Assert.assertEquals;

/** 
* UserController Tester. 
* 
* @author AnhNT
* @since <pre>Feb 16, 2023</pre> 
* @version 1.0 
*/
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ApiBaseProjectApplication.class)
public class UserControllerTest {

    @Autowired
    private WebApplicationContext context;

    @MockBean
    private UserService userService;

    private MockMvc mvc;

    @Before
    public void setup() {
        mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }
    /**
     *
     * Method: loadAllContent(@RequestHeader(name = "Accept-Language", required = false) String languageCode, @RequestParam(name = "limit", defaultValue = DefaultValue.LIMIT,required = false) Integer limit, @RequestParam(name = "page", defaultValue = DefaultValue.PAGE, required = false) Integer page, @RequestParam(name = "sortBy", defaultValue = DefaultValue.SORT_BY, required = false) String sortBy, @RequestParam(name = "sortType", defaultValue = DefaultValue.SORT_TYPE_ASC, required = false) String sortType, @RequestParam(name = "keyword", required = false) String keyword, HttpServletRequest request)
     *
     */
    @Test
    @DisplayName("testLoadAllUserWithStatus200")
    @WithMockUser(username = "admin", authorities = { "ADMIN", "USER" })
    public void testLoadAllUser() throws Exception {
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get("/api/user/find").contentType(MediaType.APPLICATION_JSON)).andReturn();
        int status = mvcResult.getResponse().getStatus();
        assertEquals(HttpStatus.OK.value(), status);
    }

    /**
     *
     * Method: loadAllContent(@RequestHeader(name = "Accept-Language", required = false) String languageCode, @RequestParam(name = "limit", defaultValue = DefaultValue.LIMIT,required = false) Integer limit, @RequestParam(name = "page", defaultValue = DefaultValue.PAGE, required = false) Integer page, @RequestParam(name = "sortBy", defaultValue = DefaultValue.SORT_BY, required = false) String sortBy, @RequestParam(name = "sortType", defaultValue = DefaultValue.SORT_TYPE_ASC, required = false) String sortType, @RequestParam(name = "keyword", required = false) String keyword, HttpServletRequest request)
     *
     */
    @Test
    @DisplayName("testLoadAllContentWithError")
    @WithMockUser(username = "admin", authorities = { "ADMIN", "USER" })
    public void testLoadAllContentWithError() throws Exception {
        Mockito.when(userService.find(Mockito.anyObject()))
                .thenThrow(new RuntimeException());
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get("/api/user/find").contentType(MediaType.APPLICATION_JSON)).andReturn();
        int status = mvcResult.getResponse().getStatus();
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), status);
    }

    /**
     *
     * Method: getCurrentUser(@PathVariable("id") Long id, HttpServletRequest request)
     *
     */
    @Test
    @DisplayName("testGetCurrentUserWithStatus200")
    @WithMockUser(username = "admin", authorities = { "ADMIN", "USER" })
    public void testGetCurrentUserWithStatus200() throws Exception {
        long id = 1l;
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get("/api/user/me/"+id).contentType(MediaType.APPLICATION_JSON)).andReturn();
        int status = mvcResult.getResponse().getStatus();
        assertEquals(HttpStatus.OK.value(), status);
    }

    /**
     *
     * Method: getCurrentUser(@PathVariable("id") Long id, HttpServletRequest request)
     *
     */
    @Test
    @DisplayName("testGetCurrentUserWithError")
    @WithMockUser(username = "admin", authorities = { "ADMIN", "USER" })
    public void testGetCurrentUserWithError() throws Exception {
        long id = 1l;
        Mockito.when(userService.getCurrentUser(Mockito.anyObject()))
                .thenThrow(new RuntimeException());
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get("/api/user/me/"+id).contentType(MediaType.APPLICATION_JSON)).andReturn();
        int status = mvcResult.getResponse().getStatus();
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), status);
    }

    /**
     *
     * Method: deleteUser(@PathVariable("id") Long id, @RequestHeader(name = "Accept-Language", required = false) String languageCode, HttpServletRequest request)
     *
     */
    @Test
    @DisplayName("testDeleteUserWithStatus200")
    @WithMockUser(username = "admin", authorities = { "ADMIN", "USER" })
    public void testDeleteUser() throws Exception {

        long id = 1l;
        Mockito.when(userService.deleteUser(Mockito.anyObject()))
                .thenReturn(1);
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post("/api/user/delete/"+id).header("Accept-Language", "vi").contentType(MediaType.APPLICATION_JSON)).andReturn();
        int status = mvcResult.getResponse().getStatus();
        assertEquals(HttpStatus.OK.value(), status);
    }

    /**
     *
     * Method: deleteUser(@PathVariable("id") Long id, @RequestHeader(name = "Accept-Language", required = false) String languageCode, HttpServletRequest request)
     *
     */
    @Test
    @DisplayName("testDeleteUserWithErrorZero")
    @WithMockUser(username = "admin", authorities = { "ADMIN", "USER" })
    public void testDeleteUserWithErrorZero() throws Exception {

        long id = 1l;
        Mockito.when(userService.deleteUser(Mockito.anyObject()))
                .thenReturn(0);
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post("/api/user/delete/"+id).header("Accept-Language", "vi").contentType(MediaType.APPLICATION_JSON)).andReturn();
        int status = mvcResult.getResponse().getStatus();
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), status);
    }

    /**
     *
     * Method: deleteUser(@PathVariable("id") Long id, @RequestHeader(name = "Accept-Language", required = false) String languageCode, HttpServletRequest request)
     *
     */
    @Test
    @DisplayName("testDeleteUserWithErrorNegative")
    @WithMockUser(username = "admin", authorities = { "ADMIN", "USER" })
    public void testDeleteUserWithErrorNegative() throws Exception {

        long id = 1l;
        Mockito.when(userService.deleteUser(Mockito.anyObject()))
                .thenReturn(-1);
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post("/api/user/delete/"+id).header("Accept-Language", "vi").contentType(MediaType.APPLICATION_JSON)).andReturn();
        int status = mvcResult.getResponse().getStatus();
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), status);
    }

    /**
     *
     * Method: deleteUser(@PathVariable("id") Long id, @RequestHeader(name = "Accept-Language", required = false) String languageCode, HttpServletRequest request)
     *
     */
    @Test
    @DisplayName("testDeleteUserWithErrRuntimeException")
    @WithMockUser(username = "admin", authorities = { "ADMIN", "USER" })
    public void testDeleteUserWithErrRuntimeException() throws Exception {

        long id = 1l;
        Mockito.when(userService.deleteUser(Mockito.anyObject()))
                .thenThrow(new RuntimeException());
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post("/api/user/delete/"+id).header("Accept-Language", "vi").contentType(MediaType.APPLICATION_JSON)).andReturn();
        int status = mvcResult.getResponse().getStatus();
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), status);
    }

    /**
     *
     * Method: updateUser(@Valid @RequestBody UserRequest userRequest, @RequestHeader(name = "Accept-Language", required = false) String languageCode, BindingResult errors, HttpServletRequest request)
     *
     */
    @Test
    @DisplayName("testUpdateUserWithStatus200")
    @WithMockUser(username = "admin", authorities = { "ADMIN", "USER" })
    public void testUpdateUser() throws Exception {
        UserRequest userRequest =  UserRequest.builder().email("xxxxx@gmail.com").name("Nguyen Van A").status(1).build();
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String json = ow.writeValueAsString(userRequest);
        Mockito.when(userService.updateUser(Mockito.any())).thenReturn(0);
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post("/api/user/update").header("Accept-Language", "vi").contentType(MediaType.APPLICATION_JSON).content(json)).andReturn();
        int status = mvcResult.getResponse().getStatus();
        assertEquals(HttpStatus.OK.value(), status);
    }

    /**
     *
     * Method: updateUser(@Valid @RequestBody UserRequest userRequest, @RequestHeader(name = "Accept-Language", required = false) String languageCode, BindingResult errors, HttpServletRequest request)
     *
     */
    @Test
    @DisplayName("testUpdateUserWithError")
    @WithMockUser(username = "admin", authorities = { "ADMIN", "USER" })
    public void testUpdateUserWithError() throws Exception {
        UserRequest userRequest =  UserRequest.builder().email("xxxxx@gmail.com").name("Nguyen Van A").status(1).build();
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String json = ow.writeValueAsString(userRequest);
        Mockito.when(userService.updateUser(Mockito.any())).thenReturn(1);
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post("/api/user/update").header("Accept-Language", "vi").contentType(MediaType.APPLICATION_JSON).content(json)).andReturn();
        int status = mvcResult.getResponse().getStatus();
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), status);
    }

    /**
     *
     * Method: updateUser(@Valid @RequestBody UserRequest userRequest, @RequestHeader(name = "Accept-Language", required = false) String languageCode, BindingResult errors, HttpServletRequest request)
     *
     */
    @Test
    @DisplayName("testUpdateUserWithException")
    @WithMockUser(username = "admin", authorities = { "ADMIN", "USER" })
    public void testUpdateUserWithException() throws Exception {
        UserRequest userRequest =  UserRequest.builder().email("xxxxx@gmail.com").name("Nguyen Van A").status(1).build();
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String json = ow.writeValueAsString(userRequest);
        Mockito.when(userService.updateUser(Mockito.any())).thenThrow(new RuntimeException());
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post("/api/user/update").header("Accept-Language", "vi").contentType(MediaType.APPLICATION_JSON).content(json)).andReturn();
        int status = mvcResult.getResponse().getStatus();
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), status);
    }


} 
