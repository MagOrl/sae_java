import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class ExecutableVendeur{

    private static ConnexionMySQL connexion;
    private static VendeurBD vendeurBD;

    public static void main(String[] args){
        try{
        connexion = new ConnexionMySQL();
        vendeurBD = new VendeurBD(connexion);
        Scanner usr = new Scanner(System.in);
        principal(usr);
        }    
        catch(ClassNotFoundException e){
            System.out.println("Nous n'avons pas pu connecter l'application à la base de données");
        }
    }

    public static void principal(Scanner usr) throws NullPointerException{
        menuConnex();
        String res = "";
        while (usr.hasNextLine()) {
            res = usr.nextLine();
            switch (res) {
                case "0":
                    return;
                case "1":
                    menuVendeur(connexionVendeur(usr), usr);
                    break;
                default:
                    System.out.println("Entrez un chiffre entre 0 et 2 svp.");
            }

        }
    }

    private static void bvn() {
        System.out.println(
                "                      _    _               ___                        \n"
                + //
                "                     | |  (_)_ ___ _ ___  | __|_ ___ __ _ _ ___ ______\n"
                + //
                "                     | |__| \\ V / '_/ -_) | _|\\ \\ / '_ \\ '_/ -_|_-<_-<\n"
                + //
                "                     |____|_|\\_/|_| \\___| |___/_\\_\\ .__/_| \\___/__/__/\n"
                + //
                "                                                  |_|                 ");
    }

    private static void menuConnex() {
        bvn();
        System.out.println("╭────────────────────────────────────────────────────────────────────────────────────╮");
        System.out.println("│ [1] Se connecter                                                                   │");
        System.out.println("│                                                                                    │");
        System.out.println("│ [0] Quitter                                                                        │");
        System.out.println("╰────────────────────────────────────────────────────────────────────────────────────╯");
    }

    private static Vendeur connexionVendeur(Scanner usr) {
        Vendeur vendeur = null;
        String identifiant = null;
        String mdp = null;
        String mag = null;
        
        System.out.println("Entrez votre identifiant");
        identifiant = usr.nextLine();
        System.out.println("Entrez votre mot de passe");
        mdp = usr.nextLine();
        System.out.println("Entrez le nom de votre magasin");
        mag = usr.nextLine();
        try{
        if(vendeurBD.connectVendeur(identifiant, mdp)){
            menuVendeur(vendeurBD.trouveVendeur(identifiant, mdp, mag), usr);
        }else{
            System.out.println("Nous n'avons pas pu trouver votre compte veuillez réessayer, si vous n'avez pas de compte veuillez demander à un administrateur de vous en créer un");
            principal(usr);
        }
        vendeur = vendeurBD.trouveVendeur(identifiant, mdp, mag);
        }catch(SQLException e){
            System.out.println("Nous n'avons pas pu trouver votre compte veuillez réessayer, si vous n'avez pas de compte veuillez demander à un administrateur de vous en créer un");
            principal(usr);
        }
        return vendeur; 

    }

    public static void afficheMenuVendeur(Vendeur vendeur, Scanner usr){
        bvn();
            System.out.println("╭────────────────────────────────────────────────────────────────────────────────────╮");
            System.out.println("│ Bonjour " + vendeur.getPrenom() + " que souhaitez vous faire ?                     │");
            System.out.println("│                                                                                    │");
            System.out.println("│ [1] Ajouter un livre au stock de la librairie                                      │");
            System.out.println("│                                                                                    │");
            System.out.println("│ [2] Mettre à jour la quantité disponible d'un livre                                │");
            System.out.println("│                                                                                    │");
            System.out.println("│ [3] Vérifier la disponibilité d'un livre dans une librairie                        │");
            System.out.println("│                                                                                    │");
            System.out.println("│ [4] Passer une commande pour un client                                             │");
            System.out.println("│                                                                                    │");
            System.out.println("│ [5] Transférer un livre d'une autre librairie                                      │");
            System.out.println("│                                                                                    │");
            System.out.println("│ [0] Quitter                                                                        │");
            System.out.println("╰────────────────────────────────────────────────────────────────────────────────────╯");
    }

    public static void menuVendeur(Vendeur vendeur, Scanner usr){
        afficheMenuVendeur(vendeur, usr);
        while(usr.hasNext()){
            String res = usr.nextLine();
            switch(res){
                case "0":
                    menuConnex();
                    return;
                case "1":
                    ajouteLivre(vendeur, usr);
                    afficheMenuVendeur(vendeur, usr);
                    break;
                case "2":
                    majQteLivre(vendeur, usr);
                    afficheMenuVendeur(vendeur, usr);
                    break;
                case "3":
                    dispoLivre(usr, trouverLivre(usr));
                    afficheMenuVendeur(vendeur, usr);
                    break;
                case "4":
                    afficheMenuVendeur(vendeur, usr);
                    break;
                case "5":
                    transfererLivre(vendeur, usr);
                    afficheMenuVendeur(vendeur, usr);
                    break;
                default:
                    System.out.println("Veuillez entrer uniquement des chiffres (0-5)");
                    break;
            }
        }
    }

    public static void ajouteLivre(Vendeur vendeur, Scanner usr){
        System.out.println("Entrez l'isbn du livre");
        String isbn = usr.nextLine();

        System.out.println("Entrez le titre du livre");
        String titre = usr.nextLine();

        System.out.println("Entrez l'auteur du livre");
        String auteur = usr.nextLine();
        
        System.out.println("Entrez l'éditeur du livre");
        String editeur = usr.nextLine();

        System.out.println("Entrez le thème du livre");
        String theme = usr.nextLine();

        System.out.println("Entrez la date de publication du livre");
        String datepubli = usr.nextLine();

        System.out.println("Entrez le nombre de pages du livre");
        String nbpages = usr.nextLine();

        System.out.println("Entrez le prix du livre");
        String prix = usr.nextLine();

        System.out.println("Entrez la quantité de livre à ajouter");
        String qte = usr.nextLine();
        try{
            vendeurBD.AjouterLivre(isbn, titre, auteur, editeur, theme, nbpages, datepubli, prix, qte, vendeur.getMag());
            System.out.println("Le livre a bien été ajouté");
            //menuGererStocksGlobaux(admin, usr, mag);
        } catch (NumberFormatException e) {
            System.out.println("Veuillez entrez uniquement des chiffres pour le nombre de pages, la date de publication et la quantité");
        } catch (SQLException e) {
            System.out.println("Une erreur est survenue lors de l'ajout du livre");
        }
    }

    public static void majQteLivre(Vendeur vendeur, Scanner usr){
        System.out.println("Entrez l'isbn du livre dont vous voulez mettre à jour la quandtité");
        String isbn = usr.nextLine();

        System.out.println("Entrez la quantité à ajouter ou à enlever (pour enlever mettez un - devant le nombre)");
        String qte = usr.nextLine();
        
        try{
            if(vendeurBD.majQteLivre(isbn, vendeur.getMag(), Integer.parseInt(qte))){
                System.out.println("La quantité a bien été mise à jour");
            }else{
                System.out.println("Le livre dont vous essayez de modifier la quantité n'existe pas dans votre librairie");
            }
        }catch(NumberFormatException e){
            System.out.println("Veuillez entrez uniquement des chiffres pour la quantité");
        }catch(QteInfAZeroException e){
            System.out.println("La nouvelle quantité est inférieure à zéro, veuillez enlever moins de quantitié");    
        }catch(SQLException e){
            System.out.println(e.getMessage());
            System.out.println("Une erreur est survenue lors de la mise à jour de la quantité");
        }
    }

    private static Livre trouverLivre(Scanner usr){
        Livre livre = null;
        System.out.println("Entrez le titre du livre");
        String titre = usr.nextLine();

        System.out.println("Entrez la date de publication du livre");
        String datepubli = usr.nextLine();

        System.out.println("Entrez l'auteur du livre");
        String auteur = usr.nextLine();
        try{
            livre = vendeurBD.trouveLivre(titre, datepubli, auteur);
            return livre;
        }catch(NumberFormatException e){
            System.out.println("Veuillez entrer uniquement des chiffres pour le nombre de pages et la date de publication");
        }catch(SQLException e){
            System.out.println("Une erreur est survenue lors de la recherche du livre");
        }
        return livre;
    }

    private static void afficheLivreDispo(Livre livre, Scanner usr) {
        try {
            System.out.println("Entrez la quantité de livre dont vous voulez vérifier la disponibilité");
            String qte = usr.nextLine();
            List<String> lesLibrairies = vendeurBD.choixLibrairie();
            livre.getTitre();
            System.out.println("╭────────────────────────────────────────────────────────────────────────────────────╮");
            System.out.println("│ Disponibilité du livre " + livre.getTitre() + "                                                │");
            for (String librairie : lesLibrairies) {
                if(vendeurBD.verifDispoLivre(livre, Integer.parseInt(qte), vendeurBD.trouveLibrairie(librairie, "null"))){
                    System.out.println("│ " + "   [" + librairie + "] -> disponible                                                │");
                }else{
                    System.out.println("│ " + "   [" + librairie + "] -> indisponible                                                │");
                }
            }
            System.out.println("│ [0] Retour                                                                         │");
            System.out.println("╰────────────────────────────────────────────────────────────────────────────────────╯");
        }catch(NumberFormatException e){
            System.out.println("Veuillez entrez uniquement des chiffres pour la quantité");
        }catch(SQLException e) {
            System.out.println(e.getMessage());
            System.out.println("Une erreur est survenue lors de l'affichage des disponibilités");
        }
    }

    private static void dispoLivre(Scanner usr, Livre livre) {
        try{
            afficheLivreDispo(livre, usr);
            String retour = usr.nextLine();
            if(retour.equals("0")){
                return;
            }
        }catch(NullPointerException e){
            System.out.println("Nous n'avons pas pu trouver le livre dont vous voulez vérifier la disponibilité");
        }
        return;
    }

    public static void afficheMenuCommande(Scanner usr){
        bvn();
        System.out.println("╭────────────────────────────────────────────────────────────────────────────────────╮");
        System.out.println("│ Veuillez choisir une option                                                        │");
        System.out.println("│                                                                                    │");
        System.out.println("│ [1] Commander un livre seul                                                        │");
        System.out.println("│                                                                                    │");
        System.out.println("│ [2] Commander plusieurs livres                                                     │");
        System.out.println("│                                                                                    │");
        System.out.println("│ [0] Quitter                                                                        │");
        System.out.println("╰────────────────────────────────────────────────────────────────────────────────────╯");
    }

    public static void menuCommande(Vendeur vendeur, Scanner usr){
        while (usr.hasNext()){
            String res = usr.nextLine();
            switch (res) {
                case "0":
                    return;
                
                case "1":
                    afficheMenuVendeur(vendeur, usr);
                    break;
                
                case "2":
                    afficheMenuVendeur(vendeur, usr);
                    break;
                default:
                    System.out.println("Veuillez entrer uniquement des chiffres (0-2)");
                    break;
            }
        }
    }

    public static void passerCommandeClient(Vendeur vendeur, Scanner usr){
        
    }

    public static void transfererLivre(Vendeur vendeur, Scanner usr){
        Livre livreAtransferer = trouverLivre(usr);

        System.out.println("Entrez la quantité de livre à transférer");
        String qte = usr.nextLine();

        try{
            if(vendeurBD.transfererLivreCommande(livreAtransferer, Integer.parseInt(qte), vendeur.getMag())){
                System.out.println("Le livre a bien été transféré");
            }else{
                System.out.println("Le livre n'est disponible dans aucune autre librairie pour cette quantité");
            }
        }catch(QteInfAZeroException e){
            System.out.println("Une erreur est survenue lors du transfert du livre");
        }catch(NumberFormatException e){
            System.out.println("Veuillez entrez uniquement des chiffres pour la quantitié");
        }catch(NullPointerException e){
            System.out.println("Veuillez entrer correctement les informations du livre");
        }catch(SQLException e){
            System.out.println("Une erreur est survenue lors du transfert du livre");
        }
    }

    

}