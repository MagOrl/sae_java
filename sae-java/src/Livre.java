
import java.util.*;


/**
 * Class Livre
 */
public class Livre {

  //
  // Fields
  //


  private Vector auteursVector = new Vector();

  private Vector editeursVector = new Vector();
  
  //
  // Constructors
  //
  public Livre () { };
  
  //
  // Methods
  //


  //
  // Accessor methods
  //

  /**
   * Add a Auteurs object to the auteursVector List
   */
  private void addAuteurs (Auteur new_object) {
    auteursVector.add(new_object);
  }

  /**
   * Remove a Auteurs object from auteursVector List
   */
  private void removeAuteurs (Auteur new_object)
  {
    auteursVector.remove(new_object);
  }

  /**
   * Get the List of Auteurs objects held by auteursVector
   * @return List of Auteurs objects held by auteursVector
   */
  private List getAuteursList () {
    return (List) auteursVector;
  }


  /**
   * Add a Editeurs object to the editeursVector List
   */
  private void addEditeurs (Editeur new_object) {
    editeursVector.add(new_object);
  }

  /**
   * Remove a Editeurs object from editeursVector List
   */
  private void removeEditeurs (Editeur new_object)
  {
    editeursVector.remove(new_object);
  }

  /**
   * Get the List of Editeurs objects held by editeursVector
   * @return List of Editeurs objects held by editeursVector
   */
  private List getEditeursList () {
    return (List) editeursVector;
  }


  //
  // Other methods
  //

}
