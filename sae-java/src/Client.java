import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Client extends Personne {
    private List<Livre> panier;

    public Client(int numCompte, String nom, String prenom, String identifiant, String adresse, int tel, String email,
            String mdp, String codePostal, String ville) {
        super(numCompte, nom, prenom, identifiant, adresse, tel, email, mdp, codePostal, ville);
        panier = new ArrayList<>();
    }

    public Client() {
        panier = new ArrayList<>();
    }

    public List<Livre> getPanier() {
        return panier;
    }

    public void addPanier(Livre liv, int qte) throws TopDeLivreException, MauvaiseQuantiteException {
        if (this.panier.size() >= 9) {
            throw new TopDeLivreException();
        }
        if (liv.getQte() < qte) {
            throw new MauvaiseQuantiteException(qte, liv);
        } else if (qte <= 0) {
            throw new MauvaiseQuantiteException(qte, liv);
        }
        liv.setQte(qte);
        this.panier.add(liv);
    }

    public void suppPanier(Livre liv) {
        this.panier.remove(liv);
    }

    public double getValPanier() {
        double res = 0;
        for (Livre liv : panier) {
            res += liv.getPrix() * liv.getQte();
        }
        return res;
    }

    public void affichePanier() {
        if (panier.isEmpty()) {
            System.out.println("Il n'y a rien dans votre panier");
            System.out.println("[0] Quitter");
            return;
        }
        int y = 1;
        String column1Format = "%-50.50s";
        String column2Format = "%-8.8s";
        String column3Format = "%8.8s";
        String formatInfo = column1Format + " " + column2Format + " " +
                column3Format;
        System.out.println("────────────────────────────────────────────────────────────────────────────────────");
        for (int i = 0; i < panier.size(); ++i) {
            System.out.format(formatInfo, "[" + y + "] Titre : " +
                    panier.get(i).getTitre(),
                    "qte : " + panier.get(i).getQte(), panier.get(i).getPrix() + " €");
            System.out.println();
            System.out.println("────────────────────────────────────────────────────────────────────────────────────");
            ++y;
        }
        System.out.println("[0] Quitter");

    }
}