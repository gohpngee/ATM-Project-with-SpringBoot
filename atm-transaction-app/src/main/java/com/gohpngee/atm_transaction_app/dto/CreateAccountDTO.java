package com.gohpngee.atm_transaction_app.dto;

import com.gohpngee.atm_transaction_app.model.Account;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@NoArgsConstructor (force = true)
@AllArgsConstructor
public class CreateAccountDTO {
    private String accountNumber;
    private String accountHolderName;
    private Account.AccountType accountType;
    private BigDecimal balance;
}


