package org.lostontheisland;
import java.util.Scanner;


/**
 * GAME CLASS - Controls all game logic
 *
 * This is the main class that:
 * - Creates all rooms, items and animals
 * - Processes player commands
 * - Controls game flow
 * - Checks victory and defeat conditions
 */
public class Game {
    private Player player;          // The player
    private Room currentRoom;       // Current room
    private Scanner scanner;        // To read player commands
    private boolean finished;       // Whether the game ended
    private boolean hasGold;        // Whether player got the gold

    /**
     * CONSTRUCTOR - Initializes the game
     */
    public Game() {
        player = new Player();
        scanner = new Scanner(System.in);
        finished = false;
        hasGold = false;
        createRooms();  // Creates all rooms and connects them
    }

    /**
     * Creates all game rooms and their contents
     * This is the game's "map"
     */
    private void createRooms() {
        // Creating rooms
        Room sea = new Room("Sea",
                "You are floating in the sea after the storm. Sharks swim nearby!");
        Room beach = new Room("Beach",
                "A calm beach with white sand. You can see fruits on the palm trees.");
        Room jungle = new Room("Jungle",
                "A dense and dark jungle. You hear animal sounds everywhere.");
        Room cave = new Room("Cave",
                "A dark and scary cave. You feel something dangerous is here...");
        Room mountain = new Room("Mountain",
                "The top of the mountain. From here you can see the entire island!");

        // Connects the rooms (defining exits)
        // Sea -> Beach
        sea.setExit("north", beach);

        // Beach -> Sea, Jungle, Mountain
        beach.setExit("south", sea);
        beach.setExit("north", jungle);
        beach.setExit("east", mountain);

        // Jungle -> Beach, Cave
        jungle.setExit("south", beach);
        jungle.setExit("west", cave);

        // Cave -> Jungle
        cave.setExit("east", jungle);

        // Mountain -> Beach
        mountain.setExit("west", beach);

        // Adds items to rooms
        beach.addItem(new Item("fruit", "A juicy fruit", true));
        beach.addItem(new Item("bottle", "An empty bottle", true));
        jungle.addItem(new Item("woodstick", "A strong piece of wood", true));
        jungle.addItem(new Item("knife", "A sharp knife", true));
        cave.addItem(new Item("gold", "The island's treasure!", true));

        // Adds animals to rooms
        sea.addAnimal(new Animal("shark", "A dangerous shark", 8));
        cave.addAnimal(new Animal("bear", "A huge fierce bear!", 10));
        jungle.addAnimal(new Animal("fish", "A fish you can catch", 0));

        // The game starts at sea
        currentRoom = sea;
    }

    /**
     * Starts the game and shows introduction
     */
    public void start() {
        printWelcome();

        // Main game loop
        while (!finished && player.isAlive()) {
            System.out.print("\n> ");
            String input = scanner.nextLine().trim();
            processCommand(input);

            // Checks if won or lost
            checkGameStatus();
        }

        // Final message
        printGameOver();
    }

    /**
     * Displays welcome message
     */
    private void printWelcome() {
        System.out.println("\n╔════════════════════════════════════════╗");
        System.out.println("║   WELCOME TO LOST ON THE ISLAND!       ║");
        System.out.println("╚════════════════════════════════════════╝");
        System.out.println("\nYou woke up in the sea after a storm.");
        System.out.println("Survive on the island and find the treasure!");
        System.out.println("Be careful, dont forget to eat and drink.");
        System.out.println("Good Luck!!!");
        System.out.println("\nType 'help' to see the commands.");
        System.out.println(currentRoom.getFullDescription());
    }

    /**
     * Processes command typed by player
     * @param input Complete command typed
     */
    private void processCommand(String input) {
        // Divides command into words
        String[] words = input.toLowerCase().split(" ");
        String command = words[0];

        // Processes each command
        switch (command) {
            case "go":
                if (words.length > 1) {
                    goRoom(words[1]);
                    // Each action, player loses water and food
                    player.decreaseStats();

                } else {
                    System.out.println("Go where? (north, south, east, west)");
                }
                break;

            case "take":
                if (words.length > 1) {
                    takeItem(words[1]);
                    // Each action, player loses water and food
                    player.decreaseStats();
                } else {
                    System.out.println("Take what?");
                }
                break;

            case"catch":
                if (words.length > 1) {
                    catchAnimal(words[1]);
                    // Each action, player loses water and food
                    player.decreaseStats();
                } else {
                    System.out.println("Catch what?");
                }
                break;

            case "drop":
                if (words.length > 1) {
                    dropItem(words[1]);
                    // Each action, player loses water and food
                    player.decreaseStats();
                } else {
                    System.out.println("Drop what?");
                }
                break;

            case "inventory":
                System.out.println(player.showInventory());
                break;

            case "status":
                System.out.println(player.showStatus());
                break;

            case "inspect":
                System.out.println(currentRoom.getFullDescription());
                break;

            case "eat":
                if (words.length > 1) {
                    eatItem(words[1]);
                } else {
                    System.out.println("Eat what?");
                }
                break;

            case "drink":
                drinkWater();
                break;

            case "use":
                if (words.length > 1) {
                    useItem(words[1]);
                    // Each action, player loses water and food
                    player.decreaseStats();
                } else {
                    System.out.println("Use what?");
                }
                break;

            case "help":
                showHelp();
                break;

            case "quit":
                finished = true;
                System.out.println("Thanks for playing!");
                break;

            default:
                System.out.println("Command not recognized. Type 'help' for help.");
        }
    }

    /**
     * Moves player to another room
     * @param direction Direction (north, south, east, west)
     */
    private void goRoom(String direction) {
        Room nextRoom = currentRoom.getExit(direction);

        if (nextRoom == null) {
            System.out.println("You can't go that way!");
            return;
        }

        // Checks for special dangers
        if (nextRoom.getName().equals("Sea")) {
            Animal shark = nextRoom.getAnimal("shark");
            if (shark != null && shark.isAlive()) {
                System.out.println("A shark attacks you! You died!");

                player.forceDeath(); // forcing death
                return;
            }
        }

        // Checks for bear in cave
        if (nextRoom.getName().equals("Cave")) {
            Animal bear = nextRoom.getAnimal("bear");
            if (bear != null && bear.isAlive() && !player.hasItem("knife")) {
                System.out.println("A fierce bear attacks you! You need a knife!");
                System.out.println("You flee back to the jungle!");
                return;
            }
        }

        // Moves to new room
        currentRoom = nextRoom;
        System.out.println(currentRoom.getFullDescription());
    }

    /**
     * Takes an item from the room
     * @param itemName Item name
     */
    private void takeItem(String itemName) {
        Item item = currentRoom.removeItem(itemName);

        if (item == null) {
            System.out.println("That item is not here.");
            return;
        }

        // Checks if it's the gold (need to defeat bear first)
        if (itemName.equals("gold")) {
            Animal bear = currentRoom.getAnimal("bear");
            if (bear != null && bear.isAlive()) {
                System.out.println("The bear is guarding the gold! You need to defeat it first!");
                currentRoom.addItem(item); // Returns the gold
                return;
            }
            hasGold = true;
            System.out.println("YOU GOT THE GOLD! Now return to the beach to win!");
        }

        player.addItem(item);
        System.out.println("You took: " + item.getName());
    }

    /**
     * Drops an item on the ground
     * @param itemName Item name
     */
    private void dropItem(String itemName) {
        Item item = player.removeItem(itemName);

        if (item == null) {
            System.out.println("You don't have that item.");
            return;
        }

        currentRoom.addItem(item);
        System.out.println("You dropped: " + item.getName());
    }

    /**
     * Eats an item
     * @param itemName Item name
     */
    private void eatItem(String itemName) {
        if (itemName.equals("fruit") && player.hasItem("fruit")) {
            player.removeItem("fruit");
            player.eat(30);
            System.out.println("You ate the fruit. Food +30%");
        } else if (itemName.equals("fish") && player.hasItem("fish")) {
            player.removeItem("fish");
            player.eat(40);
            System.out.println("You ate the fish. Food +40%");
        } else {
            System.out.println("You can't eat that!");
        }
    }

    /**
     * Drinks water from bottle
     */
    private void drinkWater() {
        if (!player.hasItem("bottle")) {
            System.out.println("You need a bottle first!");
            return;
        }

        if (!player.isBottleFilled()) {
            System.out.println("The bottle is empty! Use 'use bottle' at the sea to fill it.");
            return;
        }

        player.drink(80);
        player.emptyBottle();
        System.out.println("You drank water. Water +80%");
    }

    /**go
     * Uses a special item
     * @param itemName Item name
     */
    private void useItem(String itemName) {
        if (itemName.equals("knife")) {
            Animal bear = currentRoom.getAnimal("bear");
            if (bear != null && bear.isAlive() && player.hasItem("knife")) {
                bear.kill();
                System.out.println("You defeated the bear with the knife!");
                System.out.println("Now you can take the gold!");
            } else {
                System.out.println("There's nothing to use the knife on here.");
            }
        } else if (itemName.equals("bottle")) {
            if (currentRoom.getName().equals("Sea") || currentRoom.getName().equals("Beach")) {
                if (player.hasItem("bottle")) {
                    player.fillBottle();
                    System.out.println("You filled the bottle with sea water!");
                } else {
                    System.out.println("You don't have the bottle!");
                }
            } else {
                System.out.println("You need to be near the sea to fill the bottle.");
            }
        } else {
            System.out.println("You can't use that item now.");
        }
    }
    /**
     * Catches an animal (like fish) and converts it to an item
     * @param animalName Animal name to catch
     */
    private void catchAnimal(String animalName) {
        // Only fish can be caught
        if (animalName.equals("fish")) {
            Animal fish = currentRoom.getAnimal("fish");

            // Check if fish exists and is alive
            if (fish != null && fish.isAlive()) {
                // "Kill" the fish (remove from room)
                fish.kill();

                // Create fish as an item and add to inventory
                Item fishItem = new Item("fish", "A fresh fish", true);
                player.addItem(fishItem);

                System.out.println("You caught the fish! You can eat it now.");
            } else {
                System.out.println("There's no fish here to catch.");
            }
        } else {
            System.out.println("You can't catch that!");
        }
    }

    /**
     * Checks game status (victory or defeat)
     */
    private void checkGameStatus() {
        // Victory: has gold and is at beach
        if (hasGold && currentRoom.getName().equals("Beach")) {
            System.out.println("\n╔════════════════════════════════════════╗");
            System.out.println("║         YOU WON THE GAME!              ║");
            System.out.println("╚════════════════════════════════════════╝");
            System.out.println("You found the gold and escaped the island!");
            finished = true;
        }

        // Defeat: died from hunger or thirst
        if (!player.isAlive()) {
            System.out.println("\n╔════════════════════════════════════════╗");
            System.out.println("║          GAME OVER                     ║");
            System.out.println("╚════════════════════════════════════════╝");
            if (player.getWaterLevel() <= 0) {
                System.out.println("You died of thirst...");
            } else if (player.getFoodLevel() <= 0) {
                System.out.println("You died of hunger...");
            }
            finished = true;
        }
    }

    /**
     * Shows all available commands
     */
    private void showHelp() {
        System.out.println("\n=== AVAILABLE COMMANDS ===");
        System.out.println("go [direction]   - Move (north, south, east, west)");
        System.out.println("take [item]      - Take an item");
        System.out.println("catch [animal]   - Catch an animal (fish)");
        System.out.println("drop [item]      - Drop an item");
        System.out.println("inventory        - View your inventory");
        System.out.println("status           - View your water and food levels");
        System.out.println("inspect          - Examine the current room");
        System.out.println("eat [item]       - Eat (fruit, fish)");
        System.out.println("drink            - Drink water from bottle");
        System.out.println("use [item]       - Use a special item");
        System.out.println("help             - Show this help");
        System.out.println("quit             - Exit the game");
    }

    /**
     * Final game message
     */
    private void printGameOver() {
        System.out.println("\nThank you for playing Lost on the Island!");
        scanner.close();
    }

    /**
     * Main method - starts the program
     */
    public static void main(String[] args) {
        Game game = new Game();
        game.start();
    }
}