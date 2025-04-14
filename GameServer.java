import java.io.*;
import java.net.*;

public class GameServer {
    public static void main(String[] args) throws IOException {
        ServerSocket server = new ServerSocket(8888);
        System.out.println("🔌 Server started, waiting...");

        Socket socket = server.accept();
        System.out.println("✅ Player connected!");

        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
        BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in));

        String line;
        while ((line = in.readLine()) != null) {
            System.out.println("Player 2: " + line);
            System.out.print("You: ");
            out.println(userInput.readLine());
        }

        socket.close();
        server.close();
    }
}
