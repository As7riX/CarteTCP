package engine.connection;

import java.io.*;
import java.net.Socket;

public class ClientHandler extends Thread {
    private Socket clientSocket;
    private String username;
    private static GameManager gameManager = new GameManager();

    public ClientHandler(Socket socket) {
        this.clientSocket = socket;
    }

    @Override
    public void run() {
        try (
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true)
        ) {
            // 1) Leggi primo messaggio (ciao)
            String firstMsg = in.readLine();
            if (firstMsg == null) firstMsg = "";
            out.println("ciao quale e il tuo username?");

            // 2) Leggi username
            while (true) {
                String input = in.readLine();
                if (input == null || input.trim().isEmpty()) {
                    out.println("Errore: Username non valido. Riprova:");
                } else if (Server.players.containsKey(input)) {
                    out.println("Errore: Username già in uso. Riprova:");
                } else {
                    username = input;
                    break;
                }
            }

            Server.players.put(username, clientSocket);
            out.println("Benvenuto " + username + "! Sei entrato nella lobby.");
            gameManager.aggiungiGiocatore(username, clientSocket);

            // Aspetta che la partita inizi
            while (!gameManager.partitaIniziata()) {
                // Attende la partita (nessun sleep necessario, si aspetta input)
                // Non fare niente fino a quando la partita non inizia (il manager manda i messaggi)
                Thread.sleep(100);
            }

            // Ora la partita è iniziata, aspettiamo i comandi
            String comando;
            while ((comando = in.readLine()) != null) {
                comando = comando.trim().toLowerCase();
                if (comando.equals("prendi")) {
                    gameManager.comandoPrendi(username);
                } else if (comando.equals("stai")) {
                    gameManager.comandoStai(username);
                } else if (comando.equals("abbandona")) {
                    gameManager.comandoAbbandona(username);
                    return;
                } else {
                    out.println("Comando non valido. Usa 'prendi', 'stai' o 'abbandona'.");
                }
            }

        } catch (IOException | InterruptedException e) {
            System.out.println("Connessione persa con " + username);
        } finally {
            cleanup();
        }
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
