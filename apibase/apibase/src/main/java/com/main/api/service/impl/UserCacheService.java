package com.main.api.service.impl;

import com.base.common.constant.UserStatusEnum;
import com.base.common.model.User;
import com.base.common.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author : AnhNT
 * @since : 11/10/2022, Thu
 */
@Service
public class UserCacheService {
    private static final Logger logger = LoggerFactory.getLogger(UserCacheService.class);

    private static ConcurrentHashMap<Long, User> allUser;

    @Autowired
    UserRepository userRepository;

    @PostConstruct
    public void init() {
        getInstance();
        initAllUsers();
    }

    public ConcurrentHashMap<Long, User> getInstance() {
        if (allUser == null) {
            allUser = new ConcurrentHashMap<>();
        }
        return allUser;
    }

    private Long getLastUserId(List<User> users) {
        // If passed entry is null or empty, return 0 ( This handles the first iteration case )
        if ( users == null || users.isEmpty())
            return 0l;

        // Do the logic to sort the users list by user_id of each
        // User object
        // Return the last entry
        return users.get(users.size() -1).getId();
    }

    private void initAllUsers() {
        // apply this peeking method to retrieve the records for users
        try {
            List<User> users = new ArrayList();
            long lastUserId = 0;
            int size = 50;
            while ( true ) {
                // Create a PageRequest object that will be passed as Pageable interface to repo
                // Note that here we are setting 0 as the offset
                Pageable pageRequest = PageRequest.of(0, size);
                // Get the lastUserId
                lastUserId = getLastUserId(users);
                // Get the data from the database
                users = userRepository.findByStatusAndIdGreaterThanOrderByIdAsc(UserStatusEnum.ACTIVATE.intValue(), lastUserId, pageRequest).getContent();
                // Check if data is there
                if ( users == null || users.isEmpty()) {
                    break;
                }
                // Do the processing
                for (User u: users) {
                    save(u);
                }
            }
            logger.error("done");
        } catch (Exception ex) {
            logger.error(ex.getMessage());
        }
    }

    private boolean save(User user) {
        try {
            removeUserByUserId(user.getEmail());
            allUser.put(user.getId(), user);
            return true;
        } catch (Exception ex) {
            logger.error(ex.getMessage());
            return false;
        }
    }

    private boolean removeUserByUserId(String email) {
        if (allUser.containsKey(email)) {
            User user = allUser.get(email);
            if (user == null) {
                return true;
            }
            try {
                allUser.remove(email);
            } catch (Exception ex) {
                logger.error(ex.getMessage());
                return false;
            }
            return true;
        }
        return false;
    }
}
