package com.mah.statements.validators;


import com.mah.statements.rest.dto.ViewStatementsRequest;
import org.hibernate.validator.internal.engine.constraintvalidation.ConstraintValidatorContextImpl;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.math.BigDecimal;
import java.util.Objects;

public class AmountRangeValidator implements ConstraintValidator<ValidAmountRange, ViewStatementsRequest.AmountRange> {

    private static final String MESSAGE = "message";

    @Override
    public void initialize(ValidAmountRange constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(ViewStatementsRequest.AmountRange amountRange, ConstraintValidatorContext constraintValidatorContext) {

        if (Objects.isNull(amountRange.getFromAmount())) {

            ((ConstraintValidatorContextImpl) constraintValidatorContext).addMessageParameter(MESSAGE, "fromAmount must not be null");
            return false;
        }

        if (Objects.isNull(amountRange.getToAmount())) {

            ((ConstraintValidatorContextImpl) constraintValidatorContext).addMessageParameter(MESSAGE, "toAmount must not be null");
            return false;
        }

        if (amountRange.getFromAmount().compareTo(BigDecimal.ZERO) < 0) {

            ((ConstraintValidatorContextImpl) constraintValidatorContext).addMessageParameter(MESSAGE, "fromAmount must be a valid number greater than or equal 0");
            return false;
        }

        if (amountRange.getToAmount().compareTo(BigDecimal.ZERO) < 0 ) {

            ((ConstraintValidatorContextImpl) constraintValidatorContext).addMessageParameter(MESSAGE, "toAmount must be a valid number greater than or equal 0");
            return false;
        }

        if (amountRange.getFromAmount().compareTo(amountRange.getToAmount()) > 0) {

            ((ConstraintValidatorContextImpl) constraintValidatorContext).addMessageParameter(MESSAGE, "fromAmount must be less than or equal to toAmount");
            return false;
        }

        return true;
    }
}
