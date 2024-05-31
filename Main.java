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
            Player player1 = new Player("Red", 1);
            Player player2 = new Player("Blue", 2);

            game.setupPlayer(player1);
            game.setupPlayer(player2);

            game.playGame(player1, player2);
        } else if (gameMode.equals("2")) {
            String aiDifficulty = "";
            while (!aiDifficulty.equals("1") && !aiDifficulty.equals("2")) {
                Battleship.clearScreen();
                System.out.println("Choose AI difficulty (Please enter 1, 2, or 3): ");
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
            Battleship.clearScreen();

            if (aiDifficulty.equals("1")) {
                ai.placeShipsEasy(aiPlayer);
            } else if (aiDifficulty.equals("2")) {
                ai.placeShipsHard(aiPlayer);
            }

            while (!game.ifPlayerHasWon(humanPlayer) && !game.ifPlayerHasWon(aiPlayer)) {
                // Human player's turn

                if (game.ifPlayerHasWon(aiPlayer)) {
                    break; // AI wins
                }

                // AI's turn
                if (ai.playAI(aiPlayer, humanPlayer, aiDifficulty)) {
                    System.out.println("AI's turn. AI attacked.");
                } else {
                    System.out.println("You've won! AI's ships are all sunk.");
                    break; // Human player wins
                }
            }

            if (game.ifPlayerHasWon(humanPlayer)) {
                System.out.println("Congratulations! You win!");
            } else {
                System.out.println("AI wins. Better luck next time!");
            }
        }
        scanner.close();
    }
}
