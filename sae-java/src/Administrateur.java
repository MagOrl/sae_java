
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Administrateur extends Personne {
  //private List<Magasin> magasinsAdministrateur;
  
  public Administrateur(int numCompte, String nom, String prenom, String identifiant, String adresse, int tel, String email, String mdp, String codePostal, String ville){
    super(numCompte, nom, prenom, identifiant, adresse, tel, email, mdp, codePostal, ville);
  }
}
