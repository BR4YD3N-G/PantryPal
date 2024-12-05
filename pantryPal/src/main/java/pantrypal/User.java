package pantrypal;

import java.io.*;
import java.nio.file.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
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

    /**
     * Constructs a User with the specified username and password.
     * Generates a unique ID and salt, and hashes the password.
     *
     * @param username The username of the user
     * @param password The plaintext password of the user
     */
    public User(String username, String password) {
        this.id = generateUniqueId();
        this.username = username;
        this.salt = generateSalt(); // Create a unique salt for this user
        this.hashedPassword = hashPassword(password, this.salt);
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
     * Adds an item to the user's pantry.
     *
     * @param itemName The name of the item
     * @param quantity The quantity of the item
     * @throws IOException If an I/O error occurs
     */
    public void addToPantry(String itemName, int quantity) throws IOException {
        Path filePath = getPantryFilePath();

        // Ensure the directory exists
        Files.createDirectories(filePath.getParent());

        // Write or append pantry data
        try (BufferedWriter writer = Files.newBufferedWriter(filePath, StandardOpenOption.CREATE, StandardOpenOption.APPEND)) {
            String record = String.format("%s,%s,%d%n", id, itemName, quantity);
            writer.write(record);
        }
    }

    /**
     * Removes an item from the user's pantry.
     *
     * @param itemName The name of the item to remove
     * @return True if the item was removed, false otherwise
     * @throws IOException If an I/O error occurs
     */
    public boolean removeFromPantry(String itemName) throws IOException {
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
                String userId = parts[0];
                String item = parts[1];

                if (userId.equals(id) && item.equals(itemName)) {
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
     * Retrieves a list of the user's pantry items.
     *
     * @return A list of strings representing pantry items and quantities
     * @throws IOException If an I/O error occurs
     */
    public List<String> getPantryItems() throws IOException {
        Path filePath = getPantryFilePath();
        List<String> items = new ArrayList<>();

        if (Files.exists(filePath)) {
            List<String> allLines = Files.readAllLines(filePath);

            for (String line : allLines) {
                String[] parts = line.split(",");
                if (parts.length == 3) {
                    String userId = parts[0];
                    String itemName = parts[1];
                    String quantity = parts[2];

                    if (userId.equals(id)) {
                        items.add(String.format("%s: %s", itemName, quantity));
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
