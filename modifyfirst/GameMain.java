package modifyfirst;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class GameMain extends JPanel {
    public static final String TITLE = "Tic Tac Toe";
    public static final Color COLOR_CROSS = new Color(216, 7, 239);  // Purple
    public static final Color COLOR_NOUGHT = new Color(64, 154, 225); // Blue
    public static int BOARD_SIZE = 3;

    private Board board;
    private State currentState;
    private Seed currentPlayer;
    private JLabel statusBar;

    public GameMain() {
        setLayout(new BorderLayout());
        board = new Board(BOARD_SIZE);
        statusBar = new JLabel("X's Turn");
        statusBar.setFont(new Font("Arial", Font.BOLD, 16));
        statusBar.setHorizontalAlignment(SwingConstants.CENTER);
        add(statusBar, BorderLayout.SOUTH);
        initGame();

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int mouseX = e.getX();
                int mouseY = e.getY();
                int row = mouseY / Cell.SIZE;
                int col = mouseX / Cell.SIZE;

                if (currentState == State.PLAYING) {
                    if (row >= 0 && row < BOARD_SIZE && col >= 0 && col < BOARD_SIZE
                            && board.cells[row][col].content == Seed.NO_SEED) {
                        currentState = board.stepGame(currentPlayer, row, col);
                        currentPlayer = (currentPlayer == Seed.CROSS) ? Seed.NOUGHT : Seed.CROSS;
                        updateStatus();
                    }
                } else {
                    newGame();
                }
                repaint();
            }
        });
    }

    public void initGame() {
        board.initGame();
        currentState = State.PLAYING;
        currentPlayer = Seed.CROSS;
    }

    public void newGame() {
        board.initGame();
        currentState = State.PLAYING;
        currentPlayer = Seed.CROSS;
        updateStatus();
        repaint();
    }

    private void updateStatus() {
        if (currentState == State.PLAYING) {
            statusBar.setForeground(Color.BLACK);
            statusBar.setText((currentPlayer == Seed.CROSS) ? "X's Turn" : "O's Turn");
        } else if (currentState == State.DRAW) {
            statusBar.setForeground(Color.RED);
            statusBar.setText("It's a Draw! Click to play again.");
        } else if (currentState == State.CROSS_WON) {
            statusBar.setForeground(Color.RED);
            statusBar.setText("'X' Won! Click to play again.");
        } else if (currentState == State.NOUGHT_WON) {
            statusBar.setForeground(Color.RED);
            statusBar.setText("'O' Won! Click to play again.");
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Get the dimensions of the current panel
        int panelWidth = getWidth();
        int panelHeight = getHeight();

        // Calculate the total board size
        int boardWidth = Cell.SIZE * Board.COLS;
        int boardHeight = Cell.SIZE * Board.ROWS;

        // Calculate offsets to center the board
        int offsetX = (panelWidth - boardWidth) / 2;
        int offsetY = (panelHeight - boardHeight) / 2;

        // Draw the board grid and cells
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.translate(offsetX, offsetY); // Shift the drawing starting point
        board.paint(g2d);
        g2d.dispose();
    }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            LoginPage loginPage = new LoginPage();
            loginPage.setVisible(true);

            if (loginPage.isLoginSuccessful()) {
                BoardSizeSelection boardSizeSelection = new BoardSizeSelection();
                boardSizeSelection.setVisible(true);

                BOARD_SIZE = boardSizeSelection.getSelectedBoardSize();
                JFrame frame = new JFrame(TITLE);
                frame.setContentPane(new GameMain());
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.pack();
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
            }
        });
    }
}