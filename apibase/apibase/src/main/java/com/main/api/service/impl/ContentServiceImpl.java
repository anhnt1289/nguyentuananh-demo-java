package com.main.api.service.impl;

import com.base.common.constant.DefaultValue;
import com.base.common.model.Content;
import com.base.common.model.User;
import com.base.common.payload.AddContentRequest;
import com.base.common.payload.EditContentRequest;
import com.base.common.repository.ContentRepository;
import com.base.common.service.UserService;
import com.main.api.mapper.ContentRequestMapper;
import com.main.api.service.ContentService;
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
public class ContentServiceImpl implements ContentService {


    @Autowired
    private ContentRepository contentRepository;

    @Autowired
    private ContentRequestMapper contentRequestMapper;

    @Autowired
    private UserService userService;

    /**
     * Add content
     */
    @Override
    public Content add(AddContentRequest addContentRequest) {
        Content content = contentRequestMapper.toEntity(addContentRequest);
        User user = userService.getCurrentUser();
        content.setCreator(user.getId());
        content = contentRepository.save(content);
        return content;
    }

    /**
     * Update content
     */
    @Override
    public Content edit(EditContentRequest editContentRequest) {
        Content content = contentRequestMapper.toEntityEdit(editContentRequest);
        Optional<Content> contentOptional = contentRepository.findById(content.getId());
        if (!contentOptional.isPresent()) {
            return content;
        }
        User user = userService.getCurrentUser();
        content.setCreator(user.getId());
        contentRepository.save(content);
        return null;
    }

    /**
     * Delete content
     *
     * @return 0: content not exist, 1: delete success
     */
    @Override
    public int delete(Long id) {
        Optional<Content> contentOptional = contentRepository.findById(id);
        if (!contentOptional.isPresent()) {
            // Content change not found
            return DefaultValue.ZERO_INT;
        }
        contentRepository.deleteById(id);
        return DefaultValue.ONE_INT;
    }

    /**
     * Load all content
     */
    @Override
    public Page<Content> find(Pageable pageable) {
        Page<Content> contentPage = contentRepository.findAll(pageable);
        return contentPage;
    }
}
