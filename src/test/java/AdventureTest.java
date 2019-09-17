import static org.junit.Assert.assertEquals;

import com.google.gson.Gson;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

public class AdventureTest {
    private Adventure adventure;

    @Before
    String myJSON = readFromURL("https://courses.grainger.illinois.edu/cs126/fa2019/assignments/siebel.json");
    Gson gson = new Gson();
    adventure = gson.fromJSON(myJSON, Adventure.class);

    @Test
    public void canReadFileFromFolder() throws Exception {
        String testFileContents = Data
                .getFileContents("src", "test", "test_resources", "test_file_contents.txt");
        assertEquals("Gone, reduced to atoms", testFileContents);
    }
}
