public class Room {
    private String name;
    private String description;
    private Direction[] directions;

    public Room(String name, String description, Direction[] directions) {
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

    public Direction[] getDirections() {
        return directions;
    }
}
