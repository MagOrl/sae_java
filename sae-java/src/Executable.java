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
            query.connectClient(demandeConnexion("identifiant", usr), demandeConnexion("mot de passe", usr));
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

    private static String demandeConnexion(String demande,Scanner usr) throws QuitterExecption{
        System.out.println("Entrez votre "+demande);
        String res = usr.nextLine();
        try{
            if (Integer.parseInt(res)==0) throw new QuitterExecption();
            else{
                System.out.println("Votre"+demande+" "+res);
                System.out.println(" ");
            }
        }catch(NumberFormatException e){
            System.out.println("Votre"+demande+" "+res);
            System.out.println(" ");
        }
        return res;
    }
}
