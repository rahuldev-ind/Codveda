// ChatClient2.java
import java.io.*;
import java.net.*;
import java.util.Scanner;

public class Chat_Client2 {
    private static final String SERVER_ADDRESS = "localhost"; //SERVER IP ADDRESS(127.0.0.1 For local Machine)
    private static final int SERVER_PORT = 3003; //SERVER PORT

    public static void main(String[] args) {
        try (Socket socket = new Socket(SERVER_ADDRESS, SERVER_PORT);
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             Scanner scanner = new Scanner(System.in)) {

            System.out.println("Connected to the chat server. Type 'exit' to exit.");


            // THREAD TO READ MESSAGE FROM SERVER
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
                    System.exit(0);
                }
            });
            readThread.start(); //START READING THREAD

            //  MAIN THREAD TO SEND MESSAGES TO SERVER
            String userInput;
            while (true) {
                userInput = scanner.nextLine(); //READ USER INPUT
                out.println(userInput); // Send it to the server
                if (userInput.equalsIgnoreCase("exit")) {
                    break; //USER WANT TO QUIT
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