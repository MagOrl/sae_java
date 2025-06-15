public class Magasin {

  private int idMag;
  private String nomMag;
  private String villeMag;

  public Magasin(int id, String nom, String ville) {
    this.idMag = id;
    this.nomMag = nom;
    this.villeMag = ville;
  }

  public int getId() {
    return this.idMag;
  }

  public String getNom() {
    return this.nomMag;
  }

  public String getVille() {
    return this.villeMag;
  }

  public void setId(int newId) {
    this.idMag = newId;
  }

  public void setNom(String newName) {
    this.nomMag = newName;
  }

  public void setVille(String newVille) {
    this.villeMag = newVille;
  }

  @Override
  public String toString() {
    return this.nomMag + " (Id: " + this.idMag + " Ville: " + this.villeMag + ")";
  }

  @Override
  public boolean equals(Object o) {
    if (o == null) {
      return false;
    }
    if (o == this) {
      return true;
    }
    if (!(o instanceof Magasin)) {
      return false;
    }
    Magasin tmp = (Magasin) o;
    return this.idMag == tmp.idMag
        && this.getNom().equals(tmp.getNom())
        && this.getVille().equals(tmp.getVille());
  }
}
