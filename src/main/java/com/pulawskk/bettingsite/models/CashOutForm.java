package com.pulawskk.bettingsite.models;

import com.pulawskk.bettingsite.validators.MaxWithdraw;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;

@Setter
@Getter
@NoArgsConstructor
public class CashOutForm {

    @Pattern(regexp = "(\\d){2}(\\s(\\d){4}){5}", message = "please type bank account number space separated")
    private String bankAccount;

    @Min(value = 1, message = "Value must be positive")
    @MaxWithdraw
    private int amount;

}
