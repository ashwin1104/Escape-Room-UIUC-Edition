import com.google.gson.Gson;

import java.io.IOException;

public class Adventure {
    public static void main(String[] args) throws IOException {
        // Parses JSON into classes and instance fields
        String myJSON =
                ReadJSON.readFromURL(GetURL.getURLFromUser());
        Gson gson = new Gson();
        Layout layout = gson.fromJson(myJSON, Layout.class);

        // Begins I/O process
        layout.adventureBegin();
        layout.adventureOutput();
    }
}
