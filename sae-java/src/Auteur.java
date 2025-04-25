
import java.util.*;


/**
 * Class Auteur
 */
public class Auteur {

  //
  // Fields
  //


  private Vector livresVector = new Vector();
  
  //
  // Constructors
  //
  public Auteur () { };
  
  //
  // Methods
  //


  //
  // Accessor methods
  //

  /**
   * Add a Livres object to the livresVector List
   */
  private void addLivres (Livre new_object) {
    livresVector.add(new_object);
  }

  /**
   * Remove a Livres object from livresVector List
   */
  private void removeLivres (Livre new_object)
  {
    livresVector.remove(new_object);
  }

  /**
   * Get the List of Livres objects held by livresVector
   * @return List of Livres objects held by livresVector
   */
  private List getLivresList () {
    return (List) livresVector;
  }


  //
  // Other methods
  //

}
