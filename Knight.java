/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package functions;

/**
 *
 * @author Ben
 */
public class Knight implements Piece {

    private char team;
    private int row;
    private int col;

    Knight(char c, int col, int row) {
        team = c;
        this.row = row;
        this.col = col;
    }

    @Override
    public char symbol() {
        return 'N';
    }

    @Override
    public boolean canMove(int rowDest, int colDest, Piece board[][], String a) {
        return (((Math.abs(col - colDest) == 1)
                && (Math.abs(row - rowDest) == 2))
                || ((Math.abs(col - colDest) == 2)
                && (Math.abs(row - rowDest) == 1)))
                && ((team == 'W' && board[colDest][rowDest].getTeam() != 'W')
                || (team == 'B' && board[colDest][rowDest].getTeam() != 'B'));
    }

    @Override
    public char getTeam() {
        return team;
    }

    @Override
    public boolean hasMoved() {
        return false;
    }

    @Override
    public int getRow() {
        return row;
    }
    
    @Override
    public int getCol() {
        return col;
    }
}
