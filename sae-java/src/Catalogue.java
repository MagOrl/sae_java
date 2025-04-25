
import java.util.*;


/**
 * Class Catalogue
 */
public class Catalogue {

  //
  // Fields
  //

  private Livre nouvel_attribut;

  private Vector livresVector = new Vector();
  
  //
  // Constructors
  //
  public Catalogue () { };
  
  //
  // Methods
  //


  //
  // Accessor methods
  //

  /**
   * Set the value of nouvel_attribut
   * @param newVar the new value of nouvel_attribut
   */
  public void setNouvel_attribut (Livre newVar) {
    nouvel_attribut = newVar;
  }

  /**
   * Get the value of nouvel_attribut
   * @return the value of nouvel_attribut
   */
  public Livre getNouvel_attribut () {
    return nouvel_attribut;
  }

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
