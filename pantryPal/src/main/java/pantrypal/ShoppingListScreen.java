package pantrypal;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class ShoppingListScreen {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Shopping List");
        frame.setSize(600, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(null);

        JLabel titleLabel = new JLabel("Shopping List", SwingConstants.CENTER);
        titleLabel.setBounds(200, 10, 200, 30);
        frame.add(titleLabel);

        String[] columnNames = {"Item Name", "Quantity", "Priority"};
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);
        JTable table = new JTable(model);

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(50, 50, 500, 200);
        frame.add(scrollPane);

        JButton addButton = new JButton("Add Item");
        addButton.setBounds(50, 270, 120, 30);
        frame.add(addButton);

        JButton removeButton = new JButton("Remove Item");
        removeButton.setBounds(200, 270, 150, 30);
        frame.add(removeButton);

        JButton clearListButton = new JButton("Clear List");
        clearListButton.setBounds(380, 270, 120, 30);
        frame.add(clearListButton);

        frame.setVisible(true);
    }
}
