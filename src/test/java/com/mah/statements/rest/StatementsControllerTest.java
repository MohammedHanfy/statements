package com.mah.statements.rest;

import com.mah.statements.entity.Account;
import com.mah.statements.entity.Statement;
import com.mah.statements.entity.provider.AccountProvider;
import com.mah.statements.entity.provider.StatementProvider;
import com.mah.statements.rest.dto.ViewStatementsResponse;
import com.mah.statements.rest.provider.ViewStatementsRequestProvider;
import com.mah.statements.service.StatementsService;
import com.mah.statements.service.model.ViewStatementsResponseModel;
import com.mah.statements.service.provider.ViewStatementsResponseModelProvider;
import com.mah.statements.util.ViewStatementActionType;
import com.mah.statements.util.enums.UserType;
import com.sun.security.auth.UserPrincipal;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;

@ExtendWith(MockitoExtension.class)
class StatementsControllerTest {

    @Mock
    private StatementsService statementsService;

    @Mock
    private HttpServletRequest httpServletRequest;

    @InjectMocks
    private StatementsController statementsController;

    private static final Account account = AccountProvider.buildAccount();

    private static final List<Statement> statements = StatementProvider.buildStatements();

    @Test
    void whenCallingViewStatements_withValidRequest_thenReturnsResponseEntityWithOkStatus() {

        ViewStatementsResponseModel statementsResponseModel = ViewStatementsResponseModelProvider
                .buildViewStatementsResponseModelBuilder(account, statements, ViewStatementActionType.DEFAULT, new HashMap<>());

        Mockito.when(httpServletRequest.getUserPrincipal()).thenReturn(new UserPrincipal(UserType.ADMIN.name()));

        Mockito.when(statementsService.viewStatements(Mockito.any())).thenReturn(statementsResponseModel);

        ResponseEntity<ViewStatementsResponse> responseEntity = statementsController.viewStatements(httpServletRequest, ViewStatementsRequestProvider.buildViewStatementsRequest(account));

        Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

        Assertions.assertNotNull(responseEntity.getBody());

        Assertions.assertEquals(responseEntity.getBody().getAccountId(), account.getId());
    }

    @Test
    void whenCallingViewStatements_withInValidRequest_thenReturnsResponseEntityWithNoContentStatus() {

        Mockito.when(statementsService.viewStatements(Mockito.any())).thenReturn(null);

        Mockito.when(httpServletRequest.getUserPrincipal()).thenReturn(new UserPrincipal(UserType.ADMIN.name()));

        ResponseEntity<ViewStatementsResponse> responseEntity = statementsController.viewStatements(httpServletRequest, ViewStatementsRequestProvider.buildViewStatementsRequest(account));

        Assertions.assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());

        Assertions.assertNull(responseEntity.getBody());
    }
}