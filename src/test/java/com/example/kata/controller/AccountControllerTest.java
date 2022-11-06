package com.example.kata.controller;

import com.example.kata.domain.Account;
import com.example.kata.domain.Statement;
import com.example.kata.model.AccountDto;
import com.example.kata.model.StatementDto;
import com.example.kata.service.AccountService;
import com.example.kata.utils.exception.AccountNotFoundException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class AccountControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    ObjectMapper objectMapper;
    @MockBean
    private AccountService accountService;

    @Test
    void call_print_statements_with_invalid_authorization_header() throws Exception {
        this.mockMvc.perform(get("/bank-management/account/1/statements").header("Authorization", ""))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void call_print_statements_with_inexistant_account() throws Exception {
        // When
        Mockito.when(accountService.printStatements(any())).thenThrow(AccountNotFoundException.class);

        // Then
        this.mockMvc.perform(get("/bank-management/account/4/statements").header("Authorization", "Basic dXNlcjoxMjM="))
                .andExpect(status().isNotFound());
    }

    @Test
    void call_print_statements_nominal_case() throws Exception {
        // Given
        Date now = new Date();
        StatementDto statementDto = new StatementDto(new BigDecimal(1_500), now, new BigDecimal(50_000));
        List<StatementDto> statementsDtos = new ArrayList<>();
        statementsDtos.add(statementDto);
        String expectedContent = objectMapper.writeValueAsString(statementsDtos);

        // When
        Statement statement = new Statement(1L, new BigDecimal(1_500), now, new BigDecimal(50_000));
        List<Statement> statements = new ArrayList<>();
        statements.add(statement);
        Mockito.when(accountService.printStatements(any())).thenReturn(statements);

        // Then
        this.mockMvc.perform(get("/bank-management/account/1/statements").header("Authorization", "Basic dXNlcjoxMjM="))
                .andExpect(status().isOk())
                .andExpect(content().string(expectedContent));
    }

    @Test
    void call_doOperation_with_invalid_authorization_header() throws Exception {
        this.mockMvc.perform(put("/bank-management/account/1").header("Authorization", ""))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void call_doOperation_with_invalid_opreationType() throws Exception {
        this.mockMvc.perform(put("/bank-management/account/1?operation=random").header("Authorization", "Basic dXNlcjoxMjM="))
                .andExpect(status().isBadRequest());
    }

    @Test
    void call_doOperation_to_deposit_an_amount() throws Exception {
        // Given
        AccountDto accountDto = new AccountDto(new BigDecimal(11_000));
        String expectedContent = objectMapper.writeValueAsString(accountDto);

        // When
        Account account = new Account(1L, new BigDecimal(11_000), null);
        Mockito.when(accountService.doOperation(any(), any(), any())).thenReturn(account);

        // Then
        this.mockMvc.perform(put("/bank-management/account/1?operation=deposit&amount=1000").header("Authorization", "Basic dXNlcjoxMjM="))
                .andExpect(status().isOk())
                .andExpect(content().string(expectedContent));

    }

    @Test
    void call_doOperation_to_withdraw_an_amount() throws Exception {
        // Given
        AccountDto accountDto = new AccountDto(new BigDecimal(9_000));
        String expectedContent = objectMapper.writeValueAsString(accountDto);

        // When
        Account account = new Account(1L, new BigDecimal(9_000), null);
        Mockito.when(accountService.doOperation(any(), any(), any())).thenReturn(account);

        // Then
        this.mockMvc.perform(put("/bank-management/account/1?operation=withdraw&amount=1000").header("Authorization", "Basic dXNlcjoxMjM="))
                .andExpect(status().isOk())
                .andExpect(content().string(expectedContent));

    }
}
