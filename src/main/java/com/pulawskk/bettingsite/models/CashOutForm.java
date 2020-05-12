package com.pulawskk.bettingsite.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class CashOutForm {

    private String bankAccount;
    private int amount;

    public CashOutForm(String bankAccount, int amount) {
        this.bankAccount = bankAccount;
        this.amount = amount;
    }
}
