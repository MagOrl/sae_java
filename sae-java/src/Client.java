import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Client extends Personne {
    private HashMap<Integer, List<Livre>> panier;

    public Client(int numCompte, String nom, String prenom, String identifiant, String adresse, int tel, String email,
            String mdp, String codePostal, String ville) {
        super(numCompte, nom, prenom, identifiant, adresse, tel, email, mdp, codePostal, ville);
        panier = new HashMap<>();
    }

    public Client() {
        panier = new HashMap<>();
    }

    public List<Livre> getPanier(int idmag) {
        return panier.get(idmag);
    }

    public HashMap<Integer, List<Livre>> getPanier() {
        return panier;
    }

    public void addPanier(int idmag, Livre liv, int qte) throws TopDeLivreException, MauvaiseQuantiteException {
        if (this.panier.get(idmag) == null) {
            this.panier.put(idmag, new ArrayList<>());
        }
        if (this.panier.get(idmag).size() >= 9) {
            throw new TopDeLivreException();
        }
        if (liv.getQte() < qte) {
            throw new MauvaiseQuantiteException(qte, liv);
        } else if (qte <= 0) {
            throw new MauvaiseQuantiteException(qte, liv);
        }
        liv.setQte(qte);
        this.panier.get(idmag).add(liv);
    }

    public void suppPanier(Livre liv) {
        this.panier.remove(liv);
    }

    public double getValPanier() {
        double res = 0;
        for (List<Livre> liv : panier.values()) {
            for (Livre l : liv) {
                res += l.getPrix() * l.getQte();
            }
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
        for (int idmag : panier.keySet()) {
            System.out.println("COMMANDE POUR ID MAGASIN : " + idmag);
            System.out.println("********************************************************************************");
            for (int i = 0; i < panier.get(idmag).size(); ++i) {
                System.out.format(formatInfo, "[" + y + "] Titre : " +
                        panier.get(idmag).get(i).getTitre(),
                        "qte : " + panier.get(idmag).get(i).getQte(), panier.get(idmag).get(i).getPrix() + " €");
                System.out.println();
                System.out.println(
                        "────────────────────────────────────────────────────────────────────────────────────");
                ++y;
            }
        }
        System.out.println("[0] Quitter");

    }
}