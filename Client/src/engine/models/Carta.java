package engine.models;

import java.util.Map;
import static java.util.Map.entry;

public class Carta {

    public enum Semi { cuori, quadri, fiori, picche}
    public enum Valori { asso, due, tre, quattro, cinque, sei, sette, otto, nove, dieci, fante, donna, re, jolly}

    private final Semi Seme;
    private final Valori Valore;
    private int punteggio;

    public Carta(Semi s, Valori v) {
        Valore = v;
        Seme = s;
    }

    public Semi getSeme() {
        return Seme;
    }

    public Valori getValore() {
        return Valore;
    }

    public static void PrintCard(Carta carta, boolean coperta) {

        String[] out = new String[9];

        if (coperta) {
            out[0] = "-----------\t";
            out[1] = "|*.*.*.*.*|\t";
            out[2] = "|.*.*.*.*.|\t";
            out[3] = "|*.*.*.*.*|\t";
            out[4] = "|.*.*.*.*.|\t";
            out[5] = "|*.*.*.*.*|\t";
            out[6] = "|.*.*.*.*.|\t";
            out[7] = "|*.*.*.*.*|\t";
            out[8] = "-----------\t";

        } else {
            Map<Integer, String> v = Map.ofEntries(
                    entry(0, "A"),
                    entry(1, "2"),
                    entry(2, "3"),
                    entry(3, "4"),
                    entry(4, "5"),
                    entry(5, "6"),
                    entry(6, "7"),
                    entry(7, "8"),
                    entry(8, "9"),
                    entry(9, "10"),
                    entry(10, "J"),
                    entry(11, "Q"),
                    entry(12, "K"),
                    entry(13, "Y")
            );

            Map<Integer, String> s = Map.ofEntries(
                entry(0, "C"), // CUORI
                entry(1, "Q"), // QUADRI
                entry(2, "F"), // FIORI
                entry(3, "P")  // PICCHE
            );

            out[0] = "-----------\t";
            out[1] = "|" + s.get(carta.Seme.ordinal()) + "        |\t";
            out[2] = "|         |\t";
            out[3] = "|         |\t";
            out[4] = "|    " + v.get(carta.Valore.ordinal()) + "    |\t";
            out[5] = "|         |\t";
            out[6] = "|         |\t";
            out[7] = "|        "+ s.get(carta.Seme.ordinal()) + "|\t";
            out[8] = "-----------\t";
        }

        for (int i = 0; i < 9; i++) {
            System.out.println(out[i]);
        }

    }

    @Override
    public String toString() {
        return Valore.name() + " di " + Seme.name();
    }

}
