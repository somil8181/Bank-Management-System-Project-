import java.io.*;
import java.util.*;

// Custom Exception for insufficient balance
class InsufficientBalanceException extends Exception {

    public InsufficientBalanceException(String message) {
        super(message);
    }
}

// Custom Exception for account not found
class AccountNotFoundException extends Exception {

    public AccountNotFoundException(String message) {
        super(message);
    }
}

// Account Class
class Account implements Serializable {

    private int accountNumber;
    private String holderName;
    private String mobileNumber;
    private String dob;          // DOB Added
    private String accountType;
    private String ifscCode;
    private double balance;

    // Constructor
    public Account(int accountNumber,
                   String holderName,
                   String mobileNumber,
                   String dob,
                   String accountType,
                   String ifscCode,
                   double balance) {

        this.accountNumber = accountNumber;
        this.holderName = holderName;
        this.mobileNumber = mobileNumber;
        this.dob = dob;
        this.accountType = accountType;
        this.ifscCode = ifscCode;
        this.balance = balance;
    }

    // Getter Methods
    public int getAccountNumber() {
        return accountNumber;
    }

    public String getHolderName() {
        return holderName;
    }
    public String getMobileNumber() {
    return mobileNumber;
}

    public String getDob() {
        return dob;
    }

    public String getAccountType() {
        return accountType;
    }

    public String getIfscCode() {
        return ifscCode;
    }

    public double getBalance() {
        return balance;
    }

    // Deposit Method
    public void deposit(double amount) {
        balance += amount;
    }

    // Withdraw Method
    public void withdraw(double amount)
            throws InsufficientBalanceException {

        if (amount > balance) {

            throw new InsufficientBalanceException(
                    "Insufficient Balance!");
        }

        balance -= amount;
    }

    // Display Account Details
    @Override
    public String toString() {

        return "\n--------------------------------"
                + "\nAccount Number : " + accountNumber
                + "\nHolder Name    : " + holderName
                + "\nMobile Number  : " + mobileNumber
                + "\nDOB            : " + dob
                + "\nAccount Type   : " + accountType
                + "\nIFSC Code      : " + ifscCode
                + "\nBalance        : $" + balance
                + "\n--------------------------------";
    }
}

// Bank Class
class Bank {

    private ArrayList<Account> accounts;

    private final String FILE_NAME = "accounts.dat";

    // Constructor
    public Bank() {

        accounts = new ArrayList<>();

        loadAccounts();
    }

    // Create Account
    public void createAccount(Account account) {

        accounts.add(account);

        saveAccounts();
    }

    // Find Account
    public Account findAccount(int accountNumber)
            throws AccountNotFoundException {

        for (Account acc : accounts) {

            if (acc.getAccountNumber() == accountNumber) {

                return acc;
            }
        }

        throw new AccountNotFoundException(
                "Account Not Found!");
    }

    // Deposit Money
    public void depositMoney(int accountNumber,
                             double amount)
            throws AccountNotFoundException {

        Account acc = findAccount(accountNumber);

        acc.deposit(amount);

        saveAccounts();

        System.out.println("\n-------------------------------");
        System.out.println("      DEPOSIT SUCCESSFUL");
        System.out.println("---------------------------------");
        System.out.println("Deposited Amount : $" + amount);
        System.out.println("Current Balance  : $"
                + acc.getBalance());
        System.out.println("----------------------------------");
    }

    // Withdraw Money
    public void withdrawMoney(int accountNumber,
                              double amount,
                              String withdrawType)
            throws AccountNotFoundException,
                   InsufficientBalanceException {

        Account acc = findAccount(accountNumber);

        acc.withdraw(amount);

        saveAccounts();

        System.out.println("\n-------------------------------");
        System.out.println("      WITHDRAW SUCCESSFUL");
        System.out.println("----------------------------------");
        System.out.println("Withdraw Type    : "
                + withdrawType);
        System.out.println("Withdraw Amount  : $"
                + amount);
        System.out.println("Remaining Balance: $"
                + acc.getBalance());
        System.out.println("-----------------------------------");
    }

    // Display All Accounts
    public void displayAccounts() {

        if (accounts.isEmpty()) {

            System.out.println("\nNo Accounts Found!");

            return;
        }

        for (Account acc : accounts) {

            System.out.println(acc);
        }
    }

    // Save Accounts
    private void saveAccounts() {

        try {

            ObjectOutputStream oos =
                    new ObjectOutputStream(
                            new FileOutputStream(FILE_NAME));

            oos.writeObject(accounts);

            oos.close();

        } catch (IOException e) {

            System.out.println(
                    "Error Saving File: "
                            + e.getMessage());
        }
    }

    // Load Accounts
    @SuppressWarnings("unchecked")
    private void loadAccounts() {

        try {

            File file = new File(FILE_NAME);

            if (!file.exists()) {

                return;
            }

            ObjectInputStream ois =
                    new ObjectInputStream(
                            new FileInputStream(FILE_NAME));

            accounts =
                    (ArrayList<Account>) ois.readObject();

            ois.close();

        } catch (IOException |
                 ClassNotFoundException e) {

            System.out.println(
                    "Error Loading File: "
                            + e.getMessage());
        }
    }
}

// Main Class
public class Main {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        Bank bank = new Bank();

        while (true) {

            System.out.println("\n--------------------------------------");
            System.out.println("========   SOMIL BANK SYSTEM   =======");
            System.out.println("--------------------------------------");
            System.out.println("==  Welcome To Secure Banking Portal==");
            System.out.println("--------------------------------------");

            System.out.println("1. Create Account");
            System.out.println("2. Deposit Money");
            System.out.println("3. Withdraw Money");
            System.out.println("4. View All Accounts");
            System.out.println("5. Exit");

            System.out.print("Enter Your Choice: ");

            int choice;

            try {

                choice = sc.nextInt();

            } catch (Exception e) {

                System.out.println("Invalid Input!");

                sc.nextLine();

                continue;
            }

            try {

                switch (choice) {

                    // Create Account
                    case 1:

                        System.out.print(
                                "Enter Account Number: ");

                        int accNo = sc.nextInt();

                        sc.nextLine();

                        System.out.print(
                                "Enter Holder Name: ");

                        String name = sc.nextLine();

                        System.out.print("Enter Mobile Number: ");
                        String mobile = sc.nextLine();

                        // DOB Input
                        System.out.print(
                                "Enter DOB (DD/MM/YYYY): ");

                        String dob = sc.nextLine();

                        System.out.print(
                                "Enter Account Type "
                                + "(Savings/Current): ");

                        String type = sc.nextLine();

                        System.out.print(
                                "Enter IFSC Code: ");

                        String ifsc = sc.nextLine();

                        System.out.print(
                                "Enter Initial Balance: ");

                        double balance =
                                sc.nextDouble();

                        Account account =
                                new Account(
                                        accNo,
                                        name,
                                          mobile,
                                        dob,
                                        type,
                                        ifsc,
                                        balance);

                        bank.createAccount(account);

                        System.out.println(
                                "\nAccount Created Successfully!");

                        break;

                    // Deposit
                    case 2:

                        System.out.print(
                                "Enter Account Number: ");

                        int depAcc = sc.nextInt();

                        System.out.print(
                                "Enter Deposit Amount: ");

                        double depAmount =
                                sc.nextDouble();

                        bank.depositMoney(
                                depAcc,
                                depAmount);

                        break;

                    // Withdraw
                    case 3:

                        System.out.print(
                                "Enter Account Number: ");

                        int withAcc = sc.nextInt();

                        System.out.print(
                                "Enter Withdrawal Amount: ");

                        double withAmount =
                                sc.nextDouble();

                        sc.nextLine();

                        System.out.print(
                                "Enter Withdraw Type "
                                + "(ATM/UPI/Cheque/Online): ");

                        String withdrawType =
                                sc.nextLine();

                        bank.withdrawMoney(
                                withAcc,
                                withAmount,
                                withdrawType);

                        break;

                    // View Accounts
                    case 4:

                        bank.displayAccounts();

                        break;

                    // Exit
                    case 5:

                        System.out.println(
                                "\nThank You For Using Our Bank System!");

                        System.exit(0);

                    default:

                        System.out.println(
                                "\nInvalid Choice!");
                }

            } catch (AccountNotFoundException |
                     InsufficientBalanceException e) {

                System.out.println(
                        "\nError saving file: ");
                                
            }
        }
    }
}