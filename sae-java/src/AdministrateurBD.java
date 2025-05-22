import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.SQLException;

public class AdministrateurBD {

    private ConnexionMySQL connexion;
    private Statement st;

    public AdministrateurBD(ConnexionMySQL laConnexion) {
        this.connexion = laConnexion;
        try {
            laConnexion.connecter("localhost", "Librairie", "Kitcat", "Maria_K|DB_2109");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Vendeur CreerCompteVendeur(String nom, String prenom, String identifiant, String adresse, String tel, String email, String mdp, String codePostal, String ville, Magasin magasin) throws SQLException{
      int numVendeur = clientMax() + 1;
      try{
      this.st = this.connexion.createStatement();
      PreparedStatement ps = this.connexion.prepareStatement("insert into CLIENT values (?,?,?,?,?,?,?,?,?,?)");
        ps.setInt(1, numVendeur);
        ps.setString(2, identifiant);
        ps.setString(3, nom);
        ps.setString(4, prenom);
        ps.setString(5, adresse);
        ps.setString(6, codePostal);
        ps.setString(7, ville);
        ps.setString(8, email);
        ps.setInt(9, Integer.parseInt(tel));
        ps.setString(10, mdp);
        ps.executeUpdate();
        
      }catch(SQLException e){
        System.out.println("Un problème est survenu lors de la création du compte");
      }
      Integer telToInt = Integer.valueOf(tel);
      return new Vendeur(numVendeur, nom, prenom, identifiant, adresse, telToInt, email, mdp, codePostal, ville, magasin);
    }

    public int clientMax() throws SQLException {
        int max = 0;
        this.st = this.connexion.createStatement();
        ResultSet rs = this.st.executeQuery("select max(idcli) nbclimax from CLIENT");
        while (rs.next()) {
            max = rs.getInt("nbclimax");
        }
        rs.close();
        return max;
    }

    public boolean connectAdmin(String identif, String mdp) throws SQLException {
        this.st = this.connexion.createStatement();
        ResultSet rs = this.st
                .executeQuery(
                        "SELECT * FROM CLIENT WHERE identifiant = '" + identif + "'and motdepasse ='" + mdp + "'");
        return rs.next();
    }

    //public void creerCompteVendeur(int numCompte, String nom, String prenom, String identifiant, String adresse, int tel, String email, String mdp, Magasin magasin) throws SQLException{
    //try{
    //  Vendeur vendeur = new Vendeur(numCompte, nom, prenom, identifiant, adresse, tel, email, mdp, magasin);
    //  PreparedStatement ps = this.connexion.prepareStatement("create user 'vendeur"+vendeur.getMagasinVendeur()+"'@'%' identified by '"+mdp+"'");
    //  ps.executeUpdate();
    //  PreparedStatement ps2 = this.connexion.prepareStatement("grant 'vendeur' to '"+vendeur.getMagasinVendeur()+"'@'%'");
    //  ps2.executeUpdate();
    //}catch(SQLException e){
    //  System.out.println("");
    //}
    //create user 'adminVacances'@'%' identified by 'mdpadmin';
    //grant 'administrateur' to 'adminVacances'@'%';
  //}
//
  //public void ajouteNouvelleLibrairie(Magasin magasin) throws SQLException{
  //  PreparedStatement ps = this.connexion.prepareStatement("insert into MAGASIN values(?,?,?)");
  //  ps.setInt(1, magasin.getId());
  //  ps.setString(2, magasin.getNom());
  //  ps.setString(2, magasin.getVille());
  //  ps.executeUpdate();
  //}
//
  public void gererStock(){

  }

  public void consulteStats(){

  }
    
}
