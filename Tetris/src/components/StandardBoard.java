package components;

import pieces.BasicPiece;
import pieces.Piece;

import java.util.Arrays;

public class StandardBoard implements Board {
    private static final int MAX_NUM_OF_PIECE_ROTATIONS = 4;
    private final boolean [][] m_cells;
    private Position m_startingPosition;
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
        Piece newPiece = piece;
        if (newPiece.getCenterPosition() == null) {
            m_startingPosition = new Position(0, m_cells[0].length / 2 - 1);
            newPiece = new BasicPiece.BasicPieceBuilder(piece.positions()).center(m_startingPosition).Build();
        }
        for (Position position : newPiece.positions()) {
            if (m_cells[position.row()][position.col()]) {
                System.out.println("Position occupied : " + position.toString());
                return false;
            }
            m_cells[position.row()][position.col()] = true;
        }
        piece.setCenterPosition(newPiece.getCenterPosition());
        return true;
    }

    @Override
    public boolean rotate(final Piece piece) {
        removePiece(piece);
        piece.rotate();
        for (Position position : piece.positions()) {
            if (position.col() < 0 || position.col() >= m_cells[0].length || position.row() < 0 || position.row() >= m_cells.length || m_cells[position.row()][position.col()]) {
                for (int i = 1 ; i < MAX_NUM_OF_PIECE_ROTATIONS ; ++i) {
                    piece.rotate();
                }
                this.addPiece(piece);
                return false;
            }
        }
        this.addPiece(piece);
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
                    break;
                }
            }
            if (j == m_cells[0].length) {
                cleanRow(i);
                ++ m_rowsFinished;
            }
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
        for (int i = rowToDelete ; i > 0 ; --i) {
            System.arraycopy(m_cells[i - 1], 0, m_cells[i], 0, m_cells[rowToDelete].length);
        }
    }
}
