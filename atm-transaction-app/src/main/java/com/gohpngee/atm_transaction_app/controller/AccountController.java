package com.gohpngee.atm_transaction_app.controller;

import com.gohpngee.atm_transaction_app.dto.CreateAccountDTO;
import com.gohpngee.atm_transaction_app.service.AccountService;
import com.gohpngee.atm_transaction_app.model.Account;
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

    Account account2 = new Account();

    @GetMapping("/home")
    public String home() {
        return "Welcome to the home page";
    }

    @GetMapping("/{accountNumber}/balance")
    public BigDecimal showBalance(@PathVariable String accountNumber) {
        return accountService.showBalance(accountNumber);
    }

    @PostMapping("/create")
    public ResponseEntity<String> createAccount(@RequestBody CreateAccountDTO dto) {
        accountService.createAccount(dto);
        return ResponseEntity.ok("Account Created Successfully!");
    }

    @PutMapping("/{accountNumber}/deposit")
    public String deposit(@PathVariable String accountNumber, @RequestParam BigDecimal amount) {
        accountService.deposit(accountNumber, amount);
        return "Deposit of " + amount + " successful for account " + accountNumber;
    }

    @PutMapping("/{accountNumber}/withdraw")
    public String withdraw(@PathVariable String accountNumber, @RequestParam BigDecimal amount) {
        accountService.withdraw(accountNumber, amount);
        return "Withdraw of " + amount + " successful for account " + accountNumber;
    }

    @PutMapping("/transfer")
    public String transfer(@RequestParam String senderAccountNumber, @RequestParam String receiverAccountNumber, @RequestParam BigDecimal amount) {
        accountService.transfer(senderAccountNumber, receiverAccountNumber, amount);
        return "Transfer of " + amount + " successful from account " + senderAccountNumber + " to account " + receiverAccountNumber;
    }
}
