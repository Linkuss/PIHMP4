package pihmp4;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import pihmp4.controllers.Manager;
import pihmp4.ihm.GameView;
import pihmp4.ihm.MainView;
import pihmp4.ihm.NetworkView;
import pihmp4.ihm.OptionView;
import pihmp4.ihm.SinglePlayerView;

/**
 *
 * @author Fabien Yerly, Jonathan Sifringer, Luis Domingues, Tiago De Deus
 */
public class Main {

    public static void main(String[] args) {
        try {
            // Set System L&F
            UIManager.setLookAndFeel(
                    UIManager.getSystemLookAndFeelClassName());
        } catch (UnsupportedLookAndFeelException | ClassNotFoundException | InstantiationException | IllegalAccessException e) {
            // handle exception
        }
        
        Manager m = new Manager();

    }
}
