package com.mah.statements.rest.provider;

import com.mah.statements.entity.Account;
import com.mah.statements.rest.dto.ViewStatementsRequest;

public class ViewStatementsRequestProvider {

    public static ViewStatementsRequest buildViewStatementsRequest(Account account) {

        return ViewStatementsRequest.builder()
                .accountId(account.getId())
                .build();
    }
}
