import engine.connection.Network;
import engine.service.CommandListener;

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

        Thread cl = new Thread(new CommandListener());
        cl.start();

        /*Network net = new Network();

        int ris = net.start(serverAddress, port);

            System.out.println("Connected to server at " + serverAddress + ":" + port);


                // Read response from the server and print it
            String response = net.recive();
                System.out.println("Server response: " + response);*/

    }
}