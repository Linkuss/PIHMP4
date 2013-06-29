/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pihmp4.game;

import java.util.Observable;
import pihmp4.utils.NotificationUser;

/**
 *
 * @author Fabien Yerly, Jonathan Sifringer, Luis Domingues, Tiago De Deus
 */
public class User extends Observable{

    private String name;
    private boolean isPlayer1;
    private int NbrOfWins;
    private int NbrOfPlays;
    private int NbrOfLosses;

    public User() {
    }

    public User(String name, boolean isPlayer1, int NbrOfWins, int NbrOfLosses, int NbrOfPlays) {
        this.name = name;
        this.isPlayer1 = isPlayer1;
        this.NbrOfWins = NbrOfWins;
        this.NbrOfLosses = NbrOfLosses;
        this.NbrOfPlays = NbrOfPlays;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        notif();
    }

    public boolean isIsPlayer1() {
        return isPlayer1;
    }

    public void setIsPlayer1(boolean isPlayer1) {
        this.isPlayer1 = isPlayer1;
    }

    public int getNbrOfWins() {
        return NbrOfWins;
    }

    public void incNbrOfPlays() {
        NbrOfPlays++;
        notif();
    }

    public int getNbrOfPlays() {
        return NbrOfPlays;
    }


    public int getNbrOfLosses() {
        return NbrOfLosses;
    }


    public void incNbrOfLosses() {
        NbrOfLosses++;
        notif();
    }

    public void incNbrOfWins() {
        NbrOfWins++;
        notif();
    }

    private void notif() {
        setChanged();
        NotificationUser n = new NotificationUser(name, ""+NbrOfWins, ""+NbrOfPlays, ""+NbrOfLosses);
        notifyObservers(n);
    }
    
    
    
    
}
