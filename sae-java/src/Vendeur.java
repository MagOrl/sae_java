
import java.util.*;


/**
 * Class Vendeur
 */
public class Vendeur extends Personne {

  //
  // Fields
  //


  private Magasin m_magasin;
  
  //
  // Constructors
  //
  public Vendeur () { };
  
  //
  // Methods
  //


  //
  // Accessor methods
  //

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

  //
  // Other methods
  //

  /**
   * @param        livre
   */
  public void ajouteLivreStock(Livre livre)
  {
  }


  /**
   * @param        qte
   * @param        livre
   */
  public void majQteLivre(int qte, Livre livre)
  {
  }


  /**
   * @param        livre
   */
  public void verifDispoLivre(Livre livre)
  {
  }


  /**
   * @param        commande
   * @param        client
   */
  public void passerCommandeCli(Commande commande, Client client)
  {
  }


  /**
   * @param        livre
   */
  public void transfererLivre(Livre livre)
  {
  }


}
