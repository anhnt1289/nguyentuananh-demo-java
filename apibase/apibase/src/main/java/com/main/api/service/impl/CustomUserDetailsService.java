package com.main.api.service.impl;

import com.base.common.constant.UserStatusEnum;
import com.base.common.model.User;
import com.base.common.repository.UserRepository;
import com.main.api.exception.ResourceNotFoundException;
import com.main.api.security.UserPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

/**
 * @author : AnhNT
 * @since : 10/11/2021, Wed
 */
@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    /**
     * Load user by email
     *
     * @return user
     */
    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String email)
            throws UsernameNotFoundException {
        User user = userRepository.findByEmailAndStatus(email, UserStatusEnum.ACTIVATE.intValue())
                .orElseThrow(() ->
                        new UsernameNotFoundException("User not found with email : " + email)
        );

        return UserPrincipal.create(user);
    }

    /**
     * Load user by id
     *
     * @return user
     */
    @Transactional(readOnly = true)
    public UserDetails loadUserById(Long id, Map<String, Object> attributes) {
        User user = userRepository.findByIdAndStatus(id, UserStatusEnum.ACTIVATE.intValue()).orElseThrow(
                () -> new ResourceNotFoundException("User", "id", id)
        );

        return UserPrincipal.create(user, attributes);
    }
}