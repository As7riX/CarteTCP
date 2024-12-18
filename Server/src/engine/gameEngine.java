package engine;

import engine.models.Mazzo;
import engine.models.Player;

public class gameEngine implements Runnable{
    Player p1;
    Player p2;


    public gameEngine(Player p1, Player p2){
        this.p1 = p1;
        this.p2 = p2;
    }

    @Override
    public void run() {
        Mazzo mazzo = new Mazzo(1);
        mazzo.mescola();

    }
}
