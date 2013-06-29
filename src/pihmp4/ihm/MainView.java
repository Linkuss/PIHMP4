package pihmp4.ihm;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import net.miginfocom.swing.MigLayout;
import pihmp4.controllers.Manager;
import pihmp4.utils.GlobalVar;

/**
 * MainView this class contain the main window of the program
 *
 * @author "Fabien Yerly, Jonathan Sifringer, Luis Domingues, Tiago De Deus"
 */
public class MainView extends JFrame implements ActionListener {

    private JPanel contentPane;
    private JTextField tfPlayerName1;
    private Manager manager;
    private JButton btLocalSingle;
    private JButton btLocalMulti;
    private JButton btNetworkMulti;
    private JButton btOptions;
    private JButton btHelp;
    private JButton btExit;

    /**
     * 
     * @param manager 
     */
    public MainView(Manager manager) {
        this.manager = manager;

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 650, 500);
        this.setTitle(GlobalVar.GetRessBundle().getString("mvTitle"));
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(new MigLayout("", "[][][grow]", "[][][][][][][][][][][][][][][]"));

        JLabel lbBann = new JLabel("");
        lbBann.setIcon(GlobalVar.getBanImage());
        contentPane.add(lbBann, "cell 0 0 3 4,alignx center");

        JLabel lbPseudo1 = new JLabel(GlobalVar.GetRessBundle().getString("enter_pseudoText"));
        contentPane.add(lbPseudo1, "cell 1 4,alignx trailing");

        tfPlayerName1 = new JTextField();
        tfPlayerName1.setText(GlobalVar.GetRessBundle().getString("player1Text"));
        contentPane.add(tfPlayerName1, "cell 2 4,growx");
        tfPlayerName1.setColumns(10);
        tfPlayerName1.setSize(15, 2);


        btLocalSingle = new JButton(GlobalVar.GetRessBundle().getString("localSingButton"));
        contentPane.add(btLocalSingle, "cell 2 6");
        btLocalSingle.addActionListener(this);

        btLocalMulti = new JButton(GlobalVar.GetRessBundle().getString("localMultButton"));
        contentPane.add(btLocalMulti, "cell 2 8");
        btLocalMulti.addActionListener(this);

        btNetworkMulti = new JButton(GlobalVar.GetRessBundle().getString("netMultButton"));
        contentPane.add(btNetworkMulti, "cell 2 10");
        btNetworkMulti.addActionListener(this);

        btOptions = new JButton(GlobalVar.GetRessBundle().getString("optionButton"));
        contentPane.add(btOptions, "cell 2 12");
        btOptions.addActionListener(this);

        btHelp = new JButton(GlobalVar.GetRessBundle().getString("helpButton"));
        contentPane.add(btHelp, "cell 2 14");
        btHelp.addActionListener(this);

        btExit = new JButton(GlobalVar.GetRessBundle().getString("exitButton"));
        contentPane.add(btExit, "cell 2 16");
        btExit.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        if (source == btLocalMulti) {
            manager.setaiActive(false);
            String pseudo2 = JOptionPane.showInputDialog(this, GlobalVar.GetRessBundle().getString("insPlay2Text"),
                    GlobalVar.GetRessBundle().getString("player2Text"));
            if (pseudo2 == null) {
                // do nothing

            } else {

                manager.setPlayer2Name((String) pseudo2);
                manager.setPlayer1Name(tfPlayerName1.getText());
                manager.openGameView();

            }
        } else if (source == btHelp) {
            manager.openHelp();
        } else if (source == btLocalSingle) {
            manager.setPlayer1Name(tfPlayerName1.getText());
            manager.setaiActive(true);
            manager.openSinglePlayer();

        } else if (source == btNetworkMulti) {
            manager.setPlayer1Name(tfPlayerName1.getText());
            manager.openNetworkView();
        } else if (source == btOptions) {
            manager.openOptionView();
        } else if (source == btExit) {
            manager.exitGame();
        } else {
            JOptionPane.showMessageDialog(this, "Error");
        }

    }
}