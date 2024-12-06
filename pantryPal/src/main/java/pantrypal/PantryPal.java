package pantrypal;

import java.util.ArrayList;
import java.util.List;

import javax.swing.*;

/**
 * Represents the main application for managing users and their pantries.
 * 
 * @author Brayden Graham
 * @author Kenyon Hanson
 */

public class PantryApp {

    private String appName; // The name of the application
    private List<User> userList; // A list of registered users
    private User currentUser; // The currently logged-in user
    private List<String> notifications; // A list to hold notifications
    private List<ShoppingListItem> shoppingList; // List to hold shopping list items

    // Constructor to initialize the PantryApp with a name
    public PantryApp(String appName) {
        this.appName = appName;
        this.userList = new ArrayList<>();
        this.notifications = new ArrayList<>();  // Initialize the notifications list
        this.shoppingList = new ArrayList<>();  // Initialize the shopping list
    }

    // Starts the application (placeholder method)
    public void startApp() {
        System.out.println(appName + " is starting...");
    }

    // Registers a new user in the system
    public void registerUser(User user) {
        for (User existingUser : userList) {
            if (existingUser.getUsername().equals(user.getUsername())) {
                throw new IllegalArgumentException("Username already exists: " + user.getUsername());
            }
        }
        userList.add(user);
        System.out.println("User registered: " + user.getUsername());
    }

    // Logs in a user with the given username and password
    public User loginUser(String username, String password) {
        for (User user : userList) {
            if (user.getUsername().equals(username) && user.validatePassword(password)) {
                System.out.println("User logged in: " + username);
                currentUser = user; // Set the current user
                return user;
            }
        }
        throw new IllegalArgumentException("Invalid username or password.");
    }

    // Gets the currently logged-in user
    public User getCurrentUser() {
        return currentUser;
    }

    // Gets the list of notifications for the current user
    public List<String> getNotifications() {
        return notifications;
    }

    // Adds a new notification for the current user
    public void addNotification(String notification) {
        notifications.add(notification);
    }

    // Shopping List Methods

    // Adds a shopping list item
    public void addShoppingListItem(ShoppingListItem item) {
        shoppingList.add(item);
    }

    // Removes a shopping list item by name
    public boolean removeShoppingListItem(String itemName) {
        return shoppingList.removeIf(item -> item.getItemName().equalsIgnoreCase(itemName));
    }

    // Retrieves the list of shopping list items
    public List<ShoppingListItem> getShoppingList() {
        return shoppingList;
    }

    // Clears all shopping list items
    public void clearShoppingList() {
        shoppingList.clear();
    }

    // Main method to start the application
    public static void main(String[] args) {
        // Create an instance of the application with the name "Pantry Manager"
        PantryApp pantryApp = new PantryApp("PantryPal");

        // Register some test users
        User user1 = new User("bob_bill", "password123");
        User user2 = new User("greg_smith", "password456");
        pantryApp.registerUser(user1);
        pantryApp.registerUser(user2);

        // Set up the login screen and pass the PantryApp instance to it
        SwingUtilities.invokeLater(() -> {
            LoginScreen loginScreen = new LoginScreen(pantryApp);
            loginScreen.setVisible(true);
        });
    }
}
