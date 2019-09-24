import java.util.ArrayList;

public class Direction {
    private String directionName;
    private String room;
    private boolean enabled;
    private ArrayList<String> validKeyNames;

    public Direction(String directionName, String room) {
        this.directionName = directionName;
        this.room = room;
    }

    // Getters

    public String getDirectionName() {
        return directionName;
    }

    public String getRoom() {
        return room;
    }
}
