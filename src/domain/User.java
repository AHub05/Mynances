package domain;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

public class User {

    // Data Fields
    private final UUID userID;
    private final String fullName;
    private String email; // not final, someone may get a new email
    private final LocalDateTime createdAt;
    private final List<Account> accounts;

    public User(String fullName, String email) {
       
        if(fullName == null || email == null) {
            throw new IllegalArgumentException("User must have a name and email associated.");
        }
       
        userID = UUID.randomUUID();
        this.fullName = fullName;
        this.email = email;
        createdAt = LocalDateTime.now();
        accounts = new ArrayList<>();
    }

    public CheckingAccount createCheckingAccount(BigDecimal initialBalance) {
        CheckingAccount checking = new CheckingAccount(this, initialBalance);
        accounts.add(checking);
        return checking;
    }

    public SavingsAccount createSavingsAccount(BigDecimal initialBalance, BigDecimal interestRate) {
        SavingsAccount savings = new SavingsAccount(this, initialBalance, interestRate);
        accounts.add(savings);
        return savings;
    }

    public List<Account> getAccounts() {
        return Collections.unmodifiableList(accounts);
    }

    public String getUserSummary() {
        return String.format("%s | Email: %s | No. of Accounts: %d | Created at: [%s]", fullName, email, accounts.size(), createdAt.toString());
    }

    public UUID getUserId() {
        return userID;
    }

}