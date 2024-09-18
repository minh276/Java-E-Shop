package src.controller;

import src.view.CUI;
import src.view.GUI;
import src.persistence.FilePersistenceManager;
import src.persistence.PersistenceManager;

import javax.swing.*;

public class EShop {

    private CUI cui;
    private GUI gui;

    public EShop(boolean useGUI) {

        /**
         Deklariert und initialisiert eine Variable 'persistenceManager' vom Typ
          PersistenceManager mit einer Instanz von 'FilePersistenceManager' für die Datenpersistenz.
        */
        PersistenceManager persistenceManager = new FilePersistenceManager();

        /**
         Deklaration und Initialisierung von den Objekten KundenVW, MitarbeiterVW und ArtikelVW
        Jede Verwaltungsobjekt wird mit dem PersistenceManager initialisiert,
        um Datenpersistenz zu ermöglichen.
        */
        KundenVW kundenVW = new KundenVW(persistenceManager);
        MitarbeiterVW mitarbeiterVW = new MitarbeiterVW(persistenceManager);
        ArtikelVW artikelVW = new ArtikelVW(persistenceManager);


        /**
         Überprüft, ob die GUI verwendet werden soll.
         Wenn 'useGUI' wahr ist, wird eine Instanz der GUI-Klasse erstellt und der Instanzvariablen 'gui' zugewiesen.
         Ansonsten wird eine Instanz der CUI-Klasse erstellt und der Instanzvariablen 'cui' zugewiesen.
        */
        if (useGUI) {
            this.gui = new GUI(artikelVW, kundenVW, mitarbeiterVW); // Initialisiert die GUI mit den Verwaltungsobjekten
        } else {
            this.cui = new CUI(mitarbeiterVW, kundenVW, artikelVW, persistenceManager);// Initialisiert die CUI mit den Verwaltungsobjekten und dem PersistenceManager
        }
    }

    public void start() {
        if (gui != null) { // Überprüfen, ob die GUI initialisiert wurde

        /** Starten der GUI in einem separaten Thread, um sicherzustellen, dass
         alle GUI-Operationen auf dem Event-Dispatch-Thread (EDT) ausgeführt werden */
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    gui.setVisible(true); // Sichtbar machen des GUI-Fensters und wird auf dem EDT ausgeführt
                }
            });
        } else if (cui != null) { // Überprüfen, ob die CUI initialisiert wurde
            cui.main(null); // Starten der CUI
        }
    }
}
