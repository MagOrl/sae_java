import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.JUnitCore;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

// Importations pour les assertions standards de JUnit
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
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
        System.out.println("Fin des tests.  ");
        try {
            if (laConnexion != null) {
                laConnexion.deconnecter();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    @Test
    public void testClientMax() throws SQLException {
        System.out.println("Test: clientMax");
        int maxId = requetes.clientMax();
        assertEquals("L'ID client maximum devrait être 5005", 5005, maxId);
    }


    @Test
    public void testConnexionClientReussie() throws SQLException {
        System.out.println("Test: connexionClient (réussite)");
        // Ajoutons un client de test pour être sûr qu'il existe
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
        assertNull("Le client devrait être null pour une connexion échouée", client);
    }

    @Test
    public void testCreeClient() throws SQLException {
        System.out.println("Test: creeClient");
        int maxIdAvantCreation = requetes.clientMax();

        int nouvelId = requetes.creeClient("nouveauClient", "Durand", "Paul", "10 avenue de France", "45000",
                "Orléans", "paul.durand@email.com", "0987654321", "secret123");

        int maxIdApresCreation = requetes.clientMax();

        assertEquals("Le nouvel ID doit être celui retourné par la fonction", maxIdApresCreation, nouvelId);
        assertEquals("Le nouvel ID maximum doit être supérieur à l'ancien", maxIdAvantCreation + 1, maxIdApresCreation);
    }


    // @Test
    // public void testOnVousRecommande() throws SQLException {
    //     System.out.println("Test: onVousRecommande");
    //     Client client = new Client();
    //     client.setNumCompte(1);
    //     Magasin magasin = new Magasin(1, "La librairie parisienne", "Paris");

    //     List<List<Livre>> recommandations = requetes.onVousRecommande(client, magasin);

    //     assertNotNull("La liste de recommandations ne doit pas être nulle", recommandations);
    //     assertFalse("La liste de recommandations ne devrait pas être vide", recommandations.isEmpty());


    //     List<Livre> livresPlats = recommandations.stream().flatMap(List::stream).collect(Collectors.toList());
    //     boolean livreRecommandeTrouve = livresPlats.stream()
    //             .anyMatch(livre -> livre.getIsbn().equals("9782253004221"));
    //     assertTrue("Le livre attendu (ISBN 9782253004221) devrait être dans les recommandations",
    //             livreRecommandeTrouve);
    // }
}