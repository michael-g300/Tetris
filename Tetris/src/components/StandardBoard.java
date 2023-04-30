package components;

import pieces.BasicPiece;
import pieces.Piece;

import java.util.Arrays;

public class StandardBoard implements Board {
    private final boolean [][] m_cells;
    private final Position m_startingPosition;
    private int m_rowsFinished = 0;
    public StandardBoard(final int rows, final int columns) {
        m_cells = new boolean[rows][columns];
        m_startingPosition = new Position(0, columns / 2 - 1);
    }
    public boolean[][] getCells() {
        return m_cells;
    }

    @Override
    public int getFinishedRows() {
        return m_rowsFinished;
    }

    public boolean addPiece(Piece piece) {
        if (piece.getCenterPosition() == null) {
            piece = new BasicPiece.BasicPieceBuilder(piece.positions()).center(m_startingPosition).Build();
        }
        for (Position position : piece.positions()) {
            if (m_cells[position.row()][position.col()]) {
                return false;
            }
            m_cells[position.row()][position.col()] = true;
        }
        return true;
    }

    @Override
    public boolean rotate(final Piece piece) {
        removePiece(piece);
        var rotatedPiece = piece;
        rotatedPiece.rotate();
        for (Position position : rotatedPiece.positions()) {
            if (position.col() < 0 || position.col() >= m_cells[0].length || position.row() < 0 || position.row() >= m_cells.length) {
                return false;
            }
        }
        this.addPiece(rotatedPiece);
        return true;
    }

    public boolean moveDown(final Piece piece) {
        removePiece(piece);
        for (Position position : piece.positions()) {
            if (position.row() >= m_cells.length - 1 || m_cells[position.row() + 1][position.col()]) {
                addPiece(piece);
                return false;
            }
        }
        piece.moveDown();
        addPiece(piece);
        return true;
    }

    public void removePiece(final Piece piece) {
        for (Position position : piece.positions()) {
            m_cells[position.row()][position.col()] = false;
        }
    }

    public boolean moveLeft(final Piece piece) {
        removePiece(piece);
        for (Position position : piece.positions()) {
            if (position.col() == 0 || m_cells[position.row()][position.col() - 1]) {
                addPiece(piece);
                return false;
            }
        }
        piece.moveLeft();
        addPiece(piece);
        return true;
    }
    public boolean moveRight(final Piece piece) {
        removePiece(piece);
        for (Position position : piece.positions()) {
            if (position.col() == m_cells[0].length - 1 || m_cells[position.row()][position.col() + 1]) {
                addPiece(piece);
                return false;
            }
        }
        piece.moveRight();
        addPiece(piece);
        return true;
    }
    public void checkFinishedRows() {
        for (int i = 0 ; i < m_cells.length ; ++i) {
            int j;
            for (j = 0 ; j < m_cells[0].length ; ++j) {
                if (!m_cells[i][j]) {
                    return;
                }
            }
            cleanRow(i);
            ++ m_rowsFinished;
        }
    }
    private void cleanRow(final int rowToDelete) {
        deleteRow(rowToDelete);
        updateBoard(rowToDelete);
    }

    private void deleteRow(final int rowToDelete) {
        Arrays.fill(m_cells[rowToDelete], false);
    }
    private void updateBoard(final int rowToDelete) {
        for (int i = rowToDelete ; i >= 0 ; --i) {
            System.arraycopy(m_cells[i], 0, m_cells[i - 1], 0, m_cells[rowToDelete].length);
        }
    }
}
