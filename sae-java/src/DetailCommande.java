public class DetailCommande {
    private int numlig;
    private int qte;
    private double prixvente;

    public DetailCommande(int numlig, int qte, double prixvente) {
        this.numlig = numlig;
        this.qte = qte;
        this.prixvente = prixvente;
    }

    public int getNumlig(){
        return this.numlig;
    }
    public int getQte(){
        return this.qte;
    }
    public double getPrixvente(){
        return this.prixvente;
    }
    
}