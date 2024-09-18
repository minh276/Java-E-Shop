
# eShop Anwendung

## Projektübersicht

Die eShop-Anwendung ist ein System zur Verwaltung eines E-Shops. Sie ermöglicht es Mitarbeitern, Artikelbestände zu verwalten, Kunden können Artikel durchsuchen und kaufen, und alle Bestandsänderungen sowie Transaktionen werden protokolliert. Die Anwendung besteht aus einem Anwendungskern, einer persistenten Datenspeicherung sowie einer Kommandozeilen- und einer grafischen Benutzeroberfläche.

## Funktionen

### Kernanwendungsfunktionen
- **Artikelverwaltung**: 
  - Artikel werden in `Artikel.java` mit einer eindeutigen Artikelnummer, Bezeichnung und Bestand gespeichert. 
  - Zusätzlich gibt es die Klasse `Massengutartikel.java`, die Artikel mit einer speziellen Packungsgröße handhabt, z.B. Artikel, die nur in Vielfachen einer bestimmten Menge ein- oder ausgelagert werden können.
  
- **Mitarbeiter- und Kundenverwaltung**: 
  - Mitarbeiter (`Mitarbeiter.java`) haben eine eindeutige ID und einen Namen und können neue Mitarbeiter und Artikel im System anlegen. 
  - Kunden (`Kunden.java`) haben ebenfalls eine eindeutige ID, einen Namen und eine Adresse. Sie können sich selbst registrieren und sich mit Benutzername und Passwort einloggen. 

- **Warenkorb**: Kunden können Artikel in ihren Warenkorb (`Warenkorb.java`) legen, die Anzahl der Artikel ändern und den Warenkorb leeren.

- **Käufe**: Kunden können die im Warenkorb enthaltenen Artikel kaufen. Nach dem Kauf wird der Warenkorb geleert und die Artikel aus dem Bestand entfernt. Eine Rechnung (`Rechnung.java`) wird erzeugt und enthält alle wichtigen Informationen wie Artikel, Preise und den Gesamtpreis.

- **Bestandshistorie und Lagerereignisse**: 
  - Jede Ein- und Auslagerung wird als Ereignis (`LagerEreignis.java`) festgehalten, inklusive Datum, Artikelnummer, Anzahl und beteiligter Person (Mitarbeiter oder Kunde).
  - Es gibt eine Funktion zur Ausgabe der Bestandshistorie eines Artikels über die letzten 30 Tage.

### Benutzeroberfläche

- **Kommandozeilen-Interface (CUI)**: 
  - Eine einfache Benutzerschnittstelle über die Kommandozeile wurde in `CUI.java` implementiert. Sie ermöglicht das Testen und Bedienen des eShops ohne grafische Benutzeroberfläche.

- **Grafische Benutzeroberfläche (GUI)**: 
  - Eine vollständige grafische Benutzeroberfläche (`GUI.java`) für den eShop wurde erstellt, die alle Funktionen des Systems abdeckt. Diese ermöglicht unter anderem:
    - Die Registrierung (`RegistrierungPanel.java`) und Anmeldung (`AnmeldePanel.java`) von Benutzern.
    - Das Anlegen und Verwalten von Artikeln über spezielle Tabellenmodelle (`ArtikelTableModel.java`).
    - Mitarbeiter- und Kunden-Interaktionen über spezielle Panels wie `MitarbeiterMenuPanel.java` und `KundenMenuPanel.java`.
    - Eine Anzeige und Verwaltung der Artikelbestände über `KundenArtikelTableModel.java`, `KundenTableModel.java` und `MitarbeiterTableModel.java`.

### Persistente Datenspeicherung

- **Dateibasierte Speicherung**: 
  - Die Klasse `FilePersistenceManager.java` sorgt dafür, dass alle Daten (Artikel, Mitarbeiter, Kunden und Lagerereignisse) persistent gespeichert werden. Beim Beenden der Anwendung werden die Daten in Dateien gespeichert, und beim Starten des eShops werden diese wieder geladen.

### Fehlerbehandlung

- **Spezielle Ausnahmen**: Für typische Fehlerfälle wurden eigene Exception-Klassen implementiert, darunter:
  - `ArtikelNichtGefundenException.java`
  - `MitarbeiterIDExistiertException.java`
  - `ArtikelIDExistiertException.java`

## Installation und Ausführung

1. **Kompilierung**: Kompiliere das Java-Projekt mit allen zugehörigen Klassen und Interfaces.
2. **Start des Programms**: Das Projekt kann durch die `Main.java`-Klasse gestartet werden. Um die grafische Benutzeroberfläche zu verwenden, starte die `Main.java`-Klasse mit dem Argument `gui`:
   ```bash
   java Main gui
   ```
   Ohne das Argument wird die Kommandozeilenoberfläche (CUI) standardmäßig gestartet.
