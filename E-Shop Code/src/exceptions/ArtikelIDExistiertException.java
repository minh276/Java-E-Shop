package src.exceptions;

// Benutzerdefinierte Exception-Klasse für bereits existierende Artikel-ID
public class ArtikelIDExistiertException extends Exception {

    public ArtikelIDExistiertException(String message) {
        super(message);
    }

    /*
     Konstruktor für die ArtikelIDExistiertException.
     Er ruft den Konstruktor der Oberklasse Exception auf und übergibt den Fehlermeldungstext
     "Artikel-ID existiert bereits."
     */
    public ArtikelIDExistiertException() {
        super("Artikel-ID existiert bereits.");
    }
}
