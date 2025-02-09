package com.gohpngee.atm_transaction_app.service;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@RequiredArgsConstructor
public class AccountDTO {
    private final int accountNumber;
    private final BigDecimal balance;

}
