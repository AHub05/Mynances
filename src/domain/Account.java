package domain;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

public abstract class Account {
    
    // Data Fields
    protected final User owner;
    protected final String accountId;
    protected BigDecimal balance;
    protected final List<Transaction> transactions;
    protected final LocalDateTime createdAt;

    protected Account(User owner, BigDecimal initialBalance) {
       
        if (initialBalance == null) {
            initialBalance = BigDecimal.ZERO;
        }
        if(owner == null) {
            throw new IllegalArgumentException("Account must be associated with a user.");
        }
        if(initialBalance.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Initial balance cannot be less than 0!");
        }
   
            this.owner = owner;
            this.accountId = UUID.randomUUID().toString();
            this.balance = initialBalance;
            transactions = new ArrayList<>();
            this.createdAt = LocalDateTime.now();
    }

    // getters
    public String getAccountId() {
        return accountId;
    }
    public BigDecimal getBalance() {
        return balance;
    }
    public List<Transaction> getTransactions() {
        return Collections.unmodifiableList(transactions); // Ask about "defensive copy"
    }

    public void deposit(BigDecimal amount) {
        // Checks for transaction validity prior to mutations
        if(amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Deposit must be an amount greater than zero.");
        }

        this.balance = this.balance.add(amount);
        transactions.add(new Transaction(Transaction.DEPOSIT, amount, balance));
        
    }

    public void withdraw(BigDecimal amount) {
        // Checks for transaction validity prior to mutations
        if(amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Withdrawal amount must be greater than zero.");
        }
        if(balance.compareTo(amount) < 0) {
            throw new IllegalArgumentException("Insufficient funds.");
        }

        this.balance = this.balance.subtract(amount);
        transactions.add(new Transaction(Transaction.WITHDRAWAL, amount, balance));
    }

    public void transferTo(Account targetAccount, BigDecimal amount) {
        // Amount check
        if(amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Transfer amount must be greater than zero.");
        }
        // Target check
        if(targetAccount == null || targetAccount == this) {
            throw new IllegalArgumentException("Account unable to be found or transferred to.");
        }
        // Funds check
        if(balance.compareTo(amount) < 0) {
            throw new IllegalArgumentException("Insufficient funds.");
        }

        BigDecimal newThisBalance = this.balance.subtract(amount);
        BigDecimal newTargetBalance = targetAccount.balance.add(amount);
        this.balance = newThisBalance;
        targetAccount.balance = newTargetBalance;
        
        this.transactions.add(new Transaction(Transaction.TRANSFER_OUT, amount, this.balance));
        targetAccount.transactions.add(new Transaction(Transaction.TRANSFER_IN, amount, targetAccount.balance));
    }

    public String displayInfo() {
        return String.format("Account: %s | Balance: $%s | Date Created: [%s]", accountId, balance, createdAt);
    }

    public String printStatement(LocalDateTime startDate, LocalDateTime endDate) {

        if((startDate == null && endDate == null)) { // year-to-date
            endDate = LocalDateTime.now();
            startDate = LocalDateTime.of(endDate.getYear(), 1, 1, 0, 0, 0);
        }
        else if(startDate == null || endDate == null) { // open-ended
            if(startDate == null)
                startDate = createdAt;
            else if(endDate == null)
                endDate = LocalDateTime.now();
        }
        else if (endDate.isBefore(startDate)) { // year-to-date
            endDate = LocalDateTime.now();
            startDate = LocalDateTime.of(endDate.getYear(), 1, 1, 0, 0, 0);
        }
        
        StringBuilder statement = new StringBuilder();

        statement.append("Account: " + accountId + "\n");
        statement.append("From: " + startDate + " | To: " + endDate + "\n");
        statement.append("Statement Creation: " + LocalDateTime.now() + "\n");

        statement.append("Date | Type | Amount | Balance After\n");

        boolean printed = false;
        for (Transaction transaction : transactions) {
            if (!transaction.getTimeStamp().isBefore(startDate) && !transaction.getTimeStamp().isAfter(endDate)) {
                statement.append(transaction.getSummary() + "\n");
                printed = true;
            }
        }

        if (!printed)
            statement.append("There were no transactions found in this time period.");       

        statement.append("\nEnding Balance: $" + balance.setScale(2,  RoundingMode.HALF_UP));
        
        return statement.toString();
    }

}
