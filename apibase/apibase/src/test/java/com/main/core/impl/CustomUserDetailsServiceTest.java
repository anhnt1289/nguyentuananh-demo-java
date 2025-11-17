package com.main.core.impl;
import com.base.common.model.User;
import com.base.common.repository.UserRepository;
import com.main.api.ApiBaseProjectApplication;
import com.main.api.service.impl.CustomUserDetailsService;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * CustomUserDetailsService Tester.
 *
 * @author AnhNT
 * @version 1.0
 * @since <pre>Feb 16, 2023</pre>
 */
@SpringBootTest(classes = ApiBaseProjectApplication.class)
@RunWith(SpringRunner.class)
public class CustomUserDetailsServiceTest {

    @Autowired
    CustomUserDetailsService customUserDetailsService;
    @MockBean
    UserRepository userRepository;

    /**
     * Method: loadUserByUsername(String email)
     */
    @Test
    @DisplayName("testLoadUserByUsername")
    public void testLoadUserByUsername() throws Exception {
        String email = "xxxxx@gmail.com";
        User u = new User();
        u.setId(1l);
        u.setEmail(email);
        Mockito.when(userRepository.findByEmailAndStatus(Mockito.any(), Mockito.any())).thenReturn(Optional.of(u));
        UserDetails userDetails = customUserDetailsService.loadUserByUsername(email);
        assertEquals(email, userDetails.getUsername());
    }

    /**
     * Method: loadUserByUsername(String email)
     */
    @Test
    @DisplayName("testLoadUserByUsernameWithNotFound")
    public void testLoadUserByUsernameWithNotFound() throws Exception {
        String email = "xxxxx@gmail.com";
        User u = new User();
        u.setId(1l);
        u.setEmail(email);
        Mockito.when(userRepository.findByEmailAndStatus(Mockito.any(), Mockito.any())).thenReturn(Optional.empty());
        boolean isError = false;
        try {
            customUserDetailsService.loadUserByUsername(email);
        } catch (Exception e) {
            isError = true;
        }
        assertEquals(true, isError);
    }

    /**
     * Method: loadUserById(Long id, Map<String, Object> attributes)
     */
    @Test
    @DisplayName("testLoadUserById")
    public void testLoadUserById() throws Exception {
        String email = "xxxxx@gmail.com";
        Map<String, Object> attributes = new HashMap<>();
        attributes.put("jwtKey", "jwt");
        attributes.put("ip", "172.0.0.1");
        User u = new User();
        u.setId(1l);
        u.setEmail(email);
        Mockito.when(userRepository.findByIdAndStatus(Mockito.any(), Mockito.any())).thenReturn(Optional.of(u));
        UserDetails userDetails = customUserDetailsService.loadUserById(1l, attributes);
        assertEquals(email, userDetails.getUsername());
    }

    /**
     * Method: loadUserById(Long id, Map<String, Object> attributes)
     */
    @Test
    @DisplayName("testLoadUserByIdWithNotFound")
    public void testLoadUserByIdWithNotFound() throws Exception {
        String email = "xxxxx@gmail.com";
        Map<String, Object> attributes = new HashMap<>();
        attributes.put("jwtKey", "jwt");
        attributes.put("ip", "172.0.0.1");
        User u = new User();
        u.setId(1l);
        u.setEmail(email);
        Mockito.when(userRepository.findByIdAndStatus(Mockito.any(), Mockito.any())).thenReturn(Optional.empty());
        boolean isError = false;
        try {
            customUserDetailsService.loadUserById(1l, attributes);
        } catch (Exception e) {
            isError = true;
        }
        assertEquals(true, isError);
    }
}
