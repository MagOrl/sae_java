public class Livre {

  private String isbn;
  private String titre;
  private int nbPages;
  private String datePubli;
  private double prix;
  private int qte;

  public Livre(String isbn, String titre, int nbPages, String datePubli, double prix, int qte) {
    this.isbn = isbn;
    this.titre = titre;
    this.nbPages = nbPages;
    this.datePubli = datePubli;
    this.prix = prix;
    this.qte = qte;
  };

  public String getIsbn() {
    return this.isbn;
  };

  public String getTitre() {
    return this.titre;
  };

  public int getNbPages() {
    return this.nbPages;
  };

  public String getDatePubli() {
    return this.datePubli;
  };

  public double getPrix() {
    return this.prix;
  };

  public void setIsbn(String newIsbn) {
    this.isbn = newIsbn;
  };

  public void setTitre(String newTitre) {
    this.titre = newTitre;
  };

  public void setNbPages(int newNbPages) {
    this.nbPages = newNbPages;
  };

  public void setDatePubli(String newDatePubli) {
    this.datePubli = newDatePubli;
  };

  public void setPrix(double newPrix) {
    this.prix = newPrix;
  };

  public int getQte() {
    return this.qte;
  }

  public void setQte(int qte) {
    this.qte = qte;
  }

  @Override
  public String toString() {
    return getTitre() +
        " (isbn: " + getIsbn() +
        " nombre de pages: " + getNbPages() +
        " date de publication: " + getDatePubli() +
        " prix: " + getPrix() + ")";
  }

  @Override
  public boolean equals(Object o) {
    if (o == null) {
      return false;
    }
    if (o == this) {
      return true;
    }
    if (!(o instanceof Livre)) {
      return false;
    }
    Livre tmp = (Livre) o;
    return this.getIsbn() == tmp.getIsbn()
        && this.getTitre().equals(tmp.getTitre())
        && this.getNbPages() == tmp.getNbPages()
        && this.getDatePubli().equals(tmp.getDatePubli())
        && this.getPrix() == tmp.getPrix();
  }
}