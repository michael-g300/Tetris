package pieces;

import components.Position;

import java.util.ArrayList;
import java.util.List;

public class O_shape extends BasicPiece implements Piece {
    private static final int CELL_NUM = 4;
    private static final int ROTATIONS_NUM = 0;
    private static final List<Position> m_piecePositions = new ArrayList<>(CELL_NUM);
    private int rotation = 0;
    static {
        m_piecePositions.add(new Position(0, 0));
        m_piecePositions.add(new Position(0, 1));
        m_piecePositions.add(new Position(1, 0));
        m_piecePositions.add(new Position(1, 1));
    }
    public O_shape() {
        super(new BasicPieceBuilder(m_piecePositions));
    }

    @Override
    public boolean rotate() {
        return true;
    }
}
