package bibliotheque.metier;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Mail {
    private String objet;
    private String message;
    private String dateEnvoi;

    public Mail(String objet, String message, String dateEnvoi) {
        this.objet = objet;
        this.message = message;
        this.dateEnvoi = dateEnvoi;
    }

    public String getObjet() {
        return objet;
    }

    public void setObjet(String objet) {
        this.objet = objet;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDateEnvoi() {
        return dateEnvoi;
    }

    public void setDateEnvoi(String dateEnvoi) {
        this.dateEnvoi = dateEnvoi;
    }
    public void envoi(String emailDestinataire) {
        String contenuEmail = "Objet: " + objet + "\n\n" + message;

        Path cheminFichier = Paths.get(emailDestinataire + ".txt");

        try {
            Files.write(cheminFichier, contenuEmail.getBytes());
        } catch (IOException e) {
            System.out.println("Une erreur s'est produite lors de l'envoi de l'e-mail: " + e.getMessage());
        }
    }
    @Override
    public String toString() {
        return "Mail{" +
                "objet='" + objet + '\'' +
                ", message='" + message + '\'' +
                ", dateEnvoi='" + dateEnvoi + '\'' +
                '}';
    }
}
