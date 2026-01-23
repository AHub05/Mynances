# Mynances (Project Status: Domain Layer (v0.1))

A Java-based banking system designed to model real-world banking concepts such as users, accounts, and transactions.  
This project focuses on **clean domain modeling**, **object-oriented design**, and **financial correctness**, with plans to extend into persistence and automation in later phases.

---
## About Me

My name is Alexander Hubbard. Currently I am a Junior, Computer Scinece Major at UNC Greensboro. I am fascinated about the current and potential capabilities of technology and appreciate the ability for it to enhance productivity. Coding and designing programs is incredibly interesting as to me there is almost always a solution to a problem in which a passion for problem solving is needed. Outside of coding and problem solving I enjoy photography, exploring new things, games, and food!

---

## 🚀 Project Goals

- Practice Java with a realistic, non-trivial domain
- Model banking rules and constraints accurately
- Emphasize correctness, immutability, and encapsulation
- Build a foundation for future features:
  - database persistence
  - automated transaction ingestion
  - service and UI layers

---

## 🧠 Design Philosophy

This project prioritizes **domain-driven design** principles:

- Business rules are enforced through object construction
- Invalid states are prevented by design, not patched later
- Ownership and responsibility are explicit
- Mutations are intentional and traceable

The system is currently **single-threaded** and focuses on correctness over performance.

---

## 🧩 Core Domain Model

### 👤 User
Represents a single real-world person.

**Responsibilities**
- Owns one or more accounts
- Creates accounts
- Provides read-only access to owned accounts

**Key Rules**
- A `User` is uniquely identified by a UUID
- Accounts cannot exist without a user
- External code cannot directly modify the user’s account list

---

### 🏦 Account (Abstract)
Represents a bank account owned by a user.

**Responsibilities**
- Maintain balance
- Record transaction history
- Enforce financial rules
- Generate statements

**Key Rules**
- Every account has exactly one owner (`User`)
- Balances cannot go negative (overdrafts disallowed for now)
- All balance changes result in a `Transaction`

**Supported Operations**
- `deposit(amount)`
- `withdraw(amount)`
- `transferTo(targetAccount, amount)`
- `printStatement(startDate, endDate)`

---

### 💳 Account Types

#### CheckingAccount
- No additional constraints
- General-purpose transactional account

#### SavingsAccount
- Has an immutable interest rate
- Interest logic to be implemented in a later phase

---

### 🧾 Transaction
Represents an immutable snapshot of a completed financial event.

**Stored Data**
- Unique transaction ID
- Transaction type (deposit, withdrawal, transfer)
- Amount
- Timestamp
- Balance after transaction

**Design Notes**
- Transactions do not validate business rules
- Validation occurs before transaction creation
- Transactions are append-only and immutable

---

## 🛠️ Technologies Used

- Java
- `BigDecimal` for monetary precision
- `UUID` for unique identifiers
- `LocalDateTime` for timestamps

No external frameworks are used at this stage.

---

## ▶️ Running the Project

A simple `main` method is provided to demonstrate:
- user creation
- account creation
- deposits, withdrawals, transfers
- statement generation

Compile and run using:

```bash
javac *.java
java Main
