package src.models;
public class Massengutartikel extends Artikel {
    private int packungsgroesse;

    public Massengutartikel(int artikelId, String name, String farbe, double groesse, double preis, int bestand, int packungsgroesse) {
        super(artikelId, name, farbe, groesse, preis, bestand);
        this.packungsgroesse = packungsgroesse;
    }



    public int getPackungsgroesse() {
        return packungsgroesse;
    }

    @Override
    public String toString() {
        return super.toString()+ " ( 20% Rabatt bei Kauf von einer " + packungsgroesse + "er Packung)";
    }


    //Berechnet den rabattierten Preis eines massengutartiekl
    public double berechneRabattPreis(int stueckzahl) {
        // Überprüft, ob die Stückzahl durch die Packungsgröße ohne Rest teilbar ist
        if (stueckzahl % packungsgroesse == 0) {
            // Berechnet den Preis mit einem Rabatt von 20% für eine ganze Anzahl von Packungen
            return getPreis() * stueckzahl * 0.8; // 20% Rabatt
        }
        // Berechnet den Preis ohne Rabatt für eine unvollständige Anzahl von Packungen
        return getPreis() * stueckzahl;
    }
}
