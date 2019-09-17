public class Adventure {
    private Layout layout;
    private String startingRoom;
    private String endingRoom;

    public Adventure(Layout layout, String startingRoom, String endingRoom) {
        this.layout = layout;
        this.startingRoom = startingRoom;
        this.endingRoom = endingRoom;
    }

    public Layout getLayout() {
        return layout;
    }

    public String getStartingRoom() {
        return startingRoom;
    }

    public String getEndingRoom() {
        return endingRoom;
    }
}
