import java.util.ArrayList;
import java.util.Scanner;

public class Battleship {
    private Scanner scanner = new Scanner(System.in);
    private static final String BROWN_BACKGROUND = "\u001B[43m";
    private static final String RESET = "\u001B[0m";

    public void placeShip(Player player) {
        int[] lengths = {5, 4, 3, 3, 2};
        for (int z = 0; z < lengths.length; z++) {
            boolean validInput = false;
            while (!validInput) {
                try {
                    System.out.println("Enter Letter coordinates for Ship " + (z + 1) + " (Ship Length " + lengths[z] + ")");
                    String letter = scanner.nextLine();
                    if (letter.isEmpty()) {
                        System.out.println("Invalid input, please enter a letter coordinate.");
                        continue;
                    }
                    char char1 = letter.charAt(0);
                    System.out.println("Enter Number coordinates for Ship " + (z + 1) + " (Ship Length " + lengths[z] + ")");
                    if (!scanner.hasNextInt()) {
                        System.out.println("Invalid input, please enter a number coordinate.");
                        scanner.next(); // clear the invalid input
                        continue;
                    }
                    int number = scanner.nextInt();
                    scanner.nextLine(); // consume the newline character
                    System.out.println("Enter Orientation for Ship (Horizontal(H) or Vertical(V))");
                    String orientation2 = scanner.nextLine();
                    if (orientation2.isEmpty() || (!orientation2.equalsIgnoreCase("H") && !orientation2.equalsIgnoreCase("V"))) {
                        System.out.println("Invalid input, please enter a valid orientation (H or V).");
                        continue;
                    }
                    if (!checkCoordinateValidity(char1, number, lengths[z], orientation2, player.getGameBoard())) {
                        System.out.println("Invalid coordinates, try again.");
                    } else {
                        placeShipOnBoard(char1, number, lengths[z], orientation2, player.getGameBoard());
                        player.getShips().add(new Ship(char1, number, lengths[z], orientation2));
                        printGameBoard(player.getGameBoard());
                        validInput = true;
                    }
                } 
                catch (Exception e) {
                    System.out.println("Invalid input, please try again.");
                    scanner.nextLine(); // Clear invalid input
                }
            }
        }

        clearScreen();
        System.out.println("Switch turns");
        delay(3000);
    }

    private void placeShipOnBoard(char row, int col, int length, String orientation, String[][] gameboard) {
        orientation = orientation.toUpperCase();
        row = Character.toUpperCase(row);
        int i = (int) row - 65 + 1;
        int j = col;
        if (orientation.equals("H")) {
            for (int a = 0; a < length; a++) {
                gameboard[i][j + a] = "+";
            }
        } else if (orientation.equals("V")) {
            for (int a = 0; a < length; a++) {
                gameboard[i + a][j] = "+";
            }
        }
    }

    private boolean checkCoordinateValidity(char row, int col, int length, String orientation, String[][] gameboard) {
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
        } else if (orientation.equals("V")) {
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

    public boolean attack(Player attacker, Player defender) {
        String[][] attackBoard = attacker.getAttackBoard();
        String[][] gameBoard2 = defender.getGameBoard();
        ArrayList<Ship> opponentShips = defender.getShips();
        String player = attacker.getName();

        while (true) {
            try {
                System.out.println("Your ships: ");
                printGameBoard(attacker.getGameBoard());
                System.out.println("Your attacks: ");
                printGameBoard(attackBoard);

                System.out.println("Team " + player + " - Enter Letter coordinates for Attack");
                String letter = scanner.nextLine();
                if (letter.isEmpty() || letter.length() > 1 || !Character.isLetter(letter.charAt(0))) {
                    System.out.println("Invalid input, please enter a valid letter coordinate.");
                    continue;
                }
                char char1 = Character.toUpperCase(letter.charAt(0));

                System.out.println("Team " + player + " - Enter Number coordinates for Attack");
                if (!scanner.hasNextInt()) {
                    System.out.println("Invalid input, please enter a valid number coordinate.");
                    scanner.next(); // clear the invalid input
                    continue;
                }
                int number = scanner.nextInt();
                scanner.nextLine();

                int i = (int) char1 - 65 + 1;
                int j = number;
                if (i >= gameBoard2.length || j >= gameBoard2[0].length || i < 1 || j < 1) {
                    System.out.println("Invalid coordinates, try again.");
                    continue;
                }

                if (!attackBoard[i][j].equals("-")) {
                    System.out.println("You have already guessed this spot. Try again.");
                    continue;
                }

                boolean hit = false;
                boolean sunk = false;
                Ship sunkenShip = null;
                for (Ship ship : opponentShips) {
                    if (ship.registerHit(char1, number)) {
                        hit = true;
                        if (ship.isSunk()) {
                            sunk = true;
                            sunkenShip = ship;
                            updateBoardForSunkenShip(ship, attackBoard, gameBoard2);
                        }
                        break;
                    }
                }

                if (hit) {
                    System.out.println("Hit");
                    attackBoard[i][j] = "X";
                    gameBoard2[i][j] = "X";
                    if (sunk && sunkenShip != null) {
                        System.out.println("Ship Sunk");
                        updateBoardForSunkenShip(sunkenShip, attackBoard, gameBoard2);
                    }
                    return true;
                } else {
                    System.out.println("Miss");
                    attackBoard[i][j] = "O";
                    gameBoard2[i][j] = "O";
                    return false;
                }
            } 
            catch (Exception e) {
                System.out.println("Invalid input, please try again.");
                scanner.nextLine(); // Clear invalid input
            }
        }
    }

    public static void printScoreboard(Player one, Player two) {
        int playerOneLeft = 5;
        int playerTwoLeft = 5;
    
        for (Ship ship : one.getShips()) {
            if (ship.isSunk()) {
                playerOneLeft--;
            }
        }
    
        for (Ship ship : two.getShips()) {
            if (ship.isSunk()) {
                playerTwoLeft--;
            }
        }
    
        System.out.println("Scoreboard:");
        System.out.println(one.getName() + " Ships Left - " + playerOneLeft);
        System.out.println(two.getName() + " Ships Left - " + playerTwoLeft);
    }

    private void updateBoardForSunkenShip(Ship ship, String[][] attackBoard, String[][] gameBoard2) {
        ArrayList<int[]> coordinates = ship.getCoordinates();
        for (int[] coord : coordinates) {
            int i = coord[0];
            int j = coord[1];
            attackBoard[i][j] = BROWN_BACKGROUND + "X" + RESET;
            gameBoard2[i][j] = BROWN_BACKGROUND + "X" + RESET;
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

    public boolean ifPlayerHasWon(Player player) {
        for (Ship ship : player.getShips()) {
            if (!ship.isSunk()) {
                return false;
            }
        }
        return true;
    }

    public static void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    public static void delay(int milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
