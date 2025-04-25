

/**
 * Class Personne
 */
abstract public class Personne {

  //
  // Fields
  //

  protected int numCompte;
  protected String nom;
  protected String prenom;
  protected String Identifiant;
  protected String adresse;
  protected int tel;
  protected String email;
  protected String mdp;
  
  //
  // Constructors
  //
  public Personne () { };
  
  //
  // Methods
  //


  //
  // Accessor methods
  //

  /**
   * Set the value of numCompte
   * @param newVar the new value of numCompte
   */
  public void setNumCompte (int newVar) {
    numCompte = newVar;
  }

  /**
   * Get the value of numCompte
   * @return the value of numCompte
   */
  public int getNumCompte () {
    return numCompte;
  }

  /**
   * Set the value of nom
   * @param newVar the new value of nom
   */
  public void setNom (String newVar) {
    nom = newVar;
  }

  /**
   * Get the value of nom
   * @return the value of nom
   */
  public String getNom () {
    return nom;
  }

  /**
   * Set the value of prenom
   * @param newVar the new value of prenom
   */
  public void setPrenom (String newVar) {
    prenom = newVar;
  }

  /**
   * Get the value of prenom
   * @return the value of prenom
   */
  public String getPrenom () {
    return prenom;
  }

  /**
   * Set the value of Identifiant
   * @param newVar the new value of Identifiant
   */
  public void setIdentifiant (String newVar) {
    Identifiant = newVar;
  }

  /**
   * Get the value of Identifiant
   * @return the value of Identifiant
   */
  public String getIdentifiant () {
    return Identifiant;
  }

  /**
   * Set the value of adresse
   * @param newVar the new value of adresse
   */
  public void setAdresse (String newVar) {
    adresse = newVar;
  }

  /**
   * Get the value of adresse
   * @return the value of adresse
   */
  public String getAdresse () {
    return adresse;
  }

  /**
   * Set the value of tel
   * @param newVar the new value of tel
   */
  public void setTel (int newVar) {
    tel = newVar;
  }

  /**
   * Get the value of tel
   * @return the value of tel
   */
  public int getTel () {
    return tel;
  }

  /**
   * Set the value of email
   * @param newVar the new value of email
   */
  public void setEmail (String newVar) {
    email = newVar;
  }

  /**
   * Get the value of email
   * @return the value of email
   */
  public String getEmail () {
    return email;
  }

  /**
   * Set the value of mdp
   * @param newVar the new value of mdp
   */
  public void setMdp (String newVar) {
    mdp = newVar;
  }

  /**
   * Get the value of mdp
   * @return the value of mdp
   */
  public String getMdp () {
    return mdp;
  }

  //
  // Other methods
  //

  /**
   * @param        mdp
   * @param        Identifiant
   */
  public void seConnecter(String mdp, String Identifiant)
  {
  }


  /**
   */
  public void seDeconnecter()
  {
  }


}
