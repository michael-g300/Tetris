import components.Position;
import components.StandardBoard;
import gui.GameFrame;
import gui.GamePanel;
import gui.ScorePanel;
import piece_factory.*;
import pieces.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.security.SecureRandom;
import java.util.concurrent.Executors;

public class GameManager {
    private static final int PIXELS_PER_SQUARE = 25;
    private static final PieceFactory STANDARD_PIECE_FACTORY = new PieceFactory();
    static {
        STANDARD_PIECE_FACTORY.register(0, new I_pieceCreator());
        STANDARD_PIECE_FACTORY.register(1, new O_pieceCreator());
        STANDARD_PIECE_FACTORY.register(2, new T_pieceCreator());
        STANDARD_PIECE_FACTORY.register(3, new L_pieceCreator());
        STANDARD_PIECE_FACTORY.register(4, new J_pieceCreator());
        STANDARD_PIECE_FACTORY.register(5, new S_pieceCreator());
        STANDARD_PIECE_FACTORY.register(6, new Z_pieceCreator());
    }
private final GameFrame m_gameFrame;
    private final GamePanel m_gamePanel;
    private final GamePanel m_nextPiecePanel;
    private final ScorePanel m_scorePanel;
    private Piece m_currentPiece;
    private int m_nextPieceIdx;

    public GameManager() {
        m_gameFrame = new GameFrame();

        var gameBoard = new StandardBoard(18, 10);
        m_gamePanel = new GamePanel(gameBoard, PIXELS_PER_SQUARE);
        m_gamePanel.setBounds(10, 10, 10 * PIXELS_PER_SQUARE, 18 * PIXELS_PER_SQUARE);
        m_currentPiece = generateNextPiece();
        m_gamePanel.addPiece(m_currentPiece);
        m_gamePanel.drawBoard();
        m_gamePanel.setVisible(true);

        var nextPieceBoard = new StandardBoard(3, 8);
        m_nextPiecePanel = new GamePanel(nextPieceBoard, PIXELS_PER_SQUARE);
        m_nextPiecePanel.setBounds(m_gamePanel.getWidth() + 20, 10, 8 * PIXELS_PER_SQUARE, 4 * PIXELS_PER_SQUARE);
        m_nextPieceIdx = new SecureRandom().nextInt(0, StandardPieces.values().length - 1);
        var nextPiece = STANDARD_PIECE_FACTORY.create(m_nextPieceIdx);
        m_nextPiecePanel.addPiece(nextPiece);
        m_nextPiecePanel.moveDown(nextPiece);
        m_nextPiecePanel.drawBoard();
        m_nextPiecePanel.setVisible(true);

        m_scorePanel = new ScorePanel();
        m_scorePanel.setBounds(m_gamePanel.getWidth() + 20, m_nextPiecePanel.getHeight() + 20, m_nextPiecePanel.getWidth(), 200);
        m_scorePanel.setVisible(true);

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

        m_gameFrame.add(m_scorePanel);
        m_gameFrame.add(m_nextPiecePanel);
        m_gameFrame.add(m_gamePanel);
        m_gameFrame.setVisible(true);

        gameOn();
    }
    private void gameOn() {
        boolean isThereStillRoom = true;
        while (isThereStillRoom) {
            holdPiece();
            if (m_gamePanel.canPieceMoveDown(m_currentPiece)) {
                m_gamePanel.repaint();
                automaticPieceMovement();
            }
            else {
                //System.out.println("\nPiece has reached bottom");
                isThereStillRoom = updateGamePanel();

                updateNextPiecePanel();

                m_scorePanel.update(m_gamePanel.getFinishedRows());

                var executor = Executors.newFixedThreadPool(2);
                executor.submit(() -> m_gamePanel.repaint());
                executor.submit(() -> m_nextPiecePanel.repaint());
                executor.submit(() -> m_scorePanel.repaint());
                executor.shutdown();
            }
        }
        displayGameResult();
    }

    private void displayGameResult() {
        System.out.println("Game Over :(");
        JLabel gameConclusion = new JLabel();
        gameConclusion.setBounds(10, 10, 450, 350);
        gameConclusion.setBackground(new Color(242, 240, 82));
        gameConclusion.setForeground(Color.RED);
        gameConclusion.setFont(new Font("MV Boli", Font.BOLD, 20));
        gameConclusion.setText("Game Over  :(\nLines cleared: " + m_gamePanel.getFinishedRows());
        gameConclusion.setVerticalTextPosition(JLabel.CENTER);
        gameConclusion.setHorizontalTextPosition(JLabel.CENTER);
        gameConclusion.setOpaque(true);
        gameConclusion.setVisible(true);

        JPanel finalPanel = new JPanel();
        finalPanel.setBounds(10, 10, 475, 375);
        finalPanel.add(gameConclusion);
        finalPanel.setVisible(true);

        JLayeredPane layeredPane = new JLayeredPane();
        layeredPane.setBounds(0, 0, 500, 500);
        m_gameFrame.add(layeredPane);
        m_gameFrame.add(finalPanel);
        m_gameFrame.repaint();
    }

    private void updateNextPiecePanel() {
        m_nextPiecePanel.clearBoard();
        m_nextPieceIdx = new SecureRandom().nextInt(0, StandardPieces.values().length - 1);
        var nextPiece = STANDARD_PIECE_FACTORY.create(m_nextPieceIdx);
        nextPiece.setCenterPosition(new Position(0, 3));
        m_nextPiecePanel.addPiece(nextPiece);
        m_nextPiecePanel.moveDown(nextPiece);
    }

    private boolean updateGamePanel() {
        boolean isThereStillRoom;
        m_gamePanel.removeFinishedRows();
        m_currentPiece = STANDARD_PIECE_FACTORY.create(m_nextPieceIdx);
        //System.out.println("New piece dispatched : " + m_currentPiece.getClass().getName());
        m_nextPieceIdx = new SecureRandom().nextInt(0, StandardPieces.values().length - 1);
        isThereStillRoom = m_gamePanel.addPiece(m_currentPiece);
        return isThereStillRoom;
    }

    private void automaticPieceMovement() {
        //System.out.println("Automatic piece down movement");
        m_gamePanel.moveDown(m_currentPiece);
        m_gamePanel.repaint();
    }

    private void holdPiece() {
        try {
            final int moveTime = 700;
            Thread.sleep(m_gamePanel.getFinishedRows() / 10 >= 9 ? 100 : moveTime - (m_gamePanel.getFinishedRows() / 10) * 100L);
        }
        catch (InterruptedException e) {
            System.out.println("Unable to stop executing thread.");
            throw new RuntimeException(e);
        }
    }

    private Piece generateNextPiece() {
        var rand = new SecureRandom();
        var nextPieceIdx = rand.nextInt(0, StandardPieces.values().length - 1);
        return STANDARD_PIECE_FACTORY.create(nextPieceIdx);
    }
    public class DownAction extends AbstractAction {
        @Override
        public void actionPerformed(ActionEvent e) {
            //System.out.println("down action");
            m_gamePanel.moveDown(m_currentPiece);
            m_gamePanel.repaint();
        }
    }
    public class UpAction extends AbstractAction {
        @Override
        public void actionPerformed(ActionEvent e) {
            //System.out.println("up action");
            m_gamePanel.rotate(m_currentPiece);
            m_gamePanel.repaint();
        }
    }
    public class RightAction extends AbstractAction {
        @Override
        public void actionPerformed(ActionEvent e) {
            //System.out.println("right action");
            m_gamePanel.moveRight(m_currentPiece);
            m_gamePanel.repaint();
        }
    }
    public class LeftAction extends AbstractAction {
        @Override
        public void actionPerformed(ActionEvent e) {
            //System.out.println("left action");
            m_gamePanel.moveLeft(m_currentPiece);
            m_gamePanel.repaint();
        }
    }
}
