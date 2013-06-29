/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pihmp4.utils;

/**
 *
 *  @author Fabien Yerly, Jonathan Sifringer, Luis Domingues, Tiago De Deus
 */
public class StringValidator {

    /**
     * Validate ip adresse structure
     * @param ip the ip the user has typed
     * @return 
     */
    public static boolean ipValidator(String ip) {
        String[] tokens = ip.split("\\.");
        if (tokens.length != 4) {
            return false;
        }
        for (String str : tokens) {
            int i = Integer.parseInt(str);
            if ((i < 0) || (i > 255)) {
                return false;
            }
        }
        return true;
    }
}
