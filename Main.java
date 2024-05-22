
/*
 * Todo:
 * - 2 player
 * - Shooting, hitting, missing, etc.
 * - Check win/lose
 * - GUI
 * - AI
 */
import java.util.Scanner;
public class Main {
    public String[][] createGame() {
        String[][] gameboard = new String[11][11];
        // water is "-" and ship is "+" and hit is "x" and miss is "o"
        for (int i = 0; i < gameboard.length; i++) {
            for (int j = 0; j < gameboard[i].length; j++) {
                gameboard[i][j] = "-";
            }
        }
        gameboard[0][0] = " ";
        for (int a = 1; a < gameboard.length; a++) {
            gameboard[a][0] = String.valueOf((char) (a + 65 - 1));
        }
        for (int b = 1; b < gameboard[0].length; b++) {
            gameboard[0][b] = String.valueOf(b);
        }
        return gameboard;
    }
    /*
    public void placeOnebyOne(char row, int col, String[][] gameboard) {
        int i = (int) row - 65;
        int j = col - 1;
        gameboard[i][j] = "+";
        
    }
    */
    public void placeShip(char row, int col, int length, String orientation, String[][] gameboard) {
        orientation = orientation.substring(0, 1).toUpperCase() + orientation.substring(1).toLowerCase();
        int i = (int) row - 65 + 1;
        int j = col;
        if (orientation.equals("Horizontal")) {
            for (int a = 0; a < length; a++) {
                gameboard[i][j + a] = "+";
            }
        } 
        else if (orientation.equals("Vertical")) {
            for (int a = 0; a < length; a++) {
                gameboard[i + a][j] = "+";
            }
        }
        
    }

    public void printGameBoard(String[][] gameboard) {
        for (int i = 0; i < gameboard.length; i++) {
            for (int j = 0; j < gameboard[i].length; j++) {
                System.out.print(gameboard[i][j] + " ");
            }
            System.out.println();
        }
    }

    public boolean checkCoordinateValidity(char row, int col, int length, String orientation, String[][] gameboard) {
        // Check if its occupied by a ship, doesn't go out of bounds
        int i = (int) row - 65 + 1;
        int j = col;
        if (orientation.equals("Horizontal")) {
            if (j + length - 1 >= gameboard[0].length) {
                return false;
            }
            for (int a = 0; a < length; a++) {
                if (gameboard[i][j + a] == "+") {
                    return false;
                }
            }
        } 
        else if (orientation.equals("Vertical")) {
            if (i + length - 1 >= gameboard.length) {
                return false;
            }
            for (int a = 0; a < length; a++) {
                if (gameboard[i + a][j] == "+") {
                    return false;
                }
            }
        }
        return true;
    }

    public void getInput(String[][] gb1) {
        Main obj = new Main();
        Scanner myObj = new Scanner(System.in);
        int[] lengths = { 5, 4, 3, 3, 2 };
        for (int z = 0; z < lengths.length; z++) {
            System.out.println("Enter Letter coordinates for Ship " + (z+1) + " (Ship Length " + lengths[z] + ")");
            String letter = myObj.nextLine();
            char char1 = letter.charAt(0);
            System.out.println("Enter Number coordinates for Ship " + (z+1) + " (Ship Length " + lengths[z] + ")");
            int number = myObj.nextInt();
            myObj.nextLine();
            System.out.println("Enter Orientation for Ship (Horizontal or Vertical)");
            String orientation2 = myObj.nextLine();
            if (obj.checkCoordinateValidity(char1, number, lengths[z], orientation2, gb1) == false) {
                System.out.println("Invalid coordinates, try again.");
                z--;
            } 
            else {
                obj.placeShip(char1, number, lengths[z], orientation2, gb1);
                obj.printGameBoard(gb1);
            }
        }
    }
    public static void main(String[] args) {
        Main player1 = new Main();
        String[][] gameBoard1 = player1.createGame();
        player1.printGameBoard(gameBoard1);
        player1.getInput(gameBoard1);

        Main player2 = new Main();
        String[][] gameBoard2 = player2.createGame();
        player2.printGameBoard(gameBoard2);
        player2.getInput(gameBoard2); 
        }
}
/*
 * Ship lengths: 5, 4, 3, 3, 2
 */