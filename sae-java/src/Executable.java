import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
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
            }

        }
    }

    private static void bvn() {
        System.out.println(
                "                      _    _               ___                        \n" + //
                        "                     | |  (_)_ ___ _ ___  | __|_ ___ __ _ _ ___ ______\n" + //
                        "                     | |__| \\ V / '_/ -_) | _|\\ \\ / '_ \\ '_/ -_|_-<_-<\n" + //
                        "                     |____|_|\\_/|_| \\___| |___/_\\_\\ .__/_| \\___/__/__/\n" + //
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
            if (Integer.parseInt(res) == 0)
                throw new QuitterExecption();
            else {
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
            if (Integer.parseInt(res) == 0)
                throw new QuitterExecption();
            else {
                System.out.println("Votre " + demande + " " + res);
                System.out.println(" ");
            }
        } catch (NumberFormatException e) {
            System.out.println("Votre " + demande + " " + res);
            System.out.println(" ");
        }
        return res;
    }

    private static void menuClient(Client cli, Scanner usr) {
        System.out.println("╭────────────────────────────────────────────────────────────────────────────────────╮");
        System.out.println("│ Bonjour " + cli.getIdentifiant()
                + "                                                                       │");
        System.out.println("│                                                                                    │");
        System.out.println("│ [1] Consulte les catalogues                                                        │");
        System.out.println("│                                                                                    │");
        System.out.println("│ [2] Gestion comptes                                                                │");
        System.out.println("│                                                                                    │");
        System.out.println("│ [3] Historique commande                                                            │");
        System.out.println("│                                                                                    │");
        System.out.println("│ [0] Quitter                                                                        │");
        System.out.println("╰────────────────────────────────────────────────────────────────────────────────────╯");
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
                    System.out.println("to be built");
                    break;
                case "2":
                    gestionCompte(cli, usr);
                    break;
                case "3":
                    System.out.println("to be built");
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

    private static void changeInfoPerso(Client cli, Scanner usr) {
        System.out.println("╭────────────────────────────────────────────────────────────────────────────────────╮");
        System.out.println("│ Que voulez vous changer ?                                                          │");
        System.out.println("│                                                                                    │");
        System.out.println("╰────────────────────────────────────────────────────────────────────────────────────╯");

    }
}
