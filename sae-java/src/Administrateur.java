public class Administrateur extends Personne {

  private List<Magasin> magasinsAdministrateur;
  public Administrateur(int numCompte, String nom, String prenom, String identifiant, String adresse, int tel, String email, String mdp){
    super(numCompte, nom, prenom, identifiant, adresse, tel, email, mdp);
  }

  public void creerCompteVendeur(int numCompte, String nom, String prenom, String identifiant, String adresse, int tel, String email, String mdp, Magasin magasin){
    Vendeur vendeur = new Vendeur(numCompte, nom, prenom, identifiant, adresse, tel, email, mdp, magasin);
  }

  public void ajouteNouvelleLibrairie(Magasin magasin){
    
  }

  public void gererStock(){

  }

  public void consulteStats(){

  }

}
