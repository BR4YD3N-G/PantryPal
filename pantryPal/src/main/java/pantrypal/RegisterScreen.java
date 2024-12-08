package pantrypal;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

/**
 * The RegisterScreen class represents the user interface for the registration process.
 * It allows users to input a username and password to create a new account.
 * Upon successful registration, the user is redirected to the login screen.
 * 
 * @author Kenyon Hanson
 */

@SuppressWarnings("serial")
public class RegisterScreen extends JFrame {
    @SuppressWarnings("unused")
	private PantryApp app; // Reference to PantryApp to register users

    /**
     * Constructor to initialize the RegisterScreen with a reference to the PantryApp instance.
     * This sets up the UI elements and handles the user registration process.
     *
     * @param app The PantryApp instance used to register the user.
     */
    
    public RegisterScreen(PantryApp app) {
        this.app = app;  // Store the reference to PantryApp
        
        // Set up the JFrame
        setTitle("Register");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);

        // Username label and text field
        JLabel usernameLabel = new JLabel("Username:");
        usernameLabel.setBounds(50, 50, 100, 30);
        add(usernameLabel);

        JTextField usernameField = new JTextField();
        usernameField.setBounds(150, 50, 200, 30);
        add(usernameField);

        // Password label and password field
        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setBounds(50, 100, 100, 30);
        add(passwordLabel);

        JPasswordField passwordField = new JPasswordField();
        passwordField.setBounds(150, 100, 200, 30);
        add(passwordField);

        // Register button
        JButton registerButton = new JButton("Register");
        registerButton.setBounds(150, 150, 100, 40);
        add(registerButton);

        // Register button action listener
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());

                try {
                    // Create a new user object
                    User newUser = new User(username, password);
                    
                    // Register the user in the PantryApp
                    app.registerUser(newUser);
                    
                    // Show success message
                    JOptionPane.showMessageDialog(RegisterScreen.this, "Registration successful!");
                    
                    // Navigate to the login screen after registration
                    LoginScreen loginScreen = new LoginScreen(app);
                    loginScreen.setVisible(true);
                    dispose(); // Close the register screen
                } catch (IllegalArgumentException ex) {
                    // Show error message if the username already exists8
                    JOptionPane.showMessageDialog(RegisterScreen.this, "Username already exists.", "Error", JOptionPane.ERROR_MESSAGE);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        // Make the register screen visible
        setVisible(true);
    }
}