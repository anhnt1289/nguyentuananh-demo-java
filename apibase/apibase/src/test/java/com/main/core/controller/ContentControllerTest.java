package com.main.core.controller;

import com.base.common.model.ContentComment;
import com.base.common.payload.AddContentCommentRequest;
import com.base.common.payload.EditContentCommentRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.main.api.ApiBaseProjectApplication;
import com.main.api.service.ContentCommentService;
import org.junit.Test; 
import org.junit.Before; 
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
* ContentController Tester. 
* 
* @author AnhNT
* @since <pre>Feb 16, 2023</pre> 
* @version 1.0 
*/
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ApiBaseProjectApplication.class)
public class ContentControllerTest {

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
     * Method: loadAllContent(@RequestHeader(name = "Accept-Language", required = false) String languageCode, @RequestParam(name = "limit", defaultValue = DefaultValue.LIMIT,required = false) Integer limit, @RequestParam(name = "page", defaultValue = DefaultValue.PAGE, required = false) Integer page, @RequestParam(name = "sortBy", defaultValue = DefaultValue.SORT_BY, required = false) String sortBy, @RequestParam(name = "sortType", defaultValue = DefaultValue.SORT_TYPE_ASC, required = false) String sortType, @RequestParam(name = "keyword", required = false) String keyword, HttpServletRequest request)
     */
    @Test
    @DisplayName("testLoadAllContentWithStatus200")
    @WithMockUser(username = "admin", authorities = { "ADMIN", "USER" })
    public void testLoadAllContent() throws Exception {
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get("/api/comment/find").contentType(MediaType.APPLICATION_JSON)).andReturn();
        int status = mvcResult.getResponse().getStatus();
        assertEquals(HttpStatus.OK.value(), status);
    }

    /**
     * Method: loadAllContent(@RequestHeader(name = "Accept-Language", required = false) String languageCode, @RequestParam(name = "limit", defaultValue = DefaultValue.LIMIT,required = false) Integer limit, @RequestParam(name = "page", defaultValue = DefaultValue.PAGE, required = false) Integer page, @RequestParam(name = "sortBy", defaultValue = DefaultValue.SORT_BY, required = false) String sortBy, @RequestParam(name = "sortType", defaultValue = DefaultValue.SORT_TYPE_ASC, required = false) String sortType, @RequestParam(name = "keyword", required = false) String keyword, HttpServletRequest request)
     */
    @Test
    @DisplayName("testLoadAllContentWithError")
    @WithMockUser(username = "admin", authorities = { "ADMIN", "USER" })
    public void testLoadAllContentWithError() throws Exception {
        Mockito.when(contentCommentService.find(Mockito.anyObject()))
                .thenThrow(new RuntimeException());
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get("/api/comment/find").contentType(MediaType.APPLICATION_JSON)).andReturn();
        int status = mvcResult.getResponse().getStatus();
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), status);
    }

    /**
     * Method: addContentContent(@Valid @RequestBody AddContentCommentRequest contentCommentRequest, BindingResult errors, HttpServletRequest request)
     */
    @Test
    @DisplayName("testContentContentWithStatus200")
    @WithMockUser(username = "admin", authorities = { "ADMIN", "USER" })
    public void testContentContentWithStatus200() throws Exception {
        AddContentCommentRequest addContentCommentRequest = new AddContentCommentRequest(1l, "Content", 1, 1);
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String json = ow.writeValueAsString(addContentCommentRequest);
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post("/api/comment/add").contentType(MediaType.APPLICATION_JSON).content(json)).andReturn();
        int status = mvcResult.getResponse().getStatus();
        assertEquals(HttpStatus.OK.value(), status);
    }

    /**
     * Method: addContentContent(@Valid @RequestBody AddContentCommentRequest contentCommentRequest, BindingResult errors, HttpServletRequest request)
     */
    @Test
    @DisplayName("testContentContentWithBindingResultError")
    @WithMockUser(username = "admin", authorities = { "ADMIN", "USER" })
    public void testContentContentWithBindingResultError() throws Exception {
        AddContentCommentRequest addContentCommentRequest = new AddContentCommentRequest(1l, "Content", 1, 1);
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String json = ow.writeValueAsString(addContentCommentRequest);
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post("/api/comment/add").contentType(MediaType.APPLICATION_JSON).content(json)).andReturn();
        int status = mvcResult.getResponse().getStatus();
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), status);
    }

    /**
     * Method: addContentContent(@Valid @RequestBody AddContentCommentRequest contentCommentRequest, BindingResult errors, HttpServletRequest request)
     */
    @Test
    @DisplayName("testContentContentWithError")
    @WithMockUser(username = "admin", authorities = { "ADMIN", "USER" })
    public void testContentContentWithError() throws Exception {
        AddContentCommentRequest addContentCommentRequest = new AddContentCommentRequest(1l, "Content", 1, 1);
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String json = ow.writeValueAsString(addContentCommentRequest);
        Mockito.when(contentCommentService.add(Mockito.anyObject()))
                .thenThrow(new RuntimeException());
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post("/api/comment/add").contentType(MediaType.APPLICATION_JSON).content(json)).andReturn();
        int status = mvcResult.getResponse().getStatus();
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), status);
    }

    /**
     * Method: updateContentContent(@Valid @RequestBody EditContentCommentRequest editContentCommentRequest, @RequestHeader(name = "Accept-Language", required = false) String languageCode, BindingResult errors, HttpServletRequest request)
     */
    @Test
    @DisplayName("testUpdateContentContentWithStatus200")
    @WithMockUser(username = "admin", authorities = { "ADMIN", "USER" })
    public void testUpdateContentContentWithStatus200() throws Exception {
        EditContentCommentRequest editContentCommentRequest = new EditContentCommentRequest(1l,1l, "Content", 1, 1);
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String json = ow.writeValueAsString(editContentCommentRequest);
        Mockito.when(contentCommentService.edit(Mockito.any())).thenReturn(null);
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post("/api/comment/update").header("Accept-Language", "vi").contentType(MediaType.APPLICATION_JSON).content(json)).andReturn();
        int status = mvcResult.getResponse().getStatus();
        assertEquals(HttpStatus.OK.value(), status);
    }

    /**
     * Method: updateContentContent(@Valid @RequestBody EditContentCommentRequest editContentCommentRequest, @RequestHeader(name = "Accept-Language", required = false) String languageCode, BindingResult errors, HttpServletRequest request)
     */
    @Test
    @DisplayName("testUpdateContentContentWithError")
    @WithMockUser(username = "admin", authorities = { "ADMIN", "USER" })
    public void testUpdateContentContentWithError() throws Exception {
        EditContentCommentRequest editContentCommentRequest = new EditContentCommentRequest(1l,1l, "Content", 1, 1);
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String json = ow.writeValueAsString(editContentCommentRequest);
        ContentComment contentContent = new ContentComment();
        contentContent.setContentId(1l);
        contentContent.setComment("Comment");
        Mockito.when(contentCommentService.edit(Mockito.any())).thenReturn(contentContent);
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post("/api/comment/update").header("Accept-Language", "vi").contentType(MediaType.APPLICATION_JSON).content(json)).andReturn();
        int status = mvcResult.getResponse().getStatus();
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), status);
    }

    /**
     * Method: updateContentContent(@Valid @RequestBody EditContentCommentRequest editContentCommentRequest, @RequestHeader(name = "Accept-Language", required = false) String languageCode, BindingResult errors, HttpServletRequest request)
     */
    @Test
    @DisplayName("testUpdateContentContentWithException")
    @WithMockUser(username = "admin", authorities = { "ADMIN", "USER" })
    public void testUpdateContentContentWithException() throws Exception {
        EditContentCommentRequest editContentCommentRequest = new EditContentCommentRequest(1l,1l, "Content", 1, 1);
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
    @DisplayName("testDeleteContentContentWithStatus200")
    @WithMockUser(username = "admin", authorities = { "ADMIN", "USER" })
    public void testDeleteContentContentWithStatus200() throws Exception {
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
    @DisplayName("testDeleteContentContentWithError")
    @WithMockUser(username = "admin", authorities = { "ADMIN", "USER" })
    public void testDeleteContentContentWithError() throws Exception {
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
    @DisplayName("testDeleteContentContentWithException")
    @WithMockUser(username = "admin", authorities = { "ADMIN", "USER" })
    public void testDeleteContentContentWithException() throws Exception {
        long id = 1;
        Mockito.when(contentCommentService.delete(Mockito.any())).thenThrow(new RuntimeException());
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post("/api/comment/delete/"+id).header("Accept-Language", "vi").contentType(MediaType.APPLICATION_JSON)).andReturn();
        int status = mvcResult.getResponse().getStatus();
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), status);
    }

} 
