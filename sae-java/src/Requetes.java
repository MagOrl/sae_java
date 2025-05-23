
import java.sql.*;
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
            // laConnexion.connecter("servinfo-maria", "DBarsamerzoev", "arsamerzoev", "arsamerzoev");
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
        // rs.first();
        // numcomSave = rs.getInt("numcom");
        // rs.beforeFirst();
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

    public List<Livre> rechercheTheme(int thm) throws SQLException {
        thm = thm*100;
        this.st = laConnexion.createStatement();
        String query = "SELECT LIVRE.*,qte FROM LIVRE NATURAL JOIN THEMES NATURAL JOIN POSSEDER NATURAL JOIN MAGASIN WHERE iddewey>=" + thm + " and iddewey<=" + (thm + 90);
        ResultSet rs = this.st.executeQuery(query);
        List<Livre> catalogue = new ArrayList<>();
        while (rs.next()) {
            catalogue.add(new Livre(rs.getString("isbn"), rs.getString("titre"), rs.getInt("nbpages"), rs.getString("datepubli"), rs.getInt("prix"),rs.getInt("qte")));
        }
        rs.close();
        return catalogue;
    }
}
// SELECT LIVRE.*
// FROM LIVRE NATURAL JOIN THEMES NATURAL JOIN CLASSIFICATION NATURAL JOIN
// POSSEDER NATURAL JOIN MAGASIN
// WHERE (titre = " ", )
