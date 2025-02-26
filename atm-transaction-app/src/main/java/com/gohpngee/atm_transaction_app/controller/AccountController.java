package com.gohpngee.atm_transaction_app.controller;

import com.gohpngee.atm_transaction_app.dto.CreateAccountDTO;
import com.gohpngee.atm_transaction_app.dto.DepositWithdrawDTO;
import com.gohpngee.atm_transaction_app.dto.ShowBalanceDTO;
import com.gohpngee.atm_transaction_app.dto.TransferDTO;
import com.gohpngee.atm_transaction_app.model.Account;
import com.gohpngee.atm_transaction_app.service.AccountService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api/accounts")
public class AccountController {
    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping("/home")
    public String home() {
        return "Welcome to the home page";
    }

    @PostMapping("/create")
    public ResponseEntity<String> createAccount(@RequestBody CreateAccountDTO dto) {
        accountService.createAccount(dto);
        return ResponseEntity.ok("Account Created Successfully!");
    }

    @PutMapping("/deposit")
    public ResponseEntity<String> deposit(@RequestBody DepositWithdrawDTO dto) {
        accountService.deposit(dto);
        return ResponseEntity.ok("Deposit of " + dto.getAmount() + " successful for account " + dto.getAccountNumber());
    }

    @PutMapping("/withdraw")
    public ResponseEntity<String> withdraw(@RequestBody DepositWithdrawDTO dto) {
        accountService.withdraw(dto);
        return ResponseEntity.ok( "Withdraw of" + dto.getAmount() + " successful for account " + dto.getAccountNumber());
    }

    @PutMapping("/transfer")
    public ResponseEntity<String> transfer(@RequestBody TransferDTO dto) {
        accountService.transfer(dto);
        return ResponseEntity.ok("Transfer of $" + dto.getAmount() + " successful from account " + dto.getSenderAccountNumber() + " to account " + dto.getReceiverAccountNumber());
    }

    @GetMapping("/Balance")
    public ResponseEntity<String> showBalance(@RequestBody ShowBalanceDTO dto) {
        Account account = accountService.showBalance(dto);
        return ResponseEntity.ok("Your balance for account number: " + account.getAccountNumber() + ", for the " + account.getAccountType() + " is: " + account.getBalance());
    }
}
