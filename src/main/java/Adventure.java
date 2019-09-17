import com.google.gson.Gson;

import java.io.IOException;

public class Adventure {
    public static void main(String[] args) throws IOException {
        String myJSON = ReadJSON.readFromURL("https://courses.grainger.illinois.edu/cs126/fa2019/assignments/siebel.json");
        Gson gson = new Gson();
        Layout layout = gson.fromJson(myJSON, Layout.class);

        layout.adventureOutput();
    }
}
