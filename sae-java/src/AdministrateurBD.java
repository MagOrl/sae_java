import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.ArrayList;

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

    public Administrateur trouveAdmin(String identifiant, String mdp) throws SQLException{
      Administrateur admin = null;
      try{
        this.st = this.connexion.createStatement();
        ResultSet rs = this.st
                  .executeQuery(
                          "SELECT * FROM CLIENT WHERE identifiant ='" + identifiant + "'" + "and motdepasse ='"+mdp+"'" );
        while(rs.next()){
          admin = new Administrateur(clientMax(), rs.getString("nomcli"), rs.getString("prenomcli"), identifiant,rs.getString("adressecli"), rs.getInt("tel"), rs.getString("email"), mdp, rs.getString("codepostal"), rs.getString("villecli"));
        }
        rs.close();
      }catch(SQLException e){
        System.out.println("Nous n'avons pas pu trouver votre compte veuillez réessayer, si vous n'avez pas de compte créez en un");
      }
      return admin;
    }

    public Vendeur CreerCompteVendeur(String nom, String prenom, String identifiant, String adresse, String tel, String email, String mdp, String codePostal, String ville, String nommag) throws SQLException{
      int numVendeur = clientMax() + 1;
      Magasin mag = null;
      this.st = this.connexion.createStatement();
      ResultSet rs = this.st.executeQuery("select idmag, villemag from MAGASIN where nommag ='" +nommag+ "'");
      while(rs.next()){
        mag = new Magasin(rs.getString("idmag"), nommag, rs.getString("villemag"));
      }
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
      Integer telToInt = Integer.valueOf(tel);
      return new Vendeur(numVendeur, nom, prenom, identifiant, adresse, telToInt, email, mdp, codePostal, ville, mag);
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

    public boolean connectAdmin(String identif, String mdp) throws SQLException{
        this.st = this.connexion.createStatement();
        ResultSet rs = this.st
                .executeQuery(
                        "SELECT * FROM CLIENT WHERE identifiant = '" + identif + "'and motdepasse ='" + mdp + "'");
        return rs.next();
    }
    
    public void ajouteNouvelleLibrairie(String idmag, String nommag, String villemag) throws SQLException{
      try{
        Magasin magasin = new Magasin(idmag, nommag, villemag);
        PreparedStatement ps = this.connexion.prepareStatement("insert into MAGASIN values(?,?,?)");
        ps.setString(1, magasin.getId());
        ps.setString(2, magasin.getNom());
        ps.setString(3, magasin.getVille());
        ps.executeUpdate();
      }catch(SQLException e){
        e.printStackTrace();
      }
        //System.out.println("Une erreur est survenue lors de l'ajout de la librairie au réseau");
    }

    public List<String> choixLibrairie() throws SQLException{
      List<String> lesLibrairies = new ArrayList<>();
      this.st = connexion.createStatement();
      ResultSet rs = this.st.executeQuery("select nommag from MAGASIN");
      while(rs.next()){
        lesLibrairies.add(rs.getString("nommag"));
      }
      return lesLibrairies;
    }

    public Magasin trouveLibrairie(String nommag) throws SQLException{
      Magasin mag = null;
      this.st = connexion.createStatement();
      ResultSet rs = this.st.executeQuery("select idmag, villemag from MAGASIN where nommag ='" +nommag+"'");
      mag = new Magasin(rs.getString("idmag"), nommag, rs.getString("villemag"));
      return mag;
    }

    public void AjouterLivre(Livre livre) throws SQLException{
        PreparedStatement ps = this.connexion.prepareStatement("insert into LIVRE values(?,?,?,?,?)");
        ps.setInt(1, livre.getIsbn());   
        ps.setString(2, livre.getTitre()); 
        ps.setInt(3, livre.getNbPages());   
        ps.setInt(4, (livre.getDatePubli()));   
        ps.setDouble(5, livre.getPrix());
        ps.executeUpdate();   
        //System.out.println("Une erreur est survenue lors de l'ajout du livre veuillez réessayer");
    }


    public void SupprimerLivre(Livre livre) throws SQLException{
        PreparedStatement ps = this.connexion.prepareStatement("DELETE FROM LIVRE WHERE isbn = ?");
        ps.setInt(1, livre.getIsbn());
        ps.executeUpdate();
        //System.out.println("Une erreur est survenue lors de la suppression du livre veuillez réessayer");
    }

    public void majQteLivre(Livre livre, Magasin mag, int qte) throws SQLException{
        this.st = connexion.createStatement();
	  	  ResultSet r = this.st.executeQuery("select qte from LIVRE natural join POSSERDER natural join MAGASIN where isbn = "+ livre.getIsbn()+" and idmag = " + mag.getId() + "");
        qte += r.getInt("qte");

        PreparedStatement ps = this.connexion.prepareStatement("UPDATE POSSERDER SET qte = ? WHERE isbn = ?");
        ps.setInt(1, qte);
        ps.setInt(2, livre.getIsbn());
        ps.executeUpdate();
        //System.out.println("Une erreur est survenue lors de la mise à jour de la quantité veuillez réessayer");
    }

    public void afficherStockLibrairie(Magasin mag) throws SQLException{
        this.st = connexion.createStatement();
        ResultSet r = this.st.executeQuery("select isbn, titre, nbpages, datepubli, prix from LIVRE natural join POSSERDER natural join MAGASIN where idmag = "+ mag.getId());
        while(r.next()){
          Livre livreActuel = new Livre(Integer.parseInt(r.getString("isbn")), r.getString("titre"), r.getInt("nbpages"), r.getInt("datepubli"), r.getDouble("prix"));
          System.out.println(livreActuel + "/n");
        }
        //System.out.println("Une erreur est survenue lors de l'affichage du stock");
      
    }

    public void consulteStats(){

    }
    
}
