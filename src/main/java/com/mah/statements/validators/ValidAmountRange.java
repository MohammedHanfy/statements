package com.mah.statements.validators;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = AmountRangeValidator.class)
public @interface ValidAmountRange {

    String message() default "{message}";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };
}
