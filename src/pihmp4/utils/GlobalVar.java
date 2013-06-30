package pihmp4.utils;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.URL;
import java.net.URISyntaxException;
import java.net.UnknownHostException;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.swing.Icon;
import javax.swing.ImageIcon;

/**
 *
 * @author Fabien Yerly, Jonathan Sifringer, Luis Domingues, Tiago De Deus
 * This class contains all global static final variables and other variables.
 */
public class GlobalVar {

    private static final int WIDTH = 7;
    private static final int HEIGHT = 6;
    private static int level = 5;
    private static final int PORT = 1337;
    private static ResourceBundle ress = ResourceBundle.getBundle("pihmp4.local.IHM");
    private static String ip = "0.0.0.0.0";
    private static final Color COLOR1 = Color.RED;
    private static final Color COLOR2 = Color.YELLOW;
    private static AudioInputStream gameMusic;
    private static AudioInputStream winMusic;
    private static AudioInputStream looseMusic;
    private static boolean isMute = false;
    private static String themePath = "/ressources/Default/";
    private static String currentLang = ress.getLocale().getDisplayLanguage();
    private static String currentTheme = "Default";
    private static BufferedImage play1Coin;
    private static BufferedImage play2Coin;
    private static BufferedImage background;
    private static String helpURL;

    public static URL getHelpURL() {
        try {
            return Class.class.getResource("/help/" + GlobalVar.GetRessBundle().getString("helpURL"));
        } catch (Exception ex) {
            Logger.getLogger(GlobalVar.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public static ImageIcon getMutedImage() {
        return new ImageIcon(Class.class.getResource("/ressources/Default/mute.png"));
    }

    public static enum player {PLAYER1, PLAYER2, NOPLAYER, DRAW};

    private static String getThemePath() {
        return themePath;
    }

    public static void setTheme(String t) {
        currentTheme=t;
        themePath = "/ressources/"+currentTheme+"/";
        winMusic=null;
        gameMusic=null;
        looseMusic=null;
        play1Coin = null;
        play2Coin = null;
        background = null;

    }
    
    public static String getCurrentTheme(){
        return currentTheme;
    }

    public static boolean isIsMute() {
        return isMute;
    }

    public static void setIsMute(boolean isMute) {
        GlobalVar.isMute = isMute;
    }

    public static String getIp() {
        try {
            ip = InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException ex) {
            Logger.getLogger(GlobalVar.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ip;
    }

    public static int getWidth() {
        return WIDTH;
    }

    public static AudioInputStream getGameMusic() {
        
        try {
            gameMusic = AudioSystem.getAudioInputStream(Class.class.getResource(getThemePath()+"main.wav"));

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return gameMusic;
    }

    public static AudioInputStream getWinMusic() {
        try {
            winMusic = AudioSystem.getAudioInputStream(Class.class.getResource(getThemePath()+"win.wav"));

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return winMusic;
    }
    
    public static AudioInputStream getLooseMusic() {
        try {
            looseMusic = AudioSystem.getAudioInputStream(Class.class.getResource(getThemePath()+"loose.wav"));

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return looseMusic;
    }

    public static int getHeight() {
        return HEIGHT;
    }

    public static int getLevel() {
        return level;
    }

    public static void setLevel(int lvl) {
        level = lvl;
    }

    public static int getPort() {
        return PORT;
    }

    public static ResourceBundle GetRessBundle() {
        return ress;
    }

    public static void setLocal(String language) {
        currentLang=language;
        if(language.equals("français")){
            setLocalBund("fr", "CH");
        }else if(language.equals("english")){
            setLocalBund("en", "GB");
        }else if(language.equals("deutsch")){
            setLocalBund("de", "CH");
        }
    }
    
    public static String getCurentLanguage(){
        return currentLang;
    }
    
    public static String[] getAllLanguage(){
        return new String[]{"english", "français", "deutsch"};
    }
    
    
    private static void setLocalBund(String language, String country) {
        ress = ResourceBundle.getBundle("pihmp4.local.IHM", new Locale(language, country));
    }

    public static Color getColor(player pl) {
        switch (pl) {
            case PLAYER1:
                return COLOR1;
            case PLAYER2:
                return COLOR2;
            default:
                return Color.BLACK;
        }

    }
    
    public static BufferedImage getPlay1Img(){
        if(play1Coin == null){
            try{
                InputStream in = Class.class.getResource(getThemePath()+"play1.png").openStream(); 
                play1Coin = ImageIO.read(in);
            }catch(Exception e){
                System.out.println(e.getMessage());
            }
        }
        
        return play1Coin;
    }
    
    public static BufferedImage getPlay2Img(){
        if(play2Coin == null){
            try{
                InputStream in = Class.class.getResource(getThemePath()+"play2.png").openStream(); 
                play2Coin = ImageIO.read(in);
            }catch(Exception e){
                System.out.println(e.getMessage());
            }
        }
        
        return play2Coin;
    }
    
    public static BufferedImage getBackground(){
        if(background == null){
            try{
                InputStream in = Class.class.getResource(getThemePath()+"back.png").openStream(); 
                background = ImageIO.read(in);
            }catch(Exception e){
                System.out.println(e.getMessage());
            }
        }
        
        return background;
    }
    
    public static ImageIcon getBanImage(){
        return new ImageIcon(Class.class.getResource(getThemePath()+"banniere.jpg"));
    }
    
    public static String[] getAllthemes(){
        String[] t = new String[] {"Default", "Mario", "Zelda", "Sonic"};
        return t;
    }
    
    public static ImageIcon getUnmutedImage(){
        return new ImageIcon(Class.class.getResource("/ressources/Default/sound.png"));
    }
}
