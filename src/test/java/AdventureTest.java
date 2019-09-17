import static org.junit.Assert.assertEquals;

import com.google.gson.Gson;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

public class AdventureTest {
    private Adventure adventure;
    @Before
    public void setUp() throws IOException {
        String myJSON = Adventure.readFromURL("https://courses.grainger.illinois.edu/cs126/fa2019/assignments/siebel.json");
        //String myJSON = Data.getFileContents("src", "siebel.json");
        Gson gson = new Gson();
        adventure = gson.fromJson(myJSON, Adventure.class);
    }
    @Test
    public void knowsEndingRoom() throws Exception {
        assertEquals(adventure.getEndingRoom(),"Siebel1314");
    }
    @Test
    public void knowsStartingRoom() throws Exception {
        assertEquals(adventure.getLayout().getStartingRoom(),"MatthewsStreet");
    }
    @Test
    public void knowsDescription() throws Exception {
        assertEquals(adventure.getLayout().getRooms().get(0).getDescription(),"You are on Matthews, outside the Siebel Center");
    }
    @Test
    public void knowsRoomName() throws Exception {
        assertEquals(adventure.getLayout().getRooms().get(0).getName(),"MatthewsStreet");
    }
    @Test
    public void knowsDirectionName() throws Exception {
        assertEquals(adventure.getLayout().getRooms().get(0).getDirections().get(0).getDirectionName(),"East");
    }
    @Test
    public void knowsDirectionRoom() throws Exception {
        assertEquals(adventure.getLayout().getRooms().get(0).getDirections().get(0).getRoom(),"SiebelEntry");
    }

}
