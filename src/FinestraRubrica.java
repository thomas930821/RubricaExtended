
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.awt.BorderLayout;
import java.io.IOException;

public class FinestraRubrica extends JFrame {

    private final Rubrica rubrica;
    private final ArchivioRubrica archivio;

    private JTable tabella;
    private DefaultTableModel modelloTabella;

    private JButton btnNuovo;
    private JButton btnModifica;
    private JButton btnElimina;

    public FinestraRubrica(Rubrica rubrica,
                          ArchivioRubrica archivio) {

        super("Rubrica Telefonica");

        this.rubrica = rubrica;
        this.archivio = archivio;

        setSize(700, 450);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        String[] colonne = {"Nome", "Cognome", "Telefono"};

        modelloTabella = new DefaultTableModel(colonne, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tabella = new JTable(modelloTabella);
        tabella.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        add(new JScrollPane(tabella), BorderLayout.CENTER);

        // Barra degli strumenti
        JToolBar toolBar = new JToolBar();
        toolBar.setFloatable(false);

        btnNuovo = new JButton("Nuovo");
        btnModifica = new JButton("Modifica");
        btnElimina = new JButton("Elimina");

        btnNuovo.addActionListener(e -> apriNuovo());
        btnModifica.addActionListener(e -> apriModifica());
        btnElimina.addActionListener(e -> eliminaSelezionata());

        toolBar.add(btnNuovo);
        toolBar.add(btnModifica);
        toolBar.add(btnElimina);

        add(toolBar, BorderLayout.NORTH);

        aggiornaTabella();
    }

    private void apriNuovo() {

        EditorPersona editor = new EditorPersona(
            this, null, this::salvaNuovaPersona
        );

        editor.setVisible(true);
    }

    private boolean salvaNuovaPersona(Persona persona) {

        if (!rubrica.aggiungiPersona(persona)) {
            mostraErrore(
                "Dati non validi oppure contatto già presente."
            );
            return false;
        }

        try {
            archivio.salvaRubrica(rubrica);
            aggiornaTabella();
            return true;

        } catch (IOException e) {
            rubrica.eliminaPersona(persona);

            mostraErrore(
                "Errore durante il salvataggio: " + e.getMessage()
            );
            return false;
        }
    }

    private void apriModifica() {

        Persona originale = getPersonaSelezionata();

        if (originale == null) {
            mostraErrore("Selezionare una persona da modificare.");
            return;
        }

        EditorPersona editor = new EditorPersona(
            this,
            originale,
            modificata -> salvaModifica(originale, modificata)
        );

        editor.setVisible(true);
    }

    private boolean salvaModifica(Persona originale,
                                 Persona modificata) {

        Persona precedente = copiaPersona(originale);

        if (!rubrica.modificaPersona(originale, modificata)) {
            mostraErrore(
                "Dati non validi oppure contatto duplicato."
            );
            return false;
        }

        try {
            archivio.salvaRubrica(rubrica);
            aggiornaTabella();
            return true;

        } catch (IOException e) {
            rubrica.modificaPersona(originale, precedente);

            mostraErrore(
                "Errore durante il salvataggio: " + e.getMessage()
            );
            return false;
        }
    }

    private void eliminaSelezionata() {

        Persona persona = getPersonaSelezionata();

        if (persona == null) {
            mostraErrore("Selezionare una persona da eliminare.");
            return;
        }

        int conferma = JOptionPane.showConfirmDialog(
            this,
            "Eliminare la persona " +
                persona.getNome() + " " +
                persona.getCognome() + "?",
            "Conferma eliminazione",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.QUESTION_MESSAGE
        );

        if (conferma != JOptionPane.YES_OPTION) {
            return;
        }

        if (!rubrica.eliminaPersona(persona)) {
            mostraErrore("Persona non presente nella rubrica.");
            return;
        }

        try {
            archivio.salvaRubrica(rubrica);
            aggiornaTabella();

        } catch (IOException e) {
            rubrica.aggiungiPersona(persona);

            mostraErrore(
                "Errore durante l'eliminazione: " +
                e.getMessage()
            );
        }
    }

    private Persona getPersonaSelezionata() {

        int riga = tabella.getSelectedRow();

        if (riga == -1) {
            return null;
        }

        int indice = tabella.convertRowIndexToModel(riga);

        return rubrica.getPersone().get(indice);
    }

    private Persona copiaPersona(Persona persona) {

        return new Persona(
            persona.getNome(),
            persona.getCognome(),
            persona.getEta(),
            persona.getNumero(),
            persona.getIndirizzo()
        );
    }

    private void mostraErrore(String messaggio) {

        JOptionPane.showMessageDialog(
            this,
            messaggio,
            "Errore",
            JOptionPane.ERROR_MESSAGE
        );
    }

    private void aggiornaTabella() {

        modelloTabella.setRowCount(0);

        for (Persona persona : rubrica.getPersone()) {
            modelloTabella.addRow(new Object[]{
                persona.getNome(),
                persona.getCognome(),
                persona.getNumero()
            });
        }
    }
}
