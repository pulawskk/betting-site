package com.pulawskk.bettingsite.models;

import com.pulawskk.bettingsite.validators.MaxWithdraw;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Pattern;

@Setter
@Getter
@NoArgsConstructor
public class CashOutForm {

    @Pattern(regexp = "", message = "please type bank account number space separated")
    private String bankAccount;

//    @Min(value = 11, message = "Amount must be grater than 10")
//    @Max(value = , message = "Amount must be less than your balance")
    @MaxWithdraw
    private int amount;

    private int currentBalance;

    public CashOutForm(int currentBalance) {
        this.currentBalance = currentBalance;
    }
}
