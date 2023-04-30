package pieces;

import components.Position;

import java.util.ArrayList;
import java.util.List;

public class T_shape extends BasicPiece implements Piece {
    private static final int CELL_NUM = 4;
    private static final int ROTATIONS_NUM = 4;
    private static final List<Position> m_piecePositions = new ArrayList<>(CELL_NUM);
    private int rotation = 0;
    static {
        m_piecePositions.add(new Position(1, 0));
        m_piecePositions.add(new Position(0, 0));
        m_piecePositions.add(new Position(0, -1));
        m_piecePositions.add(new Position(0, 1));
    }
    public T_shape() {
        super(new BasicPieceBuilder(m_piecePositions));
    }
    @Override
    public boolean rotate() {
        var newPositions = new ArrayList<Position>();
        switch (rotation % ROTATIONS_NUM) {
            case 0 -> {
                newPositions.add(new Position(0, -1));
                newPositions.add(new Position(0, 0));
                newPositions.add(new Position(-1, 0));
                newPositions.add(new Position(1, 0));
            }
            case 1 -> {
                newPositions.add(new Position(-1, 0));
                newPositions.add(new Position(0, 0));
                newPositions.add(new Position(0, 1));
                newPositions.add(new Position(0, -1));
            }
            case 2 -> {
                newPositions.add(new Position(0, 1));
                newPositions.add(new Position(0, 0));
                newPositions.add(new Position(1, 0));
                newPositions.add(new Position(-1, 0));
            }
            case 3 -> {
                newPositions.add(new Position(1, 0));
                newPositions.add(new Position(0, 0));
                newPositions.add(new Position(0, -1));
                newPositions.add(new Position(0, 1));
            }
        }
        ++ rotation;
        this.setPositions(newPositions);
        return true;
    }
}
