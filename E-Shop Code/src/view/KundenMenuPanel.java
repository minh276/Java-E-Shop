package src.view;

import src.controller.ArtikelVW;
import src.controller.KundenVW;
import src.models.Artikel;
import src.models.Warenkorb;
import src.models.Kunden;
import src.models.Rechnung;
import src.exceptions.*;

import java.util.Comparator;
import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;


public class KundenMenuPanel extends JPanel {
    private ArtikelVW artikelVW;
    private KundenVW kundenVW;
    private GUI mainFrame;
    private Warenkorb warenkorb;

    private JTextField suchfeld;
    private JButton suchenButton;
    private JTable artikelTable;
    private KundenArtikelTableModel kundenArtikelTableModel;


    //Konstruktor
    public KundenMenuPanel(ArtikelVW artikelVW, KundenVW kundenVW, GUI mainFrame) {
        this.artikelVW = artikelVW;
        this.kundenVW = kundenVW;
        this.mainFrame = mainFrame;
        this.warenkorb = new Warenkorb();  // Warenkorb initialisieren

        initializeUI();  //Aufruf der Methode zur Initialisierung der Benutzeroberfläche


    }

    private void initializeUI() {
        // Setzt das Layout des Panels auf BorderLayout
        setLayout(new BorderLayout());

        // Erstellt das obere Panel mit BorderLayout
        JPanel topPanel = new JPanel(new BorderLayout());

        // Suchfeld und Suchbutton links oben
        JPanel suchePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        suchfeld = new JTextField(20); // Erstellt ein Textfeld für die Suche mit einer Breite von 20 Spalten
        suchenButton = new JButton("Suchen"); // Erstellt einen Button mit der Beschriftung "Suchen"
        suchenButton.addActionListener(e -> artikelSuchen()); // Fügt dem Suchbutton einen ActionListener hinzu, der die Methode artikelSuchen() aufruft
        suchePanel.add(new JLabel("Suchbegriff:")); // Fügt ein Label zum Suchpanel hinzu
        suchePanel.add(suchfeld); // Fügt das Textfeld zum Suchpanel hinzu
        suchePanel.add(suchenButton); // Fügt den Suchbutton zum Suchpanel hinzu

        // Fügt das Suchpanel auf der linken Seite des oberen Panels hinzu
        topPanel.add(suchePanel, BorderLayout.WEST);

        // Buttons für andere Funktionen rechts oben
        JPanel buttonPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0; // Setzt die Spaltenposition auf 0
        gbc.gridy = 0; // Setzt die Zeilenposition auf 0
        gbc.fill = GridBagConstraints.HORIZONTAL; // Die Buttons sollen die verfügbare horizontale Breite ausfüllen
        gbc.insets = new Insets(5, 5, 5, 5); // Abstand von 5 Pixeln um die Buttons herum

        // Erstellt und fügt die Buttons zum Button-Panel hinzu
        JButton artikelAnzeigenButton = new JButton("Alle Artikel anzeigen"); // Erstellt JButton mit Beschriftung "Alle Artikel anzeigen"
        artikelAnzeigenButton.addActionListener(e -> alleArtikelAnzeigen()); // Fügt dem Button einen ActionListener hinzu, der die Methode alleArtikelAnzeigen() aufruft, wenn der Button geklickt wird
        buttonPanel.add(artikelAnzeigenButton, gbc); // Fügt den Button dem buttonPanel hinzu und verwendet dabei die zuvor definierten GridBagConstraints (gbc) zur Positionierung

        // Aktualisiert Zeilenposition für den nächsten Button
        gbc.gridy++; // Eine Zeile weiter

        // Zweiter Button
        JButton warenkorbButton = new JButton("Warenkorb anzeigen"); // Erstellt JButton mit Beschriftung "Warenkorb anzeigen"
        warenkorbButton.addActionListener(e -> warenkorbAnzeigen()); // Fügt dem Button einen ActionListener hinzu, der die Methode warenkorbAnzeigen() aufruft, wenn der Button geklickt wird
        buttonPanel.add(warenkorbButton, gbc); // Fügt den Button dem buttonPanel hinzu und verwendet dabei die zuvor definierten GridBagConstraints (gbc) zur Positionierung

        // Aktualisiert Zeilenposition für den nächsten Button
        gbc.gridy++; // Eine Zeile weiter

        JButton warenkorbLeerenButton = new JButton("Warenkorb leeren"); // Erstellt JButton mit Beschriftung "Warenkorb leeren"
        warenkorbLeerenButton.addActionListener(e -> warenkorbLeeren()); // Fügt dem Button einen ActionListener hinzu, der die Methode warenkorbLeeren() aufruft, wenn der Button geklickt wird
        buttonPanel.add(warenkorbLeerenButton, gbc); // Fügt den Button dem buttonPanel hinzu und verwendet dabei die zuvor definierten GridBagConstraints (gbc) zur Positionierung

        // Aktualisiert Zeilenposition für den nächsten Button
        gbc.gridy++; // Eine Zeile weiter

        JButton artikelHinzufuegenButton = new JButton("Artikel hinzufügen"); // Erstellt JButton mit Beschriftung "Artikel hinzufügen"
        artikelHinzufuegenButton.addActionListener(e -> artikelHinzufuegen()); // Fügt dem Button einen ActionListener hinzu, der die Methode artikelHinzufuegen() aufruft, wenn der Button geklickt wird
        buttonPanel.add(artikelHinzufuegenButton, gbc); // Fügt den Button dem buttonPanel hinzu und verwendet dabei die zuvor definierten GridBagConstraints (gbc) zur Positionierung

        // Aktualisiert Zeilenposition für den nächsten Button
        gbc.gridy++; // Eine Zeile weiter

        JButton artikelEntfernenButton = new JButton("Artikel entfernen"); // Erstellt JButton mit Beschriftung "Artikel entfernen"
        artikelEntfernenButton.addActionListener(e -> artikelEntfernen()); // Fügt dem Button einen ActionListener hinzu, der die Methode artikelEntfernen() aufruft, wenn der Button geklickt wird
        buttonPanel.add(artikelEntfernenButton, gbc); // Fügt den Button dem buttonPanel hinzu und verwendet dabei die zuvor definierten GridBagConstraints (gbc) zur Positionierung

        // Aktualisiert Zeilenposition für den nächsten Button
        gbc.gridy++; // Eine Zeile weiter

        JButton mengeAendernButton = new JButton("Menge ändern"); // Erstellt JButton mit Beschriftung "Menge ändern"
        mengeAendernButton.addActionListener(e -> mengeAendern()); // Fügt dem Button einen ActionListener hinzu, der die Methode mengeAendern() aufruft, wenn der Button geklickt wird
        buttonPanel.add(mengeAendernButton, gbc); // Fügt den Button dem buttonPanel hinzu und verwendet dabei die zuvor definierten GridBagConstraints (gbc) zur Positionierung

        // Aktualisiert Zeilenposition für den nächsten Button
        gbc.gridy++; // Eine Zeile weiter

        JButton bestellungButton = new JButton("Bestellung aufgeben"); // Erstellt JButton mit Beschriftung "Bestellung aufgeben"
        bestellungButton.addActionListener(e -> bestellungAufgeben()); // Fügt dem Button einen ActionListener hinzu, der die Methode bestellungAufgeben() aufruft, wenn der Button geklickt wird
        buttonPanel.add(bestellungButton, gbc); // Fügt den Button dem buttonPanel hinzu und verwendet dabei die zuvor definierten GridBagConstraints (gbc) zur Positionierung

        // Aktualisiert Zeilenposition für den nächsten Button
        gbc.gridy++; // Eine Zeile weiter

        JButton abmeldenButton = new JButton("Abmelden"); // Erstellt JButton mit Beschriftung "Abmelden"
        abmeldenButton.addActionListener(e -> zurueckZurAnmeldung()); // Fügt dem Button einen ActionListener hinzu, der die Methode zurueckZurAnmeldung() aufruft, wenn der Button geklickt wird
        buttonPanel.add(abmeldenButton, gbc); // Fügt den Button dem buttonPanel hinzu und verwendet dabei die zuvor definierten GridBagConstraints (gbc) zur Positionierung

        // Fügt den Sortieren-Button hinzu
        gbc.gridy++; // Eine Zeile weiter
        JButton sortiereButton = new JButton("Sortieren");
        sortiereButton.addActionListener(e -> sortiereArtikel());
        buttonPanel.add(sortiereButton, gbc);

        // Fügt das Button-Panel auf der rechten Seite des oberen Panels hinzu
        topPanel.add(buttonPanel, BorderLayout.EAST);

        // Fügt das obere Panel zum Hauptpanel hinzu (oben im BorderLayout)
        add(topPanel, BorderLayout.NORTH);

        // Erstellt das Tabellenmodell und die Tabelle zur Anzeige der Artikel
        kundenArtikelTableModel = new KundenArtikelTableModel(new ArrayList<>());
        artikelTable = new JTable(kundenArtikelTableModel);

        // Fügt die Tabelle in eine ScrollPane ein und fügt diese zur Mitte des Hauptpanels hinzu
        JScrollPane scrollPane = new JScrollPane(artikelTable);
        add(scrollPane, BorderLayout.CENTER);
    }



    /**
     * Methode zum Suchen von Artikeln basierend auf dem im Suchfeld eingegebenen Begriff.
     * Aktualisiert die Tabelle mit den Suchergebnissen.
     */
    private void artikelSuchen() {
        if (suchfeld != null) {  // Sicherstellen, dass suchfeld initialisiert ist
            String suchbegriff = suchfeld.getText(); // Holt den Text aus dem Suchfeld
            List<Artikel> suchergebnisse = artikelVW.sucheArtikelNachBegriff(suchbegriff); // Sucht Artikel basierend auf dem Suchbegriff
            kundenArtikelTableModel.setArtikelListe(suchergebnisse); // Aktualisiert das Tabellenmodell mit den Suchergebnissen
        } else {
            System.err.println("Suchfeld ist nicht initialisiert."); // Gibt eine Fehlermeldung aus, wenn das Suchfeld nicht initialisiert ist
        }
    }


    /**
     * Methode zum Anzeigen aller verfügbaren Artikel.
     * Aktualisiert die Tabelle mit der vollständigen Artikelliste.
     */
    private void alleArtikelAnzeigen() {
        // Holt die Liste aller Artikel vom Artikel-Controller
        List<Artikel> artikelListe = new ArrayList<>(artikelVW.listeArtikel());
        // Aktualisiert das Tabellenmodell mit der vollständigen Artikelliste
        kundenArtikelTableModel.setArtikelListe(artikelListe);
    }


    /**
     * Methode zum Sortieren der Artikel basierend auf verschiedenen Kriterien.
     * Zeigt ein Dialogfeld zur Auswahl des Sortierkriteriums und sortiert dann die Artikelliste entsprechend.
     */
    private void sortiereArtikel() {
        // Optionen für das Sortierkriterium
        String[] optionen = {"Preis", "Name", "Farbe", "Größe", "Massengutartikel", "ID"};
        // Dialogfeld zur Auswahl des Sortierkriteriums
        String auswahl = (String) JOptionPane.showInputDialog(this, "Sortieren nach:", "Sortieroptionen", JOptionPane.PLAIN_MESSAGE, null, optionen, optionen[0]);

        // Überprüfen, ob eine Auswahl getroffen wurde
        if (auswahl != null) {
            // Holt die Liste aller Artikel vom Artikel-Controller
            List<Artikel> artikelListe = new ArrayList<>(artikelVW.listeArtikel());
            // Sortiert die Artikelliste basierend auf der getroffenen Auswahl
            switch (auswahl) {
                case "Preis":
                    artikelListe.sort(Comparator.comparing(Artikel::getPreis));
                    break;
                case "Name":
                    artikelListe.sort(Comparator.comparing(Artikel::getName));
                    break;
                case "Farbe":
                    artikelListe.sort(Comparator.comparing(Artikel::getFarbe));
                    break;
                case "Größe":
                    artikelListe.sort(Comparator.comparing(Artikel::getGroesse));
                    break;
                case "Massengutartikel":
                    artikelListe.sort(Comparator.comparing(Artikel::isMassengutartikel).reversed());
                    break;
                case "ID":
                    artikelListe.sort(Comparator.comparingInt(Artikel::getArtikelId));
                    break;
            }
            // Aktualisiert das Tabellenmodell mit der sortierten Artikelliste
            kundenArtikelTableModel.setArtikelListe(artikelListe);
        }
    }


    /**
     * Methode zum Hinzufügen eines ausgewählten Artikels in den Warenkorb.
     * Der Benutzer wird aufgefordert, die Anzahl einzugeben, die er hinzufügen möchte.
     * Wenn genügend Bestand vorhanden ist, wird der Artikel dem Warenkorb hinzugefügt.
     * Andernfalls wird eine Fehlermeldung angezeigt.
     */
    private void artikelHinzufuegen() {
        // Diese Zeile ermittelt die aktuell ausgewählte Zeile in der Tabelle artikelTable.
        int selectedRow = artikelTable.getSelectedRow();

        // Diese Bedingung überprüft, ob eine Zeile in der Tabelle ausgewählt ist. -1 bedeutet, dass keine Zeile ausgewählt ist.
        if (selectedRow != -1) {
            // Holt den Artikel aus dem Tabellenmodell basierend auf der ausgewählten Zeile
            Artikel artikel = kundenArtikelTableModel.getArtikelAt(selectedRow);

            // Zeigt einen Dialog an, um die Anzahl einzugeben, die hinzugefügt werden soll
            String stueckzahlString = JOptionPane.showInputDialog(this, "Anzahl eingeben:");

            // Stellt sicher, dass der Benutzer eine Eingabe gemacht hat und nicht den Dialog abgebrochen hat
            if (stueckzahlString != null) {
                try {
                    // Der Code versucht, die Eingabe (stueckzahlString) in eine Ganzzahl (stueckzahl) zu konvertieren
                    int stueckzahl = Integer.parseInt(stueckzahlString);
                    // Holt den aktuellen Bestand des Artikels
                    int vorhandenerBestand = artikel.getBestand();

                    // Überprüft, ob genügend Bestand verfügbar ist
                    if (stueckzahl > vorhandenerBestand) {
                        // Zeigt eine Fehlermeldung an, wenn nicht genügend Bestand verfügbar ist
                        JOptionPane.showMessageDialog(this, "Nicht genügend Bestand verfügbar. Verfügbarer Bestand: " + vorhandenerBestand, "Fehler", JOptionPane.ERROR_MESSAGE);
                    } else {
                        // Fügt den Artikel mit der angegebenen Stückzahl dem Warenkorb hinzu
                        warenkorb.hinzufuegenArtikelInWarenkorb(artikel, stueckzahl);
                        // Zeigt eine Bestätigungsmeldung an, dass der Artikel hinzugefügt wurde
                        JOptionPane.showMessageDialog(this, "Artikel wurde zum Warenkorb hinzugefügt.");
                    }
                } catch (NumberFormatException e) {
                    // Zeigt eine Fehlermeldung an, wenn die eingegebene Anzahl ungültig ist
                    JOptionPane.showMessageDialog(this, "Ungültige Anzahl eingegeben.", "Fehler", JOptionPane.ERROR_MESSAGE);
                }
            }
        } else {
            // Wenn keine Zeile in der Tabelle ausgewählt ist, wird eine Fehlermeldung angezeigt
            JOptionPane.showMessageDialog(this, "Bitte wählen Sie einen Artikel aus der Tabelle aus.", "Fehler", JOptionPane.ERROR_MESSAGE);
        }
    }



    /**
     * Methode zum Entfernen eines ausgewählten Artikels aus dem Warenkorb.
     * Der Benutzer muss einen Artikel aus der Tabelle auswählen.
     * Wenn der Artikel im Warenkorb vorhanden ist, wird er entfernt.
     * Andernfalls wird eine Fehlermeldung angezeigt.
     */
    private void artikelEntfernen() {
        // Diese Zeile ermittelt die aktuell ausgewählte Zeile in der Tabelle artikelTable.
        int selectedRow = artikelTable.getSelectedRow();

        // Diese Bedingung überprüft, ob eine Zeile in der Tabelle ausgewählt wurde
        if (selectedRow != -1) {
            // Holt den Artikel aus dem Tabellenmodell basierend auf der ausgewählten Zeile
            Artikel artikel = kundenArtikelTableModel.getArtikelAt(selectedRow);

            // Überprüft, ob der Artikel im Warenkorb vorhanden ist
            if (warenkorb.getMenge(artikel) > 0) {
                // Entfernt den Artikel aus dem Warenkorb
                warenkorb.entferneArtikelInWarenkorb(artikel);
                // Zeigt eine Bestätigungsmeldung an, dass der Artikel entfernt wurde
                JOptionPane.showMessageDialog(this, "Artikel wurde aus dem Warenkorb entfernt.");
            } else {
                // Zeigt eine Fehlermeldung an, wenn der Artikel nicht im Warenkorb ist
                JOptionPane.showMessageDialog(this, "Dieser Artikel ist nicht im Warenkorb.", "Fehler", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            // Wenn keine Zeile in der Tabelle ausgewählt ist, wird eine Fehlermeldung angezeigt
            JOptionPane.showMessageDialog(this, "Bitte wählen Sie einen Artikel aus der Tabelle aus.", "Fehler", JOptionPane.ERROR_MESSAGE);
        }
    }



    /**
     * Methode zum Anzeigen des Inhalts des Warenkorbs.
     * Diese Methode erstellt eine textbasierte Anzeige des Warenkorbinhalts
     * und zeigt sie in einem Dialogfenster an.
     */
    private void warenkorbAnzeigen() {
        // Erstellt einen StringBuilder und fügt den Inhalt des Warenkorbs hinzu
        StringBuilder warenkorbInhalt = new StringBuilder(warenkorb.toString());
        // Fügt den Gesamtbetrag des Warenkorbs hinzu
        warenkorbInhalt.append("\nGesamtbetrag: ").append(warenkorb.berechneGesamtImWarenkorb()).append(" EUR");

        // Erstellt ein JTextArea-Objekt mit dem Warenkorbinhalt
        JTextArea textArea = new JTextArea(warenkorbInhalt.toString());//Txtarea=um mehrzeiligen Text in einer GUI-Anwendung anzuzeigen und zu bearbeiten (scrollen automatisch zeilenumbrüche)

        // Setzt das JTextArea-Objekt auf nicht bearbeitbar
        textArea.setEditable(false);

        // Verpackt das JTextArea-Objekt in ein JScrollPane-Objekt (Scrollbar)
        JScrollPane scrollPane = new JScrollPane(textArea);

        // Zeigt eine Dialogbox mit dem ScrollPane und dem Titel "Warenkorb" an
        JOptionPane.showMessageDialog(this, scrollPane, "Warenkorb", JOptionPane.INFORMATION_MESSAGE);
    }


    /**
     * Methode zum Leeren des Warenkorbs.
     * Überprüft, ob der Warenkorb nicht leer ist.
     * Wenn der Warenkorb nicht leer ist, werden alle Artikel daraus entfernt
     * und eine Bestätigungsnachricht wird angezeigt.
     * Wenn der Warenkorb bereits leer ist, wird eine Fehlermeldung angezeigt.
     */
    private void warenkorbLeeren() {
        // Überprüft, ob der Warenkorb nicht leer ist
        if (!warenkorb.getArtikel().isEmpty()) {
            // Leert den Warenkorb, indem alle Artikel daraus entfernt werden
            warenkorb.leerenWarenkorb();
            // Zeigt eine Bestätigungsnachricht an, dass der Warenkorb geleert wurde
            JOptionPane.showMessageDialog(this, "Der Warenkorb wurde geleert.");
        } else {
            // Zeigt eine Fehlermeldung an, dass der Warenkorb bereits leer ist
            JOptionPane.showMessageDialog(this, "Der Warenkorb ist bereits leer.", "Fehler", JOptionPane.ERROR_MESSAGE);
        }
    }


    /**
     * Ändert die Menge eines Artikels im Warenkorb.
     * Überprüft, ob ein Artikel ausgewählt und im Warenkorb vorhanden ist.
     * Fordert den Benutzer auf, eine neue Menge einzugeben und aktualisiert die Menge im Warenkorb.
     * Zeigt entsprechende Fehlermeldungen bei ungültiger Eingabe oder nicht ausreichendem Bestand.
     */
    private void mengeAendern() {
        // Holt die aktuell ausgewählte Zeile aus der Tabelle
        int selectedRow = artikelTable.getSelectedRow();

        // Überprüft, ob eine Zeile ausgewählt ist
        if (selectedRow != -1) {
            // Holt den Artikel aus dem Modell anhand der ausgewählten Zeile
            Artikel artikel = kundenArtikelTableModel.getArtikelAt(selectedRow);

            // Überprüft, ob der Artikel im Warenkorb vorhanden ist
            if (warenkorb.getMenge(artikel) > 0) {
                // Fordert den Benutzer auf, eine neue Menge einzugeben und zeigt die aktuelle Menge an
                String neueMengeStr = JOptionPane.showInputDialog(this, "Neue Menge für " + artikel.getName() + " eingeben:", warenkorb.getMenge(artikel));

                try {
                    // Konvertiert die eingegebene Menge von String zu Integer
                    int neueMenge = Integer.parseInt(neueMengeStr);

                    // Überprüft, ob die neue Menge den verfügbaren Bestand nicht überschreitet
                    if (neueMenge <= artikel.getBestand()) {
                        // Aktualisiert die Menge des Artikels im Warenkorb
                        warenkorb.aktualisiereArtikelStueckzahl(artikel, neueMenge);

                        // Zeigt eine Erfolgsnachricht an
                        JOptionPane.showMessageDialog(this, "Menge aktualisiert!");
                    } else {
                        // Zeigt eine Fehlermeldung an, wenn die gewünschte Menge den Bestand überschreitet
                        JOptionPane.showMessageDialog(this, "Ungültige Menge! Bestand: " + artikel.getBestand(), "Fehler", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (NumberFormatException e) {
                    // Zeigt eine Fehlermeldung an, wenn die eingegebene Menge keine gültige Zahl ist
                    JOptionPane.showMessageDialog(this, "Ungültige Menge!", "Fehler", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                // Zeigt eine Fehlermeldung an, wenn der Artikel nicht im Warenkorb ist
                JOptionPane.showMessageDialog(this, "Dieser Artikel ist nicht im Warenkorb.", "Fehler", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            // Zeigt eine Fehlermeldung an, wenn kein Artikel ausgewählt ist
            JOptionPane.showMessageDialog(this, "Kein Artikel ausgewählt!", "Fehler", JOptionPane.ERROR_MESSAGE);
        }
    }



    /**
     * Methode zum Aufgeben einer Bestellung.
     * Überprüft, ob der Warenkorb leer ist und fordert den Benutzer zur Bestätigung des Kaufs auf.
     * Aktualisiert den Bestand, zeigt die Rechnung an und leert den Warenkorb bei erfolgreicher Bestellung.
     */
    private void bestellungAufgeben() {
        // Überprüfen, ob der Warenkorb leer ist
        if (warenkorb.getArtikelMap().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Der Warenkorb ist leer. Bitte fügen Sie Artikel hinzu, bevor Sie eine Bestellung aufgeben.", "Fehler", JOptionPane.ERROR_MESSAGE);
            return; // Methode beenden, wenn der Warenkorb leer ist
        }

        // Holt den aktuell angemeldeten Kunden
        Kunden kunde = kundenVW.getAngemeldeterKunde();
        if (kunde != null) {
            // Berechnet die Gesamtsumme der Artikel im Warenkorb
            double gesamtsumme = warenkorb.berechneGesamtImWarenkorb();

            // Stellt den Inhalt des Warenkorbs zusammen
            StringBuilder warenkorbInhalt = new StringBuilder();
            // Fügt den Text "Warenkorbinhalt:" und einen Zeilenumbruch zum StringBuilder hinzu
            warenkorbInhalt.append("Warenkorbinhalt:\n");

            // Startet eine Schleife, die durch jedes Eintragspaar (Artikel, Menge) im artikelMap des Warenkorbs iteriert
            for (Map.Entry<Artikel, Integer> entry : warenkorb.getArtikelMap().entrySet()) {
                // Holt den Artikel aus dem aktuellen Eintrag
                Artikel artikel = entry.getKey();
                // Holt die Menge des Artikels aus dem aktuellen Eintrag
                int menge = entry.getValue();
                // Fügt den Artikelnamen und die Artikel-ID zum StringBuilder hinzu
                warenkorbInhalt.append("- ").append(artikel.getName())
                        .append(" (Artikel ID: ").append(artikel.getArtikelId()).append("), Stückzahl: ")
                        // Fügt die Menge und den Preis pro Stück zum StringBuilder hinzu
                        .append(menge).append(", Preis pro Stück: ").append(artikel.getPreis()).append("\n");
            }

            // Fügt den Text "Gesamtbetrag:", die berechnete Gesamtsumme und die Einheit "EUR" zum StringBuilder hinzu
            warenkorbInhalt.append("\nGesamtbetrag: ").append(gesamtsumme).append(" EUR");


            // Fragt den Benutzer, ob er den Kauf bestätigen möchte
            int antwort = JOptionPane.showConfirmDialog(this, warenkorbInhalt.toString() + "\n\nMöchten Sie den Kauf bestätigen?", "Kauf bestätigen", JOptionPane.YES_NO_OPTION);
            if (antwort == JOptionPane.YES_OPTION) {
                // Erstellt eine neue Rechnung für den Kauf
                Rechnung rechnung = new Rechnung(kunde, LocalDate.now(), warenkorb.getArtikelMap(), gesamtsumme);

                // Durchläuft jedes Eintragspaar (Artikel, Menge) im artikelMap des Warenkorbs
                for (Map.Entry<Artikel, Integer> entry : warenkorb.getArtikelMap().entrySet()) {
                    // Holt den Artikel aus dem aktuellen Eintrag
                    Artikel artikel = entry.getKey();
                    // Holt die Menge des Artikels aus dem aktuellen Eintrag
                    int menge = entry.getValue();
                    try {
                        // Aktualisiert den Bestand des Artikels in der Datenbank, indem die gekaufte Menge abgezogen wird
                        artikelVW.aktualisiereBestand(artikel.getArtikelId(), -menge);
                    } catch (ArtikelNichtGefundenException e) {
                        // Zeigt eine Fehlermeldung an, wenn der Artikel nicht in der Datenbank gefunden wird
                        JOptionPane.showMessageDialog(this, "Artikel nicht gefunden: " + artikel.getName(), "Fehler", JOptionPane.ERROR_MESSAGE);
                    }
                }
                // Leert den Warenkorb nach der Bestellung
                warenkorb.leerenWarenkorb();


                // Zeigt die Rechnung an
                JTextArea textArea = new JTextArea(rechnung.toString());
                textArea.setEditable(false); // Setzt das JTextArea-Objekt auf nicht bearbeitbar
                JScrollPane scrollPane = new JScrollPane(textArea); // Verpackt das JTextArea-Objekt in ein JScrollPane-Objekt
                JOptionPane.showMessageDialog(this, scrollPane, "Rechnung", JOptionPane.INFORMATION_MESSAGE);

                // Aktualisiert die Artikelanzeige
                alleArtikelAnzeigen();
            } else {
                // Benachrichtigt den Benutzer, dass der Kauf abgebrochen wurde
                JOptionPane.showMessageDialog(this, "Der Kauf wurde abgebrochen.");
            }
        } else {
            // Zeigt eine Fehlermeldung an, wenn kein Kunde angemeldet ist
            JOptionPane.showMessageDialog(this, "Kein Kunde angemeldet!", "Fehler", JOptionPane.ERROR_MESSAGE);
        }
    }



    // Methode zur Rückkehr zum Anmeldepanel
    private void zurueckZurAnmeldung() {
        // Ruft die Methode zurueckZurAnmeldung() im mainFrame auf, um das Anmeldepanel anzuzeigen
        mainFrame.zurueckZurAnmeldung();
    }

}
