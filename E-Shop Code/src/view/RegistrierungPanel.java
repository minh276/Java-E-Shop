package src.view;

import src.controller.KundenVW;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RegistrierungPanel extends JPanel {
    private KundenVW kundenVW;
    private JTextField nameField;
    private JTextField emailField;
    private JTextField benutzernameField;
    private JPasswordField passwortField;
    private JTextField adresseField;
    private GUI mainFrame;  // Referenz auf das Hauptfenster

    // Konstruktor für das RegistrierungPanel
    public RegistrierungPanel(KundenVW kundenVW, GUI mainFrame) {
        this.kundenVW = kundenVW;        // Initialisierung der Kundenverwaltung
        this.mainFrame = mainFrame;      // Initialisierung des Hauptfensters
        initializeUI();                  // UI-Initialisierung aufrufen
    }


    /**
     * Initialisiert die Benutzeroberfläche für das Registrierungsformular.
     * Setzt das Layout auf GridBagLayout für zentrale Platzierung der Komponenten.
     * Erstellt Eingabefelder für Name, E-Mail, Benutzername, Passwort und Adresse.
     * Fügt Labels und Textfelder zum Panel hinzu und konfiguriert deren Platzierung und Ankerpunkte.
     */
    private void initializeUI() {
        setLayout(new GridBagLayout()); // Setzt das Layout auf GridBagLayout für zentrale Platzierung

        JPanel fieldsPanel = new JPanel(); // Panel für die Eingabefelder
        fieldsPanel.setLayout(new GridBagLayout()); // Setzt das Layout des Felds-Panels auf GridBagLayout
        GridBagConstraints gbc = new GridBagConstraints(); // Erstellt ein GridBagConstraint-Objekt für die Komponentenpositionierung
        gbc.insets = new Insets(5, 5, 5, 5); // Setzt den Innenabstand der Komponenten

        gbc.gridx = 0; // X-Position im Grid für das Label "Name"
        gbc.gridy = 0; // Y-Position im Grid für das Label "Name"
        gbc.anchor = GridBagConstraints.EAST; // Ankerpunkt für das Label "Name" auf der rechten Seite
        fieldsPanel.add(new JLabel("Name:"), gbc); // Fügt das Label "Name" zum Felds-Panel hinzu

        gbc.gridx = 1; // X-Position im Grid für das Textfeld "Name"
        gbc.anchor = GridBagConstraints.WEST; // Ankerpunkt für das Textfeld "Name" auf der linken Seite
        nameField = new JTextField(20); // Erstellt ein Textfeld mit einer Breite von 20 Zeichen für den Namen
        fieldsPanel.add(nameField, gbc); // Fügt das Textfeld "Name" zum Felds-Panel hinzu

        gbc.gridx = 0; // X-Position im Grid für das Label "E-Mail"
        gbc.gridy = 1; // Y-Position im Grid für das Label "E-Mail"
        gbc.anchor = GridBagConstraints.EAST; // Ankerpunkt für das Label "E-Mail" auf der rechten Seite
        fieldsPanel.add(new JLabel("E-Mail:"), gbc); // Fügt das Label "E-Mail" zum Felds-Panel hinzu

        gbc.gridx = 1; // X-Position im Grid für das Textfeld "E-Mail"
        gbc.anchor = GridBagConstraints.WEST; // Ankerpunkt für das Textfeld "E-Mail" auf der linken Seite
        emailField = new JTextField(20); // Erstellt ein Textfeld mit einer Breite von 20 Zeichen für die E-Mail
        fieldsPanel.add(emailField, gbc); // Fügt das Textfeld "E-Mail" zum Felds-Panel hinzu

        gbc.gridx = 0; // X-Position im Grid für das Label "Benutzername"
        gbc.gridy = 2; // Y-Position im Grid für das Label "Benutzername"
        gbc.anchor = GridBagConstraints.EAST; // Ankerpunkt für das Label "Benutzername" auf der rechten Seite
        fieldsPanel.add(new JLabel("Benutzername:"), gbc); // Fügt das Label "Benutzername" zum Felds-Panel hinzu

        gbc.gridx = 1; // X-Position im Grid für das Textfeld "Benutzername"
        gbc.anchor = GridBagConstraints.WEST; // Ankerpunkt für das Textfeld "Benutzername" auf der linken Seite
        benutzernameField = new JTextField(20); // Erstellt ein Textfeld mit einer Breite von 20 Zeichen für den Benutzernamen
        fieldsPanel.add(benutzernameField, gbc); // Fügt das Textfeld "Benutzername" zum Felds-Panel hinzu

        gbc.gridx = 0; // X-Position im Grid für das Label "Passwort"
        gbc.gridy = 3; // Y-Position im Grid für das Label "Passwort"
        gbc.anchor = GridBagConstraints.EAST; // Ankerpunkt für das Label "Passwort" auf der rechten Seite
        fieldsPanel.add(new JLabel("Passwort:"), gbc); // Fügt das Label "Passwort" zum Felds-Panel hinzu

        gbc.gridx = 1; // X-Position im Grid für das Passwortfeld
        gbc.anchor = GridBagConstraints.WEST; // Ankerpunkt für das Passwortfeld auf der linken Seite
        passwortField = new JPasswordField(20); // Erstellt ein Passwortfeld mit einer Breite von 20 Zeichen für das Passwort
        fieldsPanel.add(passwortField, gbc); // Fügt das Passwortfeld zum Felds-Panel hinzu

        gbc.gridx = 0; // X-Position im Grid für das Label "Adresse"
        gbc.gridy = 4; // Y-Position im Grid für das Label "Adresse"
        gbc.anchor = GridBagConstraints.EAST; // Ankerpunkt für das Label "Adresse" auf der rechten Seite
        fieldsPanel.add(new JLabel("Adresse:"), gbc); // Fügt das Label "Adresse" zum Felds-Panel hinzu

        gbc.gridx = 1; // X-Position im Grid für das Textfeld "Adresse"
        gbc.anchor = GridBagConstraints.WEST; // Ankerpunkt für das Textfeld "Adresse" auf der linken Seite
        adresseField = new JTextField(20); // Erstellt ein Textfeld mit einer Breite von 20 Zeichen für die Adresse
        fieldsPanel.add(adresseField, gbc); // Fügt das Textfeld "Adresse" zum Felds-Panel hinzu


        // Hauptpanel, das alles mittig im Hauptfenster platziert
        JPanel mainPanel = new JPanel(); // Erstellt ein neues JPanel für die Hauptanzeige
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS)); // Setzt das Layout des Hauptpanels auf ein vertikales BoxLayout

        mainPanel.add(Box.createVerticalGlue()); // Fügt vertikale Platzhalter hinzu, um die Panels zentriert zu positionieren
        mainPanel.add(fieldsPanel); // Fügt das fieldsPanel (mit den Eingabefeldern) zum Hauptpanel hinzu
        mainPanel.add(Box.createVerticalGlue()); // Fügt erneut einen vertikalen Platzhalter hinzu

        // Erstellt ein JPanel für die Buttons mit einem BorderLayout
        JPanel buttonPanel = new JPanel(new BorderLayout());

        // Erstellt einen "Abbrechen"-Button und fügt einen ActionListener hinzu, der zum Anmeldepanel zurückkehrt
        JButton cancelButton = new JButton("Abbrechen");
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainFrame.zurueckZurAnmeldung(); // Ruft die Methode zurueckZurAnmeldung() im Hauptfenster auf
            }
        });
        buttonPanel.add(cancelButton, BorderLayout.WEST); // Fügt den "Abbrechen"-Button zum linken Bereich des buttonPanels hinzu

        // Erstellt einen "Registrieren"-Button und fügt einen ActionListener hinzu, der die Methode registrieren() aufruft
        JButton registerButton = new JButton("Registrieren");
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                registrieren(); // Ruft die Methode registrieren() auf, um die Registrierung zu starten
            }
        });
        buttonPanel.add(registerButton, BorderLayout.EAST); // Fügt den "Registrieren"-Button zum rechten Bereich des buttonPanels hinzu

        mainPanel.add(buttonPanel); // Fügt das buttonPanel zum Hauptpanel hinzu

        // Fügt das mainPanel zentriert zum Registrierungspanel hinzu
        add(mainPanel, new GridBagConstraints());
    }

    /**
     * Versucht, den Kunden zu registrieren und gibt entsprechende Rückmeldungen aus.
     * Holt die Eingaben aus den Textfeldern, überprüft ihre Gültigkeit und zeigt
     * entsprechende Fehlermeldungen an, falls erforderlich. Bei erfolgreicher
     * Registrierung werden die Eingabefelder geleert und zur Anmeldeseite zurückgekehrt.
     */
    private void registrieren() {
        // Holt die Eingaben aus den Textfeldern
        String name = nameField.getText(); // Holt den Namen aus dem Textfeld
        String email = emailField.getText(); // Holt die E-Mail aus dem Textfeld
        String benutzername = benutzernameField.getText(); // Holt den Benutzernamen aus dem Textfeld
        String passwort = new String(passwortField.getPassword()); // Holt das Passwort aus dem Passwortfeld
        String adresse = adresseField.getText(); // Holt die Adresse aus dem Textfeld

        // Überprüft, ob alle Eingabefelder ausgefüllt sind
        if (name.isEmpty() || email.isEmpty() || benutzername.isEmpty() || passwort.isEmpty() || adresse.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Bitte alle Felder ausfüllen!"); // Zeigt eine Fehlermeldung an, wenn nicht alle Felder ausgefüllt sind
            return; // Beendet die Methode, wenn nicht alle Felder ausgefüllt sind
        }

        // Versucht, den Kunden zu registrieren und speichert das Ergebnis
        boolean erfolgreich = kundenVW.registriereKunden(name, email, benutzername, passwort, adresse);

        // Überprüft, ob die Registrierung erfolgreich war
        if (erfolgreich) {
            JOptionPane.showMessageDialog(this, "Registrierung erfolgreich!"); // Zeigt eine Erfolgsmeldung an
            nameField.setText(""); // Leert das Namen Textfeld
            emailField.setText(""); // Leert das E-Mail Textfeld
            benutzernameField.setText(""); // Leert das Benutzername Textfeld
            passwortField.setText(""); // Leert das Passwort Textfeld
            adresseField.setText(""); // Leert das Adresse Textfeld
            mainFrame.zurueckZurAnmeldung(); // Wechselt zurück zum Anmeldepanel im Hauptfenster
        } else {
            JOptionPane.showMessageDialog(this, "Registrierung fehlgeschlagen! Benutzername existiert bereits."); // Zeigt eine Fehlermeldung an, wenn die Registrierung fehlschlägt
        }
    }
}
