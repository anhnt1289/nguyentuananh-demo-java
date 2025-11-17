package com.main.api.service.impl;

import com.base.common.constant.DefaultValue;
import com.base.common.model.Content;
import com.base.common.model.ContentComment;
import com.base.common.model.User;
import com.base.common.payload.AddContentCommentRequest;
import com.base.common.payload.EditContentCommentRequest;
import com.base.common.repository.ContentCommentRepository;
import com.base.common.repository.ContentRepository;
import com.base.common.service.UserService;
import com.main.api.exception.ResourceNotFoundException;
import com.main.api.mapper.ContentCommentRequestMapper;
import com.main.api.service.ContentCommentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * @author : AnhNT
 * @since : 16/02/2023, Thu
 */
@Slf4j
@Service
public class ContentCommentServiceImpl implements ContentCommentService {


    @Autowired
    private ContentCommentRepository contentCommentRepository;

    @Autowired
    private ContentCommentRequestMapper contentCommentRequestMapper;

    @Autowired
    private UserService userService;

    @Autowired
    private ContentRepository contentRepository;

    /**
     * Add Comment
     */
    @Override
    public ContentComment add(AddContentCommentRequest addContentCommnetRequest) {
        ContentComment contentComment = contentCommentRequestMapper.toEntity(addContentCommnetRequest);
        User user = userService.getCurrentUser();
        contentComment.setCreator(user.getId());
        if (user == null) {
            throw new ResourceNotFoundException("User", "id", null);
        }
        boolean isExistContent = isValidation(contentComment.getContentId());
        if (!isExistContent) {
            throw new ResourceNotFoundException("Content", "id", contentComment.getContentId());
        }
        contentComment = contentCommentRepository.save(contentComment);
        return contentComment;
    }

    /**
     * Update Comment
     */
    @Override
    public ContentComment edit(EditContentCommentRequest editContentCommentRequest) {
        ContentComment contentComment = contentCommentRequestMapper.toEntityEdit(editContentCommentRequest);
        Optional<ContentComment> optionalContentComment = contentCommentRepository.findById(contentComment.getId());
        if (!optionalContentComment.isPresent()) {
            return contentComment;
        }
        User user = userService.getCurrentUser();
        contentComment.setCreator(user.getId());
        contentCommentRepository.save(contentComment);
        return null;
    }

    /**
     * Delete comment
     *
     * @return 0: content not exist, 1: delete success
     */
    @Override
    public int delete(Long id) {
        Optional<ContentComment> optionalContentComment = contentCommentRepository.findById(id);
        if (!optionalContentComment.isPresent()) {
            // Comment change not found
            return DefaultValue.ZERO_INT;
        }
        contentCommentRepository.deleteById(id);
        return DefaultValue.ONE_INT;
    }

    /**
     * Load all comment
     */
    @Override
    public Page<ContentComment> find(Pageable pageable) {
        Page<ContentComment> contentCommentPagePage = contentCommentRepository.findAll(pageable);
        return contentCommentPagePage;
    }

    /**
     * Check exist content
     */
    private boolean isValidation(Long id) {
        try {
            Optional<Content> optionalContent = contentRepository.findById(id);
            if (optionalContent.isPresent()) {
                return true;
            }
        } catch (Exception e) {
        }
        return false;
    }
}
