
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class ExecutableAdmin {

    private static ConnexionMySQL connexion;
    private static AdministrateurBD adminBD;

    public static void main(String[] args){
        try{
        connexion = new ConnexionMySQL();
        adminBD = new AdministrateurBD(connexion);
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
                    menuAdmin(connexionAdmin(usr), usr);
                    break;
                case "2":
                    menuCreerCompte(usr);
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
        System.out.println("│ [2] Créer un compte                                                                │");
        System.out.println("│                                                                                    │");
        System.out.println("│ [0] Quitter                                                                        │");
        System.out.println("╰────────────────────────────────────────────────────────────────────────────────────╯");
        System.out.println("entrez une séléction");
    }

    private static Administrateur connexionAdmin(Scanner usr) {
        Administrateur admin = null;
        String identifiant = null;
        String mdp = null;
        
        System.out.println("Entrez votre identifiant");
        identifiant = usr.nextLine();
        System.out.println("Entrez votre mot de passe");
        mdp = usr.nextLine();
        try{
        if(adminBD.connectAdmin(identifiant, mdp)){
            menuAdmin(adminBD.trouveAdmin(identifiant, mdp), usr);
        }else{
            System.out.println("Nous n'avons pas pu trouver votre compte veuillez réessayer, si vous n'avez pas de compte créez en un");
            principal(usr);
        }
        admin = adminBD.trouveAdmin(identifiant, mdp);
        }catch(SQLException e){
            System.out.println("Nous n'avons pas pu trouver votre compte veuillez réessayer, si vous n'avez pas de compte créez en un");
            principal(usr);
        }
        return admin; 

    }

    private static void menuCreerCompte(Scanner usr){
        System.out.println("Entrez votre email");
        String email = usr.nextLine();
        System.out.println("Entrez votre Identifiant");
        String identifiant = usr.nextLine();

        System.out.println("Entrez votre mot de passe");
        String mdp = usr.nextLine();

        System.out.println("Entrez votre nom");
        String nom = usr.nextLine();

        System.out.println("Entrez votre prénom");
        String prenom = usr.nextLine();

        System.out.println("Entrez votre addresse");
        String addresse = usr.nextLine();

        System.out.println("Entrez la ville ou vous habitez");
        String ville = usr.nextLine();
 
        System.out.println("Entrez votre code postal");
        String codePostal = usr.nextLine();

        System.out.println("Entrez votre numéro de télephone");
        String numTel = usr.nextLine();

        try{
        adminBD.creeClient(identifiant, nom, prenom, addresse, codePostal, ville, email, numTel, mdp);
        System.out.println("Compte créé avec succès");
        menuConnex();
        }catch(NumberFormatException e){
            System.out.println("Veuillez entrer uniquement des nombres pour le numéro de téléphone");
            menuConnex();
        }catch(SQLException e){
            System.out.println("Une erreur est survenue lors de la création du compte");
            menuConnex();
        }

    }

    private static void afficheMenuAdmin(Administrateur admin){
            bvn();
            System.out.println("╭────────────────────────────────────────────────────────────────────────────────────╮");
            System.out.println("│ Bonjour " + admin.getPrenom() + " que souhaitez vous faire ?                       │");
            System.out.println("│                                                                                    │");
            System.out.println("│ [1] Créer un compte vendeur                                                        │");
            System.out.println("│                                                                                    │");
            System.out.println("│ [2] Ajouter une nouvelle librairie au réseau                                       │");
            System.out.println("│                                                                                    │");
            System.out.println("│ [3] Gerer les stocks globaux                                                       │");
            System.out.println("│                                                                                    │");
            System.out.println("│ [4] Consulter les statistiques de ventes                                           │");
            System.out.println("│                                                                                    │");
            System.out.println("│ [0] Quitter                                                                        │");
            System.out.println("╰────────────────────────────────────────────────────────────────────────────────────╯");
    }

    private static void menuAdmin(Administrateur admin, Scanner usr) {
        afficheMenuAdmin(admin);
        while (usr.hasNext()) {
            String res = usr.nextLine();
            switch (res) {
                case "0":
                    menuConnex();
                    return;
                case "1":
                    creerCompteVendeur(usr);
                    afficheMenuAdmin(admin);
                    break;

                case "2":
                    ajouterNouvelleLibrairie(usr);
                    afficheMenuAdmin(admin);
                    break;

                case "3":
                    menuGererStocksGlobaux(admin, usr, choixLibrairie(admin, usr));
                    afficheMenuAdmin(admin);
                    break;

                case "4":
                
                    break;
                default:
                    System.out.println("Veuillez entrer un chiffre parmis la liste d'options du menu");
                    break;
            }
        }

    }

    private static void creerCompteVendeur(Scanner usr) {
            System.out.println("Entrez l'identifiant du vendeur");
            String identifiant = usr.nextLine();

            System.out.println("Entrez le nom du vendeur");
            String nom = usr.nextLine();

            System.out.println("Entrez le prénom du vendeur");
            String prenom = usr.nextLine();

            System.out.println("Entrez l'adresse du vendeur");
            String adresse = usr.nextLine();

            System.out.println("Entrez le codePostal du vendeur");
            String codePostal = usr.nextLine();

            System.out.println("Entrez la ville du vendeur");
            String ville = usr.nextLine();

            System.out.println("Entrez l'email du vendeur");
            String email = usr.nextLine();

            System.out.println("Entrez le numéro de télephone du vendeur");
            String tel = usr.nextLine();

            System.out.println("Entrez le mot de passe du vendeur");
            String mdp = usr.nextLine();

            System.out.println("Entrez le nom du magasin du vendeur");
            String magasin = usr.nextLine();
        try{
            adminBD.CreerCompteVendeur(nom, prenom, identifiant, adresse, tel, email, mdp, codePostal, ville, magasin);
            System.out.println("Le compte à bien été crée");
        }catch(NumberFormatException e){
            System.out.println("Veuillez entrer uniquement des nombres pour le numéro de téléphone");
        }catch(SQLException e){
            System.out.println("Une erreur est survenue lors de la création du compte");
            
        }

        

    }

    private static void ajouterNouvelleLibrairie(Scanner usr) {
        try {
            System.out.println("Entrez le nom de la librairie");
            String nommag = usr.nextLine();

            System.out.println("Entrez la ville de la librairie");
            String villemag = usr.nextLine();

            adminBD.ajouteNouvelleLibrairie(nommag, villemag);
            System.out.println("La librairie a été ajoutée");
        } catch (SQLException e) {
            System.out.println("Une erreur est survenue lors de l'ajout d'une nouvelle librairie");
        }
    }

    private static void afficheChoixLibrairie(Scanner usr) {
        try {
            bvn();
            List<String> lesLibrairies = adminBD.choixLibrairie();
            System.out.println("╭────────────────────────────────────────────────────────────────────────────────────╮");
            System.out.println("│ Les librairies                                                                     │");
            for (String librairie : lesLibrairies) {
                System.out.println("│ " + "   [" + librairie + "]                                                 │");
            }
            System.out.println("│ [0] Retour                                                                         │");
            System.out.println("╰────────────────────────────────────────────────────────────────────────────────────╯");
        } catch (SQLException e) {
            System.out.println("Une erreur est survenue lors de l'affichage des librairie");
        }
    }

    private static Magasin choixLibrairie(Administrateur admin, Scanner usr) {
        try {
            afficheChoixLibrairie(usr);
            List<String> lesLibrairies = adminBD.choixLibrairie();

            System.out.println("Entrez le nom de la librairie dont vous voulez gérer les stocks");
            String librairie = usr.nextLine();

            if(lesLibrairies.contains(librairie)){
                return adminBD.trouveLibrairie(librairie);
            }else if(librairie.equals("0")){
                return null;
            }
            else{
                System.out.println("Veuillez entrez le nom exact de la librairie et une librairie qui figure parmis la liste");
                choixLibrairie(admin, usr);
            }
        }catch (SQLException e) {
            System.out.println("Une erreur est survenue lors du choix de la librairie");
            menuAdmin(admin, usr);
        }
        return null;
    }

    private static void afficheMenuGererStocksGlobaux(Administrateur admin, Scanner usr, Magasin mag) {
        bvn();
        System.out.println("╭────────────────────────────────────────────────────────────────────────────────────╮");
        System.out.println("│ Que souhaitez vous faire ?                  Librairie actuelle : " + mag.getNom() + " │");
        System.out.println("│                                                                                    │");
        System.out.println("│ [1] Ajouter un livre a une librairie                                               │");
        System.out.println("│                                                                                    │");
        System.out.println("│ [2] Supprimer un livre d'une librairie                                             │");
        System.out.println("│                                                                                    │");
        System.out.println("│ [3] Mettre à jour la quantité d'un livre                                           │");
        System.out.println("│                                                                                    │");
        System.out.println("│ [4] Afficher le stock de la librairie                                              │");
        System.out.println("│                                                                                    │");
        System.out.println("│ [5] Changer de librairie                                                           │");
        System.out.println("│                                                                                    │");
        System.out.println("│ [0] Retour                                                                         │");
        System.out.println("╰────────────────────────────────────────────────────────────────────────────────────╯");
    }

    private static void menuGererStocksGlobaux(Administrateur admin, Scanner usr, Magasin mag){
        if(mag == null){
            return;
        }
        afficheMenuGererStocksGlobaux(admin, usr, mag);
        while (usr.hasNext()) {
            String res = usr.nextLine();
            switch (res) {
                case "1":
                    infosAjouteLivre(admin, usr, mag);
                    afficheMenuGererStocksGlobaux(admin, usr, mag);
                    break;
                case "2":
                    getIsbnSupprLivre(admin, usr, mag);
                    afficheMenuGererStocksGlobaux(admin, usr, mag);
                    break;
                case "3":
                    majQteLivre(usr, mag);
                    afficheMenuGererStocksGlobaux(admin, usr, mag);
                    break;
                case "4":
                    afficherStockLibrairie(mag);
                    afficheMenuGererStocksGlobaux(admin, usr, mag);
                    break;
                 //afficher le stock de la librairie concernée
                case "5":
                    mag = choixLibrairie(admin, usr);
                    afficheMenuGererStocksGlobaux(admin, usr, mag);
                    break;
                case "0":
                    return; //quitter
                default:
                    System.out.println("veuillez entrer un nombre entre 1 et 6");

            }
        }
    }

    private static void infosAjouteLivre(Administrateur admin, Scanner usr, Magasin mag) {
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
            adminBD.AjouterLivre(isbn, titre, auteur, editeur, theme, nbpages, datepubli, prix, qte, mag);
            System.out.println("Le livre a bien été ajouté");
            //menuGererStocksGlobaux(admin, usr, mag);
        } catch (NumberFormatException e) {
            System.out.println("Veuillez entrez uniquement des chiffres pour le nombre de pages, la date de publication et la quantité");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Une erreur est survenue lors de l'ajout du livre");
        }
        
         
    }

    private static void getIsbnSupprLivre(Administrateur admin, Scanner usr, Magasin mag){
        try{
            System.out.println("Entrez l'isbn du livre à supprimer");
            String res = usr.nextLine();
            if(adminBD.SupprimerLivre(res, mag)){
                System.out.println("Le livre a été supprimé avec succès");
            }else{
                System.out.println("Le livre que vous essayez de supprimer n'existe pas dans la librairie actuelle");
            }
        }catch (NumberFormatException e) {
            System.out.println("Veuillez entrez uniquement des chiffres pour le quantité");
        }catch(SQLException e){
            //System.out.println(e.getMessage());
            System.out.println("Une erreur est survenue lors de la suppression du livre");
        }
    }

    private static void majQteLivre(Scanner usr, Magasin mag){
        System.out.println("Entrez l'isbn du livre dont vous voulez mettre à jour la quandtité");
        String isbn = usr.nextLine();

        System.out.println("Entrez la nouvelle quantité");
        System.out.println("Attention, ceci ne va pas ajouter ou enlever en quantité mais mettre le chiffre que vous allez rentrer en nouvelle quantité !");
        String qte = usr.nextLine();


        try{
            if(adminBD.majQteLivre(isbn, mag, qte)){
                System.out.println("La quantité a bien été mise à jour");
            }else{
                System.out.println("Le livre dont vous essayez de modifier la quantité n'existe pas dans la libraire actulle (" + mag.getNom() + ")");
            }
        }catch(NumberFormatException e){
            System.out.println("Veuillez entrez uniquement des chiffres pour la quantité");
        }catch(SQLException e){
            System.out.println(e.getMessage());
            System.out.println("Une erreur est survenue lors de la mise à jour de la quantité");
        }
    }

    private static void afficherStockLibrairie(Magasin mag){
        try{
            adminBD.afficherStockLibrairie(mag);
        }catch(SQLException e){
            System.out.println("Une erreur est survenue lors de l'affichage du stock de la librairie");
        }
    }

    private static void afficheMenuStatsVentes(){
        bvn();
        System.out.println("╭────────────────────────────────────────────────────────────────────────────────────╮");
        System.out.println("│ Que souhaitez vous faire ?                                                         │");
        System.out.println("│                                                                                    │");
        System.out.println("│ [1] Nombre de livres vendus par magasin par année                                  │");
        System.out.println("│                                                                                    │");
        System.out.println("│ [2] Les livres les plus vendus du mois                                             │");
        System.out.println("│                                                                                    │");
        System.out.println("│ [3] Comparaison ventes en ligne et en magasin                                      │");
        System.out.println("│                                                                                    │");
        System.out.println("│ [4] Valeur du stock par magasins                                                   │");
        System.out.println("│                                                                                    │");
        System.out.println("│ [5] Chiffre d'affaire par librairie par année                                      │");
        System.out.println("│                                                                                    │");
        System.out.println("│ [0] Retour                                                                         │");
        System.out.println("╰────────────────────────────────────────────────────────────────────────────────────╯");
    }

    
}

// SELECT LIVRE.*
