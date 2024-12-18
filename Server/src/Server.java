import engine.models.Player;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class Server {
    public static final int PORT = 12345;
    protected static ConcurrentHashMap<String, Socket> players = new ConcurrentHashMap<>();

    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Server in ascolto sulla porta " + PORT);

            List<Player> playerList = new ArrayList<>();

            while (playerList.size() < 2) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Nuovo client connesso");

                Player p = new Player(clientSocket);
                Thread t = new Thread(p);
                t.start();

                playerList.add(p);

            }



        } catch (IOException e) {
            System.out.println("Errore nel server: " + e.getMessage());
        }
    }
}
