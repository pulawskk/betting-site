package com.pulawskk.bettingsite.services.impl;

import com.pulawskk.bettingsite.entities.Bet;
import com.pulawskk.bettingsite.enums.BetStatus;
import com.pulawskk.bettingsite.repositories.BetRepository;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BetServiceImplTest {

    @InjectMocks
    BetServiceImpl betService;

    @Mock
    BetRepository betRepository;

    Bet bet;

    @BeforeEach
    void setUp() {
        BigDecimal odd = new BigDecimal("2.17");
        String selectionId = "1bdb5d4";
        bet = Bet.builder()
                    .id(22L)
                    .created(LocalDateTime.now())
                    .modified(LocalDateTime.now())
                    .odd(odd)
                    .selectionId(selectionId)
                    .type("X")
                .build();
    }

    @Test
    void shouldReturnListOfAllPrematchBets_whenExistInDb() {
        //given
        bet.setBetStatus(BetStatus.PREMATCH);
        List<Bet> allPrematchBets = Lists.newArrayList(bet);

        when(betRepository.findAllPrematchBets()).thenReturn(allPrematchBets);

        //when
        List<Bet> expectedBets = betService.findAllPrematchBets();

        //then
        assertThat(expectedBets.size(), is(allPrematchBets.size()));
        verify(betRepository, times(1)).findAllPrematchBets();
    }

    @Test
    void shouldReturnListOfAllResultedBets_whenExistInDb() {
        bet.setBetStatus(BetStatus.RESULTED);
        List<Bet> allResultedBets = Lists.newArrayList(bet);

        when(betRepository.findAllResultedBets()).thenReturn(allResultedBets);

        //when
        List<Bet> expectedBets = betService.findAllResultedBets();

        //then
        assertThat(expectedBets.size(), is(allResultedBets.size()));
        verify(betRepository, times(1)).findAllResultedBets();
    }

    @Test
    void shouldReturnListOfAllBetsForUniqueEventId_whenExistInDb() {
        List<Bet> allBetsForUniqueEventId = Lists.newArrayList(bet);

        when(betRepository.findAllBySelectionId(anyString())).thenReturn(allBetsForUniqueEventId);

        //when
        List<Bet> expectedBets = betService.findAllBetsByUniqueEventId(anyString());

        //then
        assertThat(expectedBets.size(), is(allBetsForUniqueEventId.size()));
        verify(betRepository, times(1)).findAllBySelectionId(anyString());
    }

    @Test
    void shouldSaveBet_whenBetIsValid() {
        //given
        Bet betToBeSaved = Bet.builder().created(LocalDateTime.now()).build();
        when(betRepository.save(betToBeSaved)).thenReturn(bet);

        //when
        Bet actualBet = betService.saveBet(betToBeSaved);

        //then
        assertThat(actualBet.getId(), is(22L));
        verify(betRepository, times(1)).save(betToBeSaved);
    }
}