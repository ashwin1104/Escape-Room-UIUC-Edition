public class Layout {
    private String startingRoom;
    private String endingRoom;
    private Room[] rooms;

    public Layout(String startingRoom, String endingRoom, Room[] rooms) {
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

    public Room[] getRooms() {
        return rooms;
    }
}
