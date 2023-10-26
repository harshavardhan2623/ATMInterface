import java.util.HashMap;
import java.util.Scanner;

class User {
    private String userId;
    private String pin;
    private double balance;

    public User(String userId, String pin, double balance) {
        this.userId = userId;
        this.pin = pin;
        this.balance = balance;
    }

    public String getUserId() {
        return userId;
    }

    public String getPin() {
        return pin;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }
}

public class ATMInterface {
    private static HashMap<String, User> users = new HashMap<>();
    private static User currentUser;

    static {
        // Sample user data (user ID, PIN, initial balance)
        users.put("123456", new User("123456", "1234", 1000.0));
        users.put("789012", new User("789012", "5678", 1500.0));
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome to the ATM Interface");
        
        while (true) {
            System.out.print("Enter User ID: ");
            String userId = scanner.nextLine();
            System.out.print("Enter PIN: ");
            String pin = scanner.nextLine();

            if (authenticateUser(userId, pin)) {
                showOptions(scanner);
            } else {
                System.out.println("Invalid User ID or PIN. Please try again.");
            }
        }
    }

    private static boolean authenticateUser(String userId, String pin) {
        User user = users.get(userId);
        if (user != null && user.getPin().equals(pin)) {
            currentUser = user;
            return true;
        }
        return false;
    }

    private static void showOptions(Scanner scanner) {
        while (true) {
            System.out.println("\nATM Functionalities:");
            System.out.println("1. View Balance");
            System.out.println("2. Withdraw");
            System.out.println("3. Deposit");
            System.out.println("4. Transfer");
            System.out.println("5. Transactions History");
            System.out.println("6. Quit");

            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    System.out.println("Your balance: $" + currentUser.getBalance());
                    break;
                case 2:
                    System.out.print("Enter withdrawal amount: $");
                    double withdrawAmount = scanner.nextDouble();
                    scanner.nextLine(); // Consume newline
                    if (withdrawAmount > 0 && withdrawAmount <= currentUser.getBalance()) {
                        currentUser.setBalance(currentUser.getBalance() - withdrawAmount);
                        System.out.println("Withdrawal successful. Your new balance: $" + currentUser.getBalance());
                    } else {
                        System.out.println("Invalid amount or insufficient balance.");
                    }
                    break;
                case 3:
                    System.out.print("Enter deposit amount: $");
                    double depositAmount = scanner.nextDouble();
                    scanner.nextLine(); // Consume newline
                    if (depositAmount > 0) {
                        currentUser.setBalance(currentUser.getBalance() + depositAmount);
                        System.out.println("Deposit successful. Your new balance: $" + currentUser.getBalance());
                    } else {
                        System.out.println("Invalid amount.");
                    }
                    break;
                case 4:
                    System.out.print("Enter recipient's User ID: ");
                    String recipientId = scanner.nextLine();
                    User recipient = users.get(recipientId);
                    if (recipient != null) {
                        System.out.print("Enter transfer amount: $");
                        double transferAmount = scanner.nextDouble();
                        scanner.nextLine(); // Consume newline
                        if (transferAmount > 0 && transferAmount <= currentUser.getBalance()) {
                            currentUser.setBalance(currentUser.getBalance() - transferAmount);
                            recipient.setBalance(recipient.getBalance() + transferAmount);
                            System.out.println("Transfer successful. Your new balance: $" + currentUser.getBalance());
                        } else {
                            System.out.println("Invalid amount or insufficient balance.");
                        }
                    } else {
                        System.out.println("Recipient User ID not found.");
                    }
                    break;
                case 5:
                    System.out.println("Transaction History: (Not implemented in this demo)");
                    break;
                case 6:
                    System.out.println("Thank you for using the ATM Interface. Goodbye!");
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
}