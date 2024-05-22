
/*
 * Todo:
 * - 2 extra gameboards (should be created after placing ships) for seeing
 *   where you attacked and missed. make a seperate method for c
 * - Shooting, hitting, missing, etc.
 * - Check win/lose
 * - GUI
 * - AI
 */
import java.util.Scanner;
public class Main {
    public String[][] createShipArray(int player) {
        String[][] gameboard = new String[11][11];
        // water is "-" and ship is "+" and hit is "x" and miss is "o"
        for (int i = 0; i < gameboard.length; i++) {
            for (int j = 0; j < gameboard[i].length; j++) {
                gameboard[i][j] = "-";
            }
        }
        String redBackground = "\u001B[41m";
        String blueBackground = "\u001B[44m";
        String reset = "\u001B[0m";
        String character = " ";
        if (player == 1) {
            gameboard[0][0] = redBackground + character + reset;
        }
        else {
            gameboard[0][0] = blueBackground + character + reset;
        }
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
        orientation = orientation.toUpperCase();
        row = Character.toUpperCase(row);
        int i = (int) row - 65 + 1;
        int j = col;
        if (orientation.equals("H")) {
            for (int a = 0; a < length; a++) {
                gameboard[i][j + a] = "+";
            }
        } 
        else if (orientation.equals("V")) {
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
        orientation = orientation.toUpperCase();
        row = Character.toUpperCase(row);
        int i = (int) row - 65 + 1;
        int j = col;
        if (orientation.equals("H")) {
            if (j + length - 1 >= gameboard[0].length) {
                return false;
            }
            for (int a = 0; a < length; a++) {
                if (gameboard[i][j + a] == "+") {
                    return false;
                }
            }
        } 
        else if (orientation.equals("V")) {
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
            System.out.println("Enter Orientation for Ship (Horizontal(H) or Vertical(V))");
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

    public static boolean ifPlayerOneHasWon(String[][] gameBoard2) {
        //return true if someone has won, else return false
        for (int i = 0; i < gameBoard2.length; i++) {
            for (int j = 0; j < gameBoard2[i].length; j++) {
                // Check if there are any ships left
                if (gameBoard2[i][j].equals("+")) {
                    return false; // If a ship is found, the game is not won yet
                }
            }
        }
        return true; // No ships left, the game is won 
    }
    public static boolean ifPlayerTwoHasWon(String[][] gameBoard1) {
        //return true if someone has won, else return false
        for (int i = 0; i < gameBoard1.length; i++) {
            for (int j = 0; j < gameBoard1[i].length; j++) {
                // Check if there are any ships left
                if (gameBoard1[i][j].equals("+")) {
                    return false; // If a ship is found, the game is not won yet
                }
            }
        }
        return true; // No ships left, the game is won 
    }

    public static void clearScreen() {  
        System.out.print("\033[H\033[2J");  
        System.out.flush();  
    }  
    public static void main(String[] args) {
        //Main obj = new Main();

        Main player1 = new Main();
        String[][] gameBoard1 = player1.createShipArray(1);
        player1.printGameBoard(gameBoard1);
        player1.getInput(gameBoard1);

        clearScreen();

        Main player2 = new Main();
        String[][] gameBoard2 = player2.createShipArray(2);
        player2.printGameBoard(gameBoard2);
        player2.getInput(gameBoard2); 

        clearScreen();

        while (!(ifPlayerOneHasWon(gameBoard2)) && !(ifPlayerTwoHasWon(gameBoard1))) {
            //play
            /*
             * When p1 shoots, reference p1's attacking array and see if they have attempted to shoot there already
             * Then, reference p2's gameboard and see if its a "+" or "-" 
             * If the spot contains a plus, then p1's attacking array must display an X and p2's ship array must display an X too
             * Set a boolean to let p1 continue shooting until they miss
             * If its a minus, then set everything to O and switch turns
             */
        }
        if (ifPlayerOneHasWon(gameBoard2)) {
            clearScreen();
            System.out.println("Player One Has One");
        }
        if (ifPlayerTwoHasWon(gameBoard1)) {
            clearScreen();
            System.out.println("Player Two Has One");
        }
    }
}
/*
 * Ship lengths: 5, 4, 3, 3, 2
 */