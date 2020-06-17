package com.pulawskk.bettingsite.controllers;

import com.pulawskk.bettingsite.entities.User;
import com.pulawskk.bettingsite.entities.Wallet;
import com.pulawskk.bettingsite.entities.WalletAudit;
import com.pulawskk.bettingsite.enums.WalletTransactionType;
import com.pulawskk.bettingsite.models.CashInForm;
import com.pulawskk.bettingsite.models.CashOutForm;
import com.pulawskk.bettingsite.services.impl.UserServiceImpl;
import com.pulawskk.bettingsite.services.impl.WalletAuditServiceImpl;
import com.pulawskk.bettingsite.services.impl.WalletServiceImpl;
import org.assertj.core.util.Lists;
import org.hibernate.validator.internal.engine.constraintvalidation.ConstraintValidatorContextImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import javax.validation.ConstraintValidatorContext;
import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class WalletControllerTest {

    @InjectMocks
    WalletController walletController;

    @Mock
    WalletServiceImpl walletService;

    @Mock
    UserServiceImpl userService;

    @Mock
    WalletAuditServiceImpl walletAuditService;

    MockMvc mockMvc;
    MockHttpSession mockHttpSession;

    User user;

    @BeforeEach
    void setUp() {
        mockHttpSession = new MockHttpSession();
        mockMvc = MockMvcBuilders.standaloneSetup(walletController).build();

        user = new User();
        user.setName("karol");
        user.setId(1L);
    }

    @Test
    void shouldPassCashInForm_whenCashInMoneyIsCalled() throws Exception {
        //when
        mockMvc.perform(get("/wallet/cashIn"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("cashInForm"))
                .andExpect(view().name("cashInDecorated"));

        //then
        verifyNoInteractions(walletAuditService);
        verifyNoInteractions(walletService);
        verifyNoInteractions(userService);
    }

    @Test
    void shouldUpdateBalance_whenCashInFormIsValid() throws Exception {
        //given
        CashInForm cashInForm = new CashInForm();
        cashInForm.setAmount(100);
        cashInForm.setPaymentMethod("BLIK");

        when(userService.userLoggedIn()).thenReturn(user);
        doNothing().when(walletService).updateBalance(cashInForm.getAmount(), user.getId(), WalletTransactionType.DEPOSIT);

        //when
        mockMvc.perform(post("/wallet/cashInAction")
                    .param("amount", String.valueOf(cashInForm.getAmount()))
                    .param("paymentMethod", String.valueOf(cashInForm.getPaymentMethod())))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/mainBoard"));

        //then
        verifyNoInteractions(walletAuditService);
        verify(walletService, times(1)).updateBalance(cashInForm.getAmount(), user.getId(), WalletTransactionType.DEPOSIT);
        verify(userService, times(1)).userLoggedIn();
    }

    @Test
    void shouldNotUpdateBalance_whenCashInFormHasAmountLessThan11() throws Exception {
        //given
        CashInForm cashInForm = new CashInForm();
        cashInForm.setAmount(1);
        cashInForm.setPaymentMethod("BLIK");

        //when
        mockMvc.perform(post("/wallet/cashInAction")
                .param("amount", String.valueOf(cashInForm.getAmount()))
                .param("paymentMethod", String.valueOf(cashInForm.getPaymentMethod())))
                .andExpect(status().isOk())
                .andExpect(view().name("cashInDecorated"));

        //then
        verifyNoInteractions(walletAuditService);
        verifyNoInteractions(walletService);
        verifyNoInteractions(userService);
    }


    @Test
    void shouldNotUpdateBalance_whenCashInFormHasNullPaymentMethod() throws Exception {
        //given
        CashInForm cashInForm = new CashInForm();
        cashInForm.setAmount(111);
        cashInForm.setPaymentMethod("");

        //when
        mockMvc.perform(post("/wallet/cashInAction")
                .param("amount", String.valueOf(cashInForm.getAmount()))
                .param("paymentMethod", String.valueOf(cashInForm.getPaymentMethod())))
                .andExpect(status().isOk())
                .andExpect(view().name("cashInDecorated"));

        //then
        verifyNoInteractions(walletAuditService);
        verifyNoInteractions(walletService);
        verifyNoInteractions(userService);
    }

    @Test
    void shouldPassCashOutForm_whenCashOutMoneyIsCalled() throws Exception {
        //when
        mockMvc.perform(get("/wallet/cashOut"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("cashOutForm"))
                .andExpect(view().name("cashOutDecorated"));

        //then
        verifyNoInteractions(walletAuditService);
        verifyNoInteractions(walletService);
        verifyNoInteractions(userService);
    }

    @Test
    @Disabled
        // FIXME: 17.06.2020 ticket number: 130
    void shouldUpdateBalance_whenCashOutFormIsValid() throws Exception {
        //given
        user.setWallet(Wallet.builder().balance(new BigDecimal("1000")).build());
        CashOutForm cashOutForm = new CashOutForm();
        cashOutForm.setAmount(1);
        cashOutForm.setBankAccount("12 1212 1212 1212 1212 1212");
        when(userService.userLoggedIn()).thenReturn(user);
//        doNothing().when(walletService).subtractBetPlaceStake(cashOutForm.getAmount(), user.getId(), WalletTransactionType.WITHDRAW);

        //when
        mockMvc.perform(post("/wallet/cashOutAction")
                    .param("amount", String.valueOf(cashOutForm.getAmount()))
                    .param("bankAccount", String.valueOf(cashOutForm.getBankAccount())))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/mainBoard"));

        //then
        verifyNoInteractions(walletAuditService);
        verify(walletService, times(1)).subtractBetPlaceStake(cashOutForm.getAmount(), user.getId(), WalletTransactionType.WITHDRAW);
        verify(userService, times(1)).userLoggedIn();
    }

    @Test
    @Disabled
        // FIXME: 17.06.2020 ticket number: 130
    void shouldNotUpdateBalance_whenCashOutFormHasAmountLessThan2() throws Exception {
        //given
        CashOutForm cashOutForm = new CashOutForm();
        cashOutForm.setAmount(1);
        cashOutForm.setBankAccount("12 1212 1212 1212 1212 1212");

        //when
        mockMvc.perform(post("/wallet/cashOutAction")
                .param("amount", String.valueOf(cashOutForm.getAmount()))
                .param("bankAccount", String.valueOf(cashOutForm.getBankAccount())))
                .andExpect(status().isOk())
                .andExpect(view().name("cashOutDecorated"));

        //then
        verifyNoInteractions(walletAuditService);
        verifyNoInteractions(walletService);
        verifyNoInteractions(userService);
    }

    @Test
    @Disabled
        // FIXME: 17.06.2020 ticket number: 130
    void shouldNotUpdateBalance_whenCashOutFormHasWrongPatternOfBankAccount() throws Exception {
        //given
        CashOutForm cashOutForm = new CashOutForm();
        cashOutForm.setAmount(11);
        cashOutForm.setBankAccount("123141414");

        //when
        mockMvc.perform(post("/wallet/cashOutAction")
                .param("amount", String.valueOf(cashOutForm.getAmount()))
                .param("bankAccount", String.valueOf(cashOutForm.getBankAccount())))
                .andExpect(status().isOk())
                .andExpect(view().name("cashOutDecorated"));

        //then
        verifyNoInteractions(walletAuditService);
        verifyNoInteractions(walletService);
        verifyNoInteractions(userService);
    }

    @Test
    void shouldPassListOfWalletAudits_whenUserIdIsPassed() throws Exception {
        //given
        WalletAudit walletAudit = WalletAudit.builder().build();
        List<WalletAudit> walletAuditList = Lists.newArrayList(walletAudit);
        user.setWallet(Wallet.builder().id(user.getId()).balance(new BigDecimal("1000")).build());

        when(userService.userLoggedIn()).thenReturn(user);
        when(walletAuditService.getTransactionsForWallet(user.getId())).thenReturn(walletAuditList);

        //when
        mockMvc.perform(get("/wallet/history"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("transactions"))
                .andExpect(view().name("transactionsHistoryDecorated"));

        //then
        verify(userService, times(1)).userLoggedIn();
        verify(walletAuditService, times(1)).getTransactionsForWallet(user.getId());
    }
}