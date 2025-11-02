import java.util.*;
import model.Account;
import service.BankService;
import exception.*;

public class Main {
    private static Scanner sc = new Scanner(System.in);
    private static BankService bankService = new BankService();

    public static void main(String[] args) {
        while (true) {
            try {
                System.out.println("\n===== BANKING SYSTEM =====");
                System.out.println("1. Create Account");
                System.out.println("2. Manage Existing Account");
                System.out.println("3. Exit");
                System.out.print("Enter choice: ");
                int choice = sc.nextInt();
                sc.nextLine();

                switch (choice) {
                    case 1: createAccount(); break;
                    case 2: manageAccount(); break;
                    case 3: System.exit(0);
                    default: System.out.println("Invalid choice!");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input! Please enter a number.");
                sc.nextLine();
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

    private static void createAccount() throws InvalidNameException {
        System.out.print("Enter your name: ");
        String name = sc.nextLine();
        Account acc = bankService.createAccount(name);
        System.out.println("Account created successfully!");
        System.out.println(acc);
    }

    private static void manageAccount() throws Exception {
        System.out.print("Enter account number: ");
        String accNum = sc.nextLine();
        Account acc = bankService.findAccount(accNum);

        while (true) {
            System.out.println("\n===== Account Operations =====");
            System.out.println("1. Deposit");
            System.out.println("2. Withdraw");
            System.out.println("3. Transfer");
            System.out.println("4. Show Balance");
            System.out.println("5. Back to Main Menu");
            System.out.print("Choice: ");
            int ch = sc.nextInt();
            sc.nextLine();

            try {
                switch (ch) {
                    case 1:
                        System.out.print("Enter deposit amount: ");
                        double dep = sc.nextDouble();
                        bankService.deposit(accNum, dep);
                        System.out.println("Deposit successful!");
                        break;

                    case 2:
                        System.out.print("Enter withdrawal amount: ");
                        double wd = sc.nextDouble();
                        bankService.withdraw(accNum, wd);
                        System.out.println("Withdrawal successful!");
                        break;

                    case 3:
                        System.out.print("Enter destination account number: ");
                        String dest = sc.nextLine();
                        System.out.print("Enter amount to transfer: ");
                        double amt = sc.nextDouble();
                        bankService.transfer(accNum, dest, amt);
                        System.out.println("Transfer successful!");
                        break;

                    case 4:
                        System.out.println(bankService.findAccount(accNum));
                        break;

                    case 5:
                        return;

                    default:
                        System.out.println("Invalid option!");
                }
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }
}
