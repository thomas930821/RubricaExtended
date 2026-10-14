
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Properties;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

public class App {

    public static void main(String[] args) {

        Properties credenziali = new Properties();

        try (InputStream input = Files.newInputStream(
                Path.of("credenziali.properties"))) {

            credenziali.load(input);

        } catch (IOException e) {
            JOptionPane.showMessageDialog(
                null,
                "Impossibile leggere le credenziali: " + e.getMessage(),
                "Errore",
                JOptionPane.ERROR_MESSAGE
            );
            return;
        }

        String username = credenziali.getProperty("username");
        String password = credenziali.getProperty("password");

        if (username == null || password == null ||
            username.isBlank() || password.isBlank()) {

            JOptionPane.showMessageDialog(
                null,
                "File credenziali.properties non valido.",
                "Errore",
                JOptionPane.ERROR_MESSAGE
            );
            return;
        }

        Utente utente = new Utente(username, password);

        SwingUtilities.invokeLater(() -> {

            FinestraLogin login = new FinestraLogin(
                (nomeUtente, passwordInserita) -> {

                    if (!utente.verificaCredenziali(
                            nomeUtente, passwordInserita)) {
                        return false;
                    }

                    ArchivioRubrica archivio =
                        new ArchivioRubrica("informazioni");

                    try {
                        Rubrica rubrica = archivio.caricaRubrica();

                        FinestraRubrica finestra =
                            new FinestraRubrica(rubrica, archivio);

                        finestra.setVisible(true);
                        return true;

                    } catch (IOException e) {
                        JOptionPane.showMessageDialog(
                            null,
                            "Errore nel caricamento della rubrica: "
                                + e.getMessage(),
                            "Errore",
                            JOptionPane.ERROR_MESSAGE
                        );
                        return false;
                    }
                }
            );

            login.setVisible(true);
        });
    }
}
