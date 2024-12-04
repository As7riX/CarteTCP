package connection;

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

    public int start(String address, int port) throws IOException {

            socket = new Socket(address, port);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);

        /*
        } catch (UnknownHostException e) {
            System.err.println("Don't know about host " + address);
            throw new RuntimeException(e);

        } catch (IOException e) {
            System.err.println("Couldn't get I/O for the connection to " + address);
            throw new RuntimeException(e);

        }*/

        return 0;
    }

    public void send(String msg){
        out.println(msg);
    }

    public String recive() throws IOException {
        return in.readLine();
    }

}
