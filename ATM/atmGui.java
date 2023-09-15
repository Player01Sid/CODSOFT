import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

class Atm extends JFrame {
    float balance = 0;
    JLabel Welcome, output;
    JButton checkBalance, deposit, withdraw;
    Atm z;

    Atm() {

        this.setBounds(500, 250, 460, 200);
        this.setTitle("ATM");
        this.getContentPane().setBackground(Color.LIGHT_GRAY);
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });

        JLabel Welcome = new JLabel("Welcome!");
        JLabel output = new JLabel("Choose Function:-");
        JLabel Z = new JLabel();
        z = this;

        Welcome.setFont(new Font("Serif", Font.BOLD, 20));
        Welcome.setBounds(10, 10, 120, 30);
        output.setBounds(10, 50, 300, 20);
        JButton checkBalance = new JButton("Check Balance");
        JButton deposit = new JButton("Deposit");
        JButton withdraw = new JButton("Withdraw");
        checkBalance.setBounds(10, 120, 130, 30);
        deposit.setBounds(160, 120, 130, 30);
        withdraw.setBounds(310, 120, 130, 30);

        deposit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                funcDialog dlg = new funcDialog(z, "Deposit", true, "Deposited");
                float amt = dlg.amount;
                balance += amt;
                output.setText("Transaction Completed");
            }
        });

        withdraw.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                funcDialog dlg = new funcDialog(z, "Deposit", true, "Deposited");
                float amt = dlg.amount;
                if (balance >= amt) {
                    balance -= amt;
                    output.setText("Transaction Completed");
                } else {
                    output.setText("Transaction Failed, not enough Balance.");
                }

            }
        });

        checkBalance.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                output.setText("Your balance is " + balance);
            }
        });

        this.add(Welcome);
        this.add(output);
        this.add(checkBalance);
        this.add(deposit);
        this.add(withdraw);
        this.add(Z);
        this.setVisible(true);
    }
}

class funcDialog extends JDialog {
    JLabel output, temp;
    float amount;

    funcDialog(JFrame owner, String title, boolean modality, String func) {
        super(owner, title, modality);

        this.setBounds(500, 250, 220, 200);
        this.getContentPane().setBackground(Color.LIGHT_GRAY);
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                dispose();
            }
        });
        JLabel output = new JLabel("Enter Amount to be " + func + ":-\r\n");
        JLabel Z = new JLabel();
        JTextField input = new JTextField();
        JButton ok = new JButton("OK");

        output.setBounds(10, 20, 300, 20);
        input.setBounds(10, 60, 100, 25);
        ok.setBounds(10, 120, 80, 30);

        ok.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    amount = Float.parseFloat(input.getText());
                    dispose();
                } catch (Exception E) {
                    output.setText("Enter valid Amount:-");
                }
            }
        });
        this.add(output);
        this.add(input);
        this.add(ok);
        this.add(Z);
        this.setVisible(true);
    }
}

public class atmGui {
    public static void main(String[] args) {
        Atm atm = new Atm();
    }
}
