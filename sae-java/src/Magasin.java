
import java.util.*;


/**
 * Class Magasin
 */
public class Magasin {

  //
  // Fields
  //


  private Vector vendeursVector = new Vector();

  private Vector livresVector = new Vector();
  
  //
  // Constructors
  //
  public Magasin () { };
  
  //
  // Methods
  //


  //
  // Accessor methods
  //

  /**
   * Add a Vendeurs object to the vendeursVector List
   */
  private void addVendeurs (Vendeur new_object) {
    vendeursVector.add(new_object);
  }

  /**
   * Remove a Vendeurs object from vendeursVector List
   */
  private void removeVendeurs (Vendeur new_object)
  {
    vendeursVector.remove(new_object);
  }

  /**
   * Get the List of Vendeurs objects held by vendeursVector
   * @return List of Vendeurs objects held by vendeursVector
   */
  private List getVendeursList () {
    return (List) vendeursVector;
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
