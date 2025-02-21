package com.gohpngee.atm_transaction_app.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor (force = true)
public class CreateAccountDTO {
    private final String accountNumber;
    private final String accountHolderName;
    private final String accountType;
    private BigDecimal balance;
}
