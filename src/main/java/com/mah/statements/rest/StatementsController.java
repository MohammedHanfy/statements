package com.mah.statements.rest;

import com.mah.statements.mappers.ViewStatementsRequestMapper;
import com.mah.statements.mappers.ViewStatementsResponseMapper;
import com.mah.statements.rest.dto.ViewStatementsRequest;
import com.mah.statements.rest.dto.ViewStatementsResponse;
import com.mah.statements.service.StatementsService;
import com.mah.statements.service.model.ViewStatementsRequestModel;
import com.mah.statements.util.enums.UserType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Locale;
import java.util.Optional;

@RestController
@RequestMapping("/api/statements")
public class StatementsController {

    private final StatementsService statementsService;

    public StatementsController(StatementsService statementsService) {
        this.statementsService = statementsService;
    }

    @GetMapping(value = "/viewStatements")
    public ResponseEntity<ViewStatementsResponse> viewStatements(HttpServletRequest httpServletRequest,
                                                                 @Valid @RequestBody ViewStatementsRequest viewStatementsRequest) {

        UserType userType = UserType.valueOf(httpServletRequest.getUserPrincipal().getName().toUpperCase(Locale.getDefault()));

        ViewStatementsRequestModel viewStatementsRequestModel = ViewStatementsRequestMapper.INSTANCE.mapToDto(viewStatementsRequest);

        viewStatementsRequestModel.setUserType(userType);

        return Optional.ofNullable(statementsService.viewStatements(viewStatementsRequestModel))
                .map(ViewStatementsResponseMapper.INSTANCE::map)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.noContent().build());

    }
}
