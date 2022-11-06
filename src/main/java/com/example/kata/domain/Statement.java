package com.example.kata.domain;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "STATEMENT")
public class Statement {
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private BigDecimal amount;
    @Column
    private Date date;
    @Column
    private BigDecimal balance;

    public Statement(BigDecimal amount, Date date, BigDecimal balance) {
        this.amount = amount;
        this.date = date;
        this.balance = balance;
    }
}
