package com.mah.statements.service.model;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class StatementModel {

    private Long id;

    private Long accountId;

    private LocalDate dateField;

    private BigDecimal amount;
}
