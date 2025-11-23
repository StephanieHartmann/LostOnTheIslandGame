package org.lostontheisland;
/**
 * ANIMAL CLASS - Represents game animals
 *
 * Each animal has:
 * - Name (e.g., "bear", "shark", "fish")
 * - Description
 * - Danger level (0 = harmless, 10 = very dangerous)
 * - Whether it is alive or dead
 */
public class Animal {
    private String name;           // Animal name
    private String description;    // Animal description
    private int dangerLevel;       // Danger level (0-10)
    private boolean isAlive;       // Whether it's alive

    /**
     * CONSTRUCTOR - Creates a new animal
     * @param name Animal name
     * @param description Animal description
     * @param dangerLevel How dangerous it is (0-10)
     */
    public Animal(String name, String description, int dangerLevel) {
        this.name = name;
        this.description = description;
        this.dangerLevel = dangerLevel;
        this.isAlive = true; // All animals start alive
    }

    // Getters
    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int getDangerLevel() {
        return dangerLevel;
    }

    public boolean isAlive() {
        return isAlive;
    }

    /**
     * Method to "kill" the animal (e.g., when player defeats the bear)
     */
    public void kill() {
        this.isAlive = false;
    }

    @Override
    public String toString() {
        String status = isAlive ? "alive" : "dead";
        return name + " (" + status + "): " + description;
    }
}