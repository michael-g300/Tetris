package gui;

import components.Board;
import components.Position;
import pieces.Piece;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;

public class GamePanel extends JPanel {
    private static final Color FREE_CELL_COLOR = Color.lightGray;
    private static final Color OCCUPIED_CELL_COLOR = Color.blue;
    private final Board m_board;
    private final int m_pixelsPerSquare;
    public GamePanel(final Board board, final int pixelsPerSquare) {
        m_pixelsPerSquare = pixelsPerSquare;
        m_board = board;
        this.setBounds(0, 0, m_board.getCells().length * m_pixelsPerSquare, m_board.getCells()[0].length * m_pixelsPerSquare);
        this.setBackground(FREE_CELL_COLOR);
        this.setBorder(new LineBorder(Color.BLACK));
        this.setLayout(null);

        this.setVisible(true);
    }
    public void drawBoard() {
        this.removeAll();
        final boolean[][] boardCells = m_board.getCells();
        for (int i = 0 ; i < boardCells.length ; ++i) {
            for (int j = 0 ; j < boardCells[0].length ; ++j) {
                if (boardCells[i][j]) {
                    var occupiedCell = new PieceSquare(OCCUPIED_CELL_COLOR);
                    occupiedCell.setBounds(j * m_pixelsPerSquare, i * m_pixelsPerSquare, m_pixelsPerSquare, m_pixelsPerSquare);
                    this.add(occupiedCell);
                }
            }
        }
    }
    public boolean canPieceMoveDown(final Piece piece) {
        m_board.removePiece(piece);
        for (Position position : piece.positions()) {
            if (m_board.getCells()[position.row()][position.col()] || position.row() >= m_board.getCells().length - 1) {
                m_board.addPiece(piece);
                return false;
            }
        }
        m_board.addPiece(piece);
        return true;
    }
    public boolean addPiece(final Piece piece) {
        return m_board.addPiece(piece);
    }
    public void removePiece(final Piece piece) {
        m_board.removePiece(piece);
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
