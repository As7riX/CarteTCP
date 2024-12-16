package engine.models;

import engine.models.Carta.Semi;
import engine.models.Carta.Valori;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Deck {

    private List<Carta> carte = new ArrayList<>();

    public Deck() {
        for (Semi s : Semi.values()) {
            // ignoriamo il JOLLY per il Blackjack
            for (Valori v : Valori.values()) {
                if (v != Valori.JOLLY) {
                    carte.add(new Carta(s, v));
                }
            }
        }
    }

    public void mescola() {
        Collections.shuffle(carte);
    }

    public Carta pesca() {
        if (carte.isEmpty()) return null;
        return carte.remove(0);
    }
}
