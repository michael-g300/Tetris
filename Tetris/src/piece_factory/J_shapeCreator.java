package piece_factory;

import pieces.I_shape;
import pieces.J_shape;
import pieces.Piece;

public class J_shapeCreator implements PieceCreator {
    @Override
    public Piece create() {
        return new J_shape();
    }
}
