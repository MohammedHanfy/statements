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
import java.math.BigDecimal;
import java.util.stream.Stream;

@ExtendWith(MockitoExtension.class)
class AmountRangeValidatorTest {

    @Mock
    private ConstraintValidatorContextImpl constraintValidatorContext;

    private final AmountRangeValidator amountRangeValidator = new AmountRangeValidator();

    @ParameterizedTest
    @MethodSource("validScenarios")
    void isValid_whenAmountRangeIsValid_returnsTrue(final ViewStatementsRequest.AmountRange amountRange) {

        final ValidAmountRange validAmountRange = buildValidAmountRangeInstance();

        amountRangeValidator.initialize(validAmountRange);

        Assertions.assertTrue(amountRangeValidator.isValid(amountRange, constraintValidatorContext));
    }

    @ParameterizedTest
    @MethodSource("invalidScenarios")
    void isValid_whenAmountRangeIsNotValid_returnsFalse(final ViewStatementsRequest.AmountRange amountRange) {

        final ValidAmountRange validAmountRange = buildValidAmountRangeInstance();

        amountRangeValidator.initialize(validAmountRange);

        Assertions.assertFalse(amountRangeValidator.isValid(amountRange, constraintValidatorContext));
    }

    private static Stream<Arguments> validScenarios() {

        return Stream.of(
                Arguments.of(ViewStatementsRequest.AmountRange.builder().fromAmount(BigDecimal.ZERO).toAmount(BigDecimal.TEN).build())
        );
    }

    private static Stream<Arguments> invalidScenarios() {

        return Stream.of(
                Arguments.of(ViewStatementsRequest.AmountRange.builder().toAmount(BigDecimal.TEN).build()),
                Arguments.of(ViewStatementsRequest.AmountRange.builder().fromAmount(BigDecimal.TEN).build()),
                Arguments.of(ViewStatementsRequest.AmountRange.builder().fromAmount(BigDecimal.valueOf(-1)).toAmount(BigDecimal.TEN).build()),
                Arguments.of(ViewStatementsRequest.AmountRange.builder().fromAmount(BigDecimal.ZERO).toAmount(BigDecimal.valueOf(-1)).build()),
                Arguments.of(ViewStatementsRequest.AmountRange.builder().fromAmount(BigDecimal.TEN).toAmount(BigDecimal.ONE).build())
        );
    }

    private ValidAmountRange buildValidAmountRangeInstance() {

        return new ValidAmountRange() {

            @Override
            public Class<? extends Annotation> annotationType() {

                return ValidAmountRange.class;
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