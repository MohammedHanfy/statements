package com.mah.statements.service.provider;

import com.mah.statements.entity.Account;
import com.mah.statements.entity.Statement;
import com.mah.statements.mappers.StatementMapper;
import com.mah.statements.service.model.StatementModel;
import com.mah.statements.service.model.ViewStatementsResponseModel;
import com.mah.statements.util.ViewStatementActionType;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ViewStatementsResponseModelProvider {

    public static ViewStatementsResponseModel buildViewStatementsResponseModelBuilder(Account account,
                                                                                      List<Statement> statements,
                                                                                      ViewStatementActionType viewStatementActionType,
                                                                                      Map<String, Object> requestMap) {

        return ViewStatementsResponseModel.builder()
                .accountId(account.getId())
                .accountNumber(account.getAccountNumber())
                .statementDetails(buildStatementDetails(viewStatementActionType, statements, requestMap))
                .build();
    }

    public static List<ViewStatementsResponseModel.StatementDetails> buildStatementDetails(ViewStatementActionType viewStatementActionType,
                                                                                           List<Statement> statements,
                                                                                           Map<String, Object> requestMap) {

        List<StatementModel> statementModelList;

        switch (viewStatementActionType) {

            case FILTER_DATE_AND_AMOUNT:

                statementModelList = statements.stream()
                        .map(StatementMapper.INSTANCE::mapToDto)
                        .filter(statementModel ->
                                statementModel.getDateField().isAfter((LocalDate) requestMap.get("fromDate")) &&
                                        statementModel.getDateField().isBefore((LocalDate) requestMap.get("toDate")))
                        .filter(statementModel ->
                                statementModel.getAmount().compareTo((BigDecimal) requestMap.get("fromAmount")) > 0 &&
                                        statementModel.getAmount().compareTo((BigDecimal) requestMap.get("toAmount")) < 0)
                        .collect(Collectors.toList());

                break;
            case FILTER_DATE:

                statementModelList = statements.stream()
                        .map(StatementMapper.INSTANCE::mapToDto)
                        .filter(statementModel ->
                                statementModel.getDateField().isAfter((LocalDate) requestMap.get("fromDate")) &&
                                        statementModel.getDateField().isBefore((LocalDate) requestMap.get("toDate")))
                        .collect(Collectors.toList());
                break;

            case FILTER_AMOUNT:

                statementModelList = statements.stream()
                        .map(StatementMapper.INSTANCE::mapToDto)
                        .filter(statementModel ->
                                statementModel.getAmount().compareTo((BigDecimal) requestMap.get("fromAmount")) > 0 &&
                                        statementModel.getAmount().compareTo((BigDecimal) requestMap.get("toAmount")) < 0)
                        .collect(Collectors.toList());
                break;

            default:

                final LocalDate threeMonthsBack = LocalDate.now().minusMonths(3);

                statementModelList = statements.stream()
                        .map(StatementMapper.INSTANCE::mapToDto)
                        .filter(statementModel -> statementModel.getDateField().isAfter(threeMonthsBack))
                        .collect(Collectors.toList());
        }

        return statementModelList.stream()
                .map(statementModel ->
                        ViewStatementsResponseModel.StatementDetails.builder()
                                .date(statementModel.getDateField())
                                .amount(statementModel.getAmount())
                                .build())
                .collect(Collectors.toList());

    }
}
