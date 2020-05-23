package com.pulawskk.bettingsite.exceptions;

public class StakePlaceValidatorException extends RuntimeException {
    public StakePlaceValidatorException(String message) {
        super(message);
    }

    public StakePlaceValidatorException() {
    }

    @Override
    public String getMessage() {
        return "Bet can not be placed due to wrong stake: " + super.getMessage();
    }
}
