package pihmp4.ihm;

import java.util.Observable;
import java.util.Observer;
import javax.swing.JLabel;
import pihmp4.controllers.Manager;
import pihmp4.utils.GlobalVar;
import pihmp4.utils.NotificationLog;

/**
 *
 * @author "Fabien Yerly, Jonathan Sifringer, Luis Domingues, Tiago De Deus"
 */
public class PlayerTurnLabel extends JLabel implements Observer {
    
    public PlayerTurnLabel(Manager manager) {
        manager.addObserver(this);
        //setText(GlobalVar.GetRessBundle().getString("waitFirst"));
    }

    @Override
    public void update(Observable o, Object arg) {
        NotificationLog not = (NotificationLog) arg;
        String text = GlobalVar.GetRessBundle().getString("nextPlayer1") + " " + not.getNextUser() + " " +GlobalVar.GetRessBundle().getString("nextPlayer2");
        setText(text);
    }
    
}
