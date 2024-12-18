package engine.connection;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Network {

    private Socket socket;
    private BufferedReader in;
    private PrintWriter out;


    public Network(){

    }

    public int start(String address, int port) {

        try {
            socket = new Socket(address, port);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);
        } catch (IOException e) {
            return -1;
        }

        return 0;
    }

    public void stop() throws IOException {
        socket.close();
    }

    public boolean status(){
        return !socket.isClosed();
    }

    public void send(String msg){
        out.println(msg);
    }

    public String recive() throws IOException {
        return in.readLine();
    }

}
