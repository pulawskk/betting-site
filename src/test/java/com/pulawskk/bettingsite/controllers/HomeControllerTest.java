package com.pulawskk.bettingsite.controllers;

import com.pulawskk.bettingsite.entities.User;
import com.pulawskk.bettingsite.services.impl.UserServiceImpl;
import com.pulawskk.bettingsite.services.impl.WalletServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigDecimal;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class HomeControllerTest {

    @InjectMocks
    HomeController homeController;

    @Mock
    UserServiceImpl userService;

    @Mock
    WalletServiceImpl walletService;

    MockMvc mockMvc;
    MockHttpSession mockHttpSession;

    User user;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(homeController).build();
        mockHttpSession = new MockHttpSession();

        user = new User();
        user.setId(1L);
        user.setName("karol");
    }

    @Test
    void shouldGenerateMainPageDecorated_whenHomePageIsCalled() throws Exception {
        //given
        when(userService.userLoggedIn()).thenReturn(user);
        when(walletService.findBalanceForUser(user.getId())).thenReturn(new BigDecimal("1111"));

        //when
        mockMvc.perform(get("/mainBoard"))
                .andExpect(status().isOk())
                .andExpect(view().name("mainPageDecorated"));

        //then
        verify(walletService, times(1)).findBalanceForUser(user.getId());
    }

    @Test
    void shouldGenerateWelcomePageDecorated_whenWelcomeBoardIsCalled() throws Exception {
        mockMvc.perform(get("/welcomeBoard"))
                .andExpect(status().isOk())
                .andExpect(view().name("welcomePageDecorated"));
    }

    @Disabled
    void shouldGeneratedLoginPageView_whenLoginCheckIsCalled() throws Exception {
        //given
        when(userService.displayAuthName()).thenReturn(user.getName());

        //when
        mockMvc.perform(get("/login"))
                .andExpect(status().isOk())
                .andExpect(view().name("login"));

        //then
        verify(userService, times(1)).displayAuthName();
    }

    @Test
    void shouldGeneratedLogoutPageView_whenLogoutIsCalled() throws Exception {
        //given
        when(userService.displayAuthName()).thenReturn(user.getName());

        //when
        mockMvc.perform(get("/logout"))
                .andExpect(status().isOk())
                .andExpect(view().name("homeView"));

        //then
        verify(userService, times(1)).displayAuthName();
    }
}