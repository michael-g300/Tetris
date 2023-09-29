package gui;

import javax.swing.*;
import java.awt.*;

public class PlayerNameFrame extends JFrame {
    private static final int FRAME_SIZE = 530;
    public PlayerNameFrame() {
        this.setSize(FRAME_SIZE, FRAME_SIZE);
        this.setTitle("TETRIS");
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ImageIcon icon = new ImageIcon("tetris_logo.png");
        this.setIconImage(icon.getImage());
        this.getContentPane().setBackground(new Color(240, 192, 232));
        this.setLayout(null);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }
}
