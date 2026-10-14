public class Persona {

    private int eta;
    private String nome;
    private String cognome;
    private String numero;
    private String indirizzo;

    public Persona(String nome, String cognome, int eta, String numero, String indirizzo){
        this.eta = eta;
        this.nome = nome;
        this.cognome = cognome;
        this.numero = numero;
        this.indirizzo = indirizzo;
    }

    public int getEta() {
        return eta;
    }
    public void setEta(int eta) {
        this.eta = eta;
    }
    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }
    public String getCognome() {
        return cognome;
    }
    public void setCognome(String cognome) {
        this.cognome = cognome;
    }
    public String getNumero() {
        return numero;
    }
    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getIndirizzo() {
        return indirizzo;
    }

    public void setIndirizzo(String indirizzo) {
        this.indirizzo = indirizzo;
    }

    @Override 
    public boolean equals(Object obj){
        if (this == obj)
            return true;

        if (!(obj instanceof Persona))
            return false;

        Persona altra = (Persona) obj;

        return nome.strip().equalsIgnoreCase(altra.nome.strip())
            && cognome.strip().equalsIgnoreCase(altra.cognome.strip())
            && indirizzo.strip().equalsIgnoreCase(altra.indirizzo.strip());
    }

    @Override
    public int hashCode() {
        return java.util.Objects.hash(
            nome.strip().toLowerCase(java.util.Locale.ROOT),
            cognome.strip().toLowerCase(java.util.Locale.ROOT),
            indirizzo.strip().toLowerCase(java.util.Locale.ROOT)
        );
    }
}
