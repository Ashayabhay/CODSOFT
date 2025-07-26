import java.util.Scanner;

// Class to represent the user's bank account
class BankAccount {
    private final String accountNumber;
    private final String accountHolderName;
    private double balance;
    private final String pin;

    // Constructor
    public BankAccount(String accountNumber, String accountHolderName, double initialBalance, String pin) {
        this.accountNumber = accountNumber;
        this.accountHolderName = accountHolderName;
        this.balance = initialBalance;
        this.pin = pin;
    }

    // Getter methods
    public String getAccountNumber() {
        return accountNumber;
    }

    public String getAccountHolderName() {
        return accountHolderName;
    }

    public double getBalance() {
        return balance;
    }

    // Method to validate PIN
    public boolean validatePin(String enteredPin) {
        return this.pin.equals(enteredPin);
    }

    // Method to withdraw money
    public boolean withdraw(double amount) {
        if (amount <= 0) {
            return false; // Invalid amount
        }
        if (amount > balance) {
            return false; // Insufficient balance
        }
        balance -= amount;
        return true;
    }

    // Method to deposit money
    public boolean deposit(double amount) {
        if (amount <= 0) {
            return false; // Invalid amount
        }
        balance += amount;
        return true;
    }

    // Method to check balance
    public double checkBalance() {
        return balance;
    }
}

// Class to represent the ATM machine
class ATM {
    private BankAccount currentAccount;
    private final Scanner scanner;
    private boolean isAuthenticated;

    // Constructor
    public ATM() {
        this.scanner = new Scanner(System.in);
        this.isAuthenticated = false;
    }

    // Method to start the ATM system
    public void start() {
        System.out.println("========================================");
        System.out.println("    WELCOME TO THE ATM SYSTEM");
        System.out.println("========================================");
        
        // For demo purposes, create a sample account
        currentAccount = new BankAccount("123456789", "ABHAY PAATHAK", 500000.00, "1234");
        
        if (authenticateUser()) {
            showMainMenu();
        } else {
            System.out.println("Authentication failed. Exiting...");
        }
    }

    // Method to authenticate user
    private boolean authenticateUser() {
        System.out.println("\nPlease enter your credentials:");
        System.out.print("Account Number: ");
        String accountNumber = scanner.nextLine();
        
        System.out.print("PIN: ");
        String pin = scanner.nextLine();
        
        if (currentAccount.getAccountNumber().equals(accountNumber) && 
            currentAccount.validatePin(pin)) {
            isAuthenticated = true;
            System.out.println("\nAuthentication successful!");
            System.out.println("Welcome, " + currentAccount.getAccountHolderName());
            return true;
        } else {
            System.out.println("\nInvalid account number or PIN!");
            return false;
        }
    }

    // Method to display main menu
    private void showMainMenu() {
        boolean continueTransaction = true;
        
        while (continueTransaction && isAuthenticated) {
            System.out.println("\n========================================");
            System.out.println("           MAIN MENU");
            System.out.println("========================================");
            System.out.println("1. Check Balance");
            System.out.println("2. Deposit Money");
            System.out.println("3. Withdraw Money");
            System.out.println("4. Exit");
            System.out.println("========================================");
            System.out.print("Please select an option (1-4): ");
            
            String choice = scanner.nextLine();
            
            switch (choice) {
                case "1":
                    checkBalance();
                    break;
                case "2":
                    deposit();
                    break;
                case "3":
                    withdraw();
                    break;
                case "4":
                    continueTransaction = false;
                    System.out.println("\nThank you for using CODOSOFT ATM service!");
                    System.out.println("Have a great day!");
                    break;
                default:
                    System.out.println("\nInvalid option! Please select a valid option (1-4).");
            }
            
            if (continueTransaction && !choice.equals("4")) {
                System.out.print("\nWould you like to perform another transaction? (y/n): ");
                String response = scanner.nextLine().toLowerCase();
                if (!response.equals("y") && !response.equals("yes")) {
                    continueTransaction = false;
                    System.out.println("\nThank you for using our ATM service!");
                    System.out.println("Have a great day!");
                }
            }
        }
    }

    // Method to check balance
    private void checkBalance() {
        System.out.println("\n========================================");
        System.out.println("           BALANCE INQUIRY");
        System.out.println("========================================");
        System.out.println("Account Holder: " + currentAccount.getAccountHolderName());
        System.out.println("Account Number: " + currentAccount.getAccountNumber());
        System.out.printf("Current Balance: $%.2f\n", currentAccount.checkBalance());
        System.out.println("========================================");
    }

    // Method to deposit money
    private void deposit() {
        System.out.println("\n========================================");
        System.out.println("             DEPOSIT");
        System.out.println("========================================");
        System.out.print("Enter the amount to deposit: $");
        
        try {
            double amount = Double.parseDouble(scanner.nextLine());
            
            if (currentAccount.deposit(amount)) {
                System.out.println("\n✓ Deposit successful!");
                System.out.printf("Amount deposited: $%.2f\n", amount);
                System.out.printf("New balance: $%.2f\n", currentAccount.getBalance());
            } else {
                System.out.println("\n✗ Deposit failed!");
                System.out.println("Please enter a valid positive amount.");
            }
        } catch (NumberFormatException e) {
            System.out.println("\n✗ Invalid input!");
            System.out.println("Please enter a valid numeric amount.");
        }
        System.out.println("========================================");
    }

    // Method to withdraw money
    private void withdraw() {
        System.out.println("\n========================================");
        System.out.println("            WITHDRAWAL");
        System.out.println("========================================");
        System.out.printf("Current Balance: $%.2f\n", currentAccount.getBalance());
        System.out.print("Enter the amount to withdraw: $");
        
        try {
            double amount = Double.parseDouble(scanner.nextLine());
            
            if (amount <= 0) {
                System.out.println("\n✗ Withdrawal failed!");
                System.out.println("Please enter a valid positive amount.");
            } else if (amount > currentAccount.getBalance()) {
                System.out.println("\n✗ Withdrawal failed!");
                System.out.println("Insufficient balance!");
                System.out.printf("Available balance: $%.2f\n", currentAccount.getBalance());
            } else if (currentAccount.withdraw(amount)) {
                System.out.println("\n✓ Withdrawal successful!");
                System.out.printf("Amount withdrawn: $%.2f\n", amount);
                System.out.printf("Remaining balance: $%.2f\n", currentAccount.getBalance());
            } else {
                System.out.println("\n✗ Withdrawal failed!");
                System.out.println("An error occurred during the transaction.");
            }
        } catch (NumberFormatException e) {
            System.out.println("\n✗ Invalid input!");
            System.out.println("Please enter a valid numeric amount.");
        }
        System.out.println("========================================");
    }
}

// Main class to run the ATM system
public class ATMInterface {
    public static void main(String[] args) {
        ATM atm = new ATM();
        atm.start();
    }
}