import engine.connection.ListnerHandler;
import engine.connection.Network;

import java.util.Scanner;

public class Client {
    public static void main(String[] args){
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

                if (userInput.equalsIgnoreCase("exit")) {
                    break;
                }else if (userInput.equalsIgnoreCase("join")) {
                    if (!net.status()) net.start(serverAddress, port);
                    else System.out.println("Sei gia connesso al server.");
                }else if (userInput.equalsIgnoreCase("quit")) {
                    net.stop();
                    System.out.println("Disconnesso dal server.");
                }

                net.send(userInput); // Send message to the server
            }
        } catch (Exception e) {
            System.err.println("Error while reading user input: " + e.getMessage());
        }
    }
}
