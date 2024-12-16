package engine.gamemodes;

import java.util.List;
import engine.models.Carta;
import engine.models.Carta.Valori;

public class BlackjackGame {

    public static int calcolaPunteggio(List<Carta> mano) {
        int somma = 0;
        int assi = 0;

        for (Carta c : mano) {
            int val = valoreBlackjack(c);
            somma += val;
            if (c.getValore() == Valori.ASSO) {
                assi++;
            }
        }

        while (somma > 21 && assi > 0) {
            somma -= 10;
            assi--;
        }

        return somma;
    }

    private static int valoreBlackjack(Carta c) {
        switch (c.getValore()) {
            case ASSO: return 11;
            case DUE: return 2;
            case TRE: return 3;
            case QUATTRO: return 4;
            case CINQUE: return 5;
            case SEI: return 6;
            case SETTE: return 7;
            case OTTO: return 8;
            case NOVE: return 9;
            case DIECI:
            case FANTE:
            case DONNA:
            case RE:
                return 10;
        }
        return 0;
    }
}
