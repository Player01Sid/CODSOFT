import java.util.*;

public class gameTerminal {
    static Random obj = new Random();
    static Scanner scan = new Scanner(System.in);

    public static void main(String args[]) {
        int choice = -1;
        while (choice != 2) {
            System.out.println("---------\n1. Start\n2. Exit\nEnter choice: ");
            choice = scan.nextInt();
            if (choice == 1) {
                game();
            }
        }
    }

    static void game() { // function of main core game
        int a = obj.nextInt(100);
        int guess = -1;

        for (int i = 0; i < 10; i++) {
            System.out.print("Guess the Number: ");
            guess = scan.nextInt();
            if (guess == a) {
                System.out.println("Correct!\nScore: " + (10 - i));
                return;
            } else if (mod(guess - a) <= 5) {
                System.out.print("Close ");
            } else if (mod(guess - a) > 10) {
                System.out.print("Too ");
            }

            if (guess > a) {
                System.out.println("high");
            } else {
                System.out.println("low");
            }

        }
        System.out.println("Game Over!");
    }

    static int mod(int a) { // function to get mod of a number
        if (a < 0) {
            return -a;
        } else {
            return a;
        }
    }
}