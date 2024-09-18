package src.view;

import src.models.Kunden;

import javax.swing.table.AbstractTableModel;
import java.util.List;


// KundenTableModel ist eine Tabelle, die Kundeninformationen anzeigt
public class KundenTableModel extends AbstractTableModel {
    // Spaltennamen der Tabelle
    private final String[] columnNames = {"ID", "Name", "Email", "Benutzername", "Adresse"};
    // Liste der Kunden
    private List<Kunden> kundenListe;

    // Konstruktor, der die Kundenliste initialisiert
    public KundenTableModel(List<Kunden> kundenListe) {
        this.kundenListe = kundenListe;
    }

    // Setzt die Kundenliste und aktualisiert die Tabelle
    public void setKundenListe(List<Kunden> kundenListe) {
        this.kundenListe = kundenListe;
        fireTableDataChanged(); // Benachrichtigt die Tabelle, dass sich die Daten geändert haben
    }

    // Gibt die Anzahl der Zeilen in der Tabelle zurück
    @Override
    public int getRowCount() {
        return kundenListe.size();
    }

    // Gibt die Anzahl der Spalten in der Tabelle zurück
    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    // Gibt den Wert an der angegebenen Zeile und Spalte zurück
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Kunden kunde = kundenListe.get(rowIndex); // Holt den Kunden an der angegebenen Zeilenposition
        switch (columnIndex) {
            case 0:
                return kunde.getKundenId(); // Gibt die Kunden-ID zurück
            case 1:
                return kunde.getName(); // Gibt den Namen des Kunden zurück
            case 2:
                return kunde.getEmail(); // Gibt die E-Mail des Kunden zurück
            case 3:
                return kunde.getBenutzername(); // Gibt den Benutzernamen des Kunden zurück
            case 4:
                return kunde.getAdresse(); // Gibt die Adresse des Kunden zurück
            default:
                return null; // Gibt null zurück, wenn die Spalte nicht existiert
        }
    }

    // Gibt den Namen der angegebenen Spalte zurück
    @Override
    public String getColumnName(int column) {
        return columnNames[column];
    }
}
