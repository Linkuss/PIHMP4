/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pihmp4.utils;

/**
 * This classe is used to Notify the observers and udpate the logs in the StatsView
 * @author Fabien Yerly, Jonathan Sifringer, Luis Domingues, Tiago De Deus
 */
public class NotificationUser {
    
    private String name;
    private String NbrOfWins;
    private String NbrOfPlays;
    private String NbrOfLosses;
    
    public NotificationUser(String n, String nbrOfW, String nbrOfP, String nbrOfL){
        name=n;
        NbrOfPlays = nbrOfP;
        NbrOfWins = nbrOfW;
        NbrOfLosses = nbrOfL;
    }

    public String getName() {
        return name;
    }

    public String getNbrOfWins() {
        return NbrOfWins;
    }

    public String getNbrOfPlays() {
        return NbrOfPlays;
    }

    public String getNbrOfLosses() {
        return NbrOfLosses;
    }
    
}
