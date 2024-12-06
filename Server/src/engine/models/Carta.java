package engine.models;

class Carta {

    public enum Semi { cuori, quadri, fiori, picche}
    public enum Valori { asso, due, tre, quattro, cinque, sei, sette, otto, nove, dieci, fante, donna, re, jolly}

    private final Semi Seme;
    private final Valori Valore;
    private int punteggio;

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
