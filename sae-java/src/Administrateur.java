
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Administrateur extends Personne {
  private ConnexionMySQL connexion;
  private Statement st;
  //private List<Magasin> magasinsAdministrateur;
  
  public Administrateur(int numCompte, String nom, String prenom, String identifiant, String adresse, int tel, String email, String mdp){
    super(numCompte, nom, prenom, identifiant, adresse, tel, email, mdp);
  }

  public void creerCompteVendeur(int numCompte, String nom, String prenom, String identifiant, String adresse, int tel, String email, String mdp, Magasin magasin) throws SQLException{
    Vendeur vendeur = new Vendeur(numCompte, nom, prenom, identifiant, adresse, tel, email, mdp, magasin);
    PreparedStatement ps = this.connexion.prepareStatement("create user 'vendeur"+vendeur.getMagasinVendeur()+"'@'%' identified by '"+mdp+"'");
    ps.executeUpdate();
    PreparedStatement ps2 = this.connexion.prepareStatement("grant 'vendeur' to '"+vendeur.getMagasinVendeur()+"'@'%'");
    ps2.executeUpdate();

    //create user 'adminVacances'@'%' identified by 'mdpadmin';
    //grant 'administrateur' to 'adminVacances'@'%';
  }

  public void ajouteNouvelleLibrairie(Magasin magasin) throws SQLException{
    PreparedStatement ps = this.connexion.prepareStatement("insert into MAGASIN values(?,?,?)");
    ps.setInt(1, magasin.getId());
    ps.setString(2, magasin.getNom());
    ps.setString(2, magasin.getVille());
    ps.executeUpdate();
  }

  public void gererStock(){

  }

  public void consulteStats(){

  }
}
