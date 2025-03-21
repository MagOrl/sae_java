import org.junit.*;
import static org.junit.Assert.assertEquals;

import java.beans.Transient;

public class LivreTest {

        @Test
        public void testLivreCreate(){
            Livre livre = new Livre(500, "Harry potter à l'école des sorciers", 1000, "26-06-1997", 1000);
        }

        @Test
        public void testgetIsbn(){
            Livre livre = new Livre(500, "Harry potter à l'école des sorciers", 1000, "26-06-1997", 1000);
            livre.getIsbn();
        }

        @Test
        public void testgetTitre(){
            Livre livre = new Livre(500, "Harry potter à l'école des sorciers", 1000, "26-06-1997", 1000);
            livre.getTitre();
        }
        
        
    
}
