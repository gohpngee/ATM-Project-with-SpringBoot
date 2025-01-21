package com.gohpngee.atm_transaction_app.service;

import com.gohpngee.atm_transaction_app.model.Account;
import com.gohpngee.atm_transaction_app.repository.AccountRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.swing.text.html.Option;
import java.math.BigDecimal;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class AccountServiceTest {
    @Mock
    private AccountRepository accountRepository;

    @InjectMocks
    private AccountService accountService;

    @BeforeEach
    void SetUp() {
        //Initialize Mocks
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testDeposit() {
        String mockAccountNumber = "123456";
        BigDecimal mockDepositAmount = BigDecimal.valueOf(2025);

        //Mock an existing account
        Account mockAccount = new Account(mockAccountNumber, "Brian Goh", "Savings", BigDecimal.valueOf(100));
        when(accountRepository.findByAccountNumber(mockAccountNumber)).thenReturn(Optional.of(mockAccount));

        //call the deposit method
        accountService.deposit(mockAccountNumber, mockDepositAmount);

        //Verify the balance if its updated
        assertEquals(BigDecimal.valueOf(2125), mockAccount.getBalance());
        verify(accountRepository, times(1)).save(mockAccount);
    }
}
