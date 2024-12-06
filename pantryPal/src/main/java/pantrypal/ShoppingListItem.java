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
        this.quantity = (quantity > 0) ? quantity : 1; // Ensure positive quantity
        this.priority = priority != null ? priority : "Medium"; // Default to "Medium" if no priority provided
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
        if (this.quantity < 0) { // Prevent quantity from going negative
            this.quantity = 0;
        }
    }

    /**
     * Returns a string representation of the shopping list item.
     *
     * @return A string describing the item with name, quantity, and priority.
     */
    @Override
    public String toString() {
        return itemName + " (Quantity: " + quantity + ", Priority: " + priority + ")";
    }

    /**
     * Compares this shopping list item with another for equality.
     *
     * @param obj The other object to compare.
     * @return True if the items are equal (same name).
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        ShoppingListItem other = (ShoppingListItem) obj;
        return itemName.equalsIgnoreCase(other.itemName); // Compare by name, ignoring case
    }

    /**
     * Generates a hash code for the shopping list item.
     *
     * @return The hash code for this item.
     */
    @Override
    public int hashCode() {
        return itemName.toLowerCase().hashCode(); // Generate hash based on item name (case-insensitive)
    }
}

