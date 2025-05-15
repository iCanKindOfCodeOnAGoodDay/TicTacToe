import javax.swing.*;
import java.awt.*;

/*

Unfortunately the GUI button, playableSpot,
is still in this file and not with all the other GUI in main.

 */

public class GameLogic {

    public static JButton playableSpot(

            int row,
            int col,
            char[][] board,
            JLabel statusLabel,
            char[] current_move,
            boolean[] game_over,
            int[] move_count,
            JButton[][] buttons,
            boolean[] humanJustMoved ) {

                JButton button = new JButton(" ");
                button.setFont(new Font("Arial", Font.BOLD, 40));

                button.addActionListener(e -> {

                if (board[row][col] != ' ' || game_over[0]) return;

                // Place move on board
                board[row][col] = current_move[0];
                button.setText(String.valueOf(current_move[0]));
                move_count[0]++;

                // Check win
                if (check_for_winner(board, current_move[0])) {
                    statusLabel.setText(current_move[0] + " wins!");
                    System.out.printf("%s wins!%n", current_move[0]);
                    game_over[0] = true;
                    return;
                }

                // Check draw
                if (check_for_draw(move_count[0])) {
                    statusLabel.setText("Draw!");
                    game_over[0] = true;
                    return;
                }

                // Switch turn
                current_move[0] = switch_players(current_move[0]);
                statusLabel.setText("Current move: " + current_move[0]);

                // Only trigger bot move if HUMAN just played (as X)
                if (current_move[0] == 'O') {
                    humanJustMoved[0] = true;

                    // If the timer is set to say 1000, you can notice how inconsistent it is
                    // delay of 300 is the sweet spot - not enough time for 'related issues' - but smooth visual effect ... related issues include: being able to click during the bots turn, switching players when the bot makes a rapid x move
                    new javax.swing.Timer(300, evt -> {
                        if (humanJustMoved[0] && !game_over[0]) {
                            humanJustMoved[0] = false; // Clear flag so bot doesn’t loop
                            BotBrain.make_bot_move(board, buttons, move_count);
                        }
                    }).start();
                }
        });
        return button;
    }

    // horizontal, vertical, forward diagonal, backwards diagonal
    public static boolean check_for_winner(char[][] game_board, char current_move) {

        for (int i = 0; i < game_board.length; i++) {
            for (int j = 0; j < game_board[i].length; j++) {
                if (game_board[i][j] == current_move) {

                    // 1. Horizontal
                    if(game_board[i][0] == current_move
                            && game_board[i][1] == current_move
                            && game_board[i][2] == current_move){
                        return true;
                    }

                    // 2. Vertical
                    if(game_board[0][j] == current_move
                            && game_board[1][j] == current_move
                            && game_board[2][j] == current_move){
                        return true;
                    }
                }
            }
        }

        // 3. Diagonal (forwards)
        if(game_board[0][2] == current_move
                && game_board[1][1] == current_move
                && game_board[2][0] == current_move){
            return true;
        }

        // 4. Diagonal (backwards)
        if(game_board[0][0] == current_move
                && game_board[1][1] == current_move
                && game_board[2][2] == current_move){
            return true;
        }
        return false;
    }

    public static boolean check_for_draw(int move_count) {
        return move_count == 9;
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


