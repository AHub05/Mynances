package domain;

import java.math.BigDecimal;

public class SavingsAccount extends Account {
    
    protected final BigDecimal interestRate;

    public SavingsAccount(User owner, BigDecimal initialBalance, BigDecimal interestRate) {
        super(owner, initialBalance);

        if(interestRate == null) {
            interestRate = BigDecimal.ZERO;
        }
        if(interestRate.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Cannot have interest rate less than zero.");
        }

        this.interestRate = interestRate;
    }

}
