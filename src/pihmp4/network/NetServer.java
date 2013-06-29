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
public class NetServer extends NetMother{

    
    public void waitingConnect() throws IOException {
        if(servSock== null){
            servSock = new ServerSocket(GlobalVar.getPort());
        }
        sock = servSock.accept();
        out = new ObjectOutputStream(sock.getOutputStream());
        out.flush();
        in = new ObjectInputStream(sock.getInputStream());

    }
    
    public void close() throws IOException{
        super.close();
        servSock.close();
    }

}
