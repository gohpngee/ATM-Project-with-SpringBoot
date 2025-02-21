package com.gohpngee.atm_transaction_app.dto;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor (force = true)
@AllArgsConstructor
public class DepositWithdrawDTO {
    private final String accountNumber;
    private final BigDecimal balance;
}
