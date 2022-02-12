package com.mah.statements.repository;

import com.mah.statements.entity.Account;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
public class AccountRepositoryTest {

    @Autowired
    AccountRepository accountRepository;

    @Test
    public void findAllAccounts() {

        Assertions.assertTrue(accountRepository.findAll().isEmpty());
    }

    @Test
    public void findAllAccountsWhenAddNewAccount() {

        Account account = new Account();
        account.setAccountType("TEST_ACCOUNT_TYPE");
        account.setAccountNumber("TEST_ACCOUNT_NUMBER");

        Assertions.assertNotNull(accountRepository.save(account));

        Assertions.assertFalse(accountRepository.findAll().isEmpty());
    }
}
