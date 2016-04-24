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
public class EmptySquare implements Piece {

    private char team = ' ';

    @Override
    public char symbol() {
        return ' ';
    }

    @Override
    public boolean canMove(int rowDest, int colDest, Piece board[][], String a) {
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
        return 0;
    }

    @Override
    public int getCol() {
        return 0;
    }
}
