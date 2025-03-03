package com.gohpngee.atm_transaction_app.dto;

import com.gohpngee.atm_transaction_app.model.Account;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class DepositWithdrawDTO {
    private String accountNumber;
    private Account.AccountType accountType;
    private  BigDecimal amount;
}
