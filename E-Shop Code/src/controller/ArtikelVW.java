    package src.controller;

    import java.io.IOException;
    import java.time.LocalDate;
    import java.util.ArrayList;
    import java.util.Collections;
    import java.util.Comparator;
    import java.util.HashMap;
    import java.util.List;
    import java.util.Map;
    import src.exceptions.ArtikelNichtGefundenException;
    import src.models.Artikel;
    import src.models.LagerEreignis;
    import src.models.Massengutartikel;
    import src.persistence.PersistenceManager;

    public class ArtikelVW {
        private List<Artikel> artikelListe;
        private PersistenceManager persistenceManager;

        // Konstruktor
        public ArtikelVW(PersistenceManager persistenceManager) {
            this.persistenceManager = persistenceManager; // Weist den übergebenen PersistenceManager der Instanzvariable zu
            this.artikelListe = new ArrayList<>(); // Initialisiert die artikelListe als leere ArrayList
            ladeArtikelUndEreignisse(); // Lädt die Artikel und deren Lagerereignisse aus der Persistenzschicht Liste
        }




        //Sucht Artikel nach Begriff
        public List<Artikel> sucheArtikelNachBegriff(String suchbegriff) {
            List<Artikel> ergebnisse = new ArrayList<>(); //Erstellt eine neue Liste, um die Suchergebnisse zu speichern
            for (Artikel artikel : artikelListe) { //Durchläuft alle Artikel in der Artikel-Liste
                if (artikel.getName().toLowerCase().contains(suchbegriff.toLowerCase())) {  // Prüft, ob der Name des Artikels den Suchbegriff (Groß-/Kleinschreibung egal) enthält
                    ergebnisse.add(artikel); //Wenn ja, wird der Artikel zur Ergebnisliste hinzugefügt
                }
            }
            return ergebnisse; //Gibt die Liste der gefundenen Artikel zurück
        }


        //Sucht Artikel nach ID
        public Artikel sucheArtikelNachID(int artikelId) {
            for (Artikel artikel : artikelListe) {  // Durchläuft alle Artikel in der artikelListe
                if (artikel.getArtikelId() == artikelId) {  // Überprüft, ob die Artikel-ID des aktuellen Artikels mit der gesuchten Artikel-ID übereinstimmt
                    return artikel; //Gibt den aktuellen Artikel zurück, wenn die IDs übereinstimmen
                }
            }
            return null;  //Gibt null zurück, wenn kein Artikel mit der gesuchten ID gefunden wurde
        }


        //Artikel hinzufügen als Mitarbeiter
        public void anlegenArtikel(String name, String farbe, double groesse, double preis, int bestand, boolean massengut, int packungsgroesse) {
            int artikelId = generiereEindeutigeArtikelId(); //Generiert eine eindeutige Artikel-ID
            Artikel newArtikel; //Deklariert eine Artikel-Variable
            if (massengut) {//Guckt, ob es sich um einen Massengutartikel handelt
                //Wenn es ein Massengutartikel ist, erstellt ein neues Massengutartikel-Objekt
                newArtikel = new Massengutartikel(artikelId, name, farbe, groesse, preis, bestand, packungsgroesse);
            } else {
                //Wenn es kein Massengutartikel ist, erstellt ein neues Artikel-Objekt
                newArtikel = new Artikel(artikelId, name, farbe, groesse, preis, bestand);
            }
            artikelListe.add(newArtikel); //Fügt den neuen Artikel der Artikel-Liste hinzu
            speichereArtikelUndEreignisse();  //Speichert die Artikel und deren Ereignisse
            System.out.println("Neuer Artikel hinzugefügt: " + newArtikel); // Gibt eine Bestätigung auf der Konsole aus
        }


        //Generiert eine eindeutige Artikel-ID, die größer als die bisher höchste ID ist.
        private int generiereEindeutigeArtikelId() {
            int maxId = 0; // Initialisiert die maximale ID mit 0
            for (Artikel artikel : artikelListe) { //Durchläuft alle Artikel in der artikelListe
                if (artikel.getArtikelId() > maxId) {  //Überprüft, ob die aktuelle Artikel-ID größer als maxId ist
                    maxId = artikel.getArtikelId();//Setzt maxId auf die aktuelle Artikel-ID
                }
            }
            return maxId + 1; //Gibt eine neue eindeutige ID zurück, die um 1 größer ist als die bisher höchste ID
        }


        //Entfernt Artikel mithilfe der ID aus Artikeliste
        public void entfernenArtikel(int artikelId) throws ArtikelNichtGefundenException {
            Artikel zuEntfernen = null; // Initialisiert die Variable für den zu entfernenden Artikel
            for (Artikel artikel : artikelListe) { // Durchläuft alle Artikel in der artikelListe
                if (artikel.getArtikelId() == artikelId) { //Guckt ob die Artikel-ID des aktuellen Artikels mit der gesuchten Artikel-ID übereinstimmt
                    zuEntfernen = artikel; // Setzt den zu entfernenden Artikel
                    break; // Beendet die Schleife, sobald der Artikel gefunden wurde
                }
            }
            if (zuEntfernen != null) { // Überprüft, ob der Artikel gefunden wurde
                artikelListe.remove(zuEntfernen); // Entfernt den gefundenen Artikel aus der artikelListe
                speichereArtikelUndEreignisse(); // Speichert die aktualisierte Liste der Artikel und Ereignisse
            } else {
                throw new ArtikelNichtGefundenException("Artikel nicht gefunden mit der ID: " + artikelId); // Wirft eine Ausnahme, wenn kein Artikel mit der gesuchten ID gefunden wurde
            }
        }


        //Gibt eine Liste aller Artikel zurück.
        public List<Artikel> listeArtikel() {
            return new ArrayList<>(artikelListe); //Eine neue Liste, die alle Artikel enthält, um sicherzustellen, dass die interne Liste geschützt bleibt.
        }


        //Bestand ändern mithilfe der ID
        public void aktualisiereBestand(int artikelId, int menge) throws ArtikelNichtGefundenException {
            Artikel artikel = sucheArtikelNachID(artikelId); //Suche nach dem Artikel mit der angegebenen ID
            if (artikel != null) { //Überprüfen, ob der Artikel gefunden wurde
                artikel.aktualisiereBestand(menge);  //Aktualisiert den Bestand des Artikels um die angegebene Menge
                speichereLagerEreignisse(artikel); //Speichert die Lagerereignisse des Artikels, um die Änderungen zu dokumentieren
                speichereArtikel();  //Speichert die aktualisierten Artikeldaten
            } else {
                //Fehlermeldung, wenn der Artikel nicht gefunden wurde
                throw new ArtikelNichtGefundenException("Artikel nicht gefunden mit der ID: " + artikelId);
            }
        }


        //Bestandshistorie der letzten 30 Tage ausgeben
        public Map<LocalDate, Integer> ausgebenBestandshistorie(int artikelId) throws ArtikelNichtGefundenException {
            Artikel artikel = sucheArtikelNachID(artikelId); //Sucht den Artikel nach seiner ID

            // Wenn der Artikel nicht gefunden wird, eine Ausnahme werfen
            if (artikel == null) {
                throw new ArtikelNichtGefundenException("Artikel nicht gefunden mit der ID: " + artikelId);
            }

            /*ruft eine vorhandene Liste von Lagerereignissen aus dem artikel-Objekt auf
             und speichert diese in der Variablen ereignisse.
            Die Variable ereignisse enthält nun die Lagerereignisse des Artikels
            und kann weiterverarbeitet werden.
             */
            List<LagerEreignis> ereignisse = artikel.getLagerEreignisse();// Deklariert eine Liste von LagerEreignis-Objekten und initialisiert sie mit den Lagerereignissen des Artikels

            /* Collections.sort: Methode um Listen zu sortieren in Java
               ereignisse: Die Liste von LagerEreignis-Objekten, die sortiert werden soll.
               Comparator.comparing(LagerEreignis::getDatum):
               Comparator.comparing: Methode erstellt einen Comparator, der Objekte nach einem bestimmten Kriterium vergleicht.
               LagerEreignis::getDatum: Methodenreferenz in Java.
               Sie bezieht sich auf die Methode getDatum der Klasse LagerEreignis.
               Ein Comparator wird erstell der LagerEreignis-Objekte nach dem Wert,
               der von der Methode getDatum zurückgegeben wird, vergleicht.
             */
            Collections.sort(ereignisse, Comparator.comparing(LagerEreignis::getDatum)); //vergleicht Lagerereignis nach datum

            /*
            LocalData: Klasse aus der java.time-API, die ein Datum ohne Zeitangabe repräsentiert.
            heute:  Name der neu erstellten Variablen vom Typ LocalDate
            LocalDate.now():Methode aus java.time-API, die das aktuelle Datum vom System
            */
            LocalDate heute = LocalDate.now();//gibt heutiges datum

            /*
            LocalDate: Datum=Schlüssel. LocalDate repräsentiert ein Datum ohne Zeitangabe.
            Integer: ist der Datentyp der Werte. Integer repräsentiert den Bestand des Artikels als Ganzzahl.
             */
            Map<LocalDate, Integer> bestandshistorie = new HashMap<>(); // Erstellt eine neue HashMap, um die Bestandshistorie des Artikels zu speichern
            int aktuellerBestand = artikel.getBestand(); // ermittelt aktuellen Bestand

            /*
            heute: Schlüssel für die Map, der das heutige Datum repräsentiert.
            aktuellerBestand: Das ist der Wert, der in die Map eingefügt wird,
                               und es repräsentiert den aktuellen Bestand des Artikels.
            put: Die Methode put der Map fügt ein Schlüssel-Wert-Paar in die Map ein oder
                 aktualisiert den Wert, wenn der Schlüssel bereits existiert.
             */
            bestandshistorie.put(heute, aktuellerBestand);// Fügt den aktuellen Bestand des Artikels für das heutige Datum in die Map bestandshistorie ein

            //Schleife über die letzten 30 Tage und setzt alles auf aktuellen bestand
            for (int i = 1; i < 30; i++) {
                LocalDate tag = heute.minusDays(i);//Berechnet das Datum für jeden der letzten 30 Tage
                int bestandAmTag = aktuellerBestand;// Setzt den Bestand am betrachteten Tag auf den aktuellen Bestand

                //Schleife durchläuft jedes LagerEreignis in der Liste 'ereignisse' vom Artikel und passt bestand an
                for (LagerEreignis ereignis : ereignisse) {
                    //Überprüft, ob das Datum des Ereignisses nach dem betrachteten Tag liegt
                    if (ereignis.getDatum().isAfter(tag)) {
                        //Reduziert Bestand am betrachteten Tag um die Menge der Ereignise die nach dem betrachteten tag waren
                        bestandAmTag -= ereignis.getMenge();
                    }
                }
                //Nachdem der Bestand für einen Tag berechnet wurde, wird er zusammen mit dem Datum in die Map bestandshistorie eingefügt.
                bestandshistorie.put(tag, bestandAmTag);
            }
            //Die Map mit den Beständen für die letzten 30 Tage wird zurückgegeben.
            return bestandshistorie;
        }


        //Speichert die Lagerereignisse für einen gegebenen Artikel
        private void speichereLagerEreignisse(Artikel artikel) {
            try {
                //Speichert die Lagerereignisse des angegebenen Artikels
                //Die Methode speichereLagerEreignisse nimmt die Artikel-ID und die Liste der Lagerereignisse als Argumente
                persistenceManager.speichereLagerEreignisse(artikel.getArtikelId(), artikel.getLagerEreignisse());
            } catch (IOException e) {
                e.printStackTrace();  //Gibt den Stacktrace des Fehlers aus, wenn eine IOException auftritt
            }
        }


        /**
         lädt die Artikel und die Lagerereignisse aus der Datei.
         öffnet die Datei und lädt die Artikel und deren Ereignisse und schließt die Datei anschließend
        */
        public void ladeArtikelUndEreignisse() {
            try {
                persistenceManager.openForReading("artikel.txt");//Öffnet die Datei "artikel.txt" zum Lesen
                artikelListe = persistenceManager.ladeArtikel();  //Lädt die Liste der Artikel aus der Datei
                persistenceManager.close();  //Schließt Datei, nachdem das Lesen der Artikel abgeschlossen ist

                // Lade die Lagerereignisse für jeden Artikel
                for (Artikel artikel : artikelListe) {

                    // Lädt die Lagerereignisse für den aktuellen Artikel anhand seiner Artikel-ID
                    List<LagerEreignis> ereignisse = persistenceManager.ladeLagerEreignisse(artikel.getArtikelId());

                    artikel.setLagerEreignisse(ereignisse);//Setzt die geladenen Lagerereignisse für den Artikel

                }
            } catch (IOException e) {
                e.printStackTrace();  //Gibt den Stacktrace des Fehlers aus, wenn eine IOException auftritt
            }
        }


        /**
          Speichert die Artikel und deren Lagerereignisse in Dateien.
          Die Methode öffnet die Datei "artikel.txt" zum Schreiben und speichert die Liste der Artikel darin.
          Danach werden die Lagerereignisse für jeden Artikel in separaten Dateien gespeichert.
          Bei einem Fehler während des Speichervorgangs wird eine IOException abgefangen und der Stacktrace ausgegeben.
         */
        public void speichereArtikelUndEreignisse() {
            try {
                persistenceManager.openForWriting("artikel.txt"); // Öffnet die Datei "artikel.txt" zum Schreiben

                // Speichert die Liste der Artikel in der Datei
                // Konvertiert die artikelListe in eine neue ArrayList und speichert sie
                persistenceManager.speichereArtikel(new ArrayList<>(artikelListe));

                persistenceManager.close();// Schließt die Datei, nachdem das Schreiben der Artikel abgeschlossen ist

                // Speichern der Lagerereignisse für alle Artikel
                for (Artikel artikel : artikelListe) {
                    speichereLagerEreignisse(artikel); // Speichert die Lagerereignisse für den aktuellen Artikel
                }
            } catch (IOException e) {
                e.printStackTrace();  // Gibt den Stacktrace des Fehlers aus, wenn eine IOException auftritt (Gibt zeilennumer)
            }
        }


        /**
         Speichert die Liste der Artikel in einer Datei.
         Die Methode öffnet die Datei "artikel.txt" zum Schreiben und speichert die Liste der Artikel darin.
         Die Artikel-Liste wird in eine neue ArrayList konvertiert und in der Datei gespeichert.
         Bei einem Fehler während des Speichervorgangs wird eine IOException abgefangen und der Stacktrace ausgegeben.
         */
        public void speichereArtikel() {
            try {
                persistenceManager.openForWriting("artikel.txt");// Öffnet die Datei "artikel.txt" zum Schreiben

                // Speichert die Liste der Artikel in der Datei
                // Hier wird die artikelListe in eine neue ArrayList konvertiert und gespeichert
                persistenceManager.speichereArtikel(new ArrayList<>(artikelListe));
                persistenceManager.close(); // Schließt die Datei, nachdem das Schreiben abgeschlossen ist
            } catch (IOException e) {
                e.printStackTrace(); // Gibt den Stacktrace des Fehlers aus, wenn eine IOException auftritt
            }
        }
    }
