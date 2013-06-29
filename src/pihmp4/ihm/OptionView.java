/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pihmp4.ihm;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
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
public class OptionView extends JFrame implements ActionListener {

    private Manager manager;
    private JPanel contentPane;
    private JButton btValideOptions;
    private JButton btCancel;
    private JComboBox cbTheme;
    private JCheckBox cbMusic;
    private JComboBox cbLanguage;

    /**
     * The view to modify the options
     * @param manager the controler
     */
    public OptionView(Manager manager) {
        this.manager = manager;
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 650, 500);
        this.setTitle(GlobalVar.GetRessBundle().getString("mvTitle"));
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(new MigLayout("", "[grow 50,right][grow 50,left]", "[grow 20,center][grow 10,center][grow 10,center][grow 10,center][grow 10,center][grow 40]"));

        JLabel lbOptionView = new JLabel(GlobalVar.GetRessBundle().getString("optionTitle"));
        lbOptionView.setFont(new Font("Dialog", Font.BOLD, 38));
        contentPane.add(lbOptionView, "cell 0 0 2 1,alignx center,aligny center");

        JLabel lbLanguage = new JLabel(GlobalVar.GetRessBundle().getString("languageText"));
        contentPane.add(lbLanguage, "cell 0 1,alignx trailing");

        cbLanguage = new JComboBox();
        cbLanguage.setModel(new DefaultComboBoxModel(GlobalVar.getAllLanguage()));
        cbLanguage.setSelectedItem(GlobalVar.getCurentLanguage());
        cbLanguage.setMaximumRowCount(4);
        contentPane.add(cbLanguage, "cell 1 1,alignx left");

        JLabel lbTheme = new JLabel(GlobalVar.GetRessBundle().getString("themeText"));
        contentPane.add(lbTheme, "cell 0 2,alignx trailing");

        cbTheme = new JComboBox();
        cbTheme.setModel(new DefaultComboBoxModel(GlobalVar.getAllthemes()));
        cbTheme.setSelectedItem(GlobalVar.getCurrentTheme());
        cbTheme.setMaximumRowCount(5);
        contentPane.add(cbTheme, "cell 1 2,alignx left");

        JLabel lbMusic = new JLabel(GlobalVar.GetRessBundle().getString("musicText"));
        contentPane.add(lbMusic, "cell 0 3");

        cbMusic = new JCheckBox("");
        if (!GlobalVar.isIsMute()) {
            cbMusic.setSelected(true);
        }
        contentPane.add(cbMusic, "cell 1 3");

        btValideOptions = new JButton(GlobalVar.GetRessBundle().getString("validateButton"));
        btValideOptions.setVerticalAlignment(SwingConstants.TOP);
        btValideOptions.addActionListener(this);
        contentPane.add(btValideOptions, "cell 1 5, alignx left,aligny top");
        
        btCancel = new JButton(GlobalVar.GetRessBundle().getString("cancelButton"));
        btCancel.setVerticalAlignment(SwingConstants.TOP);
        btCancel.addActionListener(this);
        contentPane.add(btCancel, "cell 0 5, alignx right, aligny top");
    }

    /**
     * take button action
     * @param e 
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        if (source == btValideOptions) {
            GlobalVar.setTheme((String) cbTheme.getSelectedItem());
            GlobalVar.setIsMute(!cbMusic.isSelected());
            GlobalVar.setLocal((String) cbLanguage.getSelectedItem());

            manager.returnToMainView(this);
        }
        else if(source == btCancel){
            manager.returnToMainView(this);
        }
        else{
            manager.returnToMainView(this);
        }
    }
}
