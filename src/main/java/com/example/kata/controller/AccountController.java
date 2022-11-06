package com.example.kata.controller;

import com.example.kata.model.AccountDto;
import com.example.kata.model.StatementDto;
import com.example.kata.service.AccountService;
import com.example.kata.utils.constant.OperationType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@Controller
@RequestMapping("bank-management")
public class AccountController {

    private final AccountService accountService;

    @Autowired
    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping("/account/{accountId}/statements")
    public ResponseEntity<List<StatementDto>> printStatements(@PathVariable Long accountId) {

        return ResponseEntity
                .ok(StatementDto.of(accountService.printStatements(accountId)));
    }

    @PutMapping("/account/{accountId}")
    public ResponseEntity<AccountDto> doOperation(@PathVariable Long accountId,
                                                  @RequestParam("operation") OperationType operation,
                                                  @RequestParam BigDecimal amount) {

        return ResponseEntity
                .ok(AccountDto.of(accountService.doOperation(accountId, amount, operation)));
    }
}
