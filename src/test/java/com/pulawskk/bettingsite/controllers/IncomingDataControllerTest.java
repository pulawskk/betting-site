package com.pulawskk.bettingsite.controllers;

import com.pulawskk.bettingsite.models.GameDto;
import com.pulawskk.bettingsite.models.ResultDto;
import com.pulawskk.bettingsite.services.impl.HttpPostingService;
import com.pulawskk.bettingsite.services.impl.JmsHandleService;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static java.time.LocalDateTime.now;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class IncomingDataControllerTest {

    @InjectMocks
    IncomingDataController incomingDataController;

    @Mock
    HttpPostingService httpPostingService;

    @Mock
    JmsHandleService jmsHandleService;

    MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(incomingDataController).build();
    }

    @Test
    void shouldReceiveGame_whenGameDtoIsPassed() throws Exception {
        //given
        GameDto gameDto = GameDto.builder()
                .uniqueId("123abc")
                .teamHome("Chelsea")
                .teamAway("Arsenal")
                .gameStatus("PREMATCH")
                .oddsH("1.35")
                .oddsX("2.56")
                .oddsA("6.01")
                .startGame(now().plusDays(2).toString())
                .build();

        String gameDtoJson = JSONObject.wrap(gameDto).toString();

        doNothing().when(httpPostingService).receiveGameData(any());

        //when
        mockMvc.perform(post("/games/game")
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .content(gameDtoJson))
                .andExpect(status().isOk())
                .andExpect(view().name(""));

        //then
        verify(httpPostingService, times(1)).receiveGameData(any());
    }

    @Test
    void shouldReceiveResult_whenResultDtoIsPassed() throws Exception {
        //given
        ResultDto resultDto = ResultDto.builder()
                .uniqueId("123abc")
                .teamHome("Chelsea")
                .teamAway("Arsenal")
                .build();

        String resultDtoJson = JSONObject.wrap(resultDto).toString();

        doNothing().when(httpPostingService).receiveResultData(any());

        //when
        mockMvc.perform(post("/games/result")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(resultDtoJson))
                .andExpect(status().isOk())
                .andExpect(view().name(""));

        //then
        verify(httpPostingService, times(1)).receiveResultData(any());
    }

    @Test
    void shouldReceiveJmsMessage_whenReceiveJmsMethodIsCalled() throws Exception {
        //when
        mockMvc.perform(get("/games/jms"))
                .andExpect(status().isOk())
                .andExpect(view().name("login"));

        //then
        verify(jmsHandleService, times(1)).receiveGameDataJms();
        verify(jmsHandleService, times(1)).receiveResultDataJms();
    }
}