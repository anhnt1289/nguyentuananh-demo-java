package com.main.api.mapper;

import com.base.common.mapper.EntityMapper;
import com.base.common.model.ContentComment;
import com.base.common.payload.AddContentCommentRequest;
import com.base.common.payload.EditContentCommentRequest;
import org.mapstruct.Mapper;

/**
 * @author : AnhNT
 * @since : 16/02/2023, Thu
 */
@Mapper(componentModel = "spring")
public interface ContentCommentRequestMapper extends EntityMapper<AddContentCommentRequest, ContentComment> {
    ContentComment toEntityEdit(EditContentCommentRequest editContentCommentRequest);
}
