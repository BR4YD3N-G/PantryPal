package pantrypal;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

/**
 * A class for displaying the shopping list screen and providing functionalities 
 * to add, remove, and clear items in the shopping list. Integrates with the 
 * PantryApp to manage shopping list data.
 * 
 * @author Kenyon Hanson
 */

@SuppressWarnings("serial")
public class ShoppingListScreen extends JFrame {

    private PantryApp app;  // Reference to the PantryApp instance for managing data
    private DefaultTableModel model;  // Table model for shopping list data
    private JTable table;  // Table for displaying shopping list items

    /**
     * Constructor to initialize the ShoppingListScreen.
     * Sets up the GUI components and loads existing shopping list data.
     * 
     * @param app The PantryApp instance to interact with the shopping list data.
     */
    
    public ShoppingListScreen(PantryApp app) {
        this.app = app;  // Store the reference to PantryApp

        // Set up the JFrame
        setTitle("Shopping List");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);  // Close only this window
        setLayout(null);

        // Title label
        JLabel titleLabel = new JLabel("Shopping List", SwingConstants.CENTER);
        titleLabel.setBounds(200, 10, 200, 30);
        add(titleLabel);

        // Table setup
        String[] columnNames = {"Item Name", "Quantity", "Priority"};
        model = new DefaultTableModel(columnNames, 0);  // Create table model with column headers
        table = new JTable(model);  // Create table using the model

        JScrollPane scrollPane = new JScrollPane(table);  // Add scrollable pane for the table
        scrollPane.setBounds(50, 50, 500, 200);
        add(scrollPane);

        // Buttons for various actions
        JButton addButton = new JButton("Add Item");
        addButton.setBounds(50, 270, 120, 30);
        add(addButton);

        JButton removeButton = new JButton("Remove Item");
        removeButton.setBounds(200, 270, 150, 30);
        add(removeButton);

        JButton clearListButton = new JButton("Clear List");
        clearListButton.setBounds(380, 270, 120, 30);
        add(clearListButton);

        // Back to Home button
        JButton backToHomeButton = new JButton("Back to Home");
        backToHomeButton.setBounds(200, 320, 200, 30);
        add(backToHomeButton);

        // Add action listener for Back to Home button
        backToHomeButton.addActionListener(e -> {
            HomeScreen homeScreen = new HomeScreen(app);  // Open HomeScreen
            homeScreen.setVisible(true);
            dispose();  // Close the current screen
        });

        // Add action listener for Add Item button
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openAddItemDialog();  // Open the dialog to add a new item
            }
        });

        // Add action listener for Remove Item button
        removeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                removeSelectedItem();  // Remove the selected item from the list
            }
        });

        // Add action listener for Clear List button
        clearListButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clearList();  // Clear the entire shopping list
            }
        });

        // Load existing shopping list items into the table
        loadShoppingList();

        // Make the frame visible
        setVisible(true);
    }

    /**
     * Opens a dialog to allow the user to add a new item to the shopping list.
     * Validates input and updates the table and data in PantryApp.
     */
    
    private void openAddItemDialog() {
        JPanel panel = new JPanel(new GridLayout(3, 2));  // Panel for input fields

        // Input fields
        JTextField itemNameField = new JTextField();
        JTextField quantityField = new JTextField();
        JTextField priorityField = new JTextField();

        // Add labels and fields to the panel
        panel.add(new JLabel("Item Name:"));
        panel.add(itemNameField);
        panel.add(new JLabel("Quantity:"));
        panel.add(quantityField);
        panel.add(new JLabel("Priority:"));
        panel.add(priorityField);

        // Show the dialog and capture user input
        int result = JOptionPane.showConfirmDialog(this, panel, "Add Shopping List Item", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            try {
                String itemName = itemNameField.getText();
                int quantity = Integer.parseInt(quantityField.getText());
                String priority = priorityField.getText();

                // Add item to the shopping list and table
                ShoppingListItem newItem = new ShoppingListItem(itemName, quantity, priority);
                app.addShoppingListItem(newItem);  // Add to PantryApp
                model.addRow(new Object[] { itemName, quantity, priority });  // Add to table
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Invalid input. Please check your fields.");
            }
        }
    }

    /**
     * Removes the selected item from the shopping list and updates the table and data in PantryApp.
     */
    
    private void removeSelectedItem() {
        int selectedRow = table.getSelectedRow();  // Get selected row index
        if (selectedRow >= 0) {
            String itemName = (String) model.getValueAt(selectedRow, 0);  // Get item name from table
            app.removeShoppingListItem(itemName);  // Remove from PantryApp
            model.removeRow(selectedRow);  // Remove from table
        } else {
            JOptionPane.showMessageDialog(this, "Please select an item to remove.");
        }
    }

    /**
     * Clears the entire shopping list after user confirmation.
     * Updates both the table and data in PantryApp.
     */
    
    private void clearList() {
        int response = JOptionPane.showConfirmDialog(this, "Are you sure you want to clear the list?", 
                                                      "Clear List", JOptionPane.YES_NO_OPTION);
        if (response == JOptionPane.YES_OPTION) {
            app.clearShoppingList();  // Clear list in PantryApp
            model.setRowCount(0);  // Clear all rows in the table
        }
    }

    /**
     * Loads the existing shopping list from PantryApp into the table.
     */
    
    private void loadShoppingList() {
        List<ShoppingListItem> items = app.getShoppingList();  // Get items from PantryApp
        for (ShoppingListItem item : items) {
            model.addRow(new Object[] { item.getItemName(), item.getQuantity(), item.getPriority() });  // Add to table
        }
    }
}
