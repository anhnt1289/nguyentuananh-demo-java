package com.base.common.service;

import com.base.common.model.User;
import com.base.common.payload.UserRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * @author : AnhNT
 * @since : 10/11/2021, Wed
 */
public interface UserService {
    int updateUser(UserRequest userRequest);
    int deleteUser(Long id);
    User getCurrentUser();
    User getCurrentUser(Long id);
    List<User> getAllUser();
    Page<User> getAllUserByPage(Pageable pageable);
    Page<User> find(Pageable pageable);
}
