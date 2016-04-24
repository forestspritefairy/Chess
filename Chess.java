/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package functions;

import java.util.Scanner;

/**
 *
 * @author Ben
 */
public class Chess {
    
    public static void main(String[] args) {
        Board game = new Board();
        System.out.println(game.printBoard());
        Scanner console = new Scanner(System.in);
        while(!game.lose){
            boolean y = game.processCommand(console.nextLine());
            System.out.println(y);
        }
    }
}
