/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pihmp4.ihm;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import net.miginfocom.swing.MigLayout;
import pihmp4.controllers.Manager;
import pihmp4.game.Grid;
import pihmp4.game.User;
import pihmp4.utils.GlobalVar;

/**
 * The general view of the game
 * @author "Fabien Yerly, Jonathan Sifringer, Luis Domingues, Tiago De Deus"
 */
public class GameView extends JFrame implements ActionListener{

    private JPanel contentPane;
    private GamePanel pGamePanel;
    private JButton btExit;
    private JToggleButton btMute;
    private Manager manager;

    /**
     * Create the frame.
     * Add the different button
     * @param grid the game
     * @param manager the controler
     * @param user1 the user1 observable
     * @param user2 the user2 observable
     */
    public GameView(Grid grid, Manager manager, User user1, User user2) {
        this.manager = manager;
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 50, 800, 600);
        this.setTitle(GlobalVar.GetRessBundle().getString("mvTitle"));
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(new MigLayout("", "[80%][20%]", "[][grow][]"));

        JLabel lbPlayerTurn = new PlayerTurnLabel(manager);
        contentPane.add(lbPlayerTurn, "cell 0 0");

        pGamePanel = new GamePanel(grid, manager);
        contentPane.add(pGamePanel, "cell 0 1,grow");

        JPanel pRightGameView = new JPanel();
        contentPane.add(pRightGameView, "cell 1 1,grow");
        pRightGameView.setLayout(new MigLayout("", "[grow]", "[grow][grow]"));
        
        JTextArea taStats = new StatsView(manager, user1, user2);
        taStats.setEditable(false);
        pRightGameView.add(taStats, "grow");
        
        JScrollPane spConsol = new JScrollPane();
        pRightGameView.add(spConsol, "cell 0 1,grow");

        JTextArea textArea = new LogView(manager);
        textArea.setEditable(false);
        spConsol.setViewportView(textArea);

        btExit = new JButton(GlobalVar.GetRessBundle().getString("returnButton"));
        contentPane.add(btExit, "cell 0 2,alignx center");
        btExit.addActionListener(this);
        setMinimumSize(getSize());
        
        btMute = new JToggleButton(GlobalVar.getUnmutedImage());
        pRightGameView.add(btMute, "cell 0 3,alignx center");
        btMute.addActionListener(this);
        setMinimumSize(getSize());
    }

    /**
     * Play the animation
     * @param x ligne
     * @param y colone
     * @param player played
     */
    public void runAnime(int x, int y, GlobalVar.player player) {
        pGamePanel.runAnime(x, y, player);
    }
    
    /**
     * Get the button event
     * @param e 
     */
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        if (source == btExit) {
            manager.returnToMainView(this);
        }else if(source == btMute){
            manager.muteUnMuteSound();
            if(btMute.isSelected()){
                btMute.setIcon(GlobalVar.getMutedImage());
            }else{
                btMute.setIcon(GlobalVar.getUnmutedImage());
            }
        }
    }
}