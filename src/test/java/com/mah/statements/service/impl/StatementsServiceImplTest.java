package com.mah.statements.service.impl;

import com.mah.statements.entity.Account;
import com.mah.statements.entity.Statement;
import com.mah.statements.entity.provider.AccountProvider;
import com.mah.statements.entity.provider.StatementProvider;
import com.mah.statements.repository.AccountRepository;
import com.mah.statements.repository.StatementRepository;
import com.mah.statements.service.model.ViewStatementsRequestModel;
import com.mah.statements.service.model.ViewStatementsResponseModel;
import com.mah.statements.service.provider.ViewStatementsRequestModelProvider;
import com.mah.statements.service.provider.ViewStatementsResponseModelProvider;
import com.mah.statements.util.ViewStatementActionType;
import com.mah.statements.util.enums.UserType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.persistence.EntityNotFoundException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

@ExtendWith(MockitoExtension.class)
class StatementsServiceImplTest {

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private StatementRepository statementRepository;

    @InjectMocks
    private StatementsServiceImpl statementsService;

    private static final Account account = AccountProvider.buildAccount();

    private static final List<Statement> statements = StatementProvider.buildStatements();

    @Test
    void viewStatements_withNotFoundAccount_throwsEntityNotFoundException() {

        Throwable entityNotFoundException = Assertions.assertThrows(
                EntityNotFoundException.class, () -> statementsService.viewStatements(
                        ViewStatementsRequestModelProvider.buildViewStatementsRequestModel(UserType.ADMIN, account).build()));

        Assertions.assertEquals(entityNotFoundException.getMessage(), String.format("Account with ID: %s not found", account.getId()));
    }

    @ParameterizedTest
    @MethodSource("viewValidStatementsRequestModelScenarios")
    void viewStatements_withValidViewStatementsRequestModel_returnsOptionalListContainingOnlyTheSelectedNode(
            final ViewStatementsRequestModel viewStatementsRequestModel,
            final ViewStatementsResponseModel expectedResponseModel) {

        Mockito.when(accountRepository.findById(viewStatementsRequestModel.getAccountId()))
                .thenReturn(Optional.of(account));

        Mockito.when(statementRepository.findAllByAccountId(viewStatementsRequestModel.getAccountId()))
                .thenReturn(statements);

        ViewStatementsResponseModel responseModel = statementsService.viewStatements(viewStatementsRequestModel);

        assertFilterMessages(responseModel, expectedResponseModel);
    }

    private static Stream<Arguments> viewValidStatementsRequestModelScenarios() {

        Map<String, Object> requestMap = new HashMap<>();
        requestMap.put("fromDate", LocalDate.now().minusYears(1));
        requestMap.put("toDate", LocalDate.now());
        requestMap.put("fromAmount", BigDecimal.ZERO);
        requestMap.put("toAmount", BigDecimal.valueOf(10000));

        return Stream.of(
                Arguments.of(
                        ViewStatementsRequestModelProvider.buildViewStatementsRequestModel(UserType.ADMIN, account)
                                .fromDate(LocalDate.now().minusYears(1))
                                .toDate(LocalDate.now())
                                .fromAmount(BigDecimal.ZERO)
                                .toAmount(BigDecimal.valueOf(10000))
                                .build(),
                        ViewStatementsResponseModelProvider.buildViewStatementsResponseModelBuilder(
                                account,
                                statements,
                                ViewStatementActionType.FILTER_DATE_AND_AMOUNT,
                                requestMap)

                ),
                Arguments.of(
                        ViewStatementsRequestModelProvider.buildViewStatementsRequestModel(UserType.ADMIN, account)
                                .fromDate(LocalDate.now().minusYears(1))
                                .toDate(LocalDate.now())
                                .build(),
                        ViewStatementsResponseModelProvider.buildViewStatementsResponseModelBuilder(
                                account,
                                statements,
                                ViewStatementActionType.FILTER_DATE,
                                requestMap)
                ),
                Arguments.of(
                        ViewStatementsRequestModelProvider.buildViewStatementsRequestModel(UserType.ADMIN, account)
                                .fromAmount(BigDecimal.ZERO)
                                .toAmount(BigDecimal.valueOf(10000))
                                .build(),
                        ViewStatementsResponseModelProvider.buildViewStatementsResponseModelBuilder(
                                account,
                                statements,
                                ViewStatementActionType.FILTER_AMOUNT,
                                requestMap)
                ),
                Arguments.of(
                        ViewStatementsRequestModelProvider.buildViewStatementsRequestModel(UserType.ADMIN, account)
                                .build(),
                        ViewStatementsResponseModelProvider.buildViewStatementsResponseModelBuilder(
                                account,
                                statements,
                                ViewStatementActionType.DEFAULT,
                                requestMap)
                ),
                Arguments.of(
                        ViewStatementsRequestModelProvider.buildViewStatementsRequestModel(UserType.USER, account)
                                .build(),
                        ViewStatementsResponseModelProvider.buildViewStatementsResponseModelBuilder(
                                account,
                                statements,
                                ViewStatementActionType.DEFAULT,
                                requestMap)
                )
        );
    }

    private void assertFilterMessages(
            final ViewStatementsResponseModel responseModel,
            final ViewStatementsResponseModel expectedResponseModel) {

        Assertions.assertEquals(responseModel.getAccountId(), expectedResponseModel.getAccountId());
        Assertions.assertEquals(responseModel.getAccountNumber(), expectedResponseModel.getAccountNumber());
        Assertions.assertEquals(responseModel.getStatementDetails().size(), expectedResponseModel.getStatementDetails().size());
    }
}