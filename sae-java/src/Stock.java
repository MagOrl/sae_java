import java.util.List;
import java.util.ArrayList;

public class Stock {

  /**
  * Attibuts de la classe Catalogue
  */
  private List<Livre> livres;

  /**
  * Constructeur de la classe Catalogue
  */
  public Catalogue() {
    this.livres = new ArrayList<>();
  }

  /**
  * Methodes de la classe Catalogue
  */ 

  /**
   * recupère les livres du catalogue
   * @return les livres du catalogue
   */
  public List<Livre> getLivre() {
    return this.livres;
  }

  /**
   * Recherche un livre par son Titre
   * @param titre le titre du livre à rechercher
   * @return la liste des livres trouvés
   */
  
  public List<Livre> rechercherLivreParTitre(String titre) {
    List<Livre> livresTrouves = new ArrayList<>();
    for (Livre l : this.livres) {
      if (l.getTitre().equalsIgnoreCase(titre)) {
        livresTrouves.add(l);
      }
    }
    return livresTrouves;
  }

  /**
   * Recherche un livre par son auteur
   * @param auteur le nom de l'auteur du livre à rechercher
   * @return la liste des livres trouvés
   */
  public List<Livre> rechercherLivreParAuteur(String auteur) {
    List<Livre> livresTrouves = new ArrayList<>();
    for (Livre l : this.livres) {
     if (l.getAuteur().equalsIgnoreCase(auteur)) {
        livresTrouves.add(l);
      }
    }
    return livresTrouves;
  }

  /**
   * Le nombre de livres dans le catalogue
   * @return le nombre de livres
   */
  public int getNombreLivres() {
    return this.livres.size();
  }

  /**
   * Trier les livres par titre
   * @return Liste des livres triés par titre
   */
  public List<Livre> trierLivresParTitre() {
    this.livres.sort((l1, l2) -> l1.getTitre().compareTo(l2.getTitre()));
    return this.livres;
  }

  /**
   * Trier les livres par auteur
   * @return Liste des livres triés par auteur
   */
  public List<Livre> trierLivresParAuteur() {
    this.livres.sort((l1, l2) -> l1.getAuteur().compareTo(l2.getAuteur()));
    return this.livres;
  }

  /**
   * Trier les livres par date de publication
   * @return Liste des livres triés par date de publication
   */
  public List<Livre> trierLivresParDate() {
    this.livres.sort((l1, l2) -> l1.getDatePublication().compareTo(l2.getDatePublication()));
    return this.livres;
  }



  /**
  * Afficheur de la classe Auteur
  */
  @Override
  public String toString() {
    return null;
  }

}
