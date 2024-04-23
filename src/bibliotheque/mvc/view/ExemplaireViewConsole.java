package bibliotheque.mvc.view;

import bibliotheque.metier.*;
import bibliotheque.mvc.GestionMVC;
import bibliotheque.mvc.controller.ControllerSpecialExemplaire;

import java.util.*;


import static bibliotheque.utilitaires.Utilitaire.*;
import static bibliotheque.utilitaires.Utilitaire.affListe;

public class ExemplaireViewConsole extends AbstractView<Exemplaire> {

    Scanner sc = new Scanner(System.in);


    @Override
    public void menu() {
        update(controller.getAll());
        List options = Arrays.asList("ajouter", "retirer", "rechercher","modifier","fin");
        do {
            int ch = choixListe(options);

            switch (ch) {
                case 1:
                    ajouter();
                    break;
                case 2:
                    retirer();
                    break;
                case 3:
                    rechercher();
                    break;
                case 4:
                    modifier();
                    break;
                case 5:
                    return;
            }
        } while (true);
    }

    private void retirer() {
        int nl = choixElt(la)-1;
        Exemplaire a = la.get(nl);
        boolean ok = controller.remove(a);
        if(ok) affMsg("exemplaire effacé");
        else affMsg("exemplaire non effacé");
    }

    private void affMsg(String msg) {
        System.out.println(msg);
    }


    public void rechercher() {
        try {
            System.out.println("matricule ");
            String mat = sc.nextLine();
            Exemplaire rech = new Exemplaire(mat,"",null);
            Exemplaire a = controller.search(rech);
            if(a==null) affMsg("exemplaire inconnu");
            else {
                affMsg(a.toString());
                special(a);
            }
        }catch(Exception e){
            System.out.println("erreur : "+e);
        }

    }


    public void modifier() {
        int choix = choixElt(la);
        Exemplaire a = la.get(choix-1);
        do {
            try {
                String description = modifyIfNotBlank("nom", a.getDescriptionEtat());
                a.setDescriptionEtat(description);
                break;
            } catch (Exception e) {
                System.out.println("erreur :" + e);
            }
        }while(true);
        controller.update(a);
    }


    public void ajouter() {
        Exemplaire a;
        do {
            try {
                System.out.println("matricule ");
                String mat = sc.nextLine();
                System.out.println("description ");
                String descr = sc.nextLine();
                System.out.println("ouvrage : ");
                List<Ouvrage> lo = GestionMVC.ov.getAll();
                Collections.sort(lo, Comparator.comparing(Ouvrage::getTitre));
                int ch = choixListe(lo);
                a = new Exemplaire(mat, descr,lo.get(ch-1));
                System.out.println("rayon");
                List<Rayon> lr = GestionMVC.rv.getAll();
               lr.sort(new Comparator<Rayon>() {
                   @Override
                   public int compare(Rayon o1, Rayon o2) {
                       return o1.getCodeRayon().compareTo(o2.getCodeRayon());

                   }
               });
                ch= choixListe(lr);
                a.setRayon(lr.get(ch-1));
                break;
            } catch (Exception e) {
                System.out.println("une erreur est survenue : "+e.getMessage());
            }
        }while(true);
        controller.add(a);
    }

    public void special(Exemplaire a) {

        List options = Arrays.asList("modifier etat", "lecteur actuel", "envoi mail","en location","louer","rendre","fin");
        do {
            int ch = choixListe(options);

            switch (ch) {

                case 1:
                    modifierEtat(a);
                    break;
                case 2:
                    lecteurActuel(a);
                    break;
                case 3:
                    envoiMail(a);
                    break;
                case 4 :
                    enLocation(a);
                    break;
                case 5 :
                    louer(a);
                    break;
                case 6 :
                    rendre(a);
                    break;
                case 7: return;
            }
        } while (true);

    }

    private void rendre(Exemplaire a) {
        GestionMVC.LOCATIONS.remove(a);
   }

    private void louer(Exemplaire a) {
        // Récupérer tous les Lecteurs
        List<Lecteur> lecteurs = GestionMVC.lv.getAll();

        // Inviter l'utilisateur à sélectionner un Lecteur
        System.out.println("Choisissez un Lecteur:");
        int ch = choixListe(lecteurs);

        // Récupérer le Lecteur sélectionné
        Lecteur lecteurchoisi = lecteurs.get(ch - 1);

       GestionMVC.LOCATIONS.put(a, lecteurchoisi);
    }


    public void enLocation(Exemplaire ex) {
        boolean loc = ((ControllerSpecialExemplaire)controller).enLocation(ex);
        if(loc) System.out.println("en location");
        else System.out.println("pas en location");
    }


    public void envoiMail(Exemplaire ex) {
        Mail m = new Mail("demo","message de test","01-01-2024");
        ((ControllerSpecialExemplaire)controller).envoiMailLecteurActuel(ex,m);
    }


    public void lecteurActuel(Exemplaire ex) {
        ((ControllerSpecialExemplaire)controller).LecteurActuel(ex);
    }


    public void modifierEtat(Exemplaire ex) {
        System.out.println("nouvel état :");
        String etat = sc.nextLine();
        ((ControllerSpecialExemplaire)controller).modifierEtat(ex,etat) ;
    }

    @Override
    public void affList(List la) {
        affListe(la);
    }
}
