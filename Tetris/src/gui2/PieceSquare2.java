package gui2;

import javax.swing.*;
import java.awt.*;

public class PieceSquare2 extends JLabel {
    public PieceSquare2(final Color color, final int size) {
        this.setBounds(0, 0, size, size);
        this.setBackground(color);
        this.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
        this.setOpaque(true);
    }
}
