package com.gohpngee.atm_transaction_app.service;

import org.springframework.beans.factory.annotation.Autowired;
import java.math.BigDecimal;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gohpngee.atm_transaction_app.model.Account;
import com.gohpngee.atm_transaction_app.repository.AccountRepository;

@Service
public class AccountService {
    private final AccountRepository accountRepository;
    private Account newAccount;

    public void createAccount(String accountNumber, String accountHolderName, String accountType, BigDecimal balance) {
        this.newAccount = new Account(accountNumber, accountHolderName, accountType, balance);
    }

    public void deposit(BigDecimal amount) {
        if (amount.compareTo(BigDecimal.ZERO) > 0) {
            newAccount.setBalance(newAccount.getBalance().add(amount));
        } else {
            throw new IllegalArgumentException("Deposit amount must be more than zero.");
        }
    }

    public void withdraw(BigDecimal amount) {
        if ((amount.compareTo(BigDecimal.ZERO) > 0) && (newAccount.getBalance().compareTo(amount) >= 0)){
            newAccount.setBalance(newAccount.getBalance().subtract(amount));
        } else {
            throw new IllegalArgumentException("Insufficient Funds.");
        }
    }
}