import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class VendeurBD{

    private ConnexionMySQL connexion;
    private Statement st;
    
    public VendeurBD(ConnexionMySQL laConnexion) {
        this.connexion = laConnexion;
        try {
            //laConnexion.connecter("servinfo-maria", "DBfoucher", "foucher", "foucher");
            laConnexion.connecter("localhost", "Librairie", "Kitcat", "Maria_K|DB_2109");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean connectVendeur(String identifiant, String mdp) throws SQLException{
      this.st = this.connexion.createStatement();
        ResultSet rs = this.st
                .executeQuery(
                        "SELECT * FROM CLIENT WHERE identifiant = '" + identifiant + "'and motdepasse ='" + mdp + "'");
      return rs.next();
    }

    public Vendeur trouveVendeur(String identifiant, String mdp, String mag) throws SQLException{
      Vendeur vendeur = null;
      
        this.st = this.connexion.createStatement();
        ResultSet rs = this.st
                  .executeQuery(
                          "SELECT * FROM CLIENT WHERE identifiant ='" + identifiant + "'" + "and motdepasse ='"+mdp+"'" );
        while(rs.next()){
          vendeur = new Vendeur(idClientMax(), rs.getString("nomcli"), rs.getString("prenomcli"), identifiant,rs.getString("adressecli"), rs.getInt("tel"), rs.getString("email"), mdp, rs.getString("codepostal"), rs.getString("villecli"), trouveLibrairie(mag));
        }
        rs.close();
      
      return vendeur;
    }

    public int idClientMax() throws SQLException {
        int max = 0;
        this.st = this.connexion.createStatement();
        ResultSet rs = this.st.executeQuery("select max(idcli) nbclimax from CLIENT");
        while (rs.next()) {
            max = rs.getInt("nbclimax");
        }
        rs.close();
        return max;
    }

    public Magasin trouveLibrairie(String nommag) throws SQLException{
      Magasin mag = null;
      this.st = connexion.createStatement();
      ResultSet rs = this.st.executeQuery("select idmag, villemag from MAGASIN where nommag =" + '"' + nommag + '"');
      while(rs.next()){
        mag = new Magasin(rs.getString("idmag"), nommag, rs.getString("villemag"));
      }
      return mag;
    }
}


 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 //Ajouter un livre au stock de sa librairie.
 //Mettre à jour la quantité disponible d’un livre.
 //Vérifier la disponibilité d’un livre dans une librairie.
 //Passer une commande pour un client en magasin.
 //Transférer un livre d’une autre librairie pour satisfaire une commande client