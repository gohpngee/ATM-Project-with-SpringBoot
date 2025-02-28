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
    public enum AccountType {
        SAVINGS,
        CHECKING
    }
    @Column(unique = true, nullable = false)
    private String accountNumber;
    @Column(nullable = false)
    private String accountHolderName;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AccountType accountType;
    @Column(nullable = false)
    private BigDecimal balance;
}