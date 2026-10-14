
import javax.swing.*;
import java.awt.*;
import java.util.function.Predicate;

public class EditorPersona extends JDialog {

    private JTextField campoNome;
    private JTextField campoCognome;
    private JTextField campoIndirizzo;
    private JTextField campoNumero;
    private JTextField campoEta;

    private JButton btnSalva;
    private JButton btnAnnulla;

    private final Predicate<Persona> azioneSalvataggio;

    public EditorPersona(JFrame proprietario,
                         Persona persona,
                         Predicate<Persona> azioneSalvataggio) {

        super(proprietario, "Editor Persona", true);

        this.azioneSalvataggio = azioneSalvataggio;

        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setSize(400, 300);
        setLocationRelativeTo(proprietario);
        setLayout(new BorderLayout());

        // Barra degli strumenti
        JToolBar toolBar = new JToolBar();
        toolBar.setFloatable(false);

        btnSalva = new JButton("Salva");
        btnAnnulla = new JButton("Annulla");

        btnSalva.addActionListener(e -> salva());
        btnAnnulla.addActionListener(e -> dispose());

        toolBar.add(btnSalva);
        toolBar.add(btnAnnulla);

        add(toolBar, BorderLayout.NORTH);

        // Campi della persona
        JPanel pannelloCampi = new JPanel(
            new GridLayout(5, 2, 10, 10)
        );

        campoNome = new JTextField();
        campoCognome = new JTextField();
        campoIndirizzo = new JTextField();
        campoNumero = new JTextField();
        campoEta = new JTextField();

        pannelloCampi.add(new JLabel("Nome:"));
        pannelloCampi.add(campoNome);

        pannelloCampi.add(new JLabel("Cognome:"));
        pannelloCampi.add(campoCognome);

        pannelloCampi.add(new JLabel("Indirizzo:"));
        pannelloCampi.add(campoIndirizzo);

        pannelloCampi.add(new JLabel("Telefono:"));
        pannelloCampi.add(campoNumero);

        pannelloCampi.add(new JLabel("Età:"));
        pannelloCampi.add(campoEta);

        add(pannelloCampi, BorderLayout.CENTER);

        // Modalità modifica
        if (persona != null) {
            campoNome.setText(persona.getNome());
            campoCognome.setText(persona.getCognome());
            campoIndirizzo.setText(persona.getIndirizzo());
            campoNumero.setText(persona.getNumero());
            campoEta.setText(String.valueOf(persona.getEta()));
        }
    }

    private void salva() {

        try {
            String nome = campoNome.getText().strip();
            String cognome = campoCognome.getText().strip();
            String indirizzo = campoIndirizzo.getText().strip();
            String numero = campoNumero.getText().strip();

            int eta = Integer.parseInt(
                campoEta.getText().strip()
            );

            Persona persona = new Persona(
                nome, cognome, eta, numero, indirizzo
            );

            if (azioneSalvataggio.test(persona)) {
                dispose();
            }

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(
                this,
                "L'età deve essere un numero intero valido.",
                "Errore",
                JOptionPane.ERROR_MESSAGE
            );
        }
    }
}
