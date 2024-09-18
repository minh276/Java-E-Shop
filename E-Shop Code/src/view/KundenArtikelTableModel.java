package src.view;

import src.models.Artikel;
import src.models.Massengutartikel;

import javax.swing.table.AbstractTableModel;
import java.util.List;


public class KundenArtikelTableModel extends AbstractTableModel {
    // Ein Array von Strings, das die Namen der Spalten in der Tabelle definiert.
    private final String[] columnNames = {"Artikel ID", "Name", "Farbe", "Größe", "Preis", "Typ"};
    // Eine Liste von Artikel-Objekten, die die Daten der Tabelle enthält.
    private final List<Artikel> artikelListe;

    /**
     * Konstruktor, initialisiert die artikelListe mit der übergebenen Liste von Artikel-Objekten.
     * parameter: artikelListe Die Liste von Artikel-Objekten.
     */
    public KundenArtikelTableModel(List<Artikel> artikelListe) {
        this.artikelListe = artikelListe;
    }

    /**
     * Gibt die Anzahl der Zeilen in der Tabelle zurück, die der Größe der artikelListe entspricht.
     * return: Die Anzahl der Zeilen.
     */
    @Override
    public int getRowCount() {
        return artikelListe.size();
    }


    /**
     * Gibt die Anzahl der Spalten in der Tabelle zurück, die der Länge des columnNames-Arrays entspricht.
     * return: Die Anzahl der Spalten.
     */
    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    /**
     * Gibt den Namen der Spalte für einen gegebenen Spaltenindex zurück.
     * parameter: column Der Index der Spalte.
     * return: Der Name der Spalte.
     */
    @Override
    public String getColumnName(int column) {
        return columnNames[column];
    }

    /**
     * Holt das Artikel-Objekt an der angegebenen Zeilenposition (rowIndex) und gibt den entsprechenden Wert basierend auf der Spaltenposition (columnIndex) zurück.
     * Die Rückgabe von Object ermöglicht es, dass die Methode verschiedene Datentypen zurückgeben kann, abhängig von der Spalte und dem Inhalt der Tabelle.
     * parameter: rowIndex    Die Zeilenposition des Artikels.
     * parameter: columnIndex Die Spaltenposition des Wertes.
     * return: Der Wert des Artikels in der entsprechenden Spalte.
     */
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        // Holt das Artikel-Objekt an der angegebenen Zeilenposition (rowIndex)
        Artikel artikel = artikelListe.get(rowIndex);
        // Gibt den entsprechenden Wert basierend auf der Spaltenposition (columnIndex) zurück
        switch (columnIndex) {
            case 0: // Gibt die Artikel-ID zurück
                return artikel.getArtikelId();
            case 1: // Gibt den Namen des Artikels zurück
                return artikel.getName();
            case 2: // Gibt die Farbe des Artikels zurück
                return artikel.getFarbe();
            case 3: // Gibt die Größe des Artikels zurück
                return artikel.getGroesse();
            case 4: // Gibt den Preis des Artikels zurück
                return artikel.getPreis();
            case 5: // Gibt den Typ des Artikels zurück (Massengutartikel oder Standardartikel)
                if (artikel instanceof Massengutartikel) {
                    Massengutartikel mArtikel = (Massengutartikel) artikel;
                    return mArtikel.getPackungsgroesse() + "er Pack, 20% Rabatt";
                } else {
                    return "Standardartikel";
                }
            default: // Wenn die Spaltenindex nicht zutrifft, wird null zurückgegeben
                return null;
        }
    }


    /**
     * Gibt das Artikel-Objekt an der angegebenen Zeilenposition zurück.
     * parameter: rowIndex Die Zeilenposition des Artikels.
     * return: Das Artikel-Objekt.
     */
    public Artikel getArtikelAt(int rowIndex) {
        return artikelListe.get(rowIndex);
    }

    /**
     * Setzt die Liste der Artikel und aktualisiert die Tabelle.
     * parameter: artikelListe Die neue Liste der Artikel.
     */
    public void setArtikelListe(List<Artikel> artikelListe) {
        this.artikelListe.clear(); // Löscht die aktuelle artikelListe.
        this.artikelListe.addAll(artikelListe); // Fügt alle Artikel aus der neuen artikelListe hinzu.
        fireTableDataChanged(); // Benachrichtigt die Tabelle, dass sich die Daten geändert haben, wodurch die Tabelle aktualisiert wird. /methode von abstracttablemodel
    }
}
