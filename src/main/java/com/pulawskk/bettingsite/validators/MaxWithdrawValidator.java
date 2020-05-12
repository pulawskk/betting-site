package com.pulawskk.bettingsite.validators;

import com.pulawskk.bettingsite.services.UserService;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;


@Component
public class MaxWithdrawValidator implements ConstraintValidator<MaxWithdraw, Integer> {

    private final UserService userService;

    public MaxWithdrawValidator(UserService userService) {
        this.userService = userService;
    }

    private int currentBalance;

    @Override
    public void initialize(MaxWithdraw constraintAnnotation) {
        currentBalance = userService.userLoggedIn().getWallet().getBalance().intValue();
    }

    @Override
    public boolean isValid(Integer incomingNumber, ConstraintValidatorContext constraintValidatorContext) {
        return incomingNumber <= currentBalance;
    }
}
