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
                    menuCreaCompte();

                    break;
                case "2":
                    menuCreaCompte();
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

    private static void menuCreaCompte() {
        Requetes query = new Requetes(connexion);
        System.out.println("╭────────────────────────────────────────────────────────────────────────────────────╮");
        System.out.println("│ Pour créer le compte, veuillez entrer correctement les informations.               │");
        System.out.println("╰────────────────────────────────────────────────────────────────────────────────────╯");
        boolean fini = false;

        while (!fini) {
            try {
                query.creeClient(demandeUtilisateur("indentifiant"), demandeUtilisateur("nom"),
                        demandeUtilisateur("prenom"),
                        demandeUtilisateur("code postale"),
                        demandeUtilisateur("adresse"), demandeUtilisateur("ville"), demandeUtilisateur("email"),
                        demandeUtilisateur("numéro de téléphone"),
                        demandeUtilisateur("mot de passe"));
            } catch (NumberFormatException | SQLException e) {
                e.printStackTrace();
            }
            fini = true;
        }
        menuConnex();
    }

    public static String demandeUtilisateur(String demande) {
        Scanner usr = new Scanner(System.in);
        System.out.println("Entrez votre " + demande);
        System.out.println("Votre " + demande + " " + usr.nextLine());
        System.out.println("Entrez pour continuer");
        return usr.nextLine();
    }

}
