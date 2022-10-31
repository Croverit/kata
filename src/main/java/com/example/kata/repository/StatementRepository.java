package com.example.kata.repository;

import com.example.kata.domain.Statement;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StatementRepository extends CrudRepository<Statement, Long> {

}
