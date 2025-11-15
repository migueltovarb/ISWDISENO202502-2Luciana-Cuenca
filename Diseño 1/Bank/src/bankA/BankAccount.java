package bankA;
import java.util.Scanner;

public class BankAccount {
    public static void main(String[] args) {
        double balance = 1000.0;
        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("---Ao3 ATM ---");
            System.out.println("1. Check balance");
            System.out.println("2. Deposit");
            System.out.println("3. Withdraw");
            System.out.println("4. Exit");
            System.out.print("Choose an option: ");

            int option = sc.nextInt();

            switch (option) {
                case 1:
                    System.out.println("Current balance: $" + balance);
                    break;
                case 2:
                    System.out.print("Amount to deposit: ");
                    double deposit = sc.nextDouble();
                    if (deposit >= 0) {
                    balance += deposit;
                    System.out.println("Deposit successful. New balance: $" + balance);
                    } else {
                    	System.out.println("Error the deposit is negative");
                    }
                    break;
                case 3:
                    System.out.print("Amount to withdraw: ");
                    double withdraw = sc.nextDouble();
                    if (withdraw <= balance) {
                        balance -= withdraw;
                        System.out.println("Withdrawal successful new balance: $" + balance);
                    } else {
                        System.out.println("Insufficient funds.");
                    }
                    break;
                case 4:
                    System.out.println("Goodbye :D!");
                    sc.close();
                    return;
                default:
                    System.out.println("Invalid option >:(");
            }
        }
    }  
}
