package com.gohpngee.atm_transaction_app.dto;

import com.gohpngee.atm_transaction_app.model.Account;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ShowBalanceDTO {
    private String accountNumber;
    private Account.AccountType accountType;
    private BigDecimal balance;
}
