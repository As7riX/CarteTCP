package engine.models;

import java.util.Map;

import static java.util.Map.entry;

public class Carta {

    public static enum Semi { cuori, quadri, fiori, picche};
    public static enum Valori { asso, due, tre, quattro, cinque, sei, sette, otto, nove, dieci, fante, donna, re, jolly};

    private Semi seme;
    private Valori valore;

    public Carta(Semi s, Valori v) {
        valore = v;
        seme = s;
    };


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

        }else{
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
                entry(0, "C"),
                entry(1, "Q"),
                entry(2, "F"),
                entry(3, "P")
            );


            out[0] = "-----------\t";
            out[1] = "|" + s.get(carta.seme.ordinal()) + "        |\t";
            out[2] = "|         |\t";
            out[3] = "|         |\t";
            out[4] = "|    " + v.get(carta.valore.ordinal()) + "    |\t";
            out[5] = "|         |\t";
            out[6] = "|         |\t";
            out[7] = "|        "+ s.get(carta.seme.ordinal()) + "|\t";
            out[8] = "-----------\t";
        }

        for (int i = 0; i < 9; i++) {
            System.out.println(out[i]);
        }

    }

}
