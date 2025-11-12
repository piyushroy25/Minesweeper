package cs1302.game;

import cs1302.gameutil.GamePhase;
import cs1302.gameutil.Token;
import cs1302.gameutil.TokenGrid;

/**
 * {@code ConnectFour} represents a two-player connection game involving a two-dimensional grid of
 * {@linkplain cs1302.gameutil.Token tokens}. When a {@code ConnectFour} game object is
 * constructed, several instance variables representing the game's state are initialized and
 * subsequently accessible, either directly or indirectly, via "getter" methods. Over time, the
 * values assigned to these instance variables should change so that they always reflect the
 * latest information about the state of the game. Most of these changes are described in the
 * project's <a href="https://github.com/cs1302uga/cs1302-c4-alpha#functional-requirements">
 * functional requirements</a>.
 */
public class ConnectFour {

    //----------------------------------------------------------------------------------------------
    // INSTANCE VARIABLES: You should NOT modify the instance variable declarations below.
    // You should also NOT add any additional instance variables. Static variables should
    // also NOT be added.
    //----------------------------------------------------------------------------------------------

    private int rows;        // number of grid rows
    private int cols;        // number of grid columns
    private Token[][] grid;  // 2D array of tokens in the grid
    private Token[] player;  // 1D array of player tokens (length 2)
    private int numDropped;  // number of tokens dropped so far
    private int lastDropRow; // row index of the most recent drop
    private int lastDropCol; // column index of the most recent drop
    private GamePhase phase; // current game phase

    //----------------------------------------------------------------------------------------------
    // CONSTRUCTOR
    //----------------------------------------------------------------------------------------------

    /**
     * Constructs a {@link cs1302.game.ConnectFour} game with a grid that has {@code rows}-many
     * rows and {@code cols}-many columns. All of the game's instance variables are expected to
     * be initialized by this constructor as described in the project's
     * <a href="https://github.com/cs1302uga/cs1302-c4-alpha#functional-requirements">functional
     * requirements</a>.
     *
     * @param rows the number of grid rows
     * @param cols the number of grid columns
     * @throws IllegalArgumentException if the value supplied for {@code rows} or {@code cols} is
     *     not supported. The following values are supported: {@code 6 <= rows <= 9} and
     *     {@code 7 <= cols <= 9}.
     */
    public ConnectFour(int rows, int cols)  {
        // throw IllegalArgumentException for rows and columns
        if (rows < 6 || rows > 9) {
            throw new IllegalArgumentException("Rows must be between 6 and 9 (including)");
        } else if (cols < 7 || cols > 9) {
            throw new IllegalArgumentException("Columns must be between 7 and 9 (including)");
        }

        //initialize instance variables
        this.rows = rows;
        this.cols = cols;

        this.grid = new Token[rows][cols];
        this.player = new Token[2];

        this.numDropped = 0;
        this.lastDropRow = -1;
        this.lastDropCol = -1;

        this.phase = GamePhase.NEW;
    } // ConnectFour

    //----------------------------------------------------------------------------------------------
    // INSTANCE METHODS
    //----------------------------------------------------------------------------------------------

    /**
     * Return the number of rows in the game's grid.
     *
     * @return the number of rows
     */
    public int getRows() {
        return this.rows;
    } // getRows

    /**
     * Return the number of columns in the game's grid.
     *
     * @return the number of columns
     */
    public int getCols() {
        return this.cols;
    } // getCols

    /**
     * Return whether {@code row} and {@code col} specify a location inside this game's grid.
     *
     * @param row the position's row index
     * @param col the positions's column index
     * @return {@code true} if {@code row} and {@code col} specify a location inside this game's
     *     grid and {@code false} otherwise
     */
    public boolean isInBounds(int row, int col) {
        boolean rowBounds = true;
        if (row < 0 || row >= this.rows) {
            rowBounds = false;
        }

        boolean colBounds = true;
        if (col < 0 || col >= this.cols) {
            colBounds = false;
        }

        if (rowBounds == false || colBounds == false) {
            return false;
        } else {
            return true;
        }
    } // isInBounds


    /**
     * Return the grid {@linkplain cs1302.gameutil.Token token} located at the specified position
     * or {@code null} if no token has been dropped into that position.
     *
     * @param row the token's row index
     * @param col the token's column index
     * @return the grid token located in row {@code row} and column {@code col}, if it exists;
     *     otherwise, the value {@code null}
     * @throws IndexOutOfBoundsException if {@code row} and {@code col} specify a position that is
     *     not inside this game's grid.
     */
    public Token getTokenAt(int row, int col) {
        if (!isInBounds(row, col)) {
            throw new IndexOutOfBoundsException("Position is not inside the grid.");
        }

        return grid[row][col];
    } // getTokenAt

    /**
     * Set the first player token and second player token to {@code token0} and {@code token1},
     * respectively. If the current game phase is {@link cs1302.gameutil.GamePhase#NEW}, then
     * this method changes the game phase to {@link cs1302.gameutil.GamePhase#READY}, but only
     * if no exceptions are thrown.
     *.
     * @param token0 token for first player
     * @param token1 token for second player
     * @throws NullPointerException if {@code token0} or {@code token1} is {@code null}.
     * @throws IllegalArgumentException if {@code token0 == token1}.
     * @throws IllegalStateException if {@link #getPhase getPhase()} returns
     *     {@link cs1302.gameutil.GamePhase#PLAYABLE} or {@link cs1302.gameutil.GamePhase#OVER}.
     */
    public void setPlayerTokens(Token token0, Token token1) {
        if (token0 == null || token1 == null) {
            throw new NullPointerException("First and/or second player cannot be null.");
        }
        if (token0 == token1) {
            throw new IllegalArgumentException("First and second player cannot be equal.");
        }
        if (this.phase == GamePhase.PLAYABLE || this.phase == GamePhase.OVER) {
            throw new IllegalStateException("Players cannot be set after game is playing or over.");
        }

        // set player tokens
        this.player[0] = token0;
        this.player[1] = token1;

        if (this.phase == GamePhase.NEW) {
            this.phase = GamePhase.READY;
        }
    } // setPlayerTokens

    /**
     * Return a player's token.
     *
     * @param player the player ({@code 0} for first player and {@code 1} for second player)
     * @return the token for the specified player
     * @throws IllegalArgumentException if {@code player} is neither {@code 0} nor {@code 1}
     * @throws IllegalStateException if {@link #getPhase getPhase()} returns
     *     {@link cs1302.gameutil.GamePhase#NEW}.
     */
    public Token getPlayerToken(int player) {
        if (player != 0 || player != 1) {
            throw new IllegalArgumentException("Player is invalid");
        }
        if (this.phase == GamePhase.NEW) {
            throw new IllegalStateException("Player tokens are not set");
        }

        return this.player[player];
    } // getPlayerToken

    /**
     * Return the number of tokens that have been dropped into this game's grid so far.
     *
     * @return the number of dropped tokens
     * @throws IllegalStateException if {@link #getPhase getPhase()} returns
     *     {@link cs1302.gameutil.GamePhase#NEW} or {@link cs1302.gameutil.GamePhase#READY}.
     */
    public int getNumDropped() {
        if (this.phase == GamePhase.NEW || this.phase == GamePhase.READY) {
            throw new IllegalStateException("Cannot get num of tokens dropped before game starts");
        }

        return numDropped;
    } // getNumDropped

    /**
     * Return the row index of the last (i.e., the most recent) token dropped into this
     * game's grid.
     *
     * @return the row index of the last drop
     * @throws IllegalStateException if {@link #getPhase getPhase()} returns
     *     {@link cs1302.gameutil.GamePhase#NEW} or {@link cs1302.gameutil.GamePhase#READY}.
     */
    public int getLastDropRow() {
        if (this.phase == GamePhase.NEW || this.phase == GamePhase.READY) {
            throw new IllegalStateException("Cannot get last token dropped before game starts");
        }

        return this.lastDropRow;
    } // getLastDropRow

    /**
     * Return the col index of the last (i.e., the most recent) token dropped into this
     * game's grid.
     *
     * @return the column index of the last drop
     * @throws IllegalStateException if {@link #getPhase getPhase()} returns
     *     {@link cs1302.gameutil.GamePhase#NEW} or {@link cs1302.gameutil.GamePhase#READY}.
     */
    public int getLastDropCol() {
        if (this.phase == GamePhase.NEW || this.phase == GamePhase.READY) {
            throw new IllegalStateException("Cannot get last token dropped before game starts");
        }

        return this.lastDropCol;
    } // getLastDropCol

    /**
     * Return the current game phase.
     *
     * @return current game phase
     */
    public GamePhase getPhase() {
        return this.phase;
    } // getPhase

    /**
     * Drop a player's token into a specific column in the grid. This method should not enforce turn
     * order -- that is the players' responsibility should they desire an polite and honest game.
     *
     * <strong>NOTE:</strong> This method does not call {@link #isLastDropConnectFour}. If you
     * want to use {@link #isLastDropConnectFour} to determine a winner after each token is dropped,
     * you must call it separately.
     *
     * @param player the player ({@code 0} for first player and {@code 1} for second player)
     * @param col the grid column where the token will be dropped
     * @throws IndexOutOfBoundsException if {@code col} is not a valid column index
     * @throws IllegalArgumentException if {@code player} is neither {@code 0} nor {@code 1}
     * @throws IllegalStateException if {@link #getPhase getPhase()} does not return
     *    {@link cs1302.gameutil.GamePhase#READY} or {@link cs1302.gameutil.GamePhase#PLAYABLE}
     * @throws IllegalStateException if the specified column in the grid is full
     */
    public void dropToken(int player, int col) {
        if (col < 0 || col >= this.cols) {
            throw new IndexOutOfBoundsException("Column entered is not a valid column");
        }

        if (player != 0 && player != 1) {
            throw new IllegalArgumentException("Player is invalid");
        }

        if (this.phase != GamePhase.READY && this.phase != GamePhase.PLAYABLE) {
            throw new IllegalStateException("Tokens can only be dropped when playing");
        }

        int newDrop = -1;
        for (int i = this.rows - 1; i >= 0; i--) {
            if (this.grid[i][col] == null) {
                newDrop = i;
                break;
            }
        }

        if (newDrop == -1) {
            throw new IllegalStateException("Column is full, cannot drop token there");
        }

        this.grid[newDrop][col] = this.player[player];

        this.numDropped++;
        this.lastDropRow = newDrop;
        this.lastDropCol = col;

        if (this.phase == GamePhase.READY) {
            this.phase = GamePhase.PLAYABLE;
        }
    } // dropToken

    /**
     * Return {@code true} if the last token dropped via {@link #dropToken} created a
     * <em>connect four</em>. A <em>connect four</em> is a sequence of four equal tokens (i.e., they
     * have the same color) -- this sequence can occur horizontally, vertically, or diagonally.
     * If the grid is full or the last drop created a <em>connect four</em>, then this method
     * changes the game's phase to {@link cs1302.gameutil.GamePhase#OVER}.
     *
     * <p>
     * <strong>NOTE:</strong> The only instance variable that this method might change, if
     * applicable, is ``phase``.
     *
     * <p>
     * <strong>NOTE:</strong> If you want to use this method to determine a winner, then you must
     * call it after each call to {@link #dropToken}.
     *
     * @return {@code true} if the last token dropped created a <em>connect four</em>, else
     *     {@code false}
     */
    public boolean isLastDropConnectFour() {
        if (this.lastDropRow < 0 || this.lastDropCol < 0) {
            return false;
        }

        Token token = this.grid[this.lastDropRow][this.lastDropCol];
        if (token == null) {
            return false;
        }

        if (checkHorizontal(this.lastDropRow, this.lastDropCol, token)) {
            this.phase = GamePhase.OVER;
            return true;
        }

        if (checkVertical(this.lastDropRow, this.lastDropCol, token)) {
            this.phase = GamePhase.OVER;
            return true;
        }

        if (checkDiagonalDown(this.lastDropRow, this.lastDropCol, token)) {
            this.phase = GamePhase.OVER;
            return true;
        }

        if (checkDiagonalUp(this.lastDropRow, this.lastDropCol, token)) {
            this.phase = GamePhase.OVER;
            return true;
        }

        if (this.numDropped == (this.rows * this.cols)) {
            this.phase = GamePhase.OVER;
        }

        return false;


    } // isLastDropConnectFour

    //----------------------------------------------------------------------------------------------
    // ADDITIONAL METHODS: If you create any additional methods, then they should be placed in the
    // space provided below.
    //----------------------------------------------------------------------------------------------

    /**
     * This method checks for a connect four horizontally.
     *
     * @param row this is the row variable
     * @param col this is the column variable
     * @param token this is the token for the current player
     * @return true if connectFour
     */
    private boolean checkHorizontal(int row, int col, Token token) {
        int counter = 0;
        boolean checker = false;

        for (int i = 0; i < this.cols; i++) {
            if (this.grid[row][i] == token) {
                counter++;
                if (i == col) {
                    checker = true;
                }
                if (counter >= 4 && checker) {
                    return true;
                }
            } else {
                counter = 0;
                checker = false;
            }
        }

        return false;
    } // checkHorizontal


    /**
     * This method checks for a connect four vertically.
     *
     * @param row this is the row variable
     * @param col this is the column variable
     * @param token this is the token for the current player
     * @return true if connectFour
     */
    private boolean checkVertical(int row, int col, Token token) {
        int counter = 0;
        boolean checker = false;

        for (int x = 0; x < this.rows; x++) {
            if (this.grid[x][col] == token) {
                counter++;
                if (x == row) {
                    checker = true;
                }
                if (counter >= 4 && checker) {
                    return true;
                }
            } else {
                counter = 0;
                checker = false;
            }
        }

        return false;
    } // checkVertical

    /**
     * This method checks for a connect four diagonally down.
     *
     * @param row this is the row variable
     * @param col this is the column variable
     * @param token this is the token for the current player
     * @return true if connectFou
     */
    private boolean checkDiagonalDown(int row, int col, Token token) {
        int r = row;
        int c = col;
        while (r > 0 && c > 0) {
            r--;
            c--;
        }

        int counter = 0;
        boolean checker = false;

        while (r < this.rows && c < this.cols) {
            if (this.grid[r][c] == token) {
                counter++;
                if (r == row && c == col) {
                    checker = true;
                }
                if (counter >= 4 && checker) {
                    return true;
                }
            } else {
                counter = 0;
                checker = false;
            }
            r++;
            c++;
        }

        return false;
    } // checkDiagonalDown

    /**
     * This method checks for a connect four diagonally up.
     *
     * @param row this is the row variable
     * @param col this is the column variable
     * @param token this is the token for the current player
     * @return true if connectFour
     */
    private boolean checkDiagonalUp(int row, int col, Token token) {
        int r = row;
        int c = col;

        while (r < (this.rows - 1) && c > 0) {
            r++;
            c--;
        }

        int counter = 0;
        boolean checker = false;

        while (r >= 0 && c < this.cols) {
            if (this.grid[r][c] == token) {
                counter++;
                if (r == row && c == col) {
                    checker = true;
                }
                if (counter >= 4 && checker) {
                    return true;
                }
            } else {
                counter = 0;
                checker = false;
            }
            r--;
            c++;
        }

        return false;
    } // checkDiagonalUp

    //----------------------------------------------------------------------------------------------
    // DO NOT MODIFY THE METHODS BELOW!
    //----------------------------------------------------------------------------------------------

    /**
     * <strong>DO NOT MODIFY:</strong>
     * Print the game grid to standard output. This method assumes that the constructor
     * is implemented correctly.
     *
     * <p>
     * <strong>NOTE:</strong> This method should not be modified!
     */
    public void printGrid() {
        TokenGrid.println(this.grid);
    } // printGrid

} // ConnectFour
