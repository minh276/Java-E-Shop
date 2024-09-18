package src.view;

import java.awt.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import javax.swing.*;
import src.controller.ArtikelVW;
import src.controller.KundenVW;
import src.controller.MitarbeiterVW;
import src.exceptions.ArtikelNichtGefundenException;
import src.models.*;
import java.util.Collections;

public class MitarbeiterMenuPanel extends JPanel {
  private ArtikelVW artikelVW;
  private MitarbeiterVW mitarbeiterVW;
  private KundenVW kundenVW;
  private GUI mainFrame;

  private JPanel cards;
  private JTable artikelTable;
  private ArtikelTableModel artikelTableModel;
  private JTable mitarbeiterTable;
  private MitarbeiterTableModel mitarbeiterTableModel;
  private JTable kundenTable;
  private KundenTableModel kundenTableModel;

  private JTextField suchfeld;
  private JButton suchenButton;
  private JComboBox<String> suchOptionenBox;

  private JPanel produktMenuPanel;
  private JPanel benutzerMenuPanel;
  private JPanel menuContainerPanel;

  // Konstruktor für das MitarbeiterMenuPanel
  public MitarbeiterMenuPanel(ArtikelVW artikelVW, MitarbeiterVW mitarbeiterVW, KundenVW kundenVW, GUI mainFrame) {
    this.artikelVW = artikelVW; // Initialisiert das Artikel-Verwaltungsobjekt
    this.mitarbeiterVW = mitarbeiterVW; // Initialisiert das Mitarbeiter-Verwaltungsobjekt
    this.kundenVW = kundenVW; // Initialisiert das Kunden-Verwaltungsobjekt
    this.mainFrame = mainFrame; // Initialisiert das Hauptfenster der GUI

    initializeUI(); // Ruft die Methode zur Initialisierung der Benutzeroberfläche auf

  }


  // Initialisiert die Benutzeroberfläche des MitarbeiterMenuPanels
  private void initializeUI() {
    // Setzt das Layout des Panels auf BorderLayout
    setLayout(new BorderLayout());

    // Erstellt ein Panel mit CardLayout für das Menü
    menuContainerPanel = new JPanel(new CardLayout());
    erstelleProduktMenuPanel(); // Ruft die Methode zur Erstellung des Produktmenüs auf
    erstelleBenutzerMenuPanel(); // Ruft die Methode zur Erstellung des Benutzermenüs auf

    // Erstellt das obere Panel mit BorderLayout
    JPanel topPanel = new JPanel(new BorderLayout());

    // Erstellt das Suchpanel mit FlowLayout (linksbündig)
    JPanel suchePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
    suchfeld = new JTextField(20); // Erstellt ein Textfeld für die Suche mit einer Breite von 20 Spalten
    suchenButton = new JButton("Suchen"); // Erstellt einen Button mit der Beschriftung "Suchen"
    suchOptionenBox = new JComboBox<>(new String[]{"Artikel", "Mitarbeiter"}); // Erstellt ein Dropdown-Menü mit den Optionen "Artikel" und "Mitarbeiter"
    suchOptionenBox.addActionListener(e -> switchMenuPanel()); // Fügt dem Dropdown-Menü einen ActionListener hinzu, der die Methode switchMenuPanel() aufruft

    // Fügt dem Suchbutton einen ActionListener hinzu, der auf Klickereignisse reagiert
    suchenButton.addActionListener(
            // Lambda-Ausdruck, der eine Methode mit einem ActionEvent-Parameter definiert
            e -> {
              // Holt die aktuell ausgewählte Option aus dem Dropdown-Menü
              String option = (String) suchOptionenBox.getSelectedItem();
              // Überprüft, ob die Option "Artikel" ist und ruft dann die Methode zur Artikelsuche auf
              if (option.equals("Artikel")) {
                artikelSuchen();
                // Überprüft, ob die Option "Mitarbeiter" ist und ruft dann die Methode zur Mitarbeitersuche auf
              } else if (option.equals("Mitarbeiter")) {
                mitarbeiterSuchen();
              }
            }
    );

    // Fügt dem Suchpanel ein Label "Suchbegriff:" hinzu
    suchePanel.add(new JLabel("Suchbegriff:"));
    // Fügt dem Suchpanel das Textfeld für die Eingabe hinzu
    suchePanel.add(suchfeld);
    // Fügt dem Suchpanel den Suchbutton hinzu
    suchePanel.add(suchenButton);
    // Fügt dem Suchpanel das Dropdown-Menü für die Suchoptionen hinzu
    suchePanel.add(suchOptionenBox);
    // Fügt das Suchpanel zum oberen Teil des Top-Panels hinzu und positioniert es im Norden
    topPanel.add(suchePanel, BorderLayout.NORTH);

    // Fügt das Menücontainer-Panel zum zentralen Bereich des Top-Panels hinzu
    topPanel.add(menuContainerPanel, BorderLayout.CENTER);
    // Fügt das Top-Panel zum westlichen Bereich des Hauptpanels hinzu
    add(topPanel, BorderLayout.WEST);


    // Erstellen der Karten für die verschiedenen Tabellen
    // Erstellt ein JPanel mit CardLayout, um mehrere Panels anzuzeigen, die umgeschaltet werden können
    cards = new JPanel(new CardLayout());

    // Erstellt ein Artikel-TableModel und eine JTable, um die Artikeldaten anzuzeigen
    artikelTableModel = new ArtikelTableModel(new ArrayList<>());
    artikelTable = new JTable(artikelTableModel);
    // Verpackt die JTable in ein JScrollPane, um Scrollleisten hinzuzufügen
    JScrollPane artikelScrollPane = new JScrollPane(artikelTable);
    // Fügt das JScrollPane, das die Artikeldaten enthält, zum CardLayout-Panel hinzu und gibt ihm die Bezeichnung "artikel"
    cards.add(artikelScrollPane, "artikel");

    // Erstellt ein Mitarbeiter-TableModel und eine JTable, um die Mitarbeiterdaten anzuzeigen
    mitarbeiterTableModel = new MitarbeiterTableModel(new ArrayList<>());
    mitarbeiterTable = new JTable(mitarbeiterTableModel);
    // Verpackt die JTable in ein JScrollPane, um Scrollleisten hinzuzufügen
    JScrollPane mitarbeiterScrollPane = new JScrollPane(mitarbeiterTable);
    // Fügt das JScrollPane, das die Mitarbeiterdaten enthält, zum CardLayout-Panel hinzu und gibt ihm die Bezeichnung "mitarbeiter"
    cards.add(mitarbeiterScrollPane, "mitarbeiter");

    // Erstellt ein Kunden-TableModel und eine JTable, um die Kundendaten anzuzeigen
    kundenTableModel = new KundenTableModel(new ArrayList<>());
    kundenTable = new JTable(kundenTableModel);
    // Verpackt die JTable in ein JScrollPane, um Scrollleisten hinzuzufügen
    JScrollPane kundenScrollPane = new JScrollPane(kundenTable);
    // Fügt das JScrollPane, das die Kundendaten enthält, zum CardLayout-Panel hinzu und gibt ihm die Bezeichnung "kunden"
    cards.add(kundenScrollPane, "kunden");

    // Fügt das CardLayout-Panel dem Hauptpanel hinzu und positioniert es im Zentrum des BorderLayout
    add(cards, BorderLayout.CENTER);

    // Zeigt standardmäßig das Artikel-Panel an
    switchMenuPanel();
  }


  /**
   * Erstellt das Produktmenü-Panel und fügt es dem menuContainerPanel hinzu.
   * Das Panel enthält verschiedene Buttons für Aktionen, die auf Produkte angewendet werden können.
   */
  private void erstelleProduktMenuPanel() {
    // Erstellt ein neues JPanel für das Produktmenü
    produktMenuPanel = new JPanel();
    // Setzt das Layout des Panels auf ein GridLayout mit 14 Zeilen und 1 Spalte
    produktMenuPanel.setLayout(new GridLayout(14, 1)); // 14 Zeilen für die Buttons, da "Alle Kunden anzeigen" und "Abmelden" hinzugefügt wurden

    // Erstellt einen Button, um alle Artikel anzuzeigen
    JButton alleArtikelAnzeigenButton = new JButton("Alle Artikel anzeigen");
    // Fügt dem Button einen ActionListener hinzu, der die Methode switchToCard mit dem Parameter "artikel" aufruft
    alleArtikelAnzeigenButton.addActionListener(e -> switchToCard("artikel"));
    // Fügt den Button dem Produktmenü-Panel hinzu
    produktMenuPanel.add(alleArtikelAnzeigenButton);

    // Erstellt einen Button, um die Artikel zu sortieren
    JButton sortiereButton = new JButton("Artikel sortieren");
    // Fügt dem Button einen ActionListener hinzu, der zuerst zur "artikel" Card wechselt und dann die Artikel sortiert
    sortiereButton.addActionListener(e -> {
      switchToCard("artikel");
      sortiereArtikel();
    });
    // Fügt den Button dem Produktmenü-Panel hinzu
    produktMenuPanel.add(sortiereButton);

    // Erstellt einen Button, um einen Artikel hinzuzufügen
    JButton artikelHinzufuegenButton = new JButton("Artikel hinzufügen");
    // Fügt dem Button einen ActionListener hinzu, der die Methode artikelHinzufuegen aufruft
    artikelHinzufuegenButton.addActionListener(e -> artikelHinzufuegen());
    // Fügt den Button dem Produktmenü-Panel hinzu
    produktMenuPanel.add(artikelHinzufuegenButton);

    // Erstellt einen Button, um einen Artikel zu entfernen
    JButton artikelEntfernenButton = new JButton("Artikel entfernen");
    // Fügt dem Button einen ActionListener hinzu, der die Methode artikelEntfernen aufruft
    artikelEntfernenButton.addActionListener(e -> artikelEntfernen());
    // Fügt den Button dem Produktmenü-Panel hinzu
    produktMenuPanel.add(artikelEntfernenButton);

    // Erstellt einen Button, um den Bestand eines Artikels zu ändern
    JButton bestandAendernButton = new JButton("Bestand ändern");
    // Fügt dem Button einen ActionListener hinzu, der die Methode bestandAendern aufruft
    bestandAendernButton.addActionListener(e -> bestandAendern());
    // Fügt den Button dem Produktmenü-Panel hinzu
    produktMenuPanel.add(bestandAendernButton);

    // Erstellt einen Button, um die Bestandshistorie eines Artikels anzuzeigen
    JButton bestandshistorieAnzeigenButton = new JButton("Bestandshistorie anzeigen");
    // Fügt dem Button einen ActionListener hinzu, der die Methode bestandshistorieAnzeigen aufruft
    bestandshistorieAnzeigenButton.addActionListener(e -> bestandshistorieAnzeigen());
    // Fügt den Button dem Produktmenü-Panel hinzu
    produktMenuPanel.add(bestandshistorieAnzeigenButton);

    // Erstellt einen Button, um sich abzumelden
    JButton abmeldenButton = new JButton("Abmelden");
    // Fügt dem Button einen ActionListener hinzu, der die Methode zurueckZurAnmeldung im mainFrame aufruft
    abmeldenButton.addActionListener(e -> mainFrame.zurueckZurAnmeldung());
    // Fügt den Button dem Produktmenü-Panel hinzu
    produktMenuPanel.add(abmeldenButton);

    // Fügt das Produktmenü-Panel dem menuContainerPanel hinzu und kennzeichnet es als "produktMenu"
    menuContainerPanel.add(produktMenuPanel, "produktMenu");
  }


  /**
   * Erstellt das Benutzer-Menü-Panel mit den entsprechenden Buttons und ActionListenern.
   */
  private void erstelleBenutzerMenuPanel() {
    // Erstellt ein neues JPanel für das Benutzer-Menü
    benutzerMenuPanel = new JPanel();
    // Setzt das Layout des Panels auf ein GridLayout mit 14 Zeilen
    benutzerMenuPanel.setLayout(new GridLayout(14, 1));

    // Erstellt einen Button mit der Beschriftung "Alle Mitarbeiter anzeigen"
    JButton alleMitarbeiterAnzeigenButton = new JButton("Alle Mitarbeiter anzeigen");
    // Fügt einen ActionListener hinzu, der die Methode switchToCard("mitarbeiter") aufruft, wenn der Button geklickt wird
    alleMitarbeiterAnzeigenButton.addActionListener(e -> switchToCard("mitarbeiter"));
    // Fügt den Button dem Benutzer-Menü-Panel hinzu
    benutzerMenuPanel.add(alleMitarbeiterAnzeigenButton);

    // Erstellt einen Button mit der Beschriftung "Mitarbeiter hinzufügen"
    JButton mitarbeiterHinzufuegenButton = new JButton("Mitarbeiter hinzufügen");
    // Fügt einen ActionListener hinzu, der die Methode mitarbeiterHinzufuegen() aufruft, wenn der Button geklickt wird
    mitarbeiterHinzufuegenButton.addActionListener(e -> mitarbeiterHinzufuegen());
    // Fügt den Button dem Benutzer-Menü-Panel hinzu
    benutzerMenuPanel.add(mitarbeiterHinzufuegenButton);

    // Erstellt einen Button mit der Beschriftung "Mitarbeiter entfernen"
    JButton mitarbeiterEntfernenButton = new JButton("Mitarbeiter entfernen");
    // Fügt einen ActionListener hinzu, der die Methode mitarbeiterEntfernen() aufruft, wenn der Button geklickt wird
    mitarbeiterEntfernenButton.addActionListener(e -> mitarbeiterEntfernen());
    // Fügt den Button dem Benutzer-Menü-Panel hinzu
    benutzerMenuPanel.add(mitarbeiterEntfernenButton);

    // Erstellt einen Button mit der Beschriftung "Alle Kunden anzeigen"
    JButton alleKundenAnzeigenButton = new JButton("Alle Kunden anzeigen");
    // Fügt einen ActionListener hinzu, der die Methode switchToCard("kunden") aufruft, wenn der Button geklickt wird
    alleKundenAnzeigenButton.addActionListener(e -> switchToCard("kunden"));
    // Fügt den Button dem Benutzer-Menü-Panel hinzu
    benutzerMenuPanel.add(alleKundenAnzeigenButton);

    // Erstellt einen Button mit der Beschriftung "Abmelden"
    JButton abmeldenButton = new JButton("Abmelden");
    // Fügt einen ActionListener hinzu, der die Methode mainFrame.zurueckZurAnmeldung() aufruft, wenn der Button geklickt wird
    abmeldenButton.addActionListener(e -> mainFrame.zurueckZurAnmeldung());
    // Fügt den Button dem Benutzer-Menü-Panel hinzu
    benutzerMenuPanel.add(abmeldenButton);

    // Fügt das Benutzer-Menü-Panel dem Menü-Container-Panel hinzu und benennt es "benutzerMenu"
    menuContainerPanel.add(benutzerMenuPanel, "benutzerMenu");
  }


  /**
   * Methode zum Umschalten zwischen den Menü-Panels basierend auf der ausgewählten Option.
   */
  private void switchMenuPanel() {
    // Holt das Layout des menuContainerPanel und castet es zu CardLayout
    CardLayout cl = (CardLayout) (menuContainerPanel.getLayout());
    // Holt die aktuell ausgewählte Option aus der Dropdown-Box
    String option = (String) suchOptionenBox.getSelectedItem();

    // Überprüft, ob die ausgewählte Option "Artikel" ist
    if (option.equals("Artikel")) {
      // Zeigt das Produktmenü-Panel an
      cl.show(menuContainerPanel, "produktMenu");
    } else if (option.equals("Mitarbeiter")) { // Überprüft, ob die ausgewählte Option "Mitarbeiter" ist
      // Zeigt das Benutzermenü-Panel an
      cl.show(menuContainerPanel, "benutzerMenu");
    }
  }


  /**
   * Methode zum Umschalten zwischen den Karten (Panels) basierend auf dem angegebenen Namen der Karte.
   *
   * @param cardName Der Name der Karte, zu der gewechselt werden soll (z.B. "artikel", "mitarbeiter", "kunden").
   */
  private void switchToCard(String cardName) {
    // Holt das Layout des cards-Panels und castet es zu CardLayout
    CardLayout cl = (CardLayout) (cards.getLayout());
    // Zeigt die Karte mit dem Namen cardName an
    cl.show(cards, cardName);

    // Überprüft, ob der Kartenname "artikel" ist
    if (cardName.equals("artikel")) {
      // Zeigt alle Artikel an
      alleArtikelAnzeigen();
    } else if (cardName.equals("mitarbeiter")) { // Überprüft, ob der Kartenname "mitarbeiter" ist
      // Zeigt alle Mitarbeiter an
      mitarbeiterAnzeigen();
    } else if (cardName.equals("kunden")) { // Überprüft, ob der Kartenname "kunden" ist
      // Zeigt alle Kunden an
      alleKundenAnzeigen();
    }
  }


  /**
   * Methode zum Anzeigen aller Artikel in der Tabelle.
   * Holt die aktuelle Liste der Artikel vom ArtikelVW und setzt diese in das ArtikelTableModel.
   */
  private void alleArtikelAnzeigen() {
    // Holt die Liste aller Artikel vom Artikel-Controller (artikelVW)
    List<Artikel> artikelListe = artikelVW.listeArtikel();
    // Setzt die erhaltene Artikel-Liste im ArtikelTableModel, wodurch die Tabelle aktualisiert wird
    artikelTableModel.setArtikelListe(artikelListe);
  }


  /**
   * Methode zur Suche nach Artikeln basierend auf einem Suchbegriff.
   * Wechselt zur Artikel-Ansicht, holt den Suchbegriff aus dem Textfeld und zeigt die Suchergebnisse in der Tabelle an.
   */
  private void artikelSuchen() {
    // Wechselt zur Kartenansicht für Artikel
    switchToCard("artikel");

    // Holt den Suchbegriff aus dem Textfeld
    String suchbegriff = suchfeld.getText();

    // Sucht nach Artikeln basierend auf dem Suchbegriff
    List<Artikel> suchergebnisse = artikelVW.sucheArtikelNachBegriff(suchbegriff);

    // Aktualisiert das ArtikelTableModel mit den Suchergebnissen, wodurch die Tabelle aktualisiert wird
    artikelTableModel.setArtikelListe(suchergebnisse);
  }


  /**
   * Methode zur Suche nach Mitarbeitern basierend auf einem Suchbegriff.
   * Wechselt zur Mitarbeiter-Ansicht, holt den Suchbegriff aus dem Textfeld und zeigt die Suchergebnisse in der Tabelle an.
   */
  private void mitarbeiterSuchen() {
    // Wechselt zur Kartenansicht für Mitarbeiter
    switchToCard("mitarbeiter");

    // Holt den Suchbegriff aus dem Textfeld
    String suchbegriff = suchfeld.getText();

    // Sucht nach Mitarbeitern basierend auf dem Suchbegriff
    List<Mitarbeiter> suchergebnisse = mitarbeiterVW.sucheMitarbeiterNachBegriff(suchbegriff);

    // Aktualisiert das MitarbeiterTableModel mit den Suchergebnissen, wodurch die Tabelle aktualisiert wird
    mitarbeiterTableModel.setMitarbeiterListe(suchergebnisse);
  }


  /**
   * Methode zum Sortieren der Artikel basierend auf verschiedenen Attributen.
   * Zeigt ein Dialogfeld zur Auswahl der Sortieroption an, sortiert die Artikel entsprechend und aktualisiert die Tabelle.
   */
  private void sortiereArtikel() {
    // Definiert die Optionen für die Sortierung
    String[] optionen = {
            "Preis",
            "Name",
            "Farbe",
            "Größe",
            "Massengutartikel",
            "ID",
    };

    // Zeigt ein Eingabedialogfeld zur Auswahl der Sortieroption an
    String auswahl = (String) JOptionPane.showInputDialog(
            this, // Elternkomponente
            "Sortieren nach:", // Nachricht
            "Sortieroptionen", // Titel des Dialogs
            JOptionPane.PLAIN_MESSAGE, // Art des Dialogs  Zeigt ein Dialogfeld ohne Symbol
            null, // Symbol (null = kein Symbol)
            optionen, // Auswahlmöglichkeiten
            optionen[0] // Standardauswahl
    );

    // Überprüft, ob eine Auswahl getroffen wurde
    if (auswahl != null) {
      // Holt die aktuelle Artikelliste vom Artikel-Verwaltungsobjekt
      List<Artikel> artikelListe = new ArrayList<>(artikelVW.listeArtikel());

      // Sortiert die Artikelliste basierend auf der Auswahl
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

      // Aktualisiert das Artikel-Tabellenmodell mit der sortierten Liste
      artikelTableModel.setArtikelListe(artikelListe);
    }
  }


  /**
   * Diese Methode zeigt ein Dialogfenster an, um einen neuen Artikel hinzuzufügen.
   * Die Eingabefelder umfassen Name, Farbe, Größe, Preis, Bestand und optional Packungsgröße für Massengutartikel.
   * Wenn der Benutzer auf "OK" klickt, wird der Artikel erstellt und zur Artikelliste hinzugefügt.
   */
  private void artikelHinzufuegen() {
    // Erstellen der Eingabefelder für die Artikelinformationen
    JTextField nameField = new JTextField();
    JTextField farbeField = new JTextField();
    JTextField groesseField = new JTextField();
    JTextField preisField = new JTextField();
    JTextField bestandField = new JTextField();
    JCheckBox massengutCheckBox = new JCheckBox("Massengut");
    JTextField packungsgroesseField = new JTextField();
    packungsgroesseField.setEnabled(false); // Packungsgroesse-Textfeld zunächst deaktivieren

    // Hinzufügen eines ActionListeners zur Aktivierung/Deaktivierung des Packungsgroesse-Textfelds
    massengutCheckBox.addActionListener(
            e -> packungsgroesseField.setEnabled(massengutCheckBox.isSelected())
    );

    // Erstellen eines Panels für die Anordnung der Eingabefelder im GridLayout
    JPanel panel = new JPanel(new GridLayout(0, 2));
    panel.add(new JLabel("Name:")); // Label und Textfeld für Name
    panel.add(nameField);
    panel.add(new JLabel("Farbe:")); // Label und Textfeld für Farbe
    panel.add(farbeField);
    panel.add(new JLabel("Groesse:")); // Label und Textfeld für Größe
    panel.add(groesseField);
    panel.add(new JLabel("Preis:")); // Label und Textfeld für Preis
    panel.add(preisField);
    panel.add(new JLabel("Bestand:")); // Label und Textfeld für Bestand
    panel.add(bestandField);
    panel.add(massengutCheckBox); // Checkbox für Massengutartikel
    panel.add(new JLabel("Packungsgroesse:")); // Label und Textfeld für Packungsgröße
    panel.add(packungsgroesseField);

    // Anzeigen des Dialogs zur Eingabe der Artikelinformationen
    int result = JOptionPane.showConfirmDialog(
            this,
            panel,
            "Artikel hinzufügen",
            JOptionPane.OK_CANCEL_OPTION
    );

    // Überprüfen, ob der Benutzer auf "OK" geklickt hat
    if (result == JOptionPane.OK_OPTION) {
      // Auslesen der Werte aus den Eingabefeldern
      String name = nameField.getText();
      String farbe = farbeField.getText();
      double groesse = Double.parseDouble(groesseField.getText());
      double preis = Double.parseDouble(preisField.getText());
      int bestand = Integer.parseInt(bestandField.getText());
      boolean massengut = massengutCheckBox.isSelected();
      int packungsgroesse = massengut ? Integer.parseInt(packungsgroesseField.getText()) : 1;

      // Aufruf der Methode zum Hinzufügen eines neuen Artikels
      artikelVW.anlegenArtikel(name, farbe, groesse, preis, bestand, massengut, packungsgroesse);

      // Aktualisieren der Artikelanzeige
      alleArtikelAnzeigen();
    }
  }


  /**
   * Diese Methode entfernt einen ausgewählten Artikel aus der Tabelle und der Datenquelle.
   * Wenn der Artikel erfolgreich entfernt wurde, wird die Tabelle aktualisiert.
   * Zeigt Fehlermeldungen an, wenn kein Artikel ausgewählt wurde oder der Artikel nicht gefunden wurde.
   */
  private void artikelEntfernen() {
    // Holt die aktuell ausgewählte Zeile in der Tabelle
    int selectedRow = artikelTable.getSelectedRow();

    // Überprüft, ob eine Zeile ausgewählt ist
    if (selectedRow != -1) {
      // Holt die Artikel-ID aus der ersten Spalte der ausgewählten Zeile
      int artikelId = (int) artikelTable.getValueAt(selectedRow, 0);

      try {
        // Versucht, den Artikel mit der angegebenen ID zu entfernen
        artikelVW.entfernenArtikel(artikelId);

        // Aktualisiert die Anzeige der Artikelliste nach dem Entfernen
        alleArtikelAnzeigen();
      } catch (ArtikelNichtGefundenException e) {
        // Zeigt eine Fehlermeldung an, wenn der Artikel nicht gefunden wurde
        JOptionPane.showMessageDialog(
                this,
                e.getMessage(),
                "Fehler",
                JOptionPane.ERROR_MESSAGE
        );
      }
    } else {
      // Zeigt eine Fehlermeldung an, wenn keine Zeile ausgewählt wurde
      JOptionPane.showMessageDialog(
              this,
              "Bitte wählen Sie einen Artikel aus der Tabelle aus.",
              "Fehler",
              JOptionPane.ERROR_MESSAGE
      );
    }
  }


  /**
   * Ändert den Bestand eines ausgewählten Artikels.
   * Zeigt eine Fehlermeldung an, wenn kein Artikel ausgewählt wurde oder der Artikel nicht gefunden wurde.
   */
  private void bestandAendern() {
    // Überprüft, ob eine Zeile in der Tabelle ausgewählt wurde
    int selectedRow = artikelTable.getSelectedRow();
    if (selectedRow != -1) {
      // Holt die Artikel-ID aus der ersten Spalte der ausgewählten Zeile
      int artikelId = (int) artikelTable.getValueAt(selectedRow, 0);

      // Zeigt ein Eingabefeld an, um den neuen Bestand einzugeben
      String neuerBestandString = JOptionPane.showInputDialog(
              this,
              "Neuer Bestand eingeben:"
      );

      // Überprüft, ob der Benutzer eine Eingabe gemacht hat (nicht abgebrochen)
      if (neuerBestandString != null) {
        // Konvertiert den neuen Bestand von String zu int
        int neuerBestand = Integer.parseInt(neuerBestandString);

        try {
          // Versucht, den Bestand des Artikels zu aktualisieren
          artikelVW.aktualisiereBestand(artikelId, neuerBestand);

          // Aktualisiert die Anzeige der Artikelliste
          alleArtikelAnzeigen();
        } catch (ArtikelNichtGefundenException e) {
          // Zeigt eine Fehlermeldung an, wenn der Artikel nicht gefunden wurde
          JOptionPane.showMessageDialog(
                  this,
                  e.getMessage(),
                  "Fehler",
                  JOptionPane.ERROR_MESSAGE
          );
        }
      }
    } else {
      // Zeigt eine Fehlermeldung an, wenn keine Zeile ausgewählt wurde
      JOptionPane.showMessageDialog(
              this,
              "Bitte wählen Sie einen Artikel aus der Tabelle aus.",
              "Fehler",
              JOptionPane.ERROR_MESSAGE
      );
    }
  }


  /**
   * Zeigt die Liste aller Mitarbeiter in der GUI an.
   */
  private void mitarbeiterAnzeigen() {
    // Holt die Liste aller Mitarbeiter vom MitarbeiterVerwaltung (mitarbeiterVW)
    List<Mitarbeiter> mitarbeiterListe = new ArrayList<>(mitarbeiterVW.getMitarbeiterListe());

    // Setzt die aktualisierte Mitarbeiterliste im MitarbeiterTableModel
    mitarbeiterTableModel.setMitarbeiterListe(mitarbeiterListe);
  }


  // Methode zum Hinzufügen eines neuen Mitarbeiters über ein Eingabefenster
  private void mitarbeiterHinzufuegen() {
    // Textfelder für die Eingabe der Mitarbeiterdaten erstellen
    JTextField nameField = new JTextField();
    JTextField emailField = new JTextField();
    JTextField benutzernameField = new JTextField();
    JTextField passwortField = new JTextField();

    // Panel erstellen, um die Eingabefelder im Gridlayout anzuordnen
    JPanel panel = new JPanel(new GridLayout(0, 2));
    panel.add(new JLabel("Name:"));
    panel.add(nameField);
    panel.add(new JLabel("Email:"));
    panel.add(emailField);
    panel.add(new JLabel("Benutzername:"));
    panel.add(benutzernameField);
    panel.add(new JLabel("Passwort:"));
    panel.add(passwortField);

    // Dialogfenster anzeigen und die Eingabe bestätigen oder abbrechen lassen
    int result = JOptionPane.showConfirmDialog(
            this,
            panel,
            "Mitarbeiter hinzufügen",
            JOptionPane.OK_CANCEL_OPTION
    );

    // Wenn der Benutzer "OK" klickt, die eingegebenen Daten verarbeiten
    if (result == JOptionPane.OK_OPTION) {
      // Daten aus den Textfeldern auslesen
      String name = nameField.getText();
      String email = emailField.getText();
      String benutzername = benutzernameField.getText();
      String passwort = passwortField.getText();

      // Versucht, den Mitarbeiter zu registrieren und fängt eine Ausnahme ab, wenn der Benutzername bereits existiert
      boolean erfolgreich = mitarbeiterVW.registriereMitarbeiter(
              name,
              email,
              benutzername,
              passwort
      );

      // Wenn die Registrierung erfolgreich war, eine Erfolgsmeldung anzeigen und die Mitarbeiterliste aktualisieren
      if (erfolgreich) {
        JOptionPane.showMessageDialog(this, "Mitarbeiter hinzugefügt.");
        mitarbeiterAnzeigen(); // Aktualisieren der Anzeige aller Mitarbeiter
      } else {
        // Wenn der Benutzername bereits existiert, eine Fehlermeldung anzeigen
        JOptionPane.showMessageDialog(
                this,
                "Benutzername existiert bereits!",
                "Fehler",
                JOptionPane.ERROR_MESSAGE
        );
      }
    }
  }


  // Methode zum Entfernen eines Mitarbeiters aus der Tabelle
  private void mitarbeiterEntfernen() {
    // Zeile auswählen, die in der Tabelle ausgewählt wurde
    int selectedRow = mitarbeiterTable.getSelectedRow();

    // Überprüfen, ob eine Zeile ausgewählt wurde
    if (selectedRow != -1) {
      // Mitarbeiter-ID aus der ersten Spalte der ausgewählten Zeile erhalten
      int mitarbeiterId = (int) mitarbeiterTable.getValueAt(selectedRow, 0);

      // Versucht, den Mitarbeiter mit der gegebenen ID zu entfernen
      mitarbeiterVW.entferneMitarbeiter(mitarbeiterId);

      // Erfolgsmeldung anzeigen, dass der Mitarbeiter entfernt wurde
      JOptionPane.showMessageDialog(this, "Mitarbeiter entfernt.");

      // Aktualisiert die Anzeige aller Mitarbeiter
      mitarbeiterAnzeigen();
    } else {
      // Fehlermeldung anzeigen, wenn keine Zeile ausgewählt wurde
      JOptionPane.showMessageDialog(
              this,
              "Bitte wählen Sie einen Mitarbeiter aus der Tabelle aus.",
              "Fehler",
              JOptionPane.ERROR_MESSAGE
      );
    }
  }


  private void alleKundenAnzeigen() {
    // Alle Kunden aus der Kundenverwaltung holen
    List<Kunden> kundenListe = new ArrayList<>(kundenVW.getKundenListe());

    // Die Kundenliste im KundenTableModel setzen, um die Anzeige zu aktualisieren
    kundenTableModel.setKundenListe(kundenListe);
  }


  private void bestandshistorieAnzeigen() {
    int selectedRow = artikelTable.getSelectedRow(); // Zeile in der Tabelle auswählen
    if (selectedRow != -1) { // Wenn eine Zeile ausgewählt ist
      int artikelId = (int) artikelTable.getValueAt(selectedRow, 0); // Artikel-ID aus der ersten Spalte der ausgewählten Zeile erhalten
      try {
        // Bestandshistorie für den ausgewählten Artikel abrufen
        Map<LocalDate, Integer> bestandshistorie = artikelVW.ausgebenBestandshistorie(artikelId);

        // Neues JDialog-Fenster erstellen
        /**SwingUtilities.getWindowAncestor(this) ist nützlich, um das übergeordnete Fenster einer
         Komponente zu finden, damit Dialoge oder andere Fenster zentriert und in einem logischen
         Kontext relativ zum Hauptfenster angezeigt werden können.*/
        JDialog dialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(this), "Bestandshistorie", true);
        dialog.setSize(600, 400); // Größe des Dialogfensters festlegen
        dialog.setLocationRelativeTo(this); // Dialogfenster zentriert positionieren

        // Benutzerdefiniertes JPanel für die Darstellung des Diagramms
        JPanel graphPanel = new JPanel() {

          /**
           * Diese Methode wird aufgerufen, um die Inhalte der Komponente individuell zu zeichnen
           * und anzupassen. Durch den Aufruf von super.paintComponent(g) werden zunächst die
           * grundlegenden Zeichenoperationen ausgeführt. Das Graphics-Objekt g stellt die
           * Zeichenfläche bereit, auf der diese Operationen durchgeführt werden.
           */
          @Override
          protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g;

            int width = getWidth(); // Breite des Panels
            int height = getHeight(); // Höhe des Panels
            int padding = 25; // Randabstand
            int labelPadding = 25; // Beschriftungsabstand
            int pointWidth = 4; // Punktgröße für Achsenticks


            /*
             * Berechnung der Skalierungsfaktoren für das Diagramm:
             * xScale bestimmt die Pixel pro Tag basierend auf der Panelbreite und der Anzahl der Tage (hier angenommen 30 Tage).
             * yScale bestimmt die Pixel pro Bestandseinheit basierend auf der Panelhöhe und dem maximalen Bestand aus der Bestandshistorie.
             */
            double xScale = ((double) width - (2 * padding) - labelPadding) / (30 - 1); // x-Skala für 30 Tage
            double yScale = ((double) height - 2 * padding - labelPadding) / getMaxBestand(bestandshistorie); // y-Skala für den maximalen Bestand

            List<LocalDate> dates = new ArrayList<>(bestandshistorie.keySet()); // Liste der Datumsangaben aus der Bestandshistorie
            Collections.sort(dates); // Datumsangaben sortieren

            // x- und y-Achsen zeichnen
            g2d.drawLine(padding + labelPadding, height - padding - labelPadding, padding + labelPadding, padding); // Zeichnet die y-Achse von der oberen linken Ecke bis zur unteren linken Ecke des Panels
            g2d.drawLine(padding + labelPadding, height - padding - labelPadding, width - padding, height - padding - labelPadding); // Zeichnet die x-Achse von der unteren linken Ecke bis zur unteren rechten Ecke des Panels


            // Beschriftungen für Achsen
            g2d.drawString("Zeit", width / 2, height - padding / 2); // x-Achse Beschriftung
            g2d.drawString("Bestand", padding + 250, padding); // y-Achse Beschriftung

            // Ticks und Zahlen auf der y-Achse zeichnen
            for (int i = 0; i < 10; i++) {
              int x0 = padding + labelPadding; // Startpunkt für den Tick
              int x1 = pointWidth + padding + labelPadding; // Endpunkt für den Tick
              int y0 = height - ((i + 1) * (height - padding * 2 - labelPadding) / 10 + padding + labelPadding); // y-Position für den Tick
              int y1 = y0; // y-Position für den Text
              g2d.drawLine(x0, y0, x1, y1); // Tick zeichnen
              g2d.drawString(Integer.toString((int) ((getMaxBestand(bestandshistorie) * ((i + 1) * 1.0) / 10))), x0 - pointWidth - 2, y0 + (pointWidth / 2)); // Zahl beschriften
            }

            // Ticks und Zahlen auf der x-Achse zeichnen
            for (int i = 0; i < dates.size(); i++) {
              if (i % 2 == 0) { // Nur wenn i durch 2 teilbar ist (d.h. jeden zweiten Tag)
                // Hier wird der Tick und das Datum beschriftet
                int x0 = i * (width - padding * 2 - labelPadding) / (dates.size() - 1) + padding + labelPadding; // x-Position für den Tick
                int x1 = x0; // x-Position für die Textbeschriftung
                int y0 = height - padding - labelPadding; // Startpunkt für den Tick auf der x-Achse
                int y1 = y0 - pointWidth; // Endpunkt für den Tick auf der x-Achse
                g2d.drawLine(x0, y0, x1, y1); // Tick zeichnen
                // Datumsformatierung (nur Tag und Monat)
                String dateLabel = dates.get(i).getDayOfMonth() + "." + dates.get(i).getMonthValue();
                g2d.drawString(dateLabel, x0 - 10, y0 + padding / 2); // Datum beschriften
              }
            }

            // Diagramm zeichnen
            for (int i = 0; i < dates.size() - 1; i++) {
              int x1 = (int) (i * xScale + padding + labelPadding); // Startpunkt der Linie auf der x-Achse
              int y1 = (int) ((getMaxBestand(bestandshistorie) - bestandshistorie.get(dates.get(i))) * yScale + padding); // Startpunkt der Linie auf der y-Achse
              int x2 = (int) ((i + 1) * xScale + padding + labelPadding); // Endpunkt der Linie auf der x-Achse
              int y2 = (int) ((getMaxBestand(bestandshistorie) - bestandshistorie.get(dates.get(i + 1))) * yScale + padding); // Endpunkt der Linie auf der y-Achse

              g2d.drawLine(x1, y1, x2, y2); // Linie zeichnen
            }
          }

          // Methode zum Ermitteln des maximalen Bestands aus der Bestandshistorie
          private int getMaxBestand(Map<LocalDate, Integer> bestandshistorie) {
            int max = Integer.MIN_VALUE; // Initialisierung mit dem kleinsten Integer-Wert
            for (Integer bestand : bestandshistorie.values()) {
              if (bestand > max) {
                max = bestand; // Neuen Maximalwert setzen
              }
            }
            return max; // Maximalen Bestand zurückgeben
          }
        };

        dialog.add(graphPanel); // JPanel dem Dialog hinzufügen
        dialog.setVisible(true); // Dialogfenster sichtbar machen
      } catch (ArtikelNichtGefundenException e) {
        JOptionPane.showMessageDialog(this, e.getMessage(), "Fehler", JOptionPane.ERROR_MESSAGE); // Fehlermeldung anzeigen
      }
    } else {
      JOptionPane.showMessageDialog(this, "Bitte wählen Sie einen Artikel aus der Tabelle aus.", "Fehler", JOptionPane.ERROR_MESSAGE); // Fehlermeldung anzeigen
    }
  }
}

