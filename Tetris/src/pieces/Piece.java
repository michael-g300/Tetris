package pieces;

import components.Position;

import java.util.List;

public interface Piece {
    List<Position> positions();
    void setPositions(List<Position> newPositions);
    void setCenterPosition(Position position);
    Position getCenterPosition();
    boolean rotate();
    void moveDown();
    void moveLeft();
    void moveRight();
}
