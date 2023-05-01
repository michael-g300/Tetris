package piece_factory;

import pieces.I_shape;
import pieces.Piece;
import pieces.T_shape;

public class T_shapeCreator implements PieceCreator {
    @Override
    public Piece create() {
        return new T_shape();
    }
}
