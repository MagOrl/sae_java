import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class VendeurBD{

    private ConnexionMySQL connexion;
    private Statement st;
    
    public VendeurBD(ConnexionMySQL laConnexion) {
        this.connexion = laConnexion;
        try {
            //laConnexion.connecter("servinfo-maria", "DBfoucher", "foucher", "foucher");
            // laConnexion.connecter("localhost", "Librairie", "Kitcat", "Maria_K|DB_2109");
            laConnexion.connecter("localhost", "Librairie", "root", "mypassword");


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
          rs.getInt("tel"), rs.getString("email"), mdp, rs.getString("codepostal"), rs.getString("villecli"), trouveLibrairie(nommag, -1));
        }
        rs.close();
      
      return vendeur;
    }

    public Client trouveClient(String identif, String mdp) throws SQLException {
        Client cli = null;
        this.st = this.connexion.createStatement();
        ResultSet rs = this.st.executeQuery(
                "SELECT * FROM CLIENT WHERE identifiant ='" + identif + "'" + "and motdepasse ='" + mdp + "'");
        while (rs.next()) {
            cli = new Client(rs.getInt("idcli"), rs.getString("nomcli"), rs.getString("prenomcli"),
                    rs.getString("identifiant"),
                    rs.getString("adressecli"), rs.getInt("tel"), rs.getString("email"), rs.getString("motdepasse"),
                    rs.getString("codepostal"), rs.getString("villecli"));
        }
        rs.close();
        return cli;
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

    public Magasin trouveLibrairie(String nommag, int idmag) throws SQLException{
      Magasin mag = null;
      this.st = connexion.createStatement();
      if(nommag != "null"){
        ResultSet rs = this.st.executeQuery("select idmag, villemag from MAGASIN where nommag =" + '"' + nommag + '"');
        while(rs.next()){
          mag = new Magasin(rs.getInt("idmag"), nommag, rs.getString("villemag"));
        }
        rs.close();
      }

      if(idmag != -1){
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
        livre = new Livre(rs.getString("isbn"), titre, rs.getInt("nbpages"), rs.getString("Datepubli"), rs.getInt("prix"),rs.getInt("qte"));
      }
      rs.close();
      return livre;
    }

    public Livre trouveLivreIsbn(String isbn) throws SQLException{
      Livre livre = null;
      this.st = connexion.createStatement();
      ResultSet rs = this.st.executeQuery("select * from LIVRE natural join POSSEDER where isbn = " + isbn);
      while(rs.next()){
          livre = new Livre(rs.getString("isbn"), rs.getString("titre"), rs.getInt("nbpages"), rs.getString("datepubli"), rs.getInt("prix"),rs.getInt("qte"));
      }
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
        Livre livre = new Livre(isbn, titre, Integer.parseInt(nbpages), datepubli, Double.parseDouble(prix),Integer.parseInt(qte));

        PreparedStatement psLivre = this.connexion.prepareStatement("insert ignore into LIVRE values(?,?,?,?,?)");
        psLivre.setString(1, livre.getIsbn());   
        psLivre.setString(2, livre.getTitre()); 
        psLivre.setInt(3, livre.getNbPages());   
        psLivre.setString(4, (livre.getDatePubli()));   
        psLivre.setDouble(5, livre.getPrix());
        psLivre.executeUpdate();
        
        PreparedStatement psPosseder = this.connexion.prepareStatement("insert into POSSEDER values(?,?,?)");
        psPosseder.setString(1, mag.getId()+"");
        psPosseder.setString(2, isbn);
        psPosseder.setInt(3, Integer.parseInt(qte));
        psPosseder.executeUpdate();

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
        ps.setString(3, mag.getId()+"");
        ps.executeUpdate();
        rs.close();
        return true;
    }

    public boolean verifDispoLivre(Livre livre, int qte, Magasin mag) throws SQLException, NumberFormatException{
        this.st = connexion.createStatement();
        ResultSet rs = this.st.executeQuery("select qte from POSSEDER where isbn = " + livre.getIsbn() + " and idmag = " + mag.getId());
        while(rs.next()){
          if(rs.getInt("qte") >= qte){
            rs.close();
            return true;
          }
        }
        rs.close();
        return false;
    }

    public boolean transfererLivreCommande(Livre livre, int qteAtransferer, Magasin mag) throws SQLException, NumberFormatException, QteInfAZeroException{
      int idmagAutreLibrairie = -1;
      this.st = connexion.createStatement();
      ResultSet rs = this.st.executeQuery("select isbn, idmag, qte from POSSEDER where isbn = " + livre.getIsbn());
      while (rs.next()){
        if(!rs.getString("idmag").equals(mag.getId())){
          if(verifDispoLivre(livre, qteAtransferer, trouveLibrairie("null", rs.getInt("idmag")))){
              idmagAutreLibrairie = rs.getInt("idmag");
              break;
          }  
        }    
      }
      rs.close();

      if(idmagAutreLibrairie == -1){
        return false;
      }
      if(majQteLivre(livre.getIsbn(), mag, qteAtransferer) == false){
        PreparedStatement ps = this.connexion.prepareStatement("insert into POSSEDER values (?,?,?)");
        ps.setString(1, mag.getId()+"");
        ps.setString(2, livre.getIsbn());
        ps.setInt(3, qteAtransferer);
        ps.executeUpdate();
      }else{
        trouveLibrairie("null", idmagAutreLibrairie);
        majQteLivre(livre.getIsbn(), trouveLibrairie("null", idmagAutreLibrairie), -qteAtransferer);
      }
      return true;
       
      
    }

    public int numcomMax() throws SQLException {
        int max = 0;
        this.st = this.connexion.createStatement();
        ResultSet rs = this.st.executeQuery("select max(numcom) numcomMax from COMMANDE");
        while (rs.next()) {
            max = rs.getInt("numcomMax");
        }
        rs.close();
        return max+1;
    }

    public boolean passerCommandeClient(Client cli, Map<Livre, Integer> commande, Magasin mag) throws SQLException, NumberFormatException{
      boolean res = false;
      int numlig = 0;
      int numcom = numcomMax();
      Livre livreActuel = null;

      PreparedStatement psCommande = this.connexion.prepareStatement("insert ignore into COMMANDE values(?,CURDATE(),?,?,?,?)");
      PreparedStatement psDetailCommande = this.connexion.prepareStatement("insert into DETAILCOMMANDE values(?,?,?,?,?)");
      PreparedStatement psDeleteCommande = this.connexion.prepareStatement("DELETE from COMMANDE where numcom = ?");
      PreparedStatement psDeleteDetailCommande = this.connexion.prepareStatement("DELETE from DETAILCOMMANDE where numcom = ?");
      try{
        for(Livre livre : commande.keySet()){
          livreActuel = livre;
          if(verifDispoLivre(livre, commande.get(livre), mag)){
              numlig += 1;
              psCommande.setInt(1, numcom);
              psCommande.setString(2, "N");
              psCommande.setString(3, "M");
              psCommande.setString(4, cli.getIdentifiant());
              psCommande.setString(5, mag.getId()+"");
              psCommande.executeUpdate();


              psDetailCommande.setInt(1, numcom);
              psDetailCommande.setInt(2, numlig);
              psDetailCommande.setInt(3, commande.get(livre));
              psDetailCommande.setDouble(4, livre.getPrix());
              psDetailCommande.setString(5, livre.getIsbn());
              psDetailCommande.executeUpdate();

              majQteLivre(livre.getIsbn(), mag, -commande.get(livre));
              res = true;

          }else if(transfererLivreCommande(livre, commande.get(livre), mag)){
              numlig += 1;
              psCommande.setInt(1, numcom);
              psCommande.setString(2, "N");
              psCommande.setString(3, "M");
              psCommande.setString(4, cli.getIdentifiant());
              psCommande.setString(5, mag.getId()+"");
              psCommande.executeUpdate();


              psDetailCommande.setInt(1, numcom);
              psDetailCommande.setInt(2, numlig);
              psDetailCommande.setInt(3, commande.get(livre));
              psDetailCommande.setDouble(4, livre.getPrix());
              psDetailCommande.setString(5, livre.getIsbn());
              psDetailCommande.executeUpdate();
              
              majQteLivre(livre.getIsbn(), mag, -commande.get(livre));
              res = true;
          
          }else{
              psDeleteCommande.setInt(1, numcom);
              psDeleteDetailCommande.setInt(1, numcom);

              psDeleteDetailCommande.executeUpdate();
              psDeleteCommande.executeUpdate();

              System.out.println("Le livre: " + livreActuel.getTitre() + " n'est pas disponible, commande impossible");
              return false;
          }
       }
    }catch(QteInfAZeroException e){
      System.out.println("Le livre: " + livreActuel.getTitre() + " n'est pas disponible, commande impossible");
    }
      return res;
    }

    public int trouverDerniereCommande() throws SQLException{
      this.st = connexion.createStatement();
      ResultSet rs = this.st.executeQuery("select max(numcom) as numComMax from COMMANDE");
      if(rs.next()){
        return rs.getInt("numComMax");
      }
      return 0;
    }

    public boolean editerFacture(Client cli, int numCom) throws SQLException{
      int totalQteLivre = 0;
      double prixTotal = 0.;
      boolean fisrtLigne = false;
      System.out.println("Facture n°" + numCom);
      System.out.println(cli.getNom() + " " + cli.getPrenom());
      this.st = connexion.createStatement();
      ResultSet rsDateCom = this.st.executeQuery("select datecom from COMMANDE natural join DETAILCOMMANDE where numcom = " + numCom);
      if(rsDateCom.next()){
        System.out.println("Date " + rsDateCom.getString("datecom"));
      }else{
        return false;
      }
      ResultSet rsCommande = this.st.executeQuery("select numcom, idcli, numlig, qte, prixvente, isbn from COMMANDE natural join DETAILCOMMANDE where numcom = " + numCom);
      while(rsCommande.next()){
        Livre livreCourant = trouveLivreIsbn(rsCommande.getString("isbn"));
        totalQteLivre += rsCommande.getInt("qte");
        prixTotal += rsCommande.getDouble("prixvente");
        if(!fisrtLigne){
          System.out.println("---------------------------------------------------------------------------------------------");
          System.out.printf("%5s  %-15s  %-30s  %5s  %8s  %10s\n", "", "ISBN", "Titre", "Qte", "Prix", "Total");
          fisrtLigne = true;
        }
        System.out.printf("%5d  %-15s  %-30s  %5d  %8.2f  %10.2f\n", rsCommande.getInt("numlig"),  livreCourant.getIsbn(), livreCourant.getTitre(), rsCommande.getInt("qte"), rsCommande.getDouble("prixvente"), rsCommande.getInt("qte") * rsCommande.getDouble("prixvente"));
      }
      System.out.println("---------------------------------------------------------------------------------------------");
      System.out.println("Nombres de livres commandés " + totalQteLivre);
      System.out.print("Montant de la facture " + prixTotal);
      return true;
    }
}
