package piece_factory;

import pieces.I_shape;
import pieces.Piece;
import pieces.Z_shape;

public class Z_shapeCreator implements PieceCreator {
    @Override
    public Piece create() {
        return new Z_shape();
    }
}
