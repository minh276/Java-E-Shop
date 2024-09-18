package src.persistence;
import java.io.IOException;
import java.util.List;
import src.models.*;


public interface PersistenceManager {

    /**
     * Öffnet eine Datei zum Lesen.
     * parameter: datei Der Name der Datei, die geöffnet werden soll.
     * throws: IOException Wenn ein Fehler beim Öffnen der Datei auftritt.
     */
    void openForReading(String datei) throws IOException;


    /**
     * Öffnet eine Datei zum Schreiben.
     * parameter: datei Der Name der Datei, die geöffnet werden soll.
     * throws: IOException Wenn ein Fehler beim Öffnen der Datei auftritt.
     */
    void openForWriting(String datei) throws IOException;


    /**
     * Schließt den aktuell geöffneten Reader oder Writer.
     * return: true, wenn das Schließen erfolgreich war; false, wenn ein Fehler aufgetreten ist.
     */
    boolean close();


    /**
     * Lädt die Artikel aus einer Datei und erstellt eine Liste von Artikel-Objekten.
     * return: Eine Liste von Artikeln, die aus der Datei geladen wurden.
     * throws: IOException Wenn ein Fehler beim Lesen der Datei auftritt.
     */
    List<Artikel> ladeArtikel() throws IOException;


    /**
     * Speichert eine Liste von Artikel-Objekten in einer Datei.
     * parameter: artikelListe Die Liste der Artikel, die gespeichert werden sollen.
     * return: true, wenn das Speichern erfolgreich war.
     * throws: IOException Wenn ein Fehler beim Schreiben der Datei auftritt.
     */
    boolean speichereArtikel(List<Artikel> artikelListe) throws IOException;


    /**
     * Lädt die Mitarbeiter aus einer Datei und erstellt eine Liste von Mitarbeiter-Objekten.
     * return: Eine Liste von Mitarbeitern, die aus der Datei geladen wurden.
     * throws: IOException Wenn ein Fehler beim Lesen der Datei auftritt.
     */
    List<Mitarbeiter> ladeMitarbeiter() throws IOException;


    /**
     * Speichert eine Liste von Mitarbeiter-Objekten in einer Datei.
     * parameter: mitarbeiterListe Die Liste der Mitarbeiter, die gespeichert werden sollen.
     * return: true, wenn das Speichern erfolgreich war.
     * throws: IOException Wenn ein Fehler beim Schreiben der Datei auftritt.
     */
    boolean speichereMitarbeiter(List<Mitarbeiter> mitarbeiterListe) throws IOException;


    /**
     * Lädt die Kunden aus einer Datei und erstellt eine Liste von Kunden-Objekten.
     * return: Eine Liste von Kunden, die aus der Datei geladen wurden.
     * throws: IOException Wenn ein Fehler beim Lesen der Datei auftritt.
     */
    List<Kunden> ladeKunden() throws IOException;


    /**
     * Speichert eine Liste von Kunden-Objekten in einer Datei.
     * parameter: kundenListe Die Liste der Kunden, die gespeichert werden sollen.
     * return: true, wenn das Speichern erfolgreich war.
     * throws: IOException Wenn ein Fehler beim Schreiben der Datei auftritt.
     */
    boolean speichereKunden(List<Kunden> kundenListe) throws IOException;


    /**
     * Lädt die Lagerereignisse für einen bestimmten Artikel aus einer Datei.
     * parameter: artikelId Die ID des Artikels, dessen Lagerereignisse geladen werden sollen.
     * return: Eine Liste der Lagerereignisse für den angegebenen Artikel.
     * throws: IOException Wenn ein Fehler beim Lesen der Datei auftritt.
     */
    List<LagerEreignis> ladeLagerEreignisse(int artikelId) throws IOException;


    /**
     * Speichert eine Liste von Lagerereignis-Objekten für einen bestimmten Artikel in einer Datei.
     * parameter: artikelId Die ID des Artikels, dessen Lagerereignisse gespeichert werden sollen.
     * parameter: ereignisListe Die Liste der Lagerereignisse, die gespeichert werden sollen.
     * return: true, wenn das Speichern erfolgreich war.
     * throws: IOException Wenn ein Fehler beim Schreiben der Datei auftritt.
     */
    boolean speichereLagerEreignisse(int artikelId, List<LagerEreignis> ereignisListe) throws IOException;
}

