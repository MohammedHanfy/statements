package com.mah.statements.service;

import com.mah.statements.service.model.ViewStatementsRequestModel;
import com.mah.statements.service.model.ViewStatementsResponseModel;
import org.springframework.stereotype.Service;

@Service
public interface StatementsService {

    ViewStatementsResponseModel viewStatements(ViewStatementsRequestModel viewStatementsRequestModel);
}
