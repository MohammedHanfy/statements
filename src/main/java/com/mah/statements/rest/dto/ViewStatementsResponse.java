package com.mah.statements.rest.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ViewStatementsResponse {

    private Long accountId;

    private String accountNumber;

    private List<ViewStatementDetailsResponse> statementDetails;
}
