package com.gohpngee.atm_transaction_app.service;

import com.gohpngee.atm_transaction_app.dto.DepositWithdrawDTO;
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
        BigDecimal mockDepositAmount = BigDecimal.valueOf(1926);

        Account mockAccount = Account.builder()
                                .accountNumber(mockAccountNumber)
                                .accountHolderName("Brian Goh")
                                .accountType(Account.AccountType.SAVINGS)
                                .balance(BigDecimal.valueOf(100))
                                .build();

        when(accountRepository.findByAccountNumber(mockAccountNumber)).thenReturn(Optional.of(mockAccount));

        DepositWithdrawDTO dto = new DepositWithdrawDTO(mockAccountNumber, Account.AccountType.SAVINGS, mockDepositAmount);
        accountService.deposit(dto);

        assertEquals(BigDecimal.valueOf(2026), mockAccount.getBalance());
        verify(accountRepository, times(1)).save(mockAccount);
    }

    @Test
    void testWithdraw() {
        String mockAccountNumber = "20011210";
        BigDecimal mockWithdrawAmount = BigDecimal.valueOf(5000);

        //Mock an account
        Account mockAccount = Account.builder()
                                .accountNumber(mockAccountNumber)
                                .accountHolderName("Tammy Chan")
                                .accountType(Account.AccountType.SAVINGS)
                                .balance(BigDecimal.valueOf(7026))
                                .build();

        when(accountRepository.findByAccountNumber(mockAccountNumber)).thenReturn(Optional.of(mockAccount));

        DepositWithdrawDTO dto = new DepositWithdrawDTO(mockAccountNumber, Account.AccountType.SAVINGS, mockWithdrawAmount);
        accountService.withdraw(dto);

        //checking that the balance is updated correctly
        assertEquals(BigDecimal.valueOf(2026), mockAccount.getBalance());
        verify(accountRepository, times(1)).save(mockAccount);
    }

    @Test
    void testShowBalance() {
        String mockAccountNumber = "19990826";
        Account mockAccount = Account.builder()
                                .accountNumber(mockAccountNumber)
                                .accountHolderName("Brian Goh")
                                .accountType(Account.AccountType.SAVINGS)
                                .balance(BigDecimal.valueOf(7026))
                                .build();

        when(accountRepository.findByAccountNumber(mockAccountNumber)).thenReturn(Optional.of(mockAccount));

        assertEquals(BigDecimal.valueOf(7026), mockAccount.getBalance());
        ShowBalanceDTO dto = new ShowBalanceDTO(mockAccountNumber, Account.AccountType.SAVINGS, null);
        accountService.showBalance(dto);

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

    /*@Test
    void testCreateAccount() {
        String mockAccountNumber = "094511";
        String mockAccHolderName = "Ying Yeng";
        String mockAccountType = "Savings";
        BigDecimal mockBalance = BigDecimal.valueOf(12345);

        Account mockAccount = new Account(mockAccountNumber, mockAccHolderName, mockAccountType, mockBalance);

        when(accountRepository.findByAccountNumber(mockAccountNumber)).thenReturn(Optional.of(mockAccount));

        accountService.createAccount(mockAccountNumber, mockAccHolderName, mockAccountType, mockBalance);
        verify(accountRepository, times(1)).save(mockAccount);
    }*/
}
