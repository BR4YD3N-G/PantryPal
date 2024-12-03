package pantrypal;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginScreen {
    public static void main(String[] args) {
        // Create frame
        JFrame frame = new JFrame("Login");
        frame.setSize(400, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(null);

        // Title
        JLabel titleLabel = new JLabel("Pantry Inventory Manager", SwingConstants.CENTER);
        titleLabel.setBounds(50, 20, 300, 30);
        frame.add(titleLabel);

        // Username
        JLabel usernameLabel = new JLabel("Username:");
        usernameLabel.setBounds(50, 70, 100, 25);
        frame.add(usernameLabel);

        JTextField usernameField = new JTextField();
        usernameField.setBounds(150, 70, 200, 25);
        frame.add(usernameField);

        // Password
        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setBounds(50, 110, 100, 25);
        frame.add(passwordLabel);

        JPasswordField passwordField = new JPasswordField();
        passwordField.setBounds(150, 110, 200, 25);
        frame.add(passwordField);

        // Login Button
        JButton loginButton = new JButton("Login");
        loginButton.setBounds(50, 160, 100, 30);
        frame.add(loginButton);

        // Register Button
        JButton registerButton = new JButton("Register");
        registerButton.setBounds(250, 160, 100, 30);
        frame.add(registerButton);

        // ActionListener for Login
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());

                if (username.isEmpty() || password.isEmpty()) {
                    JOptionPane.showMessageDialog(frame, "Please fill out all fields!", "Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    // Placeholder for authentication logic
                    JOptionPane.showMessageDialog(frame, "Login successful (placeholder)!", "Success", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });

        // ActionListener for Register
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(frame, "Register functionality coming soon!", "Info", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        // Make frame visible
        frame.setVisible(true);
    }
}

