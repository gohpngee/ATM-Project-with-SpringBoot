package com.gohpngee.atm_transaction_app.dto;

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
    private String senderAccountType;
    private String receiverAccountType;
    private BigDecimal amount;
}
