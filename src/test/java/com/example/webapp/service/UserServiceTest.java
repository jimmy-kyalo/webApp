package com.example.webapp.service;

import com.example.webapp.model.User;
import com.example.webapp.repository.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.junit4.SpringRunner;

import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
public class UserServiceTest {
    @MockBean
    private UserRepository userRepository;

    private UserService userService;

    @Before
    public void init(){
        this.userService = new UserService(userRepository);
    }

    @Test
    public void shouldReturn1Comment(){
        // given
        User user = new User();
        user.setUsername("jimmy");
        user.setPassword("jim123");
        user.setRole("USER");
        when(userRepository.findByUsername("jimmy")).thenReturn(user);

        // when
        UserDetails actual = userService.loadUserByUsername("jimmy");

        // then
        verify(userRepository, times(1)).findByUsername("jimmy");
    }
}
