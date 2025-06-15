import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Executable {
    public static void main(String[] args) {
        menuConnex();
        Scanner usr = new Scanner(System.in);
        String res = "";
        while (usr.hasNextLine()) {
            res = usr.nextLine();
            switch (res) {
                case "0":
                    return;
                case "1":
                    ExecutableClient.main(args);
                    menuConnex();
                    break;
                case "2":
                    ExecutableVendeur.main(args);
                    menuConnex();
                    break;
                case "3":
                    ExecutableAdmin.main(args);
                    menuConnex();
                    break;
                case "4":
                    modifInfoServ(usr);
                    menuConnex();
                    break;
                default:
                    System.out.println("Mettre une valeur correcte");
                    break;
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
        System.out.println("│                                                                                    │");
        System.out.println("│                                                                                    │");
        System.out.println("│ [1] se connecter en Client                                                         │");
        System.out.println("│                                                                                    │");
        System.out.println("│ [2] se connecter en Vendeur                                                        │");
        System.out.println("│                                                                                    │");
        System.out.println("│ [3] se connecter en Administrateur                                                 │");
        System.out.println("│                                                                                    │");
        System.out.println("│ [4] Modifier les informations du serveur                                           │");
        System.out.println("│                                                                                    │");
        System.out.println("│ [0] Quitter                                                                        │");
        System.out.println("╰────────────────────────────────────────────────────────────────────────────────────╯");
    }

    private static void modifInfoServ(Scanner usr) {
        List<String> data = new ArrayList<>();
        try {
            File cache = new File("cachePourBaseDeDonne");
            Scanner reader = new Scanner(cache);
            while (reader.hasNextLine()) {
                data.add(reader.nextLine());
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        System.out.println("╭────────────────────────────────────────────────────────────────────────────────────╮");
        System.out.println("│ (entrez le numéro de la ligne de ce que vous voulez changez)");
        System.out.println("│ ensuite suivi de UN espace mettre par quoi vous voulez le changer ");
        System.out.println("│ex : 2 foobar");
        System.out.println("│ [1] Nom du serveur : " + data.get(0) + "                                           ");
        System.out.println("│ [2] Nom de la base de donnée  : " + data.get(1) + "                                ");
        System.out.println("│ [3] Nom de l'utilisateur: " + data.get(2) + "                                      ");
        System.out.println("│ [4] Le mot de passe  : " + data.get(3) + "                                         ");
        System.out.println("│ [5] enregistrez les modification                                                   ");
        System.out.println("│ [0] Quitter                                                                        ");
        System.out.println("╰────────────────────────────────────────────────────────────────────────────────────╯");
        while (usr.hasNext()) {
            String res = usr.nextLine();
            String[] resSplit = res.split(" ");
            switch (resSplit[0]) {
                case "1":
                case "2":
                case "3":
                case "4":
                    System.out.println(res);
                    System.out.println(resSplit[0]);
                    System.out.println(resSplit[1]);
                    data.set(Integer.parseInt(resSplit[0]) - 1, resSplit[1]);
                    System.out.println("c'est fait !");
                    break;
                case "5":
                    enregistre(data);
                    System.out.println("c'est fait !");
                    break;
                case "0":
                    return;
                default:
                    System.out.println("Entrez une valeur qui est entre crochet");
                    break;
            }
        }

    }

    public static void enregistre(List<String> data) {
        try {
            FileWriter fw = new FileWriter("cachePourBaseDeDonne", false);
            for (String d : data) {
                fw.write(d + "\n");
            }
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
