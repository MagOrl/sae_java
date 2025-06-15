
public abstract class Personne {

    protected int numCompte;
    protected String nom;
    protected String prenom;
    protected String identifiant;
    protected String adresse;
    protected int tel;
    protected String email;
    protected String mdp;
    protected String codePostal;
    protected String ville;

    public Personne() {
    }

    public Personne(int numCompte, String nom, String prenom, String identifiant, String adresse, int tel, String email,
            String mdp, String codePostal, String ville) {
        this.numCompte = numCompte;
        this.nom = nom;
        this.prenom = prenom;
        this.identifiant = identifiant;
        this.adresse = adresse;
        this.tel = tel;
        this.email = email;
        this.mdp = mdp;
    }

    public int getNumCompte() {
        return this.numCompte;
    }

    public void setNumCompte(int numCompte) {
        this.numCompte = numCompte;
    }

    public String getNom() {
        return this.nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return this.prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getIdentifiant() {
        return this.identifiant;
    }

    public void setIdentifiant(String identifiant) {
        this.identifiant = identifiant;
    }

    public String getAdresse() {
        return this.adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public int getTel() {
        return this.tel;
    }

    public void setTel(int tel) {
        this.tel = tel;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMdp() {
        return this.mdp;
    }

    public void setMdp(String mdp) {
        this.mdp = mdp;
    }

    public String getCodePostal() {
        return this.getCodePostal();
    }

    public void setCodePostal(String codepostal) {
        this.codePostal = codepostal;
    }

    public String getVille() {
        return this.ville;
    }

    public void setVille(String ville) {
        this.ville = ville;
    }

    @Override
    public String toString() {
        return "Numéro de compte : " + this.numCompte + "\n" +
                "Nom : " + this.nom + "  " + "Prenom : " + this.prenom + "\n" +
                "Identifiant : " + this.identifiant + "  " + "Adresse : " + this.adresse + "\n" +
                "Téléphone : " + tel + "  " + "Email : " + this.email + "  Code postal : " + this.codePostal +
                "  Ville : " + this.ville;
    }

}