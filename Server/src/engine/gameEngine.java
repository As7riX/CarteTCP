package engine;

import com.google.gson.Gson;
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

        Mazzo mazzo = new Mazzo(1);
        mazzo.mescola();

        p1.sendMessage("Entrato in partita");
        p2.sendMessage("Entrato in partita");

        p1.sendMessage(gson.toJson(mazzo.getFirstCard()));
        p2.sendMessage(gson.toJson(mazzo.getFirstCard()));

    }
}
