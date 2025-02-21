package com.gohpngee.atm_transaction_app.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Getter
@AllArgsConstructor
@NoArgsConstructor (force = true)
public class ShowBalanceDTO {
    private final String accountNumber;
    private final BigDecimal balance;
}
