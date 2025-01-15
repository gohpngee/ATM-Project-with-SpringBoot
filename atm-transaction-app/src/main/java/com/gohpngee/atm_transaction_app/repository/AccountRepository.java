package com.gohpngee.atm_transaction_app.repository;
import com.gohpngee.atm_transaction_app.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import java.math.BigDecimal;

public interface AccountRepository extends JpaRepository<Account, Long>{
}
