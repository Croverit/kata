package com.example.kata.service;

import com.example.kata.domain.Account;
import com.example.kata.domain.Statement;
import com.example.kata.repository.AccountRepository;
import com.example.kata.utils.constant.OperationType;
import com.example.kata.utils.exception.AccountNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
class AccountServiceTest {

    @Mock
    AccountRepository accountRepository;
    @InjectMocks
    AccountService accountService;

    @Test
    void call_print_statements_with_inexistant_account() {
        // When
        Mockito.when(accountRepository.findById(any())).thenThrow(AccountNotFoundException.class);

        // Then
        Assertions.assertThrows(AccountNotFoundException.class, () -> {
            accountService.printStatements(1L);
        });
    }

    @Test
    void call_print_statements_nominal_case() {

        // Given
        Date now = new Date();
        Statement expectedStatement = new Statement(1L, new BigDecimal(1_500), now, new BigDecimal(50_000));
        List<Statement> expectedStatements = new ArrayList<>();
        expectedStatements.add(expectedStatement);
        Account account = new Account();
        account.setStatements(expectedStatements);

        // When
        Mockito.when(accountRepository.findById(any())).thenReturn(Optional.of(account));
        List<Statement> actualStatements = accountService.printStatements(1L);

        // Then
        Assertions.assertEquals(1, actualStatements.size());
        Assertions.assertEquals(expectedStatements, actualStatements);
    }

    @Test
    void call_doOperation_to_deposit_an_amount() {

        // Given
        Account account = new Account(1L, new BigDecimal(11_000), null);

        // When
        Mockito.when(accountRepository.findById(any())).thenReturn(Optional.of(account));
        Account actualAccount = accountService.doOperation(1L, new BigDecimal(1_000), OperationType.DEPOSIT);

        // Then
        Assertions.assertEquals(12_000, actualAccount.getBalance().intValue());
    }

    @Test
    void call_doOperation_to_withdraw_an_amount() {
        // Given
        Account account = new Account(1L, new BigDecimal(11_000), null);

        // When
        Mockito.when(accountRepository.findById(any())).thenReturn(Optional.of(account));
        Account actualAccount = accountService.doOperation(1L, new BigDecimal(1_000), OperationType.WITHDRAW);

        // Then
        Assertions.assertEquals(10_000, actualAccount.getBalance().intValue());
    }
}
