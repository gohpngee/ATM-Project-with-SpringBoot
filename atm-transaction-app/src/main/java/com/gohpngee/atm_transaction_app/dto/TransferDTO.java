package com.gohpngee.atm_transaction_app.dto;

import com.gohpngee.atm_transaction_app.model.Account;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class TransferDTO {
    private String senderAccountNumber;
    private String receiverAccountNumber;
    private Account.AccountType senderAccountType;
    private Account.AccountType receiverAccountType;
    private BigDecimal amount;
}
