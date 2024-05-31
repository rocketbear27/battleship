import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Battleship game = new Battleship();
        AI ai = new AI();

        String gameMode = "";
        while (!gameMode.equals("1") && !gameMode.equals("2")) {
            Battleship.clearScreen();
            System.out.println("Do you want to play against another player or AI? (Please enter 1 or 2)");
            System.out.println("1. Another Player");
            System.out.println("2. AI");
            gameMode = scanner.nextLine();
            if (!gameMode.equals("1") && !gameMode.equals("2")) {
                System.out.println("Invalid choice. Please enter 1 or 2.");
            }
        }
        Battleship.clearScreen();
        if (gameMode.equals("1")) {
            // Player vs Player
            Player player1 = new Player("Red", 1);
            Player player2 = new Player("Blue", 2);

            game.setupPlayer(player1);
            game.setupPlayer(player2);

            game.playGame(player1, player2);
        } else if (gameMode.equals("2")) {
            String aiDifficulty = "";
            while (!aiDifficulty.equals("1") && !aiDifficulty.equals("2")) {
                Battleship.clearScreen();
                System.out.println("Choose AI difficulty (Please enter 1 or 2): ");
                System.out.println("1. Easy AI");
                System.out.println("2. Hard AI");
                aiDifficulty = scanner.nextLine();
                if (!aiDifficulty.equals("1") && !aiDifficulty.equals("2")) {
                    System.out.println("Invalid choice. Please enter 1 or 2.");
                }
            }

            Player humanPlayer = new Player("Human", 1);
            Player aiPlayer = new Player("AI", 2);

            game.setupPlayer(humanPlayer);
            if (aiDifficulty.equals("1")) {
                ai.placeShipsEasy(aiPlayer);
            } else {
                ai.placeShipsHard(aiPlayer);
            }

            while (!game.ifPlayerHasWon(humanPlayer) && !game.ifPlayerHasWon(aiPlayer)) {
                boolean humanTurn = true;
                while (humanTurn) {
                    Battleship.clearScreen();
                    humanTurn = game.attack(humanPlayer, aiPlayer);
                    if (game.ifPlayerHasWon(aiPlayer)) {
                        break;
                    }
                    if (!humanTurn)
                        if (game.ifPlayerHasWon(aiPlayer)) {
                            break;
                        }
                    if (!humanTurn) {
                        Battleship.clearScreen();
                        System.out.println("You Missed");
                        System.out.println("AI's Turn");
                        Battleship.printScoreboard(humanPlayer, aiPlayer);
                        Battleship.delay(3000);
                        Battleship.clearScreen();
                    }
                }
                while (!humanTurn) {
                    Battleship.clearScreen();
                    if (aiDifficulty.equals("1")) {
                        humanTurn = !ai.attackEasy(aiPlayer, humanPlayer);
                    } else {
                        humanTurn = !ai.attackHard(aiPlayer, humanPlayer);
                    }
                    if (game.ifPlayerHasWon(humanPlayer)) {
                        break;
                    }
                    if (humanTurn) {
                        Battleship.clearScreen();
                        System.out.println("AI Missed");
                        System.out.println("Your Turn");
                        Battleship.printScoreboard(humanPlayer, aiPlayer);
                        Battleship.delay(3000);
                        Battleship.clearScreen();
                    }
                }
            }
            if (game.ifPlayerHasWon(aiPlayer)) {
                System.out.println("Congratulations! You win!");
            }
            if (game.ifPlayerHasWon(humanPlayer)) {
                System.out.println("AI wins. Better luck next time!");
            }
        }
        scanner.close();
    }
}
