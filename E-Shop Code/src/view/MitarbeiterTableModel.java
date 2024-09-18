package src.view;

import src.models.Mitarbeiter;

import javax.swing.table.AbstractTableModel;
import java.util.List;

public class MitarbeiterTableModel extends AbstractTableModel {
    // Array mit Spaltenüberschriften für die Tabelle
    private final String[] columnNames = {"ID", "Name", "Email", "Benutzername"};
    // Liste der Mitarbeiter, die in der Tabelle angezeigt werden sollen
    private List<Mitarbeiter> mitarbeiterListe;

    // Konstruktor für das MitarbeiterTableModel
    public MitarbeiterTableModel(List<Mitarbeiter> mitarbeiterListe) {
        this.mitarbeiterListe = mitarbeiterListe;
    }

    // Methode zum Aktualisieren der Mitarbeiterliste und Benachrichtigen der Tabelle über die Änderung
    public void setMitarbeiterListe(List<Mitarbeiter> mitarbeiterListe) {
        this.mitarbeiterListe = mitarbeiterListe;
        fireTableDataChanged(); // Benachrichtigt die Tabelle, dass sich die Daten geändert haben
    }

    // Anzahl der Zeilen in der Tabelle (Anzahl der Mitarbeiter)
    @Override
    public int getRowCount() {
        return mitarbeiterListe.size();
    }

    // Anzahl der Spalten in der Tabelle (Anzahl der Attribute pro Mitarbeiter)
    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    // Rückgabe des Werts an einer bestimmten Zelle der Tabelle (Zeile, Spalte)
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Mitarbeiter mitarbeiter = mitarbeiterListe.get(rowIndex); // Mitarbeiter an der gegebenen Zeile
        switch (columnIndex) { // Entsprechend der Spalte, den entsprechenden Mitarbeiterattribut zurückgeben
            case 0:
                return mitarbeiter.getMitarbeiterId(); // ID des Mitarbeiters
            case 1:
                return mitarbeiter.getName(); // Name des Mitarbeiters
            case 2:
                return mitarbeiter.getEmail(); // Email des Mitarbeiters
            case 3:
                return mitarbeiter.getBenutzername(); // Benutzername des Mitarbeiters
            default:
                return null;
        }
    }

    // Rückgabe der Spaltenüberschrift für eine bestimmte Spalte
    @Override
    public String getColumnName(int column) {
        return columnNames[column];
    }
}
