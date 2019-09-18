import java.net.URL;
import java.util.Scanner;

public class GetURL {

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
}
