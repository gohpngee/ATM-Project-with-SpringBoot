package com.gohpngee.atm_transaction_app.model;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
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