import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Scanner;

public class Layout {
    private String startingRoom;
    private String endingRoom;
    private ArrayList<Room> rooms;
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

    //----------------------------------------Starting Room Index Initializer------------------------------------------
    public void adventureBegin() {
        currentRoomName = startingRoom;
        currentRoomIndex = -1;
        updateRoomIndex();
        if (currentRoomIndex == -1) {
            return;
        }
    }

    //-----------------------------------------Input/Output Methods----------------------------------------------------
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


    public void adventureInput() {
        Scanner userResponse = new Scanner(System.in);
        System.out.println("Which direction would you like to go?");

        String direction = userResponse.nextLine();
        handleDirection(direction);
    }

    public void handleDirection(String direction) {
        if (direction.equalsIgnoreCase("exit") || direction.equalsIgnoreCase("quit")) {
            System.out.println("Bye! Thanks for playing!");
            return;
        }

        boolean isDirectionPossible = checkDirectionValidity(direction);

        if (isDirectionPossible) {
            updateRoomIndex();
            adventureOutput();
            return;
        }
        else {
            System.out.println("I can't go " + direction + "!");
            System.out.println("From here, you can go: " + getValidDirections());
            adventureInput();
        }
    }

    //----------------------------Helper Functions for Input/Output Functions------------------------------------------
    public void updateRoomIndex() {
        for (int roomIndex = 0; roomIndex < getRooms().size(); roomIndex++) {
            if (getRooms().get(roomIndex).getName().equals(currentRoomName)) {
                currentRoomIndex = roomIndex;
            }
        }
    }

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
