import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Executable {
    private static ListeCompte dataComptes;

    public static void main(String[] args) {
        ConnexionMySQL connexion = new ConnexionMySQL();
        
        dataComptes = new ListeCompte();
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
            System.out.println("Votre nom : " + data.get(0));
            // usr.close();

            usr = new Scanner(System.in);
            System.out.println("Entrez votre prénom");
            data.add(usr.nextLine());
            System.out.println("Votre prénom: " + data.get(1));
            // usr.close();

            usr = new Scanner(System.in);
            System.out.println("Entrez votre identifiant");
            data.add(usr.nextLine());
            System.out.println("Votre identifiant: " + data.get(2));
            // usr.close();

            usr = new Scanner(System.in);
            System.out.println("Entrez votre adresse :");
            data.add(usr.nextLine());
            System.out.println("Votre adresse: " + data.get(3));
            // usr.close();

            usr = new Scanner(System.in);
            System.out.println("Entrez votre numéro de téléphone");
            data.add(usr.nextLine());
            System.out.println("Votre prénom: " + data.get(4));
            // usr.close();

            usr = new Scanner(System.in);
            System.out.println("Entrez votre email");
            data.add(usr.nextLine());
            System.out.println("Votre email: " + data.get(5));
            // usr.close();

            usr = new Scanner(System.in);
            System.out.println("Entrez votre mot de passe");
            data.add(usr.nextLine());
            data.add((dataComptes.getNbDeClient()) + "");
            fini = true;
        }
        dataComptes.ajtePers(new Client(Integer.valueOf(data.get(7)), data.get(0), data.get(1), data.get(2), data.get(3),Integer.valueOf(data.get(7)), data.get(5), data.get(6)));
        menuConnex();
    }

}
