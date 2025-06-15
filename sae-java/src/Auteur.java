import java.time.LocalDate;

public class Auteur {

    /**
     * Attibuts de la classe Auteur
     */
    private int idAuteur;
    private String nomAuteur;
    private String prenomAuteur;
    private LocalDate dateNaiss;
    private LocalDate dateDeces;


    /**
     * Constructeur de la classe Auteur
     */
    public Auteur(int idAuteur, String nomAuteur, String prenomAuteur, LocalDate dateNaiss, LocalDate dateDeces) {
        this.idAuteur = idAuteur;
        this.nomAuteur = nomAuteur;
        this.prenomAuteur = prenomAuteur;
        this.dateNaiss = dateNaiss;
        this.dateDeces = dateDeces;
    }

    /**
     * Methodes de la classe Auteur
     */


    /**
     * Récupère l'Id de l'auteur
     * 
     * @return Id de l'auteur
     */
    public int getIdAuteur() {
        return this.idAuteur;
    }

    /**
     * Récupère le nom de l'auteur
     * 
     * @return Nom de l'auteur
     */
    public String getNomAuteur() {
        return this.nomAuteur;
    }

    /**
     * Récupère le prénom de l'auteur
     * 
     * @return Prenom de l'auteur
     */
    public String getPrenomAuteur() {
        return this.prenomAuteur;
    }

    /**
     * Récupère la date de naissance de l'auteur
     * 
     * @return Date de naissance de l'auteur
     */
    public LocalDate getDateNaiss() {
        return this.dateNaiss;
    }

    /**
     * Récupère la date de décès de l'auteur
     * 
     * @return Date de deces de l'auteur
     */
    public LocalDate getDateDeces() {
        return this.dateDeces;
    }
    
    /**
     * Afficheur de la classe Auteur
     */
    @Override
    public String toString() {
        return prenomAuteur + " " + nomAuteur + " (" + dateNaiss + " - " + (dateDeces != null ? dateDeces : "encore vivant") + ")";
    }



}
