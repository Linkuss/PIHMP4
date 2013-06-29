/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pihmp4.ihm;

import java.util.logging.Level;
import java.util.logging.Logger;
import pihmp4.controllers.Manager;

/**
 *
 * @author "Fabien Yerly, Jonathan Sifringer, Luis Domingues, Tiago De Deus"
 */
public class AnimationThread extends Thread {

    private int toY;
    private int x;
    private GamePanel panel;
    private Manager manager;

    /**
     * Contructor. Create a new thread with final token position.
     *
     * @param manager Manager to tell the animation is ended.
     * @param x Position on X of the played token.
     * @param toY Destination of the token on Y.
     * @param panel Panel to repaint.
     */
    public AnimationThread(Manager manager, int x, int toY, GamePanel panel) {
        this.toY = toY;
        this.x = x;
        this.panel = panel;
        this.manager = manager;
    }

    /**
     * run
     */
    @Override
    public void run() {
        setName("Animation");
        //Get the absolute positions
        int absoluteX;
        int absoluteY = panel.getAbsoluteY(toY);
        int currentY = -50;
        panel.setAnimating(true);
        while (currentY < absoluteY) {
            absoluteX = panel.getAbsoluteX(x);
            absoluteY = panel.getAbsoluteY(toY);
            //set the new position and repaint.
            panel.setNewTokenX(absoluteX);
            panel.setNewTokenY(currentY);
            panel.repaint();
            try {
                Thread.sleep(15);
            } catch (InterruptedException ex) {
                Logger.getLogger(AnimationThread.class.getName()).log(Level.SEVERE, null, ex);
            }
            currentY = currentY + 10;
        }
        panel.setAnimating(false);
        panel.activeMouseCtrl();
        manager.endAnimation(x);
    }
}
