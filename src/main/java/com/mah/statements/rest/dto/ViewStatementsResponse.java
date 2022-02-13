package com.mah.statements.rest.dto;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ViewStatementsResponse {

    private Long accountId;

    private String accountNumber;

    private List<StatementDetails> statementDetails;

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @ToString
    public static class StatementDetails {

        private LocalDate date;

        private BigDecimal amount;
    }
}
