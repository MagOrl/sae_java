import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.JUnitCore;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

// Importations pour les assertions standards de JUnit
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

public class RequetesTest {

    private static ConnexionMySQL laConnexion;
    private static Requetes requetes;

    @BeforeClass
    public static void setUp() {
        try {
            laConnexion = new ConnexionMySQL();
            laConnexion.connecter("localhost", "Librairie", "root", "mypassword");
            requetes = new Requetes(laConnexion);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    @AfterClass
    public static void tearDown() {
        System.out.println("Fin des tests.");
        try {
            if (laConnexion != null) {
                laConnexion.deconnecter();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testConnexionClientReussie() throws SQLException {
        System.out.println("Test: connexionClient (réussite)");

        requetes.creeClient("clientTestLogin", "NomTest", "PrenomTest", "1 rue test", "75001", "Paris", "test@test.com",
                "0123456789", "bonMdp");

        Client client = requetes.trouveClient("clientTestLogin", "bonMdp");
        assertNotNull("Le client ne devrait pas être null pour une connexion réussie", client);
        assertEquals("Le nom du client connecté est incorrect", "NomTest", client.getNom());
    }

    @Test
    public void testConnexionClientEchouee() throws SQLException {
        System.out.println("Test: connexionClient (échec)");
        Client client = requetes.trouveClient("clientTestLogin", "mauvaisMdp");
        assertNull("Le client devrait être null pour une connexion échouée", client.getIdentifiant());
    }

    @Test
    public void testCreeClientEtMax() throws SQLException {
        System.out.println("Test: creeClient");
        int maxIdAvantCreation = requetes.clientMax();

        int nouvelId = requetes.creeClient("nouveauClient", "Durand", "Paul", "10 avenue de France", "45000",
                "Orléans", "paul.durand@email.com", "0987654321", "secret123");

        int maxIdApresCreation = requetes.clientMax();

        assertEquals("Le nouvel ID doit être celui retourné par la fonction", maxIdApresCreation, nouvelId);
        assertEquals("Le nouvel ID maximum doit être supérieur à l'ancien", maxIdAvantCreation + 1, maxIdApresCreation);
    }

    @Test 
    public void testMajClient() throws SQLException{
        Client client = requetes.trouveClient("clientTestLogin", "mauvaisMdp");
        int telAv = client.getTel();
        client.setTel(06666666);
        requetes.majClient(client);
        assertNotEquals("Le numéro de téléphone ne doit plus être le même ",telAv, client.getTel());
    }

}
