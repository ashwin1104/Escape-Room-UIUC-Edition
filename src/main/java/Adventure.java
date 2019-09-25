import com.google.gson.Gson;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class Adventure {
    private static boolean isURL = false;
    private static boolean isFile = false;

    public static boolean isURL() {
        return isURL;
    }

    public static void setIsURL(boolean isURL) {
        Adventure.isURL = isURL;
    }

    public static boolean isFile() {
        return isFile;
    }

    public static void setIsFile(boolean isFile) {
        Adventure.isFile = isFile;
    }

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
    public static String getInputFromUser(String input) {
        if (input == null) {
            System.out.println("You have specified a null file path or URL.");
            System.out.println("Default file will be used for your Adventure game.");
            System.out.println("");
            return "src/adventure.json";
        }
        else if (isURLValid(input)) {
            return input;
        }
        else if (isFileValid(input)) {
            return input;
        }
        else {
            System.out.println("You either specified an incorrect URL or an incorrect file path.");
            System.out.println("Default file will be used for your Adventure game.");
            System.out.println("");
            return "src/adventure.json";
        }
    }

    // Source: https://www.geeksforgeeks.org/check-if-url-is-valid-or-not-in-java/
    // This method checks if the given URL is valid or not
    public static boolean isURLValid(String url) {
        try {
            new URL(url).toURI();
            setIsURL(true);
            return true;
        }
        catch (Exception e) {
            return false;
        }
    }

    //
    public static boolean isFileValid(String file) {
        File tmpFile = new File(file);
        if (tmpFile.exists()) {
            setIsFile(true);
            return true;
        }
        return false;
    }
    public static void main(String[] args) throws IOException {
        System.out.println("Which URL would you like to use for your Adventure game?" +
                " Or, enter the file path of a local JSON file");
        System.out.println("If you have no URL and no path, say \"None\"");
        System.out.println("");

        String myJSON;
        String input = "";
        if (args.length == 0) {
            myJSON = Data.getFileContents("src", "adventure.json");
        }
        else {
            input = getInputFromUser(args[0]);
        }

        if (isURL()) {
            System.out.println("Your url is valid and will now be used");
            myJSON =
                    readFromURL(input);
        }
        else if (isFile()) {
            System.out.println("Your file is valid and will now be used");
            String firstPath = input.substring(0,input.lastIndexOf("/"));
            String secondPath = input.substring(input.lastIndexOf("/"));
            myJSON = Data.getFileContents(firstPath, secondPath);
        }
        else {
            // read in JSON file into String variable
            myJSON = Data.getFileContents("src", "adventure.json");
        }

        Gson gson = new Gson();
        // read in String variable of JSON file into network of class declarations
        Layout layout = gson.fromJson(myJSON, Layout.class);

        // Begins I/O process
        layout.adventureBegin();
        layout.adventureOutput();
    }
}
