package com.mah.statements.service.model;

import lombok.*;

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

    private List<ViewStatementDetailsModel> statementDetails;
}
