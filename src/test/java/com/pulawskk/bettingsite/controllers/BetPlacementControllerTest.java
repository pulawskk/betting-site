package com.pulawskk.bettingsite.controllers;

import com.pulawskk.bettingsite.entities.User;
import com.pulawskk.bettingsite.enums.BetSlipType;
import com.pulawskk.bettingsite.models.BetSlipSentDto;
import com.pulawskk.bettingsite.models.SelectionDto;
import com.pulawskk.bettingsite.services.impl.BetSlipServiceImpl;
import com.pulawskk.bettingsite.services.impl.UserServiceImpl;
import com.pulawskk.bettingsite.services.impl.WalletServiceImpl;
import org.assertj.core.util.Lists;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigDecimal;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class BetPlacementControllerTest {

    @InjectMocks
    BetPlacementController betPlacementController;

    @Mock
    BetSlipServiceImpl betSlipService;

    @Mock
    UserServiceImpl userService;

    @Mock
    WalletServiceImpl walletService;

    MockMvc mockMvc;

    MockHttpSession mockHttpSession;

    BetSlipSentDto betSlipSentDto;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(betPlacementController).build();
        mockHttpSession = new MockHttpSession();
        betSlipSentDto = new BetSlipSentDto();
        SelectionDto selectionDto = new SelectionDto();
        selectionDto.setUniqueId("123asd");
        selectionDto.setCompetition("Premier League");
        selectionDto.setEventName("Chelsea v Arsenal");
        selectionDto.setMarketType("1X2");
        selectionDto.setOdd("1.37");
        selectionDto.setUserType("0");
        betSlipSentDto.setStake("11");
        betSlipSentDto.setTypes(Lists.newArrayList(BetSlipType.SINGLE));
        List<SelectionDto> selectionList = Lists.newArrayList(selectionDto);
        betSlipSentDto.setSelections(selectionList);
    }

    @Test
    void shouldRedirect_whenBetIsPlaced() throws Exception {
        //given
        String betSlipSentDtoJson = JSONObject.wrap(betSlipSentDto).toString();
        User user = new User();
        user.setName("karol");
        user.setId(1L);
        when(userService.userLoggedIn()).thenReturn(user);
        when(walletService.findBalanceForUser(user.getId())).thenReturn(new BigDecimal("1111"));

        //when
        mockMvc.perform(post("/settler/post")
                    .session(mockHttpSession)
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .content(betSlipSentDtoJson))
                .andExpect(status().is3xxRedirection());

        //then
        verify(userService, times(1)).userLoggedIn();
        verify(walletService, times(1)).findBalanceForUser(user.getId());
        verify(betSlipService, times(1)).saveBetSlip(anyList(), anyString(), anyList(), any());
    }
}