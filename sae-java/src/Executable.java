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
                    System.out.println("to be built");
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

}

