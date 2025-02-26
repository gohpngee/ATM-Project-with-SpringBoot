package com.gohpngee.atm_transaction_app.model;
import jakarta.persistence.*;
import lombok.*;


import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Account {
    @Column(unique = true, nullable = false)
    private String accountNumber;
    @Column(nullable = false)
    private String accountHolderName;
    @Column(nullable = false)
    private String accountType;
    @Column(nullable = false)
    private BigDecimal balance;
}