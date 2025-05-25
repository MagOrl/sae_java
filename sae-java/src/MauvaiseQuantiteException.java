public class MauvaiseQuantiteException extends Exception {
    private int fausseQte;
    private Livre livre;
    MauvaiseQuantiteException(int qte,Livre liv){
        this.fausseQte = qte;
        this.livre = liv;
    }
    public int getQte(){
        return this.fausseQte;
    }
    public void debug(){
        if (fausseQte<=0) {
            System.out.println("Vous ne pouvez pas mettre une quantité inférieure ou égale à 0");
        }else
            System.out.println("Le livre «"+livre.getTitre()+"» n'a que "+livre.getQte()+"exemplaire or vous avez mit une quantité de "+fausseQte);
    }
}
