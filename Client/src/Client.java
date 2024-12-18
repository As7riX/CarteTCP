
import engine.connection.Network;

import java.io.IOException;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) throws IOException {

        /*
        Carta carta = new Carta(Carta.Semi.cuori, Carta.Valori.asso);

        Carta.PrintCard(carta, false);
        Gson gson = new Gson();

        String json = gson.toJson(carta);

        System.out.println(json);*/

        String serverAddress = "127.0.0.1";
        int port = 12345;

        Scanner stdIn = new Scanner(System.in);  // Create a Scanner object
        Network net = new Network();

        int ris = net.start(serverAddress, port);

        System.out.println("Connected to server at " + serverAddress + ":" + port);

        String userInput;

        while ((userInput = stdIn.nextLine()) != null) {

            net.send(userInput);

                // Read response from the server and print it
            String response = net.recive();
            System.out.println("Server response: " + response);
         }
    }
}