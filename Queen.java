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
public class Queen implements Piece {

    private char team;
    private int row;
    private int col;

    Queen(char c, int col, int row) {
        team = c;
        this.row = row;
        this.col = col;
    }

    @Override
    public char symbol() {
        return 'Q';
    }

    @Override
    public boolean canMove(int rowDest, int colDest, Piece board[][], String a) {
        return ((this.row == rowDest && this.col != colDest)
                || (this.col == colDest && this.row != rowDest)
                || Math.abs(this.row - rowDest) == Math.abs(this.col - colDest))
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
        if(board[col][row].getTeam() == board[colDest][rowDest].getTeam()){
            return false;
        }
        if (row != rowDest && col != colDest) {
            if ((rowDest - row < 0 && colDest - col > 0)
                    || (rowDest - row > 0 && colDest - col < 0)) {
                for (int i = rowMin, j = colMax; i <= rowMax; i++, j--) {
                    if ((board[j][i].symbol() != ' ') && (col != j || row != i)
                            && (colDest != j || rowDest != i)) {
                        return false;
                    }
                }
            } else if (Math.abs(col - colDest) == Math.abs(row - rowDest)) {
                //row++ , col++
                for (int i = rowMin, j = colMin; i <= rowMax; i++, j++) {
                    if (board[j][i].symbol() != ' ' && (col != j || row != i)
                            && (colDest != j || rowDest != i)) {
                        return false;
                    }
                }
            } else {
                return false;
            }
        } else {
            for (int i = rowMin; i <= rowMax; i++) {
                for (int j = colMin; j <= colMax; j++) {
                    if (board[j][i].symbol() != ' ') {
                        if (col != j || row != i) {
                            if (colDest != j || rowDest != i) {
                                return false;
                            }
                        }
                    }
                }
            }
        }
        return true;
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
