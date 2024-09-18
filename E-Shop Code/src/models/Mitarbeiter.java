package src.models;
public class Mitarbeiter extends Benutzer {

    private int mitarbeiterId;


    public Mitarbeiter(String name, String email, String benutzername, String passwort, int mitarbeiterId) {
       super(name, email, benutzername, passwort);
       this.mitarbeiterId = mitarbeiterId;
   }
  
   public int getMitarbeiterId(){
    return mitarbeiterId;
   }



  
  

}
    
