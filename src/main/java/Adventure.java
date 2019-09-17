import com.google.gson.Gson;

import java.net.*;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class Adventure {
    private Layout layout;
    private String startingRoom;
    private String endingRoom;

    public Adventure(Layout layout) {
        this.layout = layout;
    }

    public Adventure() {

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

    public static String readFromURL(String requestURL) throws IOException {
        try (Scanner scanner = new Scanner(new URL(requestURL).openStream(),
                StandardCharsets.UTF_8.toString()))
        {
            scanner.useDelimiter("\\A");
            return scanner.hasNext() ? scanner.next() : "";
        }
    }
}