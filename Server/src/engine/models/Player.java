package engine.models;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Player implements Runnable {
    private String nome;
    private Mazzo mano;

    private Socket socket;

    public Player(Socket s){
        socket = s;
        /*nome = username;
        mano = new Mazzo(3);*/
    }

    public String getNome(){
        return nome;
    }

    public Mazzo getMano(){
        return mano;
    }

    public void addCard(Carta c){
        mano.addCardUp(c);
    }

    public Carta getCard(){
        return mano.getFirstCard();
    }

    public void run() {
        try (
                BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()));
                PrintWriter out = new PrintWriter(s.getOutputStream(), true)
        ) {
            out.println("ciao quale e il tuo username?");

            // 2) Leggi username
            while (true) {
                String input = in.readLine();

                nome = input;

            }

            out.println("Benvenuto " + nome + "! Sei entrato nella lobby.");

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

}
