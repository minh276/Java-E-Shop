package src.view;

import src.controller.KundenVW;
import src.controller.MitarbeiterVW;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

public class AnmeldePanel extends JPanel {
    private KundenVW kundenVW; // Deklaration der Instanzvariablen
    private MitarbeiterVW mitarbeiterVW;
    private GUI mainFrame;

    // Eingabefelder für Benutzername und Passwort
    private JTextField benutzernameField;
    private JPasswordField passwortField;

    // Erstellung der Radiobuttons
    private JRadioButton kundenRadioButton;
    private JRadioButton mitarbeiterRadioButton;
    // Erstellung der ButtonGroup (Verwendung bei Optionen, bei denen eine exklusive Auswahl notwendig ist - Benutzerauswahl)
    private ButtonGroup userTypeGroup;

    // Schaltflächen
    private JButton registerButton;
    private JButton loginButton;


    /**
     * Konstruktor für die Klasse AnmeldePanel.
     * Initialisiert das AnmeldePanel mit den gegebenen Verwaltungsobjekten und dem Hauptframe.
     * parameter: kundenVW Die Kundenverwaltung, die verwendet wird, um Kundenoperationen durchzuführen.
     * parameter: mitarbeiterVW Die Mitarbeiterverwaltung, die verwendet wird, um Mitarbeiteroperationen durchzuführen.
     * parameter: mainFrame Das Hauptframe der GUI, in dem das AnmeldePanel angezeigt wird.
     */
    public AnmeldePanel(KundenVW kundenVW, MitarbeiterVW mitarbeiterVW, GUI mainFrame) {
        this.kundenVW = kundenVW; // Initialisierung der Instanzvariablen für die Kundenverwaltung
        this.mitarbeiterVW = mitarbeiterVW; // Initialisierung der Instanzvariablen für die Mitarbeiterverwaltung
        this.mainFrame = mainFrame; // Initialisierung der Instanzvariablen für das Hauptframe der GUI
        initializeUI(); // Aufruf der Methode zur Initialisierung der Benutzeroberfläche
    }


    // Methode zur Initialisierung der Benutzeroberfläche
    private void initializeUI() {
        // ermöglicht die Anordnung von Komponenten in fünf Bereichen: Nord, Süd, Ost, West und Mitte.
        setLayout(new GridBagLayout()); // Setzt das Layout des AnmeldePanels auf GridBagLayout für zentrierte Platzierung

        // Panel zur Eingabe von Benutzername und Passwort
        JPanel inputPanel = new JPanel(); // erstellt ein neues JPanel namens inputPanel
        inputPanel.setLayout(new GridBagLayout()); // Setzt das Layout des inputPanels auf GridBagLayout
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5); // Setzt Abstände zwischen den Komponenten

        // Panel für die Auswahl zwischen Kunde und Mitarbeiter
        JPanel radioPanel = new JPanel(new FlowLayout(FlowLayout.LEFT)); //Erstellt ein neues JPanel namens radioPanel und setzt das Layout auf FlowLayout mit linker Ausrichtung

        // Erstellung der RadioButtons
        kundenRadioButton = new JRadioButton("Als Kunde anmelden");
        mitarbeiterRadioButton = new JRadioButton("Als Mitarbeiter anmelden");

        // Gruppieren der RadioButtons in einer ButtonGroup
        userTypeGroup = new ButtonGroup(); // Eine ButtonGroup namens userTypeGroup wird erstellt.
        userTypeGroup.add(kundenRadioButton); // werden der ButtonGroup hinzugefügt.
        userTypeGroup.add(mitarbeiterRadioButton); // werden der ButtonGroup hinzugefügt.

        kundenRadioButton.setSelected(true); // Standardauswahl ist "Kunde"

        // Hinzufügen der RadioButtons zum radioPanel
        radioPanel.add(kundenRadioButton);
        radioPanel.add(mitarbeiterRadioButton);

        // Hinzufügen der RadioButtons zum inputPanel
        gbc.gridx = 0; // Setzt die x-Position des GridBagConstraints auf 0 (erste Spalte)
        gbc.gridy = 0; // Setzt die y-Position des GridBagConstraints auf 0 (erste Zeile)
        gbc.gridwidth = 2; // Setzt die Breite des GridBagConstraints auf 2 Spalten
        gbc.anchor = GridBagConstraints.WEST; // Verankert das Element am westlichen (linken) Rand des verfügbaren Platzes
        inputPanel.add(radioPanel, gbc); // Fügt das radioPanel dem inputPanel mit den spezifizierten GridBagConstraints hinzu

        gbc.gridwidth = 1;// Setzt die Breite des GridBagConstraints auf 1 Spalte
        gbc.gridx = 0;// Setzt die x-Position des GridBagConstraints auf 0 (erste Spalte)
        gbc.gridy = 1;// Setzt die y-Position des GridBagConstraints auf 1 (zweite Zeile)
        gbc.anchor = GridBagConstraints.EAST;// Verankert das Element am östlichen (rechten) Rand des verfügbaren Platzes
        inputPanel.add(new JLabel("Benutzername:"), gbc); // Fügt ein JLabel mit der Beschriftung "Benutzername:" dem inputPanel mit den spezifizierten GridBagConstraints hinzu

        gbc.gridx = 1;// Setzt die x-Position des GridBagConstraints auf 1 (zweite Spalte)
        gbc.anchor = GridBagConstraints.WEST;// Verankert das Element am westlichen (linken) Rand des verfügbaren Platzes
        benutzernameField = new JTextField(20); // // Ein JTextField namens benutzernameField wird erstellt, das 20 Spalten breit ist
        inputPanel.add(benutzernameField, gbc);// Fügt das benutzernameField dem inputPanel mit den spezifizierten GridBagConstraints hinzu

        gbc.gridx = 0; // Setzt die x-Position des GridBagConstraints auf 0 (erste Spalte)
        gbc.gridy = 2; // Setzt die y-Position des GridBagConstraints auf 2 (dritte Zeile)
        gbc.anchor = GridBagConstraints.EAST; // Verankert das Element am östlichen (rechten) Rand des verfügbaren Platzes
        inputPanel.add(new JLabel("Passwort:"), gbc); // Ein JLabel mit der Beschriftung "Passwort:" wird zum inputPanel hinzugefügt.

        gbc.gridx = 1;// Setzt die x-Position des GridBagConstraints auf 1 (zweite Spalte)
        gbc.anchor = GridBagConstraints.WEST; // Verankert das Element am westlichen (linken) Rand des verfügbaren Platzes
        passwortField = new JPasswordField(20); // // Ein JPasswordField namens passwortField wird erstellt, das 20 Spalten breit ist
        inputPanel.add(passwortField, gbc); // Fügt das passwortField dem inputPanel mit den spezifizierten GridBagConstraints hinzu

        // Panel für die Buttons
        JPanel buttonPanel = new JPanel(new BorderLayout()); // Dieses Panel wird die Schaltflächen für "Anmelden" und "Registrieren" enthalten.

        // Panel für den Registrierungs-Button (links ausgerichtet)
        JPanel leftButtonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT)); // JPanel namens leftButtonPanel erstellt und auf FlowLayout mit linker Ausrichtung gesetzt.
        registerButton = new JButton("Registrieren"); // JButton namens registerButton mit Beschriftung "Registrieren" wird erstellt.
        // Ein ActionListener wird dem registerButton hinzugefügt.
        registerButton.addActionListener(new ActionListener() { // Wenn der Button gedrückt wird, wird die Methode mainFrame.zeigeRegistrierungPanel() aufgerufen.
            @Override
            public void actionPerformed(ActionEvent e) {
                mainFrame.zeigeRegistrierungPanel(); // Diese Methode zeigt das Registrierungs-Panel an.
            }
        });
        leftButtonPanel.add(registerButton); // registerButton wird zum leftButtonPanel hinzugefügt.
        buttonPanel.add(leftButtonPanel, BorderLayout.WEST); // leftButtonPanel wird zum westlichen Bereich des buttonPanels hinzugefügt.

        // Panel für den Anmelde-Button (rechts ausgerichtet)
        JPanel rightButtonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        loginButton = new JButton("Anmelden"); // Ein JButton namens loginButton mit der Beschriftung "Anmelden" wird erstellt.

        // Fügt dem loginButton einen ActionListener hinzu
        loginButton.addActionListener(new ActionListener() { // Wenn der Button gedrückt wird, wird die Methode anmelden() aufgerufen.
            @Override
            public void actionPerformed(ActionEvent e) {
                // Diese Methode wird aufgerufen, wenn das Ereignis (der Button-Klick) eintritt
                // 'e' enthält Informationen über das Ereignis
                anmelden();
            }
        });
        // Fügt den loginButton zum rightButtonPanel hinzu
        rightButtonPanel.add(loginButton);
        // Fügt das rightButtonPanel zum östlichen Bereich des buttonPanels hinzu
        buttonPanel.add(rightButtonPanel, BorderLayout.EAST);

        // Hinzufügen eines ItemListeners (ausgewählt oder nicht ausgewählt) zu den RadioButtons, um die Sichtbarkeit des Registrierungs-Buttons zu steuern
        kundenRadioButton.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                // Überprüft, ob der Zustand des ItemEvents auf SELECTED gesetzt ist
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    // Macht den registerButton sichtbar, wenn der kundenRadioButton ausgewählt ist
                    registerButton.setVisible(true);
                }
            }
        });

        // Fügt dem mitarbeiterRadioButton einen ItemListener hinzu
        mitarbeiterRadioButton.addItemListener(new ItemListener() {
            @Override
            // Überprüft, ob der Zustand des ItemEvents auf SELECTED gesetzt ist
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    // Macht den registerButton unsichtbar, wenn der mitarbeiterRadioButton ausgewählt ist
                    registerButton.setVisible(false);
                }
            }
        });

        // Hauptpanel, das alles mittig im Hauptfenster platziert
        JPanel mainPanel = new JPanel(); // Erstellt ein neues JPanel namens mainPanel
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS)); // Setzt das Layout des mainPanel auf BoxLayout mit vertikaler Anordnung (Y-Achse)
        mainPanel.add(Box.createVerticalGlue());// Fügt einen vertikalen Abstandsgummi hinzu, um den Inhalt zentriert zu platzieren
        mainPanel.add(inputPanel); // Fügt das inputPanel zum mainPanel hinzu
        mainPanel.add(Box.createVerticalGlue()); // Fügt einen weiteren vertikalen Abstandsgummi (gleichmäßige verteilung) hinzu, um den Inhalt zentriert zu halten
        mainPanel.add(buttonPanel);// Fügt das buttonPanel zum mainPanel hinzu

        // Füge das mainPanel zum Zentrum des AnmeldePanels hinzu
        add(mainPanel, new GridBagConstraints());
    }

    // Methode zur Verarbeitung der Anmeldeaktion
    private void anmelden() {
        // Benutzername und Passwort aus den Eingabefeldern lesen
        String benutzername = benutzernameField.getText();
        String passwort = new String(passwortField.getPassword());

        // Überprüfung, ob "Kunde" ausgewählt ist
        if (kundenRadioButton.isSelected()) {
            // Anmeldung als Kunde versuchen
            if (kundenVW.anmeldenKunde(benutzername, passwort)) {
                JOptionPane.showMessageDialog(this, "Anmeldung als Kunde erfolgreich!");
                mainFrame.zeigeKundenMenue();// Zeigt das Kundenmenü an
            } else {
                // Fehlgeschlagene Anmeldung als Kunde
                JOptionPane.showMessageDialog(this, "Anmeldung als Kunde fehlgeschlagen!");
            }
            // Überprüfung, ob "Mitarbeiter" ausgewählt ist
        } else if (mitarbeiterRadioButton.isSelected()) {
            // Anmeldung als Mitarbeiter versuchen
            if (mitarbeiterVW.anmeldenMitarbeiter(benutzername, passwort)) {
                // Erfolgreiche Anmeldung als Mitarbeiter
                JOptionPane.showMessageDialog(this, "Anmeldung als Mitarbeiter erfolgreich!");
                mainFrame.zeigeMitarbeiterMenue();// Zeigt das Mitarbeitermenü an
            } else {
                // Fehlgeschlagene Anmeldung als Mitarbeiter
                JOptionPane.showMessageDialog(this, "Anmeldung als Mitarbeiter fehlgeschlagen!");
            }
        }
    }
}
