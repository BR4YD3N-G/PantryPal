package pantrypal;

import java.io.*;
import java.nio.file.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.time.LocalDate;
import java.util.*;

/**
 * Represents a user in the PantryPal application.
 * Provides methods to manage user data and pantry items.
 * @author brayden graham
 */
public class User {
    private static final String DIRECTORY_NAME = "PantryPal";
    private static final String USER_FILE_NAME = "users.csv";
    private static final String PANTRY_FILE_NAME = "pantry.csv";
    private static final Set<String> usedIds = new HashSet<>();

    private String id;               // Unique user ID
    private String username;         // The username of the user
    private String hashedPassword;   // The hashed password of the user
    private String salt;             // The unique salt for this user
    
    public String getId() {
        return id;
    }
    public String getUsername() {
        return username;
    }
    public String getHashedPassword() {
        return hashedPassword;
    }
    public String getSalt() {
        return salt;
    }

    
    /**
     * Validates the given password against the stored hashed password.
     *
     * @param password The plaintext password to validate
     * @return True if the password is correct, false otherwise
     */
    public boolean validatePassword(String password) {
        String hashedInputPassword = hashPassword(password, this.salt); // Hash the input password using the stored salt
        return hashedInputPassword.equals(this.hashedPassword); // Compare with the stored hashed password
    }

    /**
     * Constructs a User with the specified username and password.
     * Generates a unique ID and salt, and hashes the password.
     *
     * @param username The username of the user
     * @param password The plaintext password of the user
     */
    public User(String username, String password) throws IOException {
        this.id = generateUniqueId();
        this.username = username;
        this.salt = generateSalt(); // Create a unique salt for this user
        this.hashedPassword = hashPassword(password, this.salt);
        saveUser();
    }

    /**
     * Constructor to reconstruct a user from stored data.
     *
     * @param id The user's unique ID
     * @param username The username
     * @param hashedPassword The hashed password
     * @param salt The salt used for hashing
     */
    public User(String id, String username, String hashedPassword, String salt) {
        this.id = id;
        this.username = username;
        this.hashedPassword = hashedPassword;
        this.salt = salt;
    }

    /**
     * Static method to load users from the 'users.csv' file.
     *
     * @return A list of User objects loaded from the file
     */
    public static List<User> loadUsers() {
        List<User> users = new ArrayList<>();
        Path userFilePath = getUserFilePath();

        // Check if the file exists
        if (!Files.exists(userFilePath)) {
            System.out.println("No user file found. Starting with an empty user list.");
            return users; // Return empty list
        }

        try (BufferedReader reader = Files.newBufferedReader(userFilePath)) {
            String line;

            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");

                if (parts.length == 4) { // Ensure valid data
                    String id = parts[0];
                    String username = parts[1];
                    String hashedPassword = parts[2];
                    String salt = parts[3];

                    // Reconstruct the User object
                    User user = new User(id, username, hashedPassword, salt);
                    users.add(user);
                }
            }

            System.out.println("Users loaded successfully.");
        } catch (IOException e) {
            System.err.println("Error loading users: " + e.getMessage());
        }

        return users;
    }



    /**
     * Saves the user to a CSV file in the application's directory.
     *
     * @throws IOException If an I/O error occurs
     */
    public void saveUser() throws IOException {
        Path filePath = getUserFilePath();

        // Ensure the directory exists
        Files.createDirectories(filePath.getParent());

        // Write or append user data
        try (BufferedWriter writer = Files.newBufferedWriter(filePath, StandardOpenOption.CREATE, StandardOpenOption.APPEND)) {
            String record = String.format("%s,%s,%s,%s%n", id, username, hashedPassword, salt);
            writer.write(record);
        }
    }

    /**
     * Adds an item to the pantry for a specific user ID.
     *
     * @param userId   The ID of the user
     * @param itemName The name of the item
     * @param quantity The quantity of the item
     * @throws IOException If an I/O error occurs
     */
    public static void addToPantry(String userId, String itemName, String quantity, String unit, LocalDate expirationDate, String category) throws IOException {
        Path filePath = getPantryFilePath();

        // Ensure the directory exists
        Files.createDirectories(filePath.getParent());

        // Write or append pantry data
        try (BufferedWriter writer = Files.newBufferedWriter(filePath, StandardOpenOption.CREATE, StandardOpenOption.APPEND)) {
            String record = String.format("%s,%s,%s,%s,%s,%s%n", userId, itemName, quantity, unit, expirationDate, category);
            writer.write(record);
        }
    }

    /**
     * Removes an item from the pantry for a specific user ID.
     *
     * @param userId   The ID of the user
     * @param itemName The name of the item to remove
     * @return True if the item was removed, false otherwise
     * @throws IOException If an I/O error occurs
     */
    public static boolean removeFromPantry(String userId, String itemName) throws IOException {
        Path filePath = getPantryFilePath();

        if (!Files.exists(filePath)) {
            return false; // File does not exist, nothing to remove
        }

        List<String> allLines = Files.readAllLines(filePath);
        List<String> updatedLines = new ArrayList<>();
        boolean removed = false;

        for (String line : allLines) {
            String[] parts = line.split(",");
            if (parts.length == 3) {
                String storedUserId = parts[0];
                String item = parts[1];

                if (storedUserId.equals(userId) && item.equals(itemName)) {
                    removed = true; // Skip this line to "remove" it
                } else {
                    updatedLines.add(line);
                }
            }
        }

        // Rewrite the file with the updated content
        Files.write(filePath, updatedLines);
        return removed;
    }


    /**
     * Retrieves a list of the user's pantry items in a user-friendly string format.
     *
     * @return A list of Strings representing pantry items
     * @throws IOException If an I/O error occurs
     */
    public List<PantryItem> viewPantry() {
        List<PantryItem> pantryItems = new ArrayList<>();
        
        try {
            // Retrieve pantry items from the user's pantry file
            pantryItems = getPantryItems(); // This will return List<PantryItem> based on your current setup
        } catch (IOException e) {
            // Handle the exception appropriately (log it, show an error message, etc.)
            System.err.println("Error reading pantry items: " + e.getMessage());
        }
        
        return pantryItems;
    }

    /**
     * Retrieves a list of the user's pantry items from the file.
     *
     * @return A list of strings representing pantry items and quantities
     * @throws IOException If an I/O error occurs
     */
    /**
     * Retrieves a list of the user's pantry items from the file.
     *
     * @return A list of PantryItem objects representing pantry items and quantities
     * @throws IOException If an I/O error occurs
     */
    private List<PantryItem> getPantryItems() throws IOException {
        Path filePath = getPantryFilePath();
        List<PantryItem> items = new ArrayList<>();

        if (Files.exists(filePath)) {
            List<String> allLines = Files.readAllLines(filePath);

            for (String line : allLines) {
                String[] parts = line.split(",");
                if (parts.length == 5) { // Ensure there are 5 parts (itemName, quantity, unit, expirationDate, category)
                    String userId = parts[0];
                    String itemName = parts[1];
                    int quantity = Integer.parseInt(parts[2]);
                    String unit = parts[3];
                    LocalDate expirationDate = LocalDate.parse(parts[4]);
                    String category = parts[5];

                    if (userId.equals(id)) {  // Match the userId
                        PantryItem item = new PantryItem(itemName, quantity, unit, expirationDate, category);
                        items.add(item);  // Add the item to the list
                    }
                }
            }
        }

        return items;
    }


    /**
     * Generates a unique 16-character alphanumeric ID.
     *
     * @return A unique 16-character ID
     */
    private String generateUniqueId() {
        SecureRandom random = new SecureRandom();
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder idBuilder = new StringBuilder();

        while (true) {
            for (int i = 0; i < 16; i++) {
                int index = random.nextInt(characters.length());
                idBuilder.append(characters.charAt(index));
            }
            String id = idBuilder.toString();

            // Ensure the ID is unique
            if (!usedIds.contains(id)) {
                usedIds.add(id);
                return id;
            }

            // Clear the builder and retry
            idBuilder.setLength(0);
        }
    }

    /**
     * Generates a secure random salt.
     *
     * @return A Base64-encoded random salt
     */
    private String generateSalt() {
        SecureRandom random = new SecureRandom();
        byte[] saltBytes = new byte[16]; // 128-bit salt
        random.nextBytes(saltBytes);
        return Base64.getEncoder().encodeToString(saltBytes);
    }

    /**
     * Hashes the password using SHA-256 and the provided salt.
     *
     * @param password The plaintext password
     * @param salt The salt to use for hashing
     * @return The hashed password as a Base64-encoded string
     */
    private String hashPassword(String password, String salt) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            String saltedPassword = password + salt;
            byte[] hashBytes = digest.digest(saltedPassword.getBytes());
            return Base64.getEncoder().encodeToString(hashBytes);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Hashing algorithm not available", e);
        }
    }

    // Utility methods to get file paths
    private static Path getUserFilePath() {
        String userHome = System.getProperty("user.home");
        return Paths.get(userHome, DIRECTORY_NAME, USER_FILE_NAME);
    }

    private static Path getPantryFilePath() {
        String userHome = System.getProperty("user.home");
        return Paths.get(userHome, DIRECTORY_NAME, PANTRY_FILE_NAME);
    }
}
