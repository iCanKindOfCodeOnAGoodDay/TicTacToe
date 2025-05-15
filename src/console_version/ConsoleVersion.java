package console_version;
import java.util.Scanner;


public class ConsoleVersion {
    public static void main(String[] args) {

        // variables
        char CROSS = 'X';
        char CIRCLE = 'O';
        int move_count = 0;
        boolean game_over = false;
        char current_move = CROSS;
        char[][] game_board = {
                {
                        ' ', ' ', ' '
                },
                {
                        ' ', ' ', ' '
                },
                {
                        ' ', ' ', ' '
                }
        };
        print_board(game_board);


        // game loop
        while (true){
            Scanner scanner = new Scanner(System.in);
            int row = 0;
            int col = 0;

            // get coordinates
            int[] coords = new int[1];
            coords = get_coords(current_move);
            row = coords[0];
            col = coords[1];

            // validate move
            boolean valid = validate_move(game_board, row, col);
            if (!valid){
                System.out.println("Invalid move, please try again.");
                continue; // restart loop iteration prior to placing bad move etc.
            }

            // place move
            place_move(game_board, row, col, current_move);
            move_count++;

            // check for draws
            boolean draw = check_for_draw(move_count);
            if (draw){
                System.out.println("Draw!");
            }

            // check for winners, offer to reset game
            game_over = check_for_winner(game_board, current_move);
            if (game_over){
                print_board(game_board);
                System.out.printf("%c wins!!!!\n", current_move);

                String wants_play_again = "";

                // validate input
                while (true){
                    System.out.println("Would you like to play again? (Y/N)");
                    wants_play_again = scanner.next();
                    if(wants_play_again.equalsIgnoreCase("Y") || wants_play_again.equalsIgnoreCase("N")){
                        break;
                    }
                    System.out.println("Invalid input, please try again.");
                }

                // reset the game
                if (wants_play_again.equals("Y")){
                    game_over = false;
                    for(int i = 0; i < game_board.length; i++){
                        for(int j = 0; j < game_board[i].length; j++){
                            game_board[i][j] = ' ';
                        }
                    }
                    continue;
                }

                // or exit the game loop (quite program) if user does not want to play again
                break;
            }

            // since the game is not over yet, switch players
            current_move = switch_players(current_move);

            // print the board for next player
            print_board(game_board);
        }

    }

    public static int[] get_coords(char current_move){
        Scanner scanner = new Scanner(System.in);
        int row = 0;
        int col = 0;

        // get coordinates - row
        while (true){
            System.out.printf("Player %c Enter row (1, 2, 3): \n", current_move);
            if (scanner.hasNextInt()){
                row = scanner.nextInt();
                if (1 <= row && row <= 3){
                    break;
                }

            }
            System.out.print("Invalid input, please try again.");
        }

        // get coordinates - col
        while (true){
            System.out.print("Enter position (1, 2, 3): \n");
            if (scanner.hasNextInt()){
                if(1 <= col && col <= 3){}
                col = scanner.nextInt();
                break;
            }
            System.out.print("Invalid input, please try again.");
        }
        // return array so we can access both values in main
        int[] row_col = {
                row, col
        };
        return row_col;
    }

    public static boolean validate_move(char[][] game_board, int row, int col) {
        if (game_board[row - 1][col - 1] == ' ') {
            return true;
        }
        return false;
    }

    public static void place_move(char[][] game_board, int row, int col, char current_move) {
        game_board[row - 1][col - 1] = current_move;
    }

    // horizontal, vertical, forward diagonal, backwards diagonal
    public static boolean check_for_winner(char[][] game_board, char current_move) {
        for (int i = 0; i < game_board.length; i++) {
            for (int j = 0; j < game_board[i].length; j++) {
                if (game_board[i][j] == current_move) {
                    // 1. check for horizontal wins
                    if(game_board[i][0] == current_move && game_board[i][1] == current_move && game_board[i][2] == current_move){
                        return true;
                    }
                    // 2. check for vertical wins
                    if(game_board[0][j] == current_move && game_board[1][j] == current_move && game_board[2][j] == current_move){
                        return true;
                    }
                }

            }
        }
        // 3. check for diagonal wins (forwards)
        if(game_board[0][2] == current_move && game_board[1][1] == current_move && game_board[2][0] == current_move){
            return true;
        }
        // 3. check for diagonal wins (backwards)
        if(game_board[0][0] == current_move && game_board[1][1] == current_move && game_board[2][2] == current_move){
            return true;
        }
        return false;
    }

    public static boolean check_for_draw(int move_count) {
        // we don't need any other info unless we're attempting early draw detection
        return move_count == 9;
    }

    public static void print_board(char[][] game_board) {
        for(int i = 0; i < game_board.length; i++){
            System.out.print("|");

            for(int j = 0; j < game_board[i].length; j++){
                System.out.print(game_board[i][j] + "|");
                if(j == game_board[i].length - 1){
                    System.out.print("\n");
                }
            }
        }
    }

    public static char switch_players(char current_move){
        if (current_move == 'X') {
            current_move = 'O';
        }
        else if (current_move == 'O') {
            current_move = 'X';
        }
        return current_move;
    }

}



