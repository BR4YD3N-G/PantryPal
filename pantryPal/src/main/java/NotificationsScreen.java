package pantrypal;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

public class NotificationsScreen extends JFrame {
    private DefaultListModel<String> notificationModel; // Holds notifications
    private JList<String> notificationList; // Displays notifications
    private JButton markAllReadButton;
    private JButton deleteAllButton;

    public NotificationsScreen(List<String> notifications) {
        setTitle("Notifications");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        // Initialize the list model and populate it with notifications
        notificationModel = new DefaultListModel<>();
        for (String notification : notifications) {
            notificationModel.addElement(notification);
        }

        // Notification list
        notificationList = new JList<>(notificationModel);
        notificationList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollPane = new JScrollPane(notificationList);

        // Buttons
        markAllReadButton = new JButton("Mark All as Read");
        deleteAllButton = new JButton("Delete All");

        // Button panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(markAllReadButton);
        buttonPanel.add(deleteAllButton);

        // Add components to the frame
        add(new JLabel("Your Notifications:"), BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        // Button actions
        markAllReadButton.addActionListener(e -> markAllAsRead());
        deleteAllButton.addActionListener(e -> deleteAllNotifications());
    }

    // Marks all notifications as read
    private void markAllAsRead() {
        if (!notificationModel.isEmpty()) {
            for (int i = 0; i < notificationModel.size(); i++) {
                notificationModel.set(i, notificationModel.get(i) + " (Read)");
            }
        } else {
            JOptionPane.showMessageDialog(this, "No notifications to mark as read.");
        }
    }

    // Deletes all notifications
    private void deleteAllNotifications() {
        if (!notificationModel.isEmpty()) {
            notificationModel.clear();
        } else {
            JOptionPane.showMessageDialog(this, "No notifications to delete.");
        }
    }

    // Main method for testing
    public static void main(String[] args) {
        // Sample notifications for testing
        List<String> sampleNotifications = new ArrayList<>();
        sampleNotifications.add("Reminder: Check pantry expiration dates.");
        sampleNotifications.add("New item added to shopping list.");
        sampleNotifications.add("Don't forget to buy milk!");

        // Show the frame
        SwingUtilities.invokeLater(() -> {
            NotificationsScreen frame = new NotificationsScreen(sampleNotifications);
            frame.setVisible(true);
        });
    }
}
