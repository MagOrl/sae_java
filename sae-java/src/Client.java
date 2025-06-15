import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Client extends Personne {
    /**
     * le panier est un dicitonnaire ou les clefs est l'indice (la page) et les
     * valeurs des
     * listes de livres
     */
    private HashMap<Integer, List<Livre>> panier;

    /**
     * constructeur prennant en compte tout les éléments de Persone
     * 
     * @param numCompte
     * @param nom
     * @param prenom
     * @param identifiant
     * @param adresse
     * @param tel
     * @param email
     * @param mdp
     * @param codePostal
     * @param ville
     */
    public Client(int numCompte, String nom, String prenom, String identifiant, String adresse, int tel, String email,
            String mdp, String codePostal, String ville) {
        super(numCompte, nom, prenom, identifiant, adresse, tel, email, mdp, codePostal, ville);
        panier = new HashMap<>();
    }

    /**
     * Constructeur vide
     */
    public Client() {
        panier = new HashMap<>();
    }

    /**
     * permet d'avoir la liste de panier selon un indice donné par l'utilisateur
     * 
     * @param idmag l'indice du panier
     * @return la liste de livre
     */
    public List<Livre> getPanier(int idmag) {
        return panier.get(idmag);
    }

    /**
     * 
     * @return le panier
     */
    public HashMap<Integer, List<Livre>> getPanier() {
        return panier;
    }

    /**
     * ajoute un livre dans un panier en respectant la contrainte qu'il ne faut que
     * 10 élément dans une liste de panier
     * 
     * @param idmag
     * @param liv
     * @param qte
     * @throws TopDeLivreException       exception relevé si la capacité maximale de
     *                                   livre dans une liste (9) à été atteinte
     * @throws MauvaiseQuantiteException execption relevé si une quantité de 0 à été
     *                                   retrer
     */
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

    /**
     * supprime un livre du panier
     * 
     * @param idmag la page dans laquel l'utilisateur se trouve
     * @param liv   le livre que l'utilisateur veux supprimer
     */
    public void suppPanier(int idmag, Livre liv) {
        this.panier.get(idmag).remove(liv);
    }

    /**
     * 
     * @return la valeur totale du panier
     */
    public double getValPanier() {
        double res = 0;
        for (List<Livre> liv : panier.values()) {
            for (Livre l : liv) {
                res += l.getPrix() * l.getQte();
            }
        }
        return res;
    }

    /**
     * permet d'afficher le contenu d'un panier dans un terminal
     */
    public void affichePanier() {
        if (getValPanier() == 0) {
            System.out.println("Il n'y a rien dans votre panier");
            System.out.println("[0] Quitter");
        } else {
            int y = 1;
            String column1Format = "%-50.50s";
            String column2Format = "%-8.8s";
            String column3Format = "%8.8s";
            String formatInfo = column1Format + " " + column2Format + " " +
                    column3Format;
            System.out.println("────────────────────────────────────────────────────────────────────────────────────");
            for (int idmag : panier.keySet()) {
                if (!panier.get(idmag).isEmpty()) {
                    System.out.println("\nID MAGASIN : [" + idmag + "]");
                    System.out
                            .println(
                                    "***********************************************************************************");
                }
                for (int i = 0; i < panier.get(idmag).size(); ++i) {
                    System.out.format(formatInfo, "[" + y + "] Titre : " +
                            panier.get(idmag).get(i).getTitre(),
                            "qte : " + panier.get(idmag).get(i).getQte(), panier.get(idmag).get(i).getPrix() + " €");
                    System.out.println();
                    System.out.println(
                            "────────────────────────────────────────────────────────────────────────────────────");
                    ++y;
                }
                y = 1;
            }
            System.out.println("Total : " + getValPanier() + " €");
            System.out.println(
                    "Tappez l'id du magasin ainsi que le numéro du livre que vous voulez supprimer(les 2 valeurs doivent être séparer par un espace),\nfaite 'COMMANDE' si tout est bon et que vous voulez commander ");
            System.out.println("[0] Quitter");
        }

    }
}