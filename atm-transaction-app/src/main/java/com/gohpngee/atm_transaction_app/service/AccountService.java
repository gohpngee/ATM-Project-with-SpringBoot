package com.gohpngee.atm_transaction_app.service;

import com.gohpngee.atm_transaction_app.dto.CreateAccountDTO;
import com.gohpngee.atm_transaction_app.dto.DepositWithdrawDTO;
import com.gohpngee.atm_transaction_app.dto.ShowBalanceDTO;
import com.gohpngee.atm_transaction_app.dto.TransferDTO;
import com.gohpngee.atm_transaction_app.exception.InsufficientFundsException;

import java.math.BigDecimal;
import java.util.Optional;

import com.gohpngee.atm_transaction_app.model.Account;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import com.gohpngee.atm_transaction_app.repository.AccountRepository;

import com.gohpngee.atm_transaction_app.exception.AccountNotFoundException;

@RequiredArgsConstructor
@Service
public class AccountService {
    private final AccountRepository accountRepository;

    @Transactional
    public void createAccount(CreateAccountDTO dto) {
        Account account = Account.builder()
                .accountNumber(dto.getAccountNumber())
                .accountHolderName(dto.getAccountHolderName())
                .accountType(Account.AccountType.valueOf(dto.getAccountType()))
                .balance(dto.getBalance())
                .build();
        accountRepository.save(account);
    }

    @Transactional
    public void deposit(DepositWithdrawDTO dto) {
        Optional<Account> optionalAccount = accountRepository.findByAccountNumber(dto.getAccountNumber()); //avoiding NullPointerException

        if (optionalAccount.isEmpty()) {
            throw new AccountNotFoundException("Account not found");
        }

        Account account = optionalAccount.get(); //extracts the Account object from the Optional<Account> wrapper

        BigDecimal updatedBalance = account.getBalance().add(dto.getAmount());
        account.setBalance(updatedBalance);

        accountRepository.save(account);
    }

    @Transactional
    public void withdraw(DepositWithdrawDTO dto) {
        if (dto.getAmount().compareTo(BigDecimal.ZERO) <= 0){
            throw new IllegalArgumentException("Amount withdrawn must be more than zero.");
        }

        Optional<Account> optionalAccount = accountRepository.findByAccountNumber(dto.getAccountNumber());

        if (optionalAccount.isEmpty()) {
            throw new AccountNotFoundException("Account not found");
        }

        Account account = optionalAccount.get();

        if (account.getBalance().compareTo(dto.getAmount()) < 0)
            throw new InsufficientFundsException("Insufficient Funds for withdrawal");

        account.setBalance(account.getBalance().subtract(dto.getAmount()));
        accountRepository.save(account);
    }

    @Transactional
    public void transfer(TransferDTO dto) {
        if (dto.getAmount().compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Transfer amount must be more than zero.");
        }

        Optional<Account> optionalSender = accountRepository.findByAccountNumber(dto.getSenderAccountNumber());
        if (optionalSender.isEmpty()){
            throw new AccountNotFoundException("Sender account not found.");
        }
        Account sender = optionalSender.get();

        Optional<Account> optionalReceiver = accountRepository.findByAccountNumber(dto.getReceiverAccountNumber());
        if (optionalReceiver.isEmpty()){
            throw new AccountNotFoundException("Recepient account not found.");
        }
        Account receiver = optionalReceiver.get();

        if (sender.getBalance().compareTo(dto.getAmount()) < 0) {
            throw new InsufficientFundsException("Insufficient funds for transaction.");
        }

        sender.setBalance(sender.getBalance().subtract(dto.getAmount()));
        receiver.setBalance(receiver.getBalance().add(dto.getAmount()));

        accountRepository.save(sender);
        accountRepository.save(receiver);
        }

    public Account showBalance(ShowBalanceDTO dto) {
        Optional<Account> optionalAccount = accountRepository.findByAccountNumber(dto.getAccountNumber());
        if (optionalAccount.isEmpty()) {
            throw new AccountNotFoundException("Account not found.");
        }
        Account account = optionalAccount.get();
        return account;
    }
}