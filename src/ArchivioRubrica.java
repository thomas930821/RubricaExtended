
import java.io.IOException;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Stream;

public class ArchivioRubrica {

    private final Path cartella;

    public ArchivioRubrica(String percorso) {
        this.cartella = Path.of(percorso).toAbsolutePath();
    }

    public Rubrica caricaRubrica() throws IOException {

        Rubrica rubrica = new Rubrica();

        Files.createDirectories(cartella);

        try (Stream<Path> files = Files.list(cartella)) {

            List<Path> elenco = files
                .filter(Files::isRegularFile)
                .filter(p -> p.getFileName().toString()
                    .matches("Persona[0-9]+\\.txt"))
                .sorted(Comparator.comparing(
                    p -> p.getFileName().toString()))
                .toList();

            for (Path file : elenco) {

                try (Scanner scanner = new Scanner(
                        file, StandardCharsets.UTF_8)) {

                    if (!scanner.hasNextLine()) {
                        System.err.println("File vuoto: " + file);
                        continue;
                    }

                    String riga = scanner.nextLine();
                    String[] dati = riga.split(";", -1);

                    if (dati.length != 5) {
                        System.err.println("Dati non validi: " + file);
                        continue;
                    }

                    try {
                        Persona persona = new Persona(
                            dati[0],
                            dati[1],
                            Integer.parseInt(dati[4]),
                            dati[3],
                            dati[2]
                        );

                        EsitoInserimento esito =
                            rubrica.aggiungiPersona(persona);

                        switch (esito) {

                            case DATI_NON_VALIDI:
                                System.err.println(
                                    "Contatto non valido: " + file
                                );
                                break;

                            case PERSONA_GIA_PRESENTE:
                                System.err.println(
                                    "Contatto duplicato: " + file
                                );
                                break;

                            case SUCCESSO:
                                break;
                        }

                    } catch (NumberFormatException e) {
                        System.err.println("Età non valida: " + file);
                    }
                }
            }
        }

        return rubrica;
    }

    public void salvaRubrica(Rubrica rubrica) throws IOException {

        Path destinazione = cartella;
        Path padre = destinazione.getParent();

        Files.createDirectories(padre);

        Path temporanea = Files.createTempDirectory(
            padre, "rubrica-temp-"
        );

        Path backup = padre.resolve(
            "rubrica-backup-" + java.util.UUID.randomUUID()
        );

        boolean backupCreato = false;

        try {
            int numero = 1;

            for (Persona persona : rubrica.getPersone()) {

                Path filePersona = temporanea.resolve(
                    "Persona" + numero + ".txt"
                );

                try (PrintStream ps = new PrintStream(
                        Files.newOutputStream(filePersona),
                        false,
                        StandardCharsets.UTF_8)) {

                    ps.println(
                        persona.getNome() + ";" +
                        persona.getCognome() + ";" +
                        persona.getIndirizzo() + ";" +
                        persona.getNumero() + ";" +
                        persona.getEta()
                    );

                    if (ps.checkError()) {
                        throw new IOException(
                            "Errore di scrittura: " + filePersona
                        );
                    }
                }

                numero++;
            }

            if (Files.exists(destinazione)) {
                Files.move(destinazione, backup);
                backupCreato = true;
            }

            try {
                Files.move(temporanea, destinazione);

            } catch (IOException e) {

                if (backupCreato) {
                    try {
                        Files.move(backup, destinazione);
                        backupCreato = false;
                    } catch (IOException ripristino) {
                        e.addSuppressed(ripristino);
                    }
                }

                throw e;
            }

            if (backupCreato) {
                eliminaCartella(backup);
            }

        } finally {
            if (Files.exists(temporanea)) {
                eliminaCartella(temporanea);
            }
        }
    }

    private void eliminaCartella(Path percorso) throws IOException {

        try (Stream<Path> files = Files.walk(percorso)) {

            List<Path> percorsi = files
                .sorted(Comparator.reverseOrder())
                .toList();

            for (Path file : percorsi) {
                Files.delete(file);
            }
        }
    }
}
