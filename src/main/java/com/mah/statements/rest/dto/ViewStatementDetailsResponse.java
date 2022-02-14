package com.mah.statements.rest.dto;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class ViewStatementDetailsResponse {

    private LocalDate date;

    private BigDecimal amount;
}
