import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;

public class ListeCompte {
    private List<List<Personne>> listComptes;

    public ListeCompte() {
        listComptes = Arrays.asList(new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
        // LES INDICES, 0 ==> Client, 1 ==> Vendeur, 2 ==> Administrateur
        // ex : si vous voulez prendre les listes d'administrateur faites
        // listComptes.get(2)
    }
    public int ind(Personne prs){
        if(prs instanceof Client) return 0;
        if(prs instanceof Vendeur) return 1;
        if(prs instanceof Administrateur) return 2;
        return -1;
    }
    public void ajtePers(Personne prs) {
        listComptes.get(ind(prs)).add(prs);
    }
    public boolean compteExiste(Personne prs){
        return listComptes.get(ind(prs)).contains(prs);
    } 
}
