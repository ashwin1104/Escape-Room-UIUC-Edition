import static org.junit.Assert.assertEquals;

import com.google.gson.Gson;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

public class AdventureTest {
    private Layout layout;
    private ReadJSON read = new ReadJSON();
    private Adventure adventure = new Adventure();

    @Before
    public void setUp() throws IOException {
        String myJSON = ReadJSON.readFromURL("https://courses.grainger.illinois.edu/cs126/fa2019/assignments/siebel.json");
        Gson gson = new Gson();
        layout = gson.fromJson(myJSON, Layout.class);
        layout.adventureBegin();
    }

    @Test
    public void knowsEndingRoom() throws Exception {
        assertEquals(layout.getEndingRoom(),"Siebel1314");
    }
    @Test
    public void knowsStartingRoom() throws Exception {
        assertEquals(layout.getStartingRoom(),"MatthewsStreet");
    }
    @Test
    public void knowsDescription() throws Exception {
        assertEquals(layout.getRooms().get(0).getDescription(),"You are on Matthews, outside the Siebel Center");
    }
    @Test
    public void knowsRoomName() throws Exception {
        assertEquals(layout.getRooms().get(0).getName(),"MatthewsStreet");
    }
    @Test
    public void knowsDirectionName() throws Exception {
        assertEquals(layout.getRooms().get(0).getDirections().get(0).getDirectionName(),"East");
    }
    @Test
    public void knowsDirectionRoom() throws Exception {
        assertEquals(layout.getRooms().get(0).getDirections().get(0).getRoom(),"SiebelEntry");
    }
    @Test
    public void testAdventureBeginRoomNameInitialize() {
        assertEquals(layout.getCurrentRoomName(), layout.getStartingRoom());
    }
    @Test
    public void testAdventureBeginRoomIndexInitialize() {
        assertEquals(layout.getCurrentRoomIndex(), 0);
    }
    
    /*@Test
    public void testHandleDirectionProper() {
        layout.handleDirection("East");
        assertEquals(layout.getCurrentRoomIndex(),1);
    }*/
}
