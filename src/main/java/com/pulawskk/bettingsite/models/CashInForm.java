package com.pulawskk.bettingsite.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Setter
@Getter
@NoArgsConstructor
public class CashInForm {

    @NotNull(message = "You must select payment method!")
    private String paymentMethod;

    @Min(value = 11, message = "Amount must be grater than 10")
    private int amount;

    public CashInForm(String paymentMethod, int amount) {
        this.paymentMethod = paymentMethod;
        this.amount = amount;
    }
}
