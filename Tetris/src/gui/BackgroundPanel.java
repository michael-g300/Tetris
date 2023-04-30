package gui;

import components.Position;
import pieces.Piece;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;

public class BackgroundPanel extends JPanel {
    private static final int WIDTH_FACTOR = 8;
    private static final int HEIGHT_FACTOR = 4;
    private final int m_pixelsPerSquare;
    public BackgroundPanel(final int pixelsPerSquare) {
        m_pixelsPerSquare = pixelsPerSquare;
        this.setBounds(0, 0, WIDTH_FACTOR * m_pixelsPerSquare, HEIGHT_FACTOR * m_pixelsPerSquare);
        this.setBackground(new Color(202, 245, 188));
        this.setBorder(new LineBorder(Color.BLACK));
        this.setLayout(null);
        this.setVisible(true);
    }

}
