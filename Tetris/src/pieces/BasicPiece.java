package pieces;

import components.Position;

import java.util.ArrayList;
import java.util.List;

public class BasicPiece implements Piece {
    private List<Position> m_RelativePositions;
    private Position m_center;
    public BasicPiece(final BasicPieceBuilder pieceBuilder) {
        this.m_RelativePositions = pieceBuilder.m_RelativePositions;
        this.m_center = pieceBuilder.m_center;
    }
    @Override
    public List<Position> positions() {
        var piecePositions = new ArrayList<Position>(m_RelativePositions.size());
        for (Position position : m_RelativePositions) {
            if (m_center != null) {
                piecePositions.add(new Position(m_center.row() + position.row(), m_center.col() + position.col()));
            }
            else {
                piecePositions.add(position);
            }
        }
        return piecePositions;
    }

    @Override
    public void setPositions(final List<Position> newPositions) {
        m_RelativePositions = newPositions;
    }

    @Override
    public void setCenterPosition(final Position position) {
        m_center = position;
    }

    @Override
    public Position getCenterPosition() {
        return m_center;
    }

    @Override
    public boolean rotate() {   //will be calculated in each individual piece
        return false;
    }

    @Override
    public void moveDown() {
        m_center.setRow(m_center.row() + 1);
    }

    @Override
    public void moveLeft() {
        m_center.setCol(m_center.col() - 1);
    }

    @Override
    public void moveRight() {
        m_center.setCol(m_center.col() + 1);
    }
    public static class BasicPieceBuilder {
        private final List<Position> m_RelativePositions;
        private Position m_center;
        public BasicPieceBuilder(final List<Position> relativePositions) {
            this.m_RelativePositions = relativePositions;
        }
        public BasicPieceBuilder center(final Position center) {
            this.m_center = center;
            return this;
        }
        public BasicPiece Build() {
            return new BasicPiece(this);
        }
    }
}
