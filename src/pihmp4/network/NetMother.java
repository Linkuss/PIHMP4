package pihmp4.network;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import pihmp4.controllers.Manager;
import pihmp4.utils.GlobalVar;

/**
 *
 * @author Fabien Yerly, Jonathan Sifringer, Luis Domingues, Tiago De Deus
 */
public class NetMother {
    
    Socket sock;
    ServerSocket servSock = null;
    ObjectOutputStream out = null;
    ObjectInputStream in = null;
    
    
    
    
    public void sendMessage(int column) throws IOException {
        out.writeObject(String.valueOf(column));
        out.flush();

    }
    
    public void sendName(String s) throws IOException{
        out.writeObject(s);
        out.flush();
    }

    public int getMessage() throws IOException, ClassNotFoundException {
        String message = (String)in.readObject();
        if(message.equals("bye")){
            return -1;
        }
        return Integer.parseInt(message);

    }
    
    public String getName() throws IOException, ClassNotFoundException{
        String message = (String)in.readObject();
        return message;
    }
    
    public void close() throws IOException{
        sock.close();
        out.close();
        in.close();
    }
    
}
