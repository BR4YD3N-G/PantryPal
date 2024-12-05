package pantrypal;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents the main application for managing users and their pantries.
 */
public class PantryPal {

    private String appName; // The name of the application
    private List<User> userList; // A list of registered users

    /**
     * Constructor to initialize the PantryApp with a name.
     *
     * @param appName The name of the application
     */
    public PantryPal(String appName) {
        this.appName = appName;
        this.userList = new ArrayList<>();
    }

    /**
     * Starts the application (placeholder method).
     */
    public void startApp() {
        // Application start logic would go here (e.g., setting up GUI or CLI)
        System.out.println(appName + " is starting...");
    }

    /**
     * Registers a new user in the system.
     *
     * @param user The user to register
     * @throws IllegalArgumentException If a user with the same username already exists
     */
    public void registerUser(User user) {
        // Check if the username is unique
        for (User existingUser : userList) {
            if (existingUser.getUsername().equals(user.getUsername())) {
                throw new IllegalArgumentException("Username already exists: " + user.getUsername());
            }
        }
        userList.add(user);
        System.out.println("User registered: " + user.getUsername());
    }

    /**
     * Logs in a user with the given username and password.
     *
     * @param username The username of the user
     * @param password The password of the user
     * @return The logged-in User object if authentication is successful
     * @throws IllegalArgumentException If the username or password is invalid
     */
    public User loginUser(String username, String password) {
        for (User user : userList) {
            if (user.getUsername().equals(username) && user.validatePassword(password)) {
                System.out.println("User logged in: " + username);
                return user;
            }
        }
        throw new IllegalArgumentException("Invalid username or password.");
    }
}

