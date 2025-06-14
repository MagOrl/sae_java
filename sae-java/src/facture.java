import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class facture {
    //public void faireFactures(String requete, int mois, int annee) throws SQLException {
    //    List<Integer> params = Arrays.asList(mois, annee);
    //    this.st.createStatement();
    //    ResultSet rs = this.st.executeQuery("select nommag as Magasin, nomcli as Nom, prenomcli as Prenom, adressecli as adresse, codepostal as CodePostal, villecli as Ville, numcom, datecom as date, numlig, isbn, titre, qte, prixvente from CLIENT natural join COMMANDE natural join DETAILCOMMANDE natural join LIVRE natural join MAGASIN where MONTH(datecom) = " + mois + " and YEAR(datecom) = " + annee + " order by idmag, numcom, numlig");
    //    List<String> magasins = new ArrayList<>(List.of("La librairie parisienne", "Cap au Sud", "Ty Li-Breizh-rie",
    //            "L'européenne", "Le Ch'ti livre", "Rhône à lire", "Loire et livres"));
//
    //    String magasinPrec = null;
    //    Set<String> clientPrec = new HashSet<>();
    //    int totalCommande = 0, totalQteMagasin = 0, totalPrixGlobal = 0, totalQteGlobal = 0, nbFactureMag = 0;
    //    boolean premierTour = true, writeMag = true;
//
    //    System.out.println("Factures du " + mois + "/" + annee);
//
    //    while (rs.next()) {
    //        String magasin = rs.getString(1);
    //        String nom = rs.getString(2);
    //        String prenom = rs.getString(3);
    //        String adresse = rs.getString(4);
    //        String cp = rs.getString(5);
    //        String ville = rs.getString(6);
    //        int numCom = rs.getInt(7);
    //        Date dateCom = rs.getDate(8);
    //        String isbn = rs.getString(9);
    //        String titre = rs.getString(10);
    //        String auteur = rs.getString(11);
    //        int qte = rs.getInt(12);
    //        float prix = rs.getFloat(13);
//
    //        if (magasin.equals(magasinPrec)) {
    //            writeMag = false;
    //        }
//
    //        if (!clientPrec.equals(Set.of(nom, prenom)) && !writeMag) {
    //            totalPrixGlobal += totalCommande;
    //            System.out.printf("                                                                                     --------\n");
    //            System.out.printf("                                                                               Total    %d\n", totalCommande);
    //            totalCommande = 0;
    //            nbFactureMag++;
    //        }
//
    //        if (!magasin.equals(magasinPrec) && !premierTour) {
    //            System.out.println("---------------------------------------------------------------------------------------------");
    //            System.out.println(nbFactureMag + " factures éditées");
    //            System.out.println(totalQteMagasin + " livres vendus");
    //            System.out.println("*********************************************************************************************\n");
    //            totalQteGlobal += totalQteMagasin;
    //            nbFactureMag = 0;
    //        }
//
    //        if (magasins.contains(magasin)) {
    //            if (premierTour || !magasin.equals(magasinPrec)) {
    //                System.out.println("Edition des factures du magasin " + magasin);
    //                magasins.remove(magasin);
    //                totalQteMagasin = 0;
    //                writeMag = true;
    //            }
    //        }
//
    //        if (!clientPrec.equals(Set.of(nom, prenom)) || clientPrec.isEmpty()) {
    //            System.out.println("---------------------------------------------------------------------------------------------");
    //            System.out.println(nom + " " + prenom);
    //            System.out.println(adresse);
    //            System.out.println(cp + " " + ville);
    //            System.out.println("                                        commande n°" + numCom + " du " + dateCom);
    //            System.out.println("                    ISBN                           Titre                     qte  prix  total");
    //        }
//
    //        System.out.printf("              %-13s %-25s %-15s %4d  %.2f %.2f\n", isbn, titre, auteur, qte, prix, qte * prix);
    //        totalCommande += qte * prix;
    //        totalQteMagasin += qte;
//
    //        clientPrec = Set.of(nom, prenom);
    //        magasinPrec = magasin;
    //        premierTour = false;
    //    }
//
    //    totalPrixGlobal += totalCommande;
    //    System.out.printf("                                                                                     --------\n");
    //    System.out.printf("                                                                               Total    %d\n", totalCommande);
    //    nbFactureMag++;
    //    System.out.println("---------------------------------------------------------------------------------------------");
    //    System.out.println(nbFactureMag + " factures éditées");
    //    System.out.println(totalQteMagasin + " livres vendus");
    //    System.out.println("*********************************************************************************************");
    //    totalQteGlobal += totalQteMagasin;
    //    System.out.println("Chiffre d'affaire global: " + totalPrixGlobal);
    //    System.out.println("Nombre de livres vendus: " + totalQteGlobal);
//
    //    rs.close();
    //}   
}//
//