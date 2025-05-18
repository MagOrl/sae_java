import java.sql.*;

public class Requetes {

    private ConnexionMySQL laConnexion;
    private Statement st;

    public Requetes(ConnexionMySQL laConnexion) {
        this.laConnexion = laConnexion;
        try {
            laConnexion.connecter("localhost", "Librairie", "root", "mypassword");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void creeClient(String identif, String nom, String prenom, String adresse, String codepostal, String ville,
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
    }

    public Client trouveClient(int numcli) throws SQLException {
        Client cli = new Client();
        this.st = this.laConnexion.createStatement();
        ResultSet rs = this.st.executeQuery("SELECT " + numcli + "FROM CLIENT");
        while (rs.next()) {
            cli = new Client(numcli, rs.getString("nomcli"), rs.getString("prenomcli"), rs.getString("identifiant"),
                    rs.getString("adressecli"), rs.getInt("tel"), rs.getString("email"), rs.getString("motdepasse"),
                    rs.getString("codepostal"), rs.getString("villecli"));
        }
        rs.close();
        return cli;
    }

    public boolean connectClient(String identif, String mdp) throws SQLException {
        this.st = this.laConnexion.createStatement();
        ResultSet rs = this.st
                .executeQuery("SELECT * FROM CLIENT WHERE identifiant = '" + identif + "'and motdepasse ='" + mdp + "'");
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
}
