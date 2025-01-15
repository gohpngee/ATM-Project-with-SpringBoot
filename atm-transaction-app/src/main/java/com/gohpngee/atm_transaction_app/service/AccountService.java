import org.springframework.beans.factory.annotation.Autowired;
import java.math.BigDecimal;
import java.util.Optional;
import org.springframework.transaction.annotation.Transactional;

import com.gohpngee.atm_transaction_app.model.Account;
//import com.gohpngee.atm_transaction_app.repository.AccountRepository;

@Ser
public class AccountService {
    private final AccountRepository accountRepository;


    public void deposit (BigDecimal amount) {
        if (amount.compareTo(BigDecimal.ZERO) > 0) {
            balance = balace.add(amount);
        } else {
            throw new IllegalArgumentException("Deposit amount must be more than zero.");
        }
    }

    public void withdraw (BigDecimal amount) {
        if ((amount.compareTo(BigDecimal.ZERO) > 0) && (balance.compareTo(amount) >= 0)){
            balance = balance.subtract(amount);
        } else {
            throw new IllegalArgumentAcception("Insufficient Funds.");
        }
    }
}