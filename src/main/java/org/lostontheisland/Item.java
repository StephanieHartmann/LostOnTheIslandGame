package org.lostontheisland;
/**
 * ITEM CLASS - Represents game objects
 *
 * This class stores information about each game item:
 * - Item name (e.g., "knife", "bottle")
 * - Detailed description
 * - Whether it can be used or not
 */
public class Item {
    // Private attributes - encapsulation
    private String name;        // Item name (e.g., "knife")
    private String description; // Item description
    private boolean usable;     // Whether the item can be used

    /**
     * CONSTRUCTOR - Creates a new item
     * @param name Item name
     * @param description Item description
     * @param usable Whether it can be used (true/false)
     */
    public Item(String name, String description, boolean usable) {
        this.name = name;
        this.description = description;
        this.usable = usable;
    }

    // Getter methods - allow access to private attributes
    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public boolean isUsable() {
        return usable;
    }

    /**
     * toString method - returns text representation of item
     * Useful for displaying item information
     */
    @Override
    public String toString() {
        return name + ": " + description;
    }
}