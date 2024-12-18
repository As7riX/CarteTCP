# CarteTCP

**CarteTCP** è un progetto basato su Java che implementa un sistema client-server per gestire un gioco di carte tramite connessioni TCP.

---

## **Descrizione del Progetto**

Questo progetto utilizza un'architettura client-server per simulare la gestione di un gioco di carte. Include funzionalità per:
- Connessione del client al server tramite TCP.
- Scambio di messaggi tra client e server.
- Gestione delle carte e del mazzo tramite oggetti Java.

Il codice è suddiviso in moduli principali per il client, il server e la logica di gioco.

---

## **Requisiti**

- **Java** 11 o versioni successive
- **Maven** (opzionale, per la gestione delle dipendenze)
- Un IDE come IntelliJ IDEA o Eclipse

---

## **Installazione**

1. Clona il repository:
   ```bash
   git clone https://github.com/As7riX/CarteTCP.git
   ```

2. Naviga nella directory del progetto:
   ```bash
   cd CarteTCP
   ```

3. Compila il progetto utilizzando il comando:
   ```bash
   javac -d bin src/**/*.java
   ```

4. Esegui il server e il client:
   - Avvia il server:
     ```bash
     java -cp bin engine.server.Server
     ```
   - Avvia il client:
     ```bash
     java -cp bin engine.client.Client
     ```

---

## **Struttura del Progetto**

- `engine.connection`: Modulo per la gestione della connessione client-server.
- `engine.models`: Logica di gioco, inclusi mazzi e carte.
- `engine.service`: Gestione dei comandi e ascolto degli input.

---

## **Uso**

1. Avvia il server sulla macchina host.
2. Configura l'indirizzo IP del server nel file `Client.java` (di default: `127.0.0.1`).
3. Esegui il client e interagisci con il server inviando comandi.

---

## **Contributi**

Contributi sono benvenuti! Segui questi passaggi per contribuire:

1. Fai un fork del repository.
2. Crea un nuovo branch per le tue modifiche:
   ```bash
   git checkout -b feature/nome-funzionalita
   ```
3. Effettua le modifiche e committale:
   ```bash
   git commit -m "Aggiunta funzionalità X"
   ```
4. Fai una pull request verso il branch principale del repository originale.

---

## **Licenza**

Questo progetto è distribuito sotto la licenza MIT. Consulta il file [LICENSE](LICENSE) per maggiori dettagli.

---

## **Autore**

Progetto sviluppato da [As7riX](https://github.com/As7riX).

---

## **Riferimenti**

- [Documentazione ufficiale di Java](https://docs.oracle.com/en/java/)
- [Tutorial su Sockets TCP in Java](https://docs.oracle.com/javase/tutorial/networking/sockets/)
