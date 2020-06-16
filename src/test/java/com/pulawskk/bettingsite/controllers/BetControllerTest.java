package com.pulawskk.bettingsite.controllers;

import com.pulawskk.bettingsite.entities.BetSlip;
import com.pulawskk.bettingsite.entities.User;
import com.pulawskk.bettingsite.enums.BetSlipType;
import com.pulawskk.bettingsite.services.impl.BetSlipServiceImpl;
import com.pulawskk.bettingsite.services.impl.UserServiceImpl;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static java.time.LocalDateTime.now;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class BetControllerTest {

    @InjectMocks
    BetController betController;

    @Mock
    BetSlipServiceImpl betSlipService;

    @Mock
    UserServiceImpl userService;

    MockMvc mockMvc;

    User user;
    List<BetSlip> betSlipList;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(betController).build();

        user = new User();
        user.setId(1L);
        user.setName("karol");
        when(userService.userLoggedIn()).thenReturn(user);

        BetSlip betSlip = BetSlip.builder()
                .user(user)
                .created(now())
                .betSlipType(BetSlipType.SINGLE)
                .build();

        betSlipList = Lists.newArrayList(betSlip);
    }

    @Test
    void shouldPassListOfActiveBetSlipToModel_whenStatusIsActive() throws Exception {
        //given
        final String status = "active";
        when(betSlipService.betSlipsActiveForUser(user.getId())).thenReturn(betSlipList);

        //when
        mockMvc.perform(get("/betSlips/list/" + status))
                .andExpect(status().isOk())
                .andExpect(view().name("displayBetSlipsDecorated"))
                .andExpect(model().attributeExists("betSlips"));

        //then
        verify(betSlipService, times(1)).betSlipsActiveForUser(user.getId());
        verify(betSlipService, never()).betSlipsResultedForUser(user.getId());
    }

    @Test
    void shouldPassListOfResultedBetSlipToModel_whenStatusIsResulted() throws Exception {
        //given
        final String status = "resulted";
        when(betSlipService.betSlipsResultedForUser(user.getId())).thenReturn(betSlipList);

        //when
        mockMvc.perform(get("/betSlips/list/" + status))
                .andExpect(status().isOk())
                .andExpect(view().name("displayBetSlipsDecorated"))
                .andExpect(model().attributeExists("betSlips"));

        //then
        verify(betSlipService, never()).betSlipsActiveForUser(user.getId());
        verify(betSlipService, times(1)).betSlipsResultedForUser(user.getId());
    }
}