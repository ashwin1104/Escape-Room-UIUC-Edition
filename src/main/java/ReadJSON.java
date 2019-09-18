import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class ReadJSON {
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
}
