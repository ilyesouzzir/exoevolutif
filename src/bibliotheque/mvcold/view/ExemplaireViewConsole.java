package bibliotheque.mvcold.view;

import bibliotheque.metier.Exemplaire;
import bibliotheque.mvcold.controller.ExemplaireController;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import static bibliotheque.utilitaires.Utilitaire.*;

public class ExemplaireViewConsole extends AbstractViewExemplaire {
    Scanner sc = new Scanner(System.in);

    @Override
    public void menu() {
        update(exemplaireController.getAll());
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
        int nl = choixElt(le)-1;
        Exemplaire e = le.get(nl);
        boolean ok = exemplaireController.remove(e);
        if(ok) affMsg("exemplaire effacé");
        else affMsg("exemplaire non effacé");
    }

    private void affMsg(String msg) {
        System.out.println(msg);
    }

    public void rechercher() {
        try {
            System.out.println("id ");
            int id = sc.nextInt();
            Exemplaire rech = new Exemplaire(id);
            Exemplaire e = exemplaireController.search(rech);
            if(e==null) affMsg("exemplaire inconnu");
            else {
                affMsg(e.toString());
                special(e);
            }
        }catch(Exception e){
            System.out.println("erreur : "+e);
        }
    }

    public void modifier() {
        int choix = choixElt(le);
        Exemplaire e = le.get(choix-1);
        do {
            try {
                int matricule = modifyIfNotBlank("matricule", e.getMatricule());
                e.setMatricule(matricule);
                break;
            } catch (Exception e) {
                System.out.println("erreur :" + e);
            }
        }while(true);
        exemplaireController.update(e);
    }

    public void ajouter() {
        Exemplaire e;
        do {
            try {
                System.out.println("id ");
                int id = sc.nextInt();
                e = new Exemplaire(id);
                break;
            } catch (Exception e) {
                System.out.println("une erreur est survenue : "+e.getMessage());
            }
        }while(true);
        exemplaireController.add(e);
    }

    public void special(Exemplaire e) {
        // Add any special methods for Exemplaire here
    }

    @Override
    public void affList(List le) {
        affListe(le);
    }
}