import java.io.*;
import java.net.*;

public class Client {
    public static void main(String[] args) throws IOException {
        // Define the server's IP address and port number
        String serverAddress = "127.0.0.1";
        int port = 12345;

        // Create a socket to connect to the server
        try (Socket socket = new Socket(serverAddress, port);
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in))
        ) {
            System.out.println("Connected to server at " + serverAddress + ":" + port);

            String userInput;
            while ((userInput = stdIn.readLine()) != null) {
                // Send user input to the server
                out.println(userInput);

                // Read response from the server and print it
                String response = in.readLine();
                System.out.println("Server response: " + response);
            }
        } catch (UnknownHostException e) {
            System.err.println("Don't know about host " + serverAddress);
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for the connection to " + serverAddress);
            System.exit(1);
        }
    }
}