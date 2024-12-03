package pantrypal;

/**
 * Represents an item in the shopping list.
 * 
 * @author Kenyon Hanson
 */
public class ShoppingListItem {
    private final String itemName; // Name of the shopping list item
    private int quantity;          // Quantity of the item
    private final String priority; // Priority level of the item (e.g., "High", "Medium", "Low")

    /**
     * Constructor to create a shopping list item.
     *
     * @param itemName The name of the item.
     * @param quantity The quantity of the item.
     * @param priority The priority of the item.
     */
    public ShoppingListItem(String itemName, int quantity, String priority) {
        this.itemName = itemName;
        this.quantity = quantity;
        this.priority = priority;
    }

    /**
     * Gets the name of the item.
     *
     * @return The item name.
     */
    public String getItemName() {
        return itemName;
    }

    /**
     * Gets the quantity of the item.
     *
     * @return The item quantity.
     */
    public int getQuantity() {
        return quantity;
    }

    /**
     * Gets the priority of the item.
     *
     * @return The item priority.
     */
    public String getPriority() {
        return priority;
    }

    /**
     * Updates the quantity of the item by a specified amount.
     *
     * @param amount The amount to update the quantity by. Can be positive or negative.
     */
    public void updateQuantity(int amount) {
        this.quantity += amount;
    }
}
