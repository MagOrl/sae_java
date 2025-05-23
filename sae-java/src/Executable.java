
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Scanner;

public class Executable {

    private static ConnexionMySQL connexion;

    public static void main(String[] args) throws ClassNotFoundException {
        connexion = new ConnexionMySQL();
        boolean quitte = false;
        bvn();
        menuConnex();
        Scanner usr = new Scanner(System.in);
        String res = "";
        while (!quitte && usr.hasNextLine()) {
            res = usr.nextLine();
            switch (res) {
                case "0":
                    quitte = true;
                    break;
                case "1":
                    menuConnecter(usr);
                    break;
                case "2":
                    menuCreaCompte(usr);
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

    private static void menuConnecter(Scanner usr) {
        Requetes query = new Requetes(connexion);
        System.out.println("╭────────────────────────────────────────────────────────────────────────────────────╮");
        System.out.println("│ CONNEXION                                                                          │");
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
            }else {
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
            }else {
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
        System.out.println("│ [0] Déconnecter                                                                    │");
        System.out.println("╰────────────────────────────────────────────────────────────────────────────────────╯");
    }

    private static void menuClient(Client cli, Scanner usr) {
        afficheMenuClient(cli);
        boolean quitte = false;
        while (!quitte && usr.hasNext()) {
            String res = usr.nextLine();
            switch (res) {
                case "0":
                    bvn();
                    menuConnex();
                    quitte = true;
                    break;
                case "1":
                    consulteCatalogue(cli, usr);
                    break;
                case "2":
                    gestionCompte(cli, usr);
                    break;
                case "3":
                    afficheHistorique(cli, usr);
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
        boolean quitte = false;
        while (!quitte && usr.hasNext()) {
            String res = usr.nextLine();
            switch (res) {
                case "0":
                    afficheMenuClient(cli);
                    quitte = true;
                    break;
                case "1":
                    voirInfoperso(cli, usr);
                    break;
                case "2":
                    changeInfoPerso(cli, usr);
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
        boolean quitte = false;
        while (!quitte && usr.hasNext()) {
            String res = usr.nextLine();
            switch (res) {
                case "0":
                    afficheGestionCompte();
                    quitte = true;
                    break;
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
        boolean quitte = false;
        while (!quitte && usr.hasNext()) {
            String res = usr.nextLine();
            switch (res) {
                case "0":
                    afficheGestionCompte();
                    quitte = true;
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
        boolean quitte = false;
        while (!quitte && usr.hasNext()) {
            String rep = usr.nextLine();
            switch (rep) {
                case "0":
                    quitte = true;
                    afficheMenuClient(cli);
                    break;
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
        boolean quitte = false;
        while (!quitte && usr.hasNext()) {
            String res = usr.nextLine();
            switch (res) {
                case "0":
                    afficheMenuClient(cli);
                    quitte = true;
                    break;
                case "1":
                    rechercheLivre(cli, usr);
                    break;
                case "2":
                    System.out.println("to be built");
                    break;
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
        System.out.println("│ [1] Chercher selon theme                                                           │");
        System.out.println("│                                                                                    │");
        System.out.println("│ [2] Chercher selon titre                                                           │");
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
                case "1":
                    selonTheme(cli, usr);
                    break;
                default:
                    System.out.println("Veuillez entrer une séléction valide");
                    break;
            }

        }
    }

    private static void afficheSelonTheme(HashMap<Integer, String> hm) {
        for (Integer i : hm.keySet()) {
            System.out.println("[" + i + "] " + hm.get(i));
        }
        System.out.println("[0] Quitter");
        System.out.println("Entrez le thème de votre choix");
    }

    private static void selonTheme(Client cli, Scanner usr) {
        Requetes query = new Requetes(connexion);
        HashMap<Integer, String> themes = new HashMap<>();
        try {
            themes = query.afficheThemes();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        afficheSelonTheme(themes);
        boolean quitte = false;
        while (!quitte && usr.hasNext()) {
            String res = usr.next();
            switch (res) {
                case "0":
                    quitte = true;
                    afficheRechercheLivre();
                    break;
                case "1":
                case "2":
                case "3":
                case "4":
                case "5":
                case "6":
                case "7":
                case "9":
                case "10":
                    try {
                        System.out.println(query.rechercheTheme(Integer.parseInt(res)));
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }
                    break;
                default:
                    System.out.println("Veuillez entrer une séléction valide");
                    break;
            }
        }
    }

// private static void 
}

// SELECT LIVRE.*
