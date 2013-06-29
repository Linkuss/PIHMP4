/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pihmp4.controllers;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.Observable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.midi.Sequencer;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingWorker;
import pihmp4.game.Grid;
import pihmp4.game.AI;
import pihmp4.game.User;
import pihmp4.ihm.GameView;
import pihmp4.ihm.MainView;
import pihmp4.ihm.NetworkView;
import pihmp4.ihm.OptionView;
import pihmp4.ihm.SinglePlayerView;
import pihmp4.ihm.LogView;
import pihmp4.utils.GlobalVar;
import pihmp4.network.*;
import pihmp4.utils.NotificationLog;
import pihmp4.ihm.HelpView;

/**
 *
 * @author Fabien Yerly, Jonathan Sifringer, Luis Domingues, Tiago De Deus
 */
public class Manager extends Observable {

    //declare global variables
    static Manager instance;
    boolean player1Plays;
    boolean netPlay = false;
    boolean waitPlay1 = false;
    boolean wait = false;
    NetServer server;
    NetClient client;
    GameView gv;
    MainView mv;
    NetworkView nv;
    OptionView ov;
    SinglePlayerView spv;
    HelpView hv;
    LogView sv;
    Grid grid;
    User player1;
    User player2;
    AI ai;
    boolean aiActive;
    boolean isGameOver;
    Clip clip;
    Sequencer sequencer;

    public Manager() {
        init();
    }

    /**
     * Initalize classes and variables
     */
    private void init() {

        //init MainView
        mv = new MainView(this);
        mv.setVisible(true);

        //init models
        player1 = new User();
        grid = new Grid();
        //player1.setName("player1");
        player2 = new User();
        ai = new AI();

        gv = new GameView(grid, this, player1, player2);
        //init variables
        player1Plays = true;
        netPlay = false;
        aiActive = false;
        isGameOver = false;
    }

    /**
     * ********************
     * SETTERS AND GETTERS ******************
     */
    public void setPlayer2Name(String name) {
        player2.setName(name);
    }

    public void setPlayer1Name(String name) {
        player1.setName(name);
    }

    public String getPlayer2Name() {
        return player2.getName();
    }

    public String getPlayer1Name() {
        return player1.getName();
    }

    public void setaiActive(boolean active) {
        aiActive = active;
        player2.setName("AI");
    }

    /**
     * *****************************
     * SET DIFFICULTY LEVELS 
     ****************************
     */
    public void setEasyLevel() {
        GlobalVar.setLevel(2);
    }

    public void setNormalLevel() {
        GlobalVar.setLevel(4);
    }

    public void setDifficultLevel() {
        GlobalVar.setLevel(7);
    }

    /**
     * **************************
     * MANAGE GAME ***********************
     */
    public boolean play(int column) {
        GlobalVar.player winner = null;
        String prevPlayer;
        String nextPlayer;
        boolean win = false;
        if (player1Plays) {
            prevPlayer = player1.getName();
            nextPlayer = player2.getName();
        } else {
            prevPlayer = player2.getName();
            nextPlayer = player1.getName();
        }
        try {
            if (grid.isPlayValid(column)) {
                if (player1Plays) {
                    winner = grid.play(column, GlobalVar.player.PLAYER1);
                    if(aiActive){
                        changeWait();
                    }
                    player1.incNbrOfPlays();
                } else {
                    winner = grid.play(column, GlobalVar.player.PLAYER2);
                    if(aiActive){
                        changeWait();
                    }
                    player2.incNbrOfPlays();
                }
            } else {
                //play not valide do nothing.
                return true;
            }
        } catch (Exception e) {
            System.out.println("error during play " + e);
        }
        if (netPlay) {
            if (player1Plays == !waitPlay1) {
                SendNetPlay send;
                if (client != null) {
                    send = new SendNetPlay(client, column);
                } else {
                    send = new SendNetPlay(server, column);
                }
                send.execute();
            }
        }
        gv.repaint();
        //a player wins or draw
        if (winner != GlobalVar.player.NOPLAYER) {
            win = true;
            isGameOver = true;
            gameOver(grid.isGameFinished());
            return true;
        }

        //change next player
        player1Plays = !player1Plays;

        NotificationLog not = new NotificationLog(prevPlayer, nextPlayer, column, win);
        setChanged();
        notifyObservers(not);
        return false;
    }

    /**
     * Actions to do when game is finished (draw or a player wins)
     *
     * @param winner This parameter defines who wins
     */
    private void gameOver(GlobalVar.player winner) {
        switch (winner) {
            case PLAYER1:
                if (!GlobalVar.isIsMute()) {
                    if (netPlay && waitPlay1) {
                        stopMusic();
                        playLooseMusic();
                    } else if (netPlay && !waitPlay1) {
                        stopMusic();
                        playWinMusic();
                    } else {
                        stopMusic();
                        playWinMusic();
                    }
                }
                openFinishedPane(player1.getName());
                player1.incNbrOfWins();
                player2.incNbrOfLosses();
                break;
            case PLAYER2:
                if (!GlobalVar.isIsMute()) {
                    if (netPlay && !waitPlay1) {
                        stopMusic();
                        playLooseMusic();
                    } else if (netPlay && waitPlay1) {
                        stopMusic();
                        playWinMusic();
                    } else if (aiActive) {
                        stopMusic();
                        playLooseMusic();
                    } else {
                        stopMusic();
                        playWinMusic();
                    }
                }
                openFinishedPane(player2.getName());
                //update players
                player2.incNbrOfWins();
                player1.incNbrOfLosses();
                break;
            case DRAW:
                if (!GlobalVar.isIsMute()) {
                    stopMusic();
                    playLooseMusic();
                }
                openFinishedPane(GlobalVar.GetRessBundle().getString("draw"));
                //update players, if draw it is a loss
                player1.incNbrOfLosses();
                player2.incNbrOfLosses();
                break;

        }

    }

    /**
     * Players chooses a column to play, this is for the animation to be done
     * before we update the real grid.
     *
     * @param col
     */
    public void wantsToPlay(int col) {
        if (wait) {
            return;
        }
        GlobalVar.player plr;
        if (grid.isPlayValid(col)) {
            if (player1Plays) {
                plr = GlobalVar.player.PLAYER1;
            } else {
                plr = GlobalVar.player.PLAYER2;
            }
            gv.runAnime(col, grid.nextPosOfPlayIn(col), plr);
        }
    }

    /**
     * ****************************************
     * MANAGE VIEWS *************************************
     */
    /**
     * Show dialog to replay game or go back to MainView
     *
     * @param playerName
     */
    private void openFinishedPane(String playerName) {
        Object selectedVal = JOptionPane.showConfirmDialog(gv, playerName + " " + GlobalVar.GetRessBundle().getString("win") + ". " + GlobalVar.GetRessBundle().getString("playagain") + "?", GlobalVar.GetRessBundle().getString("result"), JOptionPane.YES_NO_OPTION);
        gv.repaint();
        if (selectedVal == JOptionPane.YES_OPTION) {
            resetGame();
            if (netPlay && waitPlay1) {
                waitNetPlay();
            }
            if (!GlobalVar.isIsMute()) {
                stopMusic();
                startMusic();
            }
        } else {
            returnToMainView(gv);
        }
    }

    /**
     * Returns to mainView and closes other window
     *
     * @param commingWindows
     */
    public void returnToMainView(JFrame commingWindows) {

        if (commingWindows == gv) {
            stopMusic();
        }
        //close windows from which we are coming from
        commingWindows.dispose();

        grid.restart();
        mv = new MainView(this);
        mv.setVisible(true);
    }

    public void openSinglePlayer() {
        mv.dispose();
        spv = new SinglePlayerView(this);
        spv.setVisible(true);
    }

    public void openOptionView() {
        mv.dispose();
        ov = new OptionView(this);
        ov.setVisible(true);
    }

    public void openNetworkView() {
        mv.dispose();
        nv = new NetworkView(this, getPlayer1Name());
        nv.setVisible(true);
    }

    public void openGameView() {
        isGameOver = false;
        gv = new GameView(grid, this, player1, player2);
        setChanged();
        notifyObservers(new NotificationLog(null, player1.getName(), 0, false));
        resetGame();
        gv.setVisible(true);

        if (!GlobalVar.isIsMute()) {
            startMusic();
        }
    }

    public void resetGame() {
        isGameOver = false;
        player1Plays = true;
        wait=false;
        if(netPlay && waitPlay1){
            wait=true;
        }
        ai.resetAI();
        grid.restart();
    }

    public void openHelp() {
        hv = new HelpView(this);
        hv.setVisible(true);
    }

    public void closeSinglePlayer() {
        spv.dispose();
    }

    public void closeNetworkView() {
        nv.setVisible(false);
    }

    /**
     * Play for real when animation is finished, AI plays if he is active
     * (single player)
     *
     * @param x column played
     */
    public void endAnimation(int x) {
        if (play(x)) {
            return;
        }
        if (aiActive && !player1Plays && !isGameOver) {
            int col = ai.nextMoveToDo(false, grid.clone());
            gv.runAnime(col, grid.nextPosOfPlayIn(col), GlobalVar.player.PLAYER2);

        } else if (netPlay && (waitPlay1 == player1Plays)) {
            waitNetPlay();
        }
    }

    /**
     * ***************
     * MANAGE NETWORK ***************
     */
    /**
     * Client connects to server
     *
     * @param ip ip of the server
     * @param name
     * @return
     */
    public boolean connectClient(String ip, String name) {
        client = new NetClient();
        boolean connected = false;
        try {
            client.connectToServer(ip);
            connected = true;
            client.sendName(name);
            player1.setName(client.getName());
        } catch (Exception ex) {
            return false;
        }
        player2.setName(name);
        return connected;
    }

    /**
     * Waiting for client to connect
     *
     * @param name
     * @return true if connected and false if not connected
     */
    public boolean waitConnectionClient(String name) {
        if (server == null) {
            server = new NetServer();
        }
        boolean connected = false;
        try {
            server.waitingConnect();
            connected = true;
            server.sendName(name);
            player2.setName(server.getName());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        player1.setName(name);
        return connected;

    }

    /**
     * Wait other player over the net for his move. Creates a thread that will
     * wait for the other player.
     */
    public void waitNetPlay() {
        wait = true;
        GetNetPlay get;
        if (client != null) {
            get = new GetNetPlay(client, this);
        } else {
            get = new GetNetPlay(server, this);
        }
        get.execute();
    }

    /**
     * ****************
     * MANAGE MUSIC **************
     */
    private void startMusic() {
        try {
            AudioFormat f = GlobalVar.getGameMusic().getFormat();
            DataLine.Info info = new DataLine.Info(Clip.class, f);
            clip = (Clip) AudioSystem.getLine(info);
            clip.open(GlobalVar.getGameMusic());
            clip.loop(Clip.LOOP_CONTINUOUSLY);
            clip.start();

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private void stopMusic() {
        try {
            clip.stop();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * different music played at the end of a game to the winner.
     */
    private void playWinMusic() {
        try {
            AudioFormat f = GlobalVar.getWinMusic().getFormat();
            DataLine.Info info = new DataLine.Info(Clip.class, f);
            clip = (Clip) AudioSystem.getLine(info);
            clip.open(GlobalVar.getWinMusic());
            clip.start();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Music played at the end of a game when player looses
     */
    private void playLooseMusic() {
        try {
            AudioFormat f = GlobalVar.getLooseMusic().getFormat();
            DataLine.Info info = new DataLine.Info(Clip.class, f);
            clip = (Clip) AudioSystem.getLine(info);
            clip.open(GlobalVar.getLooseMusic());
            clip.start();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void exitGame() {
        System.exit(0);
    }

    public void changeWait() {
        wait = !wait;
    }

    public void setWhosWaiting(boolean w) {
        waitPlay1 = w;
    }

    public void setNetActive() {
        netPlay = true;
    }

    private class GetNetPlay extends SwingWorker {

        NetMother net;
        int play;
        Manager ma;

        public GetNetPlay(NetMother net, Manager m) {
            this.net = net;
            ma = m;
        }

        @Override
        protected Object doInBackground() throws Exception {
            play = net.getMessage();
            return null;
        }

        @Override
        protected void done() {
            ma.changeWait();
            wantsToPlay(play);
        }
    }

    /**
     * SwingWorker created to sends what the player plays to other computer
     */
    private class SendNetPlay extends SwingWorker {

        NetMother net;
        int play;

        public SendNetPlay(NetMother net, int play) {
            this.net = net;
            this.play = play;
        }

        @Override
        protected Object doInBackground() throws Exception {
            net.sendMessage(play);
            return null;
        }

        @Override
        protected void done() {
        }
    }
}
