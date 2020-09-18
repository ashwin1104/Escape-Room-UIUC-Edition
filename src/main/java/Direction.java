import java.util.ArrayList;

public class Direction {
    private String directionName;
    private String room;
    private boolean enabled;
    private ArrayList<String> validKeyNames;

    public Direction(String directionName, String room, boolean enabled, ArrayList<String> validKeyNames) {
        this.directionName = directionName;
        this.room = room;
        this.enabled = enabled;
        this.validKeyNames = validKeyNames;
    }

    // Getters and Setters

    public String getDirectionName() {
        return directionName;
    }

    public void setDirectionName(String directionName) {
        this.directionName = directionName;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public ArrayList<String> getValidKeyNames() {
        return validKeyNames;
    }

    public void setValidKeyNames(ArrayList<String> validKeyNames) {
        this.validKeyNames = validKeyNames;
    }
}
