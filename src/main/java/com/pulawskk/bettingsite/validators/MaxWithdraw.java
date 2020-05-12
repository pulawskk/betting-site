package com.pulawskk.bettingsite.validators;


import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = MaxWithdrawValidator.class)
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface MaxWithdraw {
    String message() default "Amount can not be grater than your balance!";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
