package pantrypal;

import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 * The NotificationsScreen class provides a user interface for viewing, marking as read,
 * and deleting notifications. Users can also navigate back to the HomeScreen from this screen.
 * 
 * @author Kenyon Hanson
 */

@SuppressWarnings("serial")
public class NotificationsScreen extends JFrame {
    private DefaultListModel<String> notificationModel; // Holds notifications
    private JList<String> notificationList; // Displays notifications
    private JButton markAllReadButton; // Button to mark all notifications as read
    private JButton deleteAllButton; // Button to delete all notifications
    private JButton backToHomeButton;  // Button to navigate back to HomeScreen

    /**
     * Constructs a NotificationsScreen instance with a list of notifications.
     * Sets up the user interface for displaying, marking as read, and deleting notifications.
     *
     * @param app The PantryApp instance used for navigation back to the HomeScreen.
     * @param notifications A list of notifications to display on the screen.
     */
    
    public NotificationsScreen(PantryApp app, List<String> notifications) {
        setTitle("Notifications");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        // Initialize the list model and populate it with notifications
        notificationModel = new DefaultListModel<>();
        for (String notification : notifications) {
            notificationModel.addElement(notification);
        }

        // Notification list setup
        notificationList = new JList<>(notificationModel);
        notificationList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollPane = new JScrollPane(notificationList);

        // Buttons setup
        markAllReadButton = new JButton("Mark All as Read");
        deleteAllButton = new JButton("Delete All");
        backToHomeButton = new JButton("Back to Home");
        
        // Button panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(markAllReadButton);
        buttonPanel.add(deleteAllButton);
        buttonPanel.add(backToHomeButton);

        // Add components to the frame
        add(new JLabel("Your Notifications:"), BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        // Button actions
        markAllReadButton.addActionListener(e -> markAllAsRead());
        deleteAllButton.addActionListener(e -> deleteAllNotifications());
        
        // Back to Home button action
        backToHomeButton.addActionListener(e -> {
            HomeScreen homeScreen = new HomeScreen(app);  // Pass the PantryApp instance
            homeScreen.setVisible(true);
            dispose();  // Close the current screen
        });
    }

    /**
     * Marks all notifications in the list as read by appending "(Read)" to each notification.
     * If there are no notifications, shows a message indicating so.
     */
    
    private void markAllAsRead() {
        if (!notificationModel.isEmpty()) {
            for (int i = 0; i < notificationModel.size(); i++) {
                notificationModel.set(i, notificationModel.get(i) + " (Read)");
            }
        } else {
            JOptionPane.showMessageDialog(this, "No notifications to mark as read.");
        }
    }

    /**
     * Deletes all notifications from the list.
     * If there are no notifications, shows a message indicating so.
     */
    
    private void deleteAllNotifications() {
        if (!notificationModel.isEmpty()) {
            notificationModel.clear();
        } else {
            JOptionPane.showMessageDialog(this, "No notifications to delete.");
        }
    }
}
