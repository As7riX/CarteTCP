import engine.service.CommandListener;

import java.io.*;
import java.net.*;
import java.util.concurrent.*;

public class Server {
    private static final int PORT = 12345;
    protected static ConcurrentHashMap<String, Socket> players = new ConcurrentHashMap<>();

    public static void main(String[] args) {

        Thread cli = new Thread(new CommandListener());
        cli.start();


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

class ClientHandler extends Thread {
    private Socket clientSocket;
    private String username;

    public ClientHandler(Socket socket) {
        this.clientSocket = socket;
    }

    @Override
    public void run() {
        try (
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()))
        ) {
            // Attesa attiva per sincronizzare la comunicazione
            out.println("Inserisci il tuo username:");
            clientSocket.getOutputStream().flush();

            while (true) {
                String input = in.readLine();
                if (input == null || input.trim().isEmpty()) {
                    out.println("Errore: Username non valido. Riprova.");
                } else if (Server.players.containsKey(input)) {
                    out.println("Errore: Username già in uso. Scegline un altro.");
                } else {
                    username = input;
                    break;
                }
            }

            Server.players.put(username, clientSocket);
            out.println("Benvenuto " + username + "! Sei entrato nella lobby.");
            System.out.println(username + " si è unito alla partita.");

            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                System.out.println("Comando ricevuto da " + username + ": " + inputLine);
                switch (inputLine.toLowerCase()) {
                    case "prendi":
                        handlePrendi(out);
                        break;
                    case "abbandona":
                        handleAbbandona(out);
                        return;
                    default:
                        out.println("Comando non valido. Usa 'prendi' o 'abbandona'.");
                        break;
                }
            }
        } catch (IOException e) {
            System.out.println("Connessione persa con il client.");
        } finally {
            cleanup();
        }
    }

    private void handlePrendi(PrintWriter out) {
        int carta = (int) (Math.random() * 11) + 1;
        out.println("Hai pescato una carta di valore: " + carta);
        System.out.println(username + " ha pescato una carta di valore " + carta);
    }

    private void handleAbbandona(PrintWriter out) {
        out.println("Hai abbandonato la partita. Arrivederci " + username + "!");
        System.out.println(username + " ha abbandonato la partita.");
    }

    private void cleanup() {
        if (username != null) {
            Server.players.remove(username);
        }
        try {
            clientSocket.close();
        } catch (IOException e) {
            System.out.println("Errore durante la chiusura del socket: " + e.getMessage());
        }
    }
}