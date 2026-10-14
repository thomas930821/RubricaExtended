import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Rubrica {

    private ArrayList<Persona> persone;


    public Rubrica(){
        this.persone = new ArrayList<>();
    }

    public boolean aggiungiPersona(Persona persona) {
        if (persona == null) {
            return false;
        }

        if (!personaValida(persona)) {
            return false;
        }

        if (personaEsistente(persona)) {
            return false;
        }

        persone.add(persona);
        return true;
    }

    private boolean personaEsistente(Persona persona) {
        return persone.contains(persona);
    }


    private boolean personaValida(Persona persona) {

        if (persona == null) {
            return false;
        }

        String nome = persona.getNome();
        String cognome = persona.getCognome();
        String indirizzo = persona.getIndirizzo();
        String numero = persona.getNumero();

        if (nome == null || nome.isBlank() || nome.contains(";")) {
            return false;
        }

        if (cognome == null || cognome.isBlank() || cognome.contains(";")) {
            return false;
        }

        if (indirizzo == null || indirizzo.isBlank() || indirizzo.contains(";")) {
            return false;
        }

        if (numero == null || !numero.matches("[0-9]+")) {
            return false;
        }

        if (persona.getEta() < 0) {
            return false;
        }

        return true;
    }

    public boolean eliminaPersona(Persona persona) {
        if (!personaValida(persona)) {
            return false;
        }

        return persone.remove(persona);
    }

    public boolean modificaPersona(Persona originale, Persona modificata) {

        if (originale == null || !personaValida(modificata)) {
            return false;
        }

        boolean presente = false;

        for (Persona p : persone) {
            if (p == originale) {
                presente = true;
            } else if (p.equals(modificata)) {
                return false;
            }
        }

        if (!presente) {
            return false;
        }

        originale.setNome(modificata.getNome());
        originale.setCognome(modificata.getCognome());
        originale.setIndirizzo(modificata.getIndirizzo());
        originale.setNumero(modificata.getNumero());
        originale.setEta(modificata.getEta());

        return true;
    }

    public List<Persona> getPersone() {
        return Collections.unmodifiableList(persone);
    }
}
