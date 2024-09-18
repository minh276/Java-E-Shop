package src.models;

public class Benutzer {

    private String name;
    private String email;
    private String benutzername;
    private String passwort;
    
    // Konstruktor 
    
    public Benutzer(String name, String email,String benutzername, String passwort) {
        this.name = name;
        this.email = email;
        this.benutzername = benutzername;
        this.passwort = passwort;
      
    }

 
    //Um Wert zu lesen (Getter Methoden)


    public String getName(){
        return name;
    }

    public String getEmail(){
        return email;
    }

    public String getBenutzername(){
        return benutzername;
    }

    public String getPasswort(){
        return passwort;
    }

    // um Wert zu ändern bzw zu setzen (Setter Methoden)

    public void setName(String name){
        this.name = name;
    }

    public void setEmail(String email){
        this.email = email;
    }
    
    public void setBenutzername(String benutzername){
        this.benutzername = benutzername;
    }

    public void setPasswort(String passwort){
        this.passwort = passwort;
    }
}