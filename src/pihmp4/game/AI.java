/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pihmp4.game;

import java.util.List;
import java.util.Random;
import pihmp4.utils.GlobalVar;
import pihmp4.utils.GlobalVar.player;

/**
 *
 * @author "Fabien Yerly, Jonathan Sifringer, Luis Domingues, Tiago De Deus"
 */
public class AI {

    Random r;
    Boolean firstTwo=true;
    int itrM=0;

    public AI() {
        r = new Random();
    }
    
    public void resetAI(){
        itrM=0;
    }

    /**
     *
     * @param isPlayer1 Must be true if the player that will do the next move is
     * the player 1
     * @param crtGrid An instance of the current state of the game
     * @return returns the column number of the next best move to do
     */
    public int nextMoveToDo(boolean isPlayer1, Grid crtGrid) {
        if(itrM<2){
            int m = crtGrid.getLastMove();
            int x=0;
            do{
                if(r.nextBoolean()){
                    x=m+1;
                }else{
                    x=m-1;
                }
            }while(!crtGrid.isPlayValid(x));
            itrM++;
            return x;
        }
        Move move = new Move(0);
        try {
            int newWin = nextMoveToDo(isPlayer1, crtGrid, GlobalVar.getLevel(), move);
            Move tempMove = new Move(move.getMove());
            int nextLoose = nextMoveToDo(!isPlayer1, crtGrid, GlobalVar.getLevel(), move);
            if (Math.abs(nextLoose) != GlobalVar.getLevel() || newWin == GlobalVar.getLevel()) {
                move.setMove(tempMove.getMove());
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return move.getMove();
    }

    /**
     *
     * @param player1 Must be true if the player that will do the next move is
     * the player 1
     * @param crtGrid An instance of the current state of the game
     * @param level Level of reccursion max, when it reaches 0, te recursion
     * will stop
     * @param callBack This var will contain the next good move at the end of
     * the method
     * @return returns a number that indicates who will win for a given move,
     * used internally
     * @throws Exception Throws a exception if a bad coloumn is calculate
     * internaly, should never appen
     */
    private int nextMoveToDo(boolean player1, Grid crtGrid, int level, Move callBack) throws Exception {

        if (crtGrid.isGameFinished() == player.PLAYER1) {
            return 1 * (level + 1);
        } else if (crtGrid.isGameFinished() == player.PLAYER2) {
            return -1 * (level + 1);
        } else if (crtGrid.isGameFinished() == player.DRAW) {
            return 0;
        }

        if (level == 0) {
            return 0;
        }
        int res = 0;
        int bestRes = 0;
        if (player1) {
            bestRes = -1;
        } else {
            bestRes = 1;
        }
        int goodMove = -1;
        boolean noGoodMove = true;


        List<Integer> moves = crtGrid.allValidPlays();
        for (Integer move : moves) {
            if (player1) {
                crtGrid.play(move, player.PLAYER1);
                res = nextMoveToDo(!player1, crtGrid, level - 1, callBack);
                crtGrid.undo(move);

                if (res > 0 && res >= bestRes || noGoodMove) {
                    if (res == bestRes && r.nextBoolean()) {
                        goodMove = move;
                        noGoodMove = false;
                        bestRes = res;
                    } else {
                        goodMove = move;
                        noGoodMove = false;
                        bestRes = res;
                    }
                }
                if (res == 0) {
                    if (goodMove == -1) {
                        goodMove = move;
                        bestRes = 0;
                    } else if (noGoodMove) {
                        if (r.nextBoolean()) {
                            goodMove = move;
                        }
                    }
                } else if (goodMove == -1) {
                    goodMove = move;
                }
            }
            if (!player1) {
                crtGrid.play(move, player.PLAYER2);
                res = nextMoveToDo(!player1, crtGrid, level - 1, callBack);
                crtGrid.undo(move);

                if (res < 0 && res <= bestRes || noGoodMove) {
                    if (res == bestRes && r.nextBoolean()) {
                        goodMove = move;
                        bestRes = res;
                        noGoodMove = false;
                    } else {
                        goodMove = move;
                        bestRes = res;
                        noGoodMove = false;
                    }
                }
                if (res == 0) {
                    if (goodMove == -1) {
                        goodMove = move;
                        bestRes = 0;
                    } else if (noGoodMove) {
                        if (r.nextBoolean()) {
                            goodMove = move;
                        }
                    }
                } else if (goodMove == -1) {
                    goodMove = move;
                }
            }

        }
        callBack.setMove(goodMove);

        return bestRes;
    }

    private class Move {

        private int value;

        /**
         *
         * @param init Sets the default value of the move
         */
        public Move(int init) {
            value = init;
        }

        /**
         *
         * @param move sets the move
         */
        public void setMove(int move) {
            value = move;
        }

        /**
         *
         * @return returns a move
         */
        public int getMove() {
            return value;
        }
    }
}
