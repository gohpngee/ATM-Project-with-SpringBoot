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

    @Test
    void testWithdraw() {
        String mockAccountNumber = "20011210";
        BigDecimal mockWithdrawAmount = BigDecimal.valueOf(5000);

        //Mock an account
        Account mockAccount = new Account(mockAccountNumber, "Tammy Chan", "Savings", BigDecimal.valueOf(7025));
        when(accountRepository.findByAccountNumber(mockAccountNumber)).thenReturn(Optional.of(mockAccount));

        accountService.withdraw(mockAccountNumber, mockWithdrawAmount);

        //checking that the balance is updated correctly
        assertEquals(BigDecimal.valueOf(2025), mockAccount.getBalance());

        verify(accountRepository, times(1)).save(mockAccount);
    }

    @Test
    void testShowBalance() {
        String mockAccountNumber = "19990826";
        Account mockAccount = new Account(mockAccountNumber, "Png Ee", "Savings", BigDecimal.valueOf(1999));
        when(accountRepository.findByAccountNumber(mockAccountNumber)).thenReturn(Optional.of(mockAccount));

        BigDecimal mockBalance = accountService.showBalance(mockAccountNumber);

        assertEquals(BigDecimal.valueOf(1999), mockBalance);

        verify(accountRepository, times(1)).findByAccountNumber(mockAccountNumber);
    }

    @Test
    void testTransfer() {
        String mockSenderAccNum = "123456";
        String mockReceiverAccNum = "654321";
        BigDecimal mockTransfAmount = BigDecimal.valueOf(5090);

        Account mockSenderAcc = new Account(mockSenderAccNum, "Brian Goh", "Savings", BigDecimal.valueOf(6000));
        Account mockReceiverAcc = new Account(mockReceiverAccNum, "Tammy Chan", "Savings", BigDecimal.valueOf(10));

        when(accountRepository.findByAccountNumber(mockSenderAccNum)).thenReturn(Optional.of(mockSenderAcc));
        when(accountRepository.findByAccountNumber(mockReceiverAccNum)).thenReturn(Optional.of(mockReceiverAcc));

        accountService.transfer(mockSenderAccNum, mockReceiverAccNum, mockTransfAmount);

        assertEquals(BigDecimal.valueOf(10), mockSenderAcc.getBalance());
        assertEquals(BigDecimal.valueOf(6000), mockReceiverAcc.getBalance());

        verify(accountRepository, times(2));
    }

    @Test
    void testCreateAccount() {
        String mockAccountNumber = "094511";
        String mockAccHolderName = "Ying Yeng";
        String mockAccountType = "Savings";
        BigDecimal mockBalance = BigDecimal.valueOf(12345);

        Account mockAccount = new Account(mockAccountNumber, mockAccHolderName, mockAccountType, mockBalance);

        when(accountRepository.findByAccountNumber(mockAccountNumber)).thenReturn(Optional.of(mockAccount));

        accountService.createAccount(mockAccountNumber, mockAccHolderName, mockAccountType, mockBalance);
        verify(accountRepository, times(1)).save(mockAccount);
    }
}
