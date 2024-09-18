package src.controller;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import src.models.*;
import src.persistence.*;

public class KundenVW {
    private List<Kunden> kundenListe; //Eine Liste von Kunden-Objekten, die alle Kunden speichert.
    private Kunden angemeldeteKunden; //Ein einzelnes Kunden-Objekt, das den aktuell angemeldeten Kunden speichert.
    private PersistenceManager persistenceManager; //  Ein PersistenceManager-Objekt, das zum Laden und Speichern von Kundendaten verwendet wird.

    public KundenVW(PersistenceManager persistenceManager) { //Konstruktor,nimmt einen Parameter persistenceManager entgegen, der für die Persistenzoperationen (Laden und Speichern von Daten) verwendet wird 
        this.persistenceManager = persistenceManager;
        this.kundenListe = new ArrayList<>();
        try {
            persistenceManager.openForReading("kunden.txt"); //Öffnet die Datei kunden.txt zum Lesen.
            this.kundenListe = persistenceManager.ladeKunden(); //Lädt die Kunden aus der Datei kunden.txt und weist die geladene Liste der Instanzvariable kundenListe zu.
            persistenceManager.close(); //Schließt die geöffnete Datei, um Ressourcen freizugeben.
        } catch (IOException e) { //Fängt jede IOException, die während des Ladens der Daten auftreten könnte.
            e.printStackTrace(); //Gibt den Stacktrace der Exception aus, um zu debuggen und die Fehlerquelle zu identifizieren.
        }
    }


    // Methode zum Setzen der Kunden-Liste
    public void setKundenListe(List<Kunden> kundenListe) {
        this.kundenListe = kundenListe;
    }

    
     // Methode zum Zurückgeben der Kundenliste
     public List<Kunden> getKundenListe() {
        return this.kundenListe;
    }


    /**
     Speichert die Liste der Kunden in einer Datei.
     Diese Methode öffnet die Datei "kunden.txt" zum Schreiben und speichert die Liste der Kunden darin.
     Bei einem Fehler während des Speichervorgangs wird eine IOException abgefangen und der Stacktrace ausgegeben.
     */
    public void speichereKunden() {
        try {
            // Öffnet die Datei "kunden.txt" zum Schreiben
            persistenceManager.openForWriting("kunden.txt");
            // Speichert die Liste der Kunden in der Datei
            persistenceManager.speichereKunden(kundenListe);
            // Schließt die Datei, nachdem das Schreiben abgeschlossen ist
            persistenceManager.close();
        } catch (IOException e) {
            e.printStackTrace(); // Gibt den Stacktrace des Fehlers aus, wenn eine IOException auftritt
        }
    }


    /**
     Erzeugt eine neue, eindeutige Kunden-ID.
     Die Methode durchsucht die aktuelle Liste der Kunden, um die höchste bestehende Kunden-ID zu finden,
     und gibt eine neue ID zurück, die um eins höher ist als die höchste gefundene ID.
     Return: Eine neue, eindeutige Kunden-ID.
     */
    public int erzeugeEinzigartigeKundenId() {
        // Initialisiert die Variable maxId mit 0
        int maxId = 0;
        // Durchläuft die Liste der Kunden
        for (Kunden kunde : kundenListe) {
            // Wenn die Kunden-ID des aktuellen Kunden größer ist als maxId
            if (kunde.getKundenId() > maxId) {
                // Setzt maxId auf die Kunden-ID des aktuellen Kunden
                maxId = kunde.getKundenId();
            }
        }
        // Gibt eine neue, eindeutige Kunden-ID zurück, die um 1 höher ist als die höchste gefundene ID
        return maxId + 1;
    }


    /**
     * Registriert einen neuen Kunden, wenn der Benutzername noch nicht vergeben ist.
     * Die Methode erzeugt eine neue, eindeutige Kunden-ID, überprüft, ob der Benutzername
     * bereits in der Liste der Kunden existiert, und fügt den neuen Kunden zur Liste hinzu,
     * falls der Benutzername einzigartig ist. Anschließend wird die Kundenliste gespeichert.
     * Return: true, wenn der Kunde erfolgreich registriert wurde, false, wenn der Benutzername bereits existiert.
     */
    public boolean registriereKunden(String name, String email, String benutzername, String passwort, String adresse) {
        // Erzeugt eine neue, eindeutige Kunden-ID
        int kundenId = erzeugeEinzigartigeKundenId();

        // Überprüft, ob der Benutzername bereits existiert
        for (Kunden kunden : kundenListe) {
            if (kunden.getBenutzername().equals(benutzername)) {
                return false;  // Gibt false zurück, wenn der Benutzername bereits existiert
            }
        }
        // Erstellt einen neuen Kunden und fügt ihn zur Liste hinzu
        Kunden newKunde = new Kunden(kundenId, name, email, benutzername, passwort, adresse);
        kundenListe.add(newKunde);

        speichereKunden();  // Speichert die aktualisierte Kundenliste in der Datei
        return true; // Gibt true zurück, wenn der Kunde erfolgreich registriert wurde
    }


    // Meldet einen Kunden an wenn true
    public boolean anmeldenKunde(String benutzername, String passwort) {
        // Durchläuft die Liste der Kunden
        for (Kunden kunde : kundenListe) {
            // Überprüft, ob der Benutzername und das Passwort übereinstimmen
            if (kunde.getBenutzername().equals(benutzername) && kunde.getPasswort().equals(passwort)) {
                angemeldeteKunden = kunde; // Setzt den aktuellen Kunden als angemeldeten Kunden
                return true;  // Gibt true zurück, wenn der Kunde erfolgreich angemeldet wurde
            }
        }
        // Gibt false zurück, wenn kein Kunde mit den angegebenen Anmeldedaten gefunden wurde
        return false;
    }

    // Methode zum Anzeigen aller Kunden
    public void anzeigenAlleKunden() {
        for (Kunden kunde : kundenListe) {  // Durchläuft die Liste der Kunden
            System.out.println(kunde);  // Gibt den aktuellen Kunden auf der Konsole aus
        }
    }

    // Getter für den aktuellen Kunden
    public Kunden getAngemeldeterKunde() {
        return angemeldeteKunden;
    }
}
