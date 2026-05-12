package com.gohpngee.atm_transaction_app.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gohpngee.atm_transaction_app.dto.CreateAccountDTO;
import com.gohpngee.atm_transaction_app.dto.DepositWithdrawDTO;
import com.gohpngee.atm_transaction_app.dto.ShowBalanceDTO;
import com.gohpngee.atm_transaction_app.dto.TransferDTO;
import com.gohpngee.atm_transaction_app.model.Account;
import com.gohpngee.atm_transaction_app.service.AccountService;

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

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/create")
    public ResponseEntity<String> createAccount(@RequestBody CreateAccountDTO dto) {
        accountService.createAccount(dto);
        return ResponseEntity.ok("Account " + dto.getAccountHolderName() + " Created Successfully!");
    }

    @PreAuthorize("hasRole('USER', 'ADMIN')")
    @PutMapping("/deposit")
    public ResponseEntity<String> deposit(@RequestBody DepositWithdrawDTO dto) {
        accountService.deposit(dto);
        return ResponseEntity.ok("Deposit of " + dto.getAmount() + " is successful for account " + dto.getAccountNumber());
    }

    @PreAuthorize("hasRole('USER', 'ADMIN')")
    @PutMapping("/withdraw")
    public ResponseEntity<String> withdraw(@RequestBody DepositWithdrawDTO dto) {
        accountService.withdraw(dto);
        return ResponseEntity.ok( "Withdraw of " + dto.getAmount() + " is successful for account " + dto.getAccountNumber());
    }

    @PreAuthorize("hasRole('USER', 'ADMIN')")
    @PutMapping("/transfer")
    public ResponseEntity<String> transfer(@RequestBody TransferDTO dto) {
        accountService.transfer(dto);
        return ResponseEntity.ok("Transfer of $" + dto.getAmount() + " is successful from account " + dto.getSenderAccountNumber() + " to account " + dto.getReceiverAccountNumber());
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/balance/{accountNumber}")
    public ResponseEntity<String> showBalance(@PathVariable String accountNumber) {
        ShowBalanceDTO dto = new ShowBalanceDTO(accountNumber, null, null);

        Account account = accountService.showBalance(dto);
        return ResponseEntity.ok("Your balance for account number: " + account.getAccountNumber()
        + ", for the " + account.getAccountType()
        + " account is: " + account.getBalance());
    }
}
