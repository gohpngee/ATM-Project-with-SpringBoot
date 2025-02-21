package com.gohpngee.atm_transaction_app.model;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String accountNumber;
    private String accountHolderName;
    private String accountType;
    private BigDecimal balance;

    public Account(String accountNumber, String accountHolderName, String accountType, BigDecimal balance) {
    }
}