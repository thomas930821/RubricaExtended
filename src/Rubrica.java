import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Rubrica {

    private ArrayList<Persona> persone;


    public Rubrica(){
        this.persone = new ArrayList<>();
    }

    public EsitoInserimento aggiungiPersona(Persona persona) {
        
        if (!personaValida(persona)) {
            return EsitoInserimento.DATI_NON_VALIDI;
        }

        if (personaEsistente(persona)) {
            return EsitoInserimento.PERSONA_GIA_PRESENTE;
        }

        persone.add(persona);
        return EsitoInserimento.SUCCESSO;
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

        if (numero == null || !numero.matches("[0-9]{6,15}")) {
            return false;
        }

        if (persona.getEta() < 0 || persona.getEta() > 100) {
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


    public EsitoModifica modificaPersona(Persona originale, Persona modificata) {

        if (!personaValida(modificata)) {
            return EsitoModifica.DATI_NON_VALIDI;
        }

        if (originale == null) {
            return EsitoModifica.PERSONA_NON_TROVATA;
        }

        boolean presente = false;

        for (Persona p : persone) {

            if (p == originale) {
                presente = true;
            } else if (p.equals(modificata)) {
                return EsitoModifica.PERSONA_GIA_PRESENTE;
            }
        }

        if (!presente) {
            return EsitoModifica.PERSONA_NON_TROVATA;
        }

        originale.setNome(modificata.getNome());
        originale.setCognome(modificata.getCognome());
        originale.setIndirizzo(modificata.getIndirizzo());
        originale.setNumero(modificata.getNumero());
        originale.setEta(modificata.getEta());

        return EsitoModifica.SUCCESSO;
    }

    public List<Persona> getPersone() {
        return Collections.unmodifiableList(persone);
    }
}
