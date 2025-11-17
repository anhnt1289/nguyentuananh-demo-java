package com.main.api.mapper;

import com.base.common.mapper.EntityMapper;
import com.base.common.model.User;
import com.base.common.payload.UserRequest;
import org.mapstruct.Mapper;

/**
 * @author : AnhNT
 * @since : 10/11/2021, Wed
 */
@Mapper(componentModel = "spring")
public interface UserRequestMapper extends EntityMapper<UserRequest, User> {
}
