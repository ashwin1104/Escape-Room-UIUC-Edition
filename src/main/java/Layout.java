import java.lang.reflect.Array;
import java.util.ArrayList;

public class Layout {
    private String startingRoom;
    private String endingRoom;
    private ArrayList<Room> rooms;

    public Layout(String startingRoom, String endingRoom, ArrayList<Room> rooms) {
        this.startingRoom = startingRoom;
        this.endingRoom = endingRoom;
        this.rooms = rooms;
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
}
