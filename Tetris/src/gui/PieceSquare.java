package gui;

import javax.swing.*;
import java.awt.*;

public class PieceSquare extends JLabel {
    public PieceSquare(final Color color) {
        this.setBounds(0, 0, 10, 10);
        this.setBackground(color);
        this.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        this.setOpaque(true);
    }
}
