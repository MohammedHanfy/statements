package com.mah.statements.service.provider;

import com.mah.statements.entity.Account;
import com.mah.statements.service.model.ViewStatementsRequestModel;
import com.mah.statements.util.enums.UserType;

public class ViewStatementsRequestModelProvider {

    public static ViewStatementsRequestModel.ViewStatementsRequestModelBuilder buildViewStatementsRequestModel(UserType userType, Account account) {

        return ViewStatementsRequestModel.builder()
                .userType(userType)
                .accountId(account.getId());
    }
}
