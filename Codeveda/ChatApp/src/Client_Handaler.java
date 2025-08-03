// ClientHandler.java
import java.io.*;
import java.net.*;

public class Client_Handaler implements Runnable {
    private Socket clientSocket;
    private PrintWriter out;
    private BufferedReader in;
    private String clientName;

    public Client_Handaler(Socket socket, String clientName) {
        this.clientSocket = socket;
        this.clientName = clientName;
        try {
            out = new PrintWriter(clientSocket.getOutputStream(), true); // Auto-flush
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        } catch (IOException e) {
            System.err.println("Error setting up client streams for " + clientName + ": " + e.getMessage());
        }
    }

    // Method to send a message to this specific client
    public void sendMessage(String message) {
        out.println(message);
    }

    @Override
    public void run() {
        try {
            // Inform the client about their name
            sendMessage("Welcome to the chat, " + clientName + "!");
            // Inform all other clients that a new user joined
            Chat_Server.broadcastMessage("SERVER", clientName + " has joined the chat.");

            String messageFromClient;
            while ((messageFromClient = in.readLine()) != null) {
                if (messageFromClient.equalsIgnoreCase("exit")) {
                    break; // Client wants to disconnect
                }
                // Broadcast the message to all other clients
                Chat_Server.broadcastMessage(clientName, messageFromClient);
            }
        } catch (IOException e) {
            // Client might have disconnected abruptly or there was a read error
            // System.err.println("Error reading from client " + clientName + ": " + e.getMessage());
        } finally {
            // Clean up resources when the client disconnects
            try {
                clientSocket.close();
                in.close();
                out.close();
            } catch (IOException e) {
                System.err.println("Error closing resources for " + clientName + ": " + e.getMessage());
            }
            Chat_Server.removeClient(clientName); // Remove client from server's list
        }
    }
}