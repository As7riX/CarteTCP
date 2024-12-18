

## **utilizzo**

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

## **Uso**

1. Avvia il server sulla macchina host.
2. Configura l'indirizzo IP del server nel file `Client.java` (di default: `127.0.0.1`).
3. Esegui il client e interagisci con il server inviando comandi.

