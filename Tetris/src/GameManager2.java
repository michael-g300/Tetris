import components.StandardBoard;
import gui.GamePanel;
import gui2.GameFrame2;
import gui2.GamePanel2;
import pieces.*;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;

public class GameManager2 {
    private final static List<Piece> STANDARD_GAME_PIECES = new ArrayList<>();
    static {
        STANDARD_GAME_PIECES.add(new T_shape());
        STANDARD_GAME_PIECES.add(new I_shape());
        STANDARD_GAME_PIECES.add(new L_shape());
        STANDARD_GAME_PIECES.add(new J_shape());
        STANDARD_GAME_PIECES.add(new S_shape());
        STANDARD_GAME_PIECES.add(new Z_shape());
        STANDARD_GAME_PIECES.add(new O_shape());
    }
    private static final int PIXELS_PER_SQUARE = 20;
    private final GameFrame2 m_gameFrame;
    private final GamePanel2 m_gamePanel;
    private final GamePanel2 m_nextPiecePanel;
    private Piece m_currentPiece;
    private Piece m_nextPiece;
    private int m_moveTime = 1000;
    public GameManager2() {
        m_gameFrame = new GameFrame2();

        var gameBoard = new StandardBoard(18, 10);
        m_gamePanel = new GamePanel2(gameBoard, PIXELS_PER_SQUARE);
        m_gamePanel.setBounds(10, 10, 10 * PIXELS_PER_SQUARE, 18 * PIXELS_PER_SQUARE);
        m_currentPiece = generateNextPiece();
        m_gamePanel.addPiece(m_currentPiece);
        m_gamePanel.drawBoard();
        m_gamePanel.setVisible(true);

        var nextPieceBoard = new StandardBoard(4, 8);
        m_nextPiecePanel = new GamePanel2(nextPieceBoard, PIXELS_PER_SQUARE);
        m_nextPiecePanel.setBounds(m_gamePanel.getWidth() + 20, 10, 8 * PIXELS_PER_SQUARE, 4 * PIXELS_PER_SQUARE);
        m_nextPiece = generateNextPiece();
        m_nextPiecePanel.addPiece(m_nextPiece);
        m_nextPiecePanel.moveDown(m_nextPiece);
        m_nextPiecePanel.drawBoard();
        m_nextPiecePanel.setVisible(true);

        Action downAction = new DownAction();
        Action upAction = new UpAction();
        Action rightAction = new RightAction();
        Action leftAction = new LeftAction();

        m_gamePanel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("UP"),"upAction");
        m_gamePanel.getActionMap().put("upAction", upAction);
        m_gamePanel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("DOWN"), "downAction");
        m_gamePanel.getActionMap().put("downAction", downAction);
        m_gamePanel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("RIGHT"), "rightAction");
        m_gamePanel.getActionMap().put("rightAction", rightAction);
        m_gamePanel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("LEFT"), "leftAction");
        m_gamePanel.getActionMap().put("leftAction", leftAction);

        m_gameFrame.add(m_gamePanel);
        m_gameFrame.add(m_nextPiecePanel);
        m_gameFrame.setVisible(true);

        gameOn();
    }
    private void gameOn() {
        boolean isThereStillRoom = true;
        while (isThereStillRoom) {
            try {
                Thread.sleep(m_moveTime);
            }
            catch (InterruptedException e) {
                System.out.println("Unable to stop executing thread.");
                throw new RuntimeException(e);
            }
            System.out.println("Current piece starting position = " + m_currentPiece.getCenterPosition().toString());
            if (m_gamePanel.canPieceMoveDown(m_currentPiece)) {
                System.out.println("Automatic piece down movement");
                m_gamePanel.moveDown(m_currentPiece);
                m_gamePanel.repaint();
            }
            else {
                m_currentPiece = STANDARD_GAME_PIECES.get(STANDARD_GAME_PIECES.indexOf(m_nextPiece));
                isThereStillRoom = m_gamePanel.addPiece(m_currentPiece);
                m_gamePanel.repaint();

                m_nextPiecePanel.removeAll();
                m_nextPiece = generateNextPiece();
                m_nextPiecePanel.addPiece(m_nextPiece);
                m_nextPiecePanel.moveDown(m_nextPiece);
                m_nextPiecePanel.repaint();
            }
        }
        System.out.println("Game Over :(");
    }
    private Piece generateNextPiece() {
        var rand = new SecureRandom();
        var nextPieceIdx = rand.nextInt(0, STANDARD_GAME_PIECES.size() - 1);
        return STANDARD_GAME_PIECES.get(nextPieceIdx);
    }
    public class DownAction extends AbstractAction {
        @Override
        public void actionPerformed(ActionEvent e) {
            System.out.println("down action");
            m_gamePanel.moveDown(m_currentPiece);
            m_gamePanel.repaint();
        }
    }
    public class UpAction extends AbstractAction {
        @Override
        public void actionPerformed(ActionEvent e) {
            System.out.println("up action");
            m_gamePanel.rotate(m_currentPiece);
            m_gamePanel.repaint();
        }
    }
    public class RightAction extends AbstractAction {
        @Override
        public void actionPerformed(ActionEvent e) {
            System.out.println("right action");
            m_gamePanel.moveRight(m_currentPiece);
            m_gamePanel.repaint();
        }
    }
    public class LeftAction extends AbstractAction {
        @Override
        public void actionPerformed(ActionEvent e) {
            System.out.println("left action");
            m_gamePanel.moveLeft(m_currentPiece);
            m_gamePanel.repaint();
        }
    }
}
