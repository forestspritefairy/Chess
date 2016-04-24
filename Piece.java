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
public interface Piece {

    char symbol();

    boolean canMove(int row, int col, Piece board[][], String lastMove);

    public char getTeam();
    public int getRow();
    public int getCol();
    public boolean hasMoved();
}
