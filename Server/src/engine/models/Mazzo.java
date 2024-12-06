package engine.models;

import java.util.Collections;
import java.util.List;

public class Mazzo {
    private List<Carta> CarteMazzo;

    public Mazzo(int type){

        //creazione mazzo
        switch (type){

            //mazzo singolo completo
            case 0: {
                for (Carta.Semi seme : Carta.Semi.values())
                    for (Carta.Valori val : Carta.Valori.values())
                        CarteMazzo.addLast(new Carta(seme, val, 0));
            }

            //mazzo doppio completo
            case 1: {
                for (int l = 0; l < 2; l++)
                    for (Carta.Semi seme : Carta.Semi.values())
                        for (Carta.Valori val : Carta.Valori.values())
                            CarteMazzo.addLast(new Carta(seme, val, 0));
            }

            //mazzo A, 2, 3, 4, 5, 6, 7, J, Q, K (briscola)
            case 2: {
                for (Carta.Semi seme : Carta.Semi.values()) {
                    CarteMazzo.addLast(new Carta(seme, Carta.Valori.asso, 0));
                    CarteMazzo.addLast(new Carta(seme, Carta.Valori.due, 0));
                    CarteMazzo.addLast(new Carta(seme, Carta.Valori.tre, 0));
                    CarteMazzo.addLast(new Carta(seme, Carta.Valori.quattro, 0));
                    CarteMazzo.addLast(new Carta(seme, Carta.Valori.cinque, 0));
                    CarteMazzo.addLast(new Carta(seme, Carta.Valori.sei, 0));
                    CarteMazzo.addLast(new Carta(seme, Carta.Valori.sette, 0));
                    CarteMazzo.addLast(new Carta(seme, Carta.Valori.fante, 0));
                    CarteMazzo.addLast(new Carta(seme, Carta.Valori.donna, 0));
                    CarteMazzo.addLast(new Carta(seme, Carta.Valori.re, 0));
                }
            }

            default:
                break;
        }
    }

    public void mescola(){
        Collections.shuffle(CarteMazzo);
    }

    //funzione distribuisci prima carta
    public Carta getFirstCard() {
        return CarteMazzo.removeLast();

    }

    //funzione distribuisci ultima carta
    public Carta getLastCard() {

        return CarteMazzo.removeFirst();
    }

    //funzione valore prima carta
    public int getFirstCardVal() {
        return CarteMazzo.getLast().get_punteggio();
    }

    //funzione valore ultima carta
    public int getLastCardVal() {

        return CarteMazzo.getFirst().get_punteggio();
    }

    //funzione check se vuoto
    public boolean isEmpty() {

        return CarteMazzo.isEmpty();
    }

    //funzione impostazione punteggio
    public void setPunteggio(int[] point) {
        for (int i = 0; i < CarteMazzo.size(); i++)
            CarteMazzo.get(i).set_punteggio(point[CarteMazzo.get(i).get_valore().ordinal()]);
    }

    //ricevi numero carte
    public int getNRemains() {
        return CarteMazzo.size();
    }

    //aggiungi al mazzo
    /*
    public void addCard(Carta c, int p) {
        .insert(carte_mazzo.begin() + p, c);
    }*/

    //aggiungi carta cima mazzo
    public void addCardUp(Carta c) {
        CarteMazzo.addLast(c);
    }

    //aggiungi carta fondo mazzo
    public void addCardDown(Carta c) {
        CarteMazzo.addFirst(c);
    }


}
