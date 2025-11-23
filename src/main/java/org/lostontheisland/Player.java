package org.lostontheisland;
import java.util.ArrayList;

/**
 * PLAYER CLASS - Represents the player
 *
 * The player has:
 * - Inventory (list of items)
 * - Water and food levels (survival)
 * - State (alive or dead)
 * - Water bottle (can be empty or full)
 */
public class Player {
    private ArrayList<Item> inventory;  // Player's inventory
    private int waterLevel;             // Water level (0-100)
    private int foodLevel;              // Food level (0-100)
    private boolean isAlive;            // Whether alive
    private boolean bottleFilled;       // Whether bottle is full

    /**
     * CONSTRUCTOR - Creates a new player
     * Starts with medium water and food levels
     */
    public Player() {
        this.inventory = new ArrayList<>();
        this.waterLevel = 50;    // Starts with 50% water
        this.foodLevel = 50;     // Starts with 50% food
        this.isAlive = true;     // Starts alive
        this.bottleFilled = false;  // Bottle starts empty
    }

    /**
     * Adds an item to inventory
     * @param item Item to add
     */
    public void addItem(Item item) {
        inventory.add(item);
    }

    /**
     * Removes an item from inventory
     * @param itemName Item name
     * @return The removed item or null
     */
    public Item removeItem(String itemName) {
        for (Item item : inventory) {
            if (item.getName().equalsIgnoreCase(itemName)) {
                inventory.remove(item);
                return item;
            }
        }
        return null;
    }

    /**
     * Checks if player has a specific item
     * @param itemName Item name
     * @return true if has it, false if not
     */
    public boolean hasItem(String itemName) {
        for (Item item : inventory) {
            if (item.getName().equalsIgnoreCase(itemName)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Searches for an item in inventory
     * @param itemName Item name
     * @return The item or null if not found
     */
    public Item getItem(String itemName) {
        for (Item item : inventory) {
            if (item.getName().equalsIgnoreCase(itemName)) {
                return item;
            }
        }
        return null;
    }

    /**
     * Drinks water - increases water level
     * @param amount Amount to increase
     */
    public void drink(int amount) {
        waterLevel += amount;
        if (waterLevel > 100) {
            waterLevel = 100; // Maximum is 100
        }
    }

    /**
     * Eats - increases food level
     * @param amount Amount to increase
     */
    public void eat(int amount) {
        foodLevel += amount;
        if (foodLevel > 100) {
            foodLevel = 100; // Maximum is 100
        }
    }

    /**
     * Decreases water and food over time
     * Called after each player action
     */
    public void decreaseStats() {
        waterLevel -= 5;  // Loses 5% water
        foodLevel -= 3;   // Loses 3% food

        // Checks if died from hunger or thirst
        if (waterLevel <= 0 || foodLevel <= 0) {
            isAlive = false;
        }
    }

    public void forceDeath () {
        waterLevel = 0;
        foodLevel = 0;
        isAlive = false;
    }

    /**
     * Fills the water bottle
     */
    public void fillBottle() {
        bottleFilled = true;
    }

    /**
     * Empties the bottle (when drinking)
     */
    public void emptyBottle() {
        bottleFilled = false;
    }

    // Getters
    public ArrayList<Item> getInventory() {
        return inventory;
    }

    public int getWaterLevel() {
        return waterLevel;
    }

    public int getFoodLevel() {
        return foodLevel;
    }

    public boolean isAlive() {
        return isAlive;
    }

    public boolean isBottleFilled() {
        return bottleFilled;
    }

    /**
     * Displays the player's inventory
     * @return String with all items
     */
    public String showInventory() {
        if (inventory.isEmpty()) {
            return "Your inventory is empty.";
        }

        StringBuilder sb = new StringBuilder();
        sb.append("\n=== Inventory ===\n");
        for (Item item : inventory) {
            sb.append("- ").append(item.getName()).append("\n");
        }
        return sb.toString();
    }

    /**
     * Displays the player's survival status
     * @return String with water and food levels
     */
    public String showStatus() {
        StringBuilder sb = new StringBuilder();
        sb.append("\n=== Status ===\n");
        sb.append("Water: ").append(waterLevel).append("%\n");
        sb.append("Food: ").append(foodLevel).append("%\n");

        // Warnings if levels are low
        if (waterLevel < 30) {
            sb.append("You are thirsty!\n");
        }
        if (foodLevel < 30) {
            sb.append("You are hungry!\n");
        }

        return sb.toString();
    }
}