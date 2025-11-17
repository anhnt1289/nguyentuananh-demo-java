package com.main.core.controller;

import com.base.common.model.ContentComment;
import com.base.common.payload.AddContentCommentRequest;
import com.base.common.payload.EditContentCommentRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.main.api.ApiBaseProjectApplication;
import com.main.api.service.ContentCommentService;
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
 * ContentCommentController Tester.
 *
 * @author AnhNT
 * @version 1.0
 * @since <pre>Feb 16, 2023</pre>
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ApiBaseProjectApplication.class)
public class ContentCommentControllerTest {
    @Autowired
    private WebApplicationContext context;

    @MockBean
    private ContentCommentService contentCommentService;

    private MockMvc mvc;

    @Before
    public void setup() {
        mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }

    /**
     * Method: loadAllComment(@RequestHeader(name = "Accept-Language", required = false) String languageCode, @RequestParam(name = "limit", defaultValue = DefaultValue.LIMIT,required = false) Integer limit, @RequestParam(name = "page", defaultValue = DefaultValue.PAGE, required = false) Integer page, @RequestParam(name = "sortBy", defaultValue = DefaultValue.SORT_BY, required = false) String sortBy, @RequestParam(name = "sortType", defaultValue = DefaultValue.SORT_TYPE_ASC, required = false) String sortType, @RequestParam(name = "keyword", required = false) String keyword, HttpServletRequest request)
     */
    @Test
    @DisplayName("testLoadAllCommentWithStatus200")
    @WithMockUser(username = "admin", authorities = { "ADMIN", "USER" })
    public void testLoadAllComment() throws Exception {
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get("/api/comment/find").contentType(MediaType.APPLICATION_JSON)).andReturn();
        int status = mvcResult.getResponse().getStatus();
        assertEquals(HttpStatus.OK.value(), status);
    }

    /**
     * Method: loadAllComment(@RequestHeader(name = "Accept-Language", required = false) String languageCode, @RequestParam(name = "limit", defaultValue = DefaultValue.LIMIT,required = false) Integer limit, @RequestParam(name = "page", defaultValue = DefaultValue.PAGE, required = false) Integer page, @RequestParam(name = "sortBy", defaultValue = DefaultValue.SORT_BY, required = false) String sortBy, @RequestParam(name = "sortType", defaultValue = DefaultValue.SORT_TYPE_ASC, required = false) String sortType, @RequestParam(name = "keyword", required = false) String keyword, HttpServletRequest request)
     */
    @Test
    @DisplayName("testLoadAllCommentWithError")
    @WithMockUser(username = "admin", authorities = { "ADMIN", "USER" })
    public void testLoadAllCommentWithError() throws Exception {
        Mockito.when(contentCommentService.find(Mockito.anyObject()))
                .thenThrow(new RuntimeException());
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get("/api/comment/find").contentType(MediaType.APPLICATION_JSON)).andReturn();
        int status = mvcResult.getResponse().getStatus();
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), status);
    }

    /**
     * Method: addContentComment(@Valid @RequestBody AddContentCommentRequest contentCommentRequest, BindingResult errors, HttpServletRequest request)
     */
    @Test
    @DisplayName("testContentCommentWithStatus200")
    @WithMockUser(username = "admin", authorities = { "ADMIN", "USER" })
    public void testContentCommentWithStatus200() throws Exception {
        AddContentCommentRequest addContentCommentRequest = new AddContentCommentRequest(1l, "comment", 1, 1);
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String json = ow.writeValueAsString(addContentCommentRequest);
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post("/api/comment/add").contentType(MediaType.APPLICATION_JSON).content(json)).andReturn();
        int status = mvcResult.getResponse().getStatus();
        assertEquals(HttpStatus.OK.value(), status);
    }

    /**
     * Method: addContentComment(@Valid @RequestBody AddContentCommentRequest contentCommentRequest, BindingResult errors, HttpServletRequest request)
     */
    @Test
    @DisplayName("testContentCommentWithBindingResultError")
    @WithMockUser(username = "admin", authorities = { "ADMIN", "USER" })
    public void testContentCommentWithBindingResultError() throws Exception {
        AddContentCommentRequest addContentCommentRequest = new AddContentCommentRequest(1l, "comment", 1, 1);
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String json = ow.writeValueAsString(addContentCommentRequest);
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post("/api/comment/add").contentType(MediaType.APPLICATION_JSON).content(json)).andReturn();
        int status = mvcResult.getResponse().getStatus();
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), status);
    }

    /**
     * Method: addContentComment(@Valid @RequestBody AddContentCommentRequest contentCommentRequest, BindingResult errors, HttpServletRequest request)
     */
    @Test
    @DisplayName("testContentCommentWithError")
    @WithMockUser(username = "admin", authorities = { "ADMIN", "USER" })
    public void testContentCommentWithError() throws Exception {
        AddContentCommentRequest addContentCommentRequest = new AddContentCommentRequest(1l, "comment", 1, 1);
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String json = ow.writeValueAsString(addContentCommentRequest);
        Mockito.when(contentCommentService.add(Mockito.anyObject()))
                .thenThrow(new RuntimeException());
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post("/api/comment/add").contentType(MediaType.APPLICATION_JSON).content(json)).andReturn();
        int status = mvcResult.getResponse().getStatus();
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), status);
    }

    /**
     * Method: updateContentComment(@Valid @RequestBody EditContentCommentRequest editContentCommentRequest, @RequestHeader(name = "Accept-Language", required = false) String languageCode, BindingResult errors, HttpServletRequest request)
     */
    @Test
    @DisplayName("testUpdateContentCommentWithStatus200")
    @WithMockUser(username = "admin", authorities = { "ADMIN", "USER" })
    public void testUpdateContentCommentWithStatus200() throws Exception {
        EditContentCommentRequest editContentCommentRequest = new EditContentCommentRequest(1l,1l, "comment", 1, 1);
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String json = ow.writeValueAsString(editContentCommentRequest);
        Mockito.when(contentCommentService.edit(Mockito.any())).thenReturn(null);
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post("/api/comment/update").header("Accept-Language", "vi").contentType(MediaType.APPLICATION_JSON).content(json)).andReturn();
        int status = mvcResult.getResponse().getStatus();
        assertEquals(HttpStatus.OK.value(), status);
    }

    /**
     * Method: updateContentComment(@Valid @RequestBody EditContentCommentRequest editContentCommentRequest, @RequestHeader(name = "Accept-Language", required = false) String languageCode, BindingResult errors, HttpServletRequest request)
     */
    @Test
    @DisplayName("testUpdateContentCommentWithError")
    @WithMockUser(username = "admin", authorities = { "ADMIN", "USER" })
    public void testUpdateContentCommentWithError() throws Exception {
        EditContentCommentRequest editContentCommentRequest = new EditContentCommentRequest(1l,1l, "comment", 1, 1);
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String json = ow.writeValueAsString(editContentCommentRequest);
        ContentComment contentComment = new ContentComment();
        contentComment.setContentId(1l);
        contentComment.setComment("comment");
        Mockito.when(contentCommentService.edit(Mockito.any())).thenReturn(contentComment);
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post("/api/comment/update").header("Accept-Language", "vi").contentType(MediaType.APPLICATION_JSON).content(json)).andReturn();
        int status = mvcResult.getResponse().getStatus();
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), status);
    }

    /**
     * Method: updateContentComment(@Valid @RequestBody EditContentCommentRequest editContentCommentRequest, @RequestHeader(name = "Accept-Language", required = false) String languageCode, BindingResult errors, HttpServletRequest request)
     */
    @Test
    @DisplayName("testUpdateContentCommentWithException")
    @WithMockUser(username = "admin", authorities = { "ADMIN", "USER" })
    public void testUpdateContentCommentWithException() throws Exception {
        EditContentCommentRequest editContentCommentRequest = new EditContentCommentRequest(1l,1l, "comment", 1, 1);
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String json = ow.writeValueAsString(editContentCommentRequest);
        Mockito.when(contentCommentService.edit(Mockito.any())).thenThrow(new RuntimeException());
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post("/api/comment/update").header("Accept-Language", "vi").contentType(MediaType.APPLICATION_JSON).content(json)).andReturn();
        int status = mvcResult.getResponse().getStatus();
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), status);
    }

    /**
     * Method: deleteUser(@PathVariable("id") Long id, @RequestHeader(name = "Accept-Language", required = false) String languageCode, HttpServletRequest request)
     */
    @Test
    @DisplayName("testDeleteContentCommentWithStatus200")
    @WithMockUser(username = "admin", authorities = { "ADMIN", "USER" })
    public void testDeleteContentCommentWithStatus200() throws Exception {
        long id = 1;
        Mockito.when(contentCommentService.delete(Mockito.any())).thenReturn(1);
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post("/api/comment/delete/"+id).header("Accept-Language", "vi").contentType(MediaType.APPLICATION_JSON)).andReturn();
        int status = mvcResult.getResponse().getStatus();
        assertEquals(HttpStatus.OK.value(), status);
    }

    /**
     * Method: deleteUser(@PathVariable("id") Long id, @RequestHeader(name = "Accept-Language", required = false) String languageCode, HttpServletRequest request)
     */
    @Test
    @DisplayName("testDeleteContentCommentWithError")
    @WithMockUser(username = "admin", authorities = { "ADMIN", "USER" })
    public void testDeleteContentCommentWithError() throws Exception {
        long id = 1;
        Mockito.when(contentCommentService.delete(Mockito.any())).thenReturn(0);
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post("/api/comment/delete/"+id).header("Accept-Language", "vi").contentType(MediaType.APPLICATION_JSON)).andReturn();
        int status = mvcResult.getResponse().getStatus();
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), status);
    }

    /**
     * Method: deleteUser(@PathVariable("id") Long id, @RequestHeader(name = "Accept-Language", required = false) String languageCode, HttpServletRequest request)
     */
    @Test
    @DisplayName("testDeleteContentCommentWithException")
    @WithMockUser(username = "admin", authorities = { "ADMIN", "USER" })
    public void testDeleteContentCommentWithException() throws Exception {
        long id = 1;
        Mockito.when(contentCommentService.delete(Mockito.any())).thenThrow(new RuntimeException());
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post("/api/comment/delete/"+id).header("Accept-Language", "vi").contentType(MediaType.APPLICATION_JSON)).andReturn();
        int status = mvcResult.getResponse().getStatus();
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), status);
    }


} 
