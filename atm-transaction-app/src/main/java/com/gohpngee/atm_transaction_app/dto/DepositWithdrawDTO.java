package com.gohpngee.atm_transaction_app.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@RequiredArgsConstructor
public class AccountResponseDTO {
    private final String accountNumber;
    private final BigDecimal balance;
}
