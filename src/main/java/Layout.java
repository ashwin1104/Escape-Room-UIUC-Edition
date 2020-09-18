import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Scanner;

public class Layout {
    private String startingRoom;
    private String endingRoom;
    private ArrayList<Room> rooms;
    private Player player;
    private String currentRoomName;
    private Item currentItem;
    private int currentRoomIndex;
    private int currentDirectionIndex;
    private boolean isGivenDirectionAvailable;
    private int countMoves;
    private long startTime;
    private long endTime;
    private boolean passedPuzzleOne = false;


    public Layout() {
    }
    
    //-----------------------------------Getter Methods------------------------------------------------
    
    public String getStartingRoom() {
        return startingRoom;
    }

    public String getEndingRoom() {
        return endingRoom;
    }

    public void setEndingRoom(String endingRoom) {
        this.endingRoom = endingRoom;
    }

    public ArrayList<Room> getRooms() {
        return rooms;
    }

    public void setRooms(ArrayList<Room> rooms) {
        this.rooms = rooms;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public String getCurrentRoomName() {
        return currentRoomName;
    }

    public int getCurrentRoomIndex() {
        return currentRoomIndex;
    }

    public int getCurrentDirectionIndex() {
        return currentDirectionIndex;
    }

    public int getCountMoves() {
        return countMoves;
    }

    public Item getCurrentItem() {
        return currentItem;
    }
    
    //-----------------------------------Setter Methods for Testing----------------------------------------------------
    
    public void setCurrentRoomName(String currentRoomName) {
        this.currentRoomName = currentRoomName;
    }

    public void setCurrentRoomIndex(int currentRoomIndex) {
        this.currentRoomIndex = currentRoomIndex;
    }
    public void setStartingRoom(String room) {
        startingRoom = room;
    }

    //----------------------------------------Starting Room Index Initializer------------------------------------------

    // Initializes currentRoomName and currentRoomIndex based on startingRoom
    public String adventureBegin() {
        startTime = System.nanoTime();
        if(startingRoom == null) {
            System.out.println("No starting room.");
            return "No starting room";
        }
        currentRoomName = startingRoom;
        currentRoomIndex = -1;
        updateRoomIndex();
        if (currentRoomIndex == -1) {
            return "startingRoom is not a room";
        }
        return "Adventure has begun with flying colors";
    }

    //-----------------------------------------Input/Output Methods----------------------------------------------------

    // Main function for user outputs (description, directions) based on given room
    public String adventureOutput() {
        if (currentRoomName.equals(endingRoom)) {
            System.out.println("You have reached the final room: " + endingRoom + ". Congratulations!");
            endTime = System.nanoTime();
            long conversionToMS = 1000000;
            long conversionToSeconds = 1000;
            long duration = ((endTime - startTime)/conversionToMS)/conversionToSeconds;
            System.out.println("You took " + duration + " seconds and " + countMoves + " moves to finish the game.");
            return "final room reached";
        }

        String currentDesc = getRooms().get(currentRoomIndex).getDescription();
        System.out.println(currentDesc);
        System.out.println("You can see " + getListItems() + " here.");

        if (currentRoomName.equals(startingRoom)) {
            System.out.println("Your journey begins here");
        }

        System.out.println("From here, you can go: " + getValidDirections());
        System.out.println("You currently hold the following items: " + getInventory());
        adventureInput();
        return "adventureOutput final return statement";
    }


    // Function to take in user input
    public void adventureInput() {
        Scanner userResponse = new Scanner(System.in);
        System.out.println("Which item would you like to pick up or use? Or, which direction would you like to go?");

        String direction = userResponse.nextLine();
        countMoves++;
        handleDirection(direction);
    }

    // Function to ensure given user input is a correct command
    // If not, then gives corresponding outputs and redirects to adventureInput()
    public String handleDirection(String command) {
        // Null check
        if (command == null) {
            System.out.println("No input given");
            return "null input";
        }
        else if (command.length() == 0) {
            return "empty string";
        }
        else if (command.contains("go") && !command.contains("go ")) {
            return "incorrectly used go command";
        }
        // Quit/Exit case
        if (command.equalsIgnoreCase("exit") || command.equalsIgnoreCase("quit")) {
                System.out.println("Bye! Thanks for playing!");
                return "player has quit game";
        }

        int minLengthOfPickup = 7;
        int minLengthOfDrop = 5;
        int minLengthOfUse = 4;
        int minLengthOfGo = 3;

        if (command.equalsIgnoreCase("examine")) {
            adventureOutput();
        }
        else if (command.length() >= minLengthOfDrop &&
                command.substring(0, minLengthOfDrop).equalsIgnoreCase("drop ")) {
            String givenItem = command.substring(minLengthOfDrop).trim();
            boolean isItemDroppable = checkItemDrop(givenItem);

            if (isItemDroppable) {
                reverseUpdatePlayerAndAvailableItems();
                adventureOutput();
            }
            else {
                System.out.println("I can't " + command + "!");
                System.out.println("From here, you can go: " + getValidDirections());
                System.out.println("You can see " + getListItems() + " here.");
                System.out.println("You currently hold the following items: " + getInventory());
                adventureInput();
            }
        }
        // Checks if pickup command is used correctly
        else if (command.length() >= minLengthOfPickup &&
                command.substring(0, minLengthOfPickup).equalsIgnoreCase("pickup ")) {
            String givenItem = command.substring(minLengthOfPickup).trim();
            boolean isItemAvailable = checkItemAvailability(givenItem);

            if (isItemAvailable) {
                updatePlayerAndAvailableItems();
                adventureOutput();
            }
            else {
                System.out.println("I can't " + command + "!");
                System.out.println("From here, you can go: " + getValidDirections());
                System.out.println("You can see " + getListItems() + " here.");
                System.out.println("You currently hold the following items: " + getInventory());
                adventureInput();
            }
        }
        // Checks if use command is used correctly
        else if (command.length() >= minLengthOfUse &&
                command.substring(0,minLengthOfUse).equalsIgnoreCase("use ") &&
                command.contains(" with ")) {
            String givenItemWithDirection = command.substring(minLengthOfUse).trim();
            String[] item_direction = givenItemWithDirection.split(" with ");
            String givenItem = item_direction[0];
            String givenDirection = item_direction[1];
            boolean isItemUsable = checkItemUsability(givenItem, givenDirection);
            if (getRooms().get(currentRoomIndex).getDirections().get(currentDirectionIndex).isEnabled()) {
                System.out.println("This direction is already enabled or the direction is invalid.");
                adventureOutput();
            }
            else if (isItemUsable) {
                enableGivenDirection();
                System.out.println("You have successfully enabled the " + givenDirection + " direction");
                adventureOutput();
            }
            // If go is not used correctly, gives corresponding output and redirects to adventureInput()
            else {
                System.out.println("I can't " + command + "!");
                System.out.println("From here, you can go: " + getValidDirections());
                System.out.println("You can see " + getListItems() + " here.");
                System.out.println("You currently hold the following items: " + getInventory());
                adventureInput();
            }

        }
        // Checks if go command is used correctly
        else if (command.length() >= minLengthOfGo &&
                command.substring(0,minLengthOfGo).equalsIgnoreCase("go ")) {
            String givenDirection = command.substring(minLengthOfGo).trim();
            boolean isDirectionPossible = checkDirectionValidity(givenDirection);

            if (isDirectionPossible) {
                if (isGivenDirectionAvailable) {
                    if (givenDirection.equalsIgnoreCase("Northeast")) {
                        if (!passedPuzzleOne) {
                            passesPuzzleOne();
                            enableGivenDirection();
                        }
                    }
                    updateRoomIndex();
                    adventureOutput();
                }
                else {
                    System.out.println("The direction '" + givenDirection + "' is disabled.");
                    System.out.println("To enable it, use a valid item from your inventory.");
                    System.out.println("From here, you can go: " + getValidDirections());
                    System.out.println("You can see " + getListItems() + " here.");
                    System.out.println("You currently hold the following items: " + getInventory());
                    adventureInput();
                }
            }
            // If go is not used correctly, gives corresponding output and redirects to adventureInput()
            else {
                System.out.println("I can't " + command + "!");
                System.out.println("From here, you can go: " + getValidDirections());
                System.out.println("You can see " + getListItems() + " here.");
                adventureInput();
            }

        }
        // When go command is not used
        else {
            System.out.println("I don't understand '" + command + "'");
            System.out.println("From here, you can go: " + getValidDirections());
            System.out.println("You can see " + getListItems() + " here.");
            adventureInput();
        }
        return "handleDirection final return statement";
    }

    //----------------------------Helper Functions for Input/Output Functions------------------------------------------
    // updates the currentRoomIndex when user's direction is valid
    public void updateRoomIndex() {
        for (int roomIndex = 0; roomIndex < getRooms().size(); roomIndex++) {
            if (getRooms().get(roomIndex).getName().equals(currentRoomName)) {
                currentRoomIndex = roomIndex;
            }
        }
    }

    // updates the Player's items and currentRoom's available items when user's pickup command is valid
    public void updatePlayerAndAvailableItems() {
        getPlayer().getItems().add(currentItem);
        getRooms().get(currentRoomIndex).getItems().remove(currentItem);
    }

    // updates player and room items when item is dropped validly
    public void reverseUpdatePlayerAndAvailableItems() {
        getPlayer().getItems().remove(currentItem);
        getRooms().get(currentRoomIndex).getItems().add(currentItem);
    }

    // enables the given direction from user input
    public void enableGivenDirection() {
        getRooms().get(currentRoomIndex).getDirections().get(currentDirectionIndex).setEnabled(true);
    }

    // Function for determining if user's direction input is valid for currentRoom
    public boolean checkDirectionValidity(String direction) {
        boolean isDirectionPossible = false;
        int numDirections = getRooms().get(currentRoomIndex).getDirections().size();

        for (int directionIndex = 0; directionIndex < numDirections; directionIndex++) {

            String tempDirect = getRooms().get(currentRoomIndex).getDirections().get(directionIndex).getDirectionName();
            String tempRoomName = getRooms().get(currentRoomIndex).getDirections().get(directionIndex).getRoom();

            if (tempDirect.equalsIgnoreCase(direction)) {
                isDirectionPossible = true;
                currentDirectionIndex = directionIndex;
                currentRoomName = tempRoomName;
                isGivenDirectionAvailable =
                        getRooms().get(currentRoomIndex).getDirections().get(directionIndex)
                                .isEnabled();
                break;
            }

        }
        return isDirectionPossible;
    }

    // Function for determining if user's direction input is valid for currentRoom
    public boolean checkItemAvailability(String givenItem) {
        if (givenItem == null) {
            return false;
        }
        else if (givenItem.length() == 0) {
            return false;
        }

        boolean isItemAvailable = false;
        int numItems = getRooms().get(currentRoomIndex).getItems().size();

        for (int itemIndex = 0; itemIndex < numItems; itemIndex++) {

            String tempItemName = getRooms().get(currentRoomIndex).getItems().get(itemIndex).getName();

            if (tempItemName.equalsIgnoreCase(givenItem)) {
                isItemAvailable = true;
                currentItem = getRooms().get(currentRoomIndex).getItems().get(itemIndex);
                break;
            }

        }
        return isItemAvailable;
    }

    // checks if item can be used successfully for the given direction
    public boolean checkItemUsability(String givenItem, String givenDirection) {
        if (givenItem == null || givenDirection == null) {
            return false;
        }
        else if (givenItem.length() == 0 || givenDirection.length() == 0) {
            return false;
        }
        if (checkDirectionValidity(givenDirection)) {
            int numItemsForPlayer = getPlayer().getItems().size();
            for (int itemIndex = 0; itemIndex < numItemsForPlayer; itemIndex++) {
                String tempItemName = getPlayer().getItems().get(itemIndex).getName();
                if (tempItemName.equalsIgnoreCase(givenItem)) {
                    if (getRooms().get(currentRoomIndex).getDirections()
                            .get(currentDirectionIndex).getValidKeyNames().contains(givenItem)) {
                        currentItem = getPlayer().getItems().get(itemIndex);
                        getPlayer().getItems().remove(currentItem);
                        return true;
                    }
                    return false;
                }
            }
            return false;
        }
        return false;
    }

    // checks if item can successfully be dropped into the room
    public boolean checkItemDrop(String givenItem) {
        if (givenItem == null) {
            return false;
        }
        else if (givenItem.length() == 0) {
            return false;
        }
        boolean isItemDroppable = false;
        int numItems = getPlayer().getItems().size();

        for (int itemIndex = 0; itemIndex < numItems; itemIndex++) {

            String tempItemName = getPlayer().getItems().get(itemIndex).getName();

            if (tempItemName.equalsIgnoreCase(givenItem)) {
                isItemDroppable = true;
                currentItem = getPlayer().getItems().get(itemIndex);
                break;
            }

        }
        return isItemDroppable;
    }

    // Outputs list of valid directions to user for current room
    public String getValidDirections() {
        String validDirections = "";
        int numDirections = getRooms().get(currentRoomIndex).getDirections().size();

        for (int directionIndex = 0; directionIndex < numDirections; directionIndex++) {

            String tempDirectionName =
                    getRooms().get(currentRoomIndex).getDirections().get(directionIndex).getDirectionName();

            if (directionIndex == numDirections - 1) {
                if (directionIndex == 1 && numDirections != 2) {
                    validDirections = validDirections.substring(0,validDirections.length() - 1);
                }
                if (directionIndex != 0) {
                    validDirections += "or ";
                }

                validDirections += tempDirectionName + " ";

            }
            else {
                if (directionIndex != 0 || numDirections != 2) {
                    validDirections += tempDirectionName + ", ";
                }
                else {
                    validDirections += tempDirectionName + " ";
                }
            }

        }
        return validDirections;
    }

    // Outputs list of items the user may pick up from the given room
    public String getListItems() {
        String listItems = "a ";
        int numItems = getRooms().get(currentRoomIndex).getItems().size();
        if (numItems == 0) {
            return "no items";
        }
        for (int itemIndex = 0; itemIndex < numItems; itemIndex++) {

            String tempItemName =
                    getRooms().get(currentRoomIndex).getItems().get(itemIndex).getName();

            if (itemIndex == numItems - 1) {
                if (itemIndex != 0) {
                    listItems += "and ";
                }

                listItems += tempItemName;

            }
            else {
                if (itemIndex != 0 || numItems != 2) {
                    listItems += tempItemName + ", ";

                }
                else {
                    listItems += tempItemName + " ";
                }
            }
        }
        return listItems;
    }

    // returns formatted list of player's item names
    public String getInventory() {
        String listItems = "";
        int numItems = getPlayer().getItems().size();
        if (numItems == 0) {
            return "no items";
        }
        for (int itemIndex = 0; itemIndex < numItems; itemIndex++) {

            String tempItemName =
                    getPlayer().getItems().get(itemIndex).getName();

            if (itemIndex == numItems - 1) {
                if (itemIndex != 0) {
                    listItems += "and ";
                }

                listItems += tempItemName;

            }
            else {
                if (itemIndex != 0 || numItems != 2) {
                    listItems += tempItemName + ", ";

                }
                else {
                    listItems += tempItemName + " ";
                }
            }
        }
        return listItems;
    }

    // method for outputting puzzle for the northeast direction to user
    public void passesPuzzleOne() {
        System.out.println("Solve the following equation for x: x - 6 = 21");
        System.out.print("x = ");
        Scanner userResponse = new Scanner(System.in);
        String answer = userResponse.nextLine();
        if (answer.equals("27")) {
            System.out.println("You have passed the challenge and travel northeast successfully.");
            passedPuzzleOne = true;
        }
        else {
            System.out.println("Puzzle failed. Direction remains disabled");
            passedPuzzleOne = false;
            adventureOutput();
        }
    }
}
