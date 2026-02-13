import java.math.BigDecimal;
import domain.*;

public class Main {
    public static void main(String[] args) {

        // ---- Create User ----
        User user = new User("Alex Hubbard", "alex@example.com");

        // ---- Create Accounts ----
        CheckingAccount checking =
                user.createCheckingAccount(new BigDecimal("1000.00"));

        SavingsAccount savings =
                user.createSavingsAccount(
                        new BigDecimal("5000.00"),
                        new BigDecimal("0.02")
                );

        // ---- Basic Deposits ----
        checking.deposit(new BigDecimal("250.00"));
        savings.deposit(new BigDecimal("500.00"));

        // ---- Withdrawals ----
        checking.withdraw(new BigDecimal("100.00"));

        // ---- Transfer ----
        checking.transferTo(savings, new BigDecimal("300.00"));

        // ---- Print Statements ----
        System.out.println("===== CHECKING STATEMENT =====");
        System.out.println(
                checking.printStatement(null, null)
        );

        System.out.println("\n===== SAVINGS STATEMENT =====");
        System.out.println(
                savings.printStatement(null, null)
        );

        // ---- Account Ownership Check ----
        System.out.println("\n===== USER SUMMARY =====");
        System.out.println(user.getUserSummary());
    }
}
