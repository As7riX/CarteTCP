import connection.Network;

import java.io.*;

public class Client {
    public static void main(String[] args) throws IOException {
        // Define the server's IP address and port number
        String serverAddress = "127.0.0.1";
        int port = 12345;

        BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));

        Network net = new Network();

        int ris = net.start(serverAddress, port);

            System.out.println("Connected to server at " + serverAddress + ":" + port);

            String userInput;
            while ((userInput = stdIn.readLine()) != null) {
                // Send user input to the server
            net.send(userInput);

                // Read response from the server and print it
            String response = net.recive();
                System.out.println("Server response: " + response);
            }
    }
}