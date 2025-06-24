package modifyfirst;

import javax.swing.*;
import java.awt.*;

public class BoardSizeSelection extends JDialog {
    private int selectedBoardSize = 3;

    public BoardSizeSelection() {
        setTitle("Select Board Size");
        setModal(true);
        setLayout(new GridLayout(3, 1));

        JButton btn3x3 = new JButton("3x3 Board");
        btn3x3.addActionListener(e -> {
            selectedBoardSize = 3;
            dispose();
        });
        add(btn3x3);

        JButton btn5x5 = new JButton("5x5 Board");
        btn5x5.addActionListener(e -> {
            selectedBoardSize = 5;
            dispose();
        });
        add(btn5x5);

        JButton btn7x7 = new JButton("7x7 Board");
        btn7x7.addActionListener(e -> {
            selectedBoardSize = 7;
            dispose();
        });
        add(btn7x7);

        pack();
        setLocationRelativeTo(null);
    }

    public int getSelectedBoardSize() {
        return selectedBoardSize;
    }
}