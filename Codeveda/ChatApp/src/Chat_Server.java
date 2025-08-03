import java.io.*;
import java.net.*;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.Scanner; // Import Scanner for server console input

public class Chat_Server {
    private static final int PORT = 3003;
    private static Map<String, Client_Handaler> clientHandlers = new ConcurrentHashMap<>();
    private static int clientCount = 0;

    public static void main(String[] args) {
        System.out.println("Chat Server started on port " + PORT);

        //NEW: Thread for server console input ---
        new Thread(() -> {
            Scanner serverScanner = new Scanner(System.in);
            String serverInput;
            System.out.println("Type 'exit' to shut down server, or messages to broadcast as SERVER:");
            while (true) {
                serverInput = serverScanner.nextLine();
                if (serverInput.equalsIgnoreCase("exit")) {
                    System.out.println("Server shutting down...");

                    // For simplicity we'll just exit the JVM.
                    serverScanner.close();
                    System.exit(0); // Exit the application
                } else if (!serverInput.trim().isEmpty()) {

                    broadcastMessage("SERVER", serverInput);
                }
            }
        }).start();
        // --- END NEW ---

        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            while (true) {
                Socket clientSocket = serverSocket.accept();
                clientCount++;
                String clientName = "Client" + clientCount;
                System.out.println("New client connected: " + clientName + " (" + clientSocket.getInetAddress().getHostAddress() + ")");

                Client_Handaler clientHandler = new Client_Handaler(clientSocket, clientName);
                clientHandlers.put(clientName, clientHandler);
                new Thread(clientHandler).start();
            }
        } catch (IOException e) {
            System.err.println("Server error: " + e.getMessage());
            // If server socket has an error, ensure process exits if not already
            System.exit(1);
        }
    }

    public static void broadcastMessage(String senderName, String message) {
        String fullMessage = "[" + senderName + "]: " + message;
        System.out.println("Broadcasting: " + fullMessage);
        for (Client_Handaler handler : clientHandlers.values()) {
            handler.sendMessage(fullMessage);
        }
    }

    public static void removeClient(String clientName) {
        clientHandlers.remove(clientName);
        System.out.println(clientName + " has disconnected.");
        broadcastMessage("SERVER", clientName + " has left the chat.");
    }
}