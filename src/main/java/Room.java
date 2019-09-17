import java.util.ArrayList;

public class Room {
    private String name;
    private String description;
    private ArrayList<Direction> directions;

    public Room(String name, String description, ArrayList<Direction> directions) {
        this.name = name;
        this.description = description;
        this.directions = directions;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public ArrayList<Direction> getDirections() {
        return directions;
    }
}
