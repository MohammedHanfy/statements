package com.mah.statements.service.model;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class ViewStatementsResponseModel {

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
