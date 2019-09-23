import com.google.gson.Gson;

import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class Adventure {
    // This function reads JSON from url and returns the JSON as a String
    // Source: https://stackoverflow.com/questions/4328711/read-url-to-string-in-few-lines-of-java-code
    public static String readFromURL(String requestURL) throws IOException {
        try (Scanner scanner = new Scanner(new URL(requestURL).openStream(),
                StandardCharsets.UTF_8.toString()))
        {
            scanner.useDelimiter("\\A");
            return scanner.hasNext() ? scanner.next() : "";
        }
    }

    // This method handles user input for URL, sending out a default url (our given JSON url for this assignment)
    // If the user's url is bad or if they don't have one
    // Otherwise, returns the user's url
    public static String getURLFromUser() {
        Scanner userResponse = new Scanner(System.in);
        System.out.println("Which URL would you like to use for your Adventure game?");
        System.out.println("If you have no URL, say \"None\"");

        String url = userResponse.nextLine();
        if (url == null || url.equalsIgnoreCase("None") || !isURLValid(url)) {
            System.out.println("You either specified no URL or an incorrect URL.");
            System.out.println("Default URL will be used for your Adventure game.");
            System.out.println("");
            return "https://courses.grainger.illinois.edu/cs126/fa2019/assignments/siebel.json";
        }
        return url;
    }

    // Source: https://www.geeksforgeeks.org/check-if-url-is-valid-or-not-in-java/
    // This method checks if the given URL is valid or not
    public static boolean isURLValid(String url) {
        try {
            new URL(url).toURI();
            return true;
        }
        catch (Exception e) {
            return false;
        }
    }

    public static void main(String[] args) throws IOException {
        // Parses JSON into classes and instance fields
        String myJSON = readFromURL(getURLFromUser());
        Gson gson = new Gson();
        Layout layout = gson.fromJson(myJSON, Layout.class);

        // Begins I/O process
        layout.adventureBegin();
        layout.adventureOutput();
    }
}
