package com.example.kata.model;

import com.example.kata.domain.Statement;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class StatementDto {
    private BigDecimal amount;
    private Date date;
    private BigDecimal balance;

    public static StatementDto of(Statement statement) {
        return new StatementDto(statement.getAmount(), statement.getDate(), statement.getBalance());
    }

    public static List<StatementDto> of(List<Statement> statements) {
        return statements.stream().map(StatementDto::of).collect(Collectors.toList());
    }
}
