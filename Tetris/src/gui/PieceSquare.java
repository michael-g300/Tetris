package gui;

import javax.swing.*;
import java.awt.*;

public class PieceSquare extends JLabel {
    public PieceSquare(final Color color, final int size) {
        this.setBounds(0, 0, size, size);
        this.setBackground(color);
        this.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
        this.setOpaque(true);
    }
}
