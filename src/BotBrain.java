import javax.swing.*;

/*

  All bot 'thinking' can be found in this file.
  I read there are 27,000+ unique board states, let's not hardcode them all.
  Strategy one - according to 'the guide', has more win paths and is therefore more effective.

  How does the Bot work? The Bot makes a random first move.
  From there, Bot will win or block - if it can or must.
  If not, bot will setup double move - there are multiple strategies bot can use.
  Bot strategy can go one of two ways, depending on first move.
  From there, many combinations of double moves can be made by the bot.

  If no double move is identified from the double move functions,
  the bot will default to random move. (If the bot is considering this, then we already didn't need to win or block).

  Current issues - the '2nd tier' double moves - they can still sometimes rely on blocked squares
  Bot is not 100%, and will miss some moves some of the time. (strategyTwo)


  note: strategy_one() and strategy_two()
        return the coordinates for the next bot move.
        The default values of {-1, -1} mean that a move was not found from the function,
        which is OK.

 */


public class BotBrain {
    public static void make_bot_move(

            char[][] board,
            JButton[][] buttons,
            int[] moveCount ) {

        // At any return statement the bot will have made its 'best' move for the situation.
        int rand_row = 3;
        int rand_col = 3;
        char CROSS = 'X';
        char CIRCLE = 'O';

        // 1. Bots first pick is always random
        if(moveCount[0] == 0){
             rand_row = (int) (Math.random() * 3);
             rand_col = (int) (Math.random() * 3);
            buttons[rand_row][rand_col].doClick();
            return;
        }

        // 2. Win if possible (first thing bot checks for every move but first)
        if (win_or_block(board, buttons, 'O')) return;

        // 3. Block if necessary
        if (win_or_block(board, buttons, 'X')) return;

        // 4. Double Moves
        if(board[0][0] == CIRCLE
                || board[0][2] == CIRCLE
                || board[2][0] == CIRCLE
                || board[2][2] == CIRCLE ) {

            // 4-A. Preferred double move strategy - starting with corners
            int[] move = strategyOne(board);
            if(move[0] ==  -1) {
                // do nothing and let the fallback make the move
            } else {
                buttons[move[0]][move[1]].doClick();
                return;
            }
        }

        else {

            // 4-B. Back up double move strategy - starting with middle (or side-middles)
            int[] move = strategyTwo(board);
            if (move[0] == -1) {
                // do nothing and let the fallback make the move
            } else {
                buttons[move[0]][move[1]].doClick();
                return;
            }
        }

        // 5. Default: first available (what matters here is that we make a move, any move)
        System.out.println("default move");
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                if (board[row][col] == ' ') {
                    buttons[row][col].doClick();
                    return;
                }
            }
        }
    }

    public static boolean win_or_block(

            char[][] board,
            JButton[][] buttons,
            char player ) {

        // Horizontal
        for (int row = 0; row < 3; row++) {
            int count = 0, empty = -1;
            for (int col = 0; col < 3; col++) {
                if (board[row][col] == player) count++;
                else if (board[row][col] == ' ') empty = col;
            }
            if (count == 2 && empty != -1) {
                buttons[row][empty].doClick();
                return true;
            }
        }

        // Vertical
        for (int col = 0; col < 3; col++) {
            int count = 0, empty = -1;
            for (int row = 0; row < 3; row++) {
                if (board[row][col] == player) count++;
                else if (board[row][col] == ' ') empty = row;
            }
            if (count == 2 && empty != -1) {
                buttons[empty][col].doClick();
                return true;
            }
        }

        // Diagonal
        int count = 0, emptyIndex = -1;
        for (int i = 0; i < 3; i++) {
            if (board[i][i] == player) count++;
            else if (board[i][i] == ' ') emptyIndex = i;
        }
        if (count == 2 && emptyIndex != -1) {
            buttons[emptyIndex][emptyIndex].doClick();
            return true;
        }

        // Anti-diagonal
        count = 0; emptyIndex = -1;
        for (int i = 0; i < 3; i++) {
            if (board[i][2 - i] == player) count++;
            else if (board[i][2 - i] == ' ') emptyIndex = i;
        }
        if (count == 2 && emptyIndex != -1) {
            buttons[emptyIndex][2 - emptyIndex].doClick();
            return true;
        }

        return false;
    }

    public static int[] strategyOne(char[][] board){

       /*

         x

         x    x

        */

        System.out.println("strategy 1");
        int[] move_coords = {-1, -1};
        char CROSS = 'X';
        char CIRCLE = 'O';
        int[][] locationOfACircle = {{-1, -1, }, {-1, -1, }, {-1, -1}, {-1, -1}};
        int conflictCrosses = 0;
        boolean xBlocksStrategy = false;
        int[][] ends = {{0,0}, {0, 2}, {2, 0}, {2, 2}};
        int[] center = {1, 1};

        for (int i = 0; i < ends.length; i++) {

            if(board[ends[i][0]][ends[i][1]] == CIRCLE){
                locationOfACircle[i] = ends[i];
            }
            if(board[ends[i][0]][ends[i][1]] == CROSS){
                conflictCrosses++;
            }
        }
        if(board[center[0]][center[1]] == CROSS){
            return new int[]{-1, -1};
        }
        if(conflictCrosses >= 2){
            // Strategy update - We must now take the center and our double move will be setup

            /*

             x
                x
             x

             */

            if(board[1][1] == ' '){
                move_coords = new int[]{1, 1};
                return move_coords;
            }

        }

        // taking any corner is fine here to make our double move

            // top left
            if(board[0][0] == ' '){
                move_coords = new int[]{0, 0};
                return move_coords;
            }

            // top right
            else if(board[0][2] == ' '){
                move_coords = new int[]{0, 2};
                return move_coords;
            }

            // bottom left
            else if(board[2][0] == ' '){
                move_coords = new int[]{2, 0};
                return move_coords;
            }

            // bottom right
            else if(board[2][2] == ' '){
                move_coords = new int[]{2, 2};
                return move_coords;
            }

    return new int[]{-1, -1}; // notifies caller to use default values

    }

    // Not debugged against already blocked spots
    public static int[] strategyTwo(char[][] board){
        // Pattern Attempt

        /*

         x x
           x

         */

        System.out.println("strategy 2");
        int[] move_coords = {-1, -1};
        char CIRCLE = 'O';
        int[][] foundCircle = {{-1, -1, }, {-1, -1, }, {-1, -1}, {-1, -1}};
        int middle_circles = 0;

        int[][] mids = {{0,1}, {1, 2}, {2, 1}, {1, 0}};
        for (int i = 0; i < mids.length; i++) {

            if(board[mids[i][0]][mids[i][1]] == CIRCLE){
                foundCircle[0] = mids[i];
                middle_circles++;
            }
        }
        if(middle_circles == 1){

                // top middle
                if(foundCircle[0] == mids[0]){
                    if(board[1][0] == ' ') {
                        move_coords = mids[3];
                    } else if (board[1][2] == ' ') {
                        move_coords = mids[1];
                    }
                 }

                // middle right
                else if(foundCircle[0] == mids[1])  {
                    if(board[0][1] == ' '){
                        move_coords = mids[0];
                    } else if(board[2][1] == ' ') {
                        move_coords = mids[2];
                    }
                }

                // bottom middle
                else if(foundCircle[0] == mids[2])  {
                    if(board[1][2] == ' '){
                        move_coords = mids[1];
                    } else if(board[1][0] == ' '){
                        move_coords = mids[3];
                    }
                }

                // middle left
                else if(foundCircle[0] == mids[3])  {
                    if(board[2][1] == ' '){
                        move_coords = mids[2];
                    } else if (board[0][1] == ' '){
                        move_coords = mids[3];
                    }
                }

                return move_coords;
        }

        if(middle_circles >= 2) {
            // At this point, we can take the center, finishing setup for a double move - unless we are attempting an already blocked path, which may be the case in the current iteration of this program.
            if (board[1][1] == ' ') {
                move_coords = new int[]{1, 1};
                return move_coords;
            }

            // Since the opponent took the center, we need to update our strategy.
            // Now we take the closest corner.
            // Pattern Goal:

            /*

             x
             x x

             */

            // top right
            if(foundCircle[0] == mids[0] && foundCircle[1] == mids[1]){
                if(board[0][2] == ' '){
                    move_coords = new int[]{0, 2};
                }
            }

            // top left
            else if(foundCircle[0] == mids[0] && foundCircle[1] == mids[3]) {
                if (board[0][0] == ' ') {
                    move_coords = new int[]{0, 0};
                }
            }

            // bottom right
            else if (foundCircle[0] == mids[1] && foundCircle[1] == mids[2]) {
                    if (board[2][2] == ' ') {
                        move_coords = new int[]{2, 2};
                    }}

            // bottom left
            else if (foundCircle[0] == mids[2] && foundCircle[1] == mids[3]) {
                    if (board[2][0] == ' ') {
                        move_coords = new int[]{2, 0};
                    }
                }

            return move_coords;
        }

        return new int[]{-1, -1}; // backup for default values
    }
}
