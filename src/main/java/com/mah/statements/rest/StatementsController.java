package com.mah.statements.rest;

import com.mah.statements.mappers.ViewStatementsRequestMapper;
import com.mah.statements.mappers.ViewStatementsResponseMapper;
import com.mah.statements.rest.dto.ViewStatementsRequest;
import com.mah.statements.rest.dto.ViewStatementsResponse;
import com.mah.statements.service.StatementsService;
import com.mah.statements.service.model.ViewStatementsRequestModel;
import com.mah.statements.util.enums.UserType;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping("/api/statements")
@RequiredArgsConstructor
public class StatementsController {

    private final StatementsService statementsService;

    @PostMapping(value = "/viewStatements")
    public ResponseEntity<ViewStatementsResponse> viewStatements(@Valid @RequestBody ViewStatementsRequest viewStatementsRequest) {

        UserType userType = UserType.ADMIN;
        ViewStatementsRequestModel viewStatementsRequestModel = ViewStatementsRequestMapper.INSTANCE.mapToDto(viewStatementsRequest);
        viewStatementsRequestModel.setUserType(userType);

        return Optional.of(statementsService.viewStatements(viewStatementsRequestModel))
                .map(ViewStatementsResponseMapper.INSTANCE::map)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.noContent().build());

    }
}
