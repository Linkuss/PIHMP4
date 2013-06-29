package pihmp4.utils;

/**
 * This classe is used to Notify the observers and udpate the logs in the LogView
 * @author Fabien Yerly, Jonathan Sifringer, Luis Domingues, Tiago De Deus
 */
public class NotificationLog {
    
    private String prevUser;
    private String nextUser;
    private int column;
    private boolean winner;

    /**
     * Constructor.
     * @param prevUser User who has played.
     * @param nextUser Next user to play.
     * @param column Column played
     * @param winner Winner. True if prev player has win.
     */
    public NotificationLog(String prevUser, String nextUser, int column, boolean winner) {
        this.prevUser = prevUser;
        this.nextUser = nextUser;
        this.column = column;
        this.winner = winner;
    }

    public String getPrevUser() {
        return prevUser;
    }

    public String getNextUser() {
        return nextUser;
    }

    public int getColumn() {
        return column;
    }

    public boolean isWinner() {
        return winner;
    }

    public void setWinner(boolean winner) {
        this.winner = winner;
    }
    
}
