package com.main.api.service.impl;

import com.base.common.constant.DefaultValue;
import com.base.common.constant.UserRoleEnum;
import com.base.common.constant.UserStatusEnum;
import com.base.common.constant.UserUpdateEnum;
import com.main.api.mapper.UserRequestMapper;
import com.base.common.model.User;
import com.base.common.payload.UserRequest;
import com.base.common.repository.UserRepository;
import com.base.common.service.UserService;
import com.base.common.util.Util;
import com.main.api.exception.ResourceNotFoundException;
import com.main.api.security.UserPrincipal;
import com.main.api.util.JwtTokenUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author : AnhNT
 * @since : 10/11/2021, Wed
 */
@Slf4j
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private UserRequestMapper userRequestMapper;

    @Autowired
    private UserCacheService userCacheService;


    /**
     * Load user by id
     *
     * @return user
     */
    @Override
    public User getCurrentUser(Long id) {
        User user = userCacheService.getInstance().get(id);
        if(Util.validate(user)){
            return user;
        }
        return userRepository.findByIdAndStatus(id, UserStatusEnum.ACTIVATE.intValue())
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", id));
    }

    /**
     * Load all user
     *
     * @return user
     */
    @Override
    public List<User> getAllUser() {
        return userRepository.findAll();
    }

    /**
     * Load user with paging
     *
     * @return user
     */
    @Override
    public Page<User> getAllUserByPage(Pageable pageable) {
        return userRepository.findAllByStatus(pageable, UserStatusEnum.ACTIVATE.intValue());
    }

    /**
     * Load current user
     *
     * @return user
     */
    @Override
    public User getCurrentUser() {
        UserPrincipal userPrincipal = jwtTokenUtil.getUserPrincipal();
        Long userId = userPrincipal.getId();
        User user = userCacheService.getInstance().get(userId);
        if(Util.validate(user)){
            return user;
        }
        return null;
    }

    /**
     * Delete user
     *
     * @return user
     */
    @Override
    public int deleteUser(Long id) {
        // Check permission
        User user = getCurrentUser();
        if (Util.validate(user) &&
                // don't update role user
            user.getRoleId().compareTo(UserRoleEnum.USER.getId()) != 0 &&
                // don't update myself
                user.getId().compareTo(id) != DefaultValue.ZERO_INT) {
            try {
                getCurrentUser(id);
                userRepository.updateStatus(id, UserStatusEnum.DELETE.intValue());
                return DefaultValue.ONE_INT;
            } catch (Exception e) {
            }
            // User change not found
            return DefaultValue.ZERO_INT;
        }
        // account has no permissions
        return DefaultValue.NEGATIVE_INT;
    }

    /**
     * Update user
     *
     * @return int
     */
    @Override
    public int updateUser(UserRequest userRequest) {
        User userReqMapper = userRequestMapper.toEntity(userRequest);
        User user = getCurrentUser();
        if(Util.validate(user)){
            if (Util.validate(userReqMapper.getEmail())) {
                boolean isExist = Boolean.FALSE;
                Set<Map.Entry<Long, User>> entrySet = userCacheService.getInstance().entrySet();
                for (Map.Entry<Long, User> mapping : entrySet) {
                    User u = mapping.getValue();
                    if (u.getId().compareTo(user.getId()) != DefaultValue.ZERO_INT &&
                        u.getEmail().equalsIgnoreCase(userReqMapper.getEmail())) {
                        isExist = Boolean.TRUE;
                        break;
                    }
                }
                if (isExist) {
                    return UserUpdateEnum.EMAIL_EXIST.intValue();
                }
                user.setEmail(userReqMapper.getEmail());
            }
            if (Util.validate(userReqMapper.getName())) {
                user.setName(userReqMapper.getName());
            }
            if (Util.validate(userReqMapper.getStatus())) {
                user.setStatus(userReqMapper.getStatus());
            }
            userRepository.save(user);
        }
        return UserUpdateEnum.SUCCESS.intValue();
    }

    /**
     * Load all user
     */
    @Override
    public Page<User> find(Pageable pageable) {
        Page<User> contentPage = userRepository.findAll(pageable);
        return contentPage;
    }
}
