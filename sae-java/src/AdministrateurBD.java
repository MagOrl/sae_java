import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

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
    
    public void ajouteNouvelleLibrairie(Magasin magasin){
      try{
        PreparedStatement ps = this.connexion.prepareStatement("insert into MAGASIN values(?,?,?)");
        ps.setInt(1, magasin.getId());
        ps.setString(2, magasin.getNom());
        ps.setString(2, magasin.getVille());
        ps.executeUpdate();
      }catch(SQLException e){
        System.out.println("Une erreur est survenue lors de l'ajout de la librairie au réseau");
      }
    }

    public void AjouterLivre(Livre livre){
      try {
        PreparedStatement ps = this.connexion.prepareStatement("insert into LIVRE values(?,?,?,?,?)");
        ps.setInt(1, livre.getIsbn());   
        ps.setString(2, livre.getTitre()); 
        ps.setInt(3, livre.getNbPages());   
        ps.setInt(4, (livre.getDatePubli()));   
        ps.setDouble(5, livre.getPrix());
        ps.executeUpdate();   
      } catch (SQLException e) {
        System.out.println("Une erreur est survenue lors de l'ajout du livre veuillez réessayer");
      }
    }


    public void SupprimerLivre(Livre livre){
      try{
        PreparedStatement ps = this.connexion.prepareStatement("DELETE FROM LIVRE WHERE isbn = ?");
        ps.setInt(1, livre.getIsbn());
        ps.executeUpdate();
      }catch(SQLException e){
        System.out.println("Une erreur est survenue lors de la suppression du livre veuillez réessayer");
      }
    }

    public void majQteLivre(Livre livre, Magasin mag, int qte){
      try{
        this.st = connexion.createStatement();
	  	  ResultSet r = this.st.executeQuery("select qte from LIVRE natural join POSSERDER natural join MAGASIN where isbn = "+ livre.getIsbn()+" and idmag = " + mag.getId() + "");
        qte += r.getInt("qte");

        PreparedStatement ps = this.connexion.prepareStatement("UPDATE POSSERDER SET qte = ? WHERE isbn = ?");
        ps.setInt(1, qte);
        ps.setInt(2, livre.getIsbn());
        ps.executeUpdate();
      }catch(SQLException e){
        System.out.println("Une erreur est survenue lors de la mise à jour de la quantité veuillez réessayer");
      }
    }

    public void afficherStockLibrairie(Magasin mag){
      try{
        this.st = connexion.createStatement();
        ResultSet r = this.st.executeQuery("select isbn, titre, nbpages, datepubli, prix from LIVRE natural join POSSERDER natural join MAGASIN where idmag = "+ mag.getId());
        while(r.next()){
          Livre livreActuel = new Livre(Integer.parseInt(r.getString("isbn")), r.getString("titre"), r.getInt("nbpages"), r.getInt("datepubli"), r.getDouble("prix"));
          System.out.println(livreActuel + "/n");
        }
      }catch(SQLException e){
        System.out.println("Une erreur est survenue lors de l'affichage du stock");
      }
    }

    public void consulteStats(){

    }
    
}
