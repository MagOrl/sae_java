
import java.util.*;


/**
 * Class Commande
 */
public class Commande {

  //
  // Fields
  //

  private int numcom;
  private String datecom;

  private Magasin m_magasin;

  private Personne m_compte;
  
  //
  // Constructors
  //
  public Commande () { };
  
  //
  // Methods
  //


  //
  // Accessor methods
  //

  /**
   * Set the value of numcom
   * @param newVar the new value of numcom
   */
  public void setNumcom (int newVar) {
    numcom = newVar;
  }

  /**
   * Get the value of numcom
   * @return the value of numcom
   */
  public int getNumcom () {
    return numcom;
  }

  /**
   * Set the value of datecom
   * @param newVar the new value of datecom
   */
  public void setDatecom (String newVar) {
    datecom = newVar;
  }

  /**
   * Get the value of datecom
   * @return the value of datecom
   */
  public String getDatecom () {
    return datecom;
  }

  /**
   * Set the value of m_magasin
   * @param newVar the new value of m_magasin
   */
  private void setMagasin (Magasin newVar) {
    m_magasin = newVar;
  }

  /**
   * Get the value of m_magasin
   * @return the value of m_magasin
   */
  private Magasin getMagasin () {
    return m_magasin;
  }

  /**
   * Set the value of m_compte
   * @param newVar the new value of m_compte
   */
  private void setCompte (Personne newVar) {
    m_compte = newVar;
  }

  /**
   * Get the value of m_compte
   * @return the value of m_compte
   */
  private Personne getCompte () {
    return m_compte;
  }

  //
  // Other methods
  //

}
