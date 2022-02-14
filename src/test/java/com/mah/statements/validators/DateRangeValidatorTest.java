package com.mah.statements.validators;

import com.mah.statements.rest.dto.ViewStatementsRequest;
import org.hibernate.validator.internal.engine.constraintvalidation.ConstraintValidatorContextImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.validation.Payload;
import java.lang.annotation.Annotation;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.stream.Stream;

@ExtendWith(MockitoExtension.class)
class DateRangeValidatorTest {

    @Mock
    private ConstraintValidatorContextImpl constraintValidatorContext;

    private final DateRangeValidator dateRangeValidator = new DateRangeValidator();

    @ParameterizedTest
    @MethodSource("validScenarios")
    void isValid_whenDateRangeIsValid_returnsTrue(final ViewStatementsRequest.DateRange dateRange) {

        final ValidDateRange validDateRange = buildValidDateRangeInstance();

        dateRangeValidator.initialize(validDateRange);

        Assertions.assertTrue(dateRangeValidator.isValid(dateRange, constraintValidatorContext));
    }

    @ParameterizedTest
    @MethodSource("invalidScenarios")
    void isValid_whenDateRangeIsNotValid_returnsFalse(final ViewStatementsRequest.DateRange dateRange) {

        final ValidDateRange validDateRange = buildValidDateRangeInstance();

        dateRangeValidator.initialize(validDateRange);

        Assertions.assertFalse(dateRangeValidator.isValid(dateRange, constraintValidatorContext));
    }

    private static Stream<Arguments> validScenarios() {

        return Stream.of(
                Arguments.of(ViewStatementsRequest.DateRange.builder().fromDate("01.01.2022").toDate("01.02.2022").build())
        );
    }

    private static Stream<Arguments> invalidScenarios() {

        return Stream.of(
                Arguments.of(ViewStatementsRequest.DateRange.builder().toDate("01.02.2022").build()),
                Arguments.of(ViewStatementsRequest.DateRange.builder().fromDate("01.01.2022").build()),
                Arguments.of(ViewStatementsRequest.DateRange.builder().fromDate("01/01/2022").toDate("01.02.2022").build()),
                Arguments.of(ViewStatementsRequest.DateRange.builder().fromDate("01.01.2023").toDate("01.02.2022").build()),
                Arguments.of(ViewStatementsRequest.DateRange.builder().fromDate(LocalDate.now().format(DateTimeFormatter.ofPattern("dd.MM.yyyy"))).toDate("01.02.2022").build()),
                Arguments.of(ViewStatementsRequest.DateRange.builder().fromDate("01.01.2022").toDate("01/02/2022").build()),
                Arguments.of(ViewStatementsRequest.DateRange.builder().fromDate("01.01.2022").toDate("01.02.2023").build()),
                Arguments.of(ViewStatementsRequest.DateRange.builder().fromDate("01.02.2022").toDate("01.01.2022").build())
        );
    }

    private ValidDateRange buildValidDateRangeInstance() {

        return new ValidDateRange() {

            @Override
            public Class<? extends Annotation> annotationType() {

                return ValidDateRange.class;
            }

            @Override
            public String message() {

                return null;
            }

            @Override
            public Class<?>[] groups() {

                return new Class[0];
            }

            @Override
            public Class<? extends Payload>[] payload() {

                return new Class[0];
            }
        };
    }
}