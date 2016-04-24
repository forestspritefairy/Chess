package functions;

/**
 *
 * @author Ben
 * @author Polina
 */
public class Bishop implements Piece {

    private final char team;
    private final int row;
    private final int col;

    Bishop(char c, int col, int row) {
        team = c;
        this.row = row;
        this.col = col;
    }

    @Override
    public char symbol() {
        return 'B';
    }

    @Override
    public boolean canMove(int rowDest, int colDest, Piece board[][], String a) {
        return (Math.abs(this.row - rowDest) == Math.abs(this.col - colDest))
                && checkForPiece(rowDest, colDest, board);
    }

    @Override
    public char getTeam() {
        return team;
    }

    public boolean checkForPiece(int rowDest, int colDest, Piece board[][]) {
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
        return true;
    }

    @Override
    public boolean hasMoved() {
        return false;
    }

    @Override
    public int getRow() {
        return this.row;
    }

    @Override
    public int getCol() {
        return this.col;
    }
}
