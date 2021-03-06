import com.google.gson.Gson;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;

import static org.junit.Assert.*;

public class AdventureTest {
    // Set up Layout object for parsing
    private Layout layout;

    // Parses JSON into Layout object
    @Before
    public void setUp() throws IOException{
        // read in JSON file into String variable
        String myJSON = Data.getFileContents("src", "adventure.json");
        Gson gson = new Gson();
        // read in String variable of JSON file into network of class declarations
        layout = gson.fromJson(myJSON, Layout.class);
        layout.adventureBegin();
    }

    // Series of tests to ensure correct parsing
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

    // Tests to ensure correct initial room name and index after parsing
    @Test
    public void testAdventureBeginRoomNameInitialize() {
        assertEquals(layout.getCurrentRoomName(), layout.getStartingRoom());
    }
    @Test
    public void testAdventureBeginRoomIndexInitialize() {
        assertEquals(layout.getCurrentRoomIndex(), 0);
    }

    @Test
    public void testPlayerItems() {
        assertEquals(layout.getPlayer().getItems().get(1).getName(), "bomb");
    }

    @Test
    public void knowsDirectionKeys() {
        assertEquals(layout.getRooms().get(0).getDirections().get(0).getValidKeyNames().get(0), "axe");
    }

    @Test
    public void knowsRoomItems() {
        assertEquals(layout.getRooms().get(0).getItems().get(0).getName(), "coin");
    }
    //-------------------------------------Function Testing--------------------------------------------------------

    // Series of tests to ensure user's given directions are correctly determined to be valid or invalid
    @Test
    public void testDirectionValidityGood() {
        assertTrue(layout.checkDirectionValidity("go East".substring(3)));
    }
    @Test
    public void testDirectionValidityIgnoreCaseGood() {
        assertTrue(layout.checkDirectionValidity("gO eAsT".substring(3)));
    }
    @Test
    public void testDirectionValidityBad() {
        assertFalse(layout.checkDirectionValidity("go west".substring(3)));
    }

    // Tests if the directions given to user are correct for given room
    @Test
    public void knowsValidDirections() {
        layout.setCurrentRoomName("SiebelEastHallway");
        layout.setCurrentRoomIndex(5);
        assertEquals(layout.getValidDirections(),"West, South, or Down ");
    }

    // Tests if the outputs for each case of user input correctly correspond to each case
    @Test
    public void testHandleDirectionNull() {
        // Source: https://limzhenghong.wordpress.com/2015/03/18/junit-with-system-out-println/
        OutputStream os = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(os);
        System.setOut(ps);
        layout.handleDirection(null);
        assertEquals("No input given\n", os.toString());
        PrintStream originalOut = System.out;
        System.setOut(originalOut);
    }
    @Test
    public void testHandleDirectionExit() {
        OutputStream os = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(os);
        System.setOut(ps);
        layout.handleDirection("exit");
        assertEquals("Bye! Thanks for playing!\n", os.toString());
        PrintStream originalOut = System.out;
        System.setOut(originalOut);
    }
    @Test
    public void testHandleDirectionQuit() {
        OutputStream os = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(os);
        System.setOut(ps);
        layout.handleDirection("quit");
        assertEquals("Bye! Thanks for playing!\n", os.toString());
        PrintStream originalOut = System.out;
        System.setOut(originalOut);
    }
    @Test
    public void testHandleDirectionQuitIgnoreCase() {
        OutputStream os = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(os);
        System.setOut(ps);
        layout.handleDirection("qUiT");
        assertEquals("Bye! Thanks for playing!\n", os.toString());
        PrintStream originalOut = System.out;
        System.setOut(originalOut);
    }

    // Tests case when startingRoom is null, and output correctly corresponds
    @Test
    public void testStartingRoomNull() {
        layout.setStartingRoom(null);
        OutputStream os = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(os);
        System.setOut(ps);
        layout.adventureBegin();
        assertEquals("No starting room.\n", os.toString());
        PrintStream originalOut = System.out;
        System.setOut(originalOut);
    }

    @Test
    public void testEmptyStringCommand() {
        assertEquals(layout.handleDirection(""), "empty string");
    }

    @Test
    public void testBadGoCommand() {
        assertEquals(layout.handleDirection("goal"), "incorrectly used go command");
    }

    @Test
    public void testItemAvailabilityNull() {
        assertFalse(layout.checkItemAvailability(null));
    }
    @Test
    public void testItemAvailabilityEmpty() {
        assertFalse(layout.checkItemAvailability(""));
    }
    @Test
    public void testItemDropNull() {
        assertFalse(layout.checkItemDrop(null));
    }
    @Test
    public void testItemDropEmpty() {
        assertFalse(layout.checkItemDrop(""));
    }
    @Test
    public void testItemUsabilityNull() {
        assertFalse(layout.checkItemUsability(null, null));
    }
    @Test
    public void testItemUsabilityEmpty() {
        assertFalse(layout.checkItemUsability("", ""));
    }
    @Test
    public void testListItems() {
        assertEquals(layout.getListItems(), "a coin");
    }
    @Test
    public void testInventory() {
        assertEquals(layout.getInventory(), "axe and bomb");
    }
}
