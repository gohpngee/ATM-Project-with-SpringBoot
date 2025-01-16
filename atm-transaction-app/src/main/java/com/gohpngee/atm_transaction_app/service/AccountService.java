package com.gohpngee.atm_transaction_app.service;

import org.springframework.beans.factory.annotation.Autowired;
import java.math.BigDecimal;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gohpngee.atm_transaction_app.model.Account;
import com.gohpngee.atm_transaction_app.repository.AccountRepository;

import javax.naming.InsufficientResourcesException;
import javax.security.auth.login.AccountNotFoundException;

@Service
public class AccountService {
    private final AccountRepository accountRepository;

    @Autowired
    public AccountService(AccountRepository accountRepository){
        this.accountRepository = accountRepository;
    }
    private Account newAccount;

    @Transactional
    public void createAccount(String accountNumber, String accountHolderName, String accountType, BigDecimal balance) {
        Account account = new Account(accountNumber, accountHolderName, accountType, balance);
        accountRepository.save(account);
    }

    @Transactional
    public void deposit(String accountNumber, BigDecimal amount) {
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Deposit amount must be more than zero.");
        }
        Account account = accountRepository.findByAccountNumber(accountNumber).orElseThrow(() -> new AccountNotFoundException("Account not found."));

        account.setBalance(account.getBalance().add(amount));
        accountRepository.save(account);
    }

    @Transactional
    public void withdraw(String accountNumber, BigDecimal amount) {
        if (amount.compareTo(BigDecimal.ZERO) <= 0){
            throw new IllegalArgumentException("Amount withdrawn must be more than zero.");
        }

        Account account = accountRepository.findByAccountNumber(accountNumber).orElseThrow(() -> new AccountNotFoundException("Account not found."));

        if (account.getBalance().compareTo(amount) < 0){
            throw new InsufficientResourcesException("Insufficient Funds.");
        }

        account.setBalance(account.getBalance().subtract(amount));
        accountRepository.save(account);
    }
}