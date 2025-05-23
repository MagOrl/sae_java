import java.util.List;
import java.util.ArrayList;

public class Catalogue {

  /**
  * Attibuts de la classe Catalogue
  */
  private List<Livre> livre;

  /**
  * Constructeur de la classe Catalogue
  */
  public Catalogue() {
    this.livre = new ArrayList<>();
  }

  /**
  * Methodes de la classe Catalogue
  */ 

  /**
   * recupère les livres du catalogue
   * @return les livres du catalogue
   */
  public List<Livre> getLivre() {
    return this.livre;
  }

  /**
   * Recherche un livre par son Titre
   * @param titre le titre du livre à rechercher
   * @return la liste des livres trouvés
   */
  
  public List<Livre> rechercherLivreParTitre(String titre) {
    List<Livre> livresTrouves = new ArrayList<>();
    for (Livre l : this.livre) {
      if (l.getTitre().equals(titre)) {
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
    for (Livre l : this.livre) {
      if (l.getAuteur().equals(auteur)) {
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
    return this.livre.size();
  }

  /**
   * Trier les livres par titre
   * @return Liste des livres triés par titre
   */
  public List<Livre> trierLivresParTitre() {
    this.livre.sort((l1, l2) -> l1.getTitre().compareTo(l2.getTitre()));
    return this.livre;
  }

  /**
   * Trier les livres par auteur
   * @return Liste des livres triés par auteur
   */
  public List<Livre> trierLivresParAuteur() {
    this.livre.sort((l1, l2) -> l1.getAuteur().compareTo(l2.getAuteur()));
    return this.livre;
  }

  /**
   * Trier les livres par date de publication
   * @return Liste des livres triés par date de publication
   */
  public List<Livre> trierLivresParDate() {
    this.livre.sort((l1, l2) -> l1.getDatePublication().compareTo(l2.getDatePublication()));
    return this.livre;
  }



  /**
  * Afficheur de la classe Auteur
  */
  @Override
  public String toString() {
    return null;
  }

}
