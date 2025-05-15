import javax.swing.*;
import java.awt.*;


/*

 Scott Quashen - San Francisco State University - May 14, 2025.
 Comp 101, Prof AJ Souza - Final Project, Tic Tac Toe - Bonus GUI & Bonus AI BOT
 The bot is not yet perfected -- but it's pretty good!
 Issue: bot does miss moves at time in strategy 2, possibly strategy 1 as well.

 Player can choose who goes first. Bot is trained to win or block if can or must, and if not the bot will attempt
 to defeat the player using two tiers of double moves strategy.

 All GUI besides the game board buttons are here in main.
 A next step could be to move the GUI buttons with the rest of the GUI here to main.

 The Strategy guide (Tic Tac Toe Strategy) used for this project:
 https://www.instructables.com/Winning-tic-tac-toe-strategies/

 */


public class Main {
    public static void main(String[] args) {

        // variables
        char CROSS = 'X';
        char CIRCLE = 'O';
        int move_count[] = {0};
        boolean game_over[] = {false};
        char current_move[] = {CROSS};
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
        final boolean[] humanJustMoved = {false};

        // GUI setup
        JButton[][] buttons = new JButton[3][3];
        JFrame frame = new JFrame("Tic Tac Toe Bot");
        frame.setSize(300, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Set 3 rows, 3 columns
        JPanel gridPanel = new JPanel(new GridLayout(3, 3));
        JLabel statusLabel = new JLabel("Current move: X", SwingConstants.CENTER);
        frame.setLayout(new FlowLayout()); // or BorderLayout, etc.

        JButton playerFirstButton = new JButton("Human First");
        playerFirstButton.addActionListener(e -> {

            for (int row = 0; row < 3; row++) {
                for (int col = 0; col < 3; col++) {
                    buttons[row][col].setText(" ");
                }
            }
            for (int i = 0; i < game_board.length; i++) {
                for(int j = 0; j < game_board[0].length; j++) {
                    game_board[i][j] = ' ';
                }
            }
            move_count[0] = 0;
            humanJustMoved[0] = false; // used as an extra check during build - probably can be carefully removed
            game_over[0] = false;
            current_move[0] = CROSS;
        });
        frame.add(playerFirstButton);


        // Button to start game in bot first mode
        JButton botFirstButton = new JButton("Bot First");
        botFirstButton.addActionListener(e -> {

            for (int row = 0; row < 3; row++) {
                for (int col = 0; col < 3; col++) {
                    buttons[row][col].setText(" ");
                }
            }
            for (int i = 0; i < game_board.length; i++) {
                for( int j = 0; j < game_board[i].length; j++) {
                    game_board[i][j] = ' ';
                }
            }
            move_count[0] = 0;
            humanJustMoved[0] = false;
            game_over[0] = false;
            current_move[0] = CIRCLE;
            BotBrain.make_bot_move(game_board, buttons, move_count); // starts the game in bot first mode

        });
        frame.add(botFirstButton);

        for (int i = 0; i < game_board.length; i++) {
            for (int j = 0; j < game_board[i].length; j++) {

                JButton button = GameLogic.playableSpot(i, j,
                        game_board, statusLabel, current_move,
                        game_over, move_count, buttons, humanJustMoved);
                buttons[i][j] = button;
                gridPanel.add(button);
            }
        }
        frame.add(statusLabel, BorderLayout.NORTH);
        frame.add(gridPanel, BorderLayout.CENTER);
        frame.setVisible(true);
    }
}



