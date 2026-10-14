# RubricaExtended – Rubrica Telefonica Java

## Descrizione

RubricaExtended è un'applicazione desktop sviluppata in **Java**, con interfaccia grafica realizzata tramite **Swing**.

Il progetto nasce come esercizio di programmazione Java e permette di gestire una rubrica telefonica attraverso un'interfaccia semplice e intuitiva.

Oltre alle funzionalità di base, sono state implementate alcune estensioni per migliorare l'organizzazione dei dati e l'esperienza di utilizzo.

## Funzionalità

- **Autenticazione:** accesso all'applicazione tramite username e password.
- **Inserimento:** aggiunta di nuovi contatti alla rubrica.
- **Modifica:** aggiornamento delle informazioni dei contatti esistenti.
- **Eliminazione:** rimozione dei contatti previa conferma.
- **Visualizzazione:** elenco dei contatti tramite `JTable`.
- **Toolbar:** pulsanti per accedere alle principali operazioni.
- **Validazione:** controlli sui campi obbligatori, sull'età (da 0 a 100 anni) e sul numero telefonico.
- **Gestione dei duplicati:** verifica dei contatti già presenti.
- **Messaggi di errore specifici:** distinzione tra dati non validi e contatti duplicati.
- **Persistenza:** salvataggio e caricamento automatico dei contatti attraverso file TXT separati.

## Tecnologie utilizzate

- Java
- Java Swing
- Java Collections Framework (`ArrayList`)
- Gestione file con Java NIO
- Git e GitHub
- Visual Studio Code

## Struttura del progetto

```text
RubricaExtended/
├── src/
│   ├── App.java
│   ├── Persona.java
│   ├── Rubrica.java
│   ├── ArchivioRubrica.java
│   ├── FinestraRubrica.java
│   ├── EditorPersona.java
│   ├── Utente.java
│   ├── FinestraLogin.java
│   ├── EsitoInserimento.java
│   └── EsitoModifica.java
├── .gitignore
└── README.md
```

Durante l'esecuzione vengono utilizzati anche:

- `credenziali.properties`: file esterno contenente le credenziali di accesso.
- `informazioni/`: cartella contenente i contatti salvati nei file `Persona1.txt`, `Persona2.txt` e così via.

Questi file non vengono pubblicati su GitHub.

## Avvio dell'applicazione

Per eseguire il progetto è necessario disporre di una versione di Java compatibile con quella utilizzata per la compilazione.

### Da Visual Studio Code

1. Aprire la cartella `RubricaExtended`.
2. Creare nella directory principale il file `credenziali.properties`.
3. Inserire le credenziali di accesso nel formato:

```properties
username=admin
password=admin123
```

4. Avviare la classe `App.java`.

Le credenziali mostrate sono esclusivamente dimostrative.

### Tramite file JAR

Posizionare `RubricaExtended.jar` e `credenziali.properties` nella stessa cartella.

Aprire il terminale in quella cartella ed eseguire:

```bash
java -jar RubricaExtended.jar
```

Il programma crea automaticamente la cartella `informazioni` qualora non sia già presente.

## Gestione dei dati

Ogni contatto viene salvato in un file TXT distinto, utilizzando il seguente formato:

```text
nome;cognome;indirizzo;telefono;eta
```

All'avvio, l'applicazione legge i file presenti nella cartella `informazioni` e ricostruisce l'elenco dei contatti.

## Controlli e validazione

L'applicazione verifica che:

- I campi obbligatori siano compilati.
- L'età sia compresa tra 0 e 100 anni.
- Il numero telefonico contenga soltanto cifre e rispetti i limiti di lunghezza previsti.
- Non vengano inseriti contatti duplicati, identificati tramite nome, cognome e indirizzo.

In caso di errore, vengono visualizzati messaggi specifici e l'operazione non viene completata.

## Note

Il progetto utilizza un sistema di autenticazione dimostrativo basato su un file di configurazione locale. Non è destinato alla gestione di credenziali reali o all'utilizzo in ambienti di produzione.

I dati della rubrica e le credenziali non vengono inclusi nel repository GitHub.

## Autore

Progetto realizzato come esercitazione pratica per una selezione tecnica Java.
