import java.io.*;
import java.net.*;

public class GameClient {
    public static void main(String[] args) throws IOException {
        Socket socket = new Socket("localhost", 8888);
        System.out.println("🔌 Connected to server!");

        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
        BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in));

        String input;
        while (true) {
            System.out.print("You: ");
            input = userInput.readLine();
            out.println(input);
            System.out.println("Server: " + in.readLine());
        }
    }
}
