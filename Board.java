package functions;

import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

/**
 * Project: Chess Class: Board.java Date Created: July 19 2015 Platform:
 * NetBeans IDE 8.0.2, Java JDK 1.8.0_40
 *
 * @version 1.0.0
 * @author Benjamin Clark
 * @see functions.Bishop
 * @see functions.Chess
 * @see functions.EmptySquare
 * @see functions.King
 * @see functions.Knight
 * @see functions.Pawn
 * @see functions.Piece
 * @see functions.Queen
 * @see functions.Rook
 */
public class Board {

    /**
     * 2d array for pieces. [col][row]
     */
    public Piece[][] board = new Piece[8][8];

    /**
     * variable that keeps track of whose turn it is. True = white / False =
     * black
     */
    boolean turn;

    /**
     * variable that keeps track of what the last move was. Format: "A2 A4"
     */
    String lastMove;

    /**
     * variables that keep track of the kings location
     */
    String whiteKing;
    String blackKing;

    /**
     * variable that tells whether you are in check or not True = check / False
     * = not in check
     */
    boolean check;

    /**
     * variable that tells if you have lost or not True = lost / False = haven't
     * lost
     */
    boolean lose;

    /**
     * variable that counts how many moves have been made with a pawn moved or
     * piece taken if 100 stalemate.
     */
    int counter;

    /**
     * variable that keeps track of the amount of pieces on the board. It is
     * used to see if piece has been taken.
     */
    int numberOfPieces;

    /**
     * Map that keeps a record of past moves in a String form. String connected
     * with number if number ever reaches three stalemate occurs
     */
    Map<String, Integer> gameHistory = new TreeMap<>();

    /**
     * default constructor initializes variables
     */
    public Board() {
        newGame();
        turn = true;
        lastMove = "A1 A2";
        whiteKing = "E1";
        blackKing = "E8";
        check = false;
        counter = 0;
        numberOfPieces = 32;
        gameHistory.put(printBoard(), 1);
        lose = false;
    }

    /**
     * method that sets the initial board by filling with pieces. Calls
     * setBack()
     */
    void newGame() {
        setBack(0, 1, 'W');
        for (int i = 2; i < 6; i++) {
            for (int j = 0; j < 8; j++) {
                board[j][i] = new EmptySquare();
            }
        }
        setBack(7, 6, 'B');
    }

    /**
     * Sets either the white or black teams pieces
     *
     * @param Piecerow row location of non-pawn pieces
     * @param pawnRow row location of Pawns
     * @param team team of Pieces
     */
    void setBack(int Piecerow, int pawnRow, char team) {
        board[0][Piecerow] = new Rook(team, 0, Piecerow);
        board[1][Piecerow] = new Knight(team, 1, Piecerow);
        board[2][Piecerow] = new Bishop(team, 2, Piecerow);
        board[3][Piecerow] = new Queen(team, 3, Piecerow);
        board[4][Piecerow] = new King(team, 4, Piecerow);
        board[5][Piecerow] = new Bishop(team, 5, Piecerow);
        board[6][Piecerow] = new Knight(team, 6, Piecerow);
        board[7][Piecerow] = new Rook(team, 7, Piecerow);

        for (int i = 0; i < 8; i++) {
            board[i][pawnRow] = new Pawn(team, i, pawnRow);

        }
    }

    /**
     * Takes String from user and processes it into either a move or an error
     * calls checkCastle(), castle(), changeTurn(), printBoard(), checkTurn(),
     * getTeam(), canMove(), clone(), symbol(), check(), checkForEndGame()
     *
     * @param inputString String from user that contains instructions
     * @return copy of board and a message to user as a String
     */
    public boolean processCommand(String inputString) {
        try {
//            if (inputString.contains("o")) { //Checks for castleing
//                if (inputString.length() == 2) {
//                    if (checkCastle(true) && castle(true)) {
//                        changeTurn();
//                        return true;
//                        //return printBoard();
//                    }
//                } else if (inputString.length() == 3) {
//                    if (checkCastle(false) && castle(false)) {
//                        changeTurn();
//                        return true;
//                        //return printBoard();
//                    }
//                } else {
//                    return false;
//                    //return "Cant Castle";
//                }
//            }
            String loc = inputString.substring(0, inputString.indexOf(" "));
            String dest = inputString.substring(inputString.indexOf(" ") + 1);
//            int let1 = loc.charAt(0) - 65;
//            int num1 = loc.charAt(1) - 49;
            int let1 = loc.charAt(0);
            int num1 = loc.charAt(1);
            if (!checkTurn(board[let1][num1].getTeam())) {//Checks to see if your piece
                return false;
                //return "Not your Piece Try again.";
            }
//            int let2 = dest.charAt(0) - 65;
//            int num2 = dest.charAt(1) - 49;
            int let2 = dest.charAt(0);
            int num2 = dest.charAt(1);
            if (let2 > 7 || let2 < 0 || num2 > 7 || num2 < 0) { //Makes sure it inside the board
                return false;
                //return "Out of Board can't move there!";
            }
            //Checks if it can move and moves it
            if (board[let1][num1].canMove(num2, let2, board, lastMove)) {
                Piece oldBoard[][] = clone(board);//Piece was able to Move
                movePiece(board[let1][num1].symbol(), dest, let1, let2, num1, num2);
                if (check && check()) {
                    board = clone(oldBoard);
                    return false;
                    //return "Still in check try again";
                }
                changeTurn();
                lastMove = inputString;

                String message = checkForEndGame(let2, num2);
                if (lose) {
                    return true;
                    //return message + printBoard();
                } else {
                    return true;
                    //return message + printTurn() + printBoard();
                }
            }
            return false;
            //return "Unable to move there";
        } catch (Exception e) {
            return false;
            //return "Error Invalid Input";
        }
    }

    /**
     * 
     * @param col
     * @param row
     * @return 
     */
    private String checkForEndGame(int col, int row) {
        { //Stalemate by three rep. 
            String key = printBoard();
            if (gameHistory.containsKey(key)) {
                gameHistory.put(key, gameHistory.get(key) + 1);
            } else {
                gameHistory.put(key, 1);
            }
            if (gameHistory.containsValue(3)) {
                lose = true;
                return "Drawn by three fold repition\n";
            }
            if (board[col][row].symbol() == 'P' || checkNumberOfPieces()) {
                gameHistory.clear();
            }
        }

        { //Stalemate Insufficent mating material
            if (checkMateImpossible()) {
                lose = true;
                return "Insufficent mating material\n";
            }
        }

        { //Stalemate by repition
            counter++;
            if (board[col][row].symbol() == 'P' || checkNumberOfPieces()) {
                counter = 0;
            } else if (counter == 100) {
                lose = true;
                return "Drawn By repition\n";
            }
        }

        { //Checks to see if you are in check or checkmated
            if (check()) {
                check = true;
                if (checkMate()) {
                    lose = true;
                    return "You Lose\n";
                } else {
                    return "Check\n";
                }
            }
            check = false;
        }

        { //Stalemate by no legal moves
            if (legalMoveCheck()) {
                lose = true;
                return "Stalemate\n";
            }
            return "";
        }
    }

    private void movePiece(char type, String dest, int let1, int let2, int num1, int num2) {
        if (type == 'P') {
            if ((board[let2][num2].getTeam() == 'W' && num2 == 7)
                    || board[let2][num2].getTeam() == 'B' && num2 == 0) {
                Scanner console = new Scanner(System.in);
                System.out.println("What do you want you Piece to become?");
                String x = console.next();
                while (!x.equals("Queen") || !x.equals("Knight") || !x.equals("Bishop") || !x.equals("Rook")) {
                    System.out.println("Error invalid choice");
                    System.out.println("You can choose a Queen, Rook, Bishop, or a Knight");
                }
                switch (x) {
                    case "Queen":
                        board[let2][num2] = new Queen(board[let1][num1].getTeam(), let2, num2);
                        break;
                    case "Rook":
                        board[let2][num2] = new Rook(board[let1][num1].getTeam(), let2, num2);
                        break;
                    case "Bishop":
                        board[let2][num2] = new Bishop(board[let1][num1].getTeam(), let2, num2);
                        break;
                    default:
                        board[let2][num2] = new Knight(board[let1][num1].getTeam(), let2, num2);
                        break;
                }
                board[let1][num1] = new EmptySquare();
            } else {
                board[let2][num2] = new Pawn(board[let1][num1].getTeam(), let2, num2, true);
                board[let1][num1] = new EmptySquare();
            }
        } else if (type == 'R') {
            board[let2][num2] = new Rook(board[let1][num1].getTeam(), let2, num2, true);
            board[let1][num1] = new EmptySquare();
        } else if (type == 'N') {
            board[let2][num2] = new Knight(board[let1][num1].getTeam(), let2, num2);
            board[let1][num1] = new EmptySquare();
        } else if (type == 'B') {
            board[let2][num2] = new Bishop(board[let1][num1].getTeam(), let2, num2);
            board[let1][num1] = new EmptySquare();
        } else if (type == 'Q') {
            board[let2][num2] = new Queen(board[let1][num1].getTeam(), let2, num2);
            board[let1][num1] = new EmptySquare();
        } else if (type == 'K') {
            board[let2][num2] = new King(board[let1][num1].getTeam(), let2, num2, true);
            board[let1][num1] = new EmptySquare();
            if (board[let2][num2].getTeam() == 'W') {
                whiteKing = dest;
            } else {
                blackKing = dest;
            }
        }
    }

    private boolean check() {
        int let1, num1;
        if (turn) {
            let1 = whiteKing.charAt(0) - 65;
            num1 = whiteKing.charAt(1) - 49;
        } else {
            let1 = blackKing.charAt(0) - 65;
            num1 = blackKing.charAt(1) - 49;
        }
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                if ((board[col][row].getTeam() == 'B' && turn)
                        || (board[col][row].getTeam() == 'W' && !turn)) {
                    if (board[col][row].canMove(num1, let1, board, lastMove)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private boolean checkNumberOfPieces() {
        int count = 0;
        for (int col = 0; col < 8; col++) {
            for (int row = 0; row < 8; row++) {
                if (board[col][row].symbol() != ' ') {
                    count++;
                }
            }
        }
        return count != numberOfPieces;
    }

    private boolean checkMateImpossible() {
        int whiteCount = 0;
        int blackCount = 0;
        for (int col = 0; col < 8; col++) {
            for (int row = 0; row < 8; row++) {
                if (board[col][row].symbol() == 'Q'
                        || board[col][row].symbol() == 'R'
                        || board[col][row].symbol() == 'P') {
                    return false;
                }
                if (board[col][row].getTeam() == 'W') {
                    whiteCount++;
                } else if (board[col][row].getTeam() == 'B') {
                    blackCount++;
                }
            }
        }
        return whiteCount < 3 || blackCount < 3;
    }

    private boolean legalMoveCheck() {
        for (int boardCol = 0; boardCol < 8; boardCol++) {
            for (int boardRow = 0; boardRow < 8; boardRow++) {
                if ((board[boardCol][boardRow].getTeam() == 'W' && turn)
                        || (board[boardCol][boardRow].getTeam() == 'B' && !turn)) {
                    for (int movePieceCol = 0; movePieceCol < 8; movePieceCol++) {
                        for (int movePieceRow = 0; movePieceRow < 8; movePieceRow++) {
                            if (board[boardCol][boardRow].canMove(movePieceRow, movePieceCol, board, lastMove)) {
                                if (board[boardCol][boardRow].symbol() == 'K') {
                                    String oldLocation;
                                    if (turn) {
                                        oldLocation = whiteKing;
                                        whiteKing = Character.toString((char) (movePieceCol + 65)) + (movePieceRow + 1) + "";
                                    } else {
                                        oldLocation = blackKing;
                                        blackKing = Character.toString((char) (movePieceCol + 65)) + (movePieceRow + 1) + "";
                                    }
                                    if (check()) {
                                        if (turn) {
                                            whiteKing = oldLocation;
                                        } else {
                                            blackKing = oldLocation;
                                        }
                                    } else {
                                        return false;
                                    }
                                } else {
                                    return false;
                                }
                            }
                        }
                    }
                }
            }
        }
        return true;
    }

    private boolean checkMate() {
        Piece oldBoard[][] = clone(board);
        String dest;
        if (turn) {
            dest = whiteKing;
        } else {
            dest = blackKing;
        }
        for (int boardCol = 0; boardCol < 8; boardCol++) {
            for (int boardRow = 0; boardRow < 8; boardRow++) {
                if ((board[boardCol][boardRow].getTeam() == 'W' && turn)
                        || (board[boardCol][boardRow].getTeam() == 'B' && !turn)) {
                    for (int movePieceCol = 0; movePieceCol < 8; movePieceCol++) {
                        for (int movePieceRow = 0; movePieceRow < 8; movePieceRow++) {
                            if (board[boardCol][boardRow].canMove(movePieceRow, movePieceCol, board, lastMove)) {
                                movePiece(board[boardCol][boardRow].symbol(), dest, boardCol, movePieceCol, boardRow, movePieceRow);
                                if (board[movePieceCol][movePieceRow].symbol() == 'K') {
                                    if (turn) {
                                        whiteKing = Character.toString((char) (movePieceCol + 65)) + (movePieceRow + 1) + "";
                                    } else {
                                        blackKing = Character.toString((char) (movePieceCol + 65)) + (movePieceRow + 1) + "";
                                    }
                                }
                                if (check()) {
                                    if (board[movePieceCol][movePieceRow].symbol() == 'K') {
                                        if (turn) {
                                            whiteKing = (Character.toString((char) (boardCol + 65))) + boardRow + 1 + "";
                                        } else {
                                            blackKing = (Character.toString((char) (boardCol + 65))) + boardRow + 1 + "";
                                        }
                                    }
                                    board = clone(oldBoard);
                                } else {
                                    board = clone(oldBoard);
                                    return false;
                                }
                            }
                        }

                    }
                }
            }
        }
        return true;
    }

    private boolean checkCastle(boolean kingQueen) {
        if (turn) {
            if (kingQueen) {//White King side castle
                whiteKing = "F1";
                if (check()) {
                    return false;
                }
                whiteKing = "G1";
                if (check()) {
                    return false;
                }
            } else { //White Queen side castle
                whiteKing = "D1";
                if (check()) {
                    return false;
                }
                whiteKing = "C1";
                if (check()) {
                    return false;
                }
            }
        } else {
            if (kingQueen) { // Black King side castle
                blackKing = "F8";
                if (check()) {
                    return false;
                }
                blackKing = "G8";
                if (check()) {
                    return false;
                }
            } else { // Black Queen side castle
                blackKing = "D8";
                if (check()) {
                    return false;
                }
                blackKing = "C8";
                if (check()) {
                    return false;
                }
            }
        }
        return true;
    }

    private boolean castle(boolean kingQueen) {
        if (kingQueen) {
            //King side castle
            if (turn) {
                //Whites Move
                if ((board[4][0].symbol() == 'K' && !board[4][0].hasMoved()
                        && board[4][0].getTeam() == 'W') && (board[7][0].symbol() == 'R' && !board[7][0].hasMoved()
                        && board[7][0].getTeam() == 'W') && (board[5][0].symbol() == ' ' && board[6][0].symbol() == ' ')) {

                    board[6][0] = new King('W', 6, 0, true);
                    board[5][0] = new Rook('W', 5, 0, true);
                    board[4][0] = new EmptySquare();
                    board[7][0] = new EmptySquare();
                    return true;
                }

            } else {
                //Blacks Move
                if ((board[4][7].symbol() == 'K' && !board[4][7].hasMoved()
                        && board[4][7].getTeam() == 'B') && (board[7][7].symbol() == 'R' && !board[7][7].hasMoved()
                        && board[7][7].getTeam() == 'B') && (board[5][7].symbol() == ' ' && board[6][7].symbol() == ' ')) {
                    board[6][7] = new King('B', 6, 7, true);
                    board[5][7] = new Rook('B', 5, 7, true);
                    board[4][7] = new EmptySquare();
                    board[7][7] = new EmptySquare();
                    return true;
                }
            }
        } else {
            //Queen side castle
            if (turn) {
                //Whites Move
                if ((board[4][0].symbol() == 'K' && !board[4][0].hasMoved()
                        && board[4][0].getTeam() == 'W')
                        && (board[0][0].symbol() == 'R' && !board[0][0].hasMoved()
                        && board[0][0].getTeam() == 'W')
                        && (board[1][0].symbol() == ' ' && board[2][0].symbol() == ' '
                        && board[3][0].symbol() == ' ')) {
                    board[2][0] = new King('W', 6, 0, true);
                    board[3][0] = new Rook('W', 5, 0, true);
                    board[0][0] = new EmptySquare();
                    board[1][0] = new EmptySquare();
                    board[4][0] = new EmptySquare();
                    return true;
                }
            } else {
                //Blacks Move
                if ((board[4][7].symbol() == 'K' && !board[4][7].hasMoved()
                        && board[4][7].getTeam() == 'B')
                        && (board[0][7].symbol() == 'R' && !board[0][7].hasMoved()
                        && board[0][7].getTeam() == 'B')
                        && (board[1][7].symbol() == ' ' && board[2][7].symbol() == ' '
                        && board[3][7].symbol() == ' ')) {
                    board[2][7] = new King('B', 6, 7, true);
                    board[3][7] = new Rook('B', 5, 7, true);
                    board[0][7] = new EmptySquare();
                    board[1][7] = new EmptySquare();
                    board[4][7] = new EmptySquare();
                    return true;
                }
            }
        }
        return false;
    }

    String printTurn() {
        if (!turn) {
            return "Black's Move\n";
        } else {
            return "White's Move\n";
        }
    }

    public String printBoard() {
        String x = "";
        x += " +---------------------------------------+\n";
        for (int i = 14; i >= 0; i--) {
            for (int j = 0; j < 8; j++) {
                if (i % 2 == 0) {
                    if (j == 0) {
                        x += i / 2 + 1;
                    }
                    x += "|";
                    x += getTile(j, i / 2);
                } else {
                    if (j == 0) {
                        x += " |----";
                    } else {
                        x += "-----";
                    }
                }
            }
            x += "|\n";
        }
        x += " +---------------------------------------+\n";
        x += "   A    B    C    D    E    F    G    H\n";
        return x;
    }

    private String getTile(int i, int j) {
        char a = board[i][j].getTeam();
        char b = board[i][j].symbol();
        return " " + a + b + " ";
    }

    private void changeTurn() {
        turn = !turn;
    }

    private boolean checkTurn(char team) {
        if (team == 'W') {
            return turn;
        } else if (team == 'B') {
            return !turn;
        } else {
            return false;
        }
    }

    public Piece[][] clone(Piece[][] oldBoard) {
        Piece[][] newBoard = new Piece[8][8];
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                if (oldBoard[col][row].symbol() == 'B') {
                    newBoard[col][row] = new Bishop(oldBoard[col][row].getTeam(), col, row);
                } else if (oldBoard[col][row].symbol() == 'K') {
                    newBoard[col][row] = new King(oldBoard[col][row].getTeam(), col, row, oldBoard[col][row].hasMoved());
                } else if (oldBoard[col][row].symbol() == 'N') {
                    newBoard[col][row] = new Knight(oldBoard[col][row].getTeam(), col, row);
                } else if (oldBoard[col][row].symbol() == 'P') {
                    newBoard[col][row] = new Pawn(oldBoard[col][row].getTeam(), col, row, oldBoard[col][row].hasMoved());
                } else if (oldBoard[col][row].symbol() == 'Q') {
                    newBoard[col][row] = new Queen(oldBoard[col][row].getTeam(), col, row);
                } else if (oldBoard[col][row].symbol() == 'R') {
                    newBoard[col][row] = new Rook(oldBoard[col][row].getTeam(), col, row, oldBoard[col][row].hasMoved());
                } else {
                    newBoard[col][row] = new EmptySquare();
                }
            }
        }
        return newBoard;
    }

}
