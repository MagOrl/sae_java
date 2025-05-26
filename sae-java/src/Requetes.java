
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Requetes {

    private ConnexionMySQL laConnexion;
    private Statement st;

    public Requetes(ConnexionMySQL laConnexion) {
        this.laConnexion = laConnexion;
        try {
            laConnexion.connecter("localhost", "Librairie", "root", "mypassword");
            // laConnexion.connecter("servinfo-maria", "DBarsamerzoev", "arsamerzoev",
            // "arsamerzoev");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public int creeClient(String identif, String nom, String prenom, String adresse, String codepostal, String ville,
            String email, String tel, String mdp) throws SQLException {
        int numCli = clientMax() + 1;
        this.st = this.laConnexion.createStatement();
        PreparedStatement ps = this.laConnexion.prepareStatement("insert into CLIENT values (?,?,?,?,?,?,?,?,?,?)");
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

    public Client trouveClient(int numcli) throws SQLException {
        Client cli = new Client();
        this.st = this.laConnexion.createStatement();
        ResultSet rs = this.st.executeQuery("SELECT * FROM CLIENT WHERE numcli =" + numcli);
        while (rs.next()) {
            cli = new Client(numcli, rs.getString("nomcli"), rs.getString("prenomcli"), rs.getString("identifiant"),
                    rs.getString("adressecli"), rs.getInt("tel"), rs.getString("email"), rs.getString("motdepasse"),
                    rs.getString("codepostal"), rs.getString("villecli"));
        }
        rs.close();
        return cli;
    }

    public Client trouveClient(String identif, String mdp) throws SQLException {
        Client cli = new Client();
        this.st = this.laConnexion.createStatement();
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

    public boolean connectClient(String identif, String mdp) throws SQLException {
        this.st = this.laConnexion.createStatement();
        ResultSet rs = this.st
                .executeQuery(
                        "SELECT * FROM CLIENT WHERE identifiant = '" + identif + "'and motdepasse ='" + mdp + "'");
        return rs.next();
    }

    public int clientMax() throws SQLException {
        int max = 0;
        this.st = this.laConnexion.createStatement();
        ResultSet rs = this.st.executeQuery("select max(idcli) nbclimax from CLIENT");
        while (rs.next()) {
            max = rs.getInt("nbclimax");
        }
        rs.close();
        return max;
    }

    public void majClient(Client cli) throws SQLException {
        PreparedStatement ps = this.laConnexion
                .prepareStatement("UPDATE CLIENT SET identifiant = ?, motdepasse= ?, email = ?,tel =? WHERE idcli = ?");
        ps.setString(1, cli.getIdentifiant());
        ps.setString(2, cli.getMdp());
        ps.setString(3, cli.getEmail());
        ps.setInt(4, cli.getTel());
        ps.setInt(5, cli.getNumCompte());
        ps.executeUpdate();
    }

    public String afficheHistoriqueCommande(Client cli) throws SQLException {
        this.st = laConnexion.createStatement();
        ResultSet rs = this.st.executeQuery(
                "select numcom,numlig,datecom, enligne, livraison,titre,qte,prixvente FROM COMMANDE NATURAL JOIN DETAILCOMMANDE NATURAL JOIN LIVRE WHERE idcli = "
                        + cli.getNumCompte() + " ORDER BY datecom");
        if (!rs.next()) {
            return "Aucune commande effectuer pour le moment";
        }
        rs.beforeFirst();
        int numcomSave = -1;
        String res = "";
        int cpt = 0;
        while (rs.next()) {
            String enligne = rs.getString("enLigne").equals("O") ? "en ligne" : "en magasin";
            String livraison = rs.getString("livraison").equals("M") ? "récuperer au magasin" : "livrer au domicile";
            if (rs.getInt("numcom") == numcomSave) {
                res += "\n" + rs.getString("numlig") + "  " + rs.getString("titre") + "  " + rs.getInt("prixvente")
                        + "€  quantité : " + rs.getInt("qte");
                cpt = cpt + (rs.getInt("prixvente") * rs.getInt("qte"));
            } else {
                if (numcomSave != -1) {
                    res += "\nprix total : " + cpt + " €";
                    cpt = 0;
                }
                numcomSave = rs.getInt("numcom");
                res += "\n \nLa commande " + rs.getInt("numcom") + "\n"
                        + "le " + rs.getString("datecom") + " " + enligne + " " + livraison + "\n";
                res += rs.getString("numlig") + "  " + rs.getString("titre") + "  " + rs.getInt("prixvente")
                        + "€  quantité : " + rs.getInt("qte");
                cpt = cpt + (rs.getInt("prixvente") * rs.getInt("qte"));
            }
        }
        rs.close();
        res += "\nprix total : " + cpt + " €";
        return res;
    }

    public HashMap<Integer, String> afficheThemes() throws SQLException {
        this.st = laConnexion.createStatement();
        ResultSet rs = this.st.executeQuery("SELECT * FROM CLASSIFICATION GROUP BY FLOOR(iddewey/100)");
        HashMap<Integer, String> res = new HashMap<>();
        int i = 0;
        while (rs.next()) {
            res.put(i, rs.getString("nomclass"));
            i++;
        }
        rs.close();
        return res;

    }

    public List<List<Livre>> rechercheTheme(int thm, Magasin mag) throws SQLException {
        thm = thm * 100;
        this.st = laConnexion.createStatement();
        String query = "SELECT LIVRE.*,qte FROM LIVRE NATURAL JOIN THEMES NATURAL JOIN POSSEDER WHERE iddewey>="
                + thm + " and iddewey<=" + (thm + 90) + " and idmag =" + mag.getId();
        ResultSet rs = this.st.executeQuery(query);
        List<List<Livre>> catalogue = new ArrayList<>();
        int taille = nbLigneRequetes(rs);
        for (int i = 0; i < taille; ++i) {
            catalogue.add(new ArrayList<>());
            for (int y = 0; y < 10; ++y) {
                if (rs.next()) {
                    catalogue.get(i).add(new Livre(rs.getString("isbn"), rs.getString("titre"), rs.getInt("nbpages"),
                            rs.getString("datepubli"), rs.getInt("prix"), rs.getInt("qte")));
                }
            }
        }
        rs.close();
        taille = catalogue.size() - 1;
        while (taille >= 0) {
            if (catalogue.get(taille).isEmpty())
                catalogue.remove(catalogue.get(taille));
            --taille;
        }
        return catalogue;

    }

    public int nbLigneRequetes(ResultSet rs) throws SQLException {
        int res = 0;
        while (rs.next()) {
            ++res;
        }
        rs.first();
        return res;
    }

    public void commandeLivre(List<Livre> livres, Client cli, String envoie) throws SQLException {
        PreparedStatement ps = null;
        PreparedStatement ps2 = this.laConnexion
                .prepareStatement("insert into DETAILCOMMANDE values (?,?,?,?,?)");
        int numco = getMaxCommande();
        for (Livre liv : livres) {
            ps = this.laConnexion
                    .prepareStatement("UPDATE POSSEDER SET qte = ? WHERE isbn = ?");
            ps.setInt(1, getQteLivre(liv) - liv.getQte());
            ps.setString(2, liv.getIsbn());
            ps.executeUpdate();
        }
        HashMap<Integer, List<Livre>> livreMag = classeLivreSelonMag(livres);
        for (Integer idmag : livreMag.keySet()) {
            numco++;
            ps = this.laConnexion
                    .prepareStatement("insert into COMMANDE values (?,?,?,?,?,?)");
            ps.setInt(1, numco);
            ps.setDate(2, new Date(System.currentTimeMillis()));
            ps.setString(3, "O");
            ps.setString(4, envoie);
            ps.setInt(5, cli.getNumCompte());
            ps.setInt(6, idmag);
            ps.execute();
            for (int i = 0; i < livreMag.get(idmag).size(); ++i) {
                ps2.setInt(1, numco);
                ps2.setInt(2, i + 1);
                ps2.setInt(3, livreMag.get(livreMag).get(i).getQte());
                ps2.setDouble(4, livreMag.get(livreMag).get(i).getPrix());
                ps2.setString(5, livreMag.get(livreMag).get(i).getIsbn());
                ps2.execute();
                ps.executeUpdate();
            }
        }
        ps2.executeUpdate();
        livres.removeAll(livres);
    }

    public HashMap<Integer, Magasin> afficheMagasin() throws SQLException {
        this.st = laConnexion.createStatement();
        ResultSet rs = this.st.executeQuery("SELECT * FROM MAGASIN");
        HashMap<Integer, Magasin> res = new HashMap<>();
        int i = 0;
        while (rs.next()) {
            res.put(i, new Magasin(rs.getInt("idmag"), rs.getString("nommag"), rs.getString("villemag")));
            i++;
        }
        rs.close();
        return res;
    }

    public int getQteLivre(Livre liv) throws SQLException {
        int res = -1;
        this.st = laConnexion.createStatement();
        ResultSet rs = this.st.executeQuery("SELECT qte FROM POSSEDER WHERE isbn = " + liv.getIsbn());
        while (rs.next()) {
            res = rs.getInt("qte");
        }
        rs.close();
        return res;
    }

    public int getMaxCommande() throws SQLException {
        int res = -1;
        this.st = laConnexion.createStatement();
        ResultSet rs = this.st.executeQuery("SELECT max(numcom)max FROM COMMANDE");
        while (rs.next()) {
            res = rs.getInt("max");
        }
        rs.close();
        return res;
    }

    public int trouveMagasin(Livre liv) throws SQLException {
        int res = 0;
        this.st = laConnexion.createStatement();
        ResultSet rs = this.st.executeQuery("SELECT idmag FROM POSSEDER WHERE isbn = " + liv.getIsbn());
        while (rs.next()) {
            res = rs.getInt("idmag");
        }
        rs.close();
        return res;
    }

    public HashMap<Integer, List<Livre>> classeLivreSelonMag(List<Livre> livres) throws SQLException {
        if (livres.isEmpty())
            return null;
        HashMap<Integer, List<Livre>> res = new HashMap<>();
        for (Livre liv : livres) {
            if (res.containsKey(res))
                res.get(trouveMagasin(liv)).add(liv);
            else
                res.put(trouveMagasin(liv), new ArrayList<>());
        }
        return res;
    }
}