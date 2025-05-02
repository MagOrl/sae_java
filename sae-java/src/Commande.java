import java.util.ArrayList;
import java.util.List;

public class Commande {

  private int numcom;
  private String datecom;
  private Client cli;
  private List<DetailCommande> detailcom;
  private boolean enligne;
  private boolean livraison; // false dans le magasin , true chez soit
  private Magasin mag;

  public Commande(int numcom, String datecom, Client cli, boolean enligne, Magasin mag) {
    this.numcom = numcom;
    this.datecom = datecom;
    this.cli = cli;
    this.detailcom = new ArrayList<>();
    this.enligne = enligne;
    this.mag = mag;
  }

  public int getNumcom() {
    return this.numcom;
  }

  public void setNumcom(int numcom) {
    this.numcom = numcom;
  }

  public String getDatecom() {
    return this.datecom;
  }

  public void setDatecom(String datecom) {
    this.datecom = datecom;
  }

  public Client getCli() {
    return this.cli;
  }

  public void setCli(Client cli) {
    this.cli = cli;
  }

  public List<DetailCommande> getDetailcom() {
    return this.detailcom;
  }

  public void setDetailcom(List<DetailCommande> detailcom) {
    this.detailcom = detailcom;
  }

  public boolean isEnligne() {
    return this.enligne;
  }

  public boolean getEnligne() {
    return this.enligne;
  }

  public void setEnligne(boolean enligne) {
    this.enligne = enligne;
  }

  public Magasin getMag() {
    return this.mag;
  }

  public void setMag(Magasin mag) {
    this.mag = mag;
  }

  public boolean isLivraison() {
    return this.livraison;
  }

  public boolean getLivraison() {
    return this.livraison;
  }

  public void setLivraison(boolean livraison) {
    this.livraison = livraison;
  }

}
