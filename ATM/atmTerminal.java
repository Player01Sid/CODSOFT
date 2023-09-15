import java.util.*;

class Account {
    float balance;
    Scanner scan = new Scanner(System.in);

    void checkBalance() {
        System.out.println("\nBalance: " + balance);
    }

    void deposit() {
        System.out.print("\nEnter amount to deposit:");
        float amount = scan.nextFloat();
        balance += amount;
        System.out.println("\nTransaction Completed.");
    }

    void withdraw() {

        System.out.print("\nEnter amount to withdraw:");
        float amount = scan.nextFloat();
        if (amount > balance) {
            System.out.println("\nTransaction failed. Insufficient balance.");
            return;
        } else {
            System.out.println("\nTransaction Completed.");
            balance -= amount;
        }
    }

}

public class atmTerminal {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        Account acc = new Account();

        System.out.println("Welcome!\n");

        int c = 0;
        while (c != 4) {
            System.out.print("\n1. Check Balance\n2. Deposit\n3. Withdraw\n4. Exit\nEnter your choice number: ");
            c = scan.nextInt();
            switch (c) {
                case 1:
                    acc.checkBalance();
                    break;
                case 2:
                    acc.deposit();
                    break;
                case 3:
                    acc.withdraw();
                    break;
                case 4:
                    break;
                default:
                    System.out.println("\nEnter valid choide.");
            }
        }
    }
}
