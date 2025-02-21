package com.gohpngee.atm_transaction_app.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;

@Getter
@RequiredArgsConstructor
public class AccountRequestDTO {
    private final String accountNumber;
    private final String accountHolderName;
    private final String accountType;
    private final BigDecimal balance;
}
