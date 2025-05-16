public class Editeur {

  private int idEditeur;
  private String nomEditeur;

  public Editeur(int id, String nom) {
    this.idEditeur = id;
    this.nomEditeur = nom;
  }

  public int getId() {
    return this.idEditeur;
  }

  public String getNom() {
    return this.nomEditeur;
  }

  public void setId(int newId) {
    this.idEditeur = newId;
  }

  public void setNom(String newName) {
    this.nomEditeur = newName;
  }

  @Override
  public String toString() {
    return this.nomEditeur+" (Id: "+this.idEditeur+")";
  }

  @Override
  public boolean equals(Object o){
    if (o == null){return false;}
    if (o == this){return true;}
    if (!(o instanceof Editeur)){return false;}
    Editeur tmp = (Editeur) o;
    return this.getId()==tmp.getId()
    && this.getNom().equals(tmp.getNom());
  }
}