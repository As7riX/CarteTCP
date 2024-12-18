package engine.connection;

import com.google.gson.Gson;
import engine.models.Carta;

import java.io.IOException;

public class ListnerHandler implements Runnable {
    private final Network net;
    private final Gson gson;

    public ListnerHandler(Network net) {
        this.net = net;
        this.gson = new Gson();
    }

    @Override
    public void run() {
        try {

            while (true) {
                String response = net.recive();
                if (response == null) {
                    System.out.println("Server disconnected.");
                    break;
                }

                try {
                    Carta carta = gson.fromJson(response, Carta.class);
                    if (carta != null && carta.getSeme() != null && carta.getValore() != null) {

                        Carta.PrintCard(carta, false);
                    } else {
                        throw new IllegalArgumentException();
                    }
                } catch (Exception e) {
                    System.out.println(response);
                }
            }
        } catch (IOException e) {
            System.err.println("Error while receiving server message: " + e.getMessage());
        }
    }
}
