package gui;

import components.Board;
import components.Position;
import pieces.Piece;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;

public class GamePanel extends JPanel {
    private static final Color FREE_CELL_COLOR = Color.lightGray;
    private static final Color OCCUPIED_CELL_COLOR = new Color(5, 104, 176);
    private final Board m_board;
    private final int m_pixelsPerSquare;
    public GamePanel(final Board board, final int pixelsPerSquare) {
        m_pixelsPerSquare = pixelsPerSquare;
        m_board = board;
        this.setBackground(FREE_CELL_COLOR);
        this.setBorder(new LineBorder(Color.BLACK, 2));
        this.setLayout(null);
        this.setVisible(true);
    }
    public void drawBoard() {   //instead of creating new labels each loop, save them and move them each time
        this.removeAll();
        final boolean[][] boardCells = m_board.getCells();
        for (int i = 0 ; i < boardCells.length ; ++i) {
            for (int j = 0 ; j < boardCells[0].length ; ++j) {
                if (boardCells[i][j]) {
                    var occupiedCell = new PieceSquare(OCCUPIED_CELL_COLOR, m_pixelsPerSquare);
                    occupiedCell.setBounds(j * m_pixelsPerSquare, i * m_pixelsPerSquare, m_pixelsPerSquare, m_pixelsPerSquare);
                    this.add(occupiedCell);
                }
            }
        }
    }
    public boolean canPieceMoveDown(final Piece piece) {
        m_board.removePiece(piece);
        for (Position position : piece.positions()) {
            if (position.row() >= m_board.getCells().length - 1 || m_board.getCells()[position.row() + 1][position.col()]) {
                m_board.addPiece(piece);
                return false;
            }
        }
        m_board.addPiece(piece);
        return true;
    }
    public void clearBoard() {
        final int rows = m_board.getCells().length;
        final int cols = m_board.getCells()[0].length;
        for (int i = 0 ; i < rows ; ++i) {
            for (int j = 0 ;j < cols ; ++j) {
                m_board.getCells()[i][j] = false;
            }
        }
    }
    public int getFinishedRows() {
        return m_board.getFinishedRows();
    }
    public int getCurrentLevel() {
        return m_board.getStartingLevel() + m_board.getFinishedRows() / 10;
    }
    public boolean addPiece(final Piece piece) {
        var newPieceAdded = m_board.addPiece(piece);
        drawBoard();
        return newPieceAdded;
    }
    public void removeFinishedRows() {
        m_board.checkFinishedRows();
    }
    public boolean moveRight(final Piece piece) {
        var move = m_board.moveRight(piece);
        drawBoard();
        return move;
    }
    public boolean moveLeft(final Piece piece) {
        var move = m_board.moveLeft(piece);
        drawBoard();
        return move;
    }
    public boolean moveDown(final Piece piece) {
        var move = m_board.moveDown(piece);
        drawBoard();
        return move;
    }
    public boolean rotate(final Piece piece) {
        var move = m_board.rotate(piece);
        drawBoard();
        return move;
    }
}
