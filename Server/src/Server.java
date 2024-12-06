import java.io.*;
import java.net.*;
import java.util.concurrent.*;

public class Server {
    private static final int PORT = 12345; // Porta del server
    // Mappa globale per memorizzare i giocatori connessi
    protected static ConcurrentHashMap<String, Socket> players = new ConcurrentHashMap<>();

    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Server in ascolto sulla porta " + PORT);

            // Ciclo infinito per accettare nuove connessioni
            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Nuovo client connesso");

                // Avvia un nuovo thread per gestire il client
                new ClientHandler(clientSocket).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

// Classe per gestire le connessioni individuali dei client
class ClientHandler extends Thread {
    private Socket clientSocket; // Socket del client
    private String username; // Nome del giocatore

    public ClientHandler(Socket socket) {
        this.clientSocket = socket; // Inizializza il socket del client
    }

    @Override
    public void run() {
        try (
                PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true); // Output al client
                BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream())) // Input dal client
        ) {
            // Richiede l'username al momento della connessione
            out.println("Inserisci il tuo username:");
            username = in.readLine();

            // Controlla se l'username è già in uso
            synchronized (Server.players) {
                if (Server.players.containsKey(username)) {
                    out.println("Errore: Username già in uso. Scegline un altro.");
                    clientSocket.close();
                    return;
                } else {
                    Server.players.put(username, clientSocket); // Aggiunge il giocatore alla mappa
                    out.println("Benvenuto " + username + "! Sei entrato nella lobby.");
                    System.out.println(username + " si è unito alla partita.");
                }
            }

            // Ciclo per gestire i comandi ricevuti dal client
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                System.out.println("Comando ricevuto da " + username + ": " + inputLine);

                // Gestisce i comandi del client
                switch (inputLine.toLowerCase()) {
                    case "prendi": // Comando per pescare una carta
                        handlePrendi(out);
                        break;

                    case "abbandona": // Comando per abbandonare la partita
                        handleAbbandona(out);
                        return; // Esci dal loop per terminare il thread

                    default: // Comando non valido
                        out.println("Comando non valido. Usa 'prendi' o 'abbandona'.");
                        break;
                }
            }
        } catch (IOException e) {
            System.out.println("Connessione chiusa con " + username);
        } finally {
            cleanup(); // Pulizia finale quando il client si disconnette
        }
    }

    // Metodo per gestire il comando "prendi"
    private void handlePrendi(PrintWriter out) {
        int carta = (int) (Math.random() * 11) + 1; // Genera una carta casuale tra 1 e 11
        out.println("Hai pescato una carta di valore: " + carta); // Invia il valore della carta al client
        System.out.println(username + " ha pescato una carta di valore " + carta); // Log sul server
    }

    // Metodo per gestire il comando "abbandona"
    private void handleAbbandona(PrintWriter out) {
        out.println("Hai abbandonato la partita. Arrivederci " + username + "!"); // Messaggio di addio
        System.out.println(username + " ha abbandonato la partita."); // Log sul server
    }

    // Metodo per operazioni di pulizia
    private void cleanup() {
        if (username != null) {
            Server.players.remove(username); // Rimuove il giocatore dalla mappa
        }
        try {
            clientSocket.close(); // Chiude il socket
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
