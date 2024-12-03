package pantrypal;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

public class PantryScreen extends JFrame {
    private DefaultTableModel pantryTableModel; // Table model for pantry items
    private JTable pantryTable; // Displays pantry items
    private JButton addItemButton, removeItemButton, checkExpirationButton;
    private List<PantryItem> pantryItems; // List of pantry items

    public PantryScreen() {
        setTitle("Pantry Manager");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        // Initialize pantryItems
        pantryItems = new ArrayList<>();

        // Table setup
        String[] columnNames = {"Item Name", "Quantity", "Unit", "Expiration Date", "Category"};
        pantryTableModel = new DefaultTableModel(columnNames, 0);
        pantryTable = new JTable(pantryTableModel);
        JScrollPane scrollPane = new JScrollPane(pantryTable);

        // Buttons
        addItemButton = new JButton("Add Item");
        removeItemButton = new JButton("Remove Item");
        checkExpirationButton = new JButton("Check Expiration");

        // Button panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(addItemButton);
        buttonPanel.add(removeItemButton);
        buttonPanel.add(checkExpirationButton);

        // Add components to frame
        add(new JLabel("Manage Your Pantry:"), BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        // Button actions
        addItemButton.addActionListener(e -> openAddItemDialog());
        removeItemButton.addActionListener(e -> removeSelectedItem());
        checkExpirationButton.addActionListener(e -> highlightExpiredItems());
    }

    // Opens a dialog to add a new pantry item
    private void openAddItemDialog() {
        JPanel panel = new JPanel(new GridLayout(5, 2));

        // Input fields
        JTextField itemNameField = new JTextField();
        JTextField quantityField = new JTextField();
        JTextField unitField = new JTextField();
        JTextField expirationDateField = new JTextField(); // YYYY-MM-DD
        JTextField categoryField = new JTextField();

        // Add labels and fields to the panel
        panel.add(new JLabel("Item Name:"));
        panel.add(itemNameField);
        panel.add(new JLabel("Quantity:"));
        panel.add(quantityField);
        panel.add(new JLabel("Unit:"));
        panel.add(unitField);
        panel.add(new JLabel("Expiration Date (YYYY-MM-DD):"));
        panel.add(expirationDateField);
        panel.add(new JLabel("Category:"));
        panel.add(categoryField);

        int result = JOptionPane.showConfirmDialog(this, panel, "Add Pantry Item", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            try {
                String itemName = itemNameField.getText();
                int quantity = Integer.parseInt(quantityField.getText());
                String unit = unitField.getText();
                LocalDate expirationDate = LocalDate.parse(expirationDateField.getText(), DateTimeFormatter.ISO_LOCAL_DATE);
                String category = categoryField.getText();

                // Create PantryItem and add to list
                PantryItem item = new PantryItem(itemName, quantity, unit, expirationDate, category);
                pantryItems.add(item);

                // Update table
                pantryTableModel.addRow(new Object[]{
                        item.getItemName(), item.getQuantity(), item.getUnit(),
                        item.getExpirationDate().toString(), item.getCategory()
                });
            } catch (NumberFormatException | DateTimeParseException ex) {
                JOptionPane.showMessageDialog(this, "Invalid input. Please check your fields.");
            }
        }
    }

    // Removes the selected pantry item
    private void removeSelectedItem() {
        int selectedRow = pantryTable.getSelectedRow();
        if (selectedRow >= 0) {
            pantryItems.remove(selectedRow); // Remove from list
            pantryTableModel.removeRow(selectedRow); // Remove from table
        } else {
            JOptionPane.showMessageDialog(this, "Please select an item to remove.");
        }
    }

    // Highlights expired items
    private void highlightExpiredItems() {
        for (int i = 0; i < pantryItems.size(); i++) {
            PantryItem item = pantryItems.get(i);
            if (item.isExpired()) {
                pantryTable.setRowSelectionInterval(i, i); // Select expired item
            }
        }
    }

    // Main method for testing
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            PantryScreen frame = new PantryScreen();
            frame.setVisible(true);
        });
    }
}

