package engine.service;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ConcurrentHashMap;

public class Server {
    private static final int PORT = 12345; 
    protected static ConcurrentHashMap<String, Socket> players = new ConcurrentHashMap<>();

    public static void main(String[] args) {
        System.out.println("Avvio del server...");
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Server in ascolto sulla porta " + PORT);

            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Nuovo client connesso: " + clientSocket.getRemoteSocketAddress());
                ClientHandler handler = new ClientHandler(clientSocket);
                handler.start();
            }

        } catch (IOException e) {
            System.err.println("Errore del server: " + e.getMessage());
        }
    }
}
