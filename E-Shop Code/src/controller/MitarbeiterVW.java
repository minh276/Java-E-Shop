package src.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import src.models.Mitarbeiter;
import src.persistence.PersistenceManager;

public class MitarbeiterVW {
    private List<Mitarbeiter> mitarbeiterListe;
    private PersistenceManager persistenceManager;


    /**
     * Konstruktor für die Klasse MitarbeiterVW.
     * Der Konstruktor initialisiert den PersistenceManager und die Liste der Mitarbeiter.
     * Beim Erstellen der Instanz wird versucht, die Mitarbeiterliste aus der Datei "mitarbeiter.txt" zu laden.
     * Parameter:
     *   persistenceManager - Der PersistenceManager, der für das Laden und Speichern der Daten verwendet wird.
     */
    public MitarbeiterVW(PersistenceManager persistenceManager) {
        this.persistenceManager = persistenceManager;
        this.mitarbeiterListe = new ArrayList<>();
        try {
            // Öffnet die Datei "mitarbeiter.txt" zum Lesen
            persistenceManager.openForReading("mitarbeiter.txt");
            // Lädt die Liste der Mitarbeiter aus der Datei
            this.mitarbeiterListe = persistenceManager.ladeMitarbeiter();
            // Schließt die Datei nach dem Lesen
            persistenceManager.close();
        } catch (IOException e) {
            e.printStackTrace(); // Gibt den Stacktrace des Fehlers aus, wenn eine IOException auftritt
        }
    }


    /**
     * Setzt die Liste der Mitarbeiter.
     * Diese Methode ersetzt die aktuelle Liste der Mitarbeiter durch die übergebene Liste.
     * Parameter: mitarbeiterListe - Die neue Liste der Mitarbeiter.
     */
    public void setMitarbeiterListe(List<Mitarbeiter> mitarbeiterListe) {
        this.mitarbeiterListe = mitarbeiterListe;
    }


    /**
     * Gibt die Liste der Mitarbeiter zurück.
     * Diese Methode gibt die aktuelle Liste der Mitarbeiter zurück.
     * Return: Die Liste der Mitarbeiter.
     */
    public List<Mitarbeiter> getMitarbeiterListe() {
        return this.mitarbeiterListe;
    }


    /**
      Sucht in der Liste der Mitarbeiter nach einem bestimmten Begriff in Name, Email oder Benutzername.
     Alle Übereinstimmungen werden in einer neuen Liste von Mitarbeitern zurückgegeben.
      Parameter: begriff Der Suchbegriff, nach dem gesucht wird (nicht case-sensitive).
      Return: Eine Liste von Mitarbeitern, die den Suchbegriff im Namen, in der Email oder im Benutzernamen enthalten.
     */
    public List<Mitarbeiter> sucheMitarbeiterNachBegriff(String begriff) {
        List<Mitarbeiter> suchErgebnisse = new ArrayList<>();
        for (Mitarbeiter mitarbeiter : mitarbeiterListe) {
            if (mitarbeiter.getName().toLowerCase().contains(begriff.toLowerCase()) ||
                    mitarbeiter.getEmail().toLowerCase().contains(begriff.toLowerCase()) ||
                    mitarbeiter.getBenutzername().toLowerCase().contains(begriff.toLowerCase())) {
                //fügt den gefundenen Mitarbeiter "mitarbeiter einfach der Liste suchErgebnisse hinzu
                suchErgebnisse.add(mitarbeiter);
            }
        }
        return suchErgebnisse; //gibt die Liste der Mitarbeiter zurück, die den Suchkriterien entsprechen.
    }


    /**
     Speichert die Liste der Mitarbeiter in der Datei "mitarbeiter.txt".
     Die Methode öffnet die Datei zum Schreiben, speichert die aktuelle Liste der Mitarbeiter darin
     und schließt die Datei nach dem Schreibvorgang.
     Bei einem Fehler während des Schreibvorgangs wird eine IOException abgefangen und der Stacktrace ausgegeben.
     */
    public void speichereMitarbeiter() {
        try {
            // Öffnet die Datei "mitarbeiter.txt" zum Schreiben
            persistenceManager.openForWriting("mitarbeiter.txt");

            // Speichert die Liste der Mitarbeiter in der Datei
            persistenceManager.speichereMitarbeiter(mitarbeiterListe);

            // Schließt die Datei nach dem Schreiben
            persistenceManager.close();
        } catch (IOException e) {
            // Gibt den Stacktrace des Fehlers aus, wenn eine IOException auftritt
            e.printStackTrace();
        }
    }


    /**
     Erzeugt eine neue, eindeutige Mitarbeiter-ID.
     Diese Methode durchsucht die aktuelle Liste der Mitarbeiter, um die höchste bestehende Mitarbeiter-ID zu finden,
     und gibt eine neue ID zurück, die um eins höher ist als die höchste gefundene ID.
     Return: Eine neue, eindeutige Mitarbeiter-ID.
     */
    public int erzeugeEinzigartigeMitarbeiterId() {
        int maxId = 0; // Setzt die maximale ID auf 0 als Ausgangspunkt.
        for (Mitarbeiter mitarbeiter : mitarbeiterListe) {
            if (mitarbeiter.getMitarbeiterId() > maxId) {
                maxId = mitarbeiter.getMitarbeiterId(); //Aktualisiert maxId, falls eine größere ID gefunden wird.
            }
        }
        return maxId + 1; //Gibt eine neue, eindeutige Mitarbeiter-ID zurück, die um eins höher als die höchste gefundene ID ist.
    }


    /**
     Fügt einen neuen Mitarbeiter zur Liste der Mitarbeiter hinzu.
     Nach dem Hinzufügen des Mitarbeiters wird die Liste der Mitarbeiter gespeichert.
     parameter: mitarbeiter Der Mitarbeiter, der zur Liste hinzugefügt werden soll.
     */
    public void hinzufuegenMitarbeiterInListe(Mitarbeiter mitarbeiter) {
        mitarbeiterListe.add(mitarbeiter); // Fügt den Mitarbeiter zur Liste hinzu
        speichereMitarbeiter(); // Speichern nach Hinzufügen
    }


    /**
      Gibt die Details aller Mitarbeiter in der Mitarbeiterliste aus.
      Die Methode durchläuft die Mitarbeiterliste und gibt für jeden Mitarbeiter die ID,
      die Email, den Benutzernamen und den Namen aus.
     */
    public void zeigeAlleMitarbeiterDetails() {
        System.out.println("Mitarbeiterliste:"); // Gibt den Titel für die Mitarbeiterliste aus
        for (Mitarbeiter mitarbeiter : mitarbeiterListe) { // Durchläuft jeden Mitarbeiter in der Mitarbeiterliste
            System.out.println( // Gibt die Details des aktuellen Mitarbeiters aus
                    "Mitarbeiter: " + // Gibt den Titel "Mitarbeiter" aus
                            "ID: " + mitarbeiter.getMitarbeiterId() + ", " + // Gibt die ID des Mitarbeiters aus
                            "Email: " + mitarbeiter.getEmail() + ", " + // Gibt die Email des Mitarbeiters aus
                            "Benutzername: " + mitarbeiter.getBenutzername() + ", " + // Gibt den Benutzernamen des Mitarbeiters aus
                            "Name: " + mitarbeiter.getName() // Gibt den Namen des Mitarbeiters aus
            );
        }
    }


    /**
      Überprüft, ob ein Mitarbeiter mit dem angegebenen Benutzernamen bereits existiert.
      Wenn ein Mitarbeiter mit dem gleichen Benutzernamen gefunden wird, wird false zurückgegeben,
      da der Benutzer bereits vorhanden ist. Andernfalls wird eine neue Mitarbeiter-ID generiert
      und ein neuer Mitarbeiter mit den angegebenen Details erstellt und zur Liste hinzugefügt.
      return: true, wenn der Mitarbeiter erfolgreich hinzugefügt wurde false, wenn ein Mitarbeiter
             mit dem gleichen Benutzernamen bereits existiert.
     */
    public boolean registriereMitarbeiter(String name, String email, String benutzername, String passwort) {
        for (Mitarbeiter mitarbeiter : mitarbeiterListe) {// Durchläuft die Liste der Mitarbeiter
            if (mitarbeiter.getBenutzername().equals(benutzername)) { // Überprüft den Benutzernamen
                return false; // Mitarbeiter mit dem gleichen Benutzernamen existiert bereits
            }
        }

        // Mitarbeiter gibt es noch nicht, ID generieren und neuen Mitarbeiter erstellen
        int mitarbeiterId = erzeugeEinzigartigeMitarbeiterId();//Generiert eine neue eindeutige Mitarbeiter-ID
        // Erstellt einen neuen Mitarbeiter
        Mitarbeiter newMitarbeiter = new Mitarbeiter(name, email, benutzername, passwort, mitarbeiterId);
        // Fügt den neuen Mitarbeiter zur Liste hinzu
        hinzufuegenMitarbeiterInListe(newMitarbeiter);
        return true; // Rückgabe, dass der Mitarbeiter erfolgreich registriert wurde
    }


    /**
      Überprüft, ob ein Mitarbeiter mit dem angegebenen Benutzernamen und Passwort existiert und sich anmelden kann
      Durchläuft die Liste der Mitarbeiter und vergleicht den Benutzernamen und das Passwort jedes Mitarbeiters.
     Wenn ein Mitarbeiter mit den angegebenen Anmeldedaten gefunden wird, wird `true` zurückgegeben, sonst `false`.
     */
    public boolean anmeldenMitarbeiter(String benutzername, String passwort) {
        for (Mitarbeiter mitarbeiter : mitarbeiterListe) {
            if (mitarbeiter.getBenutzername().equals(benutzername) && mitarbeiter.getPasswort().equals(passwort)) {
                return true; // Mitarbeiter mit übereinstimmendem Benutzernamen und Passwort gefunden
            }
        }
        return false;// Kein Mitarbeiter mit übereinstimmendem Benutzernamen und Passwort gefunden
    }

    /**
      Entfernt einen Mitarbeiter aus der Liste anhand der übergebenen Mitarbeiter-ID.
      Wenn ein Mitarbeiter mit der angegebenen ID gefunden und entfernt wird, werden die
      aktualisierten Mitarbeiterdaten in der Datei gespeichert.
     return: true, wenn der Mitarbeiter erfolgreich entfernt wurde; false, wenn kein
             Mitarbeiter mit der angegebenen ID gefunden wurde.
     */
    public boolean entferneMitarbeiter(int mitarbeiterId) {
        for (Mitarbeiter mitarbeiter : mitarbeiterListe) {
            if (mitarbeiter.getMitarbeiterId() == mitarbeiterId) {
                mitarbeiterListe.remove(mitarbeiter);
                speichereMitarbeiter(); // Speichern der Mitarbeiterdaten nach Entfernen
                return true; // Rückgabe von true, da der Mitarbeiter erfolgreich entfernt wurde
            }
        }
        return false; // Rückgabe von false, da kein Mitarbeiter mit der angegebenen ID gefunden wurde
    }
}
