package src.models;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Artikel {
    private int artikelId;
    private String name;
    private String farbe;
    private double groesse;
    private double preis;
    private int bestand;
    private List<LagerEreignis> lagerEreignisse;

    public Artikel(int artikelId, String name, String farbe, double groesse, double preis, int bestand) {
        this.artikelId = artikelId;
        this.name = name;
        this.farbe = farbe;
        this.groesse = groesse;
        this.preis = preis;
        this.bestand = bestand;
        this.lagerEreignisse = new ArrayList<>();
    }

    public int getArtikelId() {
        return artikelId;
    }

    public String getName() {
        return name;
    }

    public String getFarbe() {
        return farbe;
    }

    public double getGroesse() {
        return groesse;
    }

    public double getPreis() {
        return preis;
    }

    public int getBestand() {
        return bestand;
    }


    // Gibt die Liste der Lagerereignisse für diesen Artikel zurück.
    public List<LagerEreignis> getLagerEreignisse() {
        return lagerEreignisse;
    }


    // Setzt die Liste der Lagerereignisse für diesen Artikel.
    public void setLagerEreignisse(List<LagerEreignis> lagerEreignisse) {
        this.lagerEreignisse = lagerEreignisse;
    }


    // Aktualisiert den Bestand des Artikels.
    public void aktualisiereBestand(int menge) {
        this.bestand += menge; // Erhoeht den Bestand um die angegebene Menge
        this.lagerEreignisse.add(new LagerEreignis(LocalDate.now(), menge)); // Fügt ein neues Lagerereignis dem Artikel hinzu
    }

    //Überprüft, ob das aktuelle Objekt eine Instanz der Klasse Massengutartikel ist.
    public boolean isMassengutartikel() {
        return this instanceof Massengutartikel;
    }

    @Override
    public String toString() {
        return "ArtikelID: " + artikelId + ", Name: " + name + ", Farbe: " + farbe + ", Größe: " + groesse + ", Preis: " + preis + ", Bestand: " + bestand;
    }
}
