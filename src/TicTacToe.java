import java.util.Scanner;

/* Anne Hu
 * October 22nd 2017
 */
public class TicTacToe {
	// new scanner object to use to get user input in the console
	static Scanner userInput = new Scanner(System.in);

	/*
	 * This method is called "main" It has all the components of the main game It is
	 * used to call other methods to create the game
	 */
	public static void main(String[] args) {
		/*
		 * setting a boolean to true to start the while loop (for the game to loop until
		 * user exits) calls a method called "reset" to clear the board
		 */
		boolean start = true;
		boolean play = false;
		// a 2d array to hold the position names and assigns it to an element
		String position[][] = { { "A1", "A2", "A3" }, { "B1", "B2", "B3" }, { "C1", "C2", "C3" } };
		// a 2d array that creates the game board to see visually in the console
		String gameBoard[][] = new String[3][3];
		String player = reset(gameBoard);

		// this is the main loop that will loop until the user exits
		while (start) {
			// setting the return value of this method to a variable to determine the
			// playing mode from the user
			String playerMode = menu();
			// if the player types in exit, the program will end
			if (playerMode.equals("exit")) {
				System.out.println("Bye bye!");
				start = false;
				play = false;
			} else {
				play = true;
			}
			// while loop to repeat actions until game is over
			while (play) {
				player = getTurn(player);
				System.out.println("Player " + player + ", it's your turn!");
				createBoard(gameBoard);
				// if statement used to ask for user input when nesesscary (player X in 1
				// player, and both players in 2 player mode)
				if (playerMode.equals("dos") || (playerMode.equals("uno") && player.equals("X"))) {
					while (!inputMove(position, gameBoard, player))
						;
				} else if (playerMode.equals("uno") && player.equals("O")) {
					while (!computerPlay(gameBoard, player))
						;
				}
				if (checkWinner(gameBoard, player)) {
					createBoard(gameBoard);
					player = reset(gameBoard);
					play = false;
				}
			}
		}
	}

	/*
	 * This method is used for 1 player mode. This is the computer that plays
	 * against the human It calculates 2 random numbers in between 0-2, which are
	 * the index numbers Checks if that spot is not empty, it will loop until the
	 * program finds a spot that is empty If its empty it will place the player
	 * there and return true when it returns true, the method will stop running,
	 * until called again The parameters being sent through is the game board and
	 * player to place a O in the spot on the board
	 */
	private static boolean computerPlay(String board[][], String player) {
		int randNum1 = (int) (Math.random() * 3) + 0;
		int randNum2 = (int) (Math.random() * 3) + 0;

		if (!(board[randNum1][randNum2].equals(" "))) {
			return false;
		} else {
			board[randNum1][randNum2] = player;
			return true;
		}
	}

	/*
	 * This method is used to clear the board (populate with spaces) It returns the
	 * "O" player, so X can go first The parameter that gets sent through is the
	 * board, so it can be cleared
	 */
	public static String reset(String board[][]) {
		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board[i].length; j++) {
				board[i][j] = " ";
			}
		}
		return "O";
	}

	/*
	 * This method is used at the beginning of the program as a menu There are no
	 * parameters It asks for the users prefered playing mode, 1 player or 2 player,
	 * (uno or dos) The user can also exit the program (type exit) The method
	 * returns the input of the user
	 */
	public static String menu() {
		String input = "";
		System.out.println("Welcome to TIC TAC TOE!");
		System.out.println("Please type the mode you would like to play.");
		System.out.println("uno - One player, dos - two players or exit - to exit");
		boolean receive = false;
		while (!receive) {
			input = userInput.next();
			if (input.equals("uno") || input.equals("dos") || input.equals("exit")) {
				receive = true;
			} else {
				System.out.println("Please type in uno, dos or exit");
			}
		}

		return input;
	}

	/*
	 * This method is used to print the board The parameters sent through is the
	 * board array This method calls another method called display rows, to display
	 * the game board
	 */
	public static void createBoard(String board[][]) {
		System.out.println("");
		System.out.println("   1   2   3");
		displayRows("A ", 0, board);
		System.out.println("  -----------");
		displayRows("B ", 1, board);
		System.out.println("  -----------");
		displayRows("C ", 2, board);
		System.out.println("");
	}

	/*
	 * This method is used to display the game board The parameters that it receives
	 * is the index number of the elements, game board, and the letter the row's
	 * name (so the user to identify the spot), which all gets printed to the
	 * console,
	 */
	public static void displayRows(String letter, int row, String board[][]) {
		System.out.println(letter + " " + board[row][0] + " | " + board[row][1] + " | " + board[row][2]);
	}

	/*
	 * This method is used to switch between the two players The parameters sent
	 * through is the player that previously went It returns the player that is
	 * playing now
	 */
	public static String getTurn(String player) {
		if (player == "X") {
			player = "O";
		} else {
			player = "X";
		}
		return player;
	}

	/*
	 * This method is used to determine when the player wins the game This method
	 * checks for rows, columns, the 2 diagonals and it the game is a tie The
	 * parameters that get sent through is the player- to determine if the spots
	 * match the player that is playing And the board - to check for the winner This
	 * method returns true or false, It returns true - if there is a winner and
	 * false if there isn't
	 */
	public static boolean checkWinner(String board[][], String player) {
		// counter variable to count the number of matches in the game to determine a
		// win
		int counter = 0;
		// checks for row matches
		for (int i = 0; i < board.length; i++) {
			for (int j = 1; j < board[i].length; j++) {
				if (board[i][j] == board[i][j - 1] && board[i][j] != " " && board[i][j] == player) {
					counter++;
					if (counter == 2) {
						System.out.println("Congratulations, Player " + player + " Won!");
						return true;
					}
				}
			}
			counter = 0;
		}
		// checks for column matches
		for (int x = 0; x < board.length; x++) {
			for (int i = 1; i < board.length; i++) {
				for (int j = 0; j < board[i].length; j++) {
					if (x == j && board[i][j] == board[i - 1][j] && board[i][j] != " " && board[i][j] == player) {
						counter++;
						if (counter == 2) {
							System.out.println("Congratulations, Player " + player + " Won!");
							return true;
						}
					}
				}
			}
			counter = 0;
		}

		// checks for diagonal matches
		if (board[1][1] == player) {
			if ((board[2][0] == board[1][1]) && (board[1][1] == board[0][2])) {
				System.out.println("Congratulations, Player " + player + " Won!");
				return true;
			} else if ((board[0][0] == board[1][1]) && (board[1][1] == board[2][2])) {
				System.out.println("Congratulations, Player " + player + " Won!");
				return true;
			}
		}

		// checks for a tie (cats game)
		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board[i].length; j++) {
				if (board[i][j] != " ") {
					counter++;
				}
				if (counter == 9) {
					System.out.println("CATS GAME");
					return true;
				}
			}
		}
		// returns false if all checks are completed and therefore does not return true
		// resets the counter back to 0
		counter = 0;
		return false;
	}

	/*
	 * This method is used to get user input of where the play wants to place their
	 * move which places X or O on the board It checks for the input, and if there
	 * is already an X or O there, it would ask for input again If the spot is
	 * empty, it will place an X or O there If the user inputs not good input, it
	 * will ask the user to re-input The parameters it is sent is the players turn,
	 * and both arrays to determine where to place the move Note: I did not use an
	 * enhanced for loop because I need the index number of the user input from the
	 * position array, in which the X or O gets placed in the same index on the game
	 * board where the position element was
	 */
	public static boolean inputMove(String pos[][], String board[][], String player) {
		String input = userInput.next();
		for (int i = 0; i < pos.length; i++) {
			for (int j = 0; j < pos[i].length; j++) {
				if (pos[i][j].equals(input)) {
					if (!(board[i][j] == " ")) {
						System.out.println("Oops that spot is already filled, try again");
						return false;
					} else {
						board[i][j] = player;
						return true;
					}
				}
			}
		}
		System.out.println("Please type in a valid spot on the game board (uppercase please!)");
		return false;
	}

}
