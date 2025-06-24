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

    public GameMain() {
        setLayout(new BorderLayout());
        board = new Board(BOARD_SIZE);
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
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        board.paint(g);
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