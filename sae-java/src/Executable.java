
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class Executable {

    private static ConnexionMySQL connexion;

    public static void main(String[] args) throws ClassNotFoundException {
        connexion = new ConnexionMySQL();
        Scanner usr = new Scanner(System.in);
        principal(usr);
    }

    public static void principal(Scanner usr) {
        bvn();
        menuConnex();
        String res = "";
        while (usr.hasNextLine()) {
            res = usr.nextLine();
            switch (res) {
                case "0":
                    return;
                case "1":
                    menuAdmin(connexionAdmin(usr), usr);
                    bvn();
                    menuConnex();
                    break;
                case "2":
                    bvn();
                    menuConnex();
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
        AdministrateurBD Aconnexion = new AdministrateurBD(connexion);
        Administrateur admin = null;
        String identifiant = null;
        String mdp = null;
        try {
            System.out.println("Entrez votre identifiant");
            identifiant = usr.nextLine();
            System.out.println("Entrez votre mot de passe");
            mdp = usr.nextLine();
            admin = Aconnexion.trouveAdmin(identifiant, mdp);
        } catch (SQLException e) {
            System.out.println("Identifiant ou mot de passe incorect");

        }
        return admin;

    }

    private static void afficheMenuAdmin(Administrateur admin) {
        try {
            System.out.println("╭────────────────────────────────────────────────────────────────────────────────────╮");
            System.out.println("│ Bonjour " + admin.getPrenom() + " que souhaitez vous faire ?                       │");
            System.out.println("│                                                                                    │");
            System.out.println("│ [1] Créer un compte vendeur                                                        │");
            System.out.println("│                                                                                    │");
            System.out.println("│ [2] Ajouter une nouvelle librairie au réseau                                       │");
            System.out.println("│                                                                                    │");
            System.out.println("│ [3] Gerer les stocks gloaux                                                        │");
            System.out.println("│                                                                                    │");
            System.out.println("│ [4] Consulter les statistiques de ventes                                           │");
            System.out.println("│                                                                                    │");
            System.out.println("│ [0] Quitter                                                                        │");
            System.out.println("╰────────────────────────────────────────────────────────────────────────────────────╯");
        } catch (NullPointerException e) {
            System.out.println("Identifiant ou mot de passe incorect");

        }
    }

    private static void menuAdmin(Administrateur admin, Scanner usr) {
        bvn();
        afficheMenuAdmin(admin);
        while (usr.hasNext()) {
            String res = usr.nextLine();
            switch (res) {
                case "0":
                    return;
                case "1":
                    creerCompteVendeur(admin, usr);
                    break;

                case "2":
                    ajouterNouvelleLibrairie(admin, usr);
                    break;

                case "3":
                    choixLibrairie(admin, usr);
                    break;

                case "4":

                    break;
                default:
                    System.out.println("Veuillez entrer un chiffre parmis la liste d'options du menu");
                    break;
            }
        }

    }

    private static void creerCompteVendeur(Administrateur admin, Scanner usr) {
        try {
            AdministrateurBD adminBD = new AdministrateurBD(connexion);
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
            adminBD.CreerCompteVendeur(nom, prenom, identifiant, adresse, tel, email, mdp, codePostal, ville, magasin);
            System.out.println("Le compte à bien été crée");
            menuAdmin(admin, usr);
        } catch (SQLException e) {
            System.out.println("Un problème est survenu lors de la création du compte");
            menuAdmin(admin, usr);
        }

    }

    private static void ajouterNouvelleLibrairie(Administrateur admin, Scanner usr) {
        AdministrateurBD adminBD = new AdministrateurBD(connexion);
        try {
            System.out.println("Entrez le nom de la librairie");
            String nommag = usr.nextLine();

            System.out.println("Entrez la ville de la librairie");
            String villemag = usr.nextLine();

            adminBD.ajouteNouvelleLibrairie(nommag, villemag);
            System.out.println("La librairie a été ajoutée");
            menuAdmin(admin, usr);
        } catch (SQLException e) {
            System.out.println("Une erreur est survenue lors de l'ajout d'une nouvelle librairie");
            menuAdmin(admin, usr);
        }
    }

    private static void afficheChoixLibrairie(Administrateur admin, Scanner usr) {
        try {
            AdministrateurBD adminBD = new AdministrateurBD(connexion);
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
            menuAdmin(admin, usr);
        }
    }

    private static void choixLibrairie(Administrateur admin, Scanner usr) {
        AdministrateurBD adminBd = new AdministrateurBD(connexion);
        try {
            afficheChoixLibrairie(admin, usr);
            System.out.println("Entrez le nom de la librairie que vous voulez choisir");
            String librairie = usr.nextLine();
            menuGererStocksGlobaux(admin, usr, adminBd.trouveLibrairie(librairie));
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Une erreur est survenue lors du choix de la librairie");
            menuAdmin(admin, usr);
        }
    }

    private static void afficheMenuGererStocksGlobaux(Administrateur admin, Scanner usr, Magasin mag) {
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

    private static void menuGererStocksGlobaux(Administrateur admin, Scanner usr, Magasin mag) {
        AdministrateurBD adminBd = new AdministrateurBD(connexion);
        afficheMenuGererStocksGlobaux(admin, usr, mag);
        while (usr.hasNext()) {
            String res = usr.nextLine();
            switch (res) {
                case "1":
                    infosAjouteLivre(admin, usr, mag);
                    break;
                case "2":
                    getIsbnSupprLivre(admin, usr, mag);
                case "3":
                case "4": //afficher le stock de la librairie concernée
                case "5":
                    choixLibrairie(admin, usr);
                    break;
                case "0":
                    return; //quitter
                default:
                    System.out.println("veuillez entrer un nombre entre 1 et 6");

            }
        }
    }

    private static void infosAjouteLivre(Administrateur admin, Scanner usr, Magasin mag) {
        AdministrateurBD adminBd = new AdministrateurBD(connexion);
        try {
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

            adminBd.AjouterLivre(isbn, titre, auteur, editeur, theme, nbpages, datepubli, prix, qte, mag);
            System.out.println("Le livre a bien été ajouté");
            //menuGererStocksGlobaux(admin, usr, mag);
        } catch (NumberFormatException e) {
            System.out.println("Veuillez entrez uniquement des chiffres pour le nombre de pages et la date de publication");
        } catch (SQLException e) {
            System.out.println("Une erreur est survenue lors de l'ajout du livre");
            menuGererStocksGlobaux(admin, usr, mag);
        }
    }

    private static void getIsbnSupprLivre(Administrateur admin, Scanner usr, Magasin mag){
        AdministrateurBD adminBd = new AdministrateurBD(connexion);
        try{
            System.out.println("Entrez l'isbn du livre à supprimer");
            String res = usr.nextLine();
            adminBd.SupprimerLivre(res, mag);
        }catch(SQLException e){
            System.out.println("Une erreur est survenue lors de la supprssion du livre");
        }
    }
}

// SELECT LIVRE.*
