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
public class Rook implements Piece {

    private char team;
    private int row;
    private int col;
    private boolean hasMoved;

    Rook(char c, int col, int row) {
        this.team = c;
        this.row = row;
        this.col = col;
    }
    
    Rook(char c, int col, int row, boolean x) {
        this.team = c;
        this.row = row;
        this.col = col;
        this.hasMoved = x;
    }

    @Override
    public char symbol() {
        return 'R';
    }

    @Override
    public boolean canMove(int rowDest, int colDest, Piece board[][], String a) {
        return ((this.row != rowDest && this.col == colDest)
                || (this.col != colDest && this.row == rowDest))
                && checkForPiece(rowDest, colDest, board);
    }

    @Override
    public char getTeam() {
        return team;
    }

    public boolean checkForPiece(int rowDest, int colDest, Piece[][] board) {
        int rowMin, rowMax, colMin, colMax;
        if (row >= rowDest) {
            rowMax = row;
            rowMin = rowDest;
        } else {
            rowMax = rowDest;
            rowMin = row;
        }
        if (col >= colDest) {
            colMax = col;
            colMin = colDest;
        } else {
            colMax = colDest;
            colMin = col;
        }
        for (int i = rowMin; i <= rowMax; i++) {
            for (int j = colMin; j <= colMax; j++) {
                if (board[j][i].symbol() != ' ') {
                    if (col != j || row != i) {
                        if (colDest != j || rowDest != i || board[col][row].symbol() != board[colDest][rowDest].symbol()) {
                            return false;
                        }
                    }
                }
            }
        }
        return true;
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
