package com.pulawskk.bettingsite.exceptions;

import java.io.PrintStream;

public class BetPlaceValidatorException extends RuntimeException {
    @Override
    public String getMessage() {
        return "Bet can not be placed due to having more than one the same selection within betslip";
    }
}
