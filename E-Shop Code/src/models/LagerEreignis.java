package src.models;

import java.time.LocalDate;

public class LagerEreignis {
    private LocalDate datum;
    private int menge;

    // // Konstruktor zur Initialisierung eines LagerEreignis-Objekts mit Datum und Menge
    public LagerEreignis(LocalDate datum, int menge) {
        this.datum = datum;
        this.menge = menge;
    }


    public LocalDate getDatum() {
        return datum;
    }

    public int getMenge() {
        return menge;
    }

    @Override
    public String toString() {
        return datum + ";" + menge;
    }



    /**
      Erzeugt ein neues LagerEreignis-Objekt aus einem formatierten String.
      Der String wird anhand des Trennzeichens ";" in Teile gesplittet, wobei das erste
      Teilstück als LocalDate (Datum) und das zweite Teilstück als int (Menge) geparst wird.

      Die Methode fromString ist static, weil sie unabhängig von einer spezifischen Instanz der Klasse
      ist und stattdessen direkt aufgerufen werden kann, um einen String in ein LagerEreignis-Objekt
      umzuwandeln. Sie dient dazu, Daten zu parsen und ein neues Objekt zu erzeugen, ohne dass eine Instanz
      der Klasse vorhanden sein muss.

      parameter: str Der formatierte String, aus dem das LagerEreignis-Objekt erstellt werden soll.
      return: Ein neues LagerEreignis-Objekt, das aus den geparsten Werten erstellt wurde.
     */
    public static LagerEreignis fromString(String str) {
        String[] teile = str.split(";"); // String wird anhand des Trennzeichens ";" in Teile gesplittet
        LocalDate datum = LocalDate.parse(teile[0]); // Erstes Teilstück wird als LocalDate geparst
        int menge = Integer.parseInt(teile[1]);  // Zweites Teilstück wird als int geparst
        return new LagerEreignis(datum, menge);  // Neues LagerEreignis-Objekt wird mit den geparsten Werten erstellt und zurückgegeben
    }
}
