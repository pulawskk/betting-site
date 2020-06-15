package com.pulawskk.bettingsite.services.impl;

import com.pulawskk.bettingsite.entities.User;
import com.pulawskk.bettingsite.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    private static final String EMAIL_EXAMPLE = "example@gmail.com";


    @InjectMocks
    UserServiceImpl userService;

    @Mock
    UserRepository userRepository;

    @Mock
    Authentication authentication;

    @Mock
    SecurityContext securityContext;

    User user;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setId(11L);
        user.setActive(1);
        user.setName("karol");
        user.setLastName("drogba");
        user.setPassword("test");
        user.setEmail("karol@gmail.com");
    }

    @Test
    void shouldReturnUser_whenEmailIsPassed() {
        //given
        String email = "karol@gmail.com";
        when(userRepository.findByEmail(email)).thenReturn(user);

        //when
        User actualUser = userService.findByEmail(email);

        //then
        verify(userRepository, times(1)).findByEmail(email);
        assertThat(actualUser.getId(), is(user.getId()));
        assertThat(actualUser.getEmail(), is(email));
    }

    @Test
    void shouldReturnUser_whenNamIsPassed() {
        //given
        String name = "karol";
        when(userRepository.findByName(name)).thenReturn(user);

        //when
        User actualUser = userService.findByName(name);

        //then
        verify(userRepository, times(1)).findByName(name);
        assertThat(actualUser.getId(), is(user.getId()));
        assertThat(actualUser.getName(), is(name));
    }

    @Test
    void shouldReturnUser_whenUserIsCurrentLoggedIn() {
        //given
        mockSecurityContextHolder();
        user.setEmail(EMAIL_EXAMPLE);
        when(userRepository.findByEmail(EMAIL_EXAMPLE)).thenReturn(user);

        //when
        User actualUser = userService.userLoggedIn();

        //then
        verify(userRepository, times(1)).findByEmail(anyString());
        assertThat(actualUser.getId(), is(user.getId()));
        assertThat(actualUser.getEmail(), is(EMAIL_EXAMPLE));
    }

    /**
     * Record behaviour for SecurityContextHolder
     */
    private void mockSecurityContextHolder() {
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getName()).thenReturn(EMAIL_EXAMPLE);
        SecurityContextHolder.setContext(securityContext);
    }

    @Test
    void displayAuthName() {
        //given
        mockSecurityContextHolder();

        //when
        String actualUserEmail = userService.displayAuthName();

        //then
        assertThat(actualUserEmail, is(EMAIL_EXAMPLE));
    }
}