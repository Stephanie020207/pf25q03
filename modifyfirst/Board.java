package modifyfirst;

import java.awt.*;

/**
 * The Board class models the game board with dynamic rows and columns.
 */
public class Board {
    public static int ROWS; // Number of rows in the board
    public static int COLS; // Number of columns in the board
    public Cell[][] cells;  // 2D array of cells representing the board

    /**
     * Constructor to initialize the board with a given size.
     *
     * @param size The size of the board (e.g., 3x3, 5x5, etc.)
     */
    public Board(int size) {
        ROWS = size;
        COLS = size;
        initGame();
    }

    /** Initialize the game objects (cells on the board). */
    public void initGame() {
        cells = new Cell[ROWS][COLS];
        for (int row = 0; row < ROWS; ++row) {
            for (int col = 0; col < COLS; ++col) {
                cells[row][col] = new Cell(row, col);
            }
        }
    }

    /**
     * Update the game state after a player makes a move.
     *
     * @param player       The current player (CROSS or NOUGHT).
     * @param selectedRow  The row of the cell where the move is made.
     * @param selectedCol  The column of the cell where the move is made.
     * @return The new state of the game.
     */
    public State stepGame(Seed player, int selectedRow, int selectedCol) {
        // Update the cell with the player's move.
        cells[selectedRow][selectedCol].content = player;

        // Check for a win in the current row.
        boolean winRow = true;
        for (int col = 0; col < COLS; ++col) {
            if (cells[selectedRow][col].content != player) {
                winRow = false;
                break;
            }
        }

        // Check for a win in the current column.
        boolean winCol = true;
        for (int row = 0; row < ROWS; ++row) {
            if (cells[row][selectedCol].content != player) {
                winCol = false;
                break;
            }
        }

        // Check for a win in the main diagonal.
        boolean winDiag = true;
        if (selectedRow == selectedCol) {
            for (int i = 0; i < ROWS; ++i) {
                if (cells[i][i].content != player) {
                    winDiag = false;
                    break;
                }
            }
        } else {
            winDiag = false;
        }

        // Check for a win in the anti-diagonal.
        boolean winAntiDiag = true;
        if (selectedRow + selectedCol == ROWS - 1) {
            for (int i = 0; i < ROWS; ++i) {
                if (cells[i][ROWS - 1 - i].content != player) {
                    winAntiDiag = false;
                    break;
                }
            }
        } else {
            winAntiDiag = false;
        }

        // Determine the game's state.
        if (winRow || winCol || winDiag || winAntiDiag) {
            return (player == Seed.CROSS) ? State.CROSS_WON : State.NOUGHT_WON;
        } else {
            // Check for a draw (all cells filled).
            for (int row = 0; row < ROWS; ++row) {
                for (int col = 0; col < COLS; ++col) {
                    if (cells[row][col].content == Seed.NO_SEED) {
                        return State.PLAYING; // Game is still ongoing.
                    }
                }
            }
            return State.DRAW; // No empty cells, it's a draw.
        }
    }

    /**
     * Paint the board and its cells on the graphics canvas.
     *
     * @param g The Graphics context for painting.
     */
    public void paint(Graphics g) {
        g.setColor(Color.LIGHT_GRAY);
        int cellSize = Cell.SIZE;

        // Draw the grid lines.
        for (int row = 1; row < ROWS; ++row) {
            g.fillRect(0, cellSize * row - 1, cellSize * COLS, 2); // Horizontal line
        }
        for (int col = 1; col < COLS; ++col) {
            g.fillRect(cellSize * col - 1, 0, 2, cellSize * ROWS); // Vertical line
        }

        // Paint all the cells.
        for (int row = 0; row < ROWS; ++row) {
            for (int col = 0; col < COLS; ++col) {
                cells[row][col].paint(g);
            }
        }
    }
}