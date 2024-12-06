package pantrypal;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * LoginScreen is the screen where users can log into the Pantry Manager application.
 * It provides fields for entering a username and password, and buttons for logging in or registering.
 * 
 * @author Kenyon Hanson
 */

@SuppressWarnings("serial")
public class LoginScreen extends JFrame {
    @SuppressWarnings("unused")
	private PantryApp app; // Reference to PantryApp to communicate with it

    /**
     * Constructor to initialize LoginScreen with a reference to the PantryApp instance.
     * Sets up the layout, labels, text fields, and buttons for login and registration.
     *
     * @param app The PantryApp instance to communicate with other parts of the application.
     */
    
    public LoginScreen(PantryApp app) {
        this.app = app;  // Store the reference to PantryApp
        
        // Set up the JFrame
        setTitle("Login");
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

        // Login button
        JButton loginButton = new JButton("Login");
        loginButton.setBounds(150, 150, 100, 40);
        add(loginButton);

        // Register button
        JButton registerButton = new JButton("Register");
        registerButton.setBounds(150, 200, 100, 40);
        add(registerButton);

        // Login button action listener
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());
                
                try {
                    // Attempt login with PantryApp
                    app.loginUser(username, password);
                    JOptionPane.showMessageDialog(LoginScreen.this, "Login successful!");
                    
                    // Navigate to HomeScreen after successful login
                    HomeScreen homeScreen = new HomeScreen(app);
                    homeScreen.setVisible(true);
                    dispose(); // Close the login screen
                } catch (IllegalArgumentException ex) {
                    JOptionPane.showMessageDialog(LoginScreen.this, "Invalid username or password.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // Register button action listener
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Navigate to registration screen (You can implement this separately)
                RegisterScreen registerScreen = new RegisterScreen(app);
                registerScreen.setVisible(true);
                dispose(); // Close the login screen
            }
        });

        // Make the login screen visible
        setVisible(true);
    }
}
