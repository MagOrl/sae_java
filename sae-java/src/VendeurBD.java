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
          vendeur = new Vendeur(idClientMax(), rs.getString("nomcli"), 
          rs.getString("prenomcli"), identifiant,rs.getString("adressecli"), 
          rs.getInt("tel"), rs.getString("email"), mdp, rs.getString("codepostal"), rs.getString("villecli"), trouveLibrairie(mag));
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
      rs.close();
      return mag;
    }

    public Livre trouveLivre(String titre, String nbPages, String datepubli) throws SQLException, NumberFormatException{
      int intNbPages = Integer.parseInt(nbPages);
      int intDatepubli = Integer.parseInt(datepubli);
      Livre livre = null;
      this.st = connexion.createStatement();
      ResultSet rs = this.st.executeQuery("select isbn, prix from LIVRE where titre = '" + titre + "' and nbpages = " + nbPages + " and datepubli = " + datepubli);
      while (rs.next()){
        livre = new Livre(rs.getString("isbn"), titre, intNbPages, intDatepubli, rs.getInt("prix"));
      }
      rs.close();
      return livre;
    }

    public List<String> choixLibrairie() throws SQLException{
      List<String> lesLibrairies = new ArrayList<>();
      this.st = connexion.createStatement();
      ResultSet rs = this.st.executeQuery("select nommag from MAGASIN");
      while(rs.next()){
        lesLibrairies.add(rs.getString("nommag"));
      }
      rs.close();
      return lesLibrairies;
    }

    //a réparer
    public void AjouterLivre(String isbn, String titre, String auteur, String editeur, String theme, String nbpages, String datepubli, String prix, String qte, Magasin mag) throws SQLException{
        Livre livre = new Livre(isbn, titre, Integer.parseInt(nbpages), Integer.parseInt(datepubli), Double.parseDouble(prix));

        PreparedStatement psLivre = this.connexion.prepareStatement("insert ignore into LIVRE values(?,?,?,?,?)");
        psLivre.setString(1, livre.getIsbn());   
        psLivre.setString(2, livre.getTitre()); 
        psLivre.setInt(3, livre.getNbPages());   
        psLivre.setInt(4, (livre.getDatePubli()));   
        psLivre.setDouble(5, livre.getPrix());
        psLivre.executeUpdate();
        
        PreparedStatement psPosseder = this.connexion.prepareStatement("insert into POSSEDER values(?,?,?)");
        psPosseder.setString(1, mag.getId());
        psPosseder.setString(2, isbn);
        psPosseder.setInt(3, Integer.parseInt(qte));
    }

    public boolean majQteLivre(String isbn, Magasin mag, int qte) throws SQLException, NumberFormatException{
        this.st = connexion.createStatement();
	  	  ResultSet rs = this.st.executeQuery("select qte from POSSEDER where isbn = '"+ isbn + "'" + " and idmag = '" + mag.getId() + "'");
        if(!rs.next()){
          rs.close();
          return false;
        }

        PreparedStatement ps = this.connexion.prepareStatement("UPDATE POSSEDER SET qte = qte + ? WHERE isbn = ? and idmag = ?");
        ps.setInt(1, qte);
        ps.setString(2, isbn);
        ps.setString(3, mag.getId());
        ps.executeUpdate();
        rs.close();
        return true;
    }

    public boolean verifDispoLivre(Livre livre, String qte, Magasin mag) throws SQLException{
        this.st = connexion.createStatement();
        ResultSet rs = this.st.executeQuery("select qte from POSSEDER where isbn = " + livre.getIsbn() + " and idmag = " + mag.getId());
        if(rs.getInt("qte") >= Integer.parseInt(qte)){
          rs.close();
          return true;
        }
        rs.close();
        return false;
    }

    public boolean transfererLivreCommande(Livre livre, String qteAtransferer, Magasin mag) throws SQLException{
      Livre livreAutreLibrairie = null;
      String idmagAutreLibrairie = null;
      int qteAutreLivre = 0;
      boolean transfertPossible = false;
      int nouvQteLivrePara = 0;
      this.st = connexion.createStatement();
      ResultSet rs = this.st.executeQuery("select isbn, idmag, qte from POSSEDER where isbn = " + livre.getIsbn());
      while (rs.next()){
        if(rs.getString("idmag") != mag.getId())
          if(rs.getInt("qte") >= Integer.parseInt(qteAtransferer)){
              livreAutreLibrairie = new Livre(livre.getIsbn(), livre.getTitre(), livre.getNbPages(), livre.getDatePubli(), livre.getPrix());
              qteAutreLivre = rs.getInt("qte");
              idmagAutreLibrairie = rs.getString("idmag");
              break;
          }      
      }
      rs.close();
      
      ResultSet rs2 = this.st.executeQuery("select qte from POSSEDER where isbn = " + livre.getIsbn() + " and idmag = "+ mag.getId());
      nouvQteLivrePara = rs2.getInt("qte") + Integer.parseInt(qteAtransferer);

      qteAutreLivre -= Integer.parseInt(qteAtransferer);  
       
      PreparedStatement psLivrePara = this.connexion.prepareStatement("UPDATE POSSEDER set qte = ? where isbn = ? and idmag = ?");
      psLivrePara.setInt(1, nouvQteLivrePara);
      psLivrePara.setString(2, livre.getIsbn());
      psLivrePara.setString(3, mag.getId());
      psLivrePara.executeUpdate();

      PreparedStatement psautreLivre = this.connexion.prepareStatement("UPDATE POSSEDER set qte = ? where isbn = ? and idmag = ?")
      psautreLivre.setInt(1, qteAutreLivre);
      psautreLivre.setString(2, livreAutreLibrairie.getIsbn());
      psautreLivre.setString(3, idmagAutreLibrairie);
      psautreLivre.executeUpdate();
    }

    public void passerCommandeClient(){}
}


 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 //Ajouter un livre au stock de sa librairie.
 //Mettre à jour la quantité disponible d’un livre.
 //Vérifier la disponibilité d’un livre dans une librairie.
 //Passer une commande pour un client en magasin.
 //Transférer un livre d’une autre librairie pour satisfaire une commande client