package com.main.core.impl;

import com.base.common.constant.DefaultValue;
import com.base.common.model.Content;
import com.base.common.model.User;
import com.base.common.payload.AddContentRequest;
import com.base.common.payload.EditContentRequest;
import com.base.common.repository.ContentRepository;
import com.base.common.service.UserService;
import com.main.api.ApiBaseProjectApplication;
import com.main.api.mapper.ContentRequestMapper;
import com.main.api.service.impl.ContentServiceImpl;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * ContentServiceImpl Tester.
 *
 * @author AnhNT
 * @version 1.0
 * @since <pre>Feb 16, 2023</pre>
 */
@SpringBootTest(classes = ApiBaseProjectApplication.class)
@RunWith(SpringRunner.class)
public class ContentServiceImplTest {
    @Autowired
    ContentServiceImpl contentService;
    @MockBean
    private ContentRepository contentRepository;
    @MockBean
    private ContentRequestMapper contentRequestMapper;
    @MockBean
    private UserService userService;
    /**
     * Method: add(AddContentRequest addContentRequest)
     */
    @Test
    @DisplayName("testAdd")
    public void testAdd() throws Exception {
        AddContentRequest addContentRequest = new AddContentRequest( "contentTitle", "content", "thumbnail", 1 );
        Content content = new Content();
        content.setId(1l);
        content.setContent("content");
        User u = new User();
        u.setId(1l);
        Mockito.when(userService.getCurrentUser()).thenReturn(u);
        Mockito.when(contentRequestMapper.toEntity(Mockito.any())).thenReturn(content);
        Mockito.when(contentRepository.save(Mockito.any())).thenReturn(content);
        Content contentResult = contentService.add(addContentRequest);
        assertEquals(content, contentResult);
    }

    /**
     * Method: edit(EditContentRequest editContentRequest)
     */
    @Test
    @DisplayName("testEdit")
    public void testEdit() throws Exception {
        EditContentRequest editContentRequest = new EditContentRequest(1l,"contentTitle", "Content", "thunbal", 1);
        Content content = new Content();
        content.setContent("comment");
        content.setId(1l);
        User u = new User();
        u.setId(1l);
        Mockito.when(contentRequestMapper.toEntityEdit(Mockito.any())).thenReturn(content);
        Mockito.when(contentRepository.findById(Mockito.any())).thenReturn(Optional.of(content));
        Mockito.when(userService.getCurrentUser()).thenReturn(u);
        Content contentResult = contentService.edit(editContentRequest);
        assertEquals(null, contentResult);
    }

    /**
     * Method: edit(EditContentRequest editContentRequest)
     */
    @Test
    @DisplayName("testEditNotEmpty")
    public void testEditNotEmpty() throws Exception {
        EditContentRequest editContentRequest = new EditContentRequest(1l,"contentTitle", "Content", "thunbal", 1);
        Content content = new Content();
        content.setContent("comment");
        content.setId(1l);
        Mockito.when(contentRequestMapper.toEntityEdit(Mockito.any())).thenReturn(content);
        Mockito.when(contentRepository.findById(Mockito.any())).thenReturn(Optional.empty());
        Content contentResult = contentService.edit(editContentRequest);
        assertEquals(content, contentResult);
    }

    /**
     * Method: delete(Long id)
     */
    @Test
    @DisplayName("testDeleteWithOne")
    public void testDeleteWithOne() throws Exception {
        Long id = 1l;
        Content content = new Content();
        content.setContent("comment");
        content.setId(1l);
        Mockito.when(contentRepository.findById(Mockito.any())).thenReturn(Optional.of(content));
        int result = contentService.delete(id);
        assertEquals(DefaultValue.ONE_INT, result);
    }

    /**
     * Method: delete(Long id)
     */
    @Test
    @DisplayName("testDeleteWithZero")
    public void testDeleteWithZero() throws Exception {
        Long id = 1l;
        Mockito.when(contentRepository.findById(Mockito.any())).thenReturn(Optional.empty());
        int result = contentService.delete(id);
        assertEquals(DefaultValue.ZERO_INT, result);
    }

    /**
     * Method: find(Pageable pageable)
     */
    @Test
    @DisplayName("testFind")
    public void testFind() throws Exception {
        Pageable pageable = PageRequest.of(0,1);
        contentService.find(pageable);
    }
}
