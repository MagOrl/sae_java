import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Executable {
    private static ListeCompte dataComptes;

    public static void main(String[] args) {
        dataComptes = new ListeCompte();
        boolean quitte = false;
        bvn();
        menuConnex();
        Scanner usr = new Scanner(System.in);
        while (!quitte) {
            String res = usr.nextLine();
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
        System.out.println("entrez la séction de votre choix");
    }

    private static void menuCreaCompte() {
        System.out.println("╭────────────────────────────────────────────────────────────────────────────────────╮");
        System.out.println("│ Pour créer le compte, veuillez entrer correctement les informations.               │");
        System.out.println("╰────────────────────────────────────────────────────────────────────────────────────╯");
        System.out.println("Entrez votre nom");
        Scanner usr = new Scanner(System.in);
        boolean fini = false;
        List<String> data = new ArrayList<>();// numCompte, String nom, String prenom, String identifiant, String
                                              // adresse, int tel, String email, String mdp
        while (!fini) {
            data.add(usr.nextLine());
            System.out.println("Votre nom : "+usr.nextLine());
            usr = new Scanner(System.in);
            data.add(usr.nextLine());

        }
    }

}
