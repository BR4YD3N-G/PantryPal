import java.time.LocalDate;

/**
 * Represents an item in the pantry with its details such as name, quantity,
 * unit of measurement, expiration date, and category.
 * @author brayden graham
 */
public class PantryItem {

    // Attributes
    private String itemName;   // The name of the pantry item (e.g., "Milk")
    private int quantity;      // The quantity of the item in the pantry
    private String unit;       // The unit of measurement for the item (e.g., "liters", "kg")
    private LocalDate expirationDate; // The expiration date of the item
    private String category;   // The category of the item (e.g., "Dairy")

    /**
     * Constructs a PantryItem with the specified details.
     *
     * @param itemName The name of the pantry item
     * @param quantity The quantity of the item
     * @param unit The unit of measurement for the item
     * @param expirationDate The expiration date of the item
     * @param category The category of the item
     */
    public PantryItem(String itemName, int quantity, String unit, LocalDate expirationDate, String category) {
        this.itemName = itemName;
        this.quantity = quantity;
        this.unit = unit;
        this.expirationDate = expirationDate;
        this.category = category;
    }

    /**
     * Returns the name of the pantry item.
     *
     * @return the name of the pantry item
     */
    public String getItemName() {
        return itemName;
    }

    /**
     * Returns the quantity of the pantry item.
     *
     * @return the quantity of the pantry item
     */
    public int getQuantity() {
        return quantity;
    }

    /**
     * Returns the unit of measurement for the pantry item.
     *
     * @return the unit of measurement for the pantry item
     */
    public String getUnit() {
        return unit;
    }

    /**
     * Returns the expiration date of the pantry item.
     *
     * @return the expiration date of the pantry item
     */
    public LocalDate getExpirationDate() {
        return expirationDate;
    }

    /**
     * Returns the category of the pantry item.
     *
     * @return the category of the pantry item
     */
    public String getCategory() {
        return category;
    }


    /**
     * Sets the name of the pantry item.
     *
     * @param itemName The name of the pantry item
     */
    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    /**
     * Sets the quantity of the pantry item.
     *
     * @param quantity The quantity of the pantry item
     */
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    /**
     * Sets the unit of measurement for the pantry item.
     *
     * @param unit The unit of measurement for the pantry item
     */
    public void setUnit(String unit) {
        this.unit = unit;
    }

    /**
     * Sets the expiration date of the pantry item.
     *
     * @param expirationDate The expiration date of the pantry item
     */
    public void setExpirationDate(LocalDate expirationDate) {
        this.expirationDate = expirationDate;
    }

    /**
     * Sets the category of the pantry item.
     *
     * @param category The category of the pantry item
     */
    public void setCategory(String category) {
        this.category = category;
    }

    /**
     * Updates the quantity of the pantry item by the specified amount.
     * The quantity cannot be negative after updating.
     *
     * @param amount The amount to increase or decrease the quantity
     * @throws IllegalArgumentException If the resulting quantity would be negative
     */
    public void updateQuantity(int amount) {
        if (this.quantity + amount < 0) {
            throw new IllegalArgumentException("Quantity cannot be negative.");
        }
        this.quantity += amount;
    }

    /**
     * Checks if the pantry item is expired based on the current date.
     *
     * @return true if the item is expired, false otherwise
     */
    public boolean isExpired() {
        return LocalDate.now().isAfter(expirationDate);
    }

    /**
     * Returns a string representation of the PantryItem.
     *
     * @return a string representation of the PantryItem
     */
    @Override
    public String toString() {
        return "PantryItem{" +
                "itemName='" + itemName + '\'' +
                ", quantity=" + quantity +
                ", unit='" + unit + '\'' +
                ", expirationDate=" + expirationDate +
                ", category='" + category + '\'' +
                '}';
    }
}
