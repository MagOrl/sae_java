
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
                    menuConnecter(usr);
                    bvn();
                    menuConnex();
                    break;
                case "2":
                    menuCreaCompte(usr);
                    bvn();
                    menuConnex();
                    break;
                case "3":
                    menuAdmin(connexionAdmin(usr), usr);
                    bvn();
                    menuConnex();
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
        System.out.println("│ [3] Se connecter en tant qu'administrateur                                         │");
        System.out.println("│                                                                                    │");
        System.out.println("│ [0] Quitter                                                                        │");
        System.out.println("╰────────────────────────────────────────────────────────────────────────────────────╯");
        System.out.println("entrez une séléction");
    }

    private static void menuConnecter(Scanner usr) {
        Requetes query = new Requetes(connexion);
        System.out.println("╭────────────────────────────────────────────────────────────────────────────────────╮");
        System.out.println("│ CONNEXION                                                                          │");
        System.out.println("│                                                                                    │");
        System.out.println("│ [0] Quitter                                                                        │");
        System.out.println("╰────────────────────────────────────────────────────────────────────────────────────╯");
        try {
            String identif = demandeConnexion("identifiant", usr);
            String mdp = demandeConnexion("mot de passe", usr);
            if (query.connectClient(identif, mdp)) {
                bvn();
                menuClient(query.trouveClient(identif, mdp), usr);
            } else {
                System.out.println("Mauvais mot de passe ou identifiant veuillez réesayer");
                System.out.println("Si vous n'avez pas de compte, veuillez en crée un");
                menuConnecter(usr);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (QuitterExecption e) {
            menuConnex();
            return;
        }
    }

    private static void menuCreaCompte(Scanner usr) {
        Requetes query = new Requetes(connexion);
        System.out.println("╭────────────────────────────────────────────────────────────────────────────────────╮");
        System.out.println("│ Pour créer le compte, veuillez entrer correctement les informations.               │");
        System.out.println("│                                                                                    │");
        System.out.println("│ [0]Quitter                                                                         │");
        System.out.println("╰────────────────────────────────────────────────────────────────────────────────────╯");
        try {
            query.creeClient(demandeUtilisateur("indentifiant", usr), demandeUtilisateur("nom", usr),
                    demandeUtilisateur("prenom", usr),
                    demandeUtilisateur("code postale", usr),
                    demandeUtilisateur("adresse", usr), demandeUtilisateur("ville", usr),
                    demandeUtilisateur("email", usr),
                    demandeUtilisateur("numéro de téléphone", usr),
                    demandeUtilisateur("mot de passe", usr));
        } catch (NumberFormatException | SQLException e) {
            e.printStackTrace();
        } catch (QuitterExecption e) {
            bvn();
            menuConnex();
            return;
        }
        menuConnex();
    }

    private static String demandeUtilisateur(String demande, Scanner usr) throws QuitterExecption {
        System.out.println("Entrez votre " + demande);
        String res = usr.nextLine();
        try {
            if (Integer.parseInt(res) == 0) {
                throw new QuitterExecption();
            } else {
                System.out.println("Votre " + demande + " " + res);
                System.out.println("");
                return res;
            }
        } catch (NumberFormatException e) {
            System.out.println("Votre " + demande + " " + res);
            System.out.println("");
            return res;
        }
    }

    private static String demandeConnexion(String demande, Scanner usr) throws QuitterExecption {
        System.out.println("Entrez votre " + demande);
        String res = usr.nextLine();
        try {
            if (Integer.parseInt(res) == 0) {
                throw new QuitterExecption();
            } else {
                System.out.println("Votre " + demande + " " + res);
                System.out.println(" ");
            }
        } catch (NumberFormatException e) {
            System.out.println("Votre " + demande + " " + res);
            System.out.println(" ");
        }
        return res;
    }

    private static void afficheMenuClient(Client cli) {
        System.out.println("╭────────────────────────────────────────────────────────────────────────────────────╮");
        System.out.println("│ Bonjour " + cli.getIdentifiant()
                + "                                                                       │");
        System.out.println("│                                                                                    │");
        System.out.println("│ [1] Consulte les catalogues                                                        │");
        System.out.println("│                                                                                    │");
        System.out.println("│ [2] Gestion comptes                                                                │");
        System.out.println("│                                                                                    │");
        System.out.println("│ [3] Historique de commandes                                                        │");
        System.out.println("│                                                                                    │");
        System.out.println("│ [0] Quitter                                                                        │");
        System.out.println("╰────────────────────────────────────────────────────────────────────────────────────╯");
    }

    private static void menuClient(Client cli, Scanner usr) {
        afficheMenuClient(cli);
        while (usr.hasNext()) {
            String res = usr.nextLine();
            switch (res) {
                case "0":
                    return;
                case "1":
                    consulteCatalogue(cli, usr);
                    afficheMenuClient(cli);
                    break;
                case "2":
                    gestionCompte(cli, usr);
                    afficheMenuClient(cli);
                    break;
                case "3":
                    afficheHistorique(cli, usr);
                    afficheMenuClient(cli);
                    break;
            }
        }
    }

    private static void afficheGestionCompte() {
        System.out.println("╭────────────────────────────────────────────────────────────────────────────────────╮");
        System.out.println("│ [1] Voir mes informations personelle                                               │");
        System.out.println("│                                                                                    │");
        System.out.println("│ [2] Changer mes informations personelle                                            │");
        System.out.println("│                                                                                    │");
        System.out.println("│ [0]Quitter                                                                         │");
        System.out.println("╰────────────────────────────────────────────────────────────────────────────────────╯");

    }

    private static void gestionCompte(Client cli, Scanner usr) {
        afficheGestionCompte();
        while (usr.hasNext()) {
            String res = usr.nextLine();
            switch (res) {
                case "0":
                    return;
                case "1":
                    voirInfoperso(cli, usr);
                    afficheGestionCompte();
                    break;
                case "2":
                    changeInfoPerso(cli, usr);
                    afficheGestionCompte();
                    break;
            }

        }
    }

    private static void voirInfoperso(Client cli, Scanner usr) {
        System.out.println("╭────────────────────────────────────────────────────────────────────────────────────╮");
        System.out.println("│ Voici vos informations :                                                           │");
        System.out.println(cli);
        System.out.println("╰────────────────────────────────────────────────────────────────────────────────────╯");
        System.out.println(" [0] Quitter");
        while (usr.hasNext()) {
            String res = usr.nextLine();
            switch (res) {
                case "0":
                    return;
                default:
                    System.out.println("C'est 0 pour quitter");
                    break;
            }

        }
    }

    private static void afficheChangeInfoPerso() {
        System.out.println("╭────────────────────────────────────────────────────────────────────────────────────╮");
        System.out.println("│ Que voulez vous changer ?                                                          │");
        System.out.println("│                                                                                    │");
        System.out.println("│ [1] Mon identifiant                                                                │");
        System.out.println("│                                                                                    │");
        System.out.println("│ [2] Mon mot de passe                                                               │");
        System.out.println("│                                                                                    │");
        System.out.println("│ [3] Mon email                                                                      │");
        System.out.println("│                                                                                    │");
        System.out.println("│ [4] Mon numéro de téléphone                                                        │");
        System.out.println("│                                                                                    │");
        System.out.println("│ [0] Quitter                                                                        │");
        System.out.println("│                                                                                    │");
        System.out.println("╰────────────────────────────────────────────────────────────────────────────────────╯");

    }

    private static void changeInfoPerso(Client cli, Scanner usr) {
        afficheChangeInfoPerso();
        Requetes query = new Requetes(connexion);
        while (usr.hasNext()) {
            String res = usr.nextLine();
            switch (res) {
                case "0":
                    afficheGestionCompte();
                    break;
                case "1":
                    System.out.println("Le nouveau identifiant :");
                    cli.setIdentifiant(usr.nextLine());
                    afficheChangeInfoPerso();
                    break;
                case "2":
                    System.out.println("Le nouveau mot de passe :");
                    cli.setMdp(usr.nextLine());
                    afficheChangeInfoPerso();
                    break;
                case "3":
                    System.out.println("Le nouveau email :");
                    cli.setEmail(usr.nextLine());
                    afficheChangeInfoPerso();
                    break;
                case "4":
                    System.out.println("Le nouveau numéro de téléphone :");
                    cli.setTel(Integer.parseInt(usr.nextLine()));
                    afficheChangeInfoPerso();
                    break;
                default:
                    System.out.println("les choix vont de 0 à 4");
                    break;
            }
            try {
                query.majClient(cli);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private static void afficheHistorique(Client cli, Scanner usr) {
        Requetes query = new Requetes(connexion);
        String res = "";
        try {
            res = query.afficheHistoriqueCommande(cli);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("╭────────────────────────────────────────────────────────────────────────────────────╮");
        System.out.println("│ L'historique de vos commandes :                                                    │");
        System.out.println(res);
        System.out.println("│ [0] Quitter                                                                        │");
        System.out.println("╰────────────────────────────────────────────────────────────────────────────────────╯");
        while (usr.hasNext()) {
            String rep = usr.nextLine();
            switch (rep) {
                case "0":
                    return;
                default:
                    System.out.println("0 pour quitter");
                    break;
            }

        }

    }

    private static void afficheConsulteCatalogue() {
        System.out.println("╭────────────────────────────────────────────────────────────────────────────────────╮");
        System.out.println("│ Les catalogues :                                                                   │");
        System.out.println("│                                                                                    │");
        System.out.println("│ [1] Rechercher selon critère                                                       │");
        System.out.println("│                                                                                    │");
        System.out.println("│ [2] Le catalogue                                                                   │");
        System.out.println("│                                                                                    │");
        System.out.println("│ [0] Quitter                                                                        │");
        System.out.println("╰────────────────────────────────────────────────────────────────────────────────────╯");

    }

    private static void consulteCatalogue(Client cli, Scanner usr) {
        afficheConsulteCatalogue();
        while (usr.hasNext()) {
            String res = usr.nextLine();
            switch (res) {
                case "0":
                    return;
                case "1":
                    rechercheLivre(cli, usr);
                    afficheConsulteCatalogue();
                    break;
                case "2":
                    System.out.println("to be built");
                    afficheConsulteCatalogue();
                default:
                    System.out.println("Mettre une valeur entre 0 et 2");
                    break;
            }
        }
    }

    private static void afficheRechercheLivre() {
        System.out.println("╭────────────────────────────────────────────────────────────────────────────────────╮");
        System.out.println("│ Les catalogues :                                                                   │");
        System.out.println("│                                                                                    │");
        System.out.println("│ [1] Livres selon une catégorie                                                     │");
        System.out.println("│                                                                                    │");
        System.out.println("│ [2] Le catalogue                                                                   │");
        System.out.println("│                                                                                    │");
        System.out.println("│ [0] Quitter                                                                        │");
        System.out.println("╰────────────────────────────────────────────────────────────────────────────────────╯");
    }

    private static void rechercheLivre(Client cli, Scanner usr) {
        afficheRechercheLivre();
        boolean quitte = false;
        while (!quitte && usr.hasNext()) {
            String res = usr.nextLine();
            switch (res) {
                case "0":
                    quitte = true;
                    afficheConsulteCatalogue();
                    break;

                default:
                    System.out.println("Veuillez enregistrer une séléction valide");
                    break;
            }

        }
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
        afficheMenuAdmin(admin);
        boolean quitter = false;
        while (!quitter && usr.hasNext()) {
            String res = usr.nextLine();
            switch (res) {
                case "0":
                    quitter = true;
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

                case "5":
                    quitter = true;
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
        afficheMenuGererStocksGlobaux(admin, usr, mag);
        boolean retour = false;
        while (!retour && usr.hasNext()) {
            String res = usr.nextLine();
            switch (res) {
                case "1":
                    infosAjouteLivre(admin, usr, mag);
                    break;
                case "2":
                case "3":
                case "4": //afficher le stock de la librairie concernée
                case "5":
                    choixLibrairie(admin, usr);
                    break;
                case "0":
                    menuAdmin(admin, usr);
                    break; //quitter
                default:
                    System.out.println("veuillez entrer un nombre entre 1 et 6");

            }
        }
    }

    private static void infosAjouteLivre(Administrateur admin, Scanner usr, Magasin mag) {
        AdministrateurBD adminBd = new AdministrateurBD(connexion);
        try {
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

            adminBd.AjouterLivre(titre, auteur, editeur, theme, nbpages, datepubli, prix, qte, mag);
            System.out.println("Le livre a bien été ajouté");
            menuGererStocksGlobaux(admin, usr, mag);
        } catch (NumberFormatException e) {
            System.out.println("Veuillez entrez uniquement des chiffres pour le nombre de pages et la date de publication");
            infosAjouteLivre(admin, usr, mag);
        } catch (SQLException e) {
            System.out.println("Une erreur est survenue lors de l'ajout du livre");
            menuGererStocksGlobaux(admin, usr, mag);
        }
    }
}

// SELECT LIVRE.*
