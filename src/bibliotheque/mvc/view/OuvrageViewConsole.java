package bibliotheque.mvc.view;

import bibliotheque.metier.Auteur;
import bibliotheque.metier.Exemplaire;
import bibliotheque.metier.Ouvrage;
import bibliotheque.metier.TypeOuvrage;
import bibliotheque.mvc.GestionMVC;
import bibliotheque.mvc.controller.ControllerSpecialOuvrage;
import bibliotheque.utilitaires.*;

import java.util.*;

import static bibliotheque.utilitaires.Utilitaire.*;


public class OuvrageViewConsole extends AbstractView<Ouvrage> {
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
        Ouvrage a = la.get(nl);
        boolean ok = controller.remove(a);
        if(ok) affMsg("ouvrage effacé");
        else affMsg("ouvrage non effacé");
    }

    private void affMsg(String msg) {
        System.out.println(msg);
    }


    public void rechercher() {
        // Ask the user for the type of ouvrage
        System.out.println("Veuillez entrer le type d'ouvrage :");
        String type = sc.nextLine();

        // Ask the user for the unique information related to the type of ouvrage
        System.out.println("Veuillez entrer l'information unique relative au type d'ouvrage :");
        String info = sc.nextLine();

        // Search for the ouvrage
        Ouvrage ouvrage = controller.search(type, info);

        // Print the ouvrage if found
        if (ouvrage != null) {
            System.out.println("Ouvrage trouvé : " + ouvrage);
        } else {
            System.out.println("Aucun ouvrage trouvé avec le type '" + type + "' et l'information '" + info + "'.");
        }
    }


    public void modifier() {
        int choix = choixElt(la);
        Ouvrage a = la.get(choix-1);
         do {
            try {
                double ploc =Double.parseDouble(modifyIfNotBlank("prix location",""+a.getPrixLocation()));
                a.setPrixLocation(ploc);
                break;
            } catch (Exception e) {
                System.out.println("erreur :" + e);
            }
        }while(true);
        controller.update(a);
   }

    public void ajouter() {
        TypeOuvrage[] tto = TypeOuvrage.values();
        List<TypeOuvrage> lto = new ArrayList<>(Arrays.asList(tto));
        int choix = Utilitaire.choixListe(lto);
        Ouvrage a = null;
        List<OuvrageFactory> lof = new ArrayList<>(Arrays.asList(new LivreFactory(),new CDFactory(),new DVDFactory()));
        a = lof.get(choix-1).create();

        // Récupérer tous les auteurs
        List<Auteur> auteurs = GestionMVC.av.getAll();

        // Trier les auteurs par nom et prénom
        auteurs.sort(Comparator.comparing(Auteur::getNom).thenComparing(Auteur::getPrenom));

        // Ne pas présenter les auteurs déjà enregistrés pour cet ouvrage
        auteurs.removeAll(a.getLauteurs());

        // Inviter l'utilisateur à sélectionner un ou plusieurs auteurs
        System.out.println("Choisissez un ou plusieurs auteurs:");
        List<Auteur> auteursChoisis = new ArrayList<>();
        do {
            int ch = choixListe(auteurs);
            auteursChoisis.add(auteurs.get(ch - 1));
            System.out.println("Voulez-vous ajouter un autre auteur ? (oui/non)");
            String reponse = sc.nextLine();
            if (!reponse.equalsIgnoreCase("oui")) {
                break;
            }
        } while (true);

        // Affecter les auteurs choisis à l'ouvrage
        a.setLauteurs(auteursChoisis);

        controller.add(a);
    }

    protected void special() {
        int choix =  choixElt(la);
        Ouvrage o = la.get(choix-1);

        List options = new ArrayList<>(Arrays.asList("lister exemplaires", "lister exemplaires en location", "lister exemplaires libres","fin"));
        do {
            int ch = choixListe(options);

            switch (ch) {

                case 1:
                    exemplaires(o);
                    break;
                case 2:
                    enLocation(o, true);
                    break;
                case 3:
                    enLocation(o, false);
                    break;

                case 4 :return;
            }
        } while (true);

    }

    public void enLocation(Ouvrage o, boolean enLocation) {
        List<Exemplaire> l= ((ControllerSpecialOuvrage) controller).listerExemplaire(o, enLocation);
        affList(l);
    }

    public void exemplaires(Ouvrage o) {
        List<Exemplaire> l= ((ControllerSpecialOuvrage)controller).listerExemplaire(o);
        affList(l);
    }
    @Override
    public void affList(List la) {
        affListe(la);
    }
}
