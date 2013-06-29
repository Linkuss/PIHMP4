/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pihmp4.controllers;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 *
 * @author "Fabien Yerly, Jonathan Sifringer, Luis Domingues, Tiago De Deus"
 */
public class PlayerListener implements MouseListener {

    private int columnWidth;
    private Manager manager;

    public PlayerListener(Manager manager) {
        this.manager = manager;
    }

    /**
     * Set the column width. Use to calculate the column clicked. In case of
     * panel rezise, the diameter need to be updated.
     *
     * @param columnWidth Width of the column
     */
    public void setColumnWidth(int columnWidth) {
        this.columnWidth = columnWidth;
    }

    /**
     * Get the click mouse event.
     *
     * @param e Mouse Event.
     */
    @Override
    public void mouseClicked(MouseEvent e) {
        int col = e.getX() / (columnWidth);
        manager.wantsToPlay(col);
    }

    /**
     * Get the press mouse event. This event isn't used in this program. This
     * method do nothing when called.
     *
     * @param e Mouse Event.
     */
    @Override
    public void mousePressed(MouseEvent e) {
        //Do nothing
    }

    /**
     * Get the release mouse event. This event isn't used in this program. This
     * method do nothing when called.
     *
     * @param e Mouse Event.
     */
    @Override
    public void mouseReleased(MouseEvent e) {
        //Do nothing
    }

    /**
     * Get the entered mouse event. This event isn't used in this program. This
     * method do nothing when called.
     *
     * @param e Mouse Event.
     */
    @Override
    public void mouseEntered(MouseEvent e) {
        //Do nothing
    }

    /**
     * Get the exit mouse event. This event isn't used in this program. This
     * method do nothing when called.
     *
     * @param e Mouse Event.
     */
    @Override
    public void mouseExited(MouseEvent e) {
        //Do nothing
    }
}
