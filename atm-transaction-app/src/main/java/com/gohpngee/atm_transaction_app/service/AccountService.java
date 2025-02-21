package com.gohpngee.atm_transaction_app.service;

import com.gohpngee.atm_transaction_app.dto.CreateAccountDTO;
import com.gohpngee.atm_transaction_app.exception.InsufficientFundsException;
import org.springframework.beans.factory.annotation.Autowired;
import java.math.BigDecimal;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gohpngee.atm_transaction_app.model.Account;
import com.gohpngee.atm_transaction_app.repository.AccountRepository;

import com.gohpngee.atm_transaction_app.exception.AccountNotFoundException;
import com.gohpngee.atm_transaction_app.exception.InsufficientFundsException;
import org.springframework.web.bind.annotation.RequestBody;

@Service
public class AccountService {
    private final AccountRepository accountRepository;

    @Autowired
    public AccountService(AccountRepository accountRepository){
        this.accountRepository = accountRepository;
    }

    @Transactional
    public void createAccount(CreateAccountDTO dto) {
        Account account = new Account(dto.getAccountNumber(),
                dto.getAccountHolderName(),
                dto.getAccountType(),
                dto.getBalance());
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
            throw new InsufficientFundsException("Insufficient Funds.");
        }

        account.setBalance(account.getBalance().subtract(amount));
        accountRepository.save(account);
    }

    @Transactional
    public void transfer(String senderAccountNumber, String receiverAccountNumber, BigDecimal amount) {
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Transfer amount must be more than zero.");
        }

        Account sender = accountRepository.findByAccountNumber(senderAccountNumber)
                .orElseThrow(() -> new AccountNotFoundException("Sender account does not exist."));
        Account receiver = accountRepository.findByAccountNumber(receiverAccountNumber)
                .orElseThrow(() -> new AccountNotFoundException("Receiver account does not exist."));

        if (sender.getBalance().compareTo(amount) < 0) {
            throw new InsufficientFundsException("Insufficient funds for transaction.");
        }

        sender.setBalance(sender.getBalance().subtract(amount));
        receiver.setBalance(receiver.getBalance().add(amount));

        accountRepository.save(sender);
        accountRepository.save(receiver);
    }

    public BigDecimal showBalance(String accountNumber) {
        Account account = accountRepository.findByAccountNumber(accountNumber)
                .orElseThrow(() -> new AccountNotFoundException("Account number does not exist in the database."));
        return account.getBalance();
    }
}