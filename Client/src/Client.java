import engine.connection.ListnerHandler;
import engine.connection.Network;

import java.io.IOException;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) throws IOException {
        String serverAddress = "127.0.0.1";
        int port = 12345;

        Network net = new Network();
        int connectionStatus = net.start(serverAddress, port);

        if (connectionStatus == 0) {
            System.out.println("Connesso al server " + serverAddress + ":" + port);
        } else {
            System.err.println("Impossibile collegarsi al server.");
            return;
        }

        Thread listenerThread = new Thread(new ListnerHandler(net));
        listenerThread.start();


        try (Scanner scanner = new Scanner(System.in)) {
            while (true) {
                String userInput = scanner.nextLine();

                if (userInput.equalsIgnoreCase("quit")) {
                    break;
                }

                net.send(userInput); // Send message to the server
            }
        } catch (Exception e) {
            System.err.println("Error while reading user input: " + e.getMessage());
        } finally {
            //net.close(); // Ensure the connection is closed
        }
    }
}
