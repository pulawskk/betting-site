package com.pulawskk.bettingsite.services.impl;

import com.pulawskk.bettingsite.entities.Wallet;
import com.pulawskk.bettingsite.enums.WalletTransactionType;
import com.pulawskk.bettingsite.repositories.WalletRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class WalletServiceImplTest {

    @InjectMocks
    WalletServiceImpl walletService;

    @Mock
    WalletRepository walletRepository;

    @Mock
    WalletAuditServiceImpl walletAuditService;

    @Test
    void shouldFindBalanceForUser_whenUserIdIsPassed() {
        //given
        final Long userId = 11L;
        final BigDecimal expectedBalance = new BigDecimal("1110");
        when(walletRepository.findBalanceForUser(userId)).thenReturn(new BigDecimal("1110"));

        //when
        BigDecimal actualBalance = walletService.findBalanceForUser(userId);

        //then
        assertThat(actualBalance, is(expectedBalance));
        verify(walletRepository, times(1)).findBalanceForUser(userId);
    }

    @Test
    void shouldUpdateBalanceAsDepositOrBetWinningAndMakeAudit_whenAmountAndUserIdIsPassed() {
        //given
        final Long userId = 11L;
        final double amount = 111;
        final WalletTransactionType walletTransactionType = WalletTransactionType.DEPOSIT;
        doNothing().when(walletRepository).updateBalanceForUser(amount, userId);
        when(walletRepository.findById(userId)).thenReturn(Optional.of(Wallet.builder().build()));

        //when
        walletService.updateBalance(amount, userId, walletTransactionType);

        //then
        verify(walletRepository, times(1)).updateBalanceForUser(amount, userId);
        verify(walletRepository, times(1)).findById(userId);
        verify(walletAuditService, times(1)).saveTransaction(any());
    }

    @Test
    void shouldNotUpdateBalanceAsDepositOrBetWinningAndMakeAudit_whenWalletDoesNotExistInDb() {
        //given
        final Long userId = 11L;
        final double amount = 111;
        final WalletTransactionType walletTransactionType = WalletTransactionType.DEPOSIT;
        doNothing().when(walletRepository).updateBalanceForUser(amount, userId);

        //when
        walletService.updateBalance(amount, userId, walletTransactionType);

        //then
        verify(walletRepository, times(1)).updateBalanceForUser(amount, userId);
        verify(walletRepository, times(1)).findById(userId);
        verify(walletAuditService, never()).saveTransaction(any());
    }

    @Test
    void shouldSubtractFromBalanceAndMakeAudit_whenBetIsPlacedOrDepositIsWithdrawn() {
        //given
        final Long userId = 11L;
        final double amount = 111;
        final WalletTransactionType walletTransactionType = WalletTransactionType.WITHDRAW;
        doNothing().when(walletRepository).updateBalanceForPlaceBet(amount, userId);
        when(walletRepository.findById(userId)).thenReturn(Optional.of(Wallet.builder().build()));

        //when
        walletService.subtractBetPlaceStake(amount, userId, walletTransactionType);

        //then
        verify(walletRepository, times(1)).updateBalanceForPlaceBet(amount, userId);
        verify(walletRepository, times(1)).findById(userId);
        verify(walletAuditService, times(1)).saveTransaction(any());
    }

    @Test
    void shouldNotSubtractFromBalanceAndMakeAudit_whenWalletIsNotFound() {
        //given
        final Long userId = 11L;
        final double amount = 111;
        final WalletTransactionType walletTransactionType = WalletTransactionType.WITHDRAW;
        doNothing().when(walletRepository).updateBalanceForPlaceBet(amount, userId);

        //when
        walletService.subtractBetPlaceStake(amount, userId, walletTransactionType);

        //then
        verify(walletRepository, times(1)).updateBalanceForPlaceBet(amount, userId);
        verify(walletRepository, times(1)).findById(userId);
        verify(walletAuditService, never()).saveTransaction(any());
    }

    @Test
    void shouldMakeAudit_whenWalletIsFound() {
        //given
        final Long userId = 11L;
        final double amount = 111;
        final WalletTransactionType walletTransactionType = WalletTransactionType.WITHDRAW;

        final Wallet wallet = Wallet.builder().balance(new BigDecimal("1000")).build();

        when(walletRepository.findById(userId)).thenReturn(Optional.of(wallet));

        //when
        walletService.addAudit(amount, userId, walletTransactionType);

        //then
        verify(walletAuditService, times(1)).saveTransaction(any());
    }

    @Test
    void shouldNotMakeAudit_whenWalletIsNotFound() {
        //given
        final Long userId = 11L;
        final double amount = 111;
        final WalletTransactionType walletTransactionType = WalletTransactionType.WITHDRAW;

        when(walletRepository.findById(userId)).thenReturn(Optional.empty());

        //when
        walletService.addAudit(amount, userId, walletTransactionType);

        //then
        verify(walletAuditService, never()).saveTransaction(any());
    }
}