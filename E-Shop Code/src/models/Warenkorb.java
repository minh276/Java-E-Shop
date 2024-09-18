package src.models;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Warenkorb {

    /**
      Eine Map, die jedem Artikel eine Stückzahl zuordnet
      Diese Map speichert eine Zuordnung zwischen Artikeln (Schlüssel) und deren jeweiliger Stückzahl (Werte).
      Dadurch kann der Bestand oder die Anzahl jedes Artikels effizient verwaltet und abgerufen werden.
     */
    private Map<Artikel, Integer> artikelMap;


    /**
      Konstruktor für die Klasse Warenkorb.
      Initialisiert eine neue HashMap für die Artikel und deren Stückzahlen im Warenkorb.
     */
    public Warenkorb() {
        this.artikelMap = new HashMap<>();
    }


    /**
      Gibt eine Liste aller Artikel im Warenkorb zurück.
      Nur die Schlüssel der Map (Artikel) werden in eine neue ArrayList kopiert und zurückgegeben.
      return: Eine Liste der Artikel im Warenkorb.
     */
    public List<Artikel> getArtikel() {
        return new ArrayList<>(artikelMap.keySet());
    }


    /**
      Gibt die Map zurück, die die Artikel im Warenkorb und ihre jeweiligen Stückzahlen enthält.
     return: Die Map der Artikel im Warenkorb mit ihren Stückzahlen.
     */
    public Map<Artikel, Integer> getArtikelMap() {
        return artikelMap;
    }


    /**
     * Fügt einen Artikel mit einer bestimmten Stückzahl dem Warenkorb hinzu.
     * Überprüft, ob ausreichend Bestand für die angegebene Stückzahl vorhanden ist.
     * Wenn genügend Bestand verfügbar ist und der Artikel bereits im Warenkorb ist, wird die Stückzahl aktualisiert.
     * Andernfalls wird der Artikel mit der angegebenen Stückzahl neu hinzugefügt.
     *
     * parameter: artikel    Der Artikel, der dem Warenkorb hinzugefügt werden soll.
     * paramater: stueckzahl Die Anzahl der Stücke des Artikels, die dem Warenkorb hinzugefügt werden sollen.
     */
    public void hinzufuegenArtikelInWarenkorb(Artikel artikel, int stueckzahl) {
        // Holt den aktuellen Bestand des Artikels
        int aktuellerBestand = artikel.getBestand();

        // Überprüfen, ob genug Bestand verfügbar ist
        if (aktuellerBestand >= stueckzahl) {
            if (artikelMap.containsKey(artikel)) { // Überprüft, ob der Artikel bereits im Warenkorb ist
                int aktuelleStueckzahl = artikelMap.get(artikel);  // Holt die aktuelle Stückzahl des Artikels im Warenkorb
                if (aktuellerBestand >= aktuelleStueckzahl + stueckzahl) { // Prüft, ob genug Bestand für die zusätzliche Stückzahl vorhanden ist
                    artikelMap.put(artikel, aktuelleStueckzahl + stueckzahl); // Fügt die zusätzliche Stückzahl zum Warenkorb hinzu
                } else {
                    // Meldung bei nicht ausreichendem Bestand
                    System.out.println("Nicht genügend Bestand verfügbar. Aktueller Bestand: " + aktuellerBestand);
                }
            } else {
                // Fügt den Artikel mit der angegebenen Stückzahl dem Warenkorb hinzu
                artikelMap.put(artikel, stueckzahl);
            }
        } else {
            // Meldung bei nicht ausreichendem Bestand
            System.out.println("Nicht genügend Bestand verfügbar. Aktueller Bestand: " + aktuellerBestand);
        }
    }


    // Methode um die Menge eines Artikels im Warenkorb zu bekommen +
    public int getMenge(Artikel artikel) {
        return artikelMap.getOrDefault(artikel, 0);
    }

    // Methode um ein Artikel aus dem Warenkorb zu entfernen +
    public void entferneArtikelInWarenkorb(Artikel artikel) {
        artikelMap.remove(artikel);
    }

    // Warenkorb leeren +
    public void leerenWarenkorb() {
        artikelMap.clear();
    }


    /**
      Berechnet die Gesamtsumme aller Artikel im Warenkorb.
      Berücksichtigt dabei mögliche Rabatte für Massengutartikel.
      return: Die Gesamtsumme der Preise aller Artikel im Warenkorb.
     */
        public double berechneGesamtImWarenkorb() {
            double summe = 0;// Initialisiert die Gesamtsumme auf 0

            // Iteriert über alle Einträge im Warenkorb (artikelMap)
            for (Map.Entry<Artikel, Integer> entry : artikelMap.entrySet()) {
                Artikel artikel = entry.getKey();// Holt den Artikel aus dem Eintrag
                int stueckzahl = entry.getValue();// Holt die Stückzahl des Artikels aus dem Eintrag
                double preis;  // Initialisiert eine Variable für den Preis


                // Überprüft, ob der Artikel ein Massengutartikel ist
                if (artikel instanceof Massengutartikel) {
                    Massengutartikel massengutartikel = (Massengutartikel) artikel;  //Wandelt den Artikel zu einem Massengutartikel
                    preis = massengutartikel.berechneRabattPreis(stueckzahl); // Berechnet den Preis mit Rabatt für Massengutartikel
                } else {
                    preis = artikel.getPreis() * stueckzahl;  //Berechnet den Preis ohne Rabatt für normale Artikel
                }

                summe += preis; // Addiert den berechneten Preis zum Gesamtsumme
            }
            return summe;  // Gibt die Gesamtsumme aller Artikel im Warenkorb zurück
        }


    /**
      Gibt den Inhalt des Warenkorbs aus.
      Durchläuft die Artikel im Warenkorb, berechnet den Preis unter Berücksichtigung von Rabatten für Massengutartikel,
      und zeigt für jeden Artikel die Stückzahl, den Namen, den Preis pro Stück und die Artikel-ID an.
      Am Ende wird die Gesamtsumme aller Artikel im Warenkorb ausgegeben.
     */
     public void anzeigeWarenkorbInhalt() {
        System.out.println("Inhalt des Warenkorbs:");
         // Durchläuft jedes Paar von Artikel und Stückzahl im Warenkorb
        for (Map.Entry<Artikel, Integer> entry : artikelMap.entrySet()) {
            Artikel artikel = entry.getKey(); // Holt den Artikel aus dem Paa
            int stueckzahl = entry.getValue();  // Holt die Stückzahl aus dem Paar
            double preis;

            //Überprüft, ob der Artikel ein Massengutartikel ist und berechnet den Preis entsprechend
            if (artikel instanceof Massengutartikel) {
                Massengutartikel massengutartikel = (Massengutartikel) artikel; //Artikel wird zu einem Massengutartikel umgewandelt
                // Ruft die Methode berechneRabattPreis der Massengutartikel-Instanz auf, um den Preis für die angegebene Stückzahl zu berechnen
                preis = massengutartikel.berechneRabattPreis(stueckzahl);
            } else {
                // Berechnet den Preis für normale Artikel ohne Rabatt
                preis = artikel.getPreis() * stueckzahl;
            }

            String artikelname = artikel.getName();// Holt den Namen des Artikels
            int artikelId = artikel.getArtikelId();// Holt die Artikel-ID des Artikels

            // Gibt die Anzahl, den Namen, den Preis pro Stück und die Artikel-ID des Artikels aus
            System.out.println(stueckzahl + "x " + artikelname + ": " + preis + " Euro" + " (ArtikelID: " + artikelId + ")");
        }
         // Gibt die Gesamtsumme aller Artikel im Warenkorb aus
        System.out.println("Gesamtsumme aller Artikel im Warenkorb: " + berechneGesamtImWarenkorb() + " Euro");
    }


    /**
      Aktualisiert die Stückzahl eines Artikels im Warenkorb.
      parameter: artikel Der Artikel, dessen Stückzahl aktualisiert werden soll.
      parameter: neueStueckzahl Die neue Stückzahl für den Artikel.
     */
    public void aktualisiereArtikelStueckzahl(Artikel artikel, int neueStueckzahl) {
        // Überprüfen, ob der Artikel in der Map enthalten ist
        if (artikelMap.containsKey(artikel)) {
            // Menge des Artikels aktualisieren
            artikelMap.put(artikel, neueStueckzahl);
            // Gibt eine Bestätigungsmeldung mit dem Namen des Artikels und der neuen Stückzahl aus
            System.out.println("Menge von " + artikel.getName() + " wurde auf " + neueStueckzahl + " aktualisiert.");
        } else {
            //Gibt eine Fehlermeldung aus, wenn der Artikel nicht im Warenkorb gefunden wurde
            System.out.println("Artikel nicht im Warenkorb gefunden.");
        }
    }


    /**
      Gibt eine String-Repräsentation des Warenkorbs zurück.
      Die Repräsentation enthält Informationen über alle Artikel im Warenkorb, einschließlich ihrer IDs, Namen, Preise und Stückzahlen.
      return: Eine String-Repräsentation des Inhalts des Warenkorbs.
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(); // Erstellt einen neuen StringBuilder zur effizienten Erstellung der Zeichenkette
        sb.append("Inhalt des Warenkorbs:\n"); // Fügt eine Überschrift für den Warenkorbinhalt hinzu

        // Durchläuft jedes Paar von Artikel und Stückzahl im Warenkorb
        for (Map.Entry<Artikel, Integer> entry : artikelMap.entrySet()) {
            Artikel artikel = entry.getKey(); // Holt den Artikel aus dem Eintrag
            int stueckzahl = entry.getValue(); // Holt die Stückzahl des Artikels aus dem Eintrag
            double preis = artikel.getPreis(); // Holt den Preis des Artikels
            String artikelname = artikel.getName(); // Holt den Namen des Artikels
            int artikelId = artikel.getArtikelId(); // Holt die Artikel-ID des Artikels

            // Fügt die Artikel-ID zur Zeichenkette hinzu
            sb.append("ID: ").append(artikelId).append(", ");
            // Fügt den Artikelnamen und den Preis zur Zeichenkette hinzu
            sb.append(artikelname).append(" - ").append(preis).append(" EUR\n");
            // Fügt die Stückzahl des Artikels zur Zeichenkette hinzu
            sb.append(" - Anzahl: ").append(stueckzahl).append("\n");
        }
        return sb.toString(); // Gibt die zusammengesetzte Zeichenkette zurück
    }
}
