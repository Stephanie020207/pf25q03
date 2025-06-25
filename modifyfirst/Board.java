package modifyfirst;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Board {
    public static int ROWS; // Number of rows in the board
    public static int COLS; // Number of columns in the board
    public Cell[][] cells;  // 2D array of cells representing the board
    private BufferedImage background; // To hold the background image

    public Board(int size) {
        ROWS = size;
        COLS = size;
        initGame();
    }

    public void initGame() {
        cells = new Cell[ROWS][COLS];
        for (int row = 0; row < ROWS; ++row) {
            for (int col = 0; col < COLS; ++col) {
                cells[row][col] = new Cell(row, col);
            }
        }
    }

    public State stepGame(Seed player, int selectedRow, int selectedCol) {
        // Update the cell with the player's move.
        cells[selectedRow][selectedCol].content = player;

        // Check for a win in the row
        boolean winRow = true;
        for (int col = 0; col < COLS; ++col) {
            if (cells[selectedRow][col].content != player) {
                winRow = false;
                break;
            }
        }

        // Check for a win in the column
        boolean winCol = true;
        for (int row = 0; row < ROWS; ++row) {
            if (cells[row][selectedCol].content != player) {
                winCol = false;
                break;
            }
        }

        // Check for a win in the main diagonal
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

        // Check for a win in the anti-diagonal
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

    public void paint(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        // Set the background color to blue and fill the entire board area
        g2d.setColor(Color.GRAY);
        g2d.fillRect(0, 0, Cell.SIZE * COLS, Cell.SIZE * ROWS);

        // Draw the grid lines
        int cellSize = Cell.SIZE;
        for (int row = 1; row < ROWS; ++row) {
            g2d.fillRect(0, cellSize * row - 1, cellSize * COLS, 2); // Horizontal line
        }
        for (int col = 1; col < COLS; ++col) {
            g2d.fillRect(cellSize * col - 1, 0, 2, cellSize * ROWS); // Vertical line
        }

        // Paint all the cells
        for (int row = 0; row < ROWS; ++row) {
            for (int col = 0; col < COLS; ++col) {
                cells[row][col].paint(g);
            }
        }
    }
}