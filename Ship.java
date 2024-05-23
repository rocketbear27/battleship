import java.util.ArrayList;

public class Ship {
    private char row;
    private int col;
    private int length;
    private String orientation;
    private boolean[] hits;

    public Ship(char row, int col, int length, String orientation) {
        this.row = Character.toUpperCase(row);
        this.col = col;
        this.length = length;
        this.orientation = orientation.toUpperCase();
        this.hits = new boolean[length];
    }

    public boolean registerHit(char row, int col) {
        row = Character.toUpperCase(row);
        if (orientation.equals("H")) {
            if (this.row == row && col >= this.col && col < this.col + length) {
                hits[col - this.col] = true;
                return true;
            }
        } else if (orientation.equals("V")) {
            if (this.col == col && row >= this.row && row < this.row + length) {
                hits[row - this.row] = true;
                return true;
            }
        }
        return false;
    }

    public boolean isSunk() {
        for (boolean hit : hits) {
            if (!hit) {
                return false;
            }
        }
        return true;
    }

    public ArrayList<int[]> getCoordinates() {
        ArrayList<int[]> coordinates = new ArrayList<int[]>();
        int startRow = (int) row - 65 + 1;
        if (orientation.equals("H")) {
            for (int i = 0; i < length; i++) {
                coordinates.add(new int[]{startRow, col + i});
            }
        } else if (orientation.equals("V")) {
            for (int i = 0; i < length; i++) {
                coordinates.add(new int[]{startRow + i, col});
            }
        }
        return coordinates;
    }
}
