package domain;

import java.math.BigDecimal;

public class CheckingAccount extends Account {
    
    public CheckingAccount(User owner, BigDecimal initialBalance) {
        super(owner, initialBalance);
    }
}
