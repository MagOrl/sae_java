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
            System.out.println("Nous n'avons pas vu connecter l'application à la base de données");
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
        System.out.println("entrez une séléction");
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
            System.out.println("Nous n'avons pas pu trouver votre compte veuillez réessayer, si vous n'avez pas de compte veuillez demander à un administrateur de vous en créer en un");
            principal(usr);
        }
        vendeur = vendeurBD.trouveVendeur(identifiant, mdp, mag);
        }catch(SQLException e){
            System.out.println("Nous n'avons pas pu trouver votre compte veuillez réessayer, si vous n'avez pas de compte veuillez demander à un administrateur de vous en créer en un");
        }
        return vendeur; 

    }

    public static void afficheMenuVendeur(Vendeur vendeur, Scanner usr){
        bvn();
            System.out.println("╭────────────────────────────────────────────────────────────────────────────────────╮");
            System.out.println("│ Bonjour " + vendeur.getPrenom() + " que souhaitez vous faire ?                     │");
            System.out.println("│                                                                                    │");
            System.out.println("│ [1] Ajouter un livre au stock de la librairie.                                     │");
            System.out.println("│                                                                                    │");
            System.out.println("│ [2] Mettre à jour la quantité disponible d'un livre                                │");
            System.out.println("│                                                                                    │");
            System.out.println("│ [3] Passer une commande pour un client                                             │");
            System.out.println("│                                                                                    │");
            System.out.println("│ [4] Transférer un livre d'une autre librairie                                      │");
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
                    break;
                case "2": 
                    break;
                case "3":
                    break;
                case "4":
                    break;
                default:
                    System.out.println("Veuillez entrer uniquement des chiifres (0-4)");
                    break;
            }
        }
    }

    public static void ajouteLivre(Vendeur vendeur, Scanner usr){

    }

    public static void majQteLivre(Vendeur vendeur, Scanner usr){

    }

    public static void passerCommandeClient(Vendeur vendeur, Scanner usr){

    }

    public static void transfererLivre(Vendeur vendeur, Scanner usr){
        //Transférer un livre d’une autre librairie pour satisfaire une commande client

    }

    

}