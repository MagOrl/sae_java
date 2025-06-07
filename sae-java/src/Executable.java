
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class Executable {

    private static ConnexionMySQL connexion;

    public static void main(String[] args) {
        Scanner usr = new Scanner(System.in);
        try {
            principale(usr);
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        }
    }

    private static void principale(Scanner usr) throws ClassNotFoundException {
        connexion = new ConnexionMySQL();
        bvn();
        afficheMenuConnex();
        while (usr.hasNextLine()) {
            String res = usr.nextLine();
            switch (res) {
                case "0":
                    return;
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

    private static void afficheMenuConnex() {
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
            afficheMenuConnex();
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
            System.out.print(
                    "Queleque chose de mal c'est passer, ré-essayer en mettant des informations les plus cohérente possible");
            System.out.print(
                    "Par exemple ne mettez pas d'éspace en trop, et veillez à bien mettre exactement 5 character pour le code postale sans espace nulle part");
        } catch (QuitterExecption e) {
            bvn();
            afficheMenuConnex();
            return;
        }
        afficheMenuConnex();
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
        System.out.println("│ [4] Mon panier                                                                     │");
        System.out.println("│                                                                                    │");
        System.out.println("│ [0] Déconnecter                                                                    │");
        System.out.println("╰────────────────────────────────────────────────────────────────────────────────────╯");
    }

    private static void menuClient(Client cli, Scanner usr) {
        afficheMenuClient(cli);
        while (usr.hasNext()) {
            String res = usr.nextLine();
            switch (res) {
                case "0":
                    bvn();
                    afficheMenuConnex();
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
                case "4":
                    panier(cli, usr);
                    afficheMenuClient(cli);
                    break;
                default:
                    System.out.println("Mettre une séléction valide");
            }
        }
    }

    private static void affichePanier(Client cli) {
        cli.affichePanier();

    }

    private static void panier(Client cli, Scanner usr) {
        Requetes query = new Requetes(connexion);
        affichePanier(cli);
        while (usr.hasNext()) {
            String res = usr.nextLine();
            String[] splitres = res.split(" ");
            switch (splitres[0]) {
                case "0":
                    return;
                case "1":
                case "2":
                case "3":
                case "4":
                case "5":
                case "6":
                case "7":
                    try {
                        cli.suppPanier(Integer.parseInt(splitres[0]),
                                cli.getPanier(Integer.parseInt(splitres[0])).get(Integer.parseInt(splitres[1]) - 1));
                        affichePanier(cli);
                    } catch (IndexOutOfBoundsException ex) {
                        System.out.println("vous avez mit une mauvaise saisis");
                    }
                    break;
                case "COMMANDE":
                    System.out.print("Voulez vous une réserver au [M]agasin ou directement [C]ommander ");
                    boolean fini = false;
                    while (!fini) {
                        String res2 = usr.nextLine();
                        switch (res2.toUpperCase()) {
                            case "M":
                            case "C":
                                try {
                                    query.commandeLivre(cli.getPanier(), cli, res2);
                                    System.out.println("Commande effectuer !");
                                    fini = true;
                                } catch (SQLException e) {
                                    e.printStackTrace();
                                    fini = true;
                                }
                                break;
                            case "0":
                                break;
                            default:
                                System.out.println("M pour magasin et L pour Ligne et 0 pour annulez ");
                                break;
                        }
                    }
                    break;
                default:
                    System.out.println("Mettre une séléction valide");
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
                    return;
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
        System.out.println("│ [1] Rechercher selon thème                                                         │");
        System.out.println("│                                                                                    │");
        System.out.println("│ [2] Le catalogue qu'on vous recommande                                             │");
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
                    choisiMagasinTheme(cli, usr);
                    afficheConsulteCatalogue();
                    break;
                case "2":
                    choisiMagasinOnVousRecommande(cli, usr);
                    break;
                default:
                    System.out.println("Mettre une valeur entre 0 et 2");
                    break;
            }
        }
    }

    private static void afficheChoisisMagasin(HashMap<Integer, Magasin> lesmag) {
        int y = 0;
        for (Integer i : lesmag.keySet()) {
            System.out
                    .println("[" + ++y + "] Ville : " + lesmag.get(i).getVille() + "|| Nom du magasin : "
                            + lesmag.get(i).getNom());
        }
        System.out.println("Entrez d'abord le magasin ou vous voulez commander");
        System.out.println("[0] Quitter");

    }

    private static void choisiMagasinTheme(Client cli, Scanner usr) {
        Requetes query = new Requetes(connexion);
        HashMap<Integer, Magasin> lesmag = new HashMap<>();
        try {
            lesmag = query.afficheMagasin();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        afficheChoisisMagasin(lesmag);
        while (usr.hasNext()) {
            String res = usr.next();
            switch (res) {
                case "0":
                    return;
                case "1":
                case "2":
                case "3":
                case "4":
                case "5":
                case "6":
                case "7":
                    selonTheme(cli, usr, lesmag.get(Integer.parseInt(res) - 1));
                    afficheChoisisMagasin(lesmag);
                    break;
                default:
                    System.out.println("Veuillez entrer une séléction valide");
                    break;
            }
        }
    }

    private static void choisiMagasinOnVousRecommande(Client cli, Scanner usr) {
        Requetes query = new Requetes(connexion);
        HashMap<Integer, Magasin> lesmag = new HashMap<>();
        try {
            lesmag = query.afficheMagasin();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        afficheChoisisMagasin(lesmag);
        while (usr.hasNext()) {
            String res = usr.next();
            switch (res) {
                case "0":
                    return;
                case "1":
                case "2":
                case "3":
                case "4":
                case "5":
                case "6":
                case "7":
                    try {
                        catalogue(query.onVousRecommande(cli, lesmag.get(Integer.parseInt(res) - 1)), usr, cli,
                                lesmag.get(Integer.parseInt(res) - 1));
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    afficheChoisisMagasin(lesmag);
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
        System.out.println("Entrez le thème de votre choix");
        System.out.println("[10] Quitter");
    }

    private static void selonTheme(Client cli, Scanner usr, Magasin mag) {
        Requetes query = new Requetes(connexion);
        HashMap<Integer, String> themes = new HashMap<>();
        try {
            themes = query.afficheThemes();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        afficheSelonTheme(themes);
        while (usr.hasNext()) {
            String res = usr.next();
            switch (res) {
                case "10":
                    return;
                case "0":
                case "1":
                case "2":
                case "3":
                case "4":
                case "5":
                case "6":
                case "7":
                case "8":
                case "9":
                    try {
                        catalogue(query.rechercheTheme(Integer.parseInt(res), mag), usr, cli, mag);
                        afficheSelonTheme(themes);
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }
                    break;
                default:
                    System.out.println("Veuillez entrer une séléction valide, si vous voulez quittez faites 10");
                    break;
            }
        }
    }

    private static void afficheCatalogue(List<List<Livre>> livres, int ind, Magasin mag) {
        String column1Format = "%-50.50s";
        String column2Format = "%-8.8s";
        String column3Format = "%8.8s";
        String formatInfo = column1Format + " " + column2Format + " " + column3Format;
        System.out.println("────────────────────────────────────────────────────────────────────────────────────");
        System.out.println("Magasin de chez " + mag.getNom());
        System.out.println("────────────────────────────────────────────────────────────────────────────────────");
        int pageI = ind + 1;
        System.out.println("Page : " + pageI + "/" + livres.size());
        System.out.println("────────────────────────────────────────────────────────────────────────────────────");
        for (int i = 0; i < livres.get(ind).size(); ++i) {
            System.out.format(formatInfo, "[" + i + "] Titre : " + livres.get(ind).get(i).getTitre(),
                    "qte : " + livres.get(ind).get(i).getQte(), livres.get(ind).get(i).getPrix() + " €");
            System.out.println();
        }
        System.out.println("────────────────────────────────────────────────────────────────────────────────────");
        if (ind < livres.size() - 1) {
            System.out.println("[12] Suivant");
        }
        if (ind > 0) {
            System.out.println("[11] Précédent");
        }
        System.out.println("[10] Quitter");
        System.out.println("[0->9] Commander le livre de votre choix et préciser la quantité avec un espace");
    }

    private static void catalogue(List<List<Livre>> livres, Scanner usr, Client cli, Magasin mag) {
        if (livres.isEmpty()) {
            System.out.println("Aucun livres de cette catégorie dans le magasin " + mag.getNom());
            return;
        }
        int ind = 0;
        afficheCatalogue(livres, ind, mag);
        while (usr.hasNext()) {
            String res = usr.nextLine();
            String[] splitRes = res.split(" "); // 0 : ind livre 1: qte
            switch (splitRes[0]) {
                case "10":
                    return;
                case "11":
                    try {
                        afficheCatalogue(livres, --ind, mag);
                    } catch (IndexOutOfBoundsException ex) {
                        afficheCatalogue(livres, ++ind, mag);
                        System.out.println("vous ne pouvez pas passez au précédent");
                    }
                    break;
                case "12":
                    try {
                        afficheCatalogue(livres, ++ind, mag);
                    } catch (IndexOutOfBoundsException ex) {
                        afficheCatalogue(livres, --ind, mag);
                        System.out.println("vous ne pouvez pas passez au suivant");
                    }
                    break;
                case "0":
                case "1":
                case "2":
                case "3":
                case "4":
                case "5":
                case "6":
                case "7":
                case "8":
                case "9":
                    try {
                        Livre liv = livres.get(ind).get(Integer.parseInt(splitRes[0]));
                        cli.addPanier(mag.getId(), liv, Integer.parseInt(splitRes[1]));
                        System.out.println("ajout au panier effectuer !");
                    } catch (TopDeLivreException ex) {
                        System.out.println("Vous avez mit trop de livre dans votre panier, alleger le.");
                    } catch (MauvaiseQuantiteException ex) {
                        ex.debug();
                    } catch (ArrayIndexOutOfBoundsException ex) {
                        System.out.println("Veuillez mettre une quantité.");
                    }
                    break;

                default:
                    System.out.println("Mettre une séléction valide");
                    break;
            }
        }
    }
}