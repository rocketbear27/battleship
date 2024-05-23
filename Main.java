
/*
 * Todo:
  - catch errors (Typing H5 instead of H)
  - scoreboard
    * display ship sunken score

 * - connect brown background to the ship
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