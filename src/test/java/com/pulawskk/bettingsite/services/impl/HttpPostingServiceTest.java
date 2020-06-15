package com.pulawskk.bettingsite.services.impl;

import com.pulawskk.bettingsite.models.GameDto;
import com.pulawskk.bettingsite.models.ResultDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class HttpPostingServiceTest {

    @InjectMocks
    HttpPostingService httpPostingService;

    @Mock
    GameServiceImpl gameService;

    @Mock
    SettlementServiceImpl settlementService;

    @Test
    void shouldSaveGame_whenReceivingGameFromHttpPost() {
        //given
        GameDto gameDto = GameDto.builder()
                .uniqueId("123abc")
                .gameStatus("PREMATCH")
                .startGame(LocalDateTime.now().toString())
                .endGame(LocalDateTime.now().toString())
                .competition("Premier League")
                .teamHome("Chelsea")
                .teamAway("Arsenal")
                .build();

        //when
        httpPostingService.receiveGameData(gameDto);

        //then
        verify(gameService, times(1)).savePrematchGame(any());
    }

    @Test
    void shouldSaveResultAndResultBetSlips_whenReceivingResultFromHttpPost() {
        //given
        ResultDto resultDto = ResultDto.builder()
                .uniqueId("abcd12")
                .teamHome("Chelsea")
                .teamAway("Arsenal")
                .homeScores("6")
                .awayScores("0")
                .build();

        doNothing().when(gameService).persistResult(anyString(), anyString());
        doNothing().when(gameService).updateGameStatus(anyString(), anyString());
        doNothing().when(settlementService).processResultingBets(anyString());

        //when
        httpPostingService.receiveResultData(resultDto);

        //then
        verify(gameService, times(1)).persistResult(anyString(), anyString());
        verify(gameService, times(1)).updateGameStatus(anyString(), anyString());
        verify(settlementService, times(1)).processResultingBets(anyString());
    }
}