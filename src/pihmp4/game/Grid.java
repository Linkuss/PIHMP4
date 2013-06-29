package pihmp4.game;

import java.util.LinkedList;
import java.util.List;
import pihmp4.utils.*;
import pihmp4.utils.GlobalVar.player;

/**
 *
 * @author Fabien Yerly, Jonathan Sifringer, Luis Domingues, Tiago De Deus
 */
public class Grid {

    private player grid[][];
    private player winner;
    private int LastMove=0;

    public Grid() {
        grid = new player[GlobalVar.getHeight()][GlobalVar.getWidth()];
        initGrid();
        winner = player.NOPLAYER;
    }

    /**
     *
     * @param column Indicates on which column the move will be made. Must be a
     * valid column
     * @param play Inidcates what player is playing the move
     * @return If a player wins with this move, it will return the player that
     * has won. Otherwise it will return NOPLAYER
     * @throws Exception If the column is not valid or if the player is not
     * PLAYER1 or PLAYER2, an exception will be thrown
     */
    public player play(int column, player play) throws Exception {
        if (play != player.PLAYER1 && play != player.PLAYER2) {
            throw new Exception("Not a valid player");
        }
        if (grid[0][column] == player.NOPLAYER) {
            for (int i = grid.length - 1; i >= 0; i--) {
                if (grid[i][column] == player.NOPLAYER) {
                    grid[i][column] = play;
                    LastMove=column;
                    return isGameFinished(i, column, play);
                }
            }
        } else {
            throw new Exception("Not a valid column");
        }
        return isGameFinished();
    }

    /**
     *
     * @param column Indicates what column to undo. It will remove the first
     * move in the column that it finds, stating at the top.
     */
    public void undo(int column) {
        for (int i = 0; i < grid.length; i++) {
            if (grid[i][column] != player.NOPLAYER) {
                grid[i][column] = player.NOPLAYER;
                return;
            }
        }
    }

    /**
     *
     * @return If the game is Finished, this method returns who wins. Otherwise
     * it return NOPLAYER. NOT IMPLEMENTED YET.
     */
    public player isGameFinished() {
        return winner;

    }

    /**
     *
     * @param posH Position in Heigh of the last move
     * @param posW Position in Width of the last move
     * @param whatPlayer What is the last player that played
     * @return What player won with that move, if someone won, or DRAW if there
     * is a draw, or NOPLAYER if the game if nothing won yet. You must redo this
     * method after an undo to recalculate if someone won.
     */
    private player isGameFinished(int posH, int posW, player whatPlayer) {

        winner = verifyDiag(posH, posW, whatPlayer);
        if (winner != player.NOPLAYER) {
            return winner;
        }

        winner = verifyHor(posH, posW, whatPlayer);
        if (winner != player.NOPLAYER) {
            return winner;
        }

        winner = verifyVert(posH, posW, whatPlayer);

        if (winner != player.NOPLAYER) {
            return winner;
        }


        if (allValidPlays().isEmpty()) {
            winner = player.DRAW;
        }

        return winner;
    }

    public void restart() {
        initGrid();
        winner = player.NOPLAYER;
    }

    private player verifyDiag(int posH, int posW, player whatPlayer) {
        int diag = 1;
        int i = posH + 1;
        int j = posW - 1;
        while (i >= 0 && j >= 0 && i < grid.length && j < grid[i].length) {
            if (grid[i][j] == whatPlayer) {
                diag++;
                if (diag == 4) {
                    winner = whatPlayer;
                    return winner;
                }
            } else {
                break;
            }
            i++;
            j--;
        }
        
        i = posH - 1;
        j = posW + 1;
        while (i >= 0 && j >= 0 && i < grid.length && j < grid[i].length) {
            if (grid[i][j] == whatPlayer) {
                diag++;
                if (diag == 4) {
                    winner = whatPlayer;
                    return winner;
                }
            } else {
                break;
            }
            i--;
            j++;
        }
        diag = 1;
        i = posH + 1;
        j = posW + 1;
        while (i < grid.length && j < grid[i].length) {
            if (grid[i][j] == whatPlayer) {
                diag++;
                if (diag == 4) {
                    return whatPlayer;
                }
            } else {
                break;
            }
            i++;
            j++;
        }
        
        i = posH - 1;
        j = posW - 1;
        while (i >= 0 && j >= 0) {
            if (grid[i][j] == whatPlayer) {
                diag++;
                if (diag == 4) {
                    return whatPlayer;
                }
            } else {
                break;
            }
            i--;
            j--;
        }

        return player.NOPLAYER;
    }

    private player verifyHor(int posH, int posW, player whatPlayer) {
        int hor = 1;
        int i = posH;
        int j = posW - 1;
        while (j >= 0) {
            if (grid[i][j] == whatPlayer) {
                hor++;
                if (hor == 4) {
                    winner = whatPlayer;
                    return winner;
                }
            } else {
                break;
            }
            j--;
        }

        j = posW + 1;
        while (j < grid[i].length) {
            if (grid[i][j] == whatPlayer) {
                hor++;
                if (hor == 4) {
                    return whatPlayer;
                }
            } else {
                break;
            }
            j++;
        }

        return player.NOPLAYER;
    }

    private player verifyVert(int posH, int posW, player whatPlayer) {
        int vert = 1;
        int i = posH - 1;
        int j = posW;
        while (i >= 0) {
            if (grid[i][j] == whatPlayer) {
                vert++;
                if (vert == 4) {
                    winner = whatPlayer;
                    return winner;
                }
            } else {
                break;
            }
            i--;
        }

        i = posH + 1;
        while (i < grid.length) {
            if (grid[i][j] == whatPlayer) {
                vert++;
                if (vert == 4) {
                    return whatPlayer;
                }
            } else {
                break;
            }
            i++;
        }

        return player.NOPLAYER;
    }

    /**
     *
     * @param column Indicate wich column to verify
     * @return returns true if the column can accept a move, false otherwise.
     */
    public boolean isPlayValid(int column) {
        if(column<0 || column >= grid[0].length){
            return false;
        }
        return grid[0][column] == player.NOPLAYER;
    }

    public int nextPosOfPlayIn(int column) {
        for (int i = grid.length - 1; i >= 0; i--) {
            if (grid[i][column] == player.NOPLAYER) {
                return i;
            }
        }
        return -1;
    }

    /**
     *
     * @return returns a list with all the valid columns that can accept a move.
     */
    public List<Integer> allValidPlays() {
        List<Integer> listPlays = new LinkedList<>();
        for (int i = 0; i < grid[0].length; i++) {
            if (isPlayValid(i)) {
                listPlays.add(i);
            }
        }
        return listPlays;
    }

    private void initGrid() {
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                grid[i][j] = player.NOPLAYER;
            }
        }
    }

    @Override
    public String toString() {
        String msg = "\t";
        for (int n = 0; n < grid[0].length; n++) {
            msg += n + "\t\t";
        }
        msg += "\n";
        for (int i = 0; i < grid.length; i++) {
            msg += "|\t";
            for (int j = 0; j < grid[i].length; j++) {
                switch (grid[i][j]) {
                    case NOPLAYER:
                        msg += "-\t|\t";
                        break;
                    case PLAYER1:
                        msg += "+\t|\t";
                        break;
                    case PLAYER2:
                        msg += "o\t|\t";
                        break;
                }
            }
            msg += "\n";
        }

        return msg;

    }

    public player[][] getGrid() {
        return grid.clone();
    }
    
    @Override
    public Grid clone(){
        Grid clone = new Grid();
        for(int i = 0; i < grid.length; i++){
            System.arraycopy(this.grid[i], 0, clone.grid[i], 0, grid[i].length);
        }
        clone.winner = this.winner;
        clone.LastMove = this.LastMove;
        return clone;
    }

    int getLastMove() {
        return LastMove;
    }
}
