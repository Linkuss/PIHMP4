/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pihmp4.ihm;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import net.miginfocom.swing.MigLayout;
import pihmp4.controllers.Manager;
import pihmp4.utils.GlobalVar;

/**
 *
 * @author "Fabien Yerly, Jonathan Sifringer, Luis Domingues, Tiago De Deus"
 */
public class SinglePlayerView extends JFrame implements ActionListener {

    private JPanel contentPane;
    private JButton btEasy;
    private JButton btNormal;
    private JButton btHard;
    private JButton btReturn;
    private Manager manager;
    /**
     * Create the frame.
     */
    public SinglePlayerView(Manager manager) {
        this.manager=manager;
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 650, 500);
        this.setTitle(GlobalVar.GetRessBundle().getString("mvTitle"));
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(new MigLayout("", "[grow 100]", "[grow 20][grow 10][grow 10,center][grow 10,center][grow 10,center][grow 40]"));

        JLabel lbSinglePlayer = new JLabel(GlobalVar.GetRessBundle().getString("singPlayTitle"));
        lbSinglePlayer.setFont(new Font("Dialog", Font.BOLD, 38));
        contentPane.add(lbSinglePlayer, "cell 0 0 1 1,alignx center");

        JLabel lbDifficulty = new JLabel(GlobalVar.GetRessBundle().getString("chooseDiffText"));
        lbDifficulty.setHorizontalAlignment(SwingConstants.RIGHT);
        contentPane.add(lbDifficulty, "cell 0 1,alignx center, aligny center,aligny top");

        btEasy = new JButton(GlobalVar.GetRessBundle().getString("easyButton"));
        contentPane.add(btEasy, "cell 0 2 1 1,alignx center");
        btEasy.addActionListener(this);

        btNormal = new JButton(GlobalVar.GetRessBundle().getString("normalButton"));
        contentPane.add(btNormal, "cell 0 3 1 1,alignx center");
        btNormal.addActionListener(this);

        btHard = new JButton(GlobalVar.GetRessBundle().getString("hardButton"));
        contentPane.add(btHard, "cell 0 4 1 1,alignx center");
        btHard.addActionListener(this);
        
        btReturn = new JButton(GlobalVar.GetRessBundle().getString("returnButton"));
        contentPane.add(btReturn, "cell 0 5 1 1, alignx center");
        btReturn.addActionListener(this);
        
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        manager.closeSinglePlayer();
        //easy level
        if(source == btEasy){
            manager.setEasyLevel();
            manager.openGameView();
        }
        //normal level
        else if (source == btNormal) {
            manager.setNormalLevel();
            manager.openGameView();
        }
        //Difficult level
        else if(source == btHard){
            manager.setDifficultLevel();
            manager.openGameView(); 
        }
        else if(source == btReturn){
          
            manager.returnToMainView(this);
        }
        else{
            JOptionPane.showMessageDialog(this, "Error");
        }
    }
}
