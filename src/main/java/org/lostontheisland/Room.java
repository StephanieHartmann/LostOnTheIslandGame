package org.lostontheisland;
import java.util.HashMap;
import java.util.ArrayList;

/**
 * ROOM CLASS - Represents each location on the island
 *
 * Each room has:
 * - Name and description
 * - Exits to other rooms (north, south, east, west)
 * - List of available items
 * - List of present animals
 */
public class Room {
    private String name;                           // Room name
    private String description;                    // Room description
    private HashMap<String, Room> exits;           // Exits (direction -> room)
    private ArrayList<Item> items;                 // Items in the room
    private ArrayList<Animal> animals;             // Animals in the room

    /**
     * CONSTRUCTOR - Creates a new room
     * @param name Room name (e.g., "Beach")
     * @param description Room description
     */
    public Room(String name, String description) {
        this.name = name;
        this.description = description;
        this.exits = new HashMap<>();        // Initialize exits map
        this.items = new ArrayList<>();      // Initialize items list
        this.animals = new ArrayList<>();    // Initialize animals list
    }

    /**
     * Sets an exit to another room
     * @param direction Direction (north, south, east, west)
     * @param neighbor Neighboring room
     */
    public void setExit(String direction, Room neighbor) {
        exits.put(direction, neighbor);
    }

    /**
     * Returns the room in the specified direction
     * @param direction Desired direction
     * @return Neighboring room or null if doesn't exist
     */
    public Room getExit(String direction) {
        return exits.get(direction);
    }

    /**
     * Adds an item to the room
     */
    public void addItem(Item item) {
        items.add(item);
    }

    /**
     * Removes an item from the room (when player takes it)
     * @param itemName Item name
     * @return The removed item or null if not found
     */
    public Item removeItem(String itemName) {
        for (Item item : items) {
            if (item.getName().equalsIgnoreCase(itemName)) {
                items.remove(item);
                return item;
            }
        }
        return null;
    }

    /**
     * Adds an animal to the room
     */
    public void addAnimal(Animal animal) {
        animals.add(animal);
    }

    /**
     * Searches for an animal by name
     * @param animalName Animal name
     * @return The animal or null if not found
     */
    public Animal getAnimal(String animalName) {
        for (Animal animal : animals) {
            if (animal.getName().equalsIgnoreCase(animalName)) {
                return animal;
            }
        }
        return null;
    }

    // Getters
    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public ArrayList<Item> getItems() {
        return items;
    }

    public ArrayList<Animal> getAnimals() {
        return animals;
    }

    /**
     * Returns complete information about the room
     * Includes description, exits, items and animals
     */
    public String getFullDescription() {
        StringBuilder sb = new StringBuilder();
        sb.append("\n=== ").append(name).append(" ===\n");
        sb.append(description).append("\n");

        // Lists available exits
        if (!exits.isEmpty()) {
            sb.append("\nExits: ");
            for (String direction : exits.keySet()) {
                sb.append(direction).append(" ");
            }
            sb.append("\n");
        }

        // Lists items present
        if (!items.isEmpty()) {
            sb.append("\nItems here: ");
            for (Item item : items) {
                sb.append(item.getName()).append(" ");
            }
            sb.append("\n");
        }

        // Lists animals present
        if (!animals.isEmpty()) {
            sb.append("\nAnimals here: ");
            for (Animal animal : animals) {
                if (animal.isAlive()) {
                    sb.append(animal.getName()).append(" ");
                }
            }
            sb.append("\n");
        }

        return sb.toString();
    }
}