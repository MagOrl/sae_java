public class Magasin {

  private String idMag;
  private String nomMag;
  private String villeMag;

  public Magasin(String id, String nom, String ville) {
    this.idMag = id;
    this.nomMag = nom;
    this.villeMag = ville;
  }

  public String getId() {
    return this.idMag;
  }

  public String getNom() {
    return this.nomMag;
  }

  public String getVille() {
    return this.villeMag;
  }

  public void setId(String newId) {
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
    return this.nomMag+" (Id: "+this.idMag+" Ville: "+this.villeMag+")";
  }

  @Override
  public boolean equals(Object o){
    if (o == null){return false;}
    if (o == this){return true;}
    if (!(o instanceof Magasin)){return false;}
    Magasin tmp = (Magasin) o;
    return this.getId().equals(tmp.getId())
    && this.getNom().equals(tmp.getNom())
    && this.getVille().equals(tmp.getVille());
  }
}