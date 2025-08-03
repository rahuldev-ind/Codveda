// ChatClient1.java
import java.io.*;
import java.net.*;
import java.util.Scanner;

public class Chat_Client {
    private static final String SERVER_ADDRESS = "localhost"; // Server IP address (127.0.0.1 for local machine)
    private static final int SERVER_PORT = 3003; // Server port

    public static void main(String[] args) {
        try (Socket socket = new Socket(SERVER_ADDRESS, SERVER_PORT);
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true); // Auto-flush
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             Scanner scanner = new Scanner(System.in)) {

            System.out.println("Connected to the chat server. Type 'exit' to quit.");

            // Thread to read messages from the server
            Thread readThread = new Thread(() -> {
                try {
                    String messageFromServer;
                    while ((messageFromServer = in.readLine()) != null) {
                        System.out.println(messageFromServer);
                    }
                } catch (IOException e) {
                    System.err.println("Error reading from server: " + e.getMessage());
                } finally {
                    System.out.println("Disconnected from server.");
                    // Optionally exit the client application if server disconnects
                    System.exit(0);
                }
            });
            readThread.start(); // Start the reading thread

            // Main thread to send messages to the server
            String userInput;
            while (true) {
                userInput = scanner.nextLine(); // Read user input
                out.println(userInput); // Send it to the server
                if (userInput.equalsIgnoreCase("exit")) {
                    break; // User wants to quit
                }
            }

        } catch (UnknownHostException e) {
            System.err.println("Server not found: " + e.getMessage());
        } catch (IOException e) {
            System.err.println("I/O error connecting to server: " + e.getMessage());
        } finally {
            System.out.println("Client application shutting down.");
        }
    }
}