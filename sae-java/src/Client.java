

/**
 * Class Client
 */
public class Client extends Personne, Personne {

  //
  // Fields
  //

  private double solde = 0;
  private Livre[*] panier;
  
  //
  // Constructors
  //
  public Client () { };
  
  //
  // Methods
  //


  //
  // Accessor methods
  //

  /**
   * Set the value of solde
   * @param newVar the new value of solde
   */
  public void setSolde (double newVar) {
    solde = newVar;
  }

  /**
   * Get the value of solde
   * @return the value of solde
   */
  public double getSolde () {
    return solde;
  }

  /**
   * Set the value of panier
   * @param newVar the new value of panier
   */
  public void setPanier (Livre[*] newVar) {
    panier = newVar;
  }

  /**
   * Get the value of panier
   * @return the value of panier
   */
  public Livre[*] getPanier () {
    return panier;
  }

  //
  // Other methods
  //

  /**
   */
  public void PasseruneCommande()
  {
  }


  /**
   */
  public void ConsulteCatalogue()
  {
  }


}
