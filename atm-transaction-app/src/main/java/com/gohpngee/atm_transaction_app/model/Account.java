package com.gohpngee.atm_transaction_app.model;
import java.math.BigDecimal;

public class Account {
    private static Long counter = 0;
    private Long id;
    private final String accountNumber;
    private String accountHolderName;
    private String accountType;
    private BigDecimal balance;

    public Account();

    public Account(String accountNumber, String accountHolderName, String accountType, BigDecimal balance) {
        this.id = ++counter;
        this.accountNumber = accountNumber;
        this.accountHolderName = accountHolderName;
        this.accountType = accountType;
        this.balance = balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void checkBalance() {
        System.out.println("Balance: " + balance);
    }
}