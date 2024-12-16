package engine.connection;

import engine.models.Carta;
import engine.models.Deck;
import engine.gamemodes.BlackjackGame;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.*;

public class GameManager {

    private final Object lock = new Object();
    private final int NUM_PLAYERS = 2;

    private Deck mazzo;
    private List<String> players = new ArrayList<>();
    private Map<String, List<Carta>> maniGiocatori = new HashMap<>();
    private List<Carta> manoDealer = new ArrayList<>();
    private Map<String, Boolean> fermi = new HashMap<>();
    private Map<String, Socket> sockets = new HashMap<>();

    private boolean partitaIniziata = false;

    public GameManager() {
        // Aspetteremo i player, non inizializziamo il mazzo ora
    }

    public void aggiungiGiocatore(String username, Socket socket) {
        synchronized (lock) {
            players.add(username);
            sockets.put(username, socket);
            fermi.put(username, false);

            if (players.size() == NUM_PLAYERS && !partitaIniziata) {
                iniziaPartita();
            }
        }
    }

    private void iniziaPartita() {
        mazzo = new Deck();
        mazzo.mescola();
        maniGiocatori.clear();
        manoDealer.clear();

        for (String p : players) {
            List<Carta> mano = new ArrayList<>();
            mano.add(mazzo.pesca());
            mano.add(mazzo.pesca());
            maniGiocatori.put(p, mano);
        }

        manoDealer.add(mazzo.pesca());
        manoDealer.add(mazzo.pesca());

        partitaIniziata = true;

        // Invia stato iniziale a tutti
        for (String p : players) {
            inviaMessaggio(p, "La partita è iniziata! Le tue carte iniziali:");
            for (Carta c : maniGiocatori.get(p)) {
                inviaMessaggio(p, c.toString());
            }
            int punteggio = BlackjackGame.calcolaPunteggio(maniGiocatori.get(p));
            inviaMessaggio(p, "Punteggio attuale: " + punteggio);
            inviaMessaggio(p, "Carta dealer scoperta: " + manoDealer.get(0).toString());
            inviaMessaggio(p, "Comandi: 'prendi' per pescare, 'stai' per fermarti, 'abbandona' per uscire.");
        }
    }

    public boolean partitaIniziata() {
        return partitaIniziata;
    }

    public void comandoPrendi(String username) {
        synchronized (lock) {
            if (fermi.get(username)) {
                inviaMessaggio(username, "Sei già fermo. Non puoi più pescare.");
                return;
            }

            Carta c = mazzo.pesca();
            maniGiocatori.get(username).add(c);
            inviaMessaggio(username, "Hai pescato una carta di valore: " + c.toString());
            int p = BlackjackGame.calcolaPunteggio(maniGiocatori.get(username));
            inviaMessaggio(username, "Punteggio attuale: " + p);

            if (p > 21) {
                inviaMessaggio(username, "Hai sballato! Punteggio finale: " + p);
                fermi.put(username, true);
                checkFinePartita();
            }
        }
    }

    public void comandoStai(String username) {
        synchronized (lock) {
            if (fermi.get(username)) {
                inviaMessaggio(username, "Sei già fermo.");
                return;
            }
            int p = BlackjackGame.calcolaPunteggio(maniGiocatori.get(username));
            inviaMessaggio(username, "Ti sei fermato. Punteggio finale: " + p);
            fermi.put(username, true);
            checkFinePartita();
        }
    }

    public void comandoAbbandona(String username) {
        synchronized (lock) {
            inviaMessaggio(username, "Hai abbandonato la partita. Arrivederci " + username + "!");
            chiudiConnessioni();
        }
    }

    private void checkFinePartita() {
        // Se tutti i giocatori sono fermi o sballati
        for (String p : players) {
            if (!fermi.get(p)) {
                return; // C'è qualcuno che non è ancora fermo, la partita continua
            }
        }

        // Tutti fermi o sballati, turno del dealer
        turnoDealer();

        // Calcola e invia risultati a tutti
        int puntiDealer = BlackjackGame.calcolaPunteggio(manoDealer);
        for (String p : players) {
            inviaMessaggio(p, "Le carte del dealer erano:");
            for (Carta c : manoDealer) {
                inviaMessaggio(p, c.toString());
            }
            inviaMessaggio(p, "Punteggio dealer: " + puntiDealer);

            int puntiGioc = BlackjackGame.calcolaPunteggio(maniGiocatori.get(p));
            inviaMessaggio(p, "Il tuo punteggio: " + puntiGioc);

            String risultato;
            if (puntiGioc > 21) {
                risultato = "Hai perso (sballato)!";
            } else if (puntiDealer > 21) {
                risultato = "Hai vinto! Il dealer ha sballato.";
            } else if (puntiGioc > puntiDealer) {
                risultato = "Hai vinto! Hai un punteggio maggiore del dealer.";
            } else if (puntiGioc < puntiDealer) {
                risultato = "Hai perso! Il dealer ha un punteggio maggiore.";
            } else {
                risultato = "Pareggio!";
            }

            inviaMessaggio(p, risultato);
        }

        inviaATutti("La partita è terminata. Disconnessione in corso.");
        chiudiConnessioni();
    }

    private void turnoDealer() {
        int pDealer = BlackjackGame.calcolaPunteggio(manoDealer);
        while (pDealer < 17) {
            manoDealer.add(mazzo.pesca());
            pDealer = BlackjackGame.calcolaPunteggio(manoDealer);
        }
    }

    private void inviaMessaggio(String player, String msg) {
        Socket s = sockets.get(player);
        if (s == null) return;
        try {
            PrintWriter out = new PrintWriter(s.getOutputStream(), true);
            out.println(msg);
        } catch (IOException e) {}
    }

    private void inviaATutti(String msg) {
        for (String p : players) {
            inviaMessaggio(p, msg);
        }
    }

    private void chiudiConnessioni() {
        for (String p : players) {
            Socket s = sockets.get(p);
            if (s != null && !s.isClosed()) {
                try {
                    s.close();
                } catch (IOException e) {}
            }
        }
        players.clear();
        sockets.clear();
        maniGiocatori.clear();
        manoDealer.clear();
        fermi.clear();
        partitaIniziata = false;
    }
}
