import com.google.gson.Gson;

import java.io.IOException;

public class Adventure {
    public static void main(String[] args) throws IOException {
        String myJSON =
                ReadJSON.readFromURL(GetURL.getURLFromUser());
        Gson gson = new Gson();
        Layout layout = gson.fromJson(myJSON, Layout.class);

        layout.adventureBegin();
        layout.adventureOutput();
    }
}
