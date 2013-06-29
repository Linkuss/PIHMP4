package pihmp4.network;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import pihmp4.utils.GlobalVar;

/**
 * @author Fabien Yerly, Jonathan Sifringer, Luis Domingues, Tiago De Deus
 */
public class NetClient extends NetMother {


    public void connectToServer(String ip) throws UnknownHostException, IOException {
        sock = new Socket(ip,GlobalVar.getPort());
        out = new ObjectOutputStream(sock.getOutputStream());
        in = new ObjectInputStream(sock.getInputStream());
    }
}
