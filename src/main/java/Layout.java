import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Scanner;

public class Layout {
    private String startingRoom;
    private String endingRoom;
    private ArrayList<Room> rooms;
    private String currentRoomName;
    private int currentRoomIndex;
    private boolean hasBegun = false;

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

    public void adventureOutput() {
        if (!hasBegun) {
            currentRoomName = startingRoom;
            currentRoomIndex = -1;
            hasBegun = true;
            for (int index = 0; index < getRooms().size(); index++) {
                if (getRooms().get(index).getName().equals(currentRoomName)) {
                    currentRoomIndex = index;
                }
            }
            if (currentRoomIndex == -1) {
                return;
            }
        }

        if (currentRoomName.equals(endingRoom)) {
            System.out.println("You have reached the final room: " + endingRoom + ". Congratulations!");
            return;
        }

        System.out.println(getRooms().get(currentRoomIndex).getDescription());

        if (currentRoomName == startingRoom) {
            System.out.println("Your journey begins here");
        }

        String directions = "";
        for (int index = 0; index < getRooms().get(currentRoomIndex).getDirections().size(); index++) {
            if (index + 1 == getRooms().get(currentRoomIndex).getDirections().size()) {
                if (index != 0) {
                    directions += "or ";
                }
                directions += getRooms().get(currentRoomIndex).getDirections().get(index).getDirectionName();
            }
            else {
                directions += getRooms().get(currentRoomIndex).getDirections().get(index).getDirectionName() + ", ";
            }
        }
        System.out.println("From here, you can go: " + directions);
        adventureInput();
    }


    public void adventureInput() {
        Scanner myResp = new Scanner(System.in);
        System.out.println("Which direction would you like to go?");

        String nextResp = myResp.nextLine();
        updateRoom(nextResp);
    }

    public void updateRoom(String direction) {
        if (direction.equalsIgnoreCase("exit") || direction.equalsIgnoreCase("quit")) {
            currentRoomName = startingRoom;
            currentRoomIndex = -1;
            return;
        }
        boolean isDirectionPossible = false;
        for (int index = 0; index < getRooms().get(currentRoomIndex).getDirections().size(); index++) {
            if (getRooms().get(currentRoomIndex).getDirections().get(index).getDirectionName()
                    .equalsIgnoreCase(direction)) {
                isDirectionPossible = true;
                currentRoomName = getRooms().get(currentRoomIndex).getDirections().get(index).getRoom();
                break;
            }
        }

        if (isDirectionPossible) {
            for (int index = 0; index < getRooms().size(); index++) {
                if (getRooms().get(index).getName().equals(currentRoomName)) {
                    currentRoomIndex = index;
                }
            }
            adventureOutput();
            return;
        }
        else {
            System.out.println("I can't go " + direction + "!");
            adventureInput();
        }
    }
}
