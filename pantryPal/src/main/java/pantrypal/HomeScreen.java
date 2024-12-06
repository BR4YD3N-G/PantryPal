package pantrypal;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

/**
 * HomeScreen is the main screen that users see upon logging into PantryPal.
 * It provides navigation buttons to other screens like Pantry, Shopping List, Notifications, and Logout.
 * 
 * @author Kenyon Hanson
 */

@SuppressWarnings("serial")
public class HomeScreen extends JFrame {
    @SuppressWarnings("unused")
	private PantryApp app;  // Reference to PantryApp to communicate with it

    /**
     * Constructor to initialize the HomeScreen with a reference to the PantryApp instance.
     * Sets up the JFrame, labels, and buttons for navigation.
     *
     * @param app The PantryApp instance to communicate with other parts of the application.
     */
    
    public HomeScreen(PantryApp app) {
        this.app = app;  // Store the reference to PantryApp
        
        // Set up the JFrame
        setTitle("Home");
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);

        // Welcome label
        JLabel welcomeLabel = new JLabel("Welcome to Pantry Manager!", SwingConstants.CENTER);
        welcomeLabel.setBounds(50, 20, 300, 30);
        add(welcomeLabel);

        // View Pantry button
        JButton pantryButton = new JButton("View Pantry");
        pantryButton.setBounds(100, 80, 200, 40);
        add(pantryButton);
        pantryButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Show PantryScreen when clicked
                PantryScreen pantryScreen = new PantryScreen(app);
                pantryScreen.setVisible(true);
                dispose();  // Close HomeScreen
            }
        });

        // View Shopping List button
        JButton shoppingListButton = new JButton("View Shopping List");
        shoppingListButton.setBounds(100, 140, 200, 40);
        add(shoppingListButton);
        shoppingListButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Show ShoppingListScreen when clicked
                ShoppingListScreen shoppingListScreen = new ShoppingListScreen(app);
                shoppingListScreen.setVisible(true);
                dispose();  // Close HomeScreen
            }
        });

        // View Notifications button
        JButton notificationsButton = new JButton("View Notifications");
        notificationsButton.setBounds(100, 200, 200, 40);
        add(notificationsButton);
        notificationsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Retrieve notifications from PantryApp
                List<String> notifications = app.getNotifications();

                // Show NotificationsScreen when clicked and pass the notifications
                NotificationsScreen notificationsScreen = new NotificationsScreen(app, notifications);
                notificationsScreen.setVisible(true);
                dispose();  // Close HomeScreen
            }
        });

        // Logout button
        JButton logoutButton = new JButton("Logout");
        logoutButton.setBounds(100, 260, 200, 40);
        add(logoutButton);
        logoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Navigate back to the login screen
                LoginScreen loginScreen = new LoginScreen(app);
                loginScreen.setVisible(true);
                dispose();  // Close HomeScreen
            }
        });

        // Set the HomeScreen visible
        setVisible(true);
    }
}
