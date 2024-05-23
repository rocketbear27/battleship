
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
public class Main {
    public static void main(String[] args) {
        Battleship game = new Battleship();

        Player player1 = new Player("Red", 1);
        Player player2 = new Player("Blue", 2);

        game.printGameBoard(player1.getGameBoard());
        game.placeShip(player1);

        Battleship.clearScreen();

        game.printGameBoard(player2.getGameBoard());
        game.placeShip(player2);

        Battleship.clearScreen();

        while (!game.ifPlayerHasWon(player1) && !game.ifPlayerHasWon(player2)) {
            boolean player1Turn = true;
            while (player1Turn) {
                player1Turn = game.attack(player1, player2);
                if (game.ifPlayerHasWon(player2)) {
                    break;
                }
                if (!player1Turn) {
                    Battleship.clearScreen();
                    System.out.println("Switch Turns");
                    Battleship.delay(3000);
                    Battleship.clearScreen();
                }
            }
            while (!player1Turn) {
                player1Turn = !game.attack(player2, player1);
                if (game.ifPlayerHasWon(player1)) {
                    break;
                }
                if (player1Turn) {
                    Battleship.clearScreen();
                    System.out.println("Switch Turns");
                    Battleship.delay(3000);
                    Battleship.clearScreen();
                }
            }
        }
        if (game.ifPlayerHasWon(player2)) {
            Battleship.clearScreen();
            System.out.println("Team Red Has Won");
        }
        if (game.ifPlayerHasWon(player1)) {
            Battleship.clearScreen();
            System.out.println("Team Blue Has Won");
        }
    }
}

/*
 * Ship lengths: 5, 4, 3, 3, 2
 */