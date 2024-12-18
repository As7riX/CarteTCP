package engine;

import com.google.gson.Gson;
import engine.models.Carta;
import engine.models.Mazzo;
import engine.models.Player;

public class gameEngine implements Runnable{
    Player p1;
    Player p2;
    Gson gson;

    public gameEngine(Player p1, Player p2){
        this.p1 = p1;
        this.p2 = p2;
        gson = new Gson();
    }

    @Override
    public void run() {
        boolean game = true;

        int p1p = 0;
        int p2p = 0;

        while (game){
            Mazzo mazzo = new Mazzo(1);
            mazzo.setPunteggio(new int[] {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 15});
            mazzo.mescola();

            p1.sendMessage("Entrato in partita");
            p2.sendMessage("Entrato in partita");

            Carta c1 = mazzo.getFirstCard();
            Carta c2 = mazzo.getFirstCard();

            p1.sendMessage("Carta tua");
            p1.sendMessage(gson.toJson(c1));
            p1.sendMessage("Carta Avversario");
            p1.sendMessage(gson.toJson(c2));

            p2.sendMessage("Carta tua");
            p2.sendMessage(gson.toJson(c2));
            p2.sendMessage("Carta Avversario");
            p2.sendMessage(gson.toJson(c1));

            if (c1.get_punteggio() > c2.get_punteggio()){
                p1p++;
                p1.sendMessage("Hai vinto [tu: " + p1p + " avversario: " + p2p + "]");
                p2.sendMessage("Hai Perso [tu: " + p2p + " avversario: " + p1p + "]");

            }else if (c2.get_punteggio() > c1.get_punteggio()){
                p2p++;
                p2.sendMessage("Hai vinto [tu: " + p2p + " avversario: " + p1p + "]");
                p1.sendMessage("Hai Perso [tu: " + p1p + " avversario: " + p2p + "]");


            }else {
                p1.sendMessage("Pareggio [tu: " + p1p + " avversario: " + p2p + "]");
                p2.sendMessage("Pareggio [tu: " + p2p + " avversario: " + p1p + "]");
            }

            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            p1.sendMessage("Vuoi rigiocare? (si - no)");
            p2.sendMessage("Vuoi rigiocare? (si - no)");

            String com1 = null;
            String com2 = null;

            long startTime = System.currentTimeMillis();
            long timeout = 15000;

            while ((System.currentTimeMillis() - startTime < timeout) && (com1 == null || com2 == null)) {
                if (com1 == null) com1 = p1.getLastCommand();
                if (com2 == null) com2 = p2.getLastCommand();

                //System.out.println("Debug: com1=" + com1 + ", com2=" + com2);

                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

            }

            if (com1 == null || com2 == null) {
                System.out.println("Uno o entrambi i player non hanno risposto in tempo.");
                p1.sendMessage("Uno o entrambi i player non hanno risposto in tempo.");
                p2.sendMessage("Uno o entrambi i player non hanno risposto in tempo.");
                game = false;
                continue;
            }

            if (com1.equals("no") || com2.equals("no")) game = false;

            p1.clearCommand();
            p2.clearCommand();
        }

        p1.sendMessage("Gioco terminato");
        p2.sendMessage("Gioco terminato");
        p1.closeConnection();
        p2.closeConnection();


    }
}
