package com.example.kata.service;

import com.example.kata.domain.Account;
import com.example.kata.domain.Statement;
import com.example.kata.repository.AccountRepository;
import com.example.kata.utils.constant.OperationType;
import com.example.kata.utils.exception.AccountNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Service
public class AccountService {

    private final AccountRepository accountRepository;

    @Autowired
    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public List<Statement> printStatements(Long accountId) {
        return getAccount(accountId).getStatements();
    }

    @Transactional
    public Account doOperation(Long accountId, BigDecimal amount, OperationType operation) {
        // Find account by id.
        Account account = getAccount(accountId);

        // Do operation
        if (OperationType.DEPOSIT.equals(operation)) {
            deposit(account, amount);
        } else {
            withdraw(account, amount);
        }

        // Create Statement and add it to the list of statements
        account.getStatements().add(new Statement(amount, new Date(), account.getBalance()));

        return account;
    }

    private void deposit(Account account, BigDecimal amount) {
        // Update the account balance
        account.setBalance(account.getBalance().add(amount));
    }

    private void withdraw(Account account, BigDecimal amount) {
        // Update the account balance
        account.setBalance(account.getBalance().subtract(amount));

        // Create Statement and add it to the list of statements
        Statement statement = new Statement();
        statement.setAmount(amount);
        statement.setBalance(account.getBalance());
        statement.setDate(new Date());
        account.getStatements().add(statement);

    }

    /**
     * Get account if exists, throw a {@link AccountNotFoundException} if not.
     *
     * @param accountId Id of the account to retrieve
     * @return instance of the account
     */
    private Account getAccount(Long accountId) {
        return this.accountRepository.findById(accountId).<AccountNotFoundException>orElseThrow(() -> {
            throw new AccountNotFoundException("Account not found!");
        });
    }
}
