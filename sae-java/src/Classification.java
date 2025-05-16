public class Classification {
  private int idClassi;
  private String nomClassi;

  public Classification (int id, String nom) {
    this.idClassi = id;
    this.nomClassi = nom;
  };
  
  public int getId() {
    return this.idClassi;
  }

  public String getNom() {
    return this.nomClassi;
  }

  public String toString() {
    return this.nomClassi+" : "+this.idClassi;
  }
}