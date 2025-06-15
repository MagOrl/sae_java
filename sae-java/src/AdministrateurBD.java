import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

public class AdministrateurBD{

    private ConnexionMySQL connexion;
    private Statement st;

    public AdministrateurBD(ConnexionMySQL laConnexion) {
        this.connexion = laConnexion;
        try {
            //laConnexion.connecter("servinfo-maria", "DBfoucher", "foucher", "foucher");
            // laConnexion.connecter("localhost", "Librairie", "Kitcat", "Maria_K|DB_2109");
            laConnexion.connecter("localhost", "Librairie", "root", "mypassword");

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
        mag = new Magasin(rs.getInt("idmag"), nommag, rs.getString("villemag"));
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
        ps.setInt(1, magasin.getId());
        ps.setString(2, magasin.getNom());
        ps.setString(3, magasin.getVille());
        ps.executeUpdate();
    }

    public int idmagMax() throws SQLException{
      Integer idMax = 0;
      this.st = connexion.createStatement();
      ResultSet rs = this.st.executeQuery("select max(idmag) as idMax from MAGASIN");
      while(rs.next()){
        idMax = rs.getInt("idMax")+1;
      }
      rs.close();
      return idMax;
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
        mag = new Magasin(rs.getInt("idmag"), nommag, rs.getString("villemag"));
      }
      return mag;
    }

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
        psPosseder.setInt(1, mag.getId());
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
        ps.setInt(2, mag.getId());
        ps.executeUpdate();
        return true;
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
        ps.setInt(3, mag.getId());
        ps.executeUpdate();
        rs.close();
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
          Livre livreActuel = new Livre(rs.getString("isbn"), rs.getString("titre"), rs.getInt("nbpages"), rs.getString("datepubli"), rs.getDouble("prix"),rs.getInt("qte"));
          System.out.println("------------------------------------------------------------");
          System.out.println(livreActuel);
          System.out.println("------------------------------------------------------------");
        }
        //System.out.println("Une erreur est survenue lors de l'affichage du stock");
      
    }

    public void requeteNbLivresMagAnnee() throws SQLException{
      String anneePrec = null;
      this.st= connexion.createStatement();
      ResultSet rs = this.st.executeQuery("select distinct nommag as Magasin, YEAR(datecom) as annee, sum(qte) as qte from MAGASIN natural join COMMANDE natural join DETAILCOMMANDE group by Magasin, annee order by annee");
      
      System.out.println("\n");
      System.out.println("Nombre de livres vendus par magasins par anneé");
      while(rs.next()){
        if(anneePrec == null || !anneePrec.equals(rs.getString("annee"))){
          System.out.println("---------------------------------------------------------------------");
          System.out.println("Année  " + rs.getString("annee"));
          
        }
        System.out.println("-----------------------------------------");
        System.out.println("Magasin " + rs.getString("Magasin"));
        System.out.println(rs.getString("qte") + " livres vendus");

        
        anneePrec = rs.getString("annee");
      }
      System.out.println("-----------------------------------------");
      System.out.println("---------------------------------------------------------------------");
    }

    public void requeteCAThemeAnnee(int annee) throws SQLException{
      this.st = connexion.createStatement();
      ResultSet rs = this.st.executeQuery("with CA2024 as (select sum(qte*prixvente) as total from DETAILCOMMANDE natural join COMMANDE  where YEAR(datecom) = " + annee+ ") select nomclass as Theme, ROUND((sum(qte*prixvente)/Total)*100) as total from CA2024 natural join DETAILCOMMANDE natural join COMMANDE natural join LIVRE natural join THEMES natural join CLASSIFICATION where YEAR(datecom) = " + annee + " group by LEFT(LPAD(iddewey,3,'0'),1) order by nomclass");

      System.out.println("\n");
      System.out.println("Pourcentage du chiffre d'affaire par thème en " + annee);
      while(rs.next()){
        System.out.println("-----------------------------------");
        System.out.println(rs.getString("Theme") + " " + rs.getString("total") + "%");
      }
      System.out.println("-----------------------------------");
    }
    
    public void requeteEvoCAMag(int annee) throws SQLException{
      int cptMois = 0;
      this.st = connexion.createStatement();
      ResultSet rs = this.st.executeQuery("select MONTH(datecom) as mois, nommag as Magasin, sum(qte*prix) as CA from MAGASIN natural join COMMANDE natural join DETAILCOMMANDE natural join LIVRE where YEAR(datecom) = " + annee + " group by nommag, MONTH(datecom)");
      List<String> mois = Arrays.asList("Janvier", "Février", "Mars", "Avril", "Mai", "Juin", "Juillet", "Aout", "Septembre", "Octobre", "Novembre", "Décembre");
      System.out.println("\n");
      System.out.println("Evolution des chiffres d'affaire par magasin et par mois pour l'année " + annee);
      while(rs.next()){
        if(cptMois == 12){
          cptMois = 0;
        }
        if(cptMois == 0){
          System.out.println("-----------------------------------------");
          System.out.println("Magasin " + rs.getString("Magasin"));
        }
        System.out.println("-----------");
        System.out.println("Mois de " + mois.get(cptMois) + " chiffre d'affaire : " + rs.getInt("CA") + " euros");
        

        cptMois ++;
      }
      System.out.println("-----------------------------------------");
      System.out.println("---------------------------------------------------------------------");
    
    }

    public void requeteCompVenteLMAnnee() throws SQLException{
      String anneePrec = null;
      this.st = connexion.createStatement();
      ResultSet rs = this.st.executeQuery("select YEAR(datecom) as annee, enligne as typevente, sum(qte*prix) as montant from COMMANDE natural join DETAILCOMMANDE natural join LIVRE where YEAR(datecom)<>2025 group by annee, typevente");
      System.out.println("\n");
      System.out.println("Comparaison chiffre d'affaire entre ventes en ligne et ventes en magasin");
      System.out.println("------------------------------------------------------------");
      while(rs.next()){
        if(anneePrec == null || !anneePrec.equals(rs.getString("annee"))){
          System.out.println("----------------------------------------------");
          System.out.println("Année  " + rs.getString("annee"));
          System.out.println("-----------------------------");
          
        }
        if(rs.getString("typevente").equals("O")){
          System.out.println("Ventes en ligne : " + rs.getInt("montant") + " euros");
        }else{
          System.out.println("Ventes en Magasin : " + rs.getInt("montant") + " euros");
        }
        System.out.println("--------------------------------");
        anneePrec = rs.getString("annee");

      }
      System.out.println("------------------------------------------------------------");
    }

    public void requeteDixeditPlusImportants() throws SQLException{
      this.st = connexion.createStatement();
      ResultSet rs = this.st.executeQuery("select nomedit as Editeur, count(idauteur) as nbauteurs from EDITEUR natural join EDITER natural join LIVRE natural join ECRIRE natural join AUTEUR group by nomedit order by nbauteurs desc limit 10");
      int cpt = 1;
      System.out.println("\n");
      System.out.println("Les Dix éditeurs les plus importants en nombre d'auteur");
      while (rs.next()) {
        System.out.println("----------------------------------");
        System.out.println(cpt + ". " + rs.getString("Editeur") + " " + rs.getInt("nbauteurs") + " auteurs");
        cpt++;
      }
      System.out.println("----------------------------------");
    }

    public void requeteOrigineClientAuteur(String auteur) throws SQLException{
      this.st = connexion.createStatement();
      ResultSet rs = this.st.executeQuery("select villecli as ville, sum(qte) as qte from CLIENT natural join COMMANDE natural join DETAILCOMMANDE natural join LIVRE natural join ECRIRE natural join AUTEUR where nomauteur = '" + auteur + "' group by villecli");

      System.out.println("\n");
      System.out.println("Origine des clients ayant acheté des livres de " + auteur);
      while (rs.next()) {
        System.out.println("--------------------");
        if(rs.getInt("qte") > 1){
          System.out.println(rs.getString("ville") + ": " + rs.getString("qte") + " clients");
        }else{
          System.out.println(rs.getString("ville") + ": " + rs.getString("qte") + " client");
        }
      }
      System.out.println("--------------------");
    }

    public void requeteValeurStockMag() throws SQLException{
      this.st = connexion.createStatement();
      ResultSet rs = this.st.executeQuery("select nommag as Magasin, sum(qte*prix) as total from MAGASIN natural join POSSEDER natural join LIVRE group by nommag");

      System.out.println("\n");
      System.out.println("Valeur du stock par magasin");
      System.out.println("--------------------------------");
      while (rs.next()) {
         System.out.println("------------");
         System.out.println("Magasin " + rs.getString("Magasin") + " possède " + rs.getInt("total") + " livres"); 
      }
      System.out.println("------------");
      System.out.println("--------------------------------");
    }

    public void requeteEvoCAClient() throws SQLException{
      this.st = connexion.createStatement();
      ResultSet rs = this.st.executeQuery("with MaxCAParClient as (select idcli, YEAR(datecom) as annee, sum(qte*prixvente) as CA from CLIENT natural join COMMANDE natural join DETAILCOMMANDE natural join LIVRE group by YEAR(datecom), idcli) select annee, max(CA) as maximum, min(CA) as minimum, avg(CA) as moyenne from MaxCAParClient group by annee");

      System.out.println("\n");
      System.out.println("Evolution du chiffre d'affaire par client par année");
      System.out.println("--------------------------------------------------");
      while (rs.next()) {
        System.out.println("--------------");
        System.out.println("Annee " + rs.getString("annee"));
        System.out.println("-------------");
        System.out.println("Chiffre d'affaire maximum : " + rs.getDouble("maximum") + " euros");
        System.out.println("Chiffre d'affaire moyen : " + rs.getDouble("moyenne") + " euros");
        System.out.println("Chiffre d'affaire minimum : " + rs.getDouble("minimum") + " euros");
      }
      System.out.println("--------------");
      System.out.println("--------------------------------------------------");
    }

}


    
    

 
