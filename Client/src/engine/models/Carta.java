package engine.models;

public class Carta {

    public static enum Semi { cuori, quadri, fiori, picche};
    public static enum Valori { asso, due, tre, quattro, cinque, sei, sette, otto, nove, dieci, fante, donna, re, jolly};

    private Semi Seme;
    private Valori Valore;
    private int punteggio = 0;

    public Carta(Semi s, Valori v, int p) {
        Seme = s;
        Valore = v;
        punteggio = p;
    }

    public Valori get_valore() {
        return Valore;
    }

    public Semi get_seme() {
        return Seme;
    }

    public int get_punteggio() {
        return punteggio;
    }

    public void set_punteggio(int p) {
        punteggio = p;
    }

}
