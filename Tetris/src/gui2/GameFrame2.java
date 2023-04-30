package gui2;

import javax.swing.*;
import java.awt.*;

public class GameFrame2 extends JFrame {
    public GameFrame2() {
        this.setSize(500,500);
        this.setTitle("TETRIS");
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ImageIcon icon = new ImageIcon("tetris_logo.png");
        this.setIconImage(icon.getImage());
        this.getContentPane().setBackground(Color.MAGENTA);
        this.setLayout(null);
        this.setVisible(true);
        this.setLocationRelativeTo(null);
    }
}
