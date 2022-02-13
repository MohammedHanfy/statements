package com.mah.statements.service.model;

import com.mah.statements.util.enums.UserType;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ViewStatementsRequestModel {

    private UserType userType;

    private Long accountId;

    private LocalDate fromDate;

    private LocalDate toDate;

    private BigDecimal fromAmount;

    private BigDecimal toAmount;
}
