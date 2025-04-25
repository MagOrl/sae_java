abstract public class Personne {

  protected int numCompte;
  protected String nom;
  protected String prenom;
  protected String identifiant;
  protected String adresse;
  protected int tel;
  protected String email;
  protected String mdp;
  
  public Personne(String nom, String prenom, String identifiant, String adresse, int tel, String email, String mdp){
      this.nom = nom;
      this.prenom = prenom;
      this.identifiant = identifiant;
      this.adresse = adresse;
      this.tel = tel;
      this.email = email;
      this.mdp = mdp;
  };

  /**
   * Set the value of numCompte
   * @param newVar the new value of numCompte
   */
  public void setNumCompte(int numCompte){
      this.numCompte = numCompte;
  }

  public void setNom(String nom){
      this.nom = nom;
  }

  public void setPrenom(String prenom){
      this.prenom = prenom;
  }

  public void setIdentifiant(String identifiant){
      this.identifiant = identifiant;
  }

  public void setAdresse(String adresse){
      this.adresse = adresse;
  }

  public void setTel(int tel){
      this.tel = tel;
  }

  public void setEmail(String email){
      this.email = email;
  }

  public void setMdp(String mdp){
      this.mdp = mdp;
  }

//---------------------------------------------------------------------

  public int getNumCompte(){
      return this.numCompte;
  }

  public String getNom(){
      return this.nom;
  }

  public String getPrenom(){
      return this.prenom;
  }

  public String getIdentifiant(){
      return this.identifiant; 
  }

  public String getAdresee(){
      return this.adresse;
  }

  public int getTel(){
      return this.tel;
  }

  public String getEmail(){
      return this.email;
  }

  public String getMdp(){
    return this.mdp;
  }

//---------------------------------------------------------------------


  /**
   * @param        mdp
   * @param        Identifiant
   */
  public void seConnecter(String mdp, String Identifiant){
  }


  /**
   */
  public void seDeconnecter()
  {
  }


}
