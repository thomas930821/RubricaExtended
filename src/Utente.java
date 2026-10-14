
public class Utente {

    private final String username;
    private final String password;

    public Utente(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public boolean verificaCredenziali(String username, String password) {
        return this.username.equals(username)
            && this.password.equals(password);
    }
}
