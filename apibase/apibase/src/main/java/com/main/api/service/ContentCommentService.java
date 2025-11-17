package com.main.api.service;

import com.base.common.model.ContentComment;
import com.base.common.payload.AddContentCommentRequest;
import com.base.common.payload.EditContentCommentRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ContentCommentService {
    ContentComment add(AddContentCommentRequest addContentCommentRequest);
    ContentComment edit(EditContentCommentRequest editContentCommentRequest);
    int delete(Long id);
    Page<ContentComment> find(Pageable pageable);
}
