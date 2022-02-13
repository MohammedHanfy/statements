package com.mah.statements.repository;

import com.mah.statements.entity.Statement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StatementRepository extends JpaRepository<Statement, Long> {

    List<Statement> findAllByAccountId(Long accountId);
}
