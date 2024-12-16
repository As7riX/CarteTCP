package engine.connection;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ConcurrentHashMap;

public class Server {
    public static final int PORT = 12345;
    protected static ConcurrentHashMap<String, Socket> players = new ConcurrentHashMap<>();

    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Server in ascolto sulla porta " + PORT);

            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Nuovo client connesso");
                new ClientHandler(clientSocket).start();
            }

        } catch (IOException e) {
            System.out.println("Errore nel server: " + e.getMessage());
        }
    }
}
