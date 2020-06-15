package com.pulawskk.bettingsite.services.impl;

import com.pulawskk.bettingsite.entities.User;
import com.pulawskk.bettingsite.entities.Wallet;
import com.pulawskk.bettingsite.entities.WalletAudit;
import com.pulawskk.bettingsite.enums.WalletTransactionType;
import com.pulawskk.bettingsite.repositories.WalletAuditRepository;
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
import static org.hamcrest.core.Is.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class WalletAuditServiceImplTest {

    @InjectMocks
    WalletAuditServiceImpl walletAuditService;

    @Mock
    WalletAuditRepository walletAuditRepository;

    WalletAudit walletAudit;

    @BeforeEach
    void setUp() {
        User user = new User();
        user.setId(11L);
        user.setName("karol");
        user.setActive(1);
        Wallet wallet = Wallet.builder()
                .id(33L)
                .balance(new BigDecimal("1000"))
                .modified(now())
                .user(user)
                .build();
        walletAudit = WalletAudit.builder()
                .id(111L)
                .wallet(wallet)
                .amountInTransaction(new BigDecimal("100"))
                .transactionType(WalletTransactionType.BET_WON)
                .createdAt(now())
                .build();
    }

    @Test
    void shouldReturnWalletAudit_whenTransactionIsBeingSaved() {
        //given
        when(walletAuditRepository.save(any())).thenReturn(walletAudit);

        //when
        WalletAudit actualWalletAudit = walletAuditService.saveTransaction(any());

        //then
        assertThat(actualWalletAudit.getId(), is(walletAudit.getId()));
        verify(walletAuditRepository, times(1)).save(any());
    }

    @Test
    void shouldReturnListOfWalletAudit_whenTransactionAreTakenForWallet() {
        //given
        final Long walletId = 33L;
        List<WalletAudit> walletAuditList = Lists.newArrayList(walletAudit);
        when(walletAuditRepository.findAllByWalletIdOrderByCreatedAtDesc(walletId)).thenReturn(walletAuditList);

        //when
        List<WalletAudit> actualWalletAuditList = walletAuditService.getTransactionsForWallet(walletId);

        //then
        verify(walletAuditRepository, times(1)).findAllByWalletIdOrderByCreatedAtDesc(walletId);
        assertThat(actualWalletAuditList.size(), is(walletAuditList.size()));
    }
}