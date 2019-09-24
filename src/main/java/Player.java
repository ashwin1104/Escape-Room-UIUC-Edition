import java.util.ArrayList;

public class Player {
    private ArrayList<Item> items;

    public Player(ArrayList<Item> items) {
        this.items = items;
    }

    public ArrayList<Item> getItems() {
        return items;
    }

    public void setItems(ArrayList<Item> items) {
        this.items = items;
    }
}
