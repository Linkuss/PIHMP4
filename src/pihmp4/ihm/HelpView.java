/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pihmp4.ihm;

import java.awt.Desktop;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import net.miginfocom.swing.MigLayout;


import pihmp4.controllers.Manager;
import pihmp4.utils.GlobalVar;

/**
 * Open a browser windows with the help (page)
 *
 * @author "Fabien Yerly, Jonathan Sifringer, Luis Domingues, Tiago De Deus"
 */
public class HelpView extends JFrame {

    private JPanel contentPane;
    private JScrollPane spHelp;
    private JEditorPane epHelp;
    private Manager manager;

    /**
     *
     * @param manager
     */
    public HelpView(Manager manager) {
        this.manager = manager;
        setBounds(100, 50, 800, 600);
        this.setTitle(GlobalVar.GetRessBundle().getString("mvTitle"));
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(new MigLayout("", "", ""));
        
        epHelp = new JEditorPane();
        epHelp.setEditable(false);
        try {
            epHelp.setPage(GlobalVar.getHelpURL());
        } catch (Exception ex) {
            manager.returnToMainView(this);
        }
      
        spHelp = new JScrollPane(epHelp);

        contentPane.add(spHelp, "grow");
    }
}
