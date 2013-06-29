package pihmp4.ihm;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import javax.swing.JPanel;
import pihmp4.controllers.Manager;
import pihmp4.controllers.PlayerListener;
import pihmp4.game.Grid;
import pihmp4.utils.GlobalVar;
import pihmp4.utils.GlobalVar.player;

/**
 *
 * @author "Fabien Yerly, Jonathan Sifringer, Luis Domingues, Tiago De Deus"
 */
public class GamePanel extends JPanel {

    private int radius;
    private int nbXHole;
    private int nbYHole;
    private PlayerListener listener;
    private int newTokenX;
    private int newTokenY;
    private boolean animating;
    private Grid grid;
    private player playerAnime;
    private Manager manager;

    /**
     * Constructor. Create the main game grid.
     */
    public GamePanel(Grid grid, Manager manager) {
        super();
        nbXHole = GlobalVar.getWidth();
        nbYHole = GlobalVar.getHeight();
        this.grid = grid;
        listener = new PlayerListener(manager);
        addMouseListener(listener);
        animating = false;
        newTokenX = 0;
        newTokenY = 0;
        playerAnime = player.NOPLAYER;
        this.manager = manager;
    }

    /**
     * Create the main game panel.
     *
     * @param g Graphic context
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        player[][] gameGrid = grid.getGrid();
        //Get the radius, and set de column with for the controller
        radius = getRadius();
        listener.setColumnWidth(getWidth() / nbXHole);
        Graphics2D g2 = (Graphics2D) g;
        g2.drawImage(GlobalVar.getBackground(), 0, 0, getWidth(), getHeight(), this);
        BufferedImage image = new BufferedImage(getWidth(), getHeight(),
                BufferedImage.TYPE_INT_ARGB);
        g2 = image.createGraphics();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        //Draw all the wholes
        for (int i = 0; i < nbXHole; i++) {
            for (int j = 0; j < nbYHole; j++) {
                drawWhole(g2, (getWidth() / nbXHole) * i, (getHeight() / nbYHole) * j, player.NOPLAYER);
            }
        }
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_IN));
        //Draw the tokens
        for (int i = 0; i < nbXHole; i++) {
            for (int j = 0; j < nbYHole; j++) {
                if (gameGrid[j][i] != player.NOPLAYER) {
                    drawWhole(g2, (getWidth() / nbXHole) * i, (getHeight() / nbYHole) * j, gameGrid[j][i]);
                }
            }
        }
        if (isAnimating()) {
            drawCurrentToken(g2);
        } else {
        }
        g.drawImage(image, 0, 0, null);
    }

    /**
     * Draw the wholes. Draw an image for each player, or black for empty wholes
     *
     * @param g2 Graphic context
     * @param x Center point in X
     * @param y Center point in Y
     * @param player The owner of the token. To draw the good image.
     */
    private void drawWhole(Graphics2D g2, int x, int y, player player) {
        if (player == player.PLAYER2) {
            g2.drawImage(GlobalVar.getPlay2Img(), x, y, radius, radius, this);
        } else if (player == player.PLAYER1) {
            g2.drawImage(GlobalVar.getPlay1Img(), x, y, radius, radius, this);
        } else {
            g2.setPaint(Color.BLACK);
            g2.fillOval(x, y, radius, radius);
        }
    }

    /**
     * Get the radius of token. Can be change because window resize.
     *
     * @return The radius of the token.
     */
    private int getRadius() {
        //remove 5 because of the margin.
        int radius1 = getWidth() / nbXHole - 5;
        int radius2 = getHeight() / nbYHole - 5;
        return Math.min(radius1, radius2);
    }

    /**
     * Draw the current token. Method for the animation.
     *
     * @param g2 Current graphic context
     */
    private void drawCurrentToken(Graphics2D g2) {
        drawWhole(g2, getNewTokenX(), getNewTokenY(), playerAnime);
    }

    /**
     * Run the animation. Create and start a thread.
     *
     * @param x Destination in X
     * @param y Destination in Y
     * @param player Player who has played
     */
    public void runAnime(int x, int y, player player) {
        playerAnime = player;
        removeMouseListener(listener);
        Thread animate = new AnimationThread(manager, x, y, this);
        animate.start();
    }

    /**
     * Active the mouse controller. When the ihm is animating, the controller is
     * desable.
     */
    protected void activeMouseCtrl() {
        addMouseListener(listener);
    }

    /**
     * Get absolute x for a column number.
     *
     * @param x Number of the column.
     * @return the absolute position who draw for a number of column.
     */
    protected int getAbsoluteX(int x) {
        return (getWidth() / nbXHole) * x;
    }

    /**
     * Get absolute x for a line number.
     *
     * @param x Number of the line.
     * @return the absolute position who draw for a number of line.
     */
    protected int getAbsoluteY(int y) {
        return (getHeight() / nbYHole) * y;
    }

    /**
     * Get the position x of the token animating.
     *
     * @return position x of the animate token.
     */
    public synchronized int getNewTokenX() {
        return newTokenX;
    }

    /**
     * Set the position x of the token animating.
     *
     * @param newTokenX Position x of the token.
     */
    public synchronized void setNewTokenX(int newTokenX) {
        this.newTokenX = newTokenX;
    }

    /**
     * Get the position y of the token animating.
     *
     * @return position y of the animate token.
     */
    public synchronized int getNewTokenY() {
        return newTokenY;
    }

    /**
     * Set the position y of the token animating.
     *
     * @param newTokenX Position y of the token.
     */
    public synchronized void setNewTokenY(int newTokenY) {
        this.newTokenY = newTokenY;
    }

    /**
     * Get ge animating state.
     *
     * @return true if ihm is animationg.
     */
    public synchronized boolean isAnimating() {
        return animating;
    }

    /**
     * Get ge animating state.
     *
     * @param isAnimating true if ihm is animationg.
     */
    public synchronized void setAnimating(boolean isAnimating) {
        this.animating = isAnimating;
    }
}