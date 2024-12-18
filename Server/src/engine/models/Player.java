package engine.models;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Player implements Runnable {

    private Mazzo mano;

    private Socket socket;
    private BufferedReader in;
    private PrintWriter out;

    private boolean ready;

    private String lastMessage;


    public Player(Socket s) {
        socket = s;
        try {
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);
        } catch (IOException e) {
            System.out.println("Errore" + e);
        }

        lastMessage = null;
        mano = new Mazzo(3);
        ready = false;
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

    public void closeConnection(){
        try {
            socket.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean getReady(){
        return ready;
    }

    public String getLastCommand(){
        return lastMessage;

    }

    public void clearCommand(){
        lastMessage = null;
    }

    public void sendMessage(String msg){
        out.println(msg);
    }

    public void run() {
        try {
            out.println("Sei entrato nella lobby.");
            ready = true;

            String comando;

            while (socket.isConnected()) {
                while ((comando = in.readLine()) != null) {
                    comando = comando.trim().toLowerCase();

                    if (comando.equals("si") || comando.equals("no")) {
                        lastMessage = comando;
                    }else{
                        out.println("Comando non valido");
                    }


                }
            }


        } catch (IOException e) {
            System.out.println("Connessione Terminata");
        }
    }

}
