package engine.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientHandler extends Thread {
    private Socket clientSocket;
    private String username;

    public ClientHandler(Socket socket) {
        this.clientSocket = socket;
        System.out.println("ClientHandler creato per " + socket.getRemoteSocketAddress());
    }

    @Override
    public void run() {
        System.out.println("ClientHandler avviato per " + clientSocket.getRemoteSocketAddress());
        try (
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()))
        ) {
            // Richiesta username
            out.println("Inserisci il tuo username:");
            out.flush();
            System.out.println("Richiesta username inviata a " + clientSocket.getRemoteSocketAddress());

            String input;
            while (true) {
                input = in.readLine();
                if (input == null) {
                    // Il client ha chiuso la connessione prima di inserire l'username
                    System.out.println("Client disconnesso prima di scegliere username.");
                    return;
                } else if (input.trim().isEmpty()) {
                    out.println("Errore: Username non valido. Riprova.");
                    out.flush();
                } else if (Server.players.containsKey(input)) {
                    out.println("Errore: Username già in uso. Scegline un altro.");
                    out.flush();
                } else {
                    username = input;
                    break;
                }
            }

            Server.players.put(username, clientSocket);
            out.println("Benvenuto " + username + "! Sei entrato nella lobby.");
            out.flush();
            System.out.println(username + " si è unito alla partita.");

            String command;
            while ((command = in.readLine()) != null) {
                System.out.println("Comando ricevuto da " + username + ": " + command);
                switch (command.toLowerCase()) {
                    case "prendi":
                        handlePrendi(out);
                        break;
                    case "abbandona":
                        handleAbbandona(out);
                        return;
                    default:
                        out.println("Comando non valido. Usa 'prendi' o 'abbandona'.");
                        out.flush();
                        break;
                }
            }
        } catch (IOException e) {
            System.out.println("Connessione persa con il client " + (username != null ? username : clientSocket.getRemoteSocketAddress()));
        } finally {
            cleanup();
        }
    }

    private void handlePrendi(PrintWriter out) {
        int carta = (int) (Math.random() * 11) + 1;
        out.println("Hai pescato una carta di valore: " + carta);
        out.flush();
        System.out.println(username + " ha pescato una carta di valore " + carta);
    }

    private void handleAbbandona(PrintWriter out) {
        out.println("Hai abbandonato la partita. Arrivederci " + username + "!");
        out.flush();
        System.out.println(username + " ha abbandonato la partita.");
    }

    private void cleanup() {
        if (username != null) {
            Server.players.remove(username);
            System.out.println(username + " è stato rimosso dalla lista giocatori.");
        }
        try {
            clientSocket.close();
        } catch (IOException e) {
            System.out.println("Errore durante la chiusura del socket: " + e.getMessage());
        }
    }
}
