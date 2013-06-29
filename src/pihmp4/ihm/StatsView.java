/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pihmp4.ihm;

import java.util.Observable;
import java.util.Observer;
import javax.swing.JTextArea;
import pihmp4.controllers.Manager;
import pihmp4.game.User;
import pihmp4.utils.GlobalVar;
import pihmp4.utils.NotificationUser;

/**
 *
 * @author "Fabien Yerly, Jonathan Sifringer, Luis Domingues, Tiago De Deus"
 */
class StatsView extends JTextArea implements Observer {

    User user1;
    User user2;
    
    String text = new String();
    String textU1 = "";
    String textU2 = "";

    public StatsView(Manager manager, User user1, User user2) {
        user1.addObserver(this);
        user2.addObserver(this);

        this.user1 = user1;
        this.user2 = user2;
    }

    @Override
    public void update(Observable o, Object ob) {
        NotificationUser not = (NotificationUser) ob;

        if (o == user1) {
            textU1 = new String();
            String name = not.getName();
            String nbrOfPlays = not.getNbrOfPlays();
            String nbrOfWins = not.getNbrOfWins();
            String nbrOfLosses = not.getNbrOfLosses();

            textU1 += name + "\n";
            textU1 += GlobalVar.GetRessBundle().getString("nbrPlay") + " " + nbrOfPlays + "\n";
            textU1 += GlobalVar.GetRessBundle().getString("nbrWin") + " " + nbrOfWins + "\n";
            textU1 += GlobalVar.GetRessBundle().getString("nbrLoss") + " " + nbrOfLosses + "\n";
        } else if (o == user2) {
            textU2 = new String();
            String name = not.getName();
            String nbrOfPlays = not.getNbrOfPlays();
            String nbrOfWins = not.getNbrOfWins();
            String nbrOfLosses = not.getNbrOfLosses();

            textU2 += name + "\n";
            textU2 += GlobalVar.GetRessBundle().getString("nbrPlay") + " " + nbrOfPlays + "\n";
            textU2 += GlobalVar.GetRessBundle().getString("nbrWin") + " " + nbrOfWins + "\n";
            textU2 += GlobalVar.GetRessBundle().getString("nbrLoss") + " " + nbrOfLosses + "\n";
        }
        text = GlobalVar.GetRessBundle().getString("stat") + "\n\n" + textU1 + "\n" + textU2;
        setText(text);
    }
}
