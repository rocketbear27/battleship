import java.util.ArrayList;
import java.util.Random;

public class AI {
    private Random random = new Random();
    private boolean lastHit = false;
    private ArrayList<int[]> coordinates = new ArrayList<int[]>();

    /*
     * coordinates is an array list of int arrays
     * if (lastHit is false and coord is empty)
     * {
     * random gen
     * if (hit)
     * {
     * lastHit = true
     * coordinates.add(all coordinates around it) //make new method that checks if
     * coord valid
     * }
     * }
     * else if (coord is not empty) {
     * guess at one of coords in coordinates
     * if (hit and ship is not sunk) {
     * make new coords based on hit (go up or down if ship is vertical or left or
     * right
     * if ship is horiz)
     * }
     * if (hit and ship is sunk) {
     * lastHit = false
     * empty coordinates
     * }
     * if (miss) {
     * delete that from coordinate
     * }
     * }
     */
    public boolean playAI(Player aiPlayer, Player humanPlayer) {
        boolean continuePlaying = true;
        Battleship game = new Battleship();

        while (continuePlaying) {
            if (game.ifPlayerHasWon(aiPlayer)) {
                return false; // Human player has won
            }
            if (!attackHard(aiPlayer, humanPlayer)) {
                continuePlaying = false;
            }
            if (game.ifPlayerHasWon(humanPlayer)) {
                return false;
            }
        }

        return true;
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
                    Battleship.placeShipOnBoard(row, col, length, orientation, aiPlayer.getGameBoard());
                    aiPlayer.getShips().add(new Ship(row, col, length, orientation));
                    placed = true;
                }
            }
        }
    }

    public boolean attackHard(Player aiPlayer, Player humanPlayer) {
        if (!lastHit && coordinates.isEmpty()) {
            return huntMode(aiPlayer, humanPlayer);
        } else {
            return targetMode(aiPlayer, humanPlayer);
        }
    }

    private boolean huntMode(Player aiPlayer, Player humanPlayer) {
        while (true) {
            char row = (char) ((int) (Math.random() * 10) + 'A');
            int col = (int) (Math.random() * 10) + 1;
            if (Battleship.isValidAttack(row, col, aiPlayer.getAttackBoard())) {
                boolean hit = Battleship.aiAttack(aiPlayer, humanPlayer, row, col);
                if (hit) {
                    addSurroundingCoordinates(row, col, humanPlayer);
                    lastHit = true;
                }
                return hit;
            }
        }
    }

    private boolean targetMode(Player aiPlayer, Player humanPlayer) {
        while (!coordinates.isEmpty()) {
            int[] coord = coordinates.remove(0);
            char row = (char) (coord[0] + 'A');
            int col = coord[1];
            if (Battleship.isValidAttack(row, col, aiPlayer.getAttackBoard())) {
                boolean hit = Battleship.aiAttack(aiPlayer, humanPlayer, row, col);
                if (hit) {
                    if (isShipSunk(humanPlayer, row, col)) {
                        lastHit = false;
                        coordinates.clear();
                    } else {
                        addSurroundingCoordinates(row, col, humanPlayer);
                    }
                }
                return hit;
            }
        }
        lastHit = false;
        return huntMode(aiPlayer, humanPlayer);
    }

    private void addSurroundingCoordinates(int row, int col, Player humanPlayer) {
        int[][] potentialCoordinates;

        if (lastHit) {
            Ship lastHitShip = humanPlayer.getShips().get(humanPlayer.getShips().size() - 1);
            if (lastHitShip.getOrientation().equals("H")) {
                potentialCoordinates = new int[][] { { row, col - 1 }, { row, col + 1 } };
            } else {
                potentialCoordinates = new int[][] { { row - 1, col }, { row + 1, col } };
            }
        } else {
            // If lastHit is false, add coordinates around the hit position
            potentialCoordinates = new int[][] {
                    { row - 1, col }, { row + 1, col }, { row, col - 1 }, { row, col + 1 }
            };
        }

        for (int[] coord : potentialCoordinates) {
            if (isValidCoordinate(coord[0], coord[1])) {
                coordinates.add(coord);
            }
        }
    }

    private boolean isValidCoordinate(int row, int col) {
        return row >= 0 && row < 10 && col >= 0 && col < 10;
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

    public static boolean isShipSunk(Player player, char row, int col) {
        for (Ship ship : player.getShips()) {
            ArrayList<int[]> shipCoordinates = ship.getCoordinates();
            for (int[] coordinate : shipCoordinates) {
                if (coordinate[0] == row - 'A' + 1 && coordinate[1] == col) {
                    return ship.isSunk();
                }
            }
        }
        return false;
    }
}