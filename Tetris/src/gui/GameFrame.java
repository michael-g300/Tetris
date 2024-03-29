package gui;

import javax.swing.*;
import java.awt.*;

public class GameFrame extends JFrame {
    private static final int REGULAR_FRAME_SIZE = 530;
    public GameFrame(final boolean regularWidth) {
        this.setSize(regularWidth ? REGULAR_FRAME_SIZE : REGULAR_FRAME_SIZE + 215,REGULAR_FRAME_SIZE);
        this.setTitle("TETRIS");
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ImageIcon icon = new ImageIcon("tetris_logo.png");
        this.setIconImage(icon.getImage());
        this.getContentPane().setBackground(new Color(192, 240, 205));
        this.setLayout(null);
        this.setLocationRelativeTo(null);
    }
}
