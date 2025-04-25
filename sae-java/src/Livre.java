public class Livre{
    private int isbn;
    private String titre;
    private int nbpages;
    private String datepubli;
    private int prix;

    public Livre(int id, String titre, int nbpages, String datepubli, int prix){
        this.isbn = id;
        this.titre = titre;
        this.nbpages = nbpages;
        this.datepubli = datepubli;
        this.prix = prix;
    }

    public int getIsbn(){
        return this.isbn;
    }

    public String getTitre(){
        return this.titre;
    }

    public int getNbPages(){
        return this.nbpages;
    }

    public String getDatepubli(){
        return this.datepubli;
    }
    
    public int getPrix(){
        return this.prix;
    }
}
