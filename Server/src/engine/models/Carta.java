package engine.models;

public class Carta {

    public enum Semi { CUORI, QUADRI, FIORI, PICCHE }
    public enum Valori { ASSO, DUE, TRE, QUATTRO, CINQUE, SEI, SETTE, OTTO, NOVE, DIECI, FANTE, DONNA, RE }

    private Semi seme;
    private Valori valore;

    public Carta(Semi s, Valori v) {
        this.seme = s;
        this.valore = v;
    }

    public Semi getSeme() {
        return seme;
    }

    public Valori getValore() {
        return valore;
    }

    @Override
    public String toString() {
        return valore.name() + " di " + seme.name();
    }
}
