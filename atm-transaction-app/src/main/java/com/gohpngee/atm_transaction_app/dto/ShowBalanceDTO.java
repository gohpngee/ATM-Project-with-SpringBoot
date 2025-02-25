package com.gohpngee.atm_transaction_app.dto;

import java.math.BigDecimal;

public class ShowBalanceDTO {
    private String accountNumber;
    private BigDecimal balance;

    public ShowBalanceDTO(String accountNumber, BigDecimal balance) {
        this.accountNumber = accountNumber;
        this.balance = balance;
    }

    public ShowBalanceDTO() {}

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }
}
