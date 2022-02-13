package com.mah.statements.entity.provider;

import com.mah.statements.entity.Account;

public class AccountProvider {

    public static Account buildAccount() {

        return Account.builder()
                .id(1L)
                .accountType("Current")
                .accountNumber("1234554321")
                .build();
    }
}
