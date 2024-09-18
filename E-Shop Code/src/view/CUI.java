package src.view;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import src.controller.ArtikelVW;
import src.controller.KundenVW;
import src.controller.MitarbeiterVW;
import src.exceptions.ArtikelNichtGefundenException;
import src.models.Artikel;
import src.models.Kunden;
import src.models.Massengutartikel;
import src.models.Rechnung;
import src.models.Warenkorb;
import src.persistence.PersistenceManager;



public class CUI {

        // Deklaration der Instanzvariablen für die Verwaltungsobjekte und den Warenkorb
        private MitarbeiterVW mitarbeiterVW;
        private KundenVW kundenVW;
        private ArtikelVW artikelVW;
        private Warenkorb warenkorb;
        private PersistenceManager persistenceManager;

        // Variable, um den Anmeldestatus zu verfolgen
        private boolean loggedIn = false;

        // Konstruktor für die CUI-Klasse, der die Verwaltungsobjekte und den PersistenceManager initialisiert
        public CUI(MitarbeiterVW mitarbeiterVW, KundenVW kundenVW, ArtikelVW artikelVW, PersistenceManager persistenceManager) {
            this.mitarbeiterVW = mitarbeiterVW; // Initialisierung der Mitarbeiterverwaltung
            this.kundenVW = kundenVW; // Initialisierung der Kundenverwaltung
            this.artikelVW = artikelVW; // Initialisierung der Artikelverwaltung
            this.warenkorb = new Warenkorb(); // Initialisierung des Warenkorbs
            this.persistenceManager = persistenceManager; // Initialisierung des PersistenceManagers

            // Laden der Daten beim Start der CUI
            ladeDaten();
            }


        /**
         * Einstiegspunkt der e-Shop-Anwendung.
         * Initialisiert einen Scanner für die Benutzereingabe und richtet eine Schleife ein, um Benutzerinteraktionen zu verarbeiten.
         * Bietet Optionen zum Registrieren, Anmelden oder Beenden der Anwendung.
         * Verwendet einen Shutdown-Hook, um Daten zu speichern, wenn die Anwendung beendet wird.
         * Die Schleife läuft solange, bis sich ein Benutzer erfolgreich anmeldet.
         * parameter: args Kommandozeilenargumente (werden in dieser Anwendung nicht verwendet).
         */

        public void main(String[] args) {
            Scanner scan = new Scanner(System.in); // Scanner für die Benutzereingabe

            // Shutdown-Hook zum Speichern von Daten beim Beenden der Anwendung hinzufügt
            Runtime.getRuntime().addShutdownHook(new Thread(this::speichereDaten));// Shutdown-Hook gewährleistet, dass bestimmte Aktionen ausgeführt werden, bevor die Anwendung vollständig beendet wird

            do {
                System.out.println("Herzlich Willkommen! Was möchten Sie in unserem e-Shop tun?");
                System.out.println("1: Registrieren");
                System.out.println("2: Anmelden");
                System.out.println("3: Beenden");

                String choice = scan.nextLine(); // Benutzereingabe lesen
                switch (choice) {
                    case "1":
                        registrierMenu(scan); // Methode zur Bearbeitung des Registrierungsprozesses aufrufen
                        break;
                    case "2":
                        anmeldeMenu(scan); // Methode zur Bearbeitung des Anmeldevorgangs aufrufen
                        break;
                    case "3":
                        System.out.println("Schönen Tag wünschen wir Ihnen!");// Abschiedsnachricht
                        System.exit(0);// Anwendung beenden
                    default:
                        System.out.println("Ungültige Eingabe!");// Nachricht bei ungültiger Eingabe
                        break;
                }
            } while (!loggedIn);  //Schleife solange durchlaufen, bis sich ein Benutzer erfolgreich anmeldet
        }

    private void ladeDaten() {
        try {
            // Lade Artikel und Lagerereignisse über ArtikelVW
            artikelVW.ladeArtikelUndEreignisse();

            // Lade Mitarbeiterdaten aus der Datei "mitarbeiter.txt"
            persistenceManager.openForReading("mitarbeiter.txt");
            mitarbeiterVW.setMitarbeiterListe(persistenceManager.ladeMitarbeiter()); // Setze die geladenen Mitarbeiter in MitarbeiterVW
            persistenceManager.close();

            // Lade Kundendaten aus der Datei "kunden.txt"
            persistenceManager.openForReading("kunden.txt");
            kundenVW.setKundenListe(persistenceManager.ladeKunden()); // Setze die geladenen Kunden in KundenVW
            persistenceManager.close();
        } catch (IOException e) {
            e.printStackTrace(); // Bei einem Fehler beim Laden der Daten wird der Stacktrace ausgegeben
        }
    }


    private void speichereDaten() {
        // Speichere Artikel und deren Ereignisse über ArtikelVW
        artikelVW.speichereArtikelUndEreignisse();

        try {
            // Speichere Mitarbeiterdaten in die Datei "mitarbeiter.txt"
            persistenceManager.openForWriting("mitarbeiter.txt");
            persistenceManager.speichereMitarbeiter(mitarbeiterVW.getMitarbeiterListe()); // Speichere die Mitarbeiterliste
            persistenceManager.close();

            // Speichere Kundendaten in die Datei "kunden.txt"
            persistenceManager.openForWriting("kunden.txt");
            persistenceManager.speichereKunden(kundenVW.getKundenListe()); // Speichere die Kundenliste
            persistenceManager.close();
        } catch (IOException e) {
            e.printStackTrace(); // Bei einem Fehler beim Schreiben der Daten wird der Stacktrace ausgegeben
        }
    }



    //------------------------------------------regisrtier & anmelde Menu--------------------------------------------------------------------------
    //Kunde Registriert sich hier
    private void registrierMenu(Scanner scan) {
        System.out.print("Name:");
        String name = scan.nextLine();
        System.out.print("E-Mail:");
        String email = scan.nextLine();
        System.out.println("Benutzername (nicht vergessen!):");
        String benutzername = scan.nextLine();
        System.out.println("Passwort (nicht vergessen!):");
        String passwort = scan.nextLine();

        // Ermittlung der nächsten verfügbaren Kunden-ID
        int kundenId = kundenVW.erzeugeEinzigartigeKundenId();

        System.out.println("Deine Kunden-ID: " + kundenId);

        System.out.println("Strasse und Hausnummer:");
        String adresse = scan.nextLine();

        // Versuch, den Kunden zu registrieren
        if (kundenVW.registriereKunden(name, email, benutzername, passwort, adresse)) {
            System.out.println("Registrierung erfolgreich!"); // Ausgabe bei erfolgreicher Registrierung
        } else {
            System.out.println("Registrierung fehlgeschlagen!");// Ausgabe bei fehlgeschlagener Registrierung
        }
    }


    //Kunde oder Mitarbeiter meldet sich hier an
    private void anmeldeMenu(Scanner scan) {
        System.out.print("Möchten Sie sich als Mitarbeiter(1) oder Kunde(2) anmelden? (1/2)\n");
        String userType = scan.nextLine(); // Eingabe der Benutzerauswahl (Mitarbeiter oder Kunde)

        System.out.println("Benutzername:");
        String benutzername = scan.nextLine(); // Eingabe des Benutzernamens

        System.out.println("Passwort:");
        String passwort = scan.nextLine(); // Eingabe des Passworts

        switch (userType) {
            case "1":
                // Anmeldung als Mitarbeiter versuchen
                if (mitarbeiterVW.anmeldenMitarbeiter(benutzername, passwort)) {
                    System.out.println("Anmeldung als Mitarbeiter erfolgreich!");
                    loggedIn = true; // Setze den loggedIn-Status auf true
                    mitarbeiterMenu(scan); // Zeige das Menü für Mitarbeiter
                } else {
                    System.out.println("Anmeldung als Mitarbeiter fehlgeschlagen!");
                }
                break;
            case "2":
                // Anmeldung als Kunde versuchen
                if (kundenVW.anmeldenKunde(benutzername, passwort)) {
                    System.out.println("Anmeldung als Kunde erfolgreich!");
                    loggedIn = true; // Setze den loggedIn-Status auf true
                    kundenMenu(scan); // Zeige das Menü für Kunden
                } else {
                    System.out.println("Anmeldung als Kunde fehlgeschlagen!");
                }
                break;
            default:
                System.out.println("Ungültige Auswahl!"); // Bei ungültiger Benutzerauswahl
                break;
        }
    }
//-------------------------------------------------------------------------------------------------------------------------------------

//-----------------------------------------Mitarbeiter & Kunden Menu-------------------------------------------------------------------------
   
    //MitarbeiterSicht
    private void mitarbeiterMenu(Scanner scan) {
        String choice;
        do {
            System.out.print("Willkommen im Mitarbeiterbereich!\n");
            System.out.print("Was möchten Sie tun?\n");
            System.out.print("1: Alle Artikel aus dem E-Shop anzeigen\n");
            System.out.print("2: Artikel dem E-Shop hinzufügen\n");
            System.out.print("3: Artikel aus dem E-Shop entfernen\n");
            System.out.print("4: Bestand eines Artikels ändern\n");
            System.out.print("5: Mitarbeiter anzeigen\n");
            System.out.print("6: Mitarbeiter hinzufügen\n");
            System.out.print("7: Mitarbeiter entfernen\n");
            System.out.print("8: Alle Kunden anzeigen\n");
            System.out.print("9: Bestandshistorie anzeigen\n");
            System.out.print("10: Beenden\n");
            choice = scan.nextLine();

            switch (choice) {
                case "1":
                    anzeigenArtikel(true);
                    break;
                case "2":
                    anlegenNeuenArtikel(scan);
                    break;
                case "3":
                    entfernenArtikel(scan);
                    break;
                case "4":
                    aendereArtikelBestand(scan);
                    break;
                case "5":
                    zeigAlleMitarbeiter();
                    break;
                case "6":
                    hinzufuegenMitarbeiter(scan);
                    break;
                case "7":
                    entferneMitarbeiter(scan);
                    break;
                    case "8":
                    anzeigenAlleKunden(scan);
                    break;
                    case "9":
                    anzeigenBestandshistorie(scan); 
                    break;
                    case "10":
                    System.out.println("Tschau!");
                    System.exit(0);
                default:
                    // Code, der ausgeführt wird, wenn choice keinen der oben genannten Fälle abdeckt
                    System.out.println("Ungültige Eingabe!");
                    break;
            }
        } while (!choice.equals("10"));// Schleife läuft, solange nicht "10" (Beenden) ausgewählt wurde
    }

    //KundenSicht
    private void kundenMenu(Scanner scan) {
        String choice;
        do {
            System.out.print("Willkommen im KundenMenu!\n");
            System.out.print("Was möchten Sie tun?\n");
            System.out.print("1: Alle Artikel im E-Shop anzeigen\n");
            System.out.print("2: bestimmten Artikel nach dem Namen suchen\n");
            System.out.print("3: Artikel zum Warenkorb hinzufügen\n");
             System.out.print("4: Artikel aus dem Warenkorb entfernen\n");
            System.out.print("5: Warenkorbinhalt anzeigen\n");
            System.out.print("6: Artikelmenge im Warenkorb ändern\n");
            System.out.print("7: Warenkorb leeren\n");
            System.out.print("8: Kasse\n");
            System.out.print("9: Beenden\n");
            choice = scan.nextLine();

            switch (choice) {
                case "1":
                    anzeigenArtikel(false);
                    break;
                case "2":
                    sucheArtikel(scan);
                    break;
                case "3":
                    hinzufuegenArtikelInWarenkorb(scan);
                    break;
                case "4":
                    entferneArtikelImWarenkorb(scan);
                    break;
                case "5":
                    anzeigenWarenkorb();
                    break;
                case "6":
                    aendereArtikelStueckzahlImWaarenkorb(scan);
                    break;
                case "7":
                    leerenWarenkorb();
                    break;
                case "8":
                    checkout(scan);
                    break;
                case "9":
                    System.out.println("Schönen tag wünschen wir Ihnen und wir hoffen wir sehen sie bald wieder!");
                    System.exit(0);
                default:
                    System.out.println("Ungültige Eingabe!");
                    break;
            }
        } while (!choice.equals("9")); // Schleife fortsetzen, solange nicht "9" (Beenden) gewählt wurde
    }
//-------------------------------------------------------------------------------------------------------------------------------------

//--------------------------------------------Methoden der Klasse CUI------------------------------------------------------------------

    /**
     * Methode zum Anzeigen von Artikeln basierend auf dem Benutzertyp (Mitarbeiter oder Kunde).
     * parameter: isMitarbeiter Gibt an, ob der Benutzer ein Mitarbeiter ist (true) oder nicht (false).
     */
    // Methode zum Anzeigen von Artikeln (case 1 Mitarbeiter und Kunde)
    private void anzeigenArtikel(boolean isMitarbeiter) {
        System.out.println("Artikelliste:");// Ausgabe des Titels für die Artikelliste

        // Liste der aktuellen Artikel abrufen
        List<Artikel> aktuelleArtikelListe = artikelVW.listeArtikel();

        // Schleife über alle Artikel in der aktuellen Liste
        for (Artikel artikel : aktuelleArtikelListe) {
            if (isMitarbeiter) {
                // Wenn der Benutzer ein Mitarbeiter ist, zeige alle Details einschließlich des Bestands
                System.out.println(artikel.toString()); //Ausgabe der vollständigen Artikelinformationen für Mitarbeiter
            } else {
                // Wenn der Benutzer ein Kunde ist, zeige die Artikelinformationen ohne den Bestand
                if (artikel instanceof Massengutartikel) {
                    Massengutartikel massengutartikel = (Massengutartikel) artikel;
                    // Ausgabe für Massengutartikel mit Rabattinformationen
                    System.out.println("ArtikelID: " + artikel.getArtikelId() + ", Name: " + artikel.getName() + ", Farbe: " + artikel.getFarbe() + ", Größe: " + artikel.getGroesse() + ", Preis: " + artikel.getPreis()+ " ( 20% Rabatt bei Kauf von einer " + massengutartikel.getPackungsgroesse() + "er Packung)");
                } else {
                    // Ausgabe für Standardartikel ohne Rabattinformationen
                    System.out.println("ArtikelID: " + artikel.getArtikelId() + ", Name: " + artikel.getName() + ", Farbe: " + artikel.getFarbe() + ", Größe: " + artikel.getGroesse() + ", Preis: " + artikel.getPreis());
                }
            }
        }
    }


//Methoden Mitarbeiter Menu--------------------------------------------------------------------------------------------------------

    //Methode um ein neues artikel hinzuzufügen (case 2)
    private void anlegenNeuenArtikel(Scanner scan) {
        System.out.println("Artikelname:");
        String name = scan.nextLine();

        // Eingabe der Farbe des Artikels, mit Validierung auf alphabestische Zeichen
        System.out.println("Farbe:");
        String farbe = scan.nextLine();
        if (!farbe.matches("[a-zA-Z]+")) {
            System.out.println("Ungültige Eingabe! Bitte geben Sie eine gültige Farbe ein");
            return;
        }

        // Eingabe der Größe des Artikels, mit Konvertierung von String nach Double
        System.out.println("Groesse:");
        String groesseStr = scan.nextLine();
        double groesse;
        try {
            groesse = Double.parseDouble(groesseStr); // wandelt den Wert, der als String groesseStr vorliegt, in einen Wert des Typs double um.


        } catch (NumberFormatException e) {
            System.out.println("Ungültige Eingabe! Bitte geben Sie eine gültige Größe ein.");
            return;
        }

        System.out.println("Preis:");
        String preisStr = scan.nextLine();
        double preis;
        try {
            preis = Double.parseDouble(preisStr);// wandelt den Wert, der als String preisStr vorliegt, in einen Wert des Typs double um.
        } catch (NumberFormatException e) {
            System.out.println("Ungültige Eingabe! Bitte geben Sie einen gültigen Preis ein.");
            return;
        }

        System.out.println("Bestand:");
        String bestandStr = scan.nextLine();
        int bestand;
        try {
            bestand = Integer.parseInt(bestandStr);
        } catch (NumberFormatException e) {
            System.out.println("Ungültige Eingabe! Bitte geben Sie einen gültigen Bestand ein.");
            return;
        }

        System.out.println("Ist dies ein Massengutartikel? (ja/nein):");
        String massengutAntwort = scan.nextLine();
        boolean massengut = massengutAntwort.equalsIgnoreCase("ja");
        int packungsgroesse = 1;

        if (massengut) {
            System.out.println("Packungsgroesse:");
            String packungsgroesseStr = scan.nextLine();
            try {
                packungsgroesse = Integer.parseInt(packungsgroesseStr);
            } catch (NumberFormatException e) {
                System.out.println("Ungültige Eingabe! Bitte geben Sie eine gültige Packungsgröße ein.");
                return;
            }
        }

        // Aufruf der Methode in artikelVW, um den neuen Artikel anzulegen und zu speichern
        artikelVW.anlegenArtikel(name, farbe, groesse, preis, bestand, massengut, packungsgroesse);
        System.out.println("Neuer Artikel hinzugefügt: " + name);
    }


    /**
     * Methode zum Entfernen eines Artikels aus dem E-Shop.
     *
     * parameter: scan Scanner-Objekt zum Einlesen der Benutzereingabe.
     */
    //Methode um ein Artikel zu entfernen (case 4)
    private void entfernenArtikel(Scanner scan) {
        System.out.print("Artikelnummer eingeben:\n");

        // Artikelnummer als Zeichenkette einlesen
        String artikelIdStr = scan.nextLine();
        int artikelId;

        try {
            // Versuche, die eingelesene Artikelnummer in einen Integer umzuwandeln
            artikelId = Integer.parseInt(artikelIdStr);
        } catch (NumberFormatException e) {
            // Behandlung, falls die Eingabe keine gültige Zahl ist
            System.out.println("Ungültige Eingabe! Bitte geben Sie eine gültige Artikelnummer ein.");
            return;// Beende die Methode vorzeitig, da die Eingabe ungültig ist
        }

        try {
            // Versuche, den Artikel mit der gegebenen Artikelnummer zu entfernen
            artikelVW.entfernenArtikel(artikelId);
            System.out.println("Artikel entfernt!");// Nachricht wenn der Artikel erfolgreich entfernt wurde
        } catch (ArtikelNichtGefundenException e) {
            // Behandlung, falls der Artikel nicht gefunden wurde
            System.out.println(e.getMessage()); // Gib die Fehlermeldung aus, die von ArtikelNichtGefundenException bereitgestellt wird
        }
    }



    //Methode um Artikel Bestand zu ändern (case 5)
    /**
     * Methode zum Ändern des Bestands eines Artikels im E-Shop.
     * parameter scan Scanner-Objekt zum Einlesen der Benutzereingabe.
     */
    private void aendereArtikelBestand(Scanner scan) {
        try {
            // Benutzer zur Eingabe der Artikel ID auffordern und die Eingabe in einen Integer umwandeln
            System.out.println("Artikel ID eingeben:");
            int artikelId = Integer.parseInt(scan.nextLine());

            // Artikel mit der angegebenen ID suchen
            Artikel artikel = artikelVW.sucheArtikelNachID(artikelId);
            if (artikel == null) {
                // Falls kein Artikel mit der ID gefunden wurde, Fehlermeldung ausgeben und Methode beenden
                System.out.println("Artikel nicht mit der ID gefunden!");
                return;
            }

            // Benutzer zur Eingabe des neuen Bestands auffordern und die Eingabe in einen Integer umwandeln
            System.out.println("Neuer Bestand eingeben:");
            int neuerBestand = Integer.parseInt(scan.nextLine());

            // Aktuellen Bestand des Artikels abrufen
            int aktuellerBestand = artikel.getBestand();

            // Differenz zwischen neuem Bestand und aktuellem Bestand berechnen
            int differenz = neuerBestand - aktuellerBestand;

            artikelVW.aktualisiereBestand(artikelId, differenz); // Bestand und LagerEreignis aktualisieren
            artikelVW.speichereArtikelUndEreignisse();  // Aktualisierte Artikel- und Ereignisdaten speichern
            System.out.println("Bestand des Artikels erfolgreich geändert!");     // Erfolgsmeldung ausgeben

        } catch (NumberFormatException e) {
            // Behandlung, falls die Eingabe keine gültige Zahl ist
            System.out.println("Ungültige Eingabe! Bitte geben Sie eine gültige Nummer ein.");
        } catch (ArtikelNichtGefundenException e) {
            // Behandlung, falls der Artikel nicht gefunden wurde
            System.out.println(e.getMessage()); // Fehlermeldung aus ArtikelNichtGefundenException ausgeben
        }
    }




    //Methode um alle Mitarbeiter auszugeben mit ihren Informationen (case 6)
    private void zeigAlleMitarbeiter() {
        mitarbeiterVW.zeigeAlleMitarbeiterDetails();
    }


    //Methode um einen neuen Mitarbeiter zu registrieren (case 7)
    private void hinzufuegenMitarbeiter(Scanner scan) {
        System.out.println("Name:");
        String name = scan.nextLine();
        System.out.println("Email:");
        String email = scan.nextLine();
        System.out.println("Benutzername:");
        String benutzername = scan.nextLine();
        System.out.println("Passwort:");
        String passwort = scan.nextLine();

        boolean erfolgreich = mitarbeiterVW.registriereMitarbeiter(name, email, benutzername, passwort);
        
        if (erfolgreich) {
            System.out.println("Mitarbeiter hinzugefügt!");
        } else {
            System.out.println("Benutzername existiert bereits!");
        }
    }

    // Methode um einen Mitarbeiter zu entfernen (case 8)
    private void entferneMitarbeiter(Scanner scan) {
        System.out.println("Mitarbeiter entfernen:");
        System.out.println("Mitarbeiternummer:");
        String mitarbeiterIdStr = scan.nextLine();
        int mitarbeiterId;
        try {
            mitarbeiterId = Integer.parseInt(mitarbeiterIdStr);
        } catch (NumberFormatException e) {
            System.out.println("Ungültige Eingabe! Bitte geben Sie eine gültige Mitarbeitennummer ein.");
            return;
        }

        if (mitarbeiterVW.entferneMitarbeiter(mitarbeiterId)) {
            System.out.println("Mitarbeiter entfernt!");
        } else {
            System.out.println("Mitarbeiter mit der ID nicht gefunden!");
        }
    }



    //Methode um alle Kunden zu sehen (case 9)
    private void anzeigenAlleKunden(Scanner scan) {
        System.out.println("Kundenliste:");
        kundenVW.anzeigenAlleKunden();
    }


    /**
     * Methode zum Anzeigen der Bestandshistorie eines Artikels.
     * Zeigt den Bestandshistorieverlauf eines Artikels basierend auf der eingegebenen Artikelnummer an.
     * parameter: scan Der Scanner, der für die Benutzereingabe verwendet wird.
     */
    private void anzeigenBestandshistorie(Scanner scan) {
        // Benutzer wird aufgefordert, die Artikelnummer einzugeben
        System.out.println("Artikelnummer eingeben:");
        int artikelId = Integer.parseInt(scan.nextLine()); // Einlesen und Konvertieren der eingegebenen Artikelnummer

        try {
            // Abrufen der Bestandshistorie für den angegebenen Artikel
            Map<LocalDate, Integer> bestandshistorie = artikelVW.ausgebenBestandshistorie(artikelId);

            // Sortieren der Einträge nach Datum (Schlüssel)
            List<Map.Entry<LocalDate, Integer>> eintraege = new ArrayList<>(bestandshistorie.entrySet());
            eintraege.sort(Map.Entry.comparingByKey());

            // Ausgabe der sortierten Bestandshistorie-Einträge
            for (Map.Entry<LocalDate, Integer> entry : eintraege) {
                System.out.println("Bestand am " + entry.getKey() + ": " + entry.getValue());
            }
        } catch (ArtikelNichtGefundenException e) {
            // Fehlerbehandlung, falls der Artikel nicht gefunden wurde
            System.out.println(e.getMessage()); // Fehlermeldung ausgeben, falls der Artikel nicht gefunden wurde
        }
    }



//Methoden Kunden Menu------------------------------------------------------------------------------------------------------

    // Methode zum Suchen von Artikeln nach Name (case 2)
    /**
     * Methode zum Suchen nach Artikeln anhand eines Suchbegriffs.
     * Zeigt alle Artikel an, deren Name den eingegebenen Suchbegriff enthält.
     *
     * @param scan Der Scanner, der für die Benutzereingabe verwendet wird.
     */
    private void sucheArtikel(Scanner scan) {
        // Benutzer wird aufgefordert, einen Artikelnamen oder Suchbegriff einzugeben
        System.out.println("Artikelname/Suchbegriff eingeben:");
        String suchbegriff = scan.nextLine().trim(); // Einlesen des Suchbegriffs und Entfernen von Leerzeichen

        // Suchen nach Artikeln anhand des eingegebenen Suchbegriffs
        List<Artikel> gefundeneArtikel = artikelVW.sucheArtikelNachBegriff(suchbegriff);//Nach dieser Zeile enthält gefundeneArtikel eine Liste von Artikeln, die entweder genau dem Suchbegriff entsprechen oder den Suchbegriff in ihrem Namen enthalten

        // Überprüfen, ob Artikel gefunden wurden
        if (gefundeneArtikel.isEmpty()) {
            System.out.println("Keine Artikel mit dem Suchbegriff " + suchbegriff + " gefunden!");
        } else {
            // Anzeigen der gefundenen Artikel
            System.out.println("Gefundene Artikel:");
            for (Artikel artikel : gefundeneArtikel) {
                System.out.println(artikel.toString()); // Anzeigen der Artikelinformationen
            }
        }
    }



    /// Methode um Artikel in den Warenkorb zu legen (case 3 K)
    private void hinzufuegenArtikelInWarenkorb(Scanner scan) {
        // Benutzer zur Eingabe der Artikelnummer auffordern
        System.out.println("Artikelnummer eingeben:");
        String artikelIdStr = scan.nextLine(); // Hier wird die Eingabe als Zeichenkette eingelesen

        try {
            // Versuche, die eingegebene Artikelnummer in eine ganze Zahl (Integer) umzuwandeln
            int artikelId = Integer.parseInt(artikelIdStr);

            // Suche nach dem Artikel anhand der Artikelnummer
            Artikel artikel = artikelVW.sucheArtikelNachID(artikelId);

            if (artikel != null) {
                // Wenn der Artikel gefunden wurde, fordere den Benutzer zur Eingabe der Stückzahl auf
                System.out.println("Stückzahl eingeben:");
                String stueckzahlStr = scan.nextLine();

                try {
                    // Versuche, die eingegebene Stückzahl in eine ganze Zahl (Integer) umzuwandeln
                    int stueckzahl = Integer.parseInt(stueckzahlStr);

                    if (stueckzahl > 0) {
                        // Wenn die Stückzahl größer als 0 ist, füge den Artikel zum Warenkorb hinzu
                        warenkorb.hinzufuegenArtikelInWarenkorb(artikel, stueckzahl);
                        System.out.println("Artikel zum Warenkorb hinzugefügt!");
                    } else {
                        // Falls die Stückzahl nicht größer als 0 ist, gib eine entsprechende Fehlermeldung aus
                        System.out.println("Die Stückzahl muss größer als 0 sein.");
                    }
                } catch (NumberFormatException e) {
                    // Falls die Eingabe der Stückzahl keine gültige Zahl ist, gib eine Fehlermeldung aus
                    System.out.println("Ungültige Eingabe! Bitte geben Sie eine gültige Stückzahl ein.");
                }
            } else {
                // Falls kein Artikel mit der eingegebenen Artikelnummer gefunden wurde, gib eine Fehlermeldung aus
                System.out.println("Artikel nicht gefunden!");
            }
        } catch (NumberFormatException e) {
            // Falls die Eingabe der Artikelnummer keine gültige Zahl ist, gib eine Fehlermeldung aus
            System.out.println("Ungültige Eingabe! Bitte geben Sie eine gültige Artikelnummer ein.");
        }
    }



    /**
     * Methode zum Entfernen eines Artikels aus dem Warenkorb basierend auf der Artikelnummer.
     * parameter: scan Der Scanner, der für die Benutzereingabe verwendet wird.
     */
    private void entferneArtikelImWarenkorb(Scanner scan) {
        System.out.println("Artikelnummer eingeben:");
        String artikelIdStr = scan.nextLine(); // Eingabe der Artikelnummer als Zeichenkette

        try {
            int artikelId = Integer.parseInt(artikelIdStr); // Umwandlung der Zeichenkette in eine ganze Zahl (Artikel-ID)

            Artikel artikel = artikelVW.sucheArtikelNachID(artikelId); // Suche des Artikels in der Datenquelle

            if (artikel != null) { // Wenn der Artikel gefunden wurde
                if (warenkorb.getArtikelMap().containsKey(artikel)) { // Überprüfung, ob der Artikel im Warenkorb enthalten ist
                    warenkorb.entferneArtikelInWarenkorb(artikel); // Entfernen des Artikels aus dem Warenkorb
                    System.out.println("Artikel aus dem Warenkorb entfernt!");
                } else {
                    System.out.println("Artikel war nicht im Warenkorb!");
                }
            } else {
                System.out.println("Artikel nicht gefunden!");
            }
        } catch (NumberFormatException e) {
            System.out.println("Ungültige Eingabe! Bitte geben Sie eine gültige Artikelnummer ein.");
        }
    }


    //Methode um Inhalte vom Warenkorb zu sehen (case 5 K)
    private void anzeigenWarenkorb() {
        warenkorb.anzeigeWarenkorbInhalt();
    }


    //Mithilfe von artikelId kann die stückzahl vom Artikel im Warenkorb geändert werden (case 6)
    /**
     * Methode zum Ändern der Stückzahl eines Artikels im Warenkorb.
     *
     * parameter: scan Der Scanner, der für die Benutzereingabe verwendet wird.
     */
    private void aendereArtikelStueckzahlImWaarenkorb(Scanner scan) {
        // Benutzer zur Eingabe der Artikel-ID auffordern
        System.out.println("ArtikelID eingeben:");
        String artikelIdStr = scan.nextLine(); // Einlesen der Artikel-ID als Zeichenkette

        try {
            int artikelId = Integer.parseInt(artikelIdStr); // Umwandlung der Zeichenkette in eine ganze Zahl (Artikel-ID)
            Artikel artikel = artikelVW.sucheArtikelNachID(artikelId); // Suche des Artikels in der Datenquelle

            if (artikel != null) { // Überprüfen, ob der Artikel gefunden wurde
                if (warenkorb.getArtikelMap().containsKey(artikel)) { // Überprüfen, ob der Artikel im Warenkorb enthalten ist
                    System.out.println("Neue Stückzahl eingeben:");
                    String neueStueckzahlStr = scan.nextLine(); // Einlesen der neuen Stückzahl als Zeichenkette

                    try {
                        int neueStueckzahl = Integer.parseInt(neueStueckzahlStr); // Umwandlung der Zeichenkette in eine ganze Zahl (Stückzahl)

                        if (neueStueckzahl > artikel.getBestand()) { // Überprüfen, ob die gewünschte Stückzahl verfügbar ist
                            System.out.println("Die gewünschte Stückzahl ist nicht verfügbar!");
                        } else {
                            // Aktualisieren der Stückzahl im Warenkorb
                            warenkorb.aktualisiereArtikelStueckzahl(artikel, neueStueckzahl);
                            System.out.println("Stückzahl des Artikels erfolgreich geändert!");
                        }
                    } catch (NumberFormatException e) {
                        // Fehlerbehandlung für ungültige Eingabe der neuen Stückzahl
                        System.out.println("Ungültige Eingabe! Bitte geben Sie eine gültige Stückzahl ein.");
                    }
                } else {
                    // Nachricht, wenn der Artikel nicht im Warenkorb enthalten ist
                    System.out.println("Artikel ist nicht im Warenkorb!");
                }
            } else {
                // Nachricht, wenn der Artikel mit der angegebenen ID nicht gefunden wurde
                System.out.println("Artikel mit der ID " + artikelId + " wurde nicht gefunden!");
            }
        } catch (NumberFormatException e) {
            // Fehlerbehandlung für ungültige Eingabe der Artikel-ID
            System.out.println("Ungültige Eingabe! Bitte geben Sie eine gültige Artikelnummer ein.");
        }
    }




    //Methode um den warenkorb komplett zu leeren (case 7 K)
    private void leerenWarenkorb() {
        warenkorb.leerenWarenkorb();
        System.out.println("Warenkorb geleert!");
    }

    
    //checkout (case 8)
    /**
     * Methode zum Abschließen des Einkaufs und zur Bearbeitung des Checkouts.
     *
     * @param scan Der Scanner, der für die Benutzereingabe verwendet wird.
     */
    private void checkout(Scanner scan) {
        // Gesamtsumme berechnen
        double gesamtPreis = warenkorb.berechneGesamtImWarenkorb(); // Berechnung der Gesamtsumme der Artikel im Warenkorb
        Map<Artikel, Integer> gekaufteArtikel = new HashMap<>(warenkorb.getArtikelMap()); // Kopie der Artikel im Warenkorb erstellen

        // Gesamtsumme anzeigen und Kaufbestätigung einholen
        System.out.println("Gesamtsumme: " + gesamtPreis + " EUR"); // Anzeige der Gesamtsumme
        System.out.println("Möchten Sie den Kauf bestätigen? (Ja/Nein)"); // Aufforderung zur Kaufbestätigung
        String bestaetigung = scan.nextLine(); // Einlesen der Benutzereingabe

        if (bestaetigung.equalsIgnoreCase("Ja")) {
            // Kauf bestätigt
            System.out.println("Vielen Dank für Ihren Einkauf!"); // Bestätigung der erfolgreichen Kaufabwicklung

            // Erstellen einer neuen Rechnung
            Kunden angemeldeterKunde = kundenVW.getAngemeldeterKunde(); // Abrufen des angemeldeten Kunden
            Rechnung rechnung = new Rechnung(angemeldeterKunde, LocalDate.now(), gekaufteArtikel, gesamtPreis); // Erstellen einer neuen Rechnung

            // Rechnung anzeigen
            System.out.println(rechnung.toString()); // Anzeige der Rechnung

            // Bestände aktualisieren
            for (Map.Entry<Artikel, Integer> entry : gekaufteArtikel.entrySet()) {
                Artikel artikel = entry.getKey(); // Abrufen des Artikels
                int gekaufteMenge = entry.getValue(); // Abrufen der gekauften Menge

                try {
                    artikelVW.aktualisiereBestand(artikel.getArtikelId(), -gekaufteMenge); // Aktualisieren des Bestands
                } catch (ArtikelNichtGefundenException e) {
                    System.err.println("Artikel nicht gefunden: " + e.getMessage()); // Fehlerbehandlung, wenn Artikel nicht gefunden wurde
                }
            }

            // Warenkorb leeren
            warenkorb.leerenWarenkorb(); // Leeren des Warenkorbs nach dem erfolgreichen Kauf
        } else {
            // Kauf nicht bestätigt
            System.out.println("Der Kauf wurde abgebrochen."); // Meldung, dass der Kauf abgebrochen wurde
        }
    }


}


