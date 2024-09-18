package src.persistence;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import src.models.*;

public class FilePersistenceManager implements PersistenceManager {

    // BufferedReader zum Lesen von Dateien
    private BufferedReader reader = null;
    // PrintWriter zum Schreiben in Dateien
    private PrintWriter writer = null;


    /**
     * Öffnet eine Datei zum Lesen.
     * Erstellt einen BufferedReader für die angegebene Datei, um effizient Zeilen daraus zu lesen.
     * parameter: datei Der Name der Datei, die geöffnet werden soll.
     * throws: FileNotFoundException Wenn die Datei nicht gefunden wird.
     */
    @Override
    public void openForReading(String datei) throws FileNotFoundException {
        // Erstellt einen BufferedReader, der die angegebene Datei liest.
        reader = new BufferedReader(new FileReader("data/" + datei));
    }


    /**
     * Öffnet eine Datei zum Schreiben.
     * Erstellt einen PrintWriter für die angegebene Datei, um effizient Text darin zu schreiben.
     * parameter: datei Der Name der Datei, die geöffnet werden soll.
     * throws: IOException Wenn ein Fehler beim Öffnen der Datei zum Schreiben auftritt.
     */
    @Override
    public void openForWriting(String datei) throws IOException {
        // Erstellt einen PrintWriter, der die angegebene Datei beschreibt.
        writer = new PrintWriter(new BufferedWriter(new FileWriter("data/" + datei)));
    }


    /**
     * Schließt den Reader und den Writer, falls sie geöffnet sind.
     * return: true, wenn beide Streams erfolgreich geschlossen wurden; false, wenn ein Fehler beim Schließen des Readers auftritt.
     */
    @Override
    public boolean close() {
        // Schließt den Writer, falls er geöffnet ist
        if (writer != null) writer.close();

        // Schließt den Reader, falls er geöffnet ist
        if (reader != null) {
            try {
                reader.close();
            } catch (IOException e) {
                e.printStackTrace(); // Gibt den Stacktrace des Fehlers aus, wenn eine IOException auftritt
                return false; // Rückgabe false, wenn ein Fehler beim Schließen des Readers auftritt
            }
        }
        return true; // Rückgabe true, wenn beide Streams erfolgreich geschlossen wurden
    }


    /**
     * Lädt die Artikel aus einer Datei und erstellt eine Liste von Artikel-Objekten.
     * return: Eine Liste von Artikeln, die aus der Datei geladen wurden.
     * throws: IOException Wenn ein Fehler beim Lesen der Datei auftritt.
     */
    @Override
    public List<Artikel> ladeArtikel() throws IOException {
        List<Artikel> artikelListe = new ArrayList<>(); // Erstellt eine neue Liste, um die geladenen Artikel zu speichern
        String line;
        liesZeile(); // Überspringe die Header-Zeile
        while ((line = liesZeile()) != null) { // Liest jede Zeile der Datei, bis das Ende erreicht ist
            if (line.trim().isEmpty()) continue; // Leere Zeilen überspringen
            String[] teile = line.split(";");//Teilt die Zeile in Teile, getrennt durch ";"

            // Liest die Felder für einen Artikel aus der Zeile und erstellt ein neues Artikel-Objekt
            int artikelId = Integer.parseInt(teile[0]);
            String name = teile[1];
            String farbe = teile[2];
            double groesse = Double.parseDouble(teile[3]);
            double preis = Double.parseDouble(teile[4]);
            int bestand = Integer.parseInt(teile[5]);

            // Überprüft, ob die Zeile zusätzliche Felder für Massengutartikel enthält
            if (teile.length > 6) { // Massengutartikel hat zusätzliche Felder
                int packungsgroesse = Integer.parseInt(teile[6]);
                artikelListe.add(new Massengutartikel(artikelId, name, farbe, groesse, preis, bestand, packungsgroesse));
            } else {
                artikelListe.add(new Artikel(artikelId, name, farbe, groesse, preis, bestand));
            }
        }
        return artikelListe; // Gibt die Liste der geladenen Artikel zurück
    }


    /**
     * Speichert eine Liste von Artikel-Objekten in einer Datei.
     * parameter: artikelListe Die Liste der Artikel, die gespeichert werden sollen.
     * return: true, wenn das Speichern erfolgreich war.
     * throws: IOException Wenn ein Fehler beim Schreiben der Datei auftritt.
     */
    @Override
    public boolean speichereArtikel(List<Artikel> artikelListe) throws IOException {

        // Schreibt die Header-Zeile in die Datei
        schreibeZeile("ArtikelID;Name;Farbe;Groesse;Preis;Bestand;Packungsgroesse");
        // Durchläuft jeden Artikel in der Liste
        for (Artikel artikel : artikelListe) {
            if (artikel instanceof Massengutartikel) { // Überprüft, ob der Artikel ein Massengutartikel ist
                Massengutartikel mArtikel = (Massengutartikel) artikel;
                // Schreibt die Daten des Massengutartikels in die Datei
                schreibeZeile(mArtikel.getArtikelId() + ";" + mArtikel.getName() + ";" + mArtikel.getFarbe() + ";" + mArtikel.getGroesse() + ";" + mArtikel.getPreis() + ";" + mArtikel.getBestand() + ";" + mArtikel.getPackungsgroesse());
            } else {
                // Schreibt die Daten eines normalen Artikels in die Datei
                schreibeZeile(artikel.getArtikelId() + ";" + artikel.getName() + ";" + artikel.getFarbe() + ";" + artikel.getGroesse() + ";" + artikel.getPreis() + ";" + artikel.getBestand());
            }
        }
        return true; // Gibt zurück, dass das Speichern erfolgreich wa
    }


    /**
     * Lädt die Mitarbeiter aus einer Datei und erstellt eine Liste von Mitarbeiter-Objekten.
     * return: Eine Liste von Mitarbeitern, die aus der Datei geladen wurden.
     * throws: IOException Wenn ein Fehler beim Lesen der Datei auftritt.
     */
    @Override
    public List<Mitarbeiter> ladeMitarbeiter() throws IOException {
        // Erstellt eine neue Liste, um die geladenen Mitarbeiter zu speichern
        List<Mitarbeiter> mitarbeiterListe = new ArrayList<>();
        String line; //line verwendet, um jede Zeile der Datei als String zu speichern und weiterzuverarbeiten.
        liesZeile(); // Überspringe die Header-Zeile
        while ((line = liesZeile()) != null) { // Liest jede Zeile der Datei, bis das Ende erreicht ist
            if (line.trim().isEmpty()) continue; // Überspringt leere Zeilen nach Entfernen aller führenden und nachfolgenden Leerzeichen
            String[] teile = line.split(";");// Teilt die Zeile in Teile, getrennt durch ";"

            // Liest die Felder für einen Mitarbeiter aus der Zeile und erstellt ein neues Mitarbeiter-Objekt
            int id = Integer.parseInt(teile[0]);
            String name = teile[1];
            String email = teile[2];
            String benutzername = teile[3];
            String passwort = teile[4];

            // Erstellt ein neues Mitarbeiter-Objekt und fügt es zur Liste hinzu
            Mitarbeiter mitarbeiter = new Mitarbeiter(name, email, benutzername, passwort, id);
            mitarbeiterListe.add(mitarbeiter);
        }
        return mitarbeiterListe; // Gibt die Liste der geladenen Mitarbeiter zurück
    }


        /**
         * Speichert eine Liste von Mitarbeiter-Objekten in einer Datei.
         * parameter: mitarbeiterListe Die Liste der Mitarbeiter, die gespeichert werden sollen.
         * return: true, wenn das Speichern erfolgreich war.
         * throws: IOException Wenn ein Fehler beim Schreiben der Datei auftritt.
         */
        @Override
        public boolean speichereMitarbeiter(List<Mitarbeiter> mitarbeiterListe) throws IOException {
            // Schreibt die Header-Zeile in die Datei
            schreibeZeile("MitarbeiterID;Name;Email;Benutzername;Passwort");

            // Durchläuft jeden Mitarbeiter in der Liste
            for (Mitarbeiter mitarbeiter : mitarbeiterListe) {
                // Schreibt die Daten des Mitarbeiters in die Datei
                schreibeZeile(mitarbeiter.getMitarbeiterId() + ";" + mitarbeiter.getName() + ";" + mitarbeiter.getEmail() + ";" + mitarbeiter.getBenutzername() + ";" + mitarbeiter.getPasswort());
            }
            return true;// Gibt zurück, dass das Speichern erfolgreich war
        }



    /**
     * Lädt die Kunden aus einer Datei und erstellt eine Liste von Kunden-Objekten.
     * return: Eine Liste von Kunden, die aus der Datei geladen wurden.
     * throws: IOException Wenn ein Fehler beim Lesen der Datei auftritt.
     */
    @Override
    public List<Kunden> ladeKunden() throws IOException {
        // Erstellt eine neue Liste, um die geladenen Kunden zu speichern
        List<Kunden> kundenListe = new ArrayList<>();
        String line; //wird verwendet, um eine Variable zu definieren, die jede Zeile der Datei als String speichert und weiterverarbeitet.
        liesZeile(); // Überspringe die Header-Zeile
        while ((line = liesZeile()) != null) {  // Liest jede Zeile der Datei, bis das Ende erreicht ist
            if (line.trim().isEmpty()) continue; // Überspringt leere Zeilen

            String[] teile = line.split(";");// Teilt die Zeile in Teile, getrennt durch ";"
            if (teile.length != 6) { // Überprüft, ob die Zeile das richtige Format hat
                System.err.println("Ungültige Zeile in kunden.txt: " + line);// Gibt eine Fehlermeldung aus, wenn das Format falsch ist
                continue; // Falsches Format überspringen
            }
            try {
                // Liest die Felder für einen Kunden aus der Zeile
                int id = Integer.parseInt(teile[0].trim()); // Hier wird die KundenID gelesen
                String name = teile[1].trim();
                String email = teile[2].trim();
                String benutzername = teile[3].trim();
                String passwort = teile[4].trim();
                String adresse = teile[5].trim();

                // Erstellt ein neues Kunden-Objekt und fügt es zur Liste hinzu
                Kunden kunde = new Kunden(id, name, email, benutzername, passwort, adresse);
                kundenListe.add(kunde);
            } catch (NumberFormatException e) {
                // Gibt eine Fehlermeldung aus, wenn die Kunden-ID nicht geparst werden kann
                System.err.println("Fehler beim Parsen der Kunden-ID: " + line);
                e.printStackTrace(); // Druckt den Stacktrace des Fehlers
            }
        }
        return kundenListe;// Gibt die Liste der geladenen Kunden zurück

    }


    /**
     * Speichert eine Liste von Kunden-Objekten in einer Datei.
     * parameter: kundenListe Die Liste der Kunden, die gespeichert werden sollen.
     * return: true, wenn das Speichern erfolgreich war.
     * throws: IOException Wenn ein Fehler beim Schreiben der Datei auftritt.
     */
    @Override
    public boolean speichereKunden(List<Kunden> kundenListe) throws IOException {
        // Schreibt die Header-Zeile in die Datei
        schreibeZeile("KundenID;Name;Email;Benutzername;Passwort;Adresse");

        // Durchläuft jeden Kunden in der Liste
        for (Kunden kunde : kundenListe) {
            // Schreibt die Daten des Kunden in die Datei
            schreibeZeile(kunde.getKundenId() + ";" + kunde.getName() + ";" + kunde.getEmail() + ";" + kunde.getBenutzername() + ";" + kunde.getPasswort() + ";" + kunde.getAdresse());
        }
        return true; // Gibt zurück, dass das Speichern erfolgreich war
    }


    /**
     * Liest eine Zeile aus der Datei.
     * return: Die gelesene Zeile als String oder null, wenn der Reader nicht initialisiert ist oder das Ende der Datei erreicht wurde.
     * throws: IOException Wenn ein Fehler beim Lesen der Datei auftritt.
     */
    private String liesZeile() throws IOException {
        if (reader != null) { // Überprüft, ob der Reader initialisiert ist
            return reader.readLine(); // Liest eine Zeile aus der Datei und gibt sie zurück
        } else {
            return null; // Gibt null zurück, wenn der Reader nicht initialisiert ist
        }
    }


    /**
     * Schreibt eine Zeile in die Datei.
     *
     * @param daten Die Daten, die in die Datei geschrieben werden sollen.
     */
    private void schreibeZeile(String daten) {
        if (writer != null) { // Überprüft, ob der Writer initialisiert ist
            writer.println(daten); // Schreibt die Daten in die Datei
        }
    }


    /**
     * Lädt die Lagerereignisse für einen bestimmten Artikel aus einer Datei.
     * parameter: artikelId Die ID des Artikels, dessen Lagerereignisse geladen werden sollen.
     * return: Eine Liste der Lagerereignisse für den angegebenen Artikel.
     * throws: IOException Wenn ein Fehler beim Lesen der Datei auftritt.
     */
    @Override
    public List<LagerEreignis> ladeLagerEreignisse(int artikelId) throws IOException {
        // Erstellt eine neue Liste, um die geladenen Lagerereignisse zu speichern
        List<LagerEreignis> ereignisListe = new ArrayList<>();
        // Erstellt ein File-Objekt für die Datei mit Lagerereignissen
        File file = new File("data/lagerereignisse_" + artikelId + ".txt");
        if (!file.exists()) {// Überprüft, ob die Datei existiert
            return ereignisListe; //Gibt die leere Liste zurück, wenn die Datei nicht existiert

        }

        // Öffnet die Datei zum Lesen
        openForReading("lagerereignisse_" + artikelId + ".txt");
        String line;
        while ((line = liesZeile()) != null) {// Liest jede Zeile der Datei, bis das Ende erreicht ist
            if (line.trim().isEmpty()) continue; // Leere Zeilen überspringen
            ereignisListe.add(LagerEreignis.fromString(line));// Fügt das Lagerereignis, das aus der Zeile erstellt wurde, zur Liste hinzu
        }
        close(); // Schließt die Datei
        return ereignisListe; //Gibt die Liste der geladenen Lagerereignisse zurück
    }


    /**
     * Speichert eine Liste von Lagerereignis-Objekten für einen bestimmten Artikel in einer Datei.
     *
     * @param artikelId Die ID des Artikels, dessen Lagerereignisse gespeichert werden sollen.
     * @param ereignisListe Die Liste der Lagerereignisse, die gespeichert werden sollen.
     * @return true, wenn das Speichern erfolgreich war.
     * @throws IOException Wenn ein Fehler beim Schreiben der Datei auftritt.
     */
    @Override
    public boolean speichereLagerEreignisse(int artikelId, List<LagerEreignis> ereignisListe) throws IOException {
        // Öffnet die Datei zum Schreiben
        openForWriting("lagerereignisse_" + artikelId + ".txt");

        // Durchläuft jedes Lagerereignis in der Liste
        for (LagerEreignis ereignis : ereignisListe) {
            // Schreibt das Lagerereignis in die Datei
            schreibeZeile(ereignis.toString());
        }

        // Schließt die Datei
        close();

        // Gibt zurück, dass das Speichern erfolgreich war
        return true;
    }
}
