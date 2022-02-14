package com.mah.statements.service.impl;

import com.mah.statements.mappers.AccountMapper;
import com.mah.statements.mappers.StatementMapper;
import com.mah.statements.repository.AccountRepository;
import com.mah.statements.repository.StatementRepository;
import com.mah.statements.service.StatementsService;
import com.mah.statements.service.model.*;
import com.mah.statements.util.enums.UserType;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class StatementsServiceImpl implements StatementsService {

    private final AccountRepository accountRepository;

    private final StatementRepository statementRepository;

    public StatementsServiceImpl(AccountRepository accountRepository, StatementRepository statementRepository) {
        this.accountRepository = accountRepository;
        this.statementRepository = statementRepository;
    }

    @Override
    public ViewStatementsResponseModel viewStatements(ViewStatementsRequestModel viewStatementsRequestModel) {

        var accountModel = accountRepository.findById(viewStatementsRequestModel.getAccountId())
                .map(AccountMapper.INSTANCE::mapToDto)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Account with ID: %s not found", viewStatementsRequestModel.getAccountId())));

        List<StatementModel> statementModelList = statementRepository.findAllByAccountId(accountModel.getId())
                .stream()
                .map(StatementMapper.INSTANCE::mapToDto)
                .sorted(Comparator.comparing(StatementModel::getDateField).reversed())
                .collect(Collectors.toList());

        if (UserType.ADMIN.equals(viewStatementsRequestModel.getUserType())) {

            statementModelList = viewFilteredStatements(statementModelList, viewStatementsRequestModel);
        } else {

            statementModelList = viewDefaultStatements(statementModelList);
        }

        return buildViewStatementsResponseDto(accountModel, statementModelList);
    }

    private List<StatementModel> viewFilteredStatements(List<StatementModel> statementModelList, ViewStatementsRequestModel viewStatementsRequestModel) {

        boolean filterDateRange = Objects.nonNull(viewStatementsRequestModel.getFromDate()) && Objects.nonNull(viewStatementsRequestModel.getToDate());
        boolean filterAmountRange = Objects.nonNull(viewStatementsRequestModel.getFromAmount()) && Objects.nonNull(viewStatementsRequestModel.getToAmount());

        if (filterDateRange || filterAmountRange) {

            if (filterDateRange) {
                statementModelList = viewFilteredDateRangeStatements(statementModelList, viewStatementsRequestModel);
            }

            if (filterAmountRange) {
                statementModelList = viewFilteredAmountRangeStatements(statementModelList, viewStatementsRequestModel);
            }
        } else {

            statementModelList = viewDefaultStatements(statementModelList);
        }

        return statementModelList;
    }

    private List<StatementModel> viewFilteredDateRangeStatements(List<StatementModel> statementModelList, ViewStatementsRequestModel viewStatementsRequestModel) {

        return statementModelList.stream()
                .filter(statementModel ->
                        statementModel.getDateField().isAfter(viewStatementsRequestModel.getFromDate()) &&
                                statementModel.getDateField().isBefore(viewStatementsRequestModel.getToDate()))
                .collect(Collectors.toList());
    }

    private List<StatementModel> viewFilteredAmountRangeStatements(List<StatementModel> statementModelList, ViewStatementsRequestModel viewStatementsRequestModel) {

        return statementModelList.stream()
                .filter(statementModel ->
                        statementModel.getAmount().compareTo(viewStatementsRequestModel.getFromAmount()) > 0 &&
                                statementModel.getAmount().compareTo(viewStatementsRequestModel.getToAmount()) < 0)
                .collect(Collectors.toList());
    }

    private List<StatementModel> viewDefaultStatements(List<StatementModel> statementModelList) {

        final LocalDate threeMonthsBack = LocalDate.now().minusMonths(3);

        return statementModelList.stream()
                .filter(statementModel -> statementModel.getDateField().isAfter(threeMonthsBack))
                .collect(Collectors.toList());
    }

    private ViewStatementsResponseModel buildViewStatementsResponseDto(AccountModel accountModel, List<StatementModel> statementModelList) {

        List<ViewStatementDetailsModel> statementDetails = statementModelList.stream()
                .map(statementModel ->
                        ViewStatementDetailsModel.builder()
                                .date(statementModel.getDateField())
                                .amount(statementModel.getAmount())
                                .build())
                .collect(Collectors.toList());

        return ViewStatementsResponseModel.builder()
                .accountId(accountModel.getId())
                .accountNumber(hashAccountNumber(accountModel.getAccountNumber()))
                .statementDetails(statementDetails)
                .build();
    }

    private String hashAccountNumber(String accountNumber) {

        return accountNumber.replace(accountNumber.substring(0, accountNumber.length() - 3), "#########");
    }
}

