package com.pulawskk.bettingsite.validators;

import com.pulawskk.bettingsite.services.UserService;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class MaxWithdrawValidator implements ConstraintValidator<MaxWithdraw, Integer> {

    private final UserService userService;

    public MaxWithdrawValidator(UserService userService) {
        this.userService = userService;
    }

    private int currentBalance;

    @Override
    public void initialize(MaxWithdraw constraintAnnotation) {

    }

    @Override
    public boolean isValid(Integer incomingNumber, ConstraintValidatorContext constraintValidatorContext) {
        currentBalance = userService.userLoggedIn().getWallet().getBalance().intValue();
        return incomingNumber <= currentBalance;
    }
}
