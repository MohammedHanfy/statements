package com.mah.statements.entity.provider;

import com.mah.statements.entity.Statement;

import java.util.ArrayList;
import java.util.List;

public class StatementProvider {

    public static List<Statement> buildStatements() {

        List<Statement> statements = new ArrayList<>();
        statements.add(buildStatement(2L, "01.11.2021", "2000"));
        statements.add(buildStatement(1L, "01.12.2021", "1500"));
        statements.add(buildStatement(1L, "01.01.2022", "1500"));
        statements.add(buildStatement(1L, "01.02.2022", "1000"));

        return statements;
    }

    public static Statement buildStatement(Long id, String dateField, String amount) {

        return Statement.builder()
                .id(id)
                .accountId(1L)
                .dateField(dateField)
                .amount(amount)
                .build();
    }
}
