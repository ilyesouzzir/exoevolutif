package bibliotheque.metier;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Lecteur {
    private int numlecteur;
    private String nom, prenom;
    private LocalDate dn;
    private String adresse;
    private String mail;
    private String tel;

    // Utilisation de HashMap pour stocker les locations avec l'exemplaire comme clé et le lecteur-loueur comme valeur
    public static final Map<Exemplaire, Lecteur> locations = new HashMap<>();

    public Lecteur(int numlecteur, String nom, String prenom, LocalDate dn, String adresse, String mail, String tel) {
        this.numlecteur = numlecteur;
        this.nom = nom;
        this.prenom = prenom;
        this.dn = dn;
        this.adresse = adresse;
        this.mail = mail;
        this.tel = tel;
    }

    public int getNumlecteur() {
        return numlecteur;
    }

    public void setNumlecteur(int numlecteur) {
        this.numlecteur = numlecteur;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public LocalDate getDn() {
        return dn;
    }

    public void setDn(LocalDate dn) {
        this.dn = dn;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    @Override
    public String toString() {
        return "Lecteur{" +
                "numlecteur=" + numlecteur +
                ", nom='" + nom + '\'' +
                ", prenom='" + prenom + '\'' +
                ", dn=" + dn +
                ", adresse='" + adresse + '\'' +
                ", mail='" + mail + '\'' +
                ", tel='" + tel + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Lecteur lecteur = (Lecteur) o;
        return numlecteur == lecteur.numlecteur;
    }

    @Override
    public int hashCode() {
        return Objects.hash(numlecteur);
    }
    // Méthode pour louer un exemplaire
    public void louerExemplaire(Exemplaire exemplaire) {
        if (!Exemplaire.locations.containsKey(exemplaire)) {
            Exemplaire.locations.put(exemplaire, this);
            System.out.println("Exemplaire loué avec succès.");
        } else {
            System.out.println("Cet exemplaire est déjà loué.");
        }
    }

    // Méthode pour restituer un exemplaire
    public void restituerExemplaire(Exemplaire exemplaire) {
        if (Exemplaire.locations.containsKey(exemplaire)) {
            Exemplaire.locations.remove(exemplaire);
            System.out.println("Exemplaire restitué avec succès.");
        } else {
            System.out.println("Cet exemplaire n'est pas actuellement loué.");
        }
    }
}

