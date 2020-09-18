import java.util.ArrayList;

public class Room {
    private String name;
    private String description;
    private ArrayList<Direction> directions;
    private ArrayList<Item> items;

    public Room(String name, String description, ArrayList<Direction> directions, ArrayList<Item> items) {
        this.name = name;
        this.description = description;
        this.directions = directions;
        this.items = items;
    }

    // Getters and Setters

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ArrayList<Direction> getDirections() {
        return directions;
    }

    public void setDirections(ArrayList<Direction> directions) {
        this.directions = directions;
    }

    public ArrayList<Item> getItems() {
        return items;
    }

    public void setItems(ArrayList<Item> items) {
        this.items = items;
    }
}
