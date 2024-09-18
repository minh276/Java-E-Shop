package src.view;

import src.models.Artikel;
import src.models.Massengutartikel;

import javax.swing.table.AbstractTableModel;
import java.util.List;

public class ArtikelTableModel extends AbstractTableModel {
    // Die Namen der Spalten in der Tabelle
    private final String[] columnNames = {"ID", "Name", "Farbe", "Größe", "Preis", "Bestand", "Typ"};
    // Die Liste der Artikel, die in der Tabelle angezeigt werden sollen
    private List<Artikel> artikelListe;

    // Konstruktor, der die Liste der Artikel initialisiert
    public ArtikelTableModel(List<Artikel> artikelListe) {
        this.artikelListe = artikelListe;
    }

    // Methode zum Setzen der Artikel-Liste und Benachrichtigung der Tabelle über Datenänderungen
    public void setArtikelListe(List<Artikel> artikelListe) {
        this.artikelListe = artikelListe;
        fireTableDataChanged(); // Benachrichtigt die Tabelle, dass sich die Daten geändert haben
    }

    // Gibt die Anzahl der Zeilen in der Tabelle zurück
    @Override
    public int getRowCount() {
        return artikelListe.size();
    }

    // Gibt die Anzahl der Spalten in der Tabelle zurück
    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    // Gibt den Wert einer bestimmten Zelle in der Tabelle zurück
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Artikel artikel = artikelListe.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return artikel.getArtikelId(); // Gibt die Artikel-ID zurück
            case 1:
                return artikel.getName(); // Gibt den Namen des Artikels zurück
            case 2:
                return artikel.getFarbe(); // Gibt die Farbe des Artikels zurück
            case 3:
                return artikel.getGroesse(); // Gibt die Größe des Artikels zurück
            case 4:
                return artikel.getPreis(); // Gibt den Preis des Artikels zurück
            case 5:
                return artikel.getBestand(); // Gibt den Bestand des Artikels zurück
            case 6:
                if (artikel instanceof Massengutartikel) {
                    Massengutartikel mArtikel = (Massengutartikel) artikel;
                    return mArtikel.getPackungsgroesse() + "er Pack, 20% Rabatt"; // Gibt den Packungstyp und Rabatt für Massengutartikel zurück
                } else {
                    return "Standardartikel"; // Gibt "Standardartikel" zurück, wenn es kein Massengutartikel ist
                }
            default:
                return null; // Gibt null zurück, wenn keine gültige Spalte angegeben ist
        }
    }

    // Gibt den Namen einer bestimmten Spalte zurück
    @Override
    public String getColumnName(int column) {
        return columnNames[column];
    }
}
