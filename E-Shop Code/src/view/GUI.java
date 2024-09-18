package src.view;

import src.controller.ArtikelVW;
import src.controller.KundenVW;
import src.controller.MitarbeiterVW;

import javax.swing.*;
import java.awt.*;

/**
 * GUI-Klasse für den eShop.
 * Diese Klasse erstellt das Hauptfenster der Anwendung und verwaltet die verschiedenen Panels.
 */
public class GUI extends JFrame {
    private CardLayout cardLayout; // Layout-Manager für die Kartenanordnung der Panels
    private JPanel mainPanel; // Hauptpanel, das alle anderen Panels enthält

    private ArtikelVW artikelVW; // Artikel-Controller
    private KundenVW kundenVW; // Kunden-Controller
    private MitarbeiterVW mitarbeiterVW; // Mitarbeiter-Controller

    private AnmeldePanel anmeldePanel; // Panel für die Anmeldung
    private RegistrierungPanel registrierungPanel; // Panel für die Registrierung
    private MitarbeiterMenuPanel mitarbeiterMenuPanel; // Panel für das Mitarbeitermenü
    private KundenMenuPanel kundenMenuPanel; // Panel für das Kundenmenü

    /**
     * Konstruktor für die GUI-Klasse.
     * Initialisiert die Controller und die Benutzeroberfläche.
     *
     * @param artikelVW      Der Artikel-Controller.
     * @param kundenVW       Der Kunden-Controller.
     * @param mitarbeiterVW  Der Mitarbeiter-Controller.
     */
    public GUI(ArtikelVW artikelVW, KundenVW kundenVW, MitarbeiterVW mitarbeiterVW) {
        this.artikelVW = artikelVW;
        this.kundenVW = kundenVW;
        this.mitarbeiterVW = mitarbeiterVW;

        initializeUI(); // Initialisiert die Benutzeroberfläche
    }

    /**
     * Initialisiert die Benutzeroberfläche.
     * Setzt die Fensterattribute und fügt die verschiedenen Panels hinzu.
     */
    private void initializeUI() {
        setTitle("eShop"); // Setzt den Fenstertitel
        setSize(800, 600); // Setzt die Fenstergröße
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Beendet die Anwendung beim Schließen des Fensters

        cardLayout = new CardLayout(); // Initialisiert das CardLayout
        mainPanel = new JPanel(cardLayout); // Erstellt das Hauptpanel mit CardLayout

        // Initialisiert die verschiedenen Panels
        anmeldePanel = new AnmeldePanel(kundenVW, mitarbeiterVW, this);
        registrierungPanel = new RegistrierungPanel(kundenVW, this);
        mitarbeiterMenuPanel = new MitarbeiterMenuPanel(artikelVW, mitarbeiterVW, kundenVW, this);
        kundenMenuPanel = new KundenMenuPanel(artikelVW, kundenVW, this);

        // Hinzufügen der Panels zum Hauptpanel
        mainPanel.add(anmeldePanel, "AnmeldePanel"); // Fügt das AnmeldePanel hinzu
        mainPanel.add(registrierungPanel, "RegistrierungPanel"); // Fügt das RegistrierungPanel hinzu
        mainPanel.add(mitarbeiterMenuPanel, "MitarbeiterMenuPanel"); // Fügt das MitarbeiterMenuPanel hinzu
        mainPanel.add(kundenMenuPanel, "KundenMenuPanel"); // Fügt das KundenMenuPanel hinzu

        add(mainPanel); // Fügt das Hauptpanel zum JFrame hinzu
    }

    /**
     * Methode zur Anzeige des Registrierungspanels.
     * Zeigt das Panel für die Registrierung an.
     */
    public void zeigeRegistrierungPanel() {
        cardLayout.show(mainPanel, "RegistrierungPanel"); // Zeigt das RegistrierungPanel an
    }

    /**
     * Methode zur Anzeige des Mitarbeitermenüpanels.
     * Zeigt das Panel für das Mitarbeitermenü an.
     */
    public void zeigeMitarbeiterMenue() {
        cardLayout.show(mainPanel, "MitarbeiterMenuPanel"); // Zeigt das MitarbeiterMenuPanel an
    }

    /**
     * Methode zur Anzeige des Kundenmenüpanels.
     * Zeigt das Panel für das Kundenmenü an.
     */
    public void zeigeKundenMenue() {
        cardLayout.show(mainPanel, "KundenMenuPanel"); // Zeigt das KundenMenuPanel an
    }

    /**
     * Methode zur Rückkehr zum Anmeldepanel.
     * Zeigt das Panel für die Anmeldung an.
     */
    public void zurueckZurAnmeldung() {
        cardLayout.show(mainPanel, "AnmeldePanel"); // Zeigt das AnmeldePanel an
    }
}
