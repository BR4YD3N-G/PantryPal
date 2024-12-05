package pantrypal;

import javax.swing.*;

public class HomeScreen {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Home");
        frame.setSize(400, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(null);

        JLabel welcomeLabel = new JLabel("Welcome to Pantry Manager!", SwingConstants.CENTER);
        welcomeLabel.setBounds(50, 20, 300, 30);
        frame.add(welcomeLabel);

        JButton pantryButton = new JButton("View Pantry");
        pantryButton.setBounds(100, 80, 200, 40);
        frame.add(pantryButton);

        JButton shoppingListButton = new JButton("View Shopping List");
        shoppingListButton.setBounds(100, 140, 200, 40);
        frame.add(shoppingListButton);

        JButton notificationsButton = new JButton("View Notifications");
        notificationsButton.setBounds(100, 200, 200, 40);
        frame.add(notificationsButton);

        JButton logoutButton = new JButton("Logout");
        logoutButton.setBounds(100, 260, 200, 40);
        frame.add(logoutButton);

        frame.setVisible(true);
    }
}

