
public class Vendeur extends Personne {
    private Magasin magasinVendeur;

    public Vendeur(int numCompte, String nom, String prenom, String identifiant, String adresse, int tel, String email,
            String mdp, String codePostal, String ville, Magasin magasin) {
        super(numCompte, nom, prenom, identifiant, adresse, tel, email, mdp, codePostal, ville);
        this.magasinVendeur = magasin;
    }

    public Magasin getMag() {
        return this.magasinVendeur;
    }

}
