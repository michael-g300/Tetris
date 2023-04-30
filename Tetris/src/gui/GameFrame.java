package gui;

import components.StandardBoard;
import pieces.Piece;

import javax.swing.*;
import java.awt.*;
import java.security.SecureRandom;
import java.util.List;

public class GameFrame extends JFrame {
    private static final int PIXELS_PER_SQUARE = 20;
    private final List<Piece> m_gamePieces;
    private GamePanel m_gamePanel;
    private GamePanel m_nextPiecePanel;
    private Piece m_currentPiece;
    private Piece m_nextPiece;
    public GameFrame(final List<Piece> gamePieces) {
        m_gamePieces = gamePieces;
        this.setSize(500,500);
        this.setTitle("TETRIS");
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ImageIcon icon = new ImageIcon("tetris_logo.png");
        this.setIconImage(icon.getImage());
        this.getContentPane().setBackground(Color.MAGENTA);
        this.setVisible(true);
        this.setLocationRelativeTo(null);
    }

}
