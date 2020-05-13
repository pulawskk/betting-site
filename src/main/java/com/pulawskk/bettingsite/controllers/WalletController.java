package com.pulawskk.bettingsite.controllers;

import com.pulawskk.bettingsite.entities.Wallet;
import com.pulawskk.bettingsite.entities.WalletAudit;
import com.pulawskk.bettingsite.enums.WalletTransactionType;
import com.pulawskk.bettingsite.models.CashInForm;
import com.pulawskk.bettingsite.models.CashOutForm;
import com.pulawskk.bettingsite.services.UserService;
import com.pulawskk.bettingsite.services.WalletAuditService;
import com.pulawskk.bettingsite.services.WalletService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/wallet")
public class WalletController {

    private final WalletService walletService;
    private final UserService userService;

    public WalletController(WalletService walletService, UserService userService, WalletAuditService walletAuditService) {
        this.walletService = walletService;
        this.userService = userService;
    }

    @GetMapping("/cashIn")
    public String cashInMoney(Model model, HttpServletRequest request) {
        HttpSession session = request.getSession();
        List<String> methods = new ArrayList<>();
        methods.add("BLIK");
        methods.add("BANK TRANSFER");
        methods.add("DOTPAY SERVICE");
        session.setAttribute("methodPayments", methods);

        model.addAttribute("cashInForm", new CashInForm());
        return "cashInDecorated";
    }

    @PostMapping("/cashInAction")
    public String cashInMoneyAction(@Valid CashInForm cashInForm, Errors errors) {
        if (errors.hasErrors()) {
            return "cashInDecorated";
        }

        System.out.println("Proccessing deposit: " + cashInForm.getAmount());
        System.out.println("Proccessing deposit: " + cashInForm.getPaymentMethod());
        walletService.updateBalance(cashInForm.getAmount(), userService.userLoggedIn().getId(), WalletTransactionType.DEPOSIT);
//        WalletAudit walletAudit = WalletAudit.builder()
//                .amountInTransaction(new BigDecimal(cashInForm.getAmount()))
//                .createdAt(LocalDateTime.now())
//                .transactionType(WalletTransactionType.DEPOSIT).build();
//        walletAuditService.saveTransaction(walletAudit);
        return "redirect:/mainBoard";
    }

    @GetMapping("/cashOut")
    public String cashOutMoney(Model model) {
        model.addAttribute("cashOutForm", new CashOutForm());
        return "cashOutDecorated";
    }

    @PostMapping("/cashOutAction")
    public String cashOutMoneyAction(@Valid CashOutForm cashOutForm, Errors errors) {
        if (errors.hasErrors()) {
            return "cashOutDecorated";
        }

        System.out.println("WITHDRAW process: " + cashOutForm.getBankAccount());
        System.out.println("WITHDRAW process: " + cashOutForm.getAmount());
        walletService.subtractBetPlaceStake(cashOutForm.getAmount(), userService.userLoggedIn().getId(), WalletTransactionType.WITHDRAW);
        return "redirect:/mainBoard";
    }
}
