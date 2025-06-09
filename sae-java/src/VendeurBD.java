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

    public Vendeur trouveVendeur(String identifiant, String mdp, String nommag) throws SQLException{
      Vendeur vendeur = null;
      
        this.st = this.connexion.createStatement();
        ResultSet rs = this.st
                  .executeQuery(
                          "SELECT * FROM CLIENT WHERE identifiant ='" + identifiant + "'" + "and motdepasse ='"+mdp+"'" );
        while(rs.next()){
          vendeur = new Vendeur(idClientMax(), rs.getString("nomcli"), 
          rs.getString("prenomcli"), identifiant,rs.getString("adressecli"), 
          rs.getInt("tel"), rs.getString("email"), mdp, rs.getString("codepostal"), rs.getString("villecli"), trouveLibrairie(nommag, "null"));
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

    public Magasin trouveLibrairie(String nommag, String idmag) throws SQLException{
      Magasin mag = null;
      this.st = connexion.createStatement();
      if(nommag != "null"){
        ResultSet rs = this.st.executeQuery("select idmag, villemag from MAGASIN where nommag =" + '"' + nommag + '"');
        while(rs.next()){
          mag = new Magasin(rs.getString("idmag"), nommag, rs.getString("villemag"));
        }
        rs.close();
      }

      if(idmag != "null"){
        ResultSet rs = this.st.executeQuery("select nommag, villemag from MAGASIN where idmag =" + '"' + idmag + '"');
        while(rs.next()){
          mag = new Magasin(idmag, rs.getString("nommag"), rs.getString("villemag"));
        }
        rs.close();
      }
      return mag;
    }

    public Livre trouveLivre(String titre, String datepubli, String auteur) throws SQLException, NumberFormatException{
      int intDatepubli = Integer.parseInt(datepubli);
      Livre livre = null;
      this.st = connexion.createStatement();
      ResultSet rs = this.st.executeQuery("select isbn, nbpages, prix from LIVRE natural join ECRIRE natural join AUTEUR where titre = '" + titre + "' and datepubli = " + datepubli + " and nomauteur = '" + auteur +"'");
      while (rs.next()){
        livre = new Livre(rs.getString("isbn"), titre, rs.getInt("nbpages"), intDatepubli, rs.getInt("prix"));
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

    public boolean majQteLivre(String isbn, Magasin mag, int qte) throws SQLException, NumberFormatException, QteInfAZeroException{
        this.st = connexion.createStatement();
	  	  ResultSet rs = this.st.executeQuery("select qte from POSSEDER where isbn = '"+ isbn + "'" + " and idmag = '" + mag.getId() + "'");
        if(!rs.next()){
          rs.close();
          return false;
        }
        if(rs.getInt("qte") + qte < 0){
          throw new QteInfAZeroException();
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
        while(rs.next()){
          if(rs.getInt("qte") >= Integer.parseInt(qte)){
            rs.close();
            return true;
          }
        }
        rs.close();
        return false;
    }

    public boolean transfererLivreCommande(Livre livre, int qteAtransferer, Magasin mag) throws SQLException, NumberFormatException, QteInfAZeroException{
      String idmagAutreLibrairie = null;
      this.st = connexion.createStatement();
      ResultSet rs = this.st.executeQuery("select isbn, idmag, qte from POSSEDER where isbn = " + livre.getIsbn());
      while (rs.next()){
        if(rs.getString("idmag") != mag.getId())
          if(rs.getInt("qte") >= qteAtransferer){
              idmagAutreLibrairie = rs.getString("idmag");
              break;
          }      
      }
      rs.close();
      
      majQteLivre(livre.getIsbn(), mag, qteAtransferer);
      majQteLivre(livre.getIsbn(), trouveLibrairie("null", idmagAutreLibrairie), -qteAtransferer);
      return true;
       
      
    }

    public void passerCommandeClient(){}
}


 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 //Ajouter un livre au stock de sa librairie.
 //Mettre à jour la quantité disponible d’un livre.
 //Vérifier la disponibilité d’un livre dans une librairie.
 //Passer une commande pour un client en magasin.
 //Transférer un livre d’une autre librairie pour satisfaire une commande client