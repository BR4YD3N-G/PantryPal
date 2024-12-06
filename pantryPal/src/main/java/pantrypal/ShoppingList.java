package pantrypal;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a shopping list for managing items to be purchased.
 * 
 * @author Kenyon Hanson
 */

public class ShoppingList {
    private List<ShoppingListItem> items; // List of shopping list items

    /**
     * Constructor to initialize the shopping list.
     */
    
    public ShoppingList() {
        this.items = new ArrayList<>();
    }

    /**
     * Adds an item to the shopping list.
     *
     * @param item The item to be added.
     */
    
    public void addItem(ShoppingListItem item) {
        items.add(item);
    }

    /**
     * Removes an item from the shopping list by its name.
     *
     * @param itemName The name of the item to be removed.
     * @return true if the item was removed successfully, false otherwise.
     */
    
    public boolean removeItem(String itemName) {
        return items.removeIf(item -> item.getItemName().equalsIgnoreCase(itemName));
    }

    /**
     * Retrieves the list of all items in the shopping list.
     *
     * @return A list of shopping list items.
     */
    
    public List<ShoppingListItem> listItems() {
        return new ArrayList<>(items); // Return a copy to avoid modification.
    }

    /**
     * Clears all items from the shopping list.
     */
    
    public void clearList() {
        items.clear();
    }
}
