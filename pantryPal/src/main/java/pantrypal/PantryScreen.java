package pantrypal;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

/**
 * PantryScreen is the screen where users can manage their pantry items.
 * It displays a table of pantry items and provides buttons for adding, removing,
 * and checking the expiration of items.
 * 
 * @author Kenyon Hanson
 */

@SuppressWarnings("serial")
public class PantryScreen extends JFrame {
    private DefaultTableModel pantryTableModel; // Table model for pantry items
    private JTable pantryTable; // Displays pantry items
    private JButton addItemButton, removeItemButton, checkExpirationButton, backToHomeButton;
    private List<PantryItem> pantryItems; // List of pantry items
    @SuppressWarnings("unused")
	private PantryApp app;  // Reference to PantryApp for managing user data

    /**
     * Constructor to initialize PantryScreen with a reference to the PantryApp instance.
     * Sets up the layout, pantry items, table, and buttons for user interaction.
     *
     * @param app The PantryApp instance used for managing pantry items and user data.
     */
    
    public PantryScreen(PantryApp app) {
        this.app = app;

        setTitle("Pantry Manager");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        // Initialize pantryItems from current user's pantry
        this.pantryItems = app.getCurrentUser().viewPantry();

        // Table setup
        String[] columnNames = {"Item Name", "Quantity", "Unit", "Expiration Date", "Category"};
        pantryTableModel = new DefaultTableModel(columnNames, 0);
        pantryTable = new JTable(pantryTableModel);
        JScrollPane scrollPane = new JScrollPane(pantryTable);

        // Buttons
        addItemButton = new JButton("Add Item");
        removeItemButton = new JButton("Remove Item");
        checkExpirationButton = new JButton("Check Expiration");
        backToHomeButton = new JButton("Back to Home");

        // Button panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(addItemButton);
        buttonPanel.add(removeItemButton);
        buttonPanel.add(checkExpirationButton);
        buttonPanel.add(backToHomeButton);

        // Add components to frame
        add(new JLabel("Manage Your Pantry:"), BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        // Button actions
        addItemButton.addActionListener(e -> openAddItemDialog());
        removeItemButton.addActionListener(e -> removeSelectedItem());
        checkExpirationButton.addActionListener(e -> highlightExpiredItems());

        // Back to Home button action
        backToHomeButton.addActionListener(e -> {
            HomeScreen homeScreen = new HomeScreen(app); // Pass app to HomeScreen
            homeScreen.setVisible(true);
            dispose();  // Close current screen
        });
    }

    /**
     * Opens a dialog to allow the user to add a new pantry item.
     * The user inputs the item name, quantity, unit, expiration date, and category.
     * After validation, the item is added to the pantry and displayed in the table.
     */
    
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
                User.addToPantry("23",itemName, String.valueOf(quantity), unit, expirationDate, category);
                PantryItem item = new PantryItem(itemName, quantity, unit, expirationDate, category);
                pantryItems.add(item);

                // Update table
                pantryTableModel.addRow(new Object[] {
                    item.getItemName(), item.getQuantity(), item.getUnit(),
                    item.getExpirationDate().toString(), item.getCategory()
                });
            } catch (NumberFormatException | DateTimeParseException ex) {
                JOptionPane.showMessageDialog(this, "Invalid input. Please check your fields.");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * Removes the selected pantry item from the list and updates the table.
     * If no item is selected, an error message is shown.
     */
    
    private void removeSelectedItem() {
        int selectedRow = pantryTable.getSelectedRow();
        if (selectedRow >= 0) {
            pantryItems.remove(selectedRow); // Remove from list
            pantryTableModel.removeRow(selectedRow); // Remove from table
        } else {
            JOptionPane.showMessageDialog(this, "Please select an item to remove.");
        }
    }

    /**
     * Highlights the pantry items that have expired.
     * The expired items are selected in the table for easy identification.
     */
    
    private void highlightExpiredItems() {
        for (int i = 0; i < pantryItems.size(); i++) {
            PantryItem item = pantryItems.get(i);
            if (item.isExpired()) {
                pantryTable.setRowSelectionInterval(i, i); // Select expired item
            }
        }
    }
}

