package com.pulawskk.bettingsite.services.impl;

import com.pulawskk.bettingsite.entities.BetLeg;
import com.pulawskk.bettingsite.repositories.BetLegRepository;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BetLegServiceImplTest {

    @InjectMocks
    BetLegServiceImpl betLegService;

    @Mock
    BetLegRepository betLegRepository;

    BetLeg betLeg;

    @BeforeEach
    void setUp() {
        betLegService = new BetLegServiceImpl(betLegRepository);

        betLeg = BetLeg.builder().betLegName("first").id(1L)
                .betLegWin(new BigDecimal("11")).stake(new BigDecimal("2")).result(null).build();
    }

    @Test
    void findAllUnresulted() {
        //given
        List<BetLeg> expectedUnresultedList = Lists.newArrayList(betLeg);
        when(betLegRepository.findAllUnresulted()).thenReturn(expectedUnresultedList);

        //when
        List<BetLeg> actualUnresultedList = betLegService.findAllUnresulted();

        //then
        assertThat(actualUnresultedList.size(), is(expectedUnresultedList.size()));
        verify(betLegRepository, times(1)).findAllUnresulted();
    }

    @Test
    void save() {
        //given
        BetLeg betLegToBeSaved = BetLeg.builder().betLegName("first").build();
        when(betLegRepository.save(betLegToBeSaved)).thenReturn(betLeg);

        //when
        BetLeg actualBetLeg = betLegService.save(betLegToBeSaved);

        //then
        assertThat(actualBetLeg.getId(), is(1L));
        verify(betLegRepository, times(1)).save(betLegToBeSaved);
    }
}