
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class ExecutableClient {

    private static ConnexionMySQL connexion;

    /**
     * fait lancer le TUI
     * 
     * @param args
     */
    public static void main(String[] args) {
        Scanner usr = new Scanner(System.in);
        try {
            principale(usr);
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * fonction principal du code, fait afficher le menu principale par lequel le
     * client va se connecter
     * 
     * @param usr l'utilisateur
     * @throws ClassNotFoundException
     */
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

    /**
     * permet d'afficher le logo LIVRE EXPRESS pour avoir du style
     */
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

    /**
     * fait afficher le menu pour se connecter
     */
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

    /**
     * le menu par lequel l'utilisateur va se connecter pour acceder à la
     * l'application
     * 
     * @param usr inputs de l'utilisateur
     */
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

    /**
     * menu fait pour que créée le compte utilisateur
     * 
     * @param usr
     */
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
                    demandeUtilisateur("code postal", usr),
                    demandeUtilisateur("adresse", usr), demandeUtilisateur("ville", usr),
                    demandeUtilisateur("email", usr),
                    demandeUtilisateur("numéro de téléphone", usr),
                    demandeUtilisateur("mot de passe", usr));
        } catch (NumberFormatException | SQLException e) {
            System.out.println(
                    "Queleque chose de mal c'est passé, ré-essayez en mettant des informations les plus cohérente possible");
            System.out.println(
                    "Par exemple ne mettez pas d'éspace en trop, et veillez à bien mettre exactement 5 character pour le code postale sans espace nulle part");
        } catch (QuitterExecption e) {
            bvn();
            afficheMenuConnex();
            return;
        }
        afficheMenuConnex();
    }

    /**
     * fonction qui permet d'automatiser les demande pour entrer les données
     * requises de l'utilisateur
     * 
     * @param demande l'intitule de la demande
     * @param usr     input utilisteur
     * @return input utilisateur
     * @throws QuitterExecption exception levée si l'utilisateur décide de quitter
     *                          pendant sa création de compte
     */
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

    /**
     * fonction qui permet d'automatiser les demande pour entrer les données
     * requises de l'utilisateur
     * 
     * @param demande l'intitule de la demande
     * @param usr     input utilisteur
     * @return input utilisateur
     * @throws QuitterExecption exception levée si l'utilisateur décide de quitter
     *                          pendant sa création de compte
     */
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

    /**
     * permet d'afficher l'acceuil du client
     * 
     * @param cli le client en question
     */
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

    /**
     * permet de gerer le choix du client pour pouvoir afficher le menu qu'il
     * choisit
     * 
     * @param cli le client en question
     * @param usr les inputs du client
     */
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

    /**
     * affiche le panier du client
     * 
     * @param cli le client
     */
    private static void affichePanier(Client cli) {
        cli.affichePanier();

    }

    /**
     * permet au client de gérer sont panier et de potentiellement finaliser un
     * achat
     * 
     * @param cli
     * @param usr
     */
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
                    }catch(NullPointerException e){
                        System.out.println("Vous avez mal entré les informations");
                    }
                     catch (IndexOutOfBoundsException ex) {
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

    /**
     * affiche le menu pour la gestion de compte
     */
    private static void afficheGestionCompte() {
        System.out.println("╭────────────────────────────────────────────────────────────────────────────────────╮");
        System.out.println("│ [1] Voir mes informations personelle                                               │");
        System.out.println("│                                                                                    │");
        System.out.println("│ [2] Changer mes informations personelle                                            │");
        System.out.println("│                                                                                    │");
        System.out.println("│ [0]Quitter                                                                         │");
        System.out.println("╰────────────────────────────────────────────────────────────────────────────────────╯");

    }

    /**
     * menu qui permet au client de voir ses inforamtions personel ainsi que
     * pouvoir les modfier
     * 
     * @param cli le client
     * @param usr les inputs du client
     */
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

    /**
     * fonction qui va afficher les informations du client
     * 
     * @param cli le client
     * @param usr ses inputs(ici il ne peut que quitter)
     */
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

    /**
     * affiche tout les élément que le client peut possiblement changer
     */
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

    /**
     * fonction qui va permettre à l'utilisateur de changer ses inforamtions
     * personelle
     * 
     * @param cli le client
     * @param usr
     */
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

    /**
     * permet d'afficher l'historique de commande
     * 
     * @param cli
     * @param usr
     */
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

    /**
     * permet d'afficher la consultation de catalogue
     */
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
    /**
     * redirige le client selon ce qu'il veux choisir comme consultation de catalogue
     * @param cli
     * @param usr
     */
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
                    afficheConsulteCatalogue();
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

    /**
     * permet de rediriger la selon
     * 
     * @param cli
     * @param usr
     * @param mag
     */
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

    /**
     * permet d'afficher le catalogue de livres
     * 
     * @param livres
     * @param ind
     * @param mag
     */
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

    /**
     * permet au client de consulter les livres de la base de données et de
     * potentielement commander un livre
     * 
     * @param livres
     * @param usr
     * @param cli
     * @param mag
     */
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