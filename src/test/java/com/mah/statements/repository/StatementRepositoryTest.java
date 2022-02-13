package com.mah.statements.repository;

import com.mah.statements.entity.Account;
import com.mah.statements.entity.Statement;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Date;

@DataJpaTest
public class StatementRepositoryTest {

    @Autowired
    StatementRepository statementRepository;

    @Autowired
    AccountRepository accountRepository;

    @Test
    public void findAllStatements() {

        Assertions.assertTrue(statementRepository.findAll().isEmpty());
    }

    @Test
    public void findAllStatementsWhenAddNewStatement() {

        Account account = new Account();
        account.setAccountType("TEST_ACCOUNT_TYPE");
        account.setAccountNumber("TEST_ACCOUNT_NUMBER");

        account = accountRepository.save(account);

        Statement statement = new Statement();
        statement.setAccountId(account.getId());
        statement.setDateField(new Date().toString());
        statement.setAmount("TEST_AMOUNT");

        Assertions.assertNotNull(statementRepository.save(statement));

        Assertions.assertFalse(statementRepository.findAll().isEmpty());
    }
}