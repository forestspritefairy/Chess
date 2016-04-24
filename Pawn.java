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
public class Pawn implements Piece {

    private int row;
    private int col;
    private char team;
    private boolean hasMoved = false;

    Pawn(char c, int col, int row) {
        team = c;
        this.row = row;
        this.col = col;
    }

    Pawn(char c, int col, int row, boolean x) {
        team = c;
        this.row = row;
        this.col = col;
        hasMoved = x;
    }

    @Override
    public char symbol() {
        return 'P';
    }

    @Override
    public boolean canMove(int rowDest, int colDest, Piece board[][], String lastMove) {
        if (enpassant(rowDest, colDest, lastMove, board)) {
            return true;
        }
        if (team == 'W') {
            return (rowDest > row) && (((Math.abs(this.col - colDest) == 0)
                    && ((!hasMoved && rowDest < this.row + 3)
                    || (rowDest < this.row + 2))
                    && board[colDest][rowDest].symbol() == ' ')
                    || ((rowDest == row + 1)
                    && (Math.abs(col - colDest) == 1)
                    && (board[colDest][rowDest].symbol() != ' ')
                    && (board[colDest][rowDest].getTeam() != 'W')));
        } else {
            return (rowDest < row) && (((Math.abs(this.col - colDest) == 0)
                    && ((!hasMoved && rowDest > this.row - 3)
                    || (rowDest > this.row - 2))
                    && board[colDest][rowDest].symbol() == ' ')
                    || ((rowDest == row - 1)
                    && (Math.abs(col - colDest) == 1)
                    && (board[colDest][rowDest].symbol() != ' ')
                    && (board[colDest][rowDest].getTeam() != 'B')));
        }
    }

    boolean enpassant(int rowDest, int colDest, String lastMove, Piece board[][]) {
        String loc = lastMove.substring(0, lastMove.indexOf(" "));
        String dest = lastMove.substring(lastMove.indexOf(" ") + 1);
        int num1 = loc.charAt(1) - 49;
        int let2 = dest.charAt(0) - 65;
        int num2 = dest.charAt(1) - 49;
        if (team == 'W') {
            if (board[let2][num2].symbol() == 'P' && num2 == num1 - 2
                    && (row + 1 == rowDest && (col + 1 == colDest || col - 1 == colDest))
                    && (colDest == let2 && rowDest == num2)) {
                board[let2][num2] = new EmptySquare();
                return true;
            }
        } else {
            if (board[let2][num2].symbol() == 'P' && num2 == num1 + 2
                    && (row - 1 == rowDest && (col + 1 == colDest || col - 1 == colDest))
                    && (colDest == let2 && rowDest == num2)) {
                board[let2][num2] = new EmptySquare();
                return true;
            }
        }
        return false;
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
