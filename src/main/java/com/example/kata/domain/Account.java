package com.example.kata.domain;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "ACCOUNT")
public class Account {
    @Id
    @Column
    private Long id;
    @Column
    private BigDecimal balance;
    @OneToMany(cascade = {CascadeType.ALL})
    private List<Statement> statements;

    public List<Statement> getStatements() {
        if (statements == null) {
            statements = new ArrayList<>();
        }
        return statements;
    }
}
