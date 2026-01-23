import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public class Transaction {
    
    // Transaction Type Constants
    public static final String DEPOSIT = "DEPOSIT";
    public static final String WITHDRAWAL = "WITHDRAWAL";
    public static final String TRANSFER_IN = "TRANSFER_IN";
    public static final String TRANSFER_OUT = "TRANSFER_OUT";

    // Transaction Data Fields
    private String transactionId;
    private String transactionType;
    private BigDecimal transactionAmount;
    private BigDecimal amountAfterTransaction;
    private LocalDateTime timeStamp; 

    public Transaction(String transactionType, BigDecimal transactionAmount, BigDecimal amountAfterTransaction) {

        if (transactionType == null || transactionType.isBlank())
            throw new IllegalArgumentException("Type of transaction may not be null!");
        else if (transactionAmount == null || transactionAmount.compareTo(BigDecimal.ZERO) < 0)
            throw new IllegalArgumentException("transactionAmount may not be less than zero!");
        else if (amountAfterTransaction == null || amountAfterTransaction.compareTo(BigDecimal.ZERO) < 0)
            throw new IllegalArgumentException("Balance cannot be in the negatives!");
        else {
            this.transactionId = UUID.randomUUID().toString();
            this.transactionType = transactionType;
            this.transactionAmount = transactionAmount;
            this.amountAfterTransaction = amountAfterTransaction;
            this.timeStamp = LocalDateTime.now();
        }

    }

    // Getters
    public String getTransactionId() {
        return this.transactionId;
    }
    public String getTransactionType() {
        return this.transactionType;
    }
    public BigDecimal getTransactionAmount() {
        return this.transactionAmount;
    }
    public BigDecimal getBalanceAfterTransaction() {
        return this.amountAfterTransaction;
    }
    public LocalDateTime getTimeStamp() {
        return this.timeStamp;
    }

    public String getSummary() {
        return String.format("[%s] %s | Amount: $%s | Ending Balance: %s", timeStamp, transactionType, transactionAmount, amountAfterTransaction);
    }
}
