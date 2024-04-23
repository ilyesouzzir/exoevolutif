package bibliotheque.utilitaires;

import bibliotheque.metier.DVD;
import bibliotheque.metier.Ouvrage;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

public class DVDFactory extends OuvrageFactory{
    protected long code;
    protected LocalTime dureeTotale;
    protected byte nbreBonus;

    public Ouvrage create(){
        super.base();
        System.out.println("code : ");
        code= sc.nextLong();
        dureeTotale=Utilitaire.lecTime();
        nbreBonus= sc.nextByte();
        DVD dvd =new DVD(titre,ageMin,dateParution,prixLocation,langue,genre,code,dureeTotale,nbreBonus);
        System.out.println("autres langues");
        List<String> langues = Arrays.asList("anglais","français","italien","allemand","fin");
        int choix;
        do{
            choix=Utilitaire.choixListe(langues);
            if(choix==langues.size())break;
            String langueChoisie = langues.get(choix-1);
            if (!langueChoisie.equals(dvd.getLangue())) {
                dvd.getAutresLangues().add(langueChoisie);
            }
        }while(true);
        System.out.println("sous-titres");
        do{
            choix=Utilitaire.choixListe(langues);
            if(choix==langues.size())break;
            dvd.getSousTitres().add(langues.get(choix-1));
        }while(true);
        return dvd;
    }
}
