package com.pulawskk.bettingsite.services.impl;

import com.pulawskk.bettingsite.entities.Bet;
import com.pulawskk.bettingsite.entities.BetLeg;
import com.pulawskk.bettingsite.entities.BetSlip;
import com.pulawskk.bettingsite.entities.User;
import com.pulawskk.bettingsite.enums.BetSlipType;
import com.pulawskk.bettingsite.exceptions.BetPlaceValidatorException;
import com.pulawskk.bettingsite.exceptions.StakePlaceValidatorException;
import com.pulawskk.bettingsite.models.Selection;
import com.pulawskk.bettingsite.repositories.BetLegRepository;
import com.pulawskk.bettingsite.repositories.BetRepository;
import com.pulawskk.bettingsite.repositories.BetSlipRepository;
import com.pulawskk.bettingsite.services.WalletService;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BetSlipServiceImplTest {

    @InjectMocks
    BetSlipServiceImpl betSlipService;


    @Mock
    BetLegRepository betLegRepository;

    //fixme inject services instead of repository
    @Mock
    BetSlipRepository betSlipRepository;
    @Mock
    BetRepository betRepository;
    @Mock
    WalletService walletService;

    BetSlip betSlip;
    List<Selection> selectionList;
    String stake;
    List<BetSlipType> betSlipTypeList;
    User user;
    List<BetLeg> betLegList;
    BetLeg betLeg;
    Bet bet;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setId(11L);
        user.setName("karol");
        user.setActive(1);

        stake = "4.44";

        betLeg = BetLeg.builder().build();
        bet = Bet.builder().build();
        betLeg.setBets(Lists.newArrayList(bet));
        betLegList = Lists.newArrayList(betLeg);

        betSlip = BetSlip.builder()
                .created(LocalDateTime.now())
                .modified(LocalDateTime.now())
                .betSlipWin(new BigDecimal("11.43"))
                .betSlipType(BetSlipType.SINGLE)
                .user(user)
                .betLegs(betLegList)
                .build();

        Selection selection = Selection.builder()
                .uniqueId("444abc")
                .value("1.23")
                .userType("userType")
                .build();
        selectionList = Lists.newArrayList(selection);

        BetSlipType betSlipType = BetSlipType.SINGLE;
        betSlipTypeList = Lists.newArrayList(betSlipType);
    }

    @Test
    void shouldSaveBetSlip_whenBetSlipIsValid() {
        //given
        when(walletService.findBalanceForUser(user.getId())).thenReturn(new BigDecimal("1000"));
        when(betLegRepository.save(any())).thenReturn(betLeg);
        when(betSlipRepository.save(any())).thenReturn(betSlip);

        //when
        BetSlip actualBetSlip = betSlipService.saveBetSlip(selectionList, stake, betSlipTypeList, user);

        //then
        assertThat(actualBetSlip.getBetLegs().size(), is(1));
        verify(walletService, times(1)).findBalanceForUser(user.getId());
        verify(walletService, times(1)).subtractBetPlaceStake(anyDouble(), anyLong(), any());
        verify(betLegRepository, times(1)).save(any());
        verify(betRepository, atLeast(1)).save(any());
    }

    @Test
    void shouldThrowBetPlaceValidatorException_whenBetSlipHasGamesFromTheSameEvent() {
        //given
        Selection selection1 = Selection.builder().uniqueId("123abc").build();
        Selection selection2 = Selection.builder().uniqueId("123abc").build();
        selectionList.add(selection1);
        selectionList.add(selection2);
        String stake = "11";
        BigDecimal currentBalance = new BigDecimal("1000");

        //when
        assertThrows(BetPlaceValidatorException.class, () -> {
            betSlipService.betSlipValidation(selectionList, stake, currentBalance);
        });

        //then exception
    }

    @Test
    void shouldThrowStakePlaceValidatorException_whenBetSlipHasStakeLessThan2() {
        //given
        String stake = "1.8";
        BigDecimal currentBalance = new BigDecimal("1000");

        //when
        assertThrows(StakePlaceValidatorException.class, () -> {
            betSlipService.betSlipValidation(selectionList, stake, currentBalance);
        });

        //then exception
    }


    @Test
    void shouldThrowStakePlaceValidatorException_whenBetSlipHasStakeLessThanCurrentBalance() {
        //given
        String stake = "1001";
        BigDecimal currentBalance = new BigDecimal("1000");

        //when
        assertThrows(StakePlaceValidatorException.class, () -> {
            betSlipService.betSlipValidation(selectionList, stake, currentBalance);
        });

        //then exception
    }

    @Test
    void shouldReturnListOfUnresultedBetSlips_whenExistInDb() {
        //given
        List<BetSlip> unresultedBetSlips = Lists.newArrayList(betSlip);
        when(betSlipRepository.findAllUnresulted()).thenReturn(unresultedBetSlips);

        //when
        List<BetSlip> actualBetSlipsForUser = betSlipService.findAllUnresulted();

        //then
        assertThat(actualBetSlipsForUser.size(), is(unresultedBetSlips.size()));
        verify(betSlipRepository, times(1)).findAllUnresulted();
    }

    @Test
    void shouldReturnListOfActiveBetSlipsForUser_whenExistInDb() {
        //given
        List<BetSlip> userBetSlips = Lists.newArrayList(betSlip);
        when(betSlipRepository.findActiveBetSlipsForUser(11L)).thenReturn(userBetSlips);

        //when
        List<BetSlip> actualBetSlipsForUser = betSlipService.betSlipsActiveForUser(11L);

        //then
        assertThat(actualBetSlipsForUser.size(), is(userBetSlips.size()));
        verify(betSlipRepository, times(1)).findActiveBetSlipsForUser(11L);
    }

    @Test
    void shouldReturnListOfResultedBetSlipsForUser_whenExistInDb() {
        //given
        List<BetSlip> userBetSlips = Lists.newArrayList(betSlip);
        when(betSlipRepository.findResultedBetSlipsForUser(11L)).thenReturn(userBetSlips);

        //when
        List<BetSlip> actualBetSlipsForUser = betSlipService.betSlipsResultedForUser(11L);

        //then
        assertThat(actualBetSlipsForUser.size(), is(userBetSlips.size()));
        verify(betSlipRepository, times(1)).findResultedBetSlipsForUser(11L);
    }

    @Disabled
    void betSlipsWonForUser() {
    }

    @Disabled
    void betSlipsLostForUser() {
    }

    @ParameterizedTest
    @CsvSource({
            "0,1",
            "1,X",
            "2,2",
            "3,''"
    })
    void shouldReturnUserType_whenNumberIsPassed(String comeFromSite, String result) {
        //when
        String actual = betSlipService.convertUserType(comeFromSite);

        //then
        assertThat(actual, is(result));
    }
}