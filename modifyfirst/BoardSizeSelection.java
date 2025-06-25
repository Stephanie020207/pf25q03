package modifyfirst;

import javax.swing.*;
import java.awt.*;

public class BoardSizeSelection extends JDialog {
    private int selectedBoardSize = 3;

    public BoardSizeSelection() {
        setTitle("Select Board Size");
        setUndecorated(true); // Remove window decorations
        setModal(true);

        // Set full-screen size
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setSize(screenSize);
        setLayout(new BorderLayout());

        // Background Panel
        JPanel backgroundPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.setColor(new Color(34, 40, 49)); // Dark background
                g.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        backgroundPanel.setLayout(new GridBagLayout());
        add(backgroundPanel);

        // Buttons Panel
        JPanel buttonPanel = new JPanel(new GridLayout(3, 1, 20, 20));
        buttonPanel.setOpaque(false);
        buttonPanel.setPreferredSize(new Dimension(400, 300)); // Size of the button panel

        JButton btn3x3 = createStyledButton("3x3 Board");
        btn3x3.addActionListener(e -> {
            selectedBoardSize = 3;
            dispose();
        });
        buttonPanel.add(btn3x3);

        JButton btn5x5 = createStyledButton("5x5 Board");
        btn5x5.addActionListener(e -> {
            selectedBoardSize = 5;
            dispose();
        });
        buttonPanel.add(btn5x5);

        JButton btn7x7 = createStyledButton("7x7 Board");
        btn7x7.addActionListener(e -> {
            selectedBoardSize = 7;
            dispose();
        });
        buttonPanel.add(btn7x7);

        backgroundPanel.add(buttonPanel);
        setLocationRelativeTo(null);
    }

    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 24));
        button.setBackground(new Color(52, 73, 94)); // Button background
        button.setForeground(Color.WHITE); // Text color
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        return button;
    }

    public int getSelectedBoardSize() {
        return selectedBoardSize;
    }
}