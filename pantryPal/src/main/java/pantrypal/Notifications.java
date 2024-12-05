package pantrypal;

import java.io.*;
import java.nio.file.*;
import java.util.*;

/**
 * Manages notifications for the PantryPal application.
 * Provides functionality for adding, retrieving, and clearing notifications.
 * @author brayden graham
 */
public class Notifications {
    private static final String DIRECTORY_NAME = "PantryPal";
    private static final String NOTIFICATIONS_FILE_NAME = "notifications.csv";
    private String userId;

    /**
     * Constructs a Notifications instance for a specific user.
     *
     * @param userId The ID of the user
     */
    public Notifications(String userId) {
        this.userId = userId;
    }

    /**
     * Adds a notification for the user.
     *
     * @param message The notification message
     * @throws IOException If an I/O error occurs
     */
    public void addNotification(String message) throws IOException {
        Path filePath = getNotificationsFilePath();

        // Ensure the directory exists
        Files.createDirectories(filePath.getParent());

        // Write or append the notification
        try (BufferedWriter writer = Files.newBufferedWriter(filePath, StandardOpenOption.CREATE, StandardOpenOption.APPEND)) {
            String record = String.format("%s,%s%n", userId, message);
            writer.write(record);
        }
    }

    /**
     * Retrieves all notifications for the user.
     *
     * @return A list of notifications for the user
     * @throws IOException If an I/O error occurs
     */
    public List<String> getNotifications() throws IOException {
        Path filePath = getNotificationsFilePath();
        List<String> userNotifications = new ArrayList<>();

        if (Files.exists(filePath)) {
            List<String> allLines = Files.readAllLines(filePath);

            for (String line : allLines) {
                String[] parts = line.split(",", 2); // Split into two parts: userId, message
                if (parts.length == 2) {
                    String notificationUserId = parts[0];
                    String message = parts[1];

                    if (notificationUserId.equals(userId)) {
                        userNotifications.add(message);
                    }
                }
            }
        }

        return userNotifications;
    }

    /**
     * Clears all notifications for the user.
     *
     * @throws IOException If an I/O error occurs
     */
    public void clearNotifications() throws IOException {
        Path filePath = getNotificationsFilePath();

        if (!Files.exists(filePath)) {
            return; // No notifications to clear
        }

        List<String> allLines = Files.readAllLines(filePath);
        List<String> updatedLines = new ArrayList<>();

        for (String line : allLines) {
            String[] parts = line.split(",", 2);
            if (parts.length == 2) {
                String notificationUserId = parts[0];

                // Keep notifications that belong to other users
                if (!notificationUserId.equals(userId)) {
                    updatedLines.add(line);
                }
            }
        }

        // Rewrite the file with the remaining notifications
        Files.write(filePath, updatedLines);
    }

    /**
     * Utility method to get the file path for the notifications CSV file.
     *
     * @return The Path to the notifications file
     */
    private static Path getNotificationsFilePath() {
        String userHome = System.getProperty("user.home");
        return Paths.get(userHome, DIRECTORY_NAME, NOTIFICATIONS_FILE_NAME);
    }
}
