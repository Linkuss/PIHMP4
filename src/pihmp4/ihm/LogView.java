/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pihmp4.ihm;

import java.awt.Dimension;
import java.util.Observable;
import java.util.Observer;
import javax.swing.JTextArea;
import pihmp4.controllers.Manager;
import pihmp4.utils.GlobalVar;
import pihmp4.utils.NotificationLog;

/**
 *
 * @author "Fabien Yerly, Jonathan Sifringer, Luis Domingues, Tiago De Deus"
 */
public class LogView extends JTextArea implements Observer {

    public LogView(Manager manager) {
        manager.addObserver(this);
    }

    @Override
    public void update(Observable o, Object arg) {
        NotificationLog not = (NotificationLog) arg;
        String text = getText();
        if(not.getPrevUser() == null){
          return;  
        }
        if(not.isWinner()){
            text += not.getPrevUser() + " " + GlobalVar.GetRessBundle().getString("win") + "\n";
        } else {
            text += not.getPrevUser() + " " + GlobalVar.GetRessBundle().getString("colPlayed") + " " + not.getColumn() + "\n";
        }
        setText(text);
    }

}
