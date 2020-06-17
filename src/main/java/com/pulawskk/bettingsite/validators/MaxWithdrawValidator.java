package com.pulawskk.bettingsite.validators;

import com.pulawskk.bettingsite.services.UserService;
import com.pulawskk.bettingsite.services.impl.UserServiceImpl;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class MaxWithdrawValidator implements ConstraintValidator<MaxWithdraw, Integer> {

    private UserService userService;

    public MaxWithdrawValidator(UserService userService) {
        this.userService = userService;
    }

    public MaxWithdrawValidator() {

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
