package com.main.core.impl;

import com.base.common.constant.DefaultValue;
import com.base.common.model.Content;
import com.base.common.model.ContentComment;
import com.base.common.model.User;
import com.base.common.payload.AddContentCommentRequest;
import com.base.common.payload.EditContentCommentRequest;
import com.base.common.repository.ContentCommentRepository;
import com.base.common.repository.ContentRepository;
import com.base.common.service.UserService;
import com.main.api.ApiBaseProjectApplication;
import com.main.api.mapper.ContentCommentRequestMapper;
import com.main.api.service.impl.BlackListRedisServiceImpl;
import com.main.api.service.impl.ContentCommentServiceImpl;
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
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

/**
 * ContentCommentServiceImpl Tester.
 *
 * @author AnhNT
 * @version 1.0
 * @since <pre>Feb 16, 2023</pre>
 */
@SpringBootTest(classes = ApiBaseProjectApplication.class)
@RunWith(SpringRunner.class)
public class ContentCommentServiceImplTest {

    @Autowired
    ContentCommentServiceImpl contentCommentService;
    @MockBean
    private UserService userService;
    @MockBean
    private ContentRepository contentRepository;
    @MockBean
    private ContentCommentRequestMapper contentCommentRequestMapper;
    @MockBean
    private ContentCommentRepository contentCommentRepository;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    /**
     * Method: add(AddContentCommentRequest addContentCommnetRequest)
     */
    @Test
    @DisplayName("testAdd")
    public void testAdd() throws Exception {
        ContentComment contentComment = new ContentComment();
        contentComment.setComment("comment");
        contentComment.setContentId(1l);
        AddContentCommentRequest addContentCommentRequest = new AddContentCommentRequest(1l, "Content", 1, 1);
        User u = new User();
        u.setId(1l);
        Content content = new Content();
        content.setId(1l);
        content.setContent("content");
        Mockito.when(userService.getCurrentUser()).thenReturn(u);
        Mockito.when(contentRepository.findById(Mockito.any())).thenReturn(Optional.of(content));
        Mockito.when(contentCommentRequestMapper.toEntity(Mockito.any())).thenReturn(contentComment);
        ContentComment contentCommentResult = contentCommentService.add(addContentCommentRequest);
        assertEquals(null, contentCommentResult);
    }

    /**
     * Method: add(AddContentCommentRequest addContentCommnetRequest)
     */
    @Test
    @DisplayName("testAddThrownUser")
    public void testAddThrownUser() throws Exception {
        ContentComment contentComment = new ContentComment();
        contentComment.setComment("comment");
        AddContentCommentRequest addContentCommentRequest = new AddContentCommentRequest(1l, "Content", 1, 1);
        Content content = new Content();
        content.setId(1l);
        content.setContent("content");
        Mockito.when(userService.getCurrentUser()).thenReturn(null);
        Mockito.when(contentRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(content));
        Mockito.when(contentCommentRequestMapper.toEntity(Mockito.any())).thenReturn(contentComment);
        boolean isError = false;
        try {
            contentCommentService.add(addContentCommentRequest);
        } catch (Exception e) {
            isError = true;
        }
        assertEquals(true, isError);
    }

    /**
     * Method: add(AddContentCommentRequest addContentCommnetRequest)
     */
    @Test
    @DisplayName("testAddThrownContent")
    public void testAddThrownContent() throws Exception {
        ContentComment contentComment = new ContentComment();
        contentComment.setComment("comment");
        AddContentCommentRequest addContentCommentRequest = new AddContentCommentRequest(1l, "Content", 1, 1);
        User u = new User();
        u.setId(1l);
        Content content = new Content();
        content.setId(1l);
        content.setContent("content");
        Mockito.when(userService.getCurrentUser()).thenReturn(u);
        Mockito.when(contentRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(content));
        Mockito.when(contentCommentRequestMapper.toEntity(Mockito.any())).thenReturn(contentComment);
        boolean isError = false;
        try {
            contentCommentService.add(addContentCommentRequest);
        } catch (Exception e) {
            isError = true;
        }
        assertEquals(true, isError);
    }

    /**
     * Method: edit(EditContentCommentRequest editContentCommentRequest)
     */
    @Test
    @DisplayName("testEdit")
    public void testEdit() throws Exception {
        EditContentCommentRequest editContentCommentRequest = new EditContentCommentRequest(1l,1l, "Content", 1, 1);
        ContentComment contentComment = new ContentComment();
        contentComment.setComment("comment");
        contentComment.setContentId(1l);
        User u = new User();
        u.setId(1l);
        Mockito.when(contentCommentRequestMapper.toEntityEdit(Mockito.any())).thenReturn(contentComment);
        Mockito.when(contentCommentRepository.findById(Mockito.any())).thenReturn(Optional.of(contentComment));
        Mockito.when(userService.getCurrentUser()).thenReturn(u);
        ContentComment contentCommentResult = contentCommentService.edit(editContentCommentRequest);
        assertEquals(null, contentCommentResult);
    }

    /**
     * Method: edit(EditContentCommentRequest editContentCommentRequest)
     */
    @Test
    @DisplayName("testEditException")
    public void testEditException() throws Exception {
        EditContentCommentRequest editContentCommentRequest = new EditContentCommentRequest(1l,1l, "Content", 1, 1);
        ContentComment contentComment = new ContentComment();
        contentComment.setComment("comment");
        contentComment.setContentId(1l);
        User u = new User();
        u.setId(1l);
        Mockito.when(contentCommentRequestMapper.toEntityEdit(Mockito.any())).thenReturn(contentComment);
        Mockito.when(contentCommentRepository.findById(Mockito.any())).thenReturn(Optional.empty());
        ContentComment contentCommentResult = contentCommentService.edit(editContentCommentRequest);
        assertEquals(contentComment, contentCommentResult);
    }

    /**
     * Method: delete(Long id)
     */
    @Test
    @DisplayName("testDeleteWithOne")
    public void testDeleteWithOne() throws Exception {
        Long id = 1l;
        ContentComment contentComment = new ContentComment();
        contentComment.setComment("comment");
        contentComment.setContentId(1l);
        Mockito.when(contentCommentRepository.findById(Mockito.any())).thenReturn(Optional.of(contentComment));
        int result = contentCommentService.delete(id);
        assertEquals(DefaultValue.ONE_INT, result);
    }

    /**
     * Method: delete(Long id)
     */
    @Test
    @DisplayName("testDeleteWithZero")
    public void testDeleteWithZero() throws Exception {
        Long id = 1l;
        Mockito.when(contentCommentRepository.findById(Mockito.any())).thenReturn(Optional.empty());
        int result = contentCommentService.delete(id);
        assertEquals(DefaultValue.ZERO_INT, result);
    }

    /**
     * Method: find(Pageable pageable)
     */
    @Test
    @DisplayName("testFind")
    public void testFind() throws Exception {
        Pageable pageable = PageRequest.of(0,1);
        contentCommentService.find(pageable);
    }
}
