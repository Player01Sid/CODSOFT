import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

class GameMenu extends JFrame {
    JLabel Welcome;
    JButton Start, Exit;
    GameMenu z;

    GameMenu() {

        this.setBounds(500, 250, 240, 180);
        this.setLayout(null);
        this.setTitle("Random Number Game");
        this.getContentPane().setBackground(Color.LIGHT_GRAY);
        this.addWindowListener(new WindowAdapter() {

            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });

        JButton Start = new JButton("Start");
        JButton Exit = new JButton("Exit");
        JLabel Welcome = new JLabel("Welcome!");
        z = this;

        Welcome.setBounds(10, 20, 200, 40);
        Start.setBounds(20, 100, 80, 20);
        Exit.setBounds(120, 100, 80, 20);
        Welcome.setFont(new Font("Serif", Font.BOLD, 20));

        Start.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int highscore = initGame();
                Welcome.setText("High Score: " + highscore);
            }
        });

        Exit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        this.add(Welcome);
        this.add(Start);
        this.add(Exit);
        this.setVisible(true);
    }

    public int initGame() {
        Game g = new Game(z, "Let's Play", true);
        return g.highscore;
    }
}

class Game extends JDialog {
    static Random obj = new Random();
    int a = obj.nextInt(100);
    int score = 11;
    int highscore = -1;
    JLabel Guess;
    JButton Exit, Check;
    JTextField guess;

    Game(Frame owner, String title, boolean modal) {
        super(owner, "Let's Play", modal);
        this.getContentPane().setBackground(Color.LIGHT_GRAY);
        this.setBounds(500, 250, 270, 180);
        this.setLayout(null);
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                dispose();
            }
        });

        JButton Exit = new JButton("Exit");
        JButton Check = new JButton("Check");
        JLabel Guess = new JLabel("Guess the Number: ");
        JLabel Output = new JLabel("Good luck!");
        JTextField guess = new JTextField();

        Guess.setBounds(10, 20, 120, 20);
        guess.setBounds(140, 20, 100, 20);
        Output.setBounds(20, 60, 150, 20);
        Check.setBounds(20, 100, 80, 20);
        Exit.setBounds(140, 100, 80, 20);

        Check.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Starting the New Round
                try {
                    Output.setText("Hint: ");
                    if (score == 12) {
                        Check.setText("Check");
                        Output.setText("Good luck!");
                        score--;
                    } else if (score > 2) {
                        --score;
                        int i = Integer.parseInt(guess.getText());
                        // Correct Guess
                        if (i == a) {
                            Output.setText("Correct!\nScore: " + score);
                            Check.setText("New Round");
                            a = obj.nextInt(100);
                            if (score > highscore) {
                                highscore = score;

                            }
                            score = 12;
                            // Giving hints while wrong guess
                        } else {
                            if (mod(i - a) <= 5) {
                                Output.setText(Output.getText() + "Close ");
                            } else if (mod(i - a) > 10) {
                                Output.setText(Output.getText() + "Too ");
                            }

                            if (i < a) {
                                Output.setText(Output.getText() + "low");
                            } else if (i > a) {
                                Output.setText(Output.getText() + "high");
                            }
                        }
                        // Restarting new round after losing the Game
                    } else if (score == 1) {
                        Check.setText("Check");
                        Output.setText("Good luck!");
                        a = obj.nextInt(100);
                        score = 11;
                        // Game lost after 10 trials
                    } else {
                        score--;
                        Output.setText("Game Over");
                        Check.setText("Retry");
                    }
                } catch (Exception E) {
                    Output.setText("Enter valid Guess");
                    score++;
                }
            }
        });

        Exit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                dispose();

            }
        });
        this.add(Check);
        this.add(Exit);
        this.add(Guess);
        this.add(guess);
        this.add(Output);
        this.setVisible(true);
    }

    static int mod(int a) {
        if (a < 0) {
            return -a;
        } else {
            return a;
        }
    }
}

public class gameGUI {
    static Random obj = new Random();
    static Scanner scan = new Scanner(System.in);

    public static void main(String args[]) {
        GameMenu ex = new GameMenu();
    }
}
