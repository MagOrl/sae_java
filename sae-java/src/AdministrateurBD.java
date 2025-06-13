import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class AdministrateurBD{

    private ConnexionMySQL connexion;
    private Statement st;

    public AdministrateurBD(ConnexionMySQL laConnexion) {
        this.connexion = laConnexion;
        try {
            laConnexion.connecter("servinfo-maria", "DBfoucher", "foucher", "foucher");
            //laConnexion.connecter("localhost", "Librairie", "Kitcat", "Maria_K|DB_2109");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public boolean connectAdmin(String identifiant, String mdp) throws SQLException{
      this.st = this.connexion.createStatement();
        ResultSet rs = this.st
                .executeQuery(
                        "SELECT * FROM CLIENT WHERE identifiant = '" + identifiant + "'and motdepasse ='" + mdp + "'");
      return rs.next();
    }

    public Administrateur trouveAdmin(String identifiant, String mdp) throws SQLException{
      Administrateur admin = null;
      
        this.st = this.connexion.createStatement();
        ResultSet rs = this.st
                  .executeQuery(
                          "SELECT * FROM CLIENT WHERE identifiant ='" + identifiant + "'" + "and motdepasse ='"+mdp+"'" );
        while(rs.next()){
          admin = new Administrateur(clientMax(), rs.getString("nomcli"), rs.getString("prenomcli"), identifiant,rs.getString("adressecli"), rs.getInt("tel"), rs.getString("email"), mdp, rs.getString("codepostal"), rs.getString("villecli"));
        }
        rs.close();
      
      return admin;
    }

    

     public int creeClient(String identif, String nom, String prenom, String adresse, String codepostal, String ville,
            String email, String tel, String mdp) throws SQLException {
        int numCli = clientMax() + 1;
        this.st = this.connexion.createStatement();
        PreparedStatement ps = this.connexion.prepareStatement("insert into CLIENT values (?,?,?,?,?,?,?,?,?,?)");
        ps.setInt(1, numCli);
        ps.setString(2, identif);
        ps.setString(3, nom);
        ps.setString(4, prenom);
        ps.setString(5, adresse);
        ps.setString(6, codepostal);
        ps.setString(7, ville);
        ps.setString(8, email);
        ps.setInt(9, Integer.parseInt(tel));
        ps.setString(10, mdp);
        ps.executeUpdate();
        return numCli;
    }

    public Vendeur CreerCompteVendeur(String nom, String prenom, String identifiant, String adresse, String tel, String email, String mdp, String codePostal, String ville, String nommag) throws SQLException, NumberFormatException{
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
      rs.close();
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
    
    public void ajouteNouvelleLibrairie(String nommag, String villemag) throws SQLException{
        Magasin magasin = new Magasin(idmagMax(), nommag, villemag);
        PreparedStatement ps = this.connexion.prepareStatement
        ("insert into MAGASIN values(?,?,?)");
        ps.setString(1, magasin.getId());
        ps.setString(2, magasin.getNom());
        ps.setString(3, magasin.getVille());
        ps.executeUpdate();
    }

    public String idmagMax() throws SQLException{
      Integer idMax = 0;
      this.st = connexion.createStatement();
      ResultSet rs = this.st.executeQuery("select max(idmag) as idMax from MAGASIN");
      while(rs.next()){
        idMax = Integer.parseInt(rs.getString("idMax"))+1;
      }
      rs.close();
      return idMax.toString();
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

    public Magasin trouveLibrairie(String nommag) throws SQLException{
      Magasin mag = null;
      this.st = connexion.createStatement();
      ResultSet rs = this.st.executeQuery("select idmag, villemag from MAGASIN where nommag =" + '"' + nommag + '"');
      while(rs.next()){
        mag = new Magasin(rs.getString("idmag"), nommag, rs.getString("villemag"));
      }
      return mag;
    }

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

        //PreparedStatement ps
        //System.out.println("Une erreur est survenue lors de l'ajout du livre veuillez réessayer");
    }

    public boolean SupprimerLivre(String isbn, Magasin mag) throws SQLException{
        this.st = connexion.createStatement();
        ResultSet rs = this.st.executeQuery("select * from POSSEDER where isbn = '" + isbn + "'" + " and idmag = '" + mag.getId() + "'");
        if(!rs.next()){
          return false;
        } 
        PreparedStatement ps = this.connexion.prepareStatement(("UPDATE POSSEDER SET qte = 0 where isbn = ? and idmag = ?"));
        ps.setString(1, isbn);
        ps.setString(2, mag.getId());
        ps.executeUpdate();
        return true;
    }

    public boolean majQteLivre(String isbn, Magasin mag, String qte) throws SQLException, NumberFormatException{
        this.st = connexion.createStatement();
	  	  ResultSet rs = this.st.executeQuery("select qte from POSSEDER where isbn = '"+ isbn + "'" + " and idmag = '" + mag.getId() + "'");
        if(!rs.next()){
          return false;
        }

        PreparedStatement ps = this.connexion.prepareStatement("UPDATE POSSEDER SET qte = ? WHERE isbn = ? and idmag = ?");
        ps.setInt(1, Integer.parseInt(qte));
        ps.setString(2, isbn);
        ps.setString(3, mag.getId());
        ps.executeUpdate();
        return true;
    }

    public void afficherStockLibrairie(Magasin mag) throws SQLException{
        this.st = connexion.createStatement();
        ResultSet rs = this.st.executeQuery("select isbn, titre, nbpages, datepubli, prix from LIVRE natural join POSSEDER natural join MAGASIN where idmag = "+ mag.getId());
        if(!rs.next()){
          System.out.println("------------------------------------------------------------");
          System.out.println("La libraire actuelle (" + mag.getNom() + ") ne contient aucun livre");
          System.out.println("------------------------------------------------------------");
        }
        while(rs.next()){
          Livre livreActuel = new Livre(rs.getString("isbn"), rs.getString("titre"), rs.getInt("nbpages"), rs.getInt("datepubli"), rs.getDouble("prix"));
          System.out.println("------------------------------------------------------------");
          System.out.println(livreActuel);
          System.out.println("------------------------------------------------------------");
        }
        //System.out.println("Une erreur est survenue lors de l'affichage du stock");
      
    }

    public void consulteStats(){

    }
    
}
