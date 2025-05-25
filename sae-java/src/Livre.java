public class Livre {

  private String isbn;
  private String titre;
  private int nbPages;
  private int datePubli;
  private double prix;

  public Livre(String isbn, String titre, int nbPages, int datePubli, double prix){
    this.isbn = isbn;
    this.titre = titre;
    this.nbPages = nbPages;
    this.datePubli = datePubli;
    this.prix = prix;
  };

  public String getIsbn(){
    return this.isbn;
  };

  public String getTitre(){
    return this.titre;
  };

  public int getNbPages(){
    return this.nbPages;
  };

  public int getDatePubli(){
    return this.datePubli;
  };

  public double getPrix(){
    return this.prix;
  };

  public void setIsbn(String newIsbn){
    this.isbn = newIsbn;
  };

  public void setTitre(String newTitre){
    this.titre = newTitre;
  };

  public void setNbPages(int newNbPages){
    this.nbPages = newNbPages;
  };

  public void setDatePubli(int newDatePubli){
    this.datePubli = newDatePubli;
  };

  public void setPrix(double newPrix){
    this.prix = newPrix;
  };

  @Override
  public String toString(){
    return getTitre()+
    " (isbn: "+getIsbn()+
    " nombre de pages: "+getNbPages()+
    " date de publication: "+getDatePubli()+
    " prix: "+getPrix()+")";
  }

  @Override
  public boolean equals(Object o){
    if (o == null){return false;}
    if (o == this){return true;}
    if (!(o instanceof Livre)){return false;}
    Livre tmp = (Livre) o;
    return this.getIsbn()==tmp.getIsbn()
    && this.getTitre().equals(tmp.getTitre())
    && this.getNbPages()==tmp.getNbPages()
    && this.getDatePubli() == (tmp.getDatePubli())
    && this.getPrix()==tmp.getPrix();
  }
}
