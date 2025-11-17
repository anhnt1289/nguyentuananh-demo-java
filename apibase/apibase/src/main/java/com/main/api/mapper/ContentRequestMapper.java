package com.main.api.mapper;

import com.base.common.mapper.EntityMapper;
import com.base.common.model.Content;
import com.base.common.payload.AddContentRequest;
import com.base.common.payload.EditContentRequest;
import org.mapstruct.Mapper;

/**
 * @author : AnhNT
 * @since : 16/02/2023, Thu
 */
@Mapper(componentModel = "spring")
public interface ContentRequestMapper extends EntityMapper<AddContentRequest, Content> {
    Content toEntityEdit(EditContentRequest editContentRequest);
}
