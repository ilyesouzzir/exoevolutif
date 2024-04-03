package bibliotheque.metier;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Exemplaire {

    private String matricule;
    private String descriptionEtat;
    private Ouvrage ouvrage;
    private Rayon rayon;
    private String etat;

    // La HashMap pour stocker les locations
    public static final Map<Exemplaire, Lecteur> locations = new HashMap<>();

    public Exemplaire(String matricule, String descriptionEtat, Ouvrage ouvrage) {
        this.matricule = matricule;
        this.descriptionEtat = descriptionEtat;
        this.ouvrage = ouvrage;
        this.ouvrage.getLex().add(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Exemplaire that = (Exemplaire) o;
        return Objects.equals(matricule, that.matricule);
    }

    @Override
    public int hashCode() {
        return Objects.hash(matricule);
    }

    public String getMatricule() {
        return matricule;
    }

    public void setMatricule(String matricule) {
        this.matricule = matricule;
    }

    public String getDescriptionEtat() {
        return descriptionEtat;
    }

    public void setDescriptionEtat(String descriptionEtat) {
        this.descriptionEtat = descriptionEtat;
    }

    public Ouvrage getOuvrage() {
        return ouvrage;
    }

    public void setOuvrage(Ouvrage ouvrage) {
        if (this.ouvrage != null) this.ouvrage.getLex().remove(this);
        this.ouvrage = ouvrage;
        this.ouvrage.getLex().add(this);
    }

    public Rayon getRayon() {
        return rayon;
    }

    public void setRayon(Rayon rayon) {
        if (this.rayon != null) this.rayon.getLex().remove(this);
        this.rayon = rayon;
        this.rayon.getLex().add(this);
    }

    @Override
    public String toString() {
        return "Exemplaire{" +
                "matricule='" + matricule + '\'' +
                ", descriptionEtat='" + descriptionEtat + '\'' +
                ", ouvrage=" + ouvrage +
                ", rayon=" + rayon +
                '}';
    }

    public void modifierEtat(String etat) {
        setDescriptionEtat(etat);
    }

    // Méthode pour obtenir le lecteur actuel de cet exemplaire
    public Lecteur lecteurActuel() {
        return locations.get(this);
    }

    // Méthode pour vérifier si cet exemplaire est en location
    public boolean enLocation() {
        return locations.containsKey(this);
    }

    public void envoiMailLecteurActuel(Mail mail) {
        Lecteur lecteur = lecteurActuel();
        if (lecteur != null) {
            System.out.println("Envoi de " + mail + " à " + lecteur.getMail());
        } else {
            System.out.println("Aucune location en cours");
        }
    }

    public void envoiMailLecteurs(Mail mail) {

    }
}

