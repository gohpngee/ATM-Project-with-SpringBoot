package com.gohpngee.atm_transaction_app.controller;

import com.gohpngee.atm_transaction_app.service.AccountService;
import com.gohpngee.atm_transaction_app.model.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
public class AccountController {
    private final AccountService accountService;

    @Autowired
    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }
}
