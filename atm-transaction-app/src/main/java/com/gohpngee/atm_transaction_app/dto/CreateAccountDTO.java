package com.gohpngee.atm_transaction_app.dto;

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
    private String accountType;
    private BigDecimal balance;
}


