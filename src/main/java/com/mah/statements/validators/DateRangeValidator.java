package com.mah.statements.validators;


import com.mah.statements.rest.dto.ViewStatementsRequest;
import org.hibernate.validator.internal.engine.constraintvalidation.ConstraintValidatorContextImpl;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Objects;

public class DateRangeValidator implements ConstraintValidator<ValidDateRange, ViewStatementsRequest.DateRange> {

    private static final String MESSAGE = "message";

    @Override
    public void initialize(ValidDateRange constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(ViewStatementsRequest.DateRange dateRange, ConstraintValidatorContext constraintValidatorContext) {

        if (Objects.isNull(dateRange.getFromDate())) {

            ((ConstraintValidatorContextImpl) constraintValidatorContext).addMessageParameter(MESSAGE, "fromDate must not be null");
            return false;
        }

        if (Objects.isNull(dateRange.getToDate())) {

            ((ConstraintValidatorContextImpl) constraintValidatorContext).addMessageParameter(MESSAGE, "toDate must not be null");
            return false;
        }

        final var now = LocalDate.now();

        final var dateFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");

        LocalDate fromDate;

        try {

            fromDate = LocalDate.parse(dateRange.getFromDate(), dateFormatter);

        } catch (DateTimeParseException dateTimeParseException) {

            ((ConstraintValidatorContextImpl) constraintValidatorContext).addMessageParameter(MESSAGE, "fromDate must be in proper date format dd.MM.yyyy ex. 13.02.2022");
            return false;
        }

        if (fromDate.isAfter(now) || fromDate.isEqual(now)) {

            ((ConstraintValidatorContextImpl) constraintValidatorContext).addMessageParameter(MESSAGE, "fromDate must be in the past");
            return false;
        }

        LocalDate toDate;

        try {

            toDate = LocalDate.parse(dateRange.getToDate(), dateFormatter);

        } catch (DateTimeParseException dateTimeParseException) {

            ((ConstraintValidatorContextImpl) constraintValidatorContext).addMessageParameter(MESSAGE, "toDate must be in proper date format dd.MM.yyyy ex. 13.02.2022");
            return false;
        }

        if (toDate.isAfter(now)) {

            ((ConstraintValidatorContextImpl) constraintValidatorContext).addMessageParameter(MESSAGE, "toDate must be in the past or today");
            return false;
        }

        if (fromDate.isAfter(toDate)) {

            ((ConstraintValidatorContextImpl) constraintValidatorContext).addMessageParameter(MESSAGE, "fromDate must be before toDate");
            return false;
        }

        return true;
    }
}
