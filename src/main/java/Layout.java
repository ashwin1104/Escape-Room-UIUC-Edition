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
    private boolean isGivenDirectionAvailable;

    //-----------------------------------Constructor and Getter Methods------------------------------------------------

    public Layout() {
    }

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
    }

    //-----------------------------------------Input/Output Methods----------------------------------------------------

    // Main function for user outputs (description, directions) based on given room
    public String adventureOutput() {
        if (currentRoomName.equals(endingRoom)) {
            System.out.println("You have reached the final room: " + endingRoom + ". Congratulations!");
            return "final room reached";
        }

        String currentDesc = getRooms().get(currentRoomIndex).getDescription();
        System.out.println(currentDesc);
        System.out.println("You can see a " + getListItems() + " here.");

        if (currentRoomName.equals(startingRoom)) {
            System.out.println("Your journey begins here");
        }

        System.out.println("From here, you can go: " + getValidDirections());
        adventureInput();
    }


    // Function to take in user input
    public void adventureInput() {
        Scanner userResponse = new Scanner(System.in);
        System.out.println("Which item would you like to pick up or use? Or, which direction would you like to go?");

        String direction = userResponse.nextLine();
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

        // Quit/Exit case
        if (command.equalsIgnoreCase("exit") || command.equalsIgnoreCase("quit")) {
                System.out.println("Bye! Thanks for playing!");
                return "player has quit game";
        }

        int minLengthOfPickup = 7;
        int minLengthOfUse = 4;
        int minLengthOfGo = 3;
        // Checks if pickup command is used correctly
        if (command.length() >= minLengthOfPickup &&
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
                System.out.println("You can see a " + getListItems() + " here.");
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

            if (isItemUsable) {
                System.out.println("You have successfully enabled the " + givenDirection + " direction");
                adventureOutput();
            }
            // If go is not used correctly, gives corresponding output and redirects to adventureInput()
            else {
                System.out.println("I can't " + command + "!");
                System.out.println("From here, you can go: " + getValidDirections());
                System.out.println("You can see a " + getListItems() + " here.");
                adventureInput();
            }

        }
        // Checks if go command is used correctly
        else if (command.length() >= minLengthOfGo &&
                command.substring(0,minLengthOfGo).equalsIgnoreCase("go ")) {
            String givenDirection = command.substring(minLengthOfGo).trim();
            boolean isDirectionPossible = checkDirectionValidity(givenDirection));

            if (isDirectionPossible) {
                if (isGivenDirectionAvailable) {
                    updateRoomIndex();
                    adventureOutput();
                }
                else {
                    System.out.println("The direction '" + givenDirection + "' is disabled.");
                    System.out.println("To enable it, use a valid item from your inventory.");
                    System.out.println("From here, you can go: " + getValidDirections());
                    System.out.println("You can see a " + getListItems() + " here.");
                    adventureInput();
                }
            }
            // If go is not used correctly, gives corresponding output and redirects to adventureInput()
            else {
                System.out.println("I can't " + command + "!");
                System.out.println("From here, you can go: " + getValidDirections());
                System.out.println("You can see a " + getListItems() + " here.");
                adventureInput();
            }

        }
        // When go command is not used
        else {
            System.out.println("I don't understand '" + command + "'");
            System.out.println("From here, you can go: " + getValidDirections());
            System.out.println("You can see a " + getListItems() + " here.");
            adventureInput();
        }
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

    // Function for determining if user's direction input is valid for currentRoom
    public boolean checkDirectionValidity(String direction) {
        boolean isDirectionPossible = false;
        int numDirections = getRooms().get(currentRoomIndex).getDirections().size();

        for (int directionIndex = 0; directionIndex < numDirections; directionIndex++) {

            String tempDirect = getRooms().get(currentRoomIndex).getDirections().get(directionIndex).getDirectionName();
            String tempRoomName = getRooms().get(currentRoomIndex).getDirections().get(directionIndex).getRoom();

            if (tempDirect.equalsIgnoreCase(direction)) {
                isDirectionPossible = true;
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

    // Ouputs list of valid directions to user for current room
    public String getValidDirections() {
        String validDirections = "";
        int numDirections = getRooms().get(currentRoomIndex).getDirections().size();

        for (int directionIndex = 0; directionIndex < numDirections; directionIndex++) {

            String tempDirectionName =
                    getRooms().get(currentRoomIndex).getDirections().get(directionIndex).getDirectionName();

            if (directionIndex == numDirections - 1) {
                if (directionIndex == 1) {
                    validDirections = validDirections.substring(0,validDirections.length() - 1);
                }
                if (directionIndex != 0) {
                    validDirections += "or ";
                }

                validDirections += tempDirectionName;

            }
            else {
                if (directionIndex != 0 || numDirections != 2) {
                    validDirections += tempDirectionName + ", ";
                }
            }

        }
        return validDirections;
    }

    // Ouputs list of items the user may pick up from the given room
    public String getListItems() {
        String listItems = "";
        int numItems = getRooms().get(currentRoomIndex).getItems().size();

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
            }
        }
        return listItems;
    }
}
