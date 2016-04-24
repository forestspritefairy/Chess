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
public class King implements Piece {

    private char team;
    private int row;
    private int col;
    private boolean hasMoved;

    King(char c, int col, int row) {
        this.team = c;
        this.col = col;
        this.row = row;
    }
    
    King(char c, int col, int row, boolean x) {
        this.team = c;
        this.row = row;
        this.col = col;
        this.hasMoved = x;
    }

    @Override
    public char symbol() {
        return 'K';
    }

    @Override
    public boolean canMove(int rowDest, int colDest, Piece board[][], String a) {
        return (Math.abs(this.row - rowDest) < 2
                && Math.abs(colDest - this.col) < 2
                && Math.abs(this.row - rowDest) + Math.abs(this.col - colDest) != 0)
                && ((team == 'W' && board[colDest][rowDest].getTeam() != 'W')
                || (team == 'B' && board[colDest][rowDest].getTeam() != 'B'));

    }

    @Override
    public char getTeam() {
        return team;
    }

    @Override
    public boolean hasMoved() {
        return hasMoved;
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
