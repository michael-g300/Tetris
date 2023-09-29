import components.Position;
import components.StandardBoard;
import gui.*;
import piece_factory.*;
import pieces.Piece;
import pieces.StandardPieces;
import record_keeping.HighScoreTable;
import record_keeping.PlayerScore;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.security.SecureRandom;

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
    private final HighScoreTable m_highScoreTable;

    public GameManager(final boolean regularWidth, final int startLevel) {
        m_highScoreTable = new HighScoreTable();

        m_gameFrame = new GameFrame(regularWidth);

        var gameBoard = new StandardBoard(18, regularWidth ? 10 : 20, startLevel - 1);
        m_gamePanel = new GamePanel(gameBoard, PIXELS_PER_SQUARE);
        m_gamePanel.setBounds(10, 10, (regularWidth ? 10 : 20) * PIXELS_PER_SQUARE, 18 * PIXELS_PER_SQUARE);
        m_currentPiece = generateNextPiece();
        m_gamePanel.addPiece(m_currentPiece);
        m_gamePanel.drawBoard();
        m_gamePanel.setVisible(true);

        var nextPieceBoard = new StandardBoard(3, 8, 0);
        m_nextPiecePanel = new GamePanel(nextPieceBoard, PIXELS_PER_SQUARE);
        m_nextPiecePanel.setBounds(m_gamePanel.getWidth() + 20, 10, 8 * PIXELS_PER_SQUARE, 4 * PIXELS_PER_SQUARE);
        m_nextPieceIdx = new SecureRandom().nextInt(0, StandardPieces.values().length - 1);
        var nextPiece = STANDARD_PIECE_FACTORY.create(m_nextPieceIdx);
        m_nextPiecePanel.addPiece(nextPiece);
        m_nextPiecePanel.moveDown(nextPiece);
        m_nextPiecePanel.drawBoard();
        m_nextPiecePanel.setVisible(true);

        m_scorePanel = new ScorePanel(startLevel);
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
                isThereStillRoom = updateGamePanel();

                updateNextPiecePanel();

                m_scorePanel.update(m_gamePanel.getFinishedRows());
                m_gamePanel.repaint();
                m_nextPiecePanel.repaint();
                m_scorePanel.repaint();
            }
        }
        checkHighScore();
    }

    private void checkHighScore() {
        System.out.println("Game Over :(");

        m_gameFrame.dispose();
        if (newHighScoreCheck()) {
            System.out.println("Congratulations! New high score!");
            JFrame playerNameFrame = new PlayerNameFrame();
            JButton submitButton = new JButton("submit");
            submitButton.setBounds(100, 100, 200 ,100);
            submitButton.setBorder(BorderFactory.createEtchedBorder());
            submitButton.setLocation(100, 100);
            JTextField textField = new JTextField("Your name");
            textField.setFont(new Font("Consolas", Font.PLAIN, 25));
            textField.setPreferredSize(new Dimension(250, 40));
            submitButton.addActionListener(e -> {
                System.out.println("Name: " + textField.getText() + " , score: " + m_gamePanel.getFinishedRows());
                m_highScoreTable.updateScores(textField.getText(), m_gamePanel.getFinishedRows());
                playerNameFrame.dispose();
                displayGameResult();
            });
            playerNameFrame.setLayout(new FlowLayout());
            playerNameFrame.add(textField);
            playerNameFrame.add(submitButton);
            playerNameFrame.pack();
            playerNameFrame.setVisible(true);
        }
        else {
            displayGameResult();
        }
    }

    private void displayGameResult() {
        JFrame highScoreFrame = new HighScoreFrame();
        PlayerScore[] playerScores = m_highScoreTable.getPlayersScores();
        StringBuilder tableText = new StringBuilder("<html>");
        tableText.append("--High Score--<br/><br/>");
        for (int i = 0 ; i < playerScores.length ; ++i) {
            if (playerScores[i] == null) {
                break;
            }
            tableText.append(i + 1);
            tableText.append(". ");
            tableText.append(playerScores[i].name());
            tableText.append("       ");
            tableText.append(playerScores[i].score());
            tableText.append("<br/>");
        }
        tableText.append("</html>");
        JLabel highScoreTable = new JLabel();
        highScoreTable.setBounds(10, 10, 470, 350);
        highScoreTable.setFont(new Font("MV Boli", Font.BOLD, 20));
        highScoreTable.setText(tableText.toString());
        highScoreTable.setVerticalTextPosition(JLabel.CENTER);
        highScoreTable.setHorizontalTextPosition(JLabel.CENTER);
        highScoreTable.setOpaque(true);
        highScoreTable.setVisible(true);

        JPanel finalPanel = new JPanel();
        finalPanel.setBounds(10, 10, 490, 375);
        finalPanel.add(highScoreTable);
        finalPanel.setVisible(true);
        highScoreFrame.add(finalPanel);
        highScoreFrame.repaint();

    }

    private boolean newHighScoreCheck() {
        return m_gamePanel.getFinishedRows() > m_highScoreTable.getLowestHighScore();
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
        m_nextPieceIdx = new SecureRandom().nextInt(0, StandardPieces.values().length - 1);
        isThereStillRoom = m_gamePanel.addPiece(m_currentPiece);
        return isThereStillRoom;
    }

    private void automaticPieceMovement() {
        m_gamePanel.moveDown(m_currentPiece);
        m_gamePanel.repaint();
    }

    private void holdPiece() {  //instead - do timer countdown
        try {
            final int moveTime = 700;
            Thread.sleep(m_gamePanel.getCurrentLevel() >= 7 ? 100 : moveTime - m_gamePanel.getCurrentLevel() * 100L);
        }
        catch (InterruptedException e) {
            System.out.println("Unable to stop executing thread :(");
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
            m_gamePanel.moveDown(m_currentPiece);
            m_gamePanel.repaint();
        }
    }
    public class UpAction extends AbstractAction {
        @Override
        public void actionPerformed(ActionEvent e) {
            m_gamePanel.rotate(m_currentPiece);
            m_gamePanel.repaint();
        }
    }
    public class RightAction extends AbstractAction {
        @Override
        public void actionPerformed(ActionEvent e) {
            m_gamePanel.moveRight(m_currentPiece);
            m_gamePanel.repaint();
        }
    }
    public class LeftAction extends AbstractAction {
        @Override
        public void actionPerformed(ActionEvent e) {
            m_gamePanel.moveLeft(m_currentPiece);
            m_gamePanel.repaint();
        }
    }
}
