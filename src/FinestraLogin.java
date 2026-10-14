
import javax.swing.*;
import java.awt.*;
import java.util.function.BiPredicate;

public class FinestraLogin extends JFrame {

    private JTextField campoUsername;
    private JPasswordField campoPassword;
    private JButton btnLogin;

    public FinestraLogin(BiPredicate<String, String> autenticazione) {
        super("Accesso Rubrica");

        setSize(350, 200);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        JPanel pannelloCampi = new JPanel(
            new GridLayout(2, 2, 10, 10)
        );

        campoUsername = new JTextField();
        campoPassword = new JPasswordField();

        pannelloCampi.add(new JLabel("Username:"));
        pannelloCampi.add(campoUsername);

        pannelloCampi.add(new JLabel("Password:"));
        pannelloCampi.add(campoPassword);

        JPanel pannelloBottoni = new JPanel();
        btnLogin = new JButton("LOGIN");
        pannelloBottoni.add(btnLogin);

        add(pannelloCampi, BorderLayout.CENTER);
        add(pannelloBottoni, BorderLayout.SOUTH);

        btnLogin.addActionListener(e -> {

            String username = campoUsername.getText().strip();
            String password = new String(campoPassword.getPassword());

            if (autenticazione.test(username, password)) {
                dispose();
            } else {
                JOptionPane.showMessageDialog(
                    this,
                    "Login errato",
                    "Errore di autenticazione",
                    JOptionPane.ERROR_MESSAGE
                );

                campoPassword.setText("");
            }
        });
    }
}
