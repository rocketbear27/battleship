
/*
 * Todo:
  - catch errors (Typing H5 instead of H)
  - sink ship (red background) + scoreboard
    * Ship class that has information on coordinates, length, orientation, and boolean array of hits
    * if entire boolean array is true, ship is sunk
    * main class changes: after user inputs for 5 ships, you have to make a ship object for EACH of them (10 obj in total)
    * after each hit, change the boolean array of hits to true, then check if than ship has boolean array thats all true.
    * display ship sunken score 
    * brown background of sunken ships
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
    public String[][] createAttackingArray(int player) {
        String[][] attackingArray = new String[11][11];
        for (int i = 0; i < attackingArray.length; i++) {
            for (int j = 0; j < attackingArray[i].length; j++) {
                attackingArray[i][j] = "-";
            }
        }
        String redBackground = "\u001B[41m";
        String blueBackground = "\u001B[44m";
        String reset = "\u001B[0m";
        String character = " ";
        if (player == 1) {
            attackingArray[0][0] = redBackground + character + reset;
        }
        else {
            attackingArray[0][0] = blueBackground + character + reset;
        }
        for (int a = 1; a < attackingArray.length; a++) {
            attackingArray[a][0] = String.valueOf((char) (a + 65 - 1));
        }
        for (int b = 1; b < attackingArray[0].length; b++) {
            attackingArray[0][b] = String.valueOf(b);
        }
        return attackingArray;
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

    public boolean attack(String[][] gameBoard1, String[][] attackBoard, String[][] gameboard2, String player) {
        Scanner myObj = new Scanner(System.in);
        while (true) {
            try {
                System.out.println("Your ships: ");
                printGameBoard(gameBoard1);
                System.out.println("Your attacks: ");
                printGameBoard(attackBoard);
    
                System.out.println("Team " + player + " - Enter Letter coordinates for Attack");
                String letter = myObj.nextLine();
                if (letter.isEmpty() || letter.length() > 1 || !Character.isLetter(letter.charAt(0))) {
                    throw new IllegalArgumentException("Invalid input, please enter a valid letter coordinate.");
                }
                char char1 = Character.toUpperCase(letter.charAt(0));
    
                System.out.println("Team " + player + " - Enter Number coordinates for Attack");
                if (!myObj.hasNextInt()) {
                    throw new IllegalArgumentException("Invalid input, please enter a valid number coordinate.");
                }
                int number = myObj.nextInt();
                myObj.nextLine();
    
                int i = (int) char1 - 65 + 1;
                int j = number;
                if (i >= gameboard2.length || j >= gameboard2[0].length || i < 1 || j < 1) {
                    System.out.println("Invalid coordinates, try again.");
                }
    
                if (!attackBoard[i][j].equals("-")) {
                    System.out.println("You have already guessed this spot. Try again.");
                }
    
                if (gameboard2[i][j].equals("+")) {
                    System.out.println("Hit");
                    attackBoard[i][j] = "X";
                    gameboard2[i][j] = "X";
                    return true;
                } else {
                    System.out.println("Miss");
                    attackBoard[i][j] = "O";
                    gameboard2[i][j] = "O";
                    return false;
                }
            } 
            catch (Exception e) {
                System.out.println("Please try again.");
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
            if (letter.isEmpty()) {
                System.out.println("Invalid input, please enter a letter coordinate.");
                z--;
                continue;
            }
            char char1 = letter.charAt(0);
            System.out.println("Enter Number coordinates for Ship " + (z+1) + " (Ship Length " + lengths[z] + ")");
            if (!myObj.hasNextInt()) {
                System.out.println("Invalid input, please enter a number coordinate.");
                myObj.next(); // clear the invalid input
                z--;
                continue;
            }
            int number = myObj.nextInt();
            myObj.nextLine(); // consume the newline character
            System.out.println("Enter Orientation for Ship (Horizontal(H) or Vertical(V))");
            String orientation2 = myObj.nextLine();
            if (orientation2.isEmpty() || (!orientation2.equalsIgnoreCase("H") && !orientation2.equalsIgnoreCase("V"))) {
                System.out.println("Invalid input, please enter a valid orientation (H or V).");
                z--;
                continue;
            }
            if (!obj.checkCoordinateValidity(char1, number, lengths[z], orientation2, gb1)) {
                System.out.println("Invalid coordinates, try again.");
                z--;
            } else {
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

    public static void delay(int milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } 
        catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
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

        String[][] attackBoard1 = player1.createAttackingArray(1);
        String[][] attackBoard2 = player2.createAttackingArray(2);

        clearScreen();

        while (!(ifPlayerOneHasWon(gameBoard2)) && !(ifPlayerTwoHasWon(gameBoard1))) {
            //play
            /*
             * When p1 shoots, reference p1's attacking array and see if they have attempted to shoot there already
             * Set a boolean to let p1 continue shooting until they miss
             * If its a minus, then set everything to O and switch turns
             */
            boolean player1Turn = true;
            while (player1Turn) {
                player1Turn = player1.attack(gameBoard1, attackBoard1, gameBoard2, "Red");
                if (ifPlayerOneHasWon(gameBoard2)) {
                    break;
                }
                if (!player1Turn) {
                    clearScreen();
                    System.out.println("Switch Turns");
                    delay(3000); // 5-second delay before switching turns
                    clearScreen();
                }
            }
            while (!player1Turn) {
                player1Turn = !(player2.attack(gameBoard2, attackBoard2, gameBoard1, "Blue"));
                if (ifPlayerTwoHasWon(gameBoard1)) {
                    break;
                }
                if (player1Turn) {
                    clearScreen();
                    System.out.println("Switch Turns");
                    delay(3000); // 5-second delay before switching turns
                    clearScreen();
                }
            }
        }
        if (ifPlayerOneHasWon(gameBoard2)) {
            clearScreen();
            System.out.println("Team Red Has Won");
        }
        if (ifPlayerTwoHasWon(gameBoard1)) {
            clearScreen();
            System.out.println("Team Blue Has Won");
        }
    }
}
/*
 * Ship lengths: 5, 4, 3, 3, 2
 */