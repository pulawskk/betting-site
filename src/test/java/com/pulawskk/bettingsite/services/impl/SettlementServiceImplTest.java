package com.pulawskk.bettingsite.services.impl;

import com.pulawskk.bettingsite.entities.*;
import com.pulawskk.bettingsite.enums.ResultType;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;

import static java.time.LocalDateTime.now;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.*;
import static org.hamcrest.core.Is.is;

@ExtendWith(MockitoExtension.class)
class SettlementServiceImplTest {

    @InjectMocks
    SettlementServiceImpl settlementService;

    @Mock
    BetServiceImpl betService;

    @Mock
    BetLegServiceImpl betLegService;

    @Mock
    BetSlipServiceImpl betSlipService;

    @Mock
    WalletServiceImpl walletService;

    @Mock
    GameServiceImpl gameService;

    List<BetLeg> betLegList;
    BetLeg betLeg;

    List<BetSlip> betSlipList;
    BetSlip betSlip;

    @BeforeEach
    void setUp() {
         betLeg = BetLeg.builder()
                .id(4L)
                .stake(new BigDecimal("10"))
                .betLegWin(new BigDecimal("109"))
                .betLegName("first")
                .created(now())
                .modified(now())
                .build();

        betLegList = Lists.newArrayList(betLeg);

        betSlip = BetSlip.builder()
                .id(44L)
                .created(now())
                .build();

        betSlipList = Lists.newArrayList(betSlip);
    }

    @Test
    void shouldUpdateBetLeg_whenBetResultTypeIsWinOrLose() {
        //given
        Bet betLose = Bet.builder()
                .id(12L)
                .resultType(ResultType.LOSE)
                .build();

        Bet betWin = Bet.builder()
                .id(13L)
                .resultType(ResultType.WIN)
                .build();

        betLeg.setBets(Lists.newArrayList(betLose, betWin));
        betLegList.add(betLeg);

        when(betLegService.findAllUnresulted()).thenReturn(betLegList);

        //when
        settlementService.runBetLegChecking();

        //then
        verify(betLegService, times(1)).findAllUnresulted();
        verify(betLegService, times(betLegList.size())).save(any());
    }

    @Test
    void shouldNotUpdateBetLeg_whenBetResultTypeIsNull() {
        //given
        Bet betResultTypeNull = Bet.builder()
                .id(12L)
                .resultType(null)
                .build();

        betLeg.setBets(Lists.newArrayList(betResultTypeNull));
        betLegList.add(betLeg);

        when(betLegService.findAllUnresulted()).thenReturn(betLegList);

        //when
        settlementService.runBetLegChecking();

        //then
        verify(betLegService, times(1)).findAllUnresulted();
        verify(betLegService, never()).save(any());
    }

    @Test
    void shouldUpdateBetSlip_whenBetSlipIsLost() {
        //given
        BetLeg betLegLose = BetLeg.builder()
                .result(ResultType.LOSE)
                .build();
        betSlip.addBetLeg(betLegLose);
        betSlipList.add(betSlip);
        betSlipList.add(betSlip);
        when(betSlipService.findAllUnresulted()).thenReturn(betSlipList);

        //when
        settlementService.runBetSlipChecking();

        //then
        verify(betSlipService, times(1)).findAllUnresulted();
        verify(walletService, never()).updateBalance(anyDouble(), anyLong(), any());
        verify(betSlipService, times(betSlipList.size())).save(any());
    }

    @Test
    void shouldNotUpdateBetSlip_whenBetSlipIsNotResulted() {
        //given
        BetLeg betLegUnresulted = BetLeg.builder()
                .result(null)
                .build();
        betSlip.addBetLeg(betLegUnresulted);
        betSlipList.add(betSlip);
        betSlipList.add(betSlip);
        when(betSlipService.findAllUnresulted()).thenReturn(betSlipList);

        //when
        settlementService.runBetSlipChecking();

        //then
        verify(betSlipService, times(1)).findAllUnresulted();
        verify(walletService, never()).updateBalance(anyDouble(), anyLong(), any());
        verify(betSlipService, never()).save(any());
    }

    @Test
    void shouldUpdateBetSlipAndBalance_whenBetSlipIsWin() {
        //given
        BetLeg betLegWon = BetLeg.builder()
                .result(ResultType.WIN)
                .build();
        betSlip.addBetLeg(betLegWon);
        User user = new User();
        user.setId(19L);
        betSlip.setUser(user);
        betSlip.setBetSlipWin(new BigDecimal("1234"));
        betSlipList.add(betSlip);
        betSlipList.add(betSlip);
        when(betSlipService.findAllUnresulted()).thenReturn(betSlipList);
        doNothing().when(walletService).updateBalance(anyDouble(), anyLong(), any());

        //when
        settlementService.runBetSlipChecking();

        //then
        verify(betSlipService, times(1)).findAllUnresulted();
        verify(walletService, times(betSlipList.size())).updateBalance(anyDouble(), anyLong(), any());
        verify(betSlipService, times(betSlipList.size())).save(any());
    }

    @Test
    void shouldResultBets_whenThereAreBets() {
        //given
        Bet bet1 = Bet.builder()
                .id(12L)
                .type("1")
                .build();

        Bet betX = Bet.builder()
                .id(13L)
                .type("X")
                .build();

        List<Bet> betList = Lists.newArrayList(bet1, betX);
        when(betService.findAllBetsByUniqueEventId(anyString())).thenReturn(betList);
        final String result = "{\"homeScores\":\"5\",\"awayScores\":\"4\"}";
        Game game = Game.builder()
                .result(result)
                .build();
        when(gameService.findGameById(anyString())).thenReturn(game);

        //when
        settlementService.processResultingBets(anyString());

        //then
        verify(betService, times(1)).findAllBetsByUniqueEventId(anyString());
        verify(gameService, times(1)).findGameById(anyString());
        verify(betService, times(betList.size())).saveBet(any());
    }

    @Test
    void shouldNotResultBets_whenThereAreNotBets() {
        //given
        List<Bet> betList = Lists.newArrayList();
        when(betService.findAllBetsByUniqueEventId(anyString())).thenReturn(betList);
        final String result = "{\"homeScores\":\"5\",\"awayScores\":\"4\"}";
        Game game = Game.builder()
                .result(result)
                .build();
        when(gameService.findGameById(anyString())).thenReturn(game);

        //when
        settlementService.processResultingBets(anyString());

        //then
        verify(betService, times(1)).findAllBetsByUniqueEventId(anyString());
        verify(gameService, times(1)).findGameById(anyString());
        verify(betService, never()).saveBet(any());
    }

    @Test
    void shouldReturnTrue_whenAnyBetLegIsUnresulted() {
        //given
        boolean expectedResult = true;
        List<BetLeg> betLegList = prepareBetLegListForCheckingBetSlip(null, ResultType.LOSE);

        //when
        boolean actualResult = settlementService.isAnyUnresultedBetLeg(betLegList);

        //then
        assertThat(actualResult, is(expectedResult));
    }

    /**

     * Helper method to prepare list of two BetLeg objects to check if contains specific betleg's types
     * @param firstBetLegResult ResultType for first bet leg
     * @param secondBetLegResult ResultType for second bet leg
     * @return list of BetLeg objects
     */
    private List<BetLeg> prepareBetLegListForCheckingBetSlip(ResultType firstBetLegResult, ResultType secondBetLegResult) {
        BetLeg betLeg1 = BetLeg.builder()
                .result(firstBetLegResult)
                .build();
        BetLeg betLeg2 = BetLeg.builder()
                .result(secondBetLegResult)
                .build();

        return Lists.newArrayList(betLeg1, betLeg2);
    }

    @Test
    void shouldReturnFalse_whenAllBetLegsAreResulted() {
        //given
        boolean expectedResult = false;
        List<BetLeg> betLegList = prepareBetLegListForCheckingBetSlip(ResultType.LOSE, ResultType.LOSE);

        //when
        boolean actualResult = settlementService.isAnyUnresultedBetLeg(betLegList);

        //then
        assertThat(actualResult, is(expectedResult));
    }

    @Test
    void shouldReturnTrue_whenAnyBetLegIsLost() {
        //given
        boolean expectedResult = true;
        List<BetLeg> betLegList = prepareBetLegListForCheckingBetSlip(null, ResultType.LOSE);

        //when
        boolean actualResult = settlementService.isAnyLostBetLeg(betLegList);

        //then
        assertThat(actualResult, is(expectedResult));
    }

    @Test
    void shouldReturnFalse_whenAllBetLegsAreNotLost() {
        //given
        boolean expectedResult = false;
        List<BetLeg> betLegList = prepareBetLegListForCheckingBetSlip(ResultType.WIN, ResultType.WIN);

        //when
        boolean actualResult = settlementService.isAnyLostBetLeg(betLegList);

        //then
        assertThat(actualResult, is(expectedResult));
    }

    @Test
    void shouldReturnTrue_whenAllBetLegsAreWon() {
        //given
        boolean expectedResult = true;
        List<BetLeg> betLegList = prepareBetLegListForCheckingBetSlip(ResultType.WIN, ResultType.WIN);

        //when
        boolean actualResult = settlementService.isAllBetLegsWin(betLegList);

        //then
        assertThat(actualResult, is(expectedResult));
    }

    @Test
    void shouldReturnFalse_whenAtLeastOneBetLegIsNotWon() {
        //given
        boolean expectedResult = false;
        List<BetLeg> betLegList = prepareBetLegListForCheckingBetSlip(ResultType.WIN, ResultType.LOSE);

        //when
        boolean actualResult = settlementService.isAllBetLegsWin(betLegList);

        //then
        assertThat(actualResult, is(expectedResult));
    }
}