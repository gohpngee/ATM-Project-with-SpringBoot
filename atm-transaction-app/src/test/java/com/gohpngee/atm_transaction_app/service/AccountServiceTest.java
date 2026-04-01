package com.gohpngee.atm_transaction_app.service;

import com.gohpngee.atm_transaction_app.dto.DepositWithdrawDTO;
import com.gohpngee.atm_transaction_app.dto.ShowBalanceDTO;
import com.gohpngee.atm_transaction_app.dto.TransferDTO;
import com.gohpngee.atm_transaction_app.model.Account;
import com.gohpngee.atm_transaction_app.repository.AccountRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

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

        ShowBalanceDTO dto = new ShowBalanceDTO(mockAccountNumber, Account.AccountType.SAVINGS, null);
        Account account = accountService.showBalance(dto);
        assertEquals(BigDecimal.valueOf(7026), account.getBalance());

        verify(accountRepository, times(1)).findByAccountNumber(mockAccountNumber);
    }

    @Test
    void testTransfer() {
        String mockSenderAccNum = "345678";
        String mockReceiverAccNum = "654321";
        BigDecimal mockTransfAmount = BigDecimal.valueOf(5990);

        Account mockSenderAcc = Account.builder()
                                .accountNumber(mockSenderAccNum)
                                .accountHolderName("Brian Goh")
                                .accountType(Account.AccountType.SAVINGS)
                                .balance(BigDecimal.valueOf(6000))
                                .build();
        Account mockReceiverAcc = Account.builder()
                                  .accountNumber(mockReceiverAccNum)
                                  .accountHolderName("Tammy Chan")
                                  .accountType(Account.AccountType.SAVINGS)
                                  .balance(BigDecimal.valueOf(10))
                                  .build();

        when(accountRepository.findByAccountNumber(mockSenderAccNum)).thenReturn(Optional.of(mockSenderAcc));
        when(accountRepository.findByAccountNumber(mockReceiverAccNum)).thenReturn(Optional.of(mockReceiverAcc));

        TransferDTO dto = new TransferDTO(
                            mockSenderAccNum,
                            mockReceiverAccNum,
                            Account.AccountType.SAVINGS,
                            Account.AccountType.SAVINGS,
                            mockTransfAmount);

        accountService.transfer(dto);

        assertEquals(BigDecimal.valueOf(10), mockSenderAcc.getBalance());
        assertEquals(BigDecimal.valueOf(6000), mockReceiverAcc.getBalance());

        verify(accountRepository, times(1)).save(mockSenderAcc);
        verify(accountRepository, times(1)).save(mockReceiverAcc);
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
