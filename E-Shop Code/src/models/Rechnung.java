package src.models;
import java.time.LocalDate;
import java.util.Map;

public class Rechnung {
    private Kunden kunde;
    private LocalDate datum;
    private Map<Artikel, Integer> gekaufteArtikel;
    private double gesamtsumme;

    // Konstruktor mit gesamtsumme als Parameter
    public Rechnung(Kunden kunde, LocalDate datum, Map<Artikel, Integer> gekaufteArtikel, double gesamtsumme) {
        this.kunde = kunde;
        this.datum = datum;
        this.gekaufteArtikel = gekaufteArtikel;
        this.gesamtsumme = gesamtsumme;
    }



    // Gibt die Rechnung als formatierten String zurück
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(); // Erstellt einen StringBuilder für die Zusammenstellung der Ergebniszeichenfolge
        sb.append("----- Rechnung -----\n"); // Fügt die Überschrift "----- Rechnung -----" hinzu

        if (kunde != null) { // Überprüft, ob ein Kunde vorhanden ist
            sb.append("Kunde: \n"); // Fügt den Kunde-Bereich hinzu
            sb.append("  Name: ").append(kunde.getName()).append("\n"); // Fügt den Namen des Kunden hinzu
            sb.append("  Email: ").append(kunde.getEmail()).append("\n"); // Fügt die E-Mail-Adresse des Kunden hinzu
            sb.append("  Benutzername: ").append(kunde.getBenutzername()).append("\n"); // Fügt den Benutzernamen des Kunden hinzu
            sb.append("  Adresse: ").append(kunde.getAdresse()).append("\n"); // Fügt die Adresse des Kunden hinzu
            sb.append("  Kunden-ID: ").append(kunde.getKundenId()).append("\n"); // Fügt die Kunden-ID des Kunden hinzu
        } else {
            sb.append("Kunde: Unbekannt\n"); // Fügt hinzu, dass der Kunde unbekannt ist, wenn kein Kunde vorhanden ist
        }

        sb.append("Datum: ").append(datum.toString()).append("\n"); // Fügt das Datum der Rechnung hinzu
        sb.append("Gekaufte Artikel:\n"); // Fügt den Abschnitt für gekaufte Artikel hinzu

        // Durchläuft die Map gekaufteArtikel, um jeden Artikel und die zugehörige Stückzahl aufzulisten
        for (Map.Entry<Artikel, Integer> entry : gekaufteArtikel.entrySet()) {
            Artikel artikel = entry.getKey(); // Holt den Artikel aus dem Eintrag
            int stueckzahl = entry.getValue(); // Holt die Stückzahl aus dem Eintrag
            sb.append("- ").append(artikel.getName()) // Fügt den Namen des Artikels hinzu
                    .append(" (Artikel ID: ").append(artikel.getArtikelId()).append("), Stückzahl: ") // Fügt die Artikel-ID und die Stückzahl hinzu
                    .append(stueckzahl).append(", Preis pro Stück: ").append(artikel.getPreis()).append("\n"); // Fügt den Preis pro Stück des Artikels hinzu
        }

        sb.append("Gesamtsumme: ").append(gesamtsumme).append(" EUR\n"); // Fügt die Gesamtsumme der Rechnung hinzu

        return sb.toString(); // Gibt die vollständig zusammengesetzte String-Repräsentation der Rechnung zurück
    }


}