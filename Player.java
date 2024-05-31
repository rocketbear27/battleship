import java.util.ArrayList;

public class Player {
    private ArrayList<Ship> ships;
    private String[][] gameBoard;
    private String[][] attackBoard;
    private String name;
    private Ship currentTargetShip;

    public Player(String name, int playerNumber) {
        this.ships = new ArrayList<>();
        this.name = name;
        this.gameBoard = createBoard(playerNumber);
        this.attackBoard = createBoard(playerNumber);
    }

    public ArrayList<Ship> getShips() {
        return ships;
    }

    public String[][] getGameBoard() {
        return gameBoard;
    }

    public String[][] getAttackBoard() {
        return attackBoard;
    }

    public String getName() {
        return name;
    }

    private String[][] createBoard(int player) {
        String[][] board = new String[11][11];
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                board[i][j] = "-";
            }
        }
        String redBackground = "\u001B[41m";
        String blueBackground = "\u001B[44m";
        String reset = "\u001B[0m";
        String character = " ";
        if (player == 1) {
            board[0][0] = redBackground + character + reset;
        } else {
            board[0][0] = blueBackground + character + reset;
        }
        for (int a = 1; a < board.length; a++) {
            board[a][0] = String.valueOf((char) (a + 65 - 1));
        }
        for (int b = 1; b < board[0].length; b++) {
            board[0][b] = String.valueOf(b);
        }
        return board;
    }

    public Ship getCurrentTargetShip() {
        return currentTargetShip;
    }

    public void setCurrentTargetShip(Ship ship) {
        currentTargetShip = ship;
    }

    public Ship getShipAt(char row, int col) {
        for (Ship ship : ships) {
            if (ship.containsCoordinate(row, col)) {
                return ship;
            }
        }
        return null; // No ship found at the specified coordinate
    }
}
