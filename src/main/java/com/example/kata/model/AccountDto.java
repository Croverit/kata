package com.example.kata.model;

import com.example.kata.domain.Account;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AccountDto {
    private BigDecimal balance;

    public static AccountDto of(Account account) {
        return new AccountDto(account.getBalance());
    }
}
