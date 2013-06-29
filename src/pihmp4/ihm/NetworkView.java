/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pihmp4.ihm;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.Inet4Address;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.SwingWorker;
import javax.swing.border.EmptyBorder;
import net.miginfocom.swing.MigLayout;
import pihmp4.controllers.Manager;
import pihmp4.utils.GlobalVar;
import pihmp4.utils.StringValidator;

/**
 *
 * @author "Fabien Yerly, Jonathan Sifringer, Luis Domingues, Tiago De Deus"
 */
public class NetworkView extends JFrame implements ActionListener {

    private JPanel contentPane;
    private Manager manager;
    private JButton btConnect;
    private JButton btReturn;
    private JButton btHost;
    private String name;
    private JLabel lMessage;
    private boolean isProcessingC = false;
    private boolean isProcessingH = false;
    private Connection connect = null;
    private Host host = null;

    /**
     * Create the frame.
     */
    /**
     *
     * @param manager the controler
     * @param name the name of the host player
     */
    public NetworkView(Manager manager, String name) {
        this.name = name;
        this.manager = manager;
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 650, 500);
        this.setTitle(GlobalVar.GetRessBundle().getString("mvTitle"));
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(new MigLayout("", "[grow 50][grow 50]", "[grow 20][grow 10][grow 10,center][grow 10,center][grow 50]"));

        JLabel lbNetworkBannier = new JLabel(GlobalVar.GetRessBundle().getString("netPlayTitle"));
        lbNetworkBannier.setFont(new Font("Dialog", Font.BOLD, 38));
        contentPane.add(lbNetworkBannier, "cell 0 0 2 1,alignx center");

        JLabel lbWhatIp = new JLabel(GlobalVar.GetRessBundle().getString("ip"));
        lbWhatIp.setHorizontalAlignment(SwingConstants.RIGHT);
        contentPane.add(lbWhatIp, "cell 0 1,alignx right,aligny top");

        JLabel lbIpAddress = null;
        try {
            lbIpAddress = new JLabel(Inet4Address.getLocalHost().getHostAddress());
        } catch (Exception ex) {
            Logger.getLogger(NetworkView.class.getName()).log(Level.SEVERE, null, ex);
        }
        lbIpAddress.setHorizontalAlignment(SwingConstants.LEFT);
        contentPane.add(lbIpAddress, "cell 1 1,alignx left,aligny top");

        btHost = new JButton(GlobalVar.GetRessBundle().getString("hostGameButton"));
        contentPane.add(btHost, "cell 0 2 2 1,alignx center");
        btHost.addActionListener(this);

        btConnect = new JButton(GlobalVar.GetRessBundle().getString("connectButton"));
        contentPane.add(btConnect, "cell 0 3 2 1,alignx center");
        //Add actionListeners
        btConnect.addActionListener(this);

        lMessage = new JLabel();
        contentPane.add(lMessage, "cell 0 4 2 1, alignx center");

        btReturn = new JButton(GlobalVar.GetRessBundle().getString("returnButton"));
        contentPane.add(btReturn, "cell 0 5 2 1, alignx center");
        btReturn.addActionListener(this);


    }

    /**
     *
     * @param text waiting message when wait network player
     */
    public void setMessage(String text) {
        lMessage.setText(text);
    }

    private void GrayAll() {
        btHost.setEnabled(false);
        btConnect.setEnabled(false);
        btReturn.setText(GlobalVar.GetRessBundle().getString("cancelButton"));
    }

    private void restoreAll() {
        btHost.setEnabled(true);
        btConnect.setEnabled(true);
        btReturn.setText(GlobalVar.GetRessBundle().getString("returnButton"));
        lMessage.setText("");
    }

    /**
     * Take button action
     *
     * @param e
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        if (source == btConnect) {
            String ip = JOptionPane.showInputDialog(GlobalVar.GetRessBundle().getString("ipServerText"));
            if (ip == null) {
                // do nothing
            } else {
                connect = new Connection(ip, name);
                while (!StringValidator.ipValidator(ip)) {
                    ip = JOptionPane.showInputDialog(GlobalVar.GetRessBundle().getString("ipFalseServerText"));
                }

                lMessage.setText(GlobalVar.GetRessBundle().getString("waitConnectText"));
                isProcessingC = true;
                GrayAll();
                connect.execute();
            }
        } else if (source == btHost) {
            host = new Host(name);
            isProcessingH=true;
            host.execute();
            lMessage.setText(GlobalVar.GetRessBundle().getString("waitHostText"));
            GrayAll();
        } else if (source == btReturn) {
            if(isProcessingC){
                if(connect.cancel(true)){
                    restoreAll();
                    isProcessingC=false;
                    return;
                }
            }else if(isProcessingH){
                if(host.cancel(true)){
                    restoreAll();
                    isProcessingH=false;
                    return;
                }
            }
            manager.returnToMainView(this);
        } else {
            manager.returnToMainView(this);
        }
    }

    private class Connection extends SwingWorker {

        String ip;
        String name;
        boolean isCo;

        public Connection(String ip, String name) {
            this.ip = ip;
            this.name = name;
        }

        @Override
        protected Object doInBackground() throws Exception {
            isCo = manager.connectClient(ip, name);
            return null;
        }

        @Override
        protected void done() {
            if (isCo) {
                restoreAll();
                manager.setWhosWaiting(true);
                manager.setNetActive();
                manager.closeNetworkView();
                manager.openGameView();
                manager.waitNetPlay();
            } else {
                restoreAll();
                lMessage.setText(GlobalVar.GetRessBundle().getString("errorHostText"));
            }
        }
    }

    private class Host extends SwingWorker {

        String name;
        boolean isCo;

        public Host(String name) {
            this.name = name;
        }

        @Override
        protected Object doInBackground() throws Exception {
            isCo = manager.waitConnectionClient(name);
            return null;
        }

        @Override
        protected void done() {
            btHost.setEnabled(true);
            btConnect.setEnabled(true);
            if (isCo) {
                restoreAll();
                manager.setWhosWaiting(false);
                manager.setNetActive();
                manager.closeNetworkView();
                manager.openGameView();
            } else {
                restoreAll();
                lMessage.setText(GlobalVar.GetRessBundle().getString("errorConnectText"));
            }
        }
    }
}