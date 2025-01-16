package com.gohpngee.atm_transaction_app.repository;
import com.gohpngee.atm_transaction_app.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Long>{
    Optional<Account> findByAccountNumber(String accountNumber);
}
