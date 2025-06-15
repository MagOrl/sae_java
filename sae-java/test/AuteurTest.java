import static org.junit.Assert.assertEquals;
import org.junit.Test;

public class AuteurTest {

    @Test
    public void testIdAuteur() {
        Auteur auteur = new Auteur(1, "Kalvin", "Hustler",1980-01-19, null);
        assertEquals(1, auteur.getIdAuteur());
    }

    @Test
    public void testGetNomAuteur() {
        Auteur auteur = new Auteur(1, "Kalvin", "Hustler",1980-01-19, null);
        assertEquals("Hustler", auteur.getNomAuteur());
    }

    @Test
    public void testGetPrenomAuteur() {
        Auteur auteur = new Auteur(1, "Kalvin", "Hustler",1980-01-19, null);
        assertEquals("Kalvin", auteur.getPrenomAuteur());
    }

    @Test
    public void testGetDateNaiss() {
        Auteur auteur = new Auteur(1, "Kalvin", "Hustler",1980-01-19, null);
        assertEquals("1980-01-19", auteur.getDateNaiss().toString());
    }

    @Test
    public void testGetDateDeces() {
        Auteur auteur = new Auteur(1, "Kalvin", "Hustler",1980-01-19, null);
        assertEquals(null, auteur.getDateDeces());
    }
}