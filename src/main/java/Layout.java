import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Scanner;

public class Layout {
    private String startingRoom;
    private String endingRoom;
    private ArrayList<Room> rooms;
    private Player player;
    private String currentRoomName;
    private int currentRoomIndex;

    //-----------------------------------Constructor and Getter Methods------------------------------------------------
    public Layout() {
    }

    public String getStartingRoom() {
        return startingRoom;
    }

    public String getEndingRoom() {
        return endingRoom;
    }

    public ArrayList<Room> getRooms() {
        return rooms;
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
    public void adventureBegin() {
        if(startingRoom == null) {
            System.out.println("No starting room.");
            return;
        }
        currentRoomName = startingRoom;
        currentRoomIndex = -1;
        updateRoomIndex();
        if (currentRoomIndex == -1) {
            return;
        }
    }

    //-----------------------------------------Input/Output Methods----------------------------------------------------

    // Main function for user outputs (description, directions) based on given room
    public void adventureOutput() {
        if (currentRoomName.equals(endingRoom)) {
            System.out.println("You have reached the final room: " + endingRoom + ". Congratulations!");
            return;
        }

        String currentDesc = getRooms().get(currentRoomIndex).getDescription();
        System.out.println(currentDesc);

        if (currentRoomName.equals(startingRoom)) {
            System.out.println("Your journey begins here");
        }

        System.out.println("From here, you can go: " + getValidDirections());
        adventureInput();
    }


    // Function to take in user input
    public void adventureInput() {
        Scanner userResponse = new Scanner(System.in);
        System.out.println("Which direction would you like to go?");

        String direction = userResponse.nextLine();
        handleDirection(direction);
    }

    // Function to ensure given user input is a correct command
    // If not, then gives corresponding outputs and redirects to adventureInput()
    public void handleDirection(String direction) {
        // Null check
        if (direction == null) {
            System.out.println("No input given");
            return;
        }

        // Quit/Exit case
        if (direction.equalsIgnoreCase("exit") || direction.equalsIgnoreCase("quit")) {
                System.out.println("Bye! Thanks for playing!");
                return;
        }

        int minLengthOfGoCommand = 3;
        // Checks if go command is used correctly
        if (direction.length() >= minLengthOfGoCommand &&
                direction.substring(0,minLengthOfGoCommand).equalsIgnoreCase("go ")) {
            boolean isDirectionPossible = checkDirectionValidity(direction.substring(3));

            if (isDirectionPossible) {
                updateRoomIndex();
                adventureOutput();
                return;
            }
            // If go is not used correctly, gives corresponding output and redirects to adventureInput()
            else {
                System.out.println("I can't " + direction + "!");
                System.out.println("From here, you can go: " + getValidDirections());
                adventureInput();
            }

        }
        // When go command is not used
        else {
            System.out.println("I don't understand '" + direction + "'");
            System.out.println("From here, you can go: " + getValidDirections());
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
                break;
            }

        }
        return isDirectionPossible;
    }

    // Ouputs list of valid directions to user for current room
    public String getValidDirections() {
        String validDirections = "";
        int numDirections = getRooms().get(currentRoomIndex).getDirections().size();

        for (int directionIndex = 0; directionIndex < numDirections; directionIndex++) {

            String tempDirectionName =
                    getRooms().get(currentRoomIndex).getDirections().get(directionIndex).getDirectionName();

            if (directionIndex == numDirections - 1) {
                if (directionIndex != 0) {
                    validDirections += "or ";
                }

                validDirections += tempDirectionName;

            }
            else {
                validDirections += tempDirectionName + ", ";
            }

        }
        return validDirections;
    }
}
