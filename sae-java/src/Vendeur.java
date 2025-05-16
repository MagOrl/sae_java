public class Vendeur extends Personne{

    private Magasin magasinVendeur;

    public Vendeur(int numCompte, String nom, String prenom, String identifiant, String adresse, int tel, String email, String mdp, Magasin magasin){
        super(numCompte, nom, prenom, identifiant, adresse, tel, email, mdp);
        this.magasinVendeur = magasin;
    }

    public void ajouteLivreStock(Livre livre){

    }

    public void majQteLivre(int nvQte){

    }

    public boolean verifDispoLivre(){
        
    }

    public void passerCommande(){

    }

    public void transfererLivre(){

    }

    @Override
    public void seConnecter(String identifiant, String mdp){

    }

    @Override
    public void seDeconnecter(){
        
    }

}

