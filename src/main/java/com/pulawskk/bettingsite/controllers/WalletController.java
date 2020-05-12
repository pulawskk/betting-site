package com.pulawskk.bettingsite.controllers;

import com.pulawskk.bettingsite.models.CashInForm;
import com.pulawskk.bettingsite.services.UserService;
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
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/wallet")
public class WalletController {

    private final WalletService walletService;
    private final UserService userService;

    public WalletController(WalletService walletService, UserService userService) {
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
        walletService.updateBalance(cashInForm.getAmount(), userService.userLoggedIn().getId());
        return "redirect:/wallet/cashIn";
    }

    @GetMapping("/cashOut")
    public String cashOutMoney() {
        return "cashOutDecorated";
    }
}
