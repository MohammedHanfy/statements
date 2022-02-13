package com.mah.statements.rest.dto;

import com.mah.statements.validators.ValidAmountRange;
import com.mah.statements.validators.ValidDateRange;
import lombok.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class ViewStatementsRequest {

    @NotNull(message = "Account ID must not be null")
    private Long accountId;

    @Valid
    private DateRange dateRange;

    @Valid
    private AmountRange amountRange;

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @ToString
    @ValidDateRange
    public static class DateRange {

        private String fromDate;

        private String toDate;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @ToString
    @ValidAmountRange
    public static class AmountRange {

        private BigDecimal fromAmount;

        private BigDecimal toAmount;
    }
}
