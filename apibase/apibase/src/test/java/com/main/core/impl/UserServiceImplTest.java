package com.main.core.impl;

import com.base.common.constant.DefaultValue;
import com.base.common.constant.UserUpdateEnum;
import com.base.common.model.User;
import com.base.common.payload.UserRequest;
import com.base.common.repository.UserRepository;
import com.main.api.ApiBaseProjectApplication;
import com.main.api.mapper.UserRequestMapper;
import com.main.api.security.UserPrincipal;
import com.main.api.service.impl.UserCacheService;
import com.main.api.service.impl.UserServiceImpl;
import com.main.api.util.JwtTokenUtil;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * UserServiceImpl Tester.
 *
 * @author AnhNT
 * @version 1.0
 * @since <pre>Feb 16, 2023</pre>
 */
@SpringBootTest(classes = ApiBaseProjectApplication.class)
@RunWith(SpringRunner.class)
public class UserServiceImplTest {
    @Autowired
    UserServiceImpl userService;
    @MockBean
    private UserRepository userRepository;
    @MockBean
    private JwtTokenUtil jwtTokenUtil;
    @MockBean
    private UserRequestMapper userRequestMapper;
    @MockBean
    private UserCacheService userCacheService;


    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    /**
     * Method: getCurrentUser(Long id)
     */
    @Test
    @DisplayName("testGetCurrentUserId")
    public void testGetCurrentUserId() throws Exception {
        String email = "xxxxx@gmail.com";
        Long id = 1l;
        User u = new User();
        u.setId(1l);
        u.setEmail(email);
        Mockito.when(userRepository.findByIdAndStatus(Mockito.any(), Mockito.any())).thenReturn(Optional.of(u));
        ConcurrentHashMap<Long, User> allUser = new ConcurrentHashMap<>();
        allUser.put(u.getId(), u);
        UserPrincipal userPrincipal = UserPrincipal.create(u);
        Mockito.when(jwtTokenUtil.getUserPrincipal()).thenReturn(userPrincipal);
        Mockito.when(userCacheService.getInstance()).thenReturn(allUser);
        User user = userService.getCurrentUser(id);
        assertEquals(u, user);
    }
    /**
     * Method: getCurrentUser(Long id)
     */
    @Test
    @DisplayName("testGetCurrentUserIdWithEmpty")
    public void testGetCurrentUserIdWithEmpty() throws Exception {
        String email = "xxxxx@gmail.com";
        Long id = 1l;
        Mockito.when(userRepository.findByIdAndStatus(Mockito.any(), Mockito.any())).thenReturn(Optional.empty());

        boolean isError = false;
        try {
            userService.getCurrentUser(id);
        } catch (Exception e) {
            isError = true;
        }
        assertEquals(true, isError);
    }

    /**
     * Method: getAllUser()
     */
    @Test
    @DisplayName("testGetAllUser")
    public void testGetAllUser() throws Exception {
        int size = 10;
        List<User> userList = new ArrayList<>();
        for (int i = 1; i < size; i++) {
            User u = new User();
            u.setId(Long.valueOf(i));
            userList.add(u);
        }
        Mockito.when(userRepository.findAll()).thenReturn(userList);
        List<User> users = userService.getAllUser();
        assertEquals(userList, users);
    }

    /**
     * Method: getAllUserByPage(Pageable pageable)
     */
    @Test
    @DisplayName("testGetAllUserByPage")
    public void testGetAllUserByPage() throws Exception {
        int size = 10;
        List<User> userList = new ArrayList<>();
        for (int i = 1; i < size; i++) {
            User u = new User();
            u.setId(Long.valueOf(i));
            userList.add(u);
        }
        Page<User> userPage =  new PageImpl<>(userList);
        int page = 0;
        int limit = 10;
        Pageable pageable = PageRequest.of(page, limit);
        Mockito.when(userRepository.findAllByStatus(Mockito.any(), Mockito.any())).thenReturn(userPage);
        Page<User>  users = userService.getAllUserByPage(pageable);
        assertEquals(userPage, users);
    }

    /**
     * Method: getCurrentUser()
     */
    @Test
    @DisplayName("testGetCurrentUser")
    public void testGetCurrentUser() throws Exception {
//        MockHttpServletRequest request = new MockHttpServletRequest();
//        Authentication authentication = mock(Authentication.class);
//        request.setUserPrincipal(authentication);
        User u = new User();
        u.setId(1l);
        u.setEmail("xxxxx@gmail.com");
        u.setRoleId(1);
        u.setEmailVerified(true);
        u.setImageUrl("image");
        u.setName("Vân");
        u.setPassword("123456");
        u.setPasswordConfirm("123456");
        u.setStatus(1);
        ConcurrentHashMap<Long, User> allUser = new ConcurrentHashMap<>();
        allUser.put(u.getId(), u);
        UserPrincipal userPrincipal = UserPrincipal.create(u);
        Mockito.when(jwtTokenUtil.getUserPrincipal()).thenReturn(userPrincipal);
        Mockito.when(userCacheService.getInstance()).thenReturn(allUser);
        User userResult = userService.getCurrentUser();
        assertEquals(userResult, u);
    }

    /**
     * Method: deleteUser(Long id)
     */
    @Test
    @DisplayName("testDeleteUserWithNegative")
    public void testDeleteUserWithNegative() throws Exception {
        User u = new User();
        u.setId(1l);
        u.setEmail("xxxxx@gmail.com");
        u.setRoleId(1);
        u.setEmailVerified(true);
        u.setImageUrl("image");
        u.setName("Vân");
        u.setPassword("123456");
        u.setPasswordConfirm("123456");
        u.setStatus(1);
        ConcurrentHashMap<Long, User> allUser = new ConcurrentHashMap<>();
        allUser.put(u.getId(), u);
        UserPrincipal userPrincipal = UserPrincipal.create(u);
        Mockito.when(jwtTokenUtil.getUserPrincipal()).thenReturn(userPrincipal);
        Mockito.when(userCacheService.getInstance()).thenReturn(allUser);
        int deleteResult = userService.deleteUser(1l);
        assertEquals(deleteResult, DefaultValue.NEGATIVE_INT);
    }

    /**
     * Method: deleteUser(Long id)
     */
    @Test
    @DisplayName("testDeleteUserWithZero")
    public void testDeleteUserWithZero() throws Exception {
        User u = new User();
        u.setId(1l);
        u.setEmail("xxxxx@gmail.com");
        u.setRoleId(1);
        u.setEmailVerified(true);
        u.setImageUrl("image");
        u.setName("Vân");
        u.setPassword("123456");
        u.setPasswordConfirm("123456");
        u.setStatus(1);
        ConcurrentHashMap<Long, User> allUser = new ConcurrentHashMap<>();
        allUser.put(u.getId(), u);
        UserPrincipal userPrincipal = UserPrincipal.create(u);
        Mockito.when(jwtTokenUtil.getUserPrincipal()).thenReturn(userPrincipal);
        Mockito.when(userCacheService.getInstance()).thenReturn(allUser);
        Mockito.when(userRepository.findByIdAndStatus(Mockito.any(), Mockito.any())).thenReturn(null);
        int deleteResult = userService.deleteUser(2l);
        assertEquals(deleteResult, DefaultValue.ZERO_INT);
    }


    /**
     * Method: deleteUser(Long id)
     */
    @Test
    @DisplayName("testDeleteUserWithOne")
    public void testDeleteUserWithOne() throws Exception {
        User u = new User();
        u.setId(1l);
        u.setEmail("xxxxx@gmail.com");
        u.setRoleId(1);
        u.setEmailVerified(true);
        u.setImageUrl("image");
        u.setName("Vân");
        u.setPassword("123456");
        u.setPasswordConfirm("123456");
        u.setStatus(1);
        ConcurrentHashMap<Long, User> allUser = new ConcurrentHashMap<>();
        allUser.put(u.getId(), u);
        UserPrincipal userPrincipal = UserPrincipal.create(u);
        Mockito.when(jwtTokenUtil.getUserPrincipal()).thenReturn(userPrincipal);
        Mockito.when(userCacheService.getInstance()).thenReturn(allUser);
        Mockito.when(userRepository.findByIdAndStatus(Mockito.any(), Mockito.any())).thenReturn(Optional.of(u));
        int deleteResult = userService.deleteUser(2l);
        assertEquals(deleteResult, DefaultValue.ONE_INT);
    }

    /**
     * Method: updateUser(UserRequest userRequest)
     */
    @Test
    @DisplayName("testDeleteUserWithSuccess")
    public void testDeleteUserWithSuccess() throws Exception {
        User u = new User();
        u.setId(1l);
        u.setEmail("xxxxx@gmail.com");
        u.setRoleId(1);
        u.setEmailVerified(true);
        u.setImageUrl("image");
        u.setName("Vân");
        u.setPassword("123456");
        u.setPasswordConfirm("123456");
        u.setStatus(1);
        Mockito.when(userRequestMapper.toEntity(Mockito.any())).thenReturn(u);
        ConcurrentHashMap<Long, User> allUser = new ConcurrentHashMap<>();
        allUser.put(u.getId(), u);
        UserPrincipal userPrincipal = UserPrincipal.create(u);
        Mockito.when(jwtTokenUtil.getUserPrincipal()).thenReturn(userPrincipal);
        Mockito.when(userCacheService.getInstance()).thenReturn(allUser);

        UserRequest userRequest =  UserRequest.builder().email("xxxxx@gmail.com").name("Nguyen Van A").status(1).build();
        int updateResult = userService.updateUser(userRequest);
        assertEquals(UserUpdateEnum.SUCCESS.intValue(), updateResult);
    }

    /**
     * Method: updateUser(UserRequest userRequest)
     */
    @Test
    @DisplayName("testDeleteUserWithExist")
    public void testDeleteUserWithExist() throws Exception {
        User u = new User();
        u.setId(1l);
        u.setEmail("xxxxx@gmail.com");
        u.setRoleId(1);
        u.setEmailVerified(true);
        u.setImageUrl("image");
        u.setName("Vân");
        u.setPassword("123456");
        u.setPasswordConfirm("123456");
        u.setStatus(1);
        User u1 = new User();
        u1.setId(2l);
        u1.setEmail("xxxxxx@gmail.com");
        u1.setRoleId(2);
        u1.setEmailVerified(true);
        u1.setImageUrl("image2");
        u1.setName("Vân2");
        u1.setPassword("123456");
        u1.setPasswordConfirm("123456");
        u1.setStatus(2);
        Mockito.when(userRequestMapper.toEntity(Mockito.any())).thenReturn(u);
        ConcurrentHashMap<Long, User> allUser = new ConcurrentHashMap<>();
        allUser.put(u.getId(), u);
        allUser.put(u1.getId(), u1);
        UserPrincipal userPrincipal = UserPrincipal.create(u1);
        Mockito.when(jwtTokenUtil.getUserPrincipal()).thenReturn(userPrincipal);
        Mockito.when(userCacheService.getInstance()).thenReturn(allUser);

        UserRequest userRequest =  UserRequest.builder().email("xxxxx@gmail.com").name("Nguyen Van A").status(1).build();
        int updateResult = userService.updateUser(userRequest);
        assertEquals(UserUpdateEnum.EMAIL_EXIST.intValue(), updateResult);
    }

    /**
     * Method: find(Pageable pageable)
     */
    @Test
    @DisplayName("testFind")
    public void testFind() throws Exception {
        int size = 10;
        List<User> userList = new ArrayList<>();
        for (int i = 1; i < size; i++) {
            User u = new User();
            u.setId(Long.valueOf(i));
            userList.add(u);
        }
        Page<User> userPage =  new PageImpl<>(userList);
        int page = 0;
        int limit = 10;
        Pageable pageable = PageRequest.of(page, limit);
        Mockito.when(userRepository.findAll(pageable)).thenReturn(userPage);
        Page<User>  users = userService.find(pageable);
        assertEquals(userPage, users);
    }
}
