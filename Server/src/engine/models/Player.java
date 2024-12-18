package engine.models;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Player implements Runnable {
    private String nome;
    private Mazzo mano;
    private BufferedReader in;

    private String lastMessage;

    private PrintWriter out;

    private Socket socket;

    public Player(Socket s) {
        socket = s;
        try {
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);
        } catch (IOException e) {
            System.out.println("Errore" + e);
        }

        lastMessage = null;

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

    public String getLastCommand(){
        String tmp = lastMessage;
        lastMessage = null;
        return tmp;

    }

    public void sendMessage(String msg){
        out.println(msg);
    }

    public void run() {
        try {
            out.println("ciao quale e il tuo username?");

            String input = in.readLine();

            nome = input;

            out.println("Benvenuto " + nome + "! Sei entrato nella lobby.");


            String comando;

            while (true){
                while ((comando = in.readLine()) != null) {
                    comando = comando.trim().toLowerCase();

                    lastMessage = comando;

                    }
                }


        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
