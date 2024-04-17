package bibliotheque.mvcold.view;

import bibliotheque.metier.Exemplaire;
import bibliotheque.mvc.observer.Observer;
import bibliotheque.mvcold.controller.ExemplaireController;

import java.util.List;

public abstract class AbstractViewExemplaire implements Observer {

    protected ExemplaireController exemplaireController;
    protected List<Exemplaire> le;

    public void setController(ExemplaireController exemplaireController) {
        this.exemplaireController = exemplaireController;
    }

    public abstract void menu();

    public abstract void affList(List le);

    public List<Exemplaire> getAll(){
        return le;
    }

    @Override
    public void update(List le) {
        this.le = le;
        affList(le);
    }
}