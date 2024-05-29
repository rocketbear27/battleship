import java.util.Arrays;
import java.util.Random;

public class AI {
    private Random random = new Random();
    private int lastHitRow = -1;
    private int lastHitCol = -1;
    private boolean lastHitSuccessful = false;
    private int directionIndex = 0;
    private final int[][] directions = { { -1, 0 }, { 0, 1 }, { 1, 0 }, { 0, -1 } };

    public boolean playAI(Player aiPlayer, Player humanPlayer) {
        boolean aiTurn = true;
        boolean continuePlaying = true;

        Battleship game = new Battleship();

        while (continuePlaying) {
            if (aiTurn) {
                if (!attackHard(aiPlayer, humanPlayer)) {
                    // AI missed, switch turns
                    aiTurn = false;
                }
            } else {
                if (!game.attack(humanPlayer, aiPlayer)) {
                    // Human missed, switch turns
                    aiTurn = true;
                }
            }

            if (game.ifPlayerHasWon(humanPlayer)) {
                return false; // AI has won, stop the game
            }

            continuePlaying = !game.ifPlayerHasWon(humanPlayer); // Check if game should continue
        }

        return true; // Game is still ongoing
    }

    public void placeShipsEasy(Player aiPlayer) {
        int[] lengths = { 5, 4, 3, 3, 2 };
        for (int length : lengths) {
            boolean placed = false;
            while (!placed) {
                char row = (char) ((int) (Math.random() * 10) + 'A');
                int col = (int) (Math.random() * 10) + 1;
                String[] orientations = { "H", "V" };
                String orientation = orientations[(int) (Math.random() * orientations.length)];
                if (Battleship.checkCoordinateValidity(row, col, length, orientation, aiPlayer.getGameBoard())) {
                    Battleship.placeShipOnBoard(row, col, length, orientation, aiPlayer.getGameBoard());
                    aiPlayer.getShips().add(new Ship(row, col, length, orientation));
                    placed = true;
                }
            }
        }
    }

    public boolean attackEasy(Player aiPlayer, Player humanPlayer) {
        return randomAttack(aiPlayer, humanPlayer);
    }

    public void placeShipsHard(Player aiPlayer) {
        int[] lengths = { 5, 4, 3, 3, 2 };
        for (int length : lengths) {
            boolean placed = false;
            while (!placed) {
                char row = (char) ((int) (Math.random() * 10) + 'A');
                int col = (int) (Math.random() * 10) + 1;
                String[] orientations = { "H", "V" };
                String orientation = orientations[(int) (Math.random() * orientations.length)];
                if (Battleship.checkCoordinateValidity(row, col, length, orientation, aiPlayer.getGameBoard())) {
                    if (!touchingOtherShips(row, col, length, orientation, aiPlayer.getGameBoard())) {
                        Battleship.placeShipOnBoard(row, col, length, orientation, aiPlayer.getGameBoard());
                        aiPlayer.getShips().add(new Ship(row, col, length, orientation));
                        placed = true;
                    }
                }
            }
        }
    }

    public boolean attackHard(Player aiPlayer, Player humanPlayer) {
        //implement still
        return false; //placeholders 
    }

    private boolean randomAttack(Player aiPlayer, Player humanPlayer) {
        while (true) {
            char row = (char) (random.nextInt(10) + 'A');
            int col = random.nextInt(10) + 1;
            if (Battleship.isValidAttack(row, col, aiPlayer.getAttackBoard())) {
                return Battleship.aiAttack(aiPlayer, humanPlayer, row, col);
            }
        }
    }

    private int[] getLastHitCoordinates(Player aiPlayer) {
        for (int row = 0; row < 10; row++) {
            for (int col = 0; col < 10; col++) {
                if (aiPlayer.getAttackBoard()[row][col].equals("H")) {
                    return new int[] { row, col };
                }
            }
        }
        return null;
    }

    private boolean touchingOtherShips(char row, int col, int length, String orientation, String[][] gameBoard) {
        int i = row - 'A' + 1;
        int j = col;
        if (orientation.equals("H")) {
            for (int a = 0; a < length; a++) {
                if (isAdjacentOccupied(i, j + a, gameBoard)) {
                    return true;
                }
            }
        } else if (orientation.equals("V")) {
            for (int a = 0; a < length; a++) {
                if (isAdjacentOccupied(i + a, j, gameBoard)) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean isAdjacentOccupied(int i, int j, String[][] gameBoard) {
        int[] x = { -1, 0, 1, 0 };
        int[] y = { 0, 1, 0, -1 };
        for (int k = 0; k < 4; k++) {
            int a = i + x[k];
            int b = j + y[k];
            if (a > 0 && a < gameBoard.length && b > 0 && b < gameBoard[0].length) {
                if (gameBoard[a][b].equals("+")) {
                    return true;
                }
            }
        }
        return false;
    }
}