package src.models;
import java.time.LocalDate;

public class Kunden extends Benutzer {
    private int kundenId;
    private String adresse;

    // Konstruktor
    public Kunden(int kundenId, String name, String email, String benutzername, String passwort, String adresse) {
        super(name, email, benutzername, passwort);
        this.kundenId = kundenId;
        this.adresse = adresse;
    } 

    // Getter
    public int getKundenId() {
        return kundenId;
    }

    public String getAdresse() {
        return adresse;
    }



    @Override
    public String toString() {
        return "KundenID: " + kundenId + ", Name: " + getName() + ", Benutzername: " + getBenutzername() + ", Email: " + getEmail() + ", Adresse: " + getAdresse();
    }
}
