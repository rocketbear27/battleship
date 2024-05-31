import java.util.ArrayList;
import java.util.Random;

public class AI {
    private Random random = new Random();
    private boolean lastHit = false;
    private ArrayList<int[]> coordinates = new ArrayList<int[]>();

    public boolean playAI(Player aiPlayer, Player humanPlayer, String aiDifficulty) {
        Battleship game = new Battleship();
        while (true) {
            if (game.ifPlayerHasWon(aiPlayer)) {
                return false;
            }
            if (!attackEasy(aiPlayer, humanPlayer)) {
                continue;
            }

            if (game.ifPlayerHasWon(humanPlayer)) {
                return true;
            }

            if (aiDifficulty.equals("2")) {
                if (!attackHard(aiPlayer, humanPlayer)) {
                    continue;
                }
            } else {
                if (!attackEasy(aiPlayer, humanPlayer)) {
                    continue;
                }
            }

            if (game.ifPlayerHasWon(aiPlayer)) {
                return true;
            }
        }
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
                    humanPlayer.setCurrentTargetShip(humanPlayer.getShipAt(row, col));
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
                    humanPlayer.setCurrentTargetShip(humanPlayer.getShipAt(row, col));
                    if (isShipSunk(humanPlayer, row, col)) {
                        lastHit = false;
                        coordinates.clear();
                    } else {
                        addSurroundingCoordinates(row, col, humanPlayer);
                    }
                    if (!coordinates.isEmpty()) {
                        coord = coordinates.get(0);
                        row = (char) (coord[0] + 'A');
                        col = coord[1];
                    }
                    return hit;
                } else {
                    lastHit = false;
                    return false;
                }
            }
        }
        lastHit = false;
        return huntMode(aiPlayer, humanPlayer);
    }

    private void addSurroundingCoordinates(int row, int col, Player humanPlayer) {
        int[][] potentialCoordinates;

        Ship lastHitShip = humanPlayer.getCurrentTargetShip();

        if (lastHitShip != null && lastHitShip.getOrientation().equals("H")) {
            potentialCoordinates = new int[][] { { row, col - 1 }, { row, col + 1 } };
            for (int i = 0; i < potentialCoordinates.length; i++) {
                if (isValidCoordinate(potentialCoordinates[i][0], potentialCoordinates[i][1])) {
                    coordinates.add(potentialCoordinates[i]);
                } else {
                    if (i == 0) {
                        potentialCoordinates = new int[][] { { row, col - 2 } };
                    } else {
                        potentialCoordinates = new int[][] { { row, col + 2 } };
                    }
                }
            }
        } else if (lastHitShip != null & lastHitShip.getOrientation().equals("V")) {
            potentialCoordinates = new int[][] { { row - 1, col }, { row + 1, col } };
            for (int i = 0; i < potentialCoordinates.length; i++) {
                if (isValidCoordinate(potentialCoordinates[i][0], potentialCoordinates[i][1])) {
                    coordinates.add(potentialCoordinates[i]);
                } else {
                    if (i == 0) {
                        potentialCoordinates = new int[][] { { row - 2, col } };
                    } else {
                        potentialCoordinates = new int[][] { { row + 2, col } };
                    }
                }
            }
        } else {
            potentialCoordinates = new int[][] { { row - 1, col }, { row + 1, col }, { row, col - 1 },
                    { row, col + 1 } };
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